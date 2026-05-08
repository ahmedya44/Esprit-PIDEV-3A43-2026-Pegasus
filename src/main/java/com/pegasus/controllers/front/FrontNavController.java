package com.pegasus.controllers.front;

import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

import java.io.IOException;

public abstract class FrontNavController {
    @FXML protected Button navBackofficeButton;
    @FXML protected Button navProfileButton;
    @FXML protected Button navAuthButton;
    @FXML protected MenuButton navAccountMenu;

    protected void refreshFrontNavbarState() {
        User currentUser = SceneNavigator.getCurrentUser();
        boolean loggedIn = currentUser != null;
        boolean admin = loggedIn && "admin".equalsIgnoreCase(currentUser.getDtype());

        setVisibleManaged(navBackofficeButton, admin);
        setVisibleManaged(navProfileButton, false);

        if (navAuthButton != null) {
            setVisibleManaged(navAuthButton, !loggedIn);
            navAuthButton.setText("Sign In");
        }
        if (navAccountMenu != null) {
            setVisibleManaged(navAccountMenu, loggedIn);
            navAccountMenu.setText(loggedIn ? safeText(currentUser.getUsername(), "Account") : "Account");
        }
    }

    @FXML
    public void goHome() throws IOException {
        beforeFrontNavigation();
        SceneNavigator.goTo("/views/front/home-view.fxml");
    }

    @FXML
    public void goGallery() throws IOException {
        beforeFrontNavigation();
        SceneNavigator.goTo("/views/front/menu-view.fxml");
    }

    @FXML
    public void goCourses() throws IOException {
        beforeFrontNavigation();
        FrontLayoutController.showCoursesOnOpen();
        SceneNavigator.goTo("/views/front/FrontLayout.fxml");
    }

    @FXML
    public void goEvents() throws IOException {
        beforeFrontNavigation();
        SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    public void goProducts() throws IOException {
        beforeFrontNavigation();
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
            return;
        }
        SceneNavigator.goTo("artiste".equalsIgnoreCase(currentUser.getDtype())
                ? "/views/back/DashboardArtiste.fxml"
                : "/views/front/DashboardUser.fxml");
    }

    @FXML
    public void goForum() throws IOException {
        beforeFrontNavigation();
        if (SceneNavigator.getCurrentUser() == null) {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
            return;
        }
        ForumModuleLauncher.openForumWindow();
    }

    @FXML
    public void goProfile() throws IOException {
        beforeFrontNavigation();
        SceneNavigator.goTo(SceneNavigator.getCurrentUser() == null
                ? "/views/front/signin-view.fxml"
                : "/views/front/profile-view.fxml");
    }

    @FXML
    public void handleAuth() throws IOException {
        beforeFrontNavigation();
        if (SceneNavigator.getCurrentUser() != null) {
            SceneNavigator.logoutToFrontHome();
            return;
        }
        SceneNavigator.goTo("/views/front/signin-view.fxml");
    }

    @FXML
    public void goBackoffice() throws IOException {
        beforeFrontNavigation();
        SceneNavigator.goTo("/views/back/AdminLayout.fxml");
    }

    protected void beforeFrontNavigation() {
    }

    private void setVisibleManaged(javafx.scene.Node node, boolean visible) {
        if (node != null) {
            node.setVisible(visible);
            node.setManaged(visible);
        }
    }

    private String safeText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
