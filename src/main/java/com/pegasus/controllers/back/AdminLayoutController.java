package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminLayoutController {
    private static final String ACTIVE_CLASS = "admin-nav-button-active";
    private static final String SUB_ACTIVE_CLASS = "admin-subnav-button-active";
    private static final String SIDEBAR_COLLAPSED_CLASS = "admin-sidebar-collapsed";
    private static final double SIDEBAR_EXPANDED_WIDTH = 270;
    private static final double SIDEBAR_COLLAPSED_WIDTH = 88;
    private static String initialSection = "home";

    @FXML private VBox adminSidebar;
    @FXML private VBox adminBrandTextBox;
    @FXML private StackPane contentPane;
    @FXML private Label pageTitleLabel;
    @FXML private Label pageSubtitleLabel;
    @FXML private Label adminNameLabel;
    @FXML private Label adminRoleLabel;
    @FXML private Label workspaceSectionLabel;
    @FXML private Label systemSectionLabel;

    @FXML private Button sidebarToggleButton;
    @FXML private Button homeNavButton;
    @FXML private Button usersNavButton;
    @FXML private Button eventsNavButton;
    @FXML private VBox eventSubmenu;
    @FXML private Button eventEventsNavButton;
    @FXML private Button eventParticipantsNavButton;
    @FXML private Button eventSponsorsNavButton;
    @FXML private Button eventPacksNavButton;
    @FXML private Button eventStatsNavButton;
    @FXML private Button coursesNavButton;
    @FXML private VBox courseSubmenu;
    @FXML private Button courseCoursesNavButton;
    @FXML private Button courseQuizzesNavButton;
    @FXML private Button courseStatsNavButton;
    @FXML private Button galleryNavButton;
    @FXML private Button forumNavButton;
    @FXML private Button productsNavButton;
    @FXML private Button settingsNavButton;
    @FXML private Button frontOfficeButton;
    @FXML private Button logoutButton;

    private List<Button> navButtons;
    private List<Button> eventSubNavButtons;
    private List<Button> courseSubNavButtons;
    private final Map<Button, String> expandedButtonTexts = new LinkedHashMap<>();
    private final Map<Button, String> collapsedButtonTexts = new LinkedHashMap<>();
    private boolean sidebarCollapsed;
    private boolean eventSubmenuRequested;
    private boolean courseSubmenuRequested;

    @FXML
    public void initialize() {
        navButtons = List.of(
                homeNavButton,
                usersNavButton,
                eventsNavButton,
                coursesNavButton,
                galleryNavButton,
                forumNavButton,
                productsNavButton,
                settingsNavButton
        );
        courseSubNavButtons = List.of(
                courseCoursesNavButton,
                courseQuizzesNavButton,
                courseStatsNavButton
        );
        eventSubNavButtons = List.of(
                eventEventsNavButton,
                eventParticipantsNavButton,
                eventSponsorsNavButton,
                eventPacksNavButton,
                eventStatsNavButton
        );
        cacheSidebarLabels();
        setSidebarCollapsed(false, false);

        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            goToSignIn();
            return;
        }

        adminNameLabel.setText(safeText(currentUser.getUsername(), "Pegasus Admin"));
        adminRoleLabel.setText("Administrator workspace");
        switch (initialSection) {
            case "users" -> showUsers();
            case "events" -> showEvents();
            case "courses" -> showCourses();
            case "gallery" -> showGallery();
            case "forum" -> showForum();
            case "products" -> showProducts();
            case "settings" -> showSettings();
            default -> showHome();
        }
        initialSection = "home";
    }

    public static void showEventsOnOpen() {
        initialSection = "events";
    }

    @FXML
    public void toggleSidebar() {
        setSidebarCollapsed(!sidebarCollapsed, true);
    }

    @FXML
    public void showHome() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminHomeContent.fxml",
                homeNavButton,
                "Home Dashboard",
                "Operational overview across Pegasus modules."
        );
    }

    @FXML
    public void showUsers() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminUsersContent.fxml",
                usersNavButton,
                "Users Dashboard",
                "Manage roles, account status and member access across Pegasus."
        );
    }

    @FXML
    public void showEvents() {
        showEventEvents();
    }

    @FXML
    public void showEventEvents() {
        loadEventSection(
                "/views/back/AdminEventsContent.fxml",
                eventEventsNavButton,
                "Event Dashboard",
                "Manage event records, capacity, pricing and schedule data."
        );
    }

    @FXML
    public void showEventParticipants() {
        loadEventSection(
                "/views/back/backparticipant-view.fxml",
                eventParticipantsNavButton,
                "Event Participants",
                "Review registered participants by event."
        );
    }

    @FXML
    public void showEventSponsors() {
        loadEventSection(
                "/views/back/AdminEventSponsorsContent.fxml",
                eventSponsorsNavButton,
                "Event Sponsors",
                "Review sponsorship reservations and packs across events."
        );
    }

    @FXML
    public void showEventPacks() {
        loadEventSection(
                "/views/back/AdminSponsoringPacksContent.fxml",
                eventPacksNavButton,
                "Sponsoring Packs",
                "Manage sponsorship packages, prices and descriptions."
        );
    }

    @FXML
    public void showEventStatistics() {
        loadEventSection(
                "/views/back/AdminEventStatsContent.fxml",
                eventStatsNavButton,
                "Event Statistics",
                "Track participant growth, sponsorship revenue and event distribution."
        );
    }

    private void loadEventSection(String fxmlPath, Button activeSubButton, String title, String subtitle) {
        hideCourseSubmenu();
        showEventSubmenu();
        loadSection(
                fxmlPath,
                eventsNavButton,
                title,
                subtitle
        );
        setActiveEventSubButton(activeSubButton);
    }

    @FXML
    public void showCourses() {
        showCourseCourses();
    }

    @FXML
    public void showCourseCourses() {
        loadCourseSection(
                "/views/back/AdminCoursesContent.fxml",
                courseCoursesNavButton,
                "Course Dashboard",
                "Approve artist submissions, review catalogue status and manage course records."
        );
    }

    @FXML
    public void showCourseQuizzes() {
        loadCourseSection(
                "/views/back/AdminQuizzesContent.fxml",
                courseQuizzesNavButton,
                "Quizzes Dashboard",
                "Review quizzes, edit core details and remove records when needed."
        );
    }

    @FXML
    public void showCourseStatistics() {
        loadCourseSection(
                "/views/back/AdminCourseStatsContent.fxml",
                courseStatsNavButton,
                "Course Statistics",
                "Track learner progress, course completion and quiz score analytics."
        );
    }

    private void loadCourseSection(String fxmlPath, Button activeSubButton, String title, String subtitle) {
        hideEventSubmenu();
        showCourseSubmenu();
        loadSection(
                fxmlPath,
                coursesNavButton,
                title,
                subtitle
        );
        setActiveSubButton(activeSubButton);
    }

    @FXML
    public void showGallery() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminGalleryContent.fxml",
                galleryNavButton,
                "Gallery Dashboard",
                "Review, publish, reject and remove submitted artworks."
        );
    }

    @FXML
    public void showForum() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminForumContent.fxml",
                forumNavButton,
                "Forum Dashboard",
                "Moderation entry point for community conversations."
        );
    }

    @FXML
    public void showProducts() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminProductsContent.fxml",
                productsNavButton,
                "Product Dashboard",
                "Catalogue status, stock and product visibility."
        );
    }

    @FXML
    public void showSettings() {
        hideEventSubmenu();
        hideCourseSubmenu();
        loadSection(
                "/views/back/AdminSettingsContent.fxml",
                settingsNavButton,
                "Settings",
                "Admin session, identity and backoffice preferences."
        );
    }

    @FXML
    public void logout() {
        SceneNavigator.logoutToFrontHome();
    }

    @FXML
    public void openFrontOffice() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSection(String fxmlPath, Button activeButton, String title, String subtitle) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));
            page.setOpacity(0);
            page.setTranslateY(12);
            contentPane.getChildren().setAll(page);
            pageTitleLabel.setText(title);
            pageSubtitleLabel.setText(subtitle);
            setActiveButton(activeButton);
            animateLoadedPage(page);
        } catch (IOException e) {
            Label error = new Label("Could not load " + title + ": " + e.getMessage());
            error.getStyleClass().add("admin-error-label");
            contentPane.getChildren().setAll(error);
        }
    }

    private void setActiveButton(Button activeButton) {
        for (Button button : navButtons) {
            button.getStyleClass().remove(ACTIVE_CLASS);
        }
        if (!activeButton.getStyleClass().contains(ACTIVE_CLASS)) {
            activeButton.getStyleClass().add(ACTIVE_CLASS);
        }
    }

    private void showCourseSubmenu() {
        courseSubmenuRequested = true;
        if (sidebarCollapsed) {
            courseSubmenu.setVisible(false);
            courseSubmenu.setManaged(false);
            return;
        }
        if (courseSubmenu.isVisible()) {
            return;
        }
        courseSubmenu.setVisible(true);
        courseSubmenu.setManaged(true);
        courseSubmenu.setOpacity(0);
        courseSubmenu.setTranslateY(-6);

        FadeTransition fade = new FadeTransition(Duration.millis(170), courseSubmenu);
        fade.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(190), courseSubmenu);
        slide.setToY(0);
        new ParallelTransition(fade, slide).play();
    }

    private void hideCourseSubmenu() {
        courseSubmenuRequested = false;
        if (courseSubmenu == null || !courseSubmenu.isVisible()) {
            clearSubActiveButtons();
            return;
        }
        FadeTransition fade = new FadeTransition(Duration.millis(130), courseSubmenu);
        fade.setToValue(0);
        fade.setOnFinished(event -> {
            courseSubmenu.setVisible(false);
            courseSubmenu.setManaged(false);
            courseSubmenu.setOpacity(1);
            courseSubmenu.setTranslateY(0);
        });
        fade.play();
        clearSubActiveButtons();
    }

    private void showEventSubmenu() {
        eventSubmenuRequested = true;
        if (sidebarCollapsed) {
            eventSubmenu.setVisible(false);
            eventSubmenu.setManaged(false);
            return;
        }
        if (eventSubmenu.isVisible()) {
            return;
        }
        eventSubmenu.setVisible(true);
        eventSubmenu.setManaged(true);
        eventSubmenu.setOpacity(0);
        eventSubmenu.setTranslateY(-6);

        FadeTransition fade = new FadeTransition(Duration.millis(170), eventSubmenu);
        fade.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(190), eventSubmenu);
        slide.setToY(0);
        new ParallelTransition(fade, slide).play();
    }

    private void hideEventSubmenu() {
        eventSubmenuRequested = false;
        if (eventSubmenu == null || !eventSubmenu.isVisible()) {
            clearEventSubActiveButtons();
            return;
        }
        FadeTransition fade = new FadeTransition(Duration.millis(130), eventSubmenu);
        fade.setToValue(0);
        fade.setOnFinished(event -> {
            eventSubmenu.setVisible(false);
            eventSubmenu.setManaged(false);
            eventSubmenu.setOpacity(1);
            eventSubmenu.setTranslateY(0);
        });
        fade.play();
        clearEventSubActiveButtons();
    }

    private void setActiveSubButton(Button activeButton) {
        clearSubActiveButtons();
        if (!activeButton.getStyleClass().contains(SUB_ACTIVE_CLASS)) {
            activeButton.getStyleClass().add(SUB_ACTIVE_CLASS);
        }
    }

    private void clearSubActiveButtons() {
        for (Button button : courseSubNavButtons) {
            button.getStyleClass().remove(SUB_ACTIVE_CLASS);
        }
    }

    private void setActiveEventSubButton(Button activeButton) {
        clearEventSubActiveButtons();
        if (!activeButton.getStyleClass().contains(SUB_ACTIVE_CLASS)) {
            activeButton.getStyleClass().add(SUB_ACTIVE_CLASS);
        }
    }

    private void clearEventSubActiveButtons() {
        for (Button button : eventSubNavButtons) {
            button.getStyleClass().remove(SUB_ACTIVE_CLASS);
        }
    }

    private void goToSignIn() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException ignored) {
        }
    }

    private String safeText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private void cacheSidebarLabels() {
        registerSidebarButton(homeNavButton, "Home Dashboard", "Home");
        registerSidebarButton(usersNavButton, "Users Dashboard", "Users");
        registerSidebarButton(eventsNavButton, "Event Dashboard", "Events");
        registerSidebarButton(eventEventsNavButton, "Events", "List");
        registerSidebarButton(eventParticipantsNavButton, "Participants", "Users");
        registerSidebarButton(eventSponsorsNavButton, "Sponsors", "Deals");
        registerSidebarButton(eventPacksNavButton, "Sponsoring Packs", "Box");
        registerSidebarButton(eventStatsNavButton, "Statistiques", "Stats");
        registerSidebarButton(coursesNavButton, "Course Dashboard", "Courses");
        registerSidebarButton(courseCoursesNavButton, "Course", "All");
        registerSidebarButton(courseQuizzesNavButton, "Quizzes", "Quiz");
        registerSidebarButton(courseStatsNavButton, "Statistique", "Stats");
        registerSidebarButton(galleryNavButton, "Gallery Dashboard", "Gallery");
        registerSidebarButton(forumNavButton, "Forum Dashboard", "Forum");
        registerSidebarButton(productsNavButton, "Product Dashboard", "Products");
        registerSidebarButton(settingsNavButton, "Settings", "Settings");
        registerSidebarButton(frontOfficeButton, "View Front Office", "Front");
        registerSidebarButton(logoutButton, "Log Out", "Exit");
    }

    private void registerSidebarButton(Button button, String expandedText, String collapsedText) {
        if (button == null) {
            return;
        }
        expandedButtonTexts.put(button, expandedText);
        collapsedButtonTexts.put(button, collapsedText);
        button.setTooltip(new Tooltip(expandedText));
    }

    private void setSidebarCollapsed(boolean collapsed, boolean animate) {
        sidebarCollapsed = collapsed;
        double targetWidth = collapsed ? SIDEBAR_COLLAPSED_WIDTH : SIDEBAR_EXPANDED_WIDTH;

        updateSidebarText(collapsed);
        setNodeVisible(adminBrandTextBox, !collapsed);
        setNodeVisible(workspaceSectionLabel, !collapsed);
        setNodeVisible(systemSectionLabel, !collapsed);

        if (courseSubmenu != null) {
            boolean showSubmenu = courseSubmenuRequested && !collapsed;
            courseSubmenu.setVisible(showSubmenu);
            courseSubmenu.setManaged(showSubmenu);
        }
        if (eventSubmenu != null) {
            boolean showSubmenu = eventSubmenuRequested && !collapsed;
            eventSubmenu.setVisible(showSubmenu);
            eventSubmenu.setManaged(showSubmenu);
        }

        if (sidebarToggleButton != null) {
            sidebarToggleButton.setText(collapsed ? "Open" : "Close");
        }

        if (adminSidebar != null) {
            if (collapsed && !adminSidebar.getStyleClass().contains(SIDEBAR_COLLAPSED_CLASS)) {
                adminSidebar.getStyleClass().add(SIDEBAR_COLLAPSED_CLASS);
            } else if (!collapsed) {
                adminSidebar.getStyleClass().remove(SIDEBAR_COLLAPSED_CLASS);
            }
            animateSidebarWidth(targetWidth, animate);
        }
    }

    private void updateSidebarText(boolean collapsed) {
        for (Map.Entry<Button, String> entry : expandedButtonTexts.entrySet()) {
            Button button = entry.getKey();
            button.setText(collapsed ? collapsedButtonTexts.get(button) : entry.getValue());
            button.setAlignment(collapsed ? Pos.CENTER : Pos.CENTER_LEFT);
        }
    }

    private void setNodeVisible(javafx.scene.Node node, boolean visible) {
        if (node == null) {
            return;
        }
        node.setVisible(visible);
        node.setManaged(visible);
    }

    private void animateSidebarWidth(double targetWidth, boolean animate) {
        if (adminSidebar == null) {
            return;
        }
        if (!animate) {
            adminSidebar.setMinWidth(targetWidth);
            adminSidebar.setPrefWidth(targetWidth);
            adminSidebar.setMaxWidth(targetWidth);
            return;
        }

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(260),
                new KeyValue(adminSidebar.minWidthProperty(), targetWidth, Interpolator.EASE_BOTH),
                new KeyValue(adminSidebar.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH),
                new KeyValue(adminSidebar.maxWidthProperty(), targetWidth, Interpolator.EASE_BOTH)
        ));
        timeline.play();
    }

    private void animateLoadedPage(Parent page) {
        FadeTransition fade = new FadeTransition(Duration.millis(180), page);
        fade.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(220), page);
        slide.setToY(0);
        new ParallelTransition(fade, slide).play();
    }
}
