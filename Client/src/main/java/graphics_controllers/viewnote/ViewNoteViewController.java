package graphics_controllers.viewnote;

import graphics_controllers.GraphicsController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import models.note.NoteModel;
import utils.PDFToImage;
import views.ViewNavigator;
import views.viewnote.ViewNoteView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ViewNoteViewController extends GraphicsController<ViewNoteView> {

    /** Max number of cached images */
    private static final int MAX_CACHED_IMAGES = 10;

    /** Max number of images in loading process */
    private static final int MAX_LOADING_IMAGES = 7;

    /** Loaded pages with distance > UNUSED_PAGE_THRESHOLD from visiblePage will be unloaded */
    private static final int UNUSED_PAGE_THRESHOLD = 5;

    /** The page that is actually visible in the scroll pane. It's used to decide which pages should be actually
     *  loaded.
     */
    private int visiblePage = 0;

    private static final double MIN_ZOOM = 0.2;
    private static final double MAX_ZOOM = 5.0;
    private static final double ZOOM_FACTOR = 1.05;

    /** An ExecutorService is basically a pool of n threads. Every thread executes a task and it's blocked until the
     *  execution finishes.
     *  The oldest thread can be discarded if a new one is added.
     *  MAX_LOADING_IMAGES is the max number of threads that can work at the same time.
     *
     *  This executorService allows to load a small number of images and also manages "scrolling" and "fast scrolling"
     *  in the right way: it always prefers the pages that the user is actually watching.
     */
    ExecutorService executorService = new ThreadPoolExecutor(
            MAX_LOADING_IMAGES,
            MAX_LOADING_IMAGES,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(MAX_LOADING_IMAGES),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );


    /** <a href="https://www.baeldung.com/java-linked-hashmap">LINK</a>
     * A LinkedHashmap with accessOrder is perfect to represent the loaded pages and to remember (and remove) the pages
     * that are no longer needed.
     *
     * This provides a simple way to clean up the memory: searching and deleting elements is done in O(1).
     * It removes an Image only if there are more than MAX_CACHED_IMAGES loaded and the distance between an image and
     * the actual visible page is greater than UNUSED_PAGE_THRESHOLD.
     */
    LinkedHashMap<Integer, Image> loadedPages = new LinkedHashMap<>(MAX_CACHED_IMAGES, (float) 3 /4, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Image> eldest) {

            if (size() > MAX_CACHED_IMAGES && Math.abs(eldest.getKey() - visiblePage) > UNUSED_PAGE_THRESHOLD) {
                ImageView imageView = (ImageView) getView().getImagesContainer().getChildren().get(eldest.getKey());
                imageView.setImage(null);
                return true;
            }

            return false;
        }
    };


    /** The note that will be displayed */
    private final NoteModel note;

    public ViewNoteViewController(ViewNoteView view, NoteModel note) {
        super(view);

        this.note = note;
        getView().getNoteNameLabel().setText(note.getName());
        setupUI();
        setupZoom();
    }

    @Override
    public void loaded() {
        getView().getScrollPane().vvalueProperty().addListener(_ -> calculateNewVisiblePage());
        getView().getPageDownButton().setOnMouseClicked(_ -> pageDownButtonClicked());
        getView().getPageUpButton().setOnMouseClicked(_ -> pageUpButtonClicked());
        getView().getCloseButton().setOnMouseClicked(_ -> closeButtonClicked());
    }

    private void setupUI() {
        getView().getScrollPane().setMaxWidth(note.getPdf().getPdfConfig().getMaxWidth());
        for (int i = 0; i < note.getPdf().getPdfConfig().getNumberOfPages(); i++) {

            ImageView iv = new ImageView();
            iv.setFitWidth(note.getPdf().getPdfConfig().getMaxWidth());
            iv.setFitHeight(note.getPdf().getPdfConfig().getMaxHeight());
            getView().getImagesContainer().getChildren().add(iv);
        }
        onVisiblePageUpdated(0);
    }

    private void setupZoom() {

        getView().getScrollPane().addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {

                double delta = event.getDeltaY();
                double newScale = getView().getImagesContainer().getScaleY();

                if (delta > 0) {
                    newScale *= ZOOM_FACTOR;
                } else {
                    newScale /= ZOOM_FACTOR;
                }

                newScale = Math.max(MIN_ZOOM, Math.min(newScale, MAX_ZOOM));

                getView().getImagesContainer().setScaleX(newScale);
                getView().getImagesContainer().setScaleY(newScale);
                event.consume();

                calculateNewVisiblePage();
            }
        });
    }

    private void pageDownButtonClicked() {
        scrollToPage(visiblePage + 1);
    }

    private void pageUpButtonClicked() {
        scrollToPage(visiblePage - 1);
    }

    private void closeButtonClicked() {
        ViewNavigator.displayHomeView();
    }

    /**
     * This function should scroll the ScrollPane to the new target page.
     *
     * It should calculate the height of a page (with zoom) and then scroll to pageHeight * targetPage (to percent)
     * @param targetPage the page that has to be shown
     */
    private void scrollToPage(int targetPage) {

        if (targetPage < 0 || targetPage >= note.getPdf().getPdfConfig().getNumberOfPages()) {
            return;
        }

        double currentZoom = getView().getImagesContainer().getScaleX();
        double pageHeight = note.getPdf().getPdfConfig().getMaxHeight() * currentZoom;
        double spacing = getView().getImagesContainer().getSpacing() * currentZoom;
        // Height of a zoomed page in pixels
        double totalPageHeight = pageHeight + spacing;
        // The Y of the next page
        double nextPageY = targetPage * totalPageHeight;

        // Total scrollable height. (Ignores the top bar)
        // getBoundsInParent() calculates the VBox's height using also zoom value
        double scrollableValue = getView().getImagesContainer().getBoundsInParent().getHeight() - getView().getScrollPane().getViewportBounds().getHeight();

        // nextPageY to percent
        double vValue = nextPageY / scrollableValue;
        getView().getScrollPane().setVvalue(vValue);
    }

    /**
     * This function should launch the threads that will "load the images".
     * I decided to load the visible page and the +-3 near pages.
     *
     * Check the image loading process from PDFToImage.requestPageToImage :D
     *
     * Every thread should manage a loading process and then it should set the loaded image in the ImageView.
     * Actually, a thread that starts loading image "i", could end setting the image "j"!="i" because these
     * threads can receive any loaded image.
     * It's not necessary that every thread loads the image it started loading: every image will be set in the
     * right place anyway!
     *
     * @param visiblePage the updated visible page
     */
    private void onVisiblePageUpdated(int visiblePage) {
        this.visiblePage = visiblePage;
        getView().getPageLabel().setText(String.format("%d/%d", visiblePage+1, note.getPdf().getPdfConfig().getNumberOfPages()));

        for (int i = -3; i <= 3; i++) {

            int pageId = visiblePage + i;

            if (pageId < 0 || pageId >= note.getPdf().getPdfConfig().getNumberOfPages() || loadedPages.get(pageId) != null) {
                continue;
            }

            executorService.submit(() -> {

                PDFToImage.PageToImageResponse response = note.getPdf().pageToImage(pageId);

                if (response == null)
                    return;

                ImageView imageView = (ImageView) getView().getImagesContainer().getChildren().get(response.getPageId());
                Image img = response.getImage();

                Platform.runLater(() -> {
                    imageView.setImage(img);
                    loadedPages.put(response.getPageId(), img);
                });
            });
        }
    }


    /**
     * This function should calculate which page the user is seeing and notify the PageUpdated event when
     * needed.
     */
    private void calculateNewVisiblePage() {

        double currentZoom = getView().getImagesContainer().getScaleX();
        double scrolledPixels = calculateCurrentlyScrolledPixels();

        // The height of every page (every ImageView)
        double pageHeight = note.getPdf().getPdfConfig().getMaxHeight() * currentZoom;
        double pageSpacing = getView().getImagesContainer().getSpacing() * currentZoom;
        // Zoomed page height (including the spacing). Also spacing should be scaled because I'm scaling the parent VBox
        double totalPageHeight = pageHeight + pageSpacing;
        // The part of height the user can see of the ScrollPane
        int page = (int) Math.floor((scrolledPixels + 5) / totalPageHeight);

        if (page != visiblePage) {
            onVisiblePageUpdated(page);
        }
    }

    private double calculateCurrentlyScrolledPixels() {
        // percent value representing scroll position
        double scrollValue = getView().getScrollPane().getVvalue();
        // Total scrollable height. (Ignores the top bar)
        // getBoundsInParent() calculates the VBox's height using also zoom value
        double scrollableValue = getView().getImagesContainer().getBoundsInParent().getHeight() - getView().getScrollPane().getViewportBounds().getHeight();
        // The level that the user is actually seeing
        return scrollValue * scrollableValue;
    }
}
