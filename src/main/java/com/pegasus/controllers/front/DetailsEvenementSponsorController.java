package com.pegasus.controllers.front;

import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.controllers.front.FrontLayoutController;
import com.pegasus.controllers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.pegasus.entities.Evenement;
import com.pegasus.entities.User;

import java.io.IOException;

public class DetailsEvenementSponsorController {

    @FXML private ImageView bigImageView;
    @FXML private Label titreLabel;
    @FXML private Label dateLabel;
    @FXML private Label heureLabel;
    @FXML private Label lieuLabel;
    @FXML private Label prixLabel;

    @FXML private Label capaciteLabel;
    @FXML private Label descLabel;
    
    @FXML private Button btnSponsoriser;
    @FXML private Label confirmationLabel;
    @FXML private Button navProfileButton;
    @FXML private Button navAuthButton;
    @FXML private MenuButton navAccountMenu;

    private Evenement currentEvenement;

    @FXML
    private void initialize() {
        refreshNavbarState();
    }

    public void initData(Evenement e) {
        this.currentEvenement = e;
        titreLabel.setText(e.getTitre());
        dateLabel.setText(e.getDate());
        heureLabel.setText(e.getHeure());
        lieuLabel.setText(e.getLieu());
        prixLabel.setText(String.format("%.2f DT", e.getPrix()));

        capaciteLabel.setText(e.getCapacite_max() + " Personnes");
        descLabel.setText(e.getDescription() != null && !e.getDescription().isEmpty() ? e.getDescription() : "Aucun détail.");

        // Image clipping
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(600, 350);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        bigImageView.setClip(clip);

        try {
            if (e.getImage() != null && !e.getImage().isEmpty()) {
                String path = e.getImage();
                if (!path.startsWith("http") && !path.startsWith("file:")) {
                    path = "file:///" + path.replace("\\", "/");
                }
                Image img = new Image(path, true);
                bigImageView.setImage(img);
            }
        } catch (Exception ex) { }
    }

    @FXML
    void sponsoriser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/front/reserver-pack-view.fxml"));
        Parent root = loader.load();
        
        ReserverPackController controller = loader.getController();
        controller.initData(currentEvenement);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void retourListe(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/front/liste-evenement-sponsor.fxml"));
        stage.getScene().setRoot(root);
    }

    @FXML
    private void goHome() throws IOException {
        SceneNavigator.goTo("/views/front/home-view.fxml");
    }

    @FXML
    private void goGallery() throws IOException {
        SceneNavigator.goTo("/views/front/menu-view.fxml");
    }

    @FXML
    private void goCourses() throws IOException {
        FrontLayoutController.showCoursesOnOpen();
        SceneNavigator.goTo("/views/front/FrontLayout.fxml");
    }

    @FXML
    private void goProducts() throws IOException {
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
    private void goForum() throws IOException {
        if (SceneNavigator.getCurrentUser() == null) {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
            return;
        }
        ForumModuleLauncher.openForumWindow();
    }

    @FXML
    private void goEvents() throws IOException {
        SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    private void goProfile() throws IOException {
        SceneNavigator.goTo(SceneNavigator.getCurrentUser() == null ? "/views/front/signin-view.fxml" : "/views/front/profile-view.fxml");
    }

    @FXML
    private void handleAuth() throws IOException {
        if (SceneNavigator.getCurrentUser() != null) {
            SceneNavigator.logoutToFrontHome();
            return;
        }
        SceneNavigator.goTo("/views/front/signin-view.fxml");
    }

    private void refreshNavbarState() {
        boolean loggedIn = SceneNavigator.getCurrentUser() != null;
        if (navProfileButton != null) {
            navProfileButton.setVisible(false);
            navProfileButton.setManaged(false);
        }
        if (navAuthButton != null) {
            navAuthButton.setVisible(!loggedIn);
            navAuthButton.setManaged(!loggedIn);
            navAuthButton.setText("Sign In");
        }
        if (navAccountMenu != null) {
            navAccountMenu.setVisible(loggedIn);
            navAccountMenu.setManaged(loggedIn);
        }
    }
}
