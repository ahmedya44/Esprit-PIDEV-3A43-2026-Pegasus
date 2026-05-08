package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.controllers.FrontContentAware;
import com.pegasus.controllers.EventsRoleRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import com.pegasus.entities.Course;
import com.pegasus.entities.User;

import java.io.IOException;
import java.util.function.Consumer;

public class FrontLayoutController {
    private static final String COURSES_PAGE = "/views/front/CoursesContent.fxml";
    private static final String DASHBOARD_PAGE = "/views/back/CoursesDashboardContent.fxml";
    private static String initialPage = COURSES_PAGE;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button navProfileButton;

    @FXML
    private Button navAuthButton;

    @FXML
    private MenuButton navAccountMenu;

    @FXML
    private Button navCoursesDashboardButton;

    @FXML
    private Button navBackofficeButton;

    @FXML
    private Button navCoursesButton;

    public static void showCoursesOnOpen() {
        initialPage = COURSES_PAGE;
    }

    public static void showDashboardOnOpen() {
        initialPage = DASHBOARD_PAGE;
    }

    @FXML
    public void initialize() {
        refreshAuthState();
        if (navCoursesButton != null && !navCoursesButton.getStyleClass().contains("pegasus-nav-button-active")) {
            navCoursesButton.getStyleClass().add("pegasus-nav-button-active");
        }
        if (!isUserLoggedIn()) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        if (DASHBOARD_PAGE.equals(initialPage) && !isCurrentUserArtist()) {
            initialPage = COURSES_PAGE;
        }
        loadPage(initialPage);
        initialPage = COURSES_PAGE;
    }

    @FXML
    public void showCourses(ActionEvent event) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        loadPage(COURSES_PAGE);
    }

    @FXML
    public void showCoursesDashboard(ActionEvent event) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        if (!isCurrentUserArtist()) {
            loadPage(COURSES_PAGE);
            return;
        }
        loadPage(DASHBOARD_PAGE);
    }

    @FXML
    public void goHome(ActionEvent event) {
        navigateTo("/views/front/home-view.fxml");
    }

    @FXML
    public void goGallery(ActionEvent event) {
        navigateTo("/views/front/menu-view.fxml");
    }

    @FXML
    public void goEvents(ActionEvent event) {
        navigateTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    public void goProducts(ActionEvent event) {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        String target = "artiste".equalsIgnoreCase(currentUser.getDtype())
                ? "/views/back/DashboardArtiste.fxml"
                : "/views/front/DashboardUser.fxml";
        navigateTo(target);
    }

    @FXML
    public void goForum(ActionEvent event) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        ForumModuleLauncher.openForumWindow();
    }

    @FXML
    public void goProfile(ActionEvent event) {
        if (SceneNavigator.getCurrentUser() == null) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        navigateTo("/views/front/profile-view.fxml");
    }

    @FXML
    public void goLogin(ActionEvent event) {
        if (SceneNavigator.getCurrentUser() != null) {
            SceneNavigator.logoutToFrontHome();
            return;
        }
        navigateTo("/views/front/signin-view.fxml");
    }

    @FXML
    public void goBackoffice(ActionEvent event) {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        navigateTo("/views/back/AdminLayout.fxml");
    }

    private void loadPage(String path) {
        loadPage(path, null);
    }

    public void showCoursePlayer(Course course) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        loadPage("/views/front/CoursePlayerContent.fxml", controller -> {
            if (controller instanceof CoursePlayerController coursePlayerController) {
                coursePlayerController.setCourse(course);
            }
        });
    }

    private void refreshAuthState() {
        boolean loggedIn = SceneNavigator.getCurrentUser() != null;
        if (navProfileButton != null) {
            navProfileButton.setVisible(false);
            navProfileButton.setManaged(false);
        }
        if (navCoursesDashboardButton != null) {
            boolean isArtist = isCurrentUserArtist();
            navCoursesDashboardButton.setVisible(isArtist);
            navCoursesDashboardButton.setManaged(isArtist);
        }
        if (navBackofficeButton != null) {
            boolean isAdmin = isCurrentUserAdmin();
            navBackofficeButton.setVisible(isAdmin);
            navBackofficeButton.setManaged(isAdmin);
        }
        if (navAuthButton != null) {
            navAuthButton.setVisible(!loggedIn);
            navAuthButton.setManaged(!loggedIn);
            navAuthButton.setText("Login");
        }
        if (navAccountMenu != null) {
            navAccountMenu.setVisible(loggedIn);
            navAccountMenu.setManaged(loggedIn);
            navAccountMenu.setText("\uD83D\uDC64");
        }
    }

    private boolean isUserLoggedIn() {
        return SceneNavigator.getCurrentUser() != null;
    }

    private void navigateTo(String fxmlPath) {
        try {
            SceneNavigator.goTo(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCurrentUserArtist() {
        User currentUser = SceneNavigator.getCurrentUser();
        return currentUser != null && "artiste".equalsIgnoreCase(currentUser.getDtype());
    }

    private boolean isCurrentUserAdmin() {
        User currentUser = SceneNavigator.getCurrentUser();
        return currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
    }

    private void loadPage(String path, Consumer<Object> controllerConfigurer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent page = loader.load();
            Object controller = loader.getController();
            if (controller instanceof FrontContentAware aware) {
                aware.setFrontLayoutController(this);
            }
            if (controllerConfigurer != null) {
                controllerConfigurer.accept(controller);
            }
            contentArea.getChildren().setAll(page);
            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 0.0);
            AnchorPane.setLeftAnchor(page, 0.0);
        } catch (Exception e) {
            System.out.println("Load page error: " + path);
            e.printStackTrace();
        }
    }
}
