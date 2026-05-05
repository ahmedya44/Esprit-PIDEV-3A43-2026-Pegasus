package com.pegasus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import com.pegasus.entities.Course;
import com.pegasus.entities.User;

import java.io.IOException;
import java.util.function.Consumer;

public class FrontLayoutController {
    private static final String COURSES_PAGE = "/views/CoursesContent.fxml";
    private static final String DASHBOARD_PAGE = "/views/CoursesDashboardContent.fxml";
    private static String initialPage = COURSES_PAGE;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button navProfileButton;

    @FXML
    private Button navAuthButton;

    @FXML
    private Button navCoursesDashboardButton;

    public static void showCoursesOnOpen() {
        initialPage = COURSES_PAGE;
    }

    public static void showDashboardOnOpen() {
        initialPage = DASHBOARD_PAGE;
    }

    @FXML
    public void initialize() {
        refreshAuthState();
        if (!isUserLoggedIn()) {
            navigateTo("/views/signin-view.fxml");
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
            navigateTo("/views/signin-view.fxml");
            return;
        }
        loadPage(COURSES_PAGE);
    }

    @FXML
    public void showCoursesDashboard(ActionEvent event) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/signin-view.fxml");
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
        navigateTo("/views/home-view.fxml");
    }

    @FXML
    public void goGallery(ActionEvent event) {
        navigateTo("/views/menu-view.fxml");
    }

    @FXML
    public void goEvents(ActionEvent event) {
        navigateTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    public void goProfile(ActionEvent event) {
        if (SceneNavigator.getCurrentUser() == null) {
            navigateTo("/views/signin-view.fxml");
            return;
        }
        navigateTo("/views/profile-view.fxml");
    }

    @FXML
    public void goLogin(ActionEvent event) {
        if (SceneNavigator.getCurrentUser() != null) {
            SceneNavigator.clearSession();
            refreshAuthState();
            loadPage(COURSES_PAGE);
            return;
        }
        navigateTo("/views/signin-view.fxml");
    }

    private void loadPage(String path) {
        loadPage(path, null);
    }

    public void showCoursePlayer(Course course) {
        if (!isUserLoggedIn()) {
            navigateTo("/views/signin-view.fxml");
            return;
        }
        loadPage("/views/CoursePlayerContent.fxml", controller -> {
            if (controller instanceof CoursePlayerController coursePlayerController) {
                coursePlayerController.setCourse(course);
            }
        });
    }

    private void refreshAuthState() {
        boolean loggedIn = SceneNavigator.getCurrentUser() != null;
        if (navProfileButton != null) {
            navProfileButton.setVisible(loggedIn);
            navProfileButton.setManaged(loggedIn);
        }
        if (navCoursesDashboardButton != null) {
            boolean isArtist = isCurrentUserArtist();
            navCoursesDashboardButton.setVisible(isArtist);
            navCoursesDashboardButton.setManaged(isArtist);
        }
        if (navAuthButton != null) {
            navAuthButton.setText(loggedIn ? "Log Out" : "Login");
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
        } catch (IOException e) {
            System.out.println("Load page error: " + path);
            e.printStackTrace();
        }
    }
}
