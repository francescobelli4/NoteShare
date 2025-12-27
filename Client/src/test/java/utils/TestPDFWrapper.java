package utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TestPDFWrapper {

    PDFWrapper pdfWrapper;

    @BeforeEach
    void setup(@TempDir Path tempDir) {

        PDDocument mockedPDDocument = mock(PDDocument.class);
        PDPage mockedPage = mock(PDPage.class);
        PDRectangle mockedBBox = mock(PDRectangle.class);
        when(mockedBBox.getWidth()).thenReturn(100f);
        when(mockedBBox.getHeight()).thenReturn(100f);
        when(mockedPage.getBBox()).thenReturn(mockedBBox);

        PDPageTree mockedPages = mock(PDPageTree.class);
        mockedPages.add(mockedPage);

        when(mockedPDDocument.getPages()).thenReturn(mockedPages);
        when(mockedPDDocument.getNumberOfPages()).thenReturn(1);
        when(mockedPages.iterator()).thenReturn(List.of(mockedPage).iterator());

        Path filePath = tempDir.resolve("test.txt");
        File fileRealeTemporaneo = filePath.toFile();

        try (MockedStatic<Loader> staticLoader = mockStatic(Loader.class)) {
            staticLoader.when(() -> Loader.loadPDF(any(File.class))).thenReturn(mockedPDDocument);

            pdfWrapper = new PDFWrapper(fileRealeTemporaneo);
        }
    }

    @Test
    void pageToImage() {

    }

    @Test
    void getPdfConfig() {
        assertNotNull(pdfWrapper.getPdfConfig());
    }

    @Test
    void testPdfConfig() {

        pdfWrapper.getPdfConfig().setPdfFile("TMOOO");
        pdfWrapper.getPdfConfig().setMaxWidth(1024.5f);
        pdfWrapper.getPdfConfig().setMaxHeight(768.0f);
        pdfWrapper.getPdfConfig().setNumberOfPages(10);

        assertEquals("TMOOO", pdfWrapper.getPdfConfig().getPdfFilePath());
        assertEquals(1024.5f, pdfWrapper.getPdfConfig().getMaxWidth());
        assertEquals(768.0f, pdfWrapper.getPdfConfig().getMaxHeight());
        assertEquals(10, pdfWrapper.getPdfConfig().getNumberOfPages());
    }
}