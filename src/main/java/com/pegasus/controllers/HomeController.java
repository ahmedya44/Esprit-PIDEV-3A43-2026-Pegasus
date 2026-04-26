package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController {
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
    private Button navAuthButton;

    @FXML
    private VBox adminUsersBox;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colStatus;

    private ServiceUser serviceUser;

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("dtype"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        refreshUserState();
    }

    public void onGoToSignIn() {
        try {
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.goTo("/views/role-selection-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogout() {
        SceneNavigator.clearSession();
        refreshUserState();
    }

    public void onGoToEditProfile() {
        try {
            SceneNavigator.goTo("/views/profile-edit-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshUserState() {
        User currentUser = SceneNavigator.getCurrentUser();
        boolean loggedIn = currentUser != null;

        signInButton.setVisible(!loggedIn);
        signInButton.setManaged(!loggedIn);
        signUpButton.setVisible(!loggedIn);
        signUpButton.setManaged(!loggedIn);

        logoutButton.setVisible(loggedIn);
        logoutButton.setManaged(loggedIn);
        editProfileButton.setVisible(loggedIn);
        editProfileButton.setManaged(loggedIn);

        if (loggedIn) {
            userStatusLabel.setText("Connected as: " + currentUser.getUsername());
            navAuthButton.setText("Log Out");
            navAuthButton.setOnAction(event -> onLogout());

            boolean isAdmin = "admin".equalsIgnoreCase(currentUser.getDtype());
            adminUsersBox.setVisible(isAdmin);
            adminUsersBox.setManaged(isAdmin);
            if (isAdmin) {
                loadUsersTable();
            }
        } else {
            userStatusLabel.setText("You are not connected.");
            navAuthButton.setText("Sign In");
            navAuthButton.setOnAction(event -> onGoToSignIn());
            adminUsersBox.setVisible(false);
            adminUsersBox.setManaged(false);
            usersTable.getItems().clear();
        }
    }

    private void loadUsersTable() {
        if (!initService()) {
            return;
        }
        usersTable.setItems(FXCollections.observableArrayList(serviceUser.findAllUsers()));
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
}
