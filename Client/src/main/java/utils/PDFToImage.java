package utils;

import com.google.gson.Gson;
import javafx.scene.image.Image;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class PDFToImage {

    private static final Logger logger = Logger.getLogger(PDFToImage.class.getName());

    private PDFToImage() {}

    /** This socket is used to communicate with the python script that will provide the images :D */
    private static Socket converter;
    /** Streams */
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    /** Need a lock to grant correct accesses to the socket! (like mutexes)*/
    private static final Object writeLock = new Object();
    private static final Object readLock = new Object();

    /**
     * This function should run the python pdfToImageConverter script in base of which is the actual OS.
     *
     * The executable script should be ridden as a stream from resources and then a temporary file with this
     * script is created and executed.
     *
     * @param port the communication port between this and the python script
     * @throws IOException in case there is a problem creating the temp file
     */
    private static void startPythonServer(int port) throws IOException {

        String os = System.getProperty("os.name").toLowerCase();
        String exeResource = "";

        if (os.contains("win")) {
            exeResource = "/pdfToImageConverter.exe";
        } else if (os.contains("linux")) {
            exeResource = "/pdfToImageConverter";
        } else if (os.contains("mac")) {
            exeResource = "/pdfToImageConverter";
        }

        InputStream input = Objects.requireNonNull(
                PDFToImage.class.getResourceAsStream(exeResource),
                "Missing pdfToImageConverter script!"
        );

        File tempExe;

        if (os.contains("win")) {
            Path secureDir = Files.createTempDirectory("pdf_converter_dir");
            secureDir.toFile().deleteOnExit();

            tempExe = Files.createTempFile(secureDir, "pdfToImageConverter", ".exe").toFile();
            if (
                    !tempExe.setReadable(true, true) ||
                    !tempExe.setWritable(true, true) ||
                    !tempExe.setExecutable(true, true)
            ) {
                return;
            }
        } else {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            tempExe = Files.createTempFile("pdfToImageConverter", "", attr).toFile();
        }

        tempExe.deleteOnExit();

        Files.copy(input, tempExe.toPath(), StandardCopyOption.REPLACE_EXISTING);
        if (!tempExe.setExecutable(true)) {
            logger.warning("Could not set python script as executable...");
        }

        ProcessBuilder pb = new ProcessBuilder(tempExe.getAbsolutePath(), String.valueOf(port));
        pb.start();
    }

    /**
     * This function should return a free port for the communication
     * @return a port number
     */
    private static int findFreePort() {
        int port;
        try (ServerSocket tempSocket = new ServerSocket(0)) {
            port = tempSocket.getLocalPort();
        } catch (IOException _) {
            return -1;
        }

        return port;
    }

    /**
     * This function should actually start the python script and the communication socket.
     */
    public static void initialize() {

        try {
            int freePort = findFreePort();
            startPythonServer(freePort);

            while (true) {
                Socket s = new Socket();
                try {
                    s.connect(new InetSocketAddress("localhost", freePort), 500);
                    s.setSoTimeout(10000);
                    s.setTcpNoDelay(true);
                    converter = s;
                    break;
                } catch (IOException _) {
                    try {
                        s.close();
                    } catch (IOException _) {
                        // Nothing to do...
                    }
                }
            }

            outputStream = new DataOutputStream(converter.getOutputStream());
            inputStream = new DataInputStream(converter.getInputStream());
        } catch (IOException _) {
            logger.severe("Failed initializing python server");
            System.exit(-1);
        }
    }
    /**
     * This function should send a conversion request to the python script.
     * This function is thread safe: the output stream is protected by a mutex that avoids multiple writeUTF at
     * the same time from different threads.
     * The same thing for reading.
     *
     * WARNING: if multiple threads launch this function, they could receive back an Image with a different pageId
     * from the one that the requested to convert. For this app, I don't need that each thread receives only its
     * page image.
     *
     * @param pdfPath the path of the pdf
     * @param pageId the page to convert
     * @param dpi the quality
     * @return a PageToImageResponse with page id and the image
     */
    public static PageToImageResponse requestPageToImage(String pdfPath, int pageId, int dpi) {

        if (converter == null) {
            initialize();
        }

        PageToImageRequest pageToImageRequest = new PageToImageRequest(pdfPath, pageId, dpi);

        try {

            synchronized (writeLock) {
                outputStream.writeUTF(pageToImageRequest.toJson());
                outputStream.flush();
            }

            int rPageId;
            byte[] image;

            synchronized (readLock) {
                rPageId = inputStream.readInt();
                int rPageSize = inputStream.readInt();
                image = inputStream.readNBytes(rPageSize);
            }

            return new PageToImageResponse(rPageId, new Image(new ByteArrayInputStream(image)));
        } catch (IOException _) {
            return null;
        }
    }

    public static class PageToImageResponse {

        private int pageId;
        private Image image;

        public PageToImageResponse(int pageId, Image image) {
            this.pageId = pageId;
            this.image = image;
        }

        public int getPageId() {
            return pageId;
        }

        public Image getImage() {
            return image;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }

    private static class PageToImageRequest {

        private String pdfPath;
        private int pageId;
        private int dpi;

        public PageToImageRequest(String pdfPath, int pageId, int dpi) {
            this.pdfPath = pdfPath;
            this.pageId = pageId;
            this.dpi = dpi;
        }

        public String toJson() { return new Gson().toJson(this); }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public void setDpi(int dpi) {
            this.dpi = dpi;
        }

        public void setPdfPath(String pdfPath) {
            this.pdfPath = pdfPath;
        }

        public int getDpi() {
            return dpi;
        }

        public int getPageId() {
            return pageId;
        }

        public String getPdfPath() {
            return pdfPath;
        }
    }
}
