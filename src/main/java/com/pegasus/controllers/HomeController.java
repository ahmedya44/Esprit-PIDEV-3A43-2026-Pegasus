package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.CloudinaryService;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private Button navProfileButton;

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
    private final ObservableList<User> allUsers = FXCollections.observableArrayList();

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

    public void onGoToHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
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
            SceneNavigator.goTo("/views/profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToGallery() {
        try {
            SceneNavigator.goTo("/views/menu-view.fxml");
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
            SceneNavigator.goTo("/views/FrontLayout.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToEvents() {
        try {
            SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToCoursesDashboard() {
        if (SceneNavigator.getCurrentUser() == null) {
            onGoToSignIn();
            return;
        }
        try {
            FrontLayoutController.showDashboardOnOpen();
            SceneNavigator.goTo("/views/FrontLayout.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToBackoffice() {
        try {
            SceneNavigator.goTo("/views/backoffice-simple.fxml");
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
        if (navProfileButton != null) {
            navProfileButton.setVisible(loggedIn);
            navProfileButton.setManaged(loggedIn);
        }
        if (navBackofficeButton != null) {
            navBackofficeButton.setVisible(false);
            navBackofficeButton.setManaged(false);
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
                navAuthButton.setText("Log Out");
                navAuthButton.setOnAction(event -> onLogout());
            }

            boolean isAdmin = "admin".equalsIgnoreCase(currentUser.getDtype());
            if (navBackofficeButton != null) {
                navBackofficeButton.setVisible(isAdmin);
                navBackofficeButton.setManaged(isAdmin);
            }
            if (adminUsersBox != null) {
                adminUsersBox.setVisible(isAdmin);
                adminUsersBox.setManaged(isAdmin);
            }
            if (applyStatusButton != null) {
                applyStatusButton.setDisable(true);
            }
            if (isAdmin) {
                loadUsersTable();
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
        Properties properties = new Properties();
        try (InputStream inputStream = HomeController.class.getResourceAsStream(CLOUDFLARE_CONFIG_PATH)) {
            if (inputStream == null) {
                return null;
            }
            properties.load(inputStream);
            String value = properties.getProperty(propertyKey);
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        } catch (IOException e) {
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
