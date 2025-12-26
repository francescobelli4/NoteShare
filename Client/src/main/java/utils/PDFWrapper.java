package utils;

import com.google.gson.Gson;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

/**
 * This is a wrapper class that should define a pdf file better.
 * Every time the user adds a note and chooses a pdf file, a wrapper for that file should be created
 * and it should add some operation to a pdf file:
 * it should allow the creation of an image from a pdf page and it should allow to create a PDFConfig, which
 * is a class that can be encoded to json and represents some important data for that pdf file that are useful
 * to display the pdf and should not be re-calculated every time the user wants to see the note.
 *
 * In demo mode, the config file is not actually saved in a persistent file.
 */
public class PDFWrapper {

    /** Needed to convert from PDF's measures to pixels */
    private static final float PDF_POINTS_PER_INCH = 72f;
    /** Used to specify the image quality and calculate the image dimensions in pixel*/
    private static final float IMAGE_DPI = 150f;

    /** The config associated with this PDF */
    private final PDFConfig pdfConfig;

    /**
     * Base constructor
     *
     * This constructor should create a new PDFWrapper and set up its config class.
     *
     * @param pdfFile the PDF file
     */
    public PDFWrapper(File pdfFile) {
        pdfConfig = new PDFConfig();
        pdfConfig.setPdfFile(pdfFile.getPath());

        try {
            PDDocument pdfDocument = Loader.loadPDF(pdfFile);
            pdfConfig.setNumberOfPages(pdfDocument.getNumberOfPages());

            for (PDPage page : pdfDocument.getPages()) {

                float pageWidthWithDPI = (page.getBBox().getWidth() / PDF_POINTS_PER_INCH) * IMAGE_DPI;
                float pageHeightWithDPI = (page.getBBox().getHeight() / PDF_POINTS_PER_INCH) * IMAGE_DPI;

                if (pageWidthWithDPI > pdfConfig.getMaxWidth()) {
                    pdfConfig.setMaxWidth(pageWidthWithDPI);
                }

                if (pageHeightWithDPI > pdfConfig.getMaxHeight()) {
                    pdfConfig.setMaxHeight(pageHeightWithDPI);
                }
            }
        } catch (IOException _) {
            // TODO ?????
        }
    }

    /**
     * Constructor with config
     *
     * This constructor should create the PDFWrapper from a config class
     *
     * @param config the config class
     */
    public PDFWrapper(PDFConfig config) {
        //TODO
        this.pdfConfig = config;
    }

    /**
     * This function should convert a pdf page to a image (which is contained in PageToImageResponse, together
     * with the page id).
     *
     * More info about this conversion in PDFToImage class :D
     *
     * @param pageId the id of the page that should be converted
     * @return a response that contains page id and the image
     */
    public PDFToImage.PageToImageResponse pageToImage(int pageId) {
        return PDFToImage.requestPageToImage(pdfConfig.getPdfFilePath(), pageId, (int)IMAGE_DPI);
    }

    public PDFConfig getPdfConfig() {
        return pdfConfig;
    }

    /**
     * This class represents a config for every pdf wrapper that can be encoded and decoded.
     * This should be used to keep trace of the data needed to display the pdf.
     */
    public class PDFConfig {
        private float maxWidth;
        private float maxHeight;
        private String pdfFilePath;
        private int numberOfPages;

        public String toJson() { return new Gson().toJson(this); }

        public static PDFConfig fromJson(String json) {
            return new Gson().fromJson(json, PDFConfig.class);
        }

        public void setMaxWidth(float maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMaxHeight(float maxHeight) {
            this.maxHeight = maxHeight;
        }

        public void setPdfFile(String pdfFilePath) {
            this.pdfFilePath = pdfFilePath;
        }

        public void setNumberOfPages(int numberOfPages) {
            this.numberOfPages = numberOfPages;
        }

        public float getMaxWidth() {
            return maxWidth;
        }

        public float getMaxHeight() {
            return maxHeight;
        }

        public String getPdfFilePath() {
            return pdfFilePath;
        }

        public int getNumberOfPages() {
            return numberOfPages;
        }
    }
}
