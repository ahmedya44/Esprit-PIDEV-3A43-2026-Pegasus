package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.dao.ProduitDAO;
import com.pegasus.config.PropertiesLoader;
import com.pegasus.entities.Art;
import com.pegasus.entities.Course;
import com.pegasus.entities.User;
import com.pegasus.entities.Produit;
import com.pegasus.services.CloudinaryService;
import com.pegasus.services.CourseCategoryClassifier;
import com.pegasus.services.CourseService;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceUser;
import com.pegasus.services.VoiceSearchService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public class HomeController {
    private static final String PUBLIC_ROOT_DIR = "C:\\Users\\MSI\\PiV3\\public";
    private static final String PROFILE_PICS_DIR_1 = "C:\\Users\\MSI\\PiV3\\public\\profileStylized";
    private static final String PROFILE_PICS_DIR_2 = "C:\\Users\\MSI\\PiV3\\public\\profilePics";
    private static final String PROFILE_DEFAULT_DIR = "C:\\Users\\MSI\\PiV3\\public\\profileCom";
    private static final String[] IMAGE_EXTENSIONS = {".png", ".jpg", ".jpeg", ".webp"};
    private static final String CF_MODEL = "@cf/runwayml/stable-diffusion-v1-5-img2img";
    private static final Duration CF_TIMEOUT = Duration.ofSeconds(90);
    private static final String CLOUDFLARE_CONFIG_PATH = "/cloudflare.properties";
    private static final int HOME_PREVIEW_LIMIT = 4;
    private static final double HOME_CARD_WIDTH = 260;
    private static final double HOME_IMAGE_HEIGHT = 140;

    @FXML
    private Label userStatusLabel;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button roleRequestButton;

    @FXML
    private Button roleRequestHistoryButton;

    @FXML
    private Button navAuthButton;

    @FXML
    private Button navProfileButton;

    @FXML
    private MenuButton navAccountMenu;

    @FXML
    private Button navBackofficeButton;

    @FXML
    private Button navCoursesDashboardButton;

    @FXML
    private VBox adminUsersBox;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colStatus;

    private ServiceUser serviceUser;
    private CloudinaryService cloudinaryService;
    private VoiceSearchService voiceSearchService;
    private CourseService courseService;
    private ProduitDAO produitDAO;
    private ServiceArt artService;
    private final ObservableList<User> allUsers = FXCollections.observableArrayList();
    private Timeline scrollTimeline;
    private Timeline heroFloatTimeline;

    @FXML
    private StackPane heroCard;

    @FXML
    private VBox heroTextBox;

    @FXML
    private VBox heroShowcase;

    @FXML
    private FlowPane homeFeatureCards;

    @FXML
    private FlowPane homeCoursesContainer;

    @FXML
    private FlowPane homeProductsContainer;

    @FXML
    private FlowPane homeGalleryContainer;

    @FXML
    private ScrollPane homeScrollPane;

    @FXML
    private FlowPane homeDetailCards;

    @FXML
    private Label coursesCountLabel;

    @FXML
    private Label productsCountLabel;

    @FXML
    private Label galleryCountLabel;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> sortByCombo;

    @FXML
    private ComboBox<String> sortOrderCombo;

    @FXML
    private ComboBox<String> statusUpdateCombo;

    @FXML
    private Button applyStatusButton;

    @FXML
    private Button voiceSearchButton;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Label profileImageHintLabel;

    @FXML
    private VBox profileBox;

    @FXML
    private Button expandProfileButton;

    @FXML
    public void initialize() {
        if (colId != null) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
        if (colUsername != null) {
            colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        }
        if (colEmail != null) {
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        }
        if (colRole != null) {
            colRole.setCellValueFactory(new PropertyValueFactory<>("dtype"));
        }
        if (colStatus != null) {
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        }

        if (sortByCombo != null) {
            sortByCombo.setItems(FXCollections.observableArrayList("Username", "Email", "Role", "Status"));
            sortByCombo.getSelectionModel().select("Username");
        }
        if (sortOrderCombo != null) {
            sortOrderCombo.setItems(FXCollections.observableArrayList("Ascending", "Descending"));
            sortOrderCombo.getSelectionModel().select("Ascending");
        }
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> applySearchAndSort());
        }
        if (statusUpdateCombo != null) {
            statusUpdateCombo.setItems(FXCollections.observableArrayList(
                    ServiceUser.STATUS_ACTIVE,
                    ServiceUser.STATUS_PENDING_VERIFICATION,
                    "SUSPENDED"
            ));
            statusUpdateCombo.getSelectionModel().select(ServiceUser.STATUS_ACTIVE);
        }
        if (usersTable != null) {
            usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
                boolean hasSelection = newUser != null;
                if (applyStatusButton != null) {
                    applyStatusButton.setDisable(!hasSelection);
                }
                if (statusUpdateCombo != null && newUser != null && newUser.getStatus() != null) {
                    statusUpdateCombo.getSelectionModel().select(newUser.getStatus());
                }
            });
        }
        if (profileImageView != null) {
            profileImageView.setOnMouseClicked(event -> onProfileImageClicked());
            profileImageView.setStyle("-fx-cursor: hand;");
        }
        if (expandProfileButton != null) {
            expandProfileButton.setVisible(false);
            expandProfileButton.setManaged(false);
        }
        refreshUserState();
        if (homeScrollPane != null) {
            homeScrollPane.setOnScroll(event -> {
                double direction = Math.signum(event.getDeltaY());
                double magnitude = Math.max(0.08, Math.min(0.22, Math.abs(event.getDeltaY()) * 0.0075));
                double target = clamp(homeScrollPane.getVvalue() - direction * magnitude, 0, 1);

                if (scrollTimeline != null) {
                    scrollTimeline.stop();
                }

                scrollTimeline = new Timeline(
                        new KeyFrame(javafx.util.Duration.ZERO,
                                new KeyValue(homeScrollPane.vvalueProperty(), homeScrollPane.getVvalue(), Interpolator.EASE_BOTH)),
                        new KeyFrame(javafx.util.Duration.millis(240),
                                new KeyValue(homeScrollPane.vvalueProperty(), target, Interpolator.EASE_BOTH))
                );
                scrollTimeline.play();
                event.consume();
            });
        }
        if (isHomeView()) {
            loadHomePreviews();
            SceneNavigator.authModalOpenProperty().addListener((obs, wasOpen, isOpen) -> {
                if (isOpen) {
                    stopHomeAmbientMotion();
                } else {
                    startHomeAmbientMotion();
                }
            });
            Platform.runLater(this::playHomeAnimations);
        }
    }

    private boolean isHomeView() {
        return homeCoursesContainer != null || homeProductsContainer != null || homeGalleryContainer != null;
    }

    private void loadHomePreviews() {
        loadCoursePreviews();
        loadGalleryPreviews();
        loadProductPreviews();
    }

    private void loadCoursePreviews() {
        if (homeCoursesContainer == null) {
            return;
        }

        homeCoursesContainer.getChildren().clear();
        try {
            if (courseService == null) {
                courseService = new CourseService();
            }
            List<Course> courses = courseService.getPublishedCourses();
            List<Course> previewCourses = courses.stream()
                    .filter(this::hasCoursePreviewImage)
                    .toList();
            setCounter(coursesCountLabel, previewCourses.size());
            if (previewCourses.isEmpty()) {
                showPreviewMessage(homeCoursesContainer, "Courses with images will appear here", "Only courses that include a preview image are shown on the home page.");
                return;
            }

            for (Course course : firstItems(previewCourses, HOME_PREVIEW_LIMIT)) {
                homeCoursesContainer.getChildren().add(createCoursePreviewCard(course));
            }
        } catch (Exception exception) {
            setCounter(coursesCountLabel, 0);
            showPreviewMessage(homeCoursesContainer, "Could not load courses", "The preview will refresh when data is available.");
        }
    }

    private void loadProductPreviews() {
        if (homeProductsContainer == null) {
            return;
        }

        homeProductsContainer.getChildren().clear();
        try {
            if (produitDAO == null) {
                produitDAO = new ProduitDAO();
            }
            List<Produit> products = produitDAO.getAll().stream()
                    .filter(this::isPublicProduct)
                    .toList();
            setCounter(productsCountLabel, products.size());
            if (products.isEmpty()) {
                showPreviewMessage(homeProductsContainer, "No products yet", "Marketplace highlights will appear here.");
                return;
            }

            for (Produit produit : firstItems(products, HOME_PREVIEW_LIMIT)) {
                homeProductsContainer.getChildren().add(createProductPreviewCard(produit));
            }
        } catch (Exception exception) {
            setCounter(productsCountLabel, 0);
            showPreviewMessage(homeProductsContainer, "Could not load products", "The marketplace preview is temporarily unavailable.");
        }
    }

    private void loadGalleryPreviews() {
        if (homeGalleryContainer == null) {
            return;
        }

        homeGalleryContainer.getChildren().clear();
        try {
            if (artService == null) {
                artService = new ServiceArt();
            }
            List<Art> artworks = artService.getAllArts().stream()
                    .filter(art -> isVisibleArtStatus(art.getStatus()))
                    .toList();
            setCounter(galleryCountLabel, artworks.size());
            if (artworks.isEmpty()) {
                showPreviewMessage(homeGalleryContainer, "No gallery items yet", "Published artworks will appear here.");
                return;
            }

            for (Art art : firstItems(artworks, HOME_PREVIEW_LIMIT)) {
                homeGalleryContainer.getChildren().add(createGalleryPreviewCard(art));
            }
        } catch (Exception exception) {
            setCounter(galleryCountLabel, 0);
            showPreviewMessage(homeGalleryContainer, "Could not load gallery", "Artwork previews are temporarily unavailable.");
        }
    }

    private <T> List<T> firstItems(List<T> items, int limit) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }
        return items.subList(0, Math.min(limit, items.size()));
    }

    private boolean isPublicProduct(Produit produit) {
        if (produit == null) {
            return false;
        }
        String status = normalizeSearch(produit.getStatut());
        return !status.equals("archive") && !status.equals("refuse") && !status.equals("refused");
    }

    private boolean isVisibleArtStatus(String status) {
        String normalized = normalizeSearch(status);
        return normalized.isEmpty()
                || normalized.equals("published")
                || normalized.equals("available")
                || normalized.equals("approved");
    }

    private VBox createCoursePreviewCard(Course course) {
        VBox card = createBasePreviewCard();
        card.getChildren().add(createPreviewImage(course.getThumbnailUrl(), "Course", "home-course-media"));

        Label category = createPill(CourseCategoryClassifier.classify(course), "home-pill-gold");
        Label title = createPreviewTitle(course.getTitle());

        card.getChildren().addAll(category, title);
        configurePreviewCard(card, this::onGoToCourses);
        return card;
    }

    private VBox createProductPreviewCard(Produit produit) {
        VBox card = createBasePreviewCard();
        card.getChildren().add(createPreviewImage(produit.getImage(), "Product", "home-product-media"));

        String categoryName = produit.getCategorie() == null ? "Marketplace" : produit.getCategorie().getNom();
        Label category = createPill(categoryName, "home-pill-rose");
        Label title = createPreviewTitle(produit.getNom());
        Label description = createPreviewCopy(produit.getDescription(), 84);
        Label price = createPreviewMeta(String.format(Locale.US, "%.2f EUR", produit.getPrix()));

        card.getChildren().addAll(category, title, description, price);
        configurePreviewCard(card, this::onGoToProduit);
        return card;
    }

    private VBox createGalleryPreviewCard(Art art) {
        VBox card = createBasePreviewCard();
        card.getChildren().add(createPreviewImage(art.getImageUrl(), "Artwork", "home-gallery-media"));

        Label artist = createPill(safeText(art.getArtist(), "Gallery"), "home-pill-teal");
        Label title = createPreviewTitle(art.getTitle());
        Label description = createPreviewCopy(art.getDescription(), 88);
        Label likes = createPreviewMeta(art.getLikes() + " likes");

        card.getChildren().addAll(artist, title, description, likes);
        configurePreviewCard(card, this::onGoToGallery);
        return card;
    }

    private VBox createBasePreviewCard() {
        VBox card = new VBox(9);
        card.getStyleClass().add("home-mini-card");
        card.setPrefWidth(HOME_CARD_WIDTH);
        card.setMinWidth(HOME_CARD_WIDTH);
        card.setMaxWidth(HOME_CARD_WIDTH);
        card.setPadding(new Insets(0, 0, 14, 0));
        return card;
    }

    private StackPane createPreviewImage(String imageSource, String fallbackText, String accentClass) {
        StackPane media = new StackPane();
        media.getStyleClass().addAll("home-preview-media", accentClass);
        media.setPrefSize(HOME_CARD_WIDTH, HOME_IMAGE_HEIGHT);
        media.setMinSize(HOME_CARD_WIDTH, HOME_IMAGE_HEIGHT);
        media.setMaxSize(HOME_CARD_WIDTH, HOME_IMAGE_HEIGHT);

        String source = resolvePreviewImageSource(imageSource);
        if (source != null) {
            try {
                Image image = new Image(source, HOME_CARD_WIDTH, HOME_IMAGE_HEIGHT, false, true, false);
ImageView imageView = new ImageView(image);
                imageView.setFitWidth(HOME_CARD_WIDTH);
                imageView.setFitHeight(HOME_IMAGE_HEIGHT);
                imageView.setPreserveRatio(false);
                javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(HOME_CARD_WIDTH, HOME_IMAGE_HEIGHT);
                clip.setArcWidth(14);
                clip.setArcHeight(14);
                imageView.setClip(clip);
                imageView.getStyleClass().add("home-preview-image");
                media.getChildren().add(imageView);
                return media;
            } catch (Exception ignored) {
            }
        }

        Label fallback = new Label(fallbackText);
        fallback.getStyleClass().add("home-preview-fallback");
        media.getChildren().add(fallback);
        return media;
    }

    private Label createPreviewTitle(String value) {
        Label label = new Label(truncate(safeText(value, "Untitled"), 44));
        label.getStyleClass().add("home-mini-title");
        label.setWrapText(true);
        label.setMaxWidth(HOME_CARD_WIDTH - 28);
        return label;
    }

    private Label createPreviewCopy(String value, int maxLength) {
        Label label = new Label(truncate(safeText(value, "A Pegasus preview item."), maxLength));
        label.getStyleClass().add("home-mini-copy");
        label.setWrapText(true);
        label.setMaxWidth(HOME_CARD_WIDTH - 28);
        return label;
    }

    private Label createPreviewMeta(String value) {
        Label label = new Label(truncate(safeText(value, "Preview"), 32));
        label.getStyleClass().add("home-mini-meta");
        label.setMaxWidth(HOME_CARD_WIDTH - 28);
        return label;
    }

    private Label createPill(String value, String styleClass) {
        Label label = new Label(truncate(safeText(value, "Pegasus"), 24));
        label.getStyleClass().addAll("home-mini-pill", styleClass);
        label.setMaxWidth(HOME_CARD_WIDTH - 28);
        return label;
    }

    private void configurePreviewCard(VBox card, Runnable action) {
        card.setCursor(Cursor.HAND);
        card.setOnMouseClicked(event -> action.run());
        installCardHover(card);
    }

    private void showPreviewMessage(FlowPane container, String title, String copy) {
        VBox card = createBasePreviewCard();
        card.getStyleClass().add("home-empty-preview-card");
        Label titleLabel = createPreviewTitle(title);
        Label copyLabel = createPreviewCopy(copy, 96);
        card.getChildren().addAll(titleLabel, copyLabel);
        container.getChildren().add(card);
        installCardHover(card);
    }

    private boolean hasCoursePreviewImage(Course course) {
        return course != null && !normalizeSearch(course.getThumbnailUrl()).isEmpty();
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void setCounter(Label label, int value) {
        if (label != null) {
            label.setText(String.valueOf(value));
        }
    }

    private String resolvePreviewImageSource(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String trimmed = value.trim();
        String lower = trimmed.toLowerCase(Locale.ROOT);
        if (lower.startsWith("http://") || lower.startsWith("https://") || lower.startsWith("file:") || lower.startsWith("data:")) {
            return trimmed;
        }

        File directFile = new File(trimmed);
        if (directFile.isFile()) {
            return directFile.toURI().toString();
        }

        File resourceImage = new File("src/main/resources/images", trimmed);
        if (resourceImage.isFile()) {
            return resourceImage.toURI().toString();
        }

        String resourcePath = trimmed.replace("\\", "/");
        URL resource = getClass().getResource(resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath);
        if (resource != null) {
            return resource.toExternalForm();
        }

        resource = getClass().getResource("/images/" + resourcePath);
        return resource == null ? null : resource.toExternalForm();
    }

    private void playHomeAnimations() {
        animateIn(heroCard, 0, 20, 0.99);
        animateIn(heroTextBox, 110, 12, 1.0);
        animateIn(heroShowcase, 210, 18, 0.97);
        animateChildren(homeFeatureCards, 280);
        animateChildren(homeDetailCards, 380);
        animateChildren(homeCoursesContainer, 520);
        animateChildren(homeGalleryContainer, 620);
        animateChildren(homeProductsContainer, 720);
        if (!SceneNavigator.isAuthModalOpen()) {
            startHomeAmbientMotion();
        }
    }

    private void animateChildren(FlowPane container, int initialDelay) {
        if (container == null) {
            return;
        }
        int delay = initialDelay;
        for (Node child : container.getChildren()) {
            animateIn(child, delay, 18, 0.98);
            delay += 55;
        }
    }

    private void animateIn(Node node, int delayMillis, double fromY, double fromScale) {
        if (node == null) {
            return;
        }

        node.setOpacity(0);
        node.setTranslateY(fromY);
        node.setScaleX(fromScale);
        node.setScaleY(fromScale);

        FadeTransition fade = new FadeTransition(javafx.util.Duration.millis(520), node);
        fade.setDelay(javafx.util.Duration.millis(delayMillis));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition move = new TranslateTransition(javafx.util.Duration.millis(560), node);
        move.setDelay(javafx.util.Duration.millis(delayMillis));
        move.setFromY(fromY);
        move.setToY(0);
        move.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition scale = new ScaleTransition(javafx.util.Duration.millis(560), node);
        scale.setDelay(javafx.util.Duration.millis(delayMillis));
        scale.setFromX(fromScale);
        scale.setFromY(fromScale);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fade, move, scale).play();
    }

    private void startSubtleFloat(Node node) {
        if (node == null) {
            return;
        }
        if (heroFloatTimeline != null) {
            heroFloatTimeline.stop();
        }
        node.setTranslateY(0);
        heroFloatTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.ZERO,
                        new KeyValue(node.translateYProperty(), 0, Interpolator.EASE_BOTH)),
                new KeyFrame(javafx.util.Duration.seconds(3.4),
                        new KeyValue(node.translateYProperty(), -8, Interpolator.EASE_BOTH))
        );
        heroFloatTimeline.setAutoReverse(true);
        heroFloatTimeline.setCycleCount(Timeline.INDEFINITE);
        heroFloatTimeline.setDelay(javafx.util.Duration.millis(900));
        heroFloatTimeline.play();
    }

    private void startHomeAmbientMotion() {
        startSubtleFloat(heroShowcase);
    }

    private void stopHomeAmbientMotion() {
        if (heroFloatTimeline != null) {
            heroFloatTimeline.stop();
            heroFloatTimeline = null;
        }
        if (heroShowcase != null) {
            Timeline settle = new Timeline(
                    new KeyFrame(javafx.util.Duration.millis(160),
                            new KeyValue(heroShowcase.translateYProperty(), 0, Interpolator.EASE_OUT))
            );
            settle.play();
        }
    }

    private void installCardHover(Node node) {
        node.setOnMouseEntered(event -> animateCardScale(node, 1.025));
        node.setOnMouseExited(event -> animateCardScale(node, 1.0));
    }

    private void animateCardScale(Node node, double scale) {
        ScaleTransition transition = new ScaleTransition(javafx.util.Duration.millis(140), node);
        transition.setToX(scale);
        transition.setToY(scale);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();
    }

    private String safeText(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }

    private String normalizeSearch(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, Math.max(0, maxLength - 3)).trim() + "...";
    }

    public void onGoToSignIn() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.setSelectedRole("NORMAL_USER");
            SceneNavigator.goTo("/views/front/signup-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToProfile() {
        if (SceneNavigator.getCurrentUser() == null) {
            onGoToSignIn();
            return;
        }
        try {
            SceneNavigator.goTo("/views/front/profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToGallery() {
        if (!requireSignedIn()) {
            return;
        }
        try {
            SceneNavigator.goTo("/views/front/menu-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToCourses() {
        if (SceneNavigator.getCurrentUser() == null) {
            onGoToSignIn();
            return;
        }
        try {
            FrontLayoutController.showCoursesOnOpen();
            SceneNavigator.goTo("/views/front/FrontLayout.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToEvents() {
        if (!requireSignedIn()) {
            return;
        }
        try {
            SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToProduit() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            onGoToSignIn();
            return;
        }
        String target = "artiste".equalsIgnoreCase(currentUser.getDtype())
                ? "/views/back/DashboardArtiste.fxml"
                : "/views/front/DashboardUser.fxml";
        try {
            SceneNavigator.goTo(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToForum() {
        if (!requireSignedIn()) {
            return;
        }
        ForumModuleLauncher.openForumWindow();
    }

    public void onGoToCoursesDashboard() {
        if (SceneNavigator.getCurrentUser() == null) {
            onGoToSignIn();
            return;
        }
        try {
            FrontLayoutController.showDashboardOnOpen();
            SceneNavigator.goTo("/views/front/FrontLayout.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToBackoffice() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            onGoToSignIn();
            return;
        }
        if (!"admin".equalsIgnoreCase(currentUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Backoffice", "Only admins can open the backoffice.");
            return;
        }
        try {
            SceneNavigator.goTo("/views/back/AdminLayout.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogout() {
        SceneNavigator.logoutToFrontHome();
    }

    public void onGoToEditProfile() {
        try {
            SceneNavigator.goTo("/views/front/profile-edit-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToRoleRequest() {
        try {
            SceneNavigator.goTo("/views/front/role-request-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open role request page.");
        }
    }

    public void onGoToRoleRequestHistory() {
        try {
            SceneNavigator.goTo("/views/front/role-request-history-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open role request history page.");
        }
    }

    private boolean requireSignedIn() {
        if (SceneNavigator.getCurrentUser() != null) {
            return true;
        }
        onGoToSignIn();
        return false;
    }

    private void refreshUserState() {
        User currentUser = SceneNavigator.getCurrentUser();
        boolean loggedIn = currentUser != null;

        if (signInButton != null) {
            signInButton.setVisible(!loggedIn);
            signInButton.setManaged(!loggedIn);
        }
        if (signUpButton != null) {
            signUpButton.setVisible(!loggedIn);
            signUpButton.setManaged(!loggedIn);
        }

        if (logoutButton != null) {
            logoutButton.setVisible(loggedIn);
            logoutButton.setManaged(loggedIn);
        }
        if (editProfileButton != null) {
            editProfileButton.setVisible(loggedIn);
            editProfileButton.setManaged(loggedIn);
        }
        if (roleRequestButton != null) {
            boolean canRequestRole = loggedIn && "normal_user".equalsIgnoreCase(currentUser.getDtype());
            roleRequestButton.setVisible(canRequestRole);
            roleRequestButton.setManaged(canRequestRole);
        }
        if (roleRequestHistoryButton != null) {
            boolean canViewHistory = loggedIn;
            roleRequestHistoryButton.setVisible(canViewHistory);
            roleRequestHistoryButton.setManaged(canViewHistory);
        }
        if (navProfileButton != null) {
            navProfileButton.setVisible(false);
            navProfileButton.setManaged(false);
        }
        if (navAccountMenu != null) {
            navAccountMenu.setVisible(loggedIn);
            navAccountMenu.setManaged(loggedIn);
            navAccountMenu.setText("\uD83D\uDC64");
        }
        if (navBackofficeButton != null) {
            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
            navBackofficeButton.setVisible(isAdmin);
            navBackofficeButton.setManaged(isAdmin);
        }
        if (navCoursesDashboardButton != null) {
            boolean isArtist = isArtist(currentUser);
            navCoursesDashboardButton.setVisible(isArtist);
            navCoursesDashboardButton.setManaged(isArtist);
        }

        if (loggedIn) {
            if (userStatusLabel != null) {
                userStatusLabel.setText("Connected as: " + currentUser.getUsername());
            }
            if (profileBox != null) {
                profileBox.setVisible(true);
                profileBox.setManaged(true);
            }
            updateProfileImage(currentUser);
            if (navAuthButton != null) {
                navAuthButton.setVisible(false);
                navAuthButton.setManaged(false);
            }

            boolean isAdmin = "admin".equalsIgnoreCase(currentUser.getDtype());
            if (adminUsersBox != null) {
                adminUsersBox.setVisible(false);
                adminUsersBox.setManaged(false);
            }
            if (applyStatusButton != null) {
                applyStatusButton.setDisable(true);
            }
        } else {
            if (userStatusLabel != null) {
                userStatusLabel.setText("You are not connected.");
            }
            clearProfileImage();
            if (profileBox != null) {
                profileBox.setVisible(false);
                profileBox.setManaged(false);
            }
            if (expandProfileButton != null) {
                expandProfileButton.setVisible(false);
                expandProfileButton.setManaged(false);
            }
            if (navAuthButton != null) {
                navAuthButton.setVisible(true);
                navAuthButton.setManaged(true);
                navAuthButton.setText("Sign In");
                navAuthButton.setOnAction(event -> onGoToSignIn());
            }
            if (adminUsersBox != null) {
                adminUsersBox.setVisible(false);
                adminUsersBox.setManaged(false);
            }
            if (usersTable != null) {
                usersTable.getItems().clear();
            }
            if (applyStatusButton != null) {
                applyStatusButton.setDisable(true);
            }
        }
    }

    private void loadUsersTable() {
        if (!initService()) {
            return;
        }
        List<User> users = serviceUser.findAllUsers();
        allUsers.setAll(users);
        applySearchAndSort();
    }

    @FXML
    public void onSearchUsers() {
        applySearchAndSort();
    }

    @FXML
    public void onSortUsers() {
        applySearchAndSort();
    }

    @FXML
    public void onResetUsersFilter() {
        searchField.clear();
        sortByCombo.getSelectionModel().select("Username");
        sortOrderCombo.getSelectionModel().select("Ascending");
        applySearchAndSort();
    }

    @FXML
    public void onChangeSelectedUserStatus() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Status Update", "Only admins can change user status.");
            return;
        }
        if (!initService()) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to database.");
            return;
        }

        User selectedUser = usersTable == null ? null : usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Status Update", "Select a user first.");
            return;
        }
        if ("admin".equalsIgnoreCase(selectedUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Status Update", "You cannot change the status of another admin.");
            return;
        }

        String newStatus = statusUpdateCombo == null ? null : statusUpdateCombo.getValue();
        if (newStatus == null || newStatus.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Status Update", "Select a valid status.");
            return;
        }
        if (newStatus.equalsIgnoreCase(selectedUser.getStatus())) {
            showAlert(Alert.AlertType.INFORMATION, "Status Update", "The selected user already has this status.");
            return;
        }

        selectedUser.setStatus(newStatus);
        serviceUser.modifier(selectedUser);
        if (serviceUser.getLastError() != null) {
            showAlert(Alert.AlertType.ERROR, "Status Update", serviceUser.getLastError());
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Status Update", "User status updated successfully.");
        loadUsersTable();
    }

    @FXML
    public void onExportUsersPdf() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Export PDF", "Only admins can export users.");
            return;
        }
        if (usersTable == null || usersTable.getItems().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Export PDF", "No users to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Users Table as PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("pegasus-users.pdf");
        Window owner = usersTable.getScene() == null ? null : usersTable.getScene().getWindow();
        File destination = fileChooser.showSaveDialog(owner);
        if (destination == null) {
            return;
        }

        try {
            exportUsersToPdf(destination, new ArrayList<>(usersTable.getItems()));
            showAlert(Alert.AlertType.INFORMATION, "Export PDF", "Users table exported successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Export PDF", "Could not export PDF: " + e.getMessage());
        }
    }

    @FXML
    public void onVoiceSearchUsers() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Voice Search", "Only admins can use voice search.");
            return;
        }
        if (voiceSearchButton != null) {
            voiceSearchButton.setDisable(true);
            voiceSearchButton.setText("Listening...");
        }

        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                if (voiceSearchService == null) {
                    voiceSearchService = new VoiceSearchService();
                }
                return voiceSearchService.recognizeOnce();
            }
        };

        task.setOnSucceeded(event -> {
            String text = task.getValue();
            if (voiceSearchButton != null) {
                voiceSearchButton.setDisable(false);
                voiceSearchButton.setText("Voice Search");
            }
            if (text == null || text.isBlank()) {
                showAlert(Alert.AlertType.INFORMATION, "Voice Search", "No speech recognized. Please try again.");
                return;
            }
            if (searchField != null) {
                searchField.setText(text);
            }
            applySearchAndSort();
        });

        task.setOnFailed(event -> {
            if (voiceSearchButton != null) {
                voiceSearchButton.setDisable(false);
                voiceSearchButton.setText("Voice Search");
            }
            Throwable error = task.getException();
            String message = error == null ? "Voice recognition failed." : error.getMessage();
            showAlert(Alert.AlertType.ERROR, "Voice Search", message);
        });

        Thread thread = new Thread(task, "voice-search-task");
        thread.setDaemon(true);
        thread.start();
    }

    private void applySearchAndSort() {
        if (usersTable == null) {
            return;
        }
        String q = (searchField == null || searchField.getText() == null)
                ? ""
                : searchField.getText().trim().toLowerCase(Locale.ROOT);
        List<User> sorted = allUsers.stream()
                .filter(user -> matchesQuery(user, q))
                .sorted(buildComparator())
                .toList();
        usersTable.setItems(FXCollections.observableArrayList(sorted));
    }

    private boolean matchesQuery(User user, String q) {
        if (q.isEmpty()) {
            return true;
        }
        return safe(user.getId()).contains(q)
                || safe(user.getUsername()).contains(q)
                || safe(user.getEmail()).contains(q)
                || safe(user.getDtype()).contains(q)
                || safe(user.getStatus()).contains(q);
    }

    private Comparator<User> buildComparator() {
        String sortBy = sortByCombo == null ? "Username" : sortByCombo.getValue();
        String order = sortOrderCombo == null ? "Ascending" : sortOrderCombo.getValue();

        Comparator<User> comparator;
        if ("Username".equals(sortBy)) {
            comparator = Comparator.comparing(user -> safe(user.getUsername()), String::compareToIgnoreCase);
        } else if ("Email".equals(sortBy)) {
            comparator = Comparator.comparing(user -> safe(user.getEmail()), String::compareToIgnoreCase);
        } else if ("Role".equals(sortBy)) {
            comparator = Comparator.comparing(user -> safe(user.getDtype()), String::compareToIgnoreCase);
        } else if ("Status".equals(sortBy)) {
            comparator = Comparator.comparing(user -> safe(user.getStatus()), String::compareToIgnoreCase);
        } else {
            comparator = Comparator.comparing(user -> safe(user.getUsername()), String::compareToIgnoreCase);
        }

        if ("Descending".equals(order)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private String safe(Object value) {
        return value == null ? "" : value.toString().toLowerCase(Locale.ROOT);
    }

    private boolean initService() {
        try {
            if (serviceUser == null) {
                serviceUser = new ServiceUser();
            }
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateProfileImage(User user) {
        if (user == null || profileImageView == null || profileImageHintLabel == null) {
            return;
        }
        String avatarSource = resolveAvatarSource(user);
        if (avatarSource == null) {
            clearProfileImage();
            return;
        }

        try {
            Image image = new Image(avatarSource, true);
            if (image.isError()) {
                clearProfileImage();
                return;
            }
            profileImageView.setImage(image);
            profileImageView.setVisible(true);
            profileImageView.setManaged(true);
            if (expandProfileButton != null) {
                expandProfileButton.setVisible(true);
                expandProfileButton.setManaged(true);
            }
            profileImageHintLabel.setText("Profile picture (click to change)");
        } catch (Exception e) {
            clearProfileImage();
        }
    }

    private String resolveAvatarSource(User user) {
        String avatarUrl = user.getAvatarUrl();
        if (avatarUrl != null && !avatarUrl.trim().isEmpty()) {
            String trimmed = avatarUrl.trim();
            if (trimmed.startsWith("http://") || trimmed.startsWith("https://") || trimmed.startsWith("file:/")) {
                return trimmed;
            }

            File directFile = new File(trimmed);
            if (directFile.exists() && directFile.isFile()) {
                return directFile.toURI().toString();
            }

            String fromPublicRoot = resolveFromPublicRoot(trimmed);
            if (fromPublicRoot != null) {
                return fromPublicRoot;
            }

            String fromKnownFolders = findInKnownFolders(trimmed);
            if (fromKnownFolders != null) {
                return fromKnownFolders;
            }
        }

        String byId = user.getId() == null ? null : findInKnownFolders(String.valueOf(user.getId()));
        if (byId != null) {
            return byId;
        }
        String byUsername = findInKnownFolders(user.getUsername());
        if (byUsername != null) {
            return byUsername;
        }
        String byEmail = findInKnownFolders(user.getEmail());
        if (byEmail != null) {
            return byEmail;
        }
        return findRandomDefaultAvatar(user);
    }

    private String findInKnownFolders(String baseName) {
        if (baseName == null || baseName.trim().isEmpty()) {
            return null;
        }
        String clean = sanitizeFileBase(baseName.trim());
        if (clean.isEmpty()) {
            return null;
        }

        String[] roots = {PROFILE_PICS_DIR_1, PROFILE_PICS_DIR_2};
        for (String root : roots) {
            File asIs = new File(root, clean);
            if (asIs.exists() && asIs.isFile()) {
                return asIs.toURI().toString();
            }
            for (String ext : IMAGE_EXTENSIONS) {
                File withExt = new File(root, clean + ext);
                if (withExt.exists() && withExt.isFile()) {
                    return withExt.toURI().toString();
                }
            }
        }
        return null;
    }

    private String sanitizeFileBase(String input) {
        String noExt = input;
        int dot = noExt.lastIndexOf('.');
        if (dot > 0) {
            noExt = noExt.substring(0, dot);
        }
        return noExt.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private String resolveFromPublicRoot(String avatarPath) {
        if (avatarPath == null || avatarPath.isBlank()) {
            return null;
        }
        String normalized = avatarPath.replace("/", File.separator).replace("\\", File.separator);
        File file = new File(PUBLIC_ROOT_DIR, normalized);
        if (file.exists() && file.isFile()) {
            return file.toURI().toString();
        }
        return null;
    }

    private String findRandomDefaultAvatar(User user) {
        File dir = new File(PROFILE_DEFAULT_DIR);
        File[] files = dir.listFiles(file -> file.isFile() && hasImageExtension(file.getName()));
        if (files == null || files.length == 0) {
            return null;
        }
        int seed = stableUserSeed(user);
        int index = Math.floorMod(seed, files.length);
        return files[index].toURI().toString();
    }

    private int stableUserSeed(User user) {
        if (user == null) {
            return 0;
        }
        if (user.getId() != null) {
            return user.getId();
        }
        String basis = user.getEmail() != null ? user.getEmail() : user.getUsername();
        return basis == null ? 0 : basis.toLowerCase(Locale.ROOT).hashCode();
    }

    private boolean hasImageExtension(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lower = fileName.toLowerCase(Locale.ROOT);
        for (String ext : IMAGE_EXTENSIONS) {
            if (lower.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private void clearProfileImage() {
        if (profileImageView != null) {
            profileImageView.setImage(null);
            profileImageView.setVisible(false);
            profileImageView.setManaged(false);
        }
        if (expandProfileButton != null) {
            expandProfileButton.setVisible(false);
            expandProfileButton.setManaged(false);
        }
        if (profileImageHintLabel != null) {
            profileImageHintLabel.setText("No profile picture (click to choose one)");
        }
    }

    public void onExpandProfileImage() {
        if (profileImageView == null || profileImageView.getImage() == null) {
            showAlert(Alert.AlertType.INFORMATION, "Profile Picture", "No profile image to preview.");
            return;
        }

        ImageView largeView = new ImageView(profileImageView.getImage());
        largeView.setPreserveRatio(true);
        largeView.setFitWidth(560);
        largeView.setFitHeight(560);

        StackPane root = new StackPane(largeView);
        root.setStyle("-fx-padding: 16; -fx-background-color: #111827;");

        Stage stage = new Stage();
        stage.setTitle("Profile Picture");
        stage.initModality(Modality.APPLICATION_MODAL);
        if (profileImageView.getScene() != null && profileImageView.getScene().getWindow() != null) {
            stage.initOwner(profileImageView.getScene().getWindow());
        }
        stage.setScene(new Scene(root, 600, 600));
        stage.showAndWait();
    }

    private void onProfileImageClicked() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.INFORMATION, "Profile Picture", "Sign in first to change your profile picture.");
            return;
        }
        if (!initService()) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to database.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Profile Picture");
        dialog.setHeaderText("Change profile picture");
        ButtonType uploadButton = new ButtonType("Upload from file");
        ButtonType defaultButton = new ButtonType("Choose default avatar");
        ButtonType stylizeButton = new ButtonType("Stylize with Cloudflare");
        dialog.getDialogPane().getButtonTypes().addAll(uploadButton, defaultButton, stylizeButton, ButtonType.CANCEL);
        styleDialog(dialog);
        styleDialogButton(dialog, uploadButton, "gold-button");
        styleDialogButton(dialog, defaultButton, "secondary-button");
        styleDialogButton(dialog, stylizeButton, "secondary-button");
        styleDialogButton(dialog, ButtonType.CANCEL, "ghost-button");
        Optional<ButtonType> choice = dialog.showAndWait();
        if (choice.isEmpty() || choice.get() == ButtonType.CANCEL) {
            return;
        }

        if (choice.get() == uploadButton) {
            uploadProfilePicture(currentUser);
        } else if (choice.get() == stylizeButton) {
            stylizeProfilePicture(currentUser);
        } else {
            chooseDefaultAvatar(currentUser);
        }
    }

    private void uploadProfilePicture(User currentUser) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose profile picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Images", "*.png", "*.jpg", "*.jpeg", "*.webp"
        ));
        Window owner = profileImageView == null || profileImageView.getScene() == null ? null : profileImageView.getScene().getWindow();
        File selected = fileChooser.showOpenDialog(owner);
        if (selected == null) {
            return;
        }

        try {
            if (cloudinaryService == null) {
                cloudinaryService = new CloudinaryService();
            }
            String uploadedUrl = cloudinaryService.uploadProfileImage(selected, currentUser.getId());
            currentUser.setAvatarUrl(uploadedUrl);
            serviceUser.modifier(currentUser);
            if (serviceUser.getLastError() != null) {
                showAlert(Alert.AlertType.ERROR, "Profile Picture", serviceUser.getLastError());
                return;
            }
            updateProfileImage(currentUser);
            showAlert(Alert.AlertType.INFORMATION, "Profile Picture", "Profile picture uploaded to Cloudinary.");
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null || message.isBlank()) {
                message = "Could not upload profile picture.";
            }
            showAlert(Alert.AlertType.ERROR, "Profile Picture", message);
        }
    }

    private void chooseDefaultAvatar(User currentUser) {
        File dir = new File(PROFILE_DEFAULT_DIR);
        File[] files = dir.listFiles(file -> file.isFile() && hasImageExtension(file.getName()));
        if (files == null || files.length == 0) {
            showAlert(Alert.AlertType.ERROR, "Profile Picture", "No default avatars found.");
            return;
        }

        List<File> avatarFiles = new ArrayList<>(List.of(files));
        avatarFiles.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        Dialog<File> dialog = new Dialog<>();
        dialog.setTitle("Default Avatars");
        dialog.setHeaderText("Choose a default avatar");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        styleDialog(dialog);
        styleDialogButton(dialog, ButtonType.OK, "gold-button");
        styleDialogButton(dialog, ButtonType.CANCEL, "ghost-button");

        ListView<File> listView = new ListView<>(FXCollections.observableArrayList(avatarFiles));
        listView.setPrefHeight(320);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                ImageView imageView = new ImageView(new Image(item.toURI().toString(), 72, 72, true, true, true));
                HBox box = new HBox(10, imageView);
                setText(null);
                setGraphic(box);
            }
        });
        if (!avatarFiles.isEmpty()) {
            listView.getSelectionModel().select(0);
        }
        dialog.getDialogPane().setContent(listView);
        dialog.setResultConverter(button -> button == ButtonType.OK ? listView.getSelectionModel().getSelectedItem() : null);

        Optional<File> selectedFile = dialog.showAndWait();
        if (selectedFile.isEmpty()) {
            return;
        }

        currentUser.setAvatarUrl("profileCom/" + selectedFile.get().getName());
        serviceUser.modifier(currentUser);
        if (serviceUser.getLastError() != null) {
            showAlert(Alert.AlertType.ERROR, "Profile Picture", serviceUser.getLastError());
            return;
        }
        updateProfileImage(currentUser);
        showAlert(Alert.AlertType.INFORMATION, "Profile Picture", "Default avatar selected.");
    }

    private void stylizeProfilePicture(User currentUser) {
        if (currentUser.getAvatarUrl() == null || currentUser.getAvatarUrl().isBlank()) {
            showAlert(Alert.AlertType.INFORMATION, "Stylize Avatar", "Please upload or choose a profile picture first.");
            return;
        }

        Dialog<ButtonType> styleDialog = new Dialog<>();
        styleDialog.setTitle("Stylize Avatar");
        styleDialog.setHeaderText("Choose style");
        ButtonType animeButton = new ButtonType("anime");
        ButtonType comicButton = new ButtonType("comic");
        ButtonType pixarButton = new ButtonType("pixar");
        styleDialog.getDialogPane().getButtonTypes().addAll(animeButton, comicButton, pixarButton, ButtonType.CANCEL);
        styleDialog(styleDialog);
        styleDialogButton(styleDialog, animeButton, "gold-button");
        styleDialogButton(styleDialog, comicButton, "secondary-button");
        styleDialogButton(styleDialog, pixarButton, "secondary-button");
        styleDialogButton(styleDialog, ButtonType.CANCEL, "ghost-button");
        Optional<ButtonType> selectedStyleButton = styleDialog.showAndWait();
        if (selectedStyleButton.isEmpty() || selectedStyleButton.get() == ButtonType.CANCEL) {
            return;
        }

        String style = selectedStyleButton.get() == animeButton
                ? "anime"
                : selectedStyleButton.get() == comicButton ? "comic" : "pixar";

        String source = resolveAvatarSource(currentUser);
        if (source == null) {
            showAlert(Alert.AlertType.ERROR, "Stylize Avatar", "Current profile image could not be found.");
            return;
        }

        String accountId = readCloudflareConfig("cloudflare.accountId", "CLOUDFLARE_ACCOUNT_ID");
        String apiToken = readCloudflareConfig("cloudflare.apiToken", "CLOUDFLARE_API_TOKEN");
        if (accountId == null || apiToken == null) {
            showAlert(
                    Alert.AlertType.ERROR,
                    "Cloudflare Config",
                    "Set cloudflare.accountId and cloudflare.apiToken in cloudflare.properties (or environment variables)."
            );
            return;
        }

        try {
            byte[] inputBytes = readImageBytes(source);
            String imageB64 = Base64.getEncoder().encodeToString(inputBytes);
            String prompt = switch (style) {
                case "anime" -> "Convert this profile portrait to clean anime style, preserve face identity, centered headshot, high quality.";
                case "comic" -> "Convert this profile portrait to bold comic-book style, preserve face identity, centered headshot, high quality.";
                default -> "Convert this profile portrait to pixar-style 3D character art, preserve face identity, centered headshot, high quality.";
            };

            String requestBody = "{"
                    + "\"prompt\":\"" + escapeJson(prompt) + "\","
                    + "\"image_b64\":\"" + imageB64 + "\","
                    + "\"num_steps\":20,"
                    + "\"strength\":0.78,"
                    + "\"guidance\":7.5"
                    + "}";

            String endpoint = "https://api.cloudflare.com/client/v4/accounts/" + accountId + "/ai/run/" + CF_MODEL;
            HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint))
                    .timeout(CF_TIMEOUT)
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<byte[]> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                String body = new String(response.body());
                if (body.isBlank()) {
                    body = "";
                }
                if (body.length() > 220) {
                    body = body.substring(0, 220) + "...";
                }
                showAlert(
                        Alert.AlertType.ERROR,
                        "Stylize Avatar",
                        "Cloudflare request failed: HTTP " + response.statusCode() + "\n" + body
                );
                return;
            }

            byte[] outputBytes;
            String contentType = response.headers().firstValue("content-type").orElse("");
            if (contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
                outputBytes = response.body();
            } else {
                String resultImageB64 = extractImageFromCloudflareResponse(new String(response.body()));
                if (resultImageB64 == null || resultImageB64.isBlank()) {
                    showAlert(Alert.AlertType.ERROR, "Stylize Avatar", "Cloudflare did not return an image.");
                    return;
                }
                outputBytes = Base64.getDecoder().decode(resultImageB64);
            }
            Path targetDir = Path.of(PROFILE_PICS_DIR_1);
            Files.createDirectories(targetDir);
            String filename = "user_" + (currentUser.getId() == null ? "x" : currentUser.getId()) + "_" + style + "_" +
                    UUID.randomUUID().toString().replace("-", "").substring(0, 10) + ".jpg";
            Path targetPath = targetDir.resolve(filename);
            Files.write(targetPath, outputBytes);

            currentUser.setAvatarUrl("profileStylized/" + filename);
            serviceUser.modifier(currentUser);
            if (serviceUser.getLastError() != null) {
                showAlert(Alert.AlertType.ERROR, "Stylize Avatar", serviceUser.getLastError());
                return;
            }

            updateProfileImage(currentUser);
            showAlert(Alert.AlertType.INFORMATION, "Stylize Avatar", "Stylized avatar created in " + style + " style.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Stylize Avatar", "Could not stylize profile picture.");
        }
    }

    private byte[] readImageBytes(String source) throws IOException {
        if (source.startsWith("file:/")) {
            return Files.readAllBytes(Path.of(URI.create(source)));
        }
        if (source.startsWith("http://") || source.startsWith("https://")) {
            try (InputStream inputStream = new URL(source).openStream()) {
                return inputStream.readAllBytes();
            }
        }
        return Files.readAllBytes(Path.of(source));
    }

    private String extractImageFromCloudflareResponse(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            return null;
        }
        try {
            JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonElement directImage = root.get("image");
            if (directImage != null && !directImage.isJsonNull()) {
                return directImage.getAsString();
            }
            JsonObject result = root.getAsJsonObject("result");
            if (result != null) {
                JsonElement nestedImage = result.get("image");
                if (nestedImage != null && !nestedImage.isJsonNull()) {
                    return nestedImage.getAsString();
                }
            }
        } catch (Exception ignored) {
            // Fallback for non-standard bodies.
        }

        int imageKeyIdx = responseBody.indexOf("\"image\"");
        if (imageKeyIdx < 0) {
            return null;
        }
        int colon = responseBody.indexOf(':', imageKeyIdx);
        if (colon < 0) {
            return null;
        }
        int startQuote = responseBody.indexOf('"', colon + 1);
        if (startQuote < 0) {
            return null;
        }
        int endQuote = responseBody.indexOf('"', startQuote + 1);
        if (endQuote < 0) {
            return null;
        }
        return responseBody.substring(startQuote + 1, endQuote);
    }

    private String env(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String readCloudflareConfig(String propertyKey, String envKey) {
        String value = readProperty(propertyKey);
        if (value != null && !value.startsWith("YOUR_")) {
            return value;
        }
        return env(envKey);
    }

    private String readProperty(String propertyKey) {
        try {
            Properties properties = PropertiesLoader.load(CLOUDFLARE_CONFIG_PATH, HomeController.class);
            String value = properties.getProperty(propertyKey);
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        } catch (Exception e) {
            return null;
        }
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void styleDialog(Dialog<?> dialog) {
        if (dialog == null || dialog.getDialogPane() == null) {
            return;
        }
        String theme = HomeController.class.getResource("/styles/theme.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(theme);
        dialog.getDialogPane().getStyleClass().add("profile-dialog");
    }

    private void styleDialogButton(Dialog<?> dialog, ButtonType buttonType, String styleClass) {
        if (dialog == null || dialog.getDialogPane() == null || buttonType == null) {
            return;
        }
        Node node = dialog.getDialogPane().lookupButton(buttonType);
        if (node instanceof Button button) {
            button.getStyleClass().add(styleClass);
            button.setPrefWidth(180);
        }
    }

    private void exportUsersToPdf(File destination, List<User> users) throws IOException, DocumentException {
        try (FileOutputStream outputStream = new FileOutputStream(destination)) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph title = new Paragraph("Pegasus - Users Export", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph meta = new Paragraph(
                    "Generated: " + new Date() + " | Rows: " + users.size(),
                    FontFactory.getFont(FontFactory.HELVETICA, 10)
            );
            meta.setAlignment(Element.ALIGN_CENTER);
            document.add(meta);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.0f, 3.2f, 1.6f, 1.4f});
            addHeaderCell(table, "Username");
            addHeaderCell(table, "Email");
            addHeaderCell(table, "Role");
            addHeaderCell(table, "Status");

            for (User user : users) {
                table.addCell(safeExport(user.getUsername()));
                table.addCell(safeExport(user.getEmail()));
                table.addCell(safeExport(user.getDtype()));
                table.addCell(safeExport(user.getStatus()));
            }
            document.add(table);
            document.close();
        }
    }

    private void addHeaderCell(PdfPTable table, String title) {
        PdfPCell header = new PdfPCell(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBackgroundColor(new java.awt.Color(232, 238, 248));
        header.setBorder(Rectangle.BOX);
        table.addCell(header);
    }

    private String safeExport(String value) {
        return value == null ? "" : value;
    }

    private boolean isArtist(User user) {
        return user != null && "artiste".equalsIgnoreCase(user.getDtype());
    }
}
