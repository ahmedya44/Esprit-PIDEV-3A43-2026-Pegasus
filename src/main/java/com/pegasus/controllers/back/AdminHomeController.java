package com.pegasus.controllers.back;

import com.pegasus.dao.ProduitDAO;
import com.pegasus.entities.Art;
import com.pegasus.entities.Course;
import com.pegasus.entities.Evenement;
import com.pegasus.entities.User;
import com.pegasus.entities.Produit;
import com.pegasus.services.CourseService;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceEvenement;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class AdminHomeController {
    @FXML private Label usersCountLabel;
    @FXML private Label eventsCountLabel;
    @FXML private Label coursesCountLabel;
    @FXML private Label artworksCountLabel;
    @FXML private Label productsCountLabel;
    @FXML private Label pendingArtworksLabel;
    @FXML private Label healthLabel;

    @FXML
    public void initialize() {
        int users = safeUsers();
        List<Evenement> events = safeEvents();
        List<Course> courses = safeCourses();
        List<Art> artworks = safeArtworks();
        List<Produit> products = safeProducts();

        usersCountLabel.setText(String.valueOf(users));
        eventsCountLabel.setText(String.valueOf(events.size()));
        coursesCountLabel.setText(String.valueOf(courses.size()));
        artworksCountLabel.setText(String.valueOf(artworks.size()));
        productsCountLabel.setText(String.valueOf(products.size()));
        pendingArtworksLabel.setText(String.valueOf(artworks.stream()
                .filter(art -> "pending".equalsIgnoreCase(nullToEmpty(art.getStatus())))
                .count()));
        healthLabel.setText("Live workspace data loaded");
    }

    private int safeUsers() {
        try {
            return new ServiceUser().findAllUsers().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private List<Evenement> safeEvents() {
        try {
            return new ServiceEvenement().afficherEvenements();
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<Course> safeCourses() {
        try {
            return new CourseService().getAll();
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<Art> safeArtworks() {
        try {
            return new ServiceArt().getAllArts();
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<Produit> safeProducts() {
        try {
            return new ProduitDAO().getAll();
        } catch (Exception e) {
            return List.of();
        }
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
