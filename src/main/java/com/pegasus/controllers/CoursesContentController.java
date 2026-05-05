package com.pegasus.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.pegasus.entities.Course;
import com.pegasus.services.CourseCategoryClassifier;
import com.pegasus.services.CourseService;
import com.pegasus.services.FantasyChatbotService;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CoursesContentController implements FrontContentAware {
    private static final String ALL_CATEGORIES = "All";

    @FXML
    private TextField txtCourseSearch;

    @FXML
    private FlowPane categoryFiltersContainer;

    @FXML
    private FlowPane coursesContainer;

    @FXML
    private ScrollPane coursesScrollPane;

    @FXML
    private VBox chatPanel;

    @FXML
    private Button chatBubbleButton;

    @FXML
    private VBox chatMessagesContainer;

    @FXML
    private ScrollPane chatMessagesScrollPane;

    @FXML
    private TextField chatPromptField;

    @FXML
    private Button chatSendButton;

    private final CourseService courseService = new CourseService();
    private final FantasyChatbotService fantasyChatbotService = new FantasyChatbotService();
    private final List<Course> publishedCourses = new ArrayList<>();
    private final Set<String> discoveredCategories = new LinkedHashSet<>();
    private static final double CARD_WIDTH = 320;
    private static final double CARD_HEIGHT = 270;
    private FrontLayoutController frontLayoutController;
    private String selectedCategory = ALL_CATEGORIES;

    @FXML
    public void initialize() {
        coursesContainer.prefWrapLengthProperty().bind(
                coursesScrollPane.widthProperty().subtract(48)
        );
        if (txtCourseSearch != null) {
            txtCourseSearch.textProperty().addListener((obs, oldValue, newValue) -> refreshCourseCards());
        }
        setupChatWidget();
        loadPublishedCourses();
    }

    private void setupChatWidget() {
        if (chatPanel == null || chatBubbleButton == null) {
            return;
        }

        chatPanel.setVisible(false);
        chatPanel.setManaged(false);
        addBotMessage("Hi, I am Pegasus Muse. Tell me a fantasy scene and I will generate an art image for it.");
    }

    @FXML
    private void toggleChat() {
        boolean opening = chatPanel != null && !chatPanel.isVisible();
        if (chatPanel != null) {
            chatPanel.setVisible(opening);
            chatPanel.setManaged(opening);
        }
        if (chatBubbleButton != null) {
            chatBubbleButton.setVisible(!opening);
            chatBubbleButton.setManaged(!opening);
        }
        if (opening && chatPromptField != null) {
            chatPromptField.requestFocus();
        }
    }

    @FXML
    private void sendChatMessage() {
        if (chatPromptField == null) {
            return;
        }

        String prompt = chatPromptField.getText() == null ? "" : chatPromptField.getText().trim();
        if (prompt.isEmpty()) {
            return;
        }

        addUserMessage(prompt);
        chatPromptField.clear();
        setChatInputDisabled(true);
        addBotMessage("Creating your fantasy image...");

        CompletableFuture
                .supplyAsync(() -> generateFantasyImage(prompt))
                .thenAccept(response -> Platform.runLater(() -> {
                    addBotMessage(response.getMessage());
                    response.getImageBase64().ifPresent(this::addBotImage);
                    setChatInputDisabled(false);
                    chatPromptField.requestFocus();
                }));
    }

    private FantasyChatbotService.ChatbotResponse generateFantasyImage(String prompt) {
        try {
            return fantasyChatbotService.createFantasyImage(prompt);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return FantasyChatbotService.ChatbotResponse.promptOnly("Image generation was interrupted. Try again.");
        } catch (Exception exception) {
            return FantasyChatbotService.ChatbotResponse.promptOnly("Could not generate the image: " + exception.getMessage());
        }
    }

    private void setChatInputDisabled(boolean disabled) {
        if (chatPromptField != null) {
            chatPromptField.setDisable(disabled);
        }
        if (chatSendButton != null) {
            chatSendButton.setDisable(disabled);
        }
    }

    private void addUserMessage(String message) {
        addChatBubble(message, true);
    }

    private void addBotMessage(String message) {
        addChatBubble(message, false);
    }

    private void addChatBubble(String message, boolean userMessage) {
        if (chatMessagesContainer == null) {
            return;
        }

        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(270);
        label.getStyleClass().add(userMessage ? "fantasy-chat-user-message" : "fantasy-chat-bot-message");

        HBox row = new HBox(label);
        row.setAlignment(userMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        row.setPadding(new Insets(0, 2, 0, 2));
        chatMessagesContainer.getChildren().add(row);

        if (chatMessagesScrollPane != null) {
            chatMessagesScrollPane.layout();
            chatMessagesScrollPane.setVvalue(1.0);
        }
    }

    private void addBotImage(String imageBase64) {
        if (chatMessagesContainer == null) {
            return;
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageBytes)));
            imageView.setFitWidth(270);
            imageView.setFitHeight(270);
            imageView.setPreserveRatio(true);
            imageView.getStyleClass().add("fantasy-chat-generated-image");

            HBox row = new HBox(imageView);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(0, 2, 0, 2));
            chatMessagesContainer.getChildren().add(row);

            if (chatMessagesScrollPane != null) {
                chatMessagesScrollPane.layout();
                chatMessagesScrollPane.setVvalue(1.0);
            }
        } catch (IllegalArgumentException exception) {
            addBotMessage("The image arrived, but I could not decode it.");
        }
    }

    private void loadPublishedCourses() {
        publishedCourses.clear();
        publishedCourses.addAll(courseService.getPublishedCourses());
        rebuildCategoryFilters();
        refreshCourseCards();
    }

    private void refreshCourseCards() {
        applyCourseFilter(txtCourseSearch == null ? "" : txtCourseSearch.getText(), selectedCategory);
    }

    private void applyCourseFilter(String query, String category) {
        String normalizedQuery = normalizeSearch(query);
        coursesContainer.getChildren().clear();

        for (Course course : publishedCourses) {
            if (!matchesCourse(course, normalizedQuery, category)) {
                continue;
            }
            VBox card = createCourseCard(course);
            coursesContainer.getChildren().add(card);
        }
    }

    private boolean matchesCourse(Course course, String normalizedQuery, String category) {
        String courseCategory = CourseCategoryClassifier.classify(course);
        boolean matchesCategory = ALL_CATEGORIES.equals(category) || courseCategory.equalsIgnoreCase(category);
        boolean matchesSearch = normalizedQuery.isEmpty()
                || containsIgnoreCase(course.getTitle(), normalizedQuery)
                || containsIgnoreCase(course.getDescription(), normalizedQuery)
                || containsIgnoreCase(course.getStatus(), normalizedQuery)
                || containsIgnoreCase(courseCategory, normalizedQuery);

        return matchesCategory && matchesSearch;
    }

    private void rebuildCategoryFilters() {
        if (categoryFiltersContainer == null) {
            return;
        }

        discoveredCategories.clear();
        for (Course course : publishedCourses) {
            discoveredCategories.add(CourseCategoryClassifier.classify(course));
        }

        categoryFiltersContainer.getChildren().clear();
        categoryFiltersContainer.getChildren().add(createCategoryButton(ALL_CATEGORIES));
        for (String category : discoveredCategories) {
            categoryFiltersContainer.getChildren().add(createCategoryButton(category));
        }
    }

    private Button createCategoryButton(String category) {
        Button button = new Button(category);
        button.getStyleClass().add("course-category-chip");
        if (category.equalsIgnoreCase(selectedCategory)) {
            button.getStyleClass().add("course-category-chip-active");
        }

        button.setOnAction(event -> {
            selectedCategory = category;
            rebuildCategoryFilters();
            refreshCourseCards();
        });
        return button;
    }

    private boolean containsIgnoreCase(String value, String normalizedQuery) {
        return value != null && value.toLowerCase().contains(normalizedQuery);
    }

    private String normalizeSearch(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    @Override
    public void setFrontLayoutController(FrontLayoutController frontLayoutController) {
        this.frontLayoutController = frontLayoutController;
    }

    private VBox createCourseCard(Course course) {
        StackPane thumbnailBox = new StackPane();
        thumbnailBox.getStyleClass().add("course-thumbnail-box");
        thumbnailBox.setPrefWidth(CARD_WIDTH);
        thumbnailBox.setMinHeight(140);
        thumbnailBox.setPrefHeight(140);
        thumbnailBox.setMaxWidth(Double.MAX_VALUE);

        populateThumbnail(thumbnailBox, course);

        Label titleLabel = new Label(course.getTitle());
        titleLabel.getStyleClass().add("course-title");
        titleLabel.setWrapText(true);

        Label descriptionLabel = new Label(course.getDescription());
        descriptionLabel.getStyleClass().add("course-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(CARD_WIDTH - 40);

        Label statusLabel = new Label(course.getStatus());
        statusLabel.getStyleClass().add("course-status");

        Label categoryLabel = new Label(CourseCategoryClassifier.classify(course));
        categoryLabel.getStyleClass().add("course-category-badge");

        Button openButton = new Button("Open Course");
        openButton.getStyleClass().add("course-open-button");
        openButton.setMaxWidth(Region.USE_PREF_SIZE);
        openButton.setOnAction(event -> openCourse(course));

        VBox body = new VBox(10);
        body.getStyleClass().add("course-body");
        body.getChildren().addAll(titleLabel, descriptionLabel, statusLabel, categoryLabel, openButton);
        body.setFillWidth(true);

        VBox card = new VBox();
        card.getStyleClass().add("course-card");
        card.setPrefWidth(CARD_WIDTH);
        card.setMinWidth(CARD_WIDTH);
        card.setPrefHeight(CARD_HEIGHT);
        card.setAlignment(Pos.TOP_LEFT);
        card.getChildren().addAll(thumbnailBox, body);
        card.setCursor(Cursor.HAND);
        card.setOnMouseClicked(event -> openCourse(course));

        return card;
    }

    private void openCourse(Course course) {
        if (frontLayoutController != null) {
            frontLayoutController.showCoursePlayer(course);
        }
    }

    private void populateThumbnail(StackPane thumbnailBox, Course course) {
        String thumbnailUrl = course.getThumbnailUrl();
        if (thumbnailUrl != null && !thumbnailUrl.isBlank()) {
            try {
                ImageView imageView = new ImageView(new Image(thumbnailUrl, CARD_WIDTH, 140, false, true, true));
                imageView.setFitWidth(CARD_WIDTH);
                imageView.setFitHeight(140);
                imageView.setPreserveRatio(false);
                imageView.getStyleClass().add("course-card-image");
                thumbnailBox.getChildren().add(imageView);
                return;
            } catch (Exception ignored) {
            }
        }

        Label thumbnailPlaceholder = new Label("Thumbnail");
        thumbnailPlaceholder.getStyleClass().add("course-thumbnail-placeholder");
        thumbnailBox.getChildren().add(thumbnailPlaceholder);
    }
}
