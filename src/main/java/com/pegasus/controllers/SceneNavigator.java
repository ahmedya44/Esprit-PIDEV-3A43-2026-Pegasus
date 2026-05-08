package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.GoogleUserProfile;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.CacheHint;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SceneNavigator {
    private static Stage stage;
    private static String selectedRole;
    private static User currentUser;
    private static GoogleUserProfile pendingGoogleUserProfile;
    private static final BooleanProperty authModalOpen = new SimpleBooleanProperty(false);
    private static final Set<String> AUTH_VIEWS = Set.of(
            "/views/front/signin-view.fxml",
            "/views/front/role-selection-view.fxml",
            "/views/front/signup-view.fxml",
            "/views/front/email-verification-view.fxml",
            "/views/front/reset-password-view.fxml"
    );

    private SceneNavigator() {
    }

    public static void init(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Pegasus");
    }

    public static void goTo(String fxmlPath) throws IOException {
        if (stage == null) {
            throw new IllegalStateException("SceneNavigator is not initialized.");
        }
        URL fxmlUrl = SceneNavigator.class.getResource(fxmlPath);
        if (fxmlUrl == null) {
            throw new IOException("FXML not found: " + fxmlPath);
        }
        if (AUTH_VIEWS.contains(fxmlPath) && showAuthOverlay(fxmlUrl, fxmlPath)) {
            return;
        }

        Parent root;
        try {
            root = FXMLLoader.load(fxmlUrl);
        } catch (Exception e) {
            Throwable rootCause = rootCauseOf(e);
            String message = "Failed to load " + fxmlPath
                    + "\nCause: " + rootCause.getClass().getSimpleName()
                    + " - " + (rootCause.getMessage() == null ? "(no details)" : rootCause.getMessage());
            throw new IOException(message, e);
        }

        applyButtonAnimations(root);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.getStyleClass().add("app-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(scrollPane, 800, 520);
            scene.getStylesheets().add(SceneNavigator.class.getResource("/styles/theme.css").toExternalForm());
            scene.getStylesheets().add(SceneNavigator.class.getResource("/css/courses.css").toExternalForm());
            stage.setScene(scene);
        } else {
            scene.setRoot(scrollPane);
        }
        authModalOpen.set(false);
        stage.show();
    }

    public static void showWelcomeBack(User user) {
        String username = user == null || user.getUsername() == null || user.getUsername().isBlank()
                ? "Pegasus"
                : user.getUsername().trim();
        showToast("Welcome back, " + username, "You are connected to Pegasus.");
    }

    public static void showToast(String title, String message) {
        if (stage == null) {
            return;
        }

        Platform.runLater(() -> {
            if (stage.getScene() == null || !stage.isShowing()) {
                return;
            }

            Popup popup = new Popup();
            popup.setAutoFix(true);
            popup.setAutoHide(true);

            Label bar = new Label();
            bar.setMinWidth(5);
            bar.setPrefWidth(5);
            bar.setMaxWidth(5);
            bar.setMinHeight(58);
            bar.setStyle("-fx-background-color: #22c55e; -fx-background-radius: 999;");

            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: 900;");

            Label messageLabel = new Label(message);
            messageLabel.setWrapText(true);
            messageLabel.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 12px; -fx-font-weight: 600;");

            VBox copy = new VBox(4, titleLabel, messageLabel);
            copy.setAlignment(Pos.CENTER_LEFT);

            HBox toast = new HBox(14, bar, copy);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.setPrefWidth(360);
            toast.setMinHeight(86);
            toast.setStyle(
                    "-fx-background-color: linear-gradient(to right, #111827, #1f2937);" +
                    "-fx-background-radius: 16;" +
                    "-fx-padding: 14 18 14 14;" +
                    "-fx-border-color: rgba(255,255,255,0.08);" +
                    "-fx-border-radius: 16;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.34), 24, 0, 0, 10);"
            );

            popup.getContent().add(toast);
            toast.setOpacity(0);
            toast.setTranslateY(-10);

            double x = stage.getX() + Math.max(24, stage.getWidth() - 396);
            double y = stage.getY() + 86;
            popup.show(stage, x, y);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(180), toast);
            fadeIn.setToValue(1);
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(180), toast);
            slideIn.setToY(0);
            new ParallelTransition(fadeIn, slideIn).play();

            PauseTransition stay = new PauseTransition(Duration.seconds(3.2));
            stay.setOnFinished(event -> {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(190), toast);
                fadeOut.setToValue(0);
                TranslateTransition slideOut = new TranslateTransition(Duration.millis(190), toast);
                slideOut.setToY(-8);
                ParallelTransition exit = new ParallelTransition(fadeOut, slideOut);
                exit.setOnFinished(done -> popup.hide());
                exit.play();
            });
            stay.play();
        });
    }

    private static boolean showAuthOverlay(URL fxmlUrl, String fxmlPath) throws IOException {
        Scene scene = stage.getScene();
        if (scene == null || scene.getRoot() == null) {
            return false;
        }
        if (scene.getRoot().lookup(".signin-root") != null && scene.getRoot().lookup(".signin-modal-overlay") == null) {
            return false;
        }
        if (scene.getRoot().lookup("#authPanelFrame") instanceof StackPane existingFrame) {
            switchAuthPanel(existingFrame, fxmlUrl, fxmlPath);
            return true;
        }

        Parent currentRoot = scene.getRoot();

        Parent authRoot;
        try {
            authRoot = FXMLLoader.load(fxmlUrl);
        } catch (Exception e) {
            Throwable rootCause = rootCauseOf(e);
            String message = "Failed to load " + fxmlPath
                    + "\nCause: " + rootCause.getClass().getSimpleName()
                    + " - " + (rootCause.getMessage() == null ? "(no details)" : rootCause.getMessage());
            throw new IOException(message, e);
        }

        Node authPanel = extractAuthPanel(authRoot);
        if (authPanel == null) {
            return false;
        }
        detachFromParent(authPanel);
        prepareAuthPanel(authPanel, fxmlPath);

        currentRoot.setCache(true);
        currentRoot.setCacheHint(CacheHint.SPEED);
        currentRoot.setEffect(new GaussianBlur(9));
        Button closeButton = new Button("X");
        closeButton.setId("authModalCloseButton");
        closeButton.getStyleClass().add("signin-modal-close-button");
        StackPane panelFrame = new StackPane(wrapAuthPanel(authPanel, fxmlPath), closeButton);
        panelFrame.setId("authPanelFrame");
        panelFrame.getStyleClass().add("auth-panel-frame");
        panelFrame.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(closeButton, javafx.geometry.Pos.TOP_RIGHT);
        StackPane.setMargin(closeButton, new javafx.geometry.Insets(14));
        panelFrame.setPickOnBounds(false);

        StackPane overlay = new StackPane(panelFrame);
        overlay.setId("authModalOverlay");
        overlay.getStyleClass().add("signin-modal-overlay");
        overlay.setPickOnBounds(true);

        StackPane modalRoot = new StackPane(currentRoot, overlay);
        modalRoot.getStyleClass().add("signin-modal-shell");
        modalRoot.setMinSize(0, 0);
        modalRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        overlay.prefWidthProperty().bind(scene.widthProperty());
        overlay.prefHeightProperty().bind(scene.heightProperty());
        AtomicBoolean closing = new AtomicBoolean(false);
        Runnable closeOverlay = () -> {
            if (!closing.compareAndSet(false, true)) {
                return;
            }
            ParallelTransition exit = buildSignInModalExit(overlay, panelFrame);
            exit.setOnFinished(event -> {
                currentRoot.setEffect(null);
                currentRoot.setCache(false);
                modalRoot.getChildren().remove(currentRoot);
                scene.setRoot(currentRoot);
                authModalOpen.set(false);
            });
            exit.play();
        };
        closeButton.setOnAction(event -> closeOverlay.run());
        overlay.setOnMouseClicked(event -> {
            if (event.getTarget() == overlay) {
                closeOverlay.run();
            }
        });
        panelFrame.setOnMouseClicked(event -> event.consume());
        if (authPanel instanceof Parent panelParent) {
            applyButtonAnimations(panelParent);
        }
        scene.setRoot(modalRoot);
        authModalOpen.set(true);
        stage.show();
        buildSignInModalEntrance(overlay, panelFrame).play();
        return true;
    }

    private static void switchAuthPanel(StackPane panelFrame, URL fxmlUrl, String fxmlPath) throws IOException {
        Parent authRoot;
        try {
            authRoot = FXMLLoader.load(fxmlUrl);
        } catch (Exception e) {
            Throwable rootCause = rootCauseOf(e);
            String message = "Failed to load " + fxmlPath
                    + "\nCause: " + rootCause.getClass().getSimpleName()
                    + " - " + (rootCause.getMessage() == null ? "(no details)" : rootCause.getMessage());
            throw new IOException(message, e);
        }

        Node nextPanel = extractAuthPanel(authRoot);
        if (nextPanel == null) {
            return;
        }
        detachFromParent(nextPanel);
        prepareAuthPanel(nextPanel, fxmlPath);
        if (nextPanel instanceof Parent panelParent) {
            applyButtonAnimations(panelParent);
        }

        Node closeButton = panelFrame.lookup("#authModalCloseButton");
        Node currentPanel = panelFrame.getChildren().stream()
                .filter(node -> node != closeButton)
                .findFirst()
                .orElse(null);
        Node wrappedNextPanel = wrapAuthPanel(nextPanel, fxmlPath);
        boolean reverse = "/views/front/signin-view.fxml".equals(fxmlPath)
                || "/views/front/role-selection-view.fxml".equals(fxmlPath);
        double offset = reverse ? -78 : 78;
        wrappedNextPanel.setTranslateX(offset);
        wrappedNextPanel.setOpacity(0);
        panelFrame.getChildren().add(0, wrappedNextPanel);

        FadeTransition nextFade = new FadeTransition(Duration.millis(160), wrappedNextPanel);
        nextFade.setToValue(1);
        TranslateTransition nextMove = new TranslateTransition(Duration.millis(190), wrappedNextPanel);
        nextMove.setInterpolator(Interpolator.EASE_BOTH);
        nextMove.setToX(0);

        ParallelTransition transition;
        if (currentPanel != null) {
            FadeTransition currentFade = new FadeTransition(Duration.millis(140), currentPanel);
            currentFade.setToValue(0);
            TranslateTransition currentMove = new TranslateTransition(Duration.millis(180), currentPanel);
            currentMove.setInterpolator(Interpolator.EASE_BOTH);
            currentMove.setToX(reverse ? 78 : -78);
            transition = new ParallelTransition(currentFade, currentMove, nextFade, nextMove);
            Node oldPanel = currentPanel;
            transition.setOnFinished(event -> panelFrame.getChildren().remove(oldPanel));
        } else {
            transition = new ParallelTransition(nextFade, nextMove);
        }
        transition.play();
    }

    private static Node extractAuthPanel(Parent root) {
        Node panel = root.lookup(".signin-panel");
        return panel != null ? panel : root.lookup(".card");
    }

    private static void detachFromParent(Node node) {
        if (node.getParent() instanceof Pane parentPane) {
            parentPane.getChildren().remove(node);
        }
    }

    private static void prepareAuthPanel(Node panel, String fxmlPath) {
        if (!panel.getStyleClass().contains("signin-panel")) {
            panel.getStyleClass().add("signin-panel");
        }
        if ("/views/front/signup-view.fxml".equals(fxmlPath)) {
            panel.getStyleClass().add("signup-modal-panel");
        }
    }

    private static ScrollPane wrapAuthPanel(Node panel, String fxmlPath) {
        ScrollPane scrollPane = new ScrollPane(panel);
        scrollPane.getStyleClass().add("auth-panel-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        double width = "/views/front/signup-view.fxml".equals(fxmlPath) ? 720 : 640;
        double height = "/views/front/signup-view.fxml".equals(fxmlPath) ? 720 : 640;
        scrollPane.setPrefViewportWidth(width);
        scrollPane.setPrefViewportHeight(height);
        scrollPane.setMinViewportWidth(width);
        scrollPane.setMinWidth(width);
        scrollPane.setPrefWidth(width);
        scrollPane.setMaxWidth(width);
        scrollPane.setMaxHeight(height);
        scrollPane.setPickOnBounds(false);
        return scrollPane;
    }

    private static ParallelTransition buildSignInModalEntrance(Node overlay, Node panelFrame) {
        overlay.setOpacity(0);
        panelFrame.setOpacity(0);
        panelFrame.setScaleX(0.96);
        panelFrame.setScaleY(0.96);
        panelFrame.setTranslateY(18);

        FadeTransition overlayFade = new FadeTransition(Duration.millis(120), overlay);
        overlayFade.setToValue(1);

        FadeTransition panelFade = new FadeTransition(Duration.millis(170), panelFrame);
        panelFade.setToValue(1);

        ScaleTransition panelScale = new ScaleTransition(Duration.millis(190), panelFrame);
        panelScale.setInterpolator(Interpolator.EASE_BOTH);
        panelScale.setToX(1);
        panelScale.setToY(1);

        TranslateTransition panelMove = new TranslateTransition(Duration.millis(190), panelFrame);
        panelMove.setInterpolator(Interpolator.EASE_BOTH);
        panelMove.setToY(0);

        return new ParallelTransition(overlayFade, panelFade, panelScale, panelMove);
    }

    private static ParallelTransition buildSignInModalExit(Node overlay, Node panelFrame) {
        FadeTransition overlayFade = new FadeTransition(Duration.millis(120), overlay);
        overlayFade.setToValue(0);

        FadeTransition panelFade = new FadeTransition(Duration.millis(110), panelFrame);
        panelFade.setToValue(0);

        ScaleTransition panelScale = new ScaleTransition(Duration.millis(130), panelFrame);
        panelScale.setInterpolator(Interpolator.EASE_BOTH);
        panelScale.setToX(0.97);
        panelScale.setToY(0.97);

        TranslateTransition panelMove = new TranslateTransition(Duration.millis(130), panelFrame);
        panelMove.setInterpolator(Interpolator.EASE_BOTH);
        panelMove.setToY(14);

        return new ParallelTransition(overlayFade, panelFade, panelScale, panelMove);
    }

    private static void applyButtonAnimations(Parent root) {
        root.lookupAll(".button").forEach(node -> {
            if (!(node instanceof ButtonBase)) {
                return;
            }

            node.setOnMouseEntered(event -> animateScale(node, 1.05, 120));
            node.setOnMouseExited(event -> animateScale(node, 1.0, 120));
            node.setOnMousePressed(event -> animateScale(node, 0.96, 70));
            node.setOnMouseReleased(event -> animateScale(node, 1.05, 90));
        });
    }

    private static void animateScale(javafx.scene.Node node, double targetScale, int millis) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(millis), node);
        transition.setToX(targetScale);
        transition.setToY(targetScale);
        transition.play();
    }

    private static Throwable rootCauseOf(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    public static String getSelectedRole() {
        return selectedRole;
    }

    public static void setSelectedRole(String role) {
        selectedRole = role;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static void logoutToFrontHome() {
        clearSession();
        selectedRole = null;
        pendingGoogleUserProfile = null;
        com.pegasus.tools.Session.setCurrentUser(null);
        try {
            goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GoogleUserProfile getPendingGoogleUserProfile() {
        return pendingGoogleUserProfile;
    }

    public static void setPendingGoogleUserProfile(GoogleUserProfile profile) {
        pendingGoogleUserProfile = profile;
    }

    public static void clearPendingGoogleUserProfile() {
        pendingGoogleUserProfile = null;
    }

    public static boolean isAuthModalOpen() {
        return authModalOpen.get();
    }

    public static ReadOnlyBooleanProperty authModalOpenProperty() {
        return authModalOpen;
    }
}
