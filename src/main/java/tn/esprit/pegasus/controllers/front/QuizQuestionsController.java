package tn.esprit.pegasus.controllers.front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.pegasus.entities.Quiz;
import tn.esprit.pegasus.entities.QuizChoice;
import tn.esprit.pegasus.entities.QuizQuestion;
import tn.esprit.pegasus.services.QuizChoiceService;
import tn.esprit.pegasus.services.QuizQuestionService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class QuizQuestionsController {

    @FXML
    private Label lblQuizTitle;

    @FXML
    private Label lblQuizSubtitle;

    @FXML
    private Label lblQuestionCount;

    @FXML
    private Label lblChoiceCount;

    @FXML
    private ListView<QuizQuestion> lvQuestions;

    @FXML
    private Label lblSelectedQuestionTitle;

    @FXML
    private Label lblSelectedQuestionMeta;

    @FXML
    private Label lblCorrectChoice;

    @FXML
    private VBox choicesContainer;

    @FXML
    private Label lblEmptyChoices;

    @FXML
    private Button btnEditQuestion;

    @FXML
    private Button btnDeleteQuestion;

    private final QuizQuestionService quizQuestionService = new QuizQuestionService();
    private final QuizChoiceService quizChoiceService = new QuizChoiceService();
    private final ObservableList<QuizQuestion> questionObservableList = FXCollections.observableArrayList();

    private Quiz quiz;
    private QuizQuestion selectedQuestion;

    @FXML
    public void initialize() {
        setupQuestionsList();
        updateSelectedQuestionState(null);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        lblQuizTitle.setText(quiz.getTitle());
        lblQuizSubtitle.setText("Build the quiz with ordered questions, answer choices, and a single correct answer.");
        refreshQuestions();
    }

    @FXML
    public void openCreateQuestionDialog() {
        showQuestionDialog(null);
    }

    @FXML
    public void openEditQuestionDialog() {
        if (selectedQuestion != null) {
            showQuestionDialog(selectedQuestion);
        }
    }

    @FXML
    public void deleteSelectedQuestion() {
        if (selectedQuestion == null) {
            showAlert(Alert.AlertType.WARNING, "No Question Selected", "Choose a question before deleting it.");
            return;
        }

        if (confirmDelete("Delete Question", "This will remove the question and all its choices.")) {
            quizQuestionService.delete(selectedQuestion.getId());
            refreshQuestions();
        }
    }

    private void setupQuestionsList() {
        lvQuestions.setItems(questionObservableList);
        lvQuestions.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(QuizQuestion item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label orderBadge = new Label(String.format("%02d", item.getOrderIndex()));
                orderBadge.getStyleClass().add("section-order-badge");

                Label titleLabel = new Label(item.getQuestionText());
                titleLabel.getStyleClass().add("section-item-title");
                titleLabel.setWrapText(true);

                Label metaLabel = new Label(item.getPoints() + " point(s) | " + quizChoiceService.getByQuestionId(item.getId()).size() + " choice(s)");
                metaLabel.getStyleClass().add("section-item-meta");

                VBox textBox = new VBox(4, titleLabel, metaLabel);
                HBox row = new HBox(12, orderBadge, textBox);
                row.getStyleClass().add("section-list-item");
                setGraphic(row);
            }
        });

        lvQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedQuestion = newValue;
            updateSelectedQuestionState(newValue);
            loadChoicesForSelectedQuestion();
        });
    }

    private void refreshQuestions() {
        if (quiz == null) {
            return;
        }

        List<QuizQuestion> questions = quizQuestionService.getByQuizId(quiz.getId());
        questionObservableList.setAll(questions);
        lvQuestions.refresh();
        refreshHeaderCounts();

        if (questions.isEmpty()) {
            selectedQuestion = null;
            updateSelectedQuestionState(null);
            choicesContainer.getChildren().clear();
            lblEmptyChoices.setVisible(true);
            lblEmptyChoices.setManaged(true);
            return;
        }

        if (selectedQuestion != null) {
            questions.stream()
                    .filter(question -> question.getId() == selectedQuestion.getId())
                    .findFirst()
                    .ifPresentOrElse(
                            question -> lvQuestions.getSelectionModel().select(question),
                            () -> lvQuestions.getSelectionModel().selectFirst()
                    );
        } else {
            lvQuestions.getSelectionModel().selectFirst();
        }
    }

    private void refreshHeaderCounts() {
        lblQuestionCount.setText(questionObservableList.size() + " question(s)");
        int choiceCount = 0;
        for (QuizQuestion question : questionObservableList) {
            choiceCount += quizChoiceService.getByQuestionId(question.getId()).size();
        }
        lblChoiceCount.setText(choiceCount + " answer choice(s)");
    }

    private void loadChoicesForSelectedQuestion() {
        choicesContainer.getChildren().clear();

        if (selectedQuestion == null) {
            lblEmptyChoices.setVisible(true);
            lblEmptyChoices.setManaged(true);
            lblCorrectChoice.setText("No correct answer selected.");
            return;
        }

        List<QuizChoice> choices = quizChoiceService.getByQuestionId(selectedQuestion.getId());
        lblEmptyChoices.setVisible(choices.isEmpty());
        lblEmptyChoices.setManaged(choices.isEmpty());

        String correctChoiceText = "No correct answer selected.";
        for (int i = 0; i < choices.size(); i++) {
            QuizChoice choice = choices.get(i);
            Label indexLabel = new Label(String.valueOf((char) ('A' + i)));
            indexLabel.getStyleClass().add("section-order-badge");

            Label choiceText = new Label(choice.getLabel());
            choiceText.getStyleClass().add("course-player-section-title");
            choiceText.setWrapText(true);

            Label state = new Label(choice.isCorrect() ? "Correct answer" : "Choice");
            state.getStyleClass().add(choice.isCorrect() ? "green-badge" : "neutral-badge");

            if (choice.isCorrect()) {
                correctChoiceText = choice.getLabel();
            }

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox row = new HBox(12, indexLabel, choiceText, spacer, state);
            row.getStyleClass().add("course-player-section-card");
            choicesContainer.getChildren().add(row);
        }

        lblCorrectChoice.setText(correctChoiceText);
    }

    private void updateSelectedQuestionState(QuizQuestion question) {
        boolean hasSelection = question != null;
        btnEditQuestion.setDisable(!hasSelection);
        btnDeleteQuestion.setDisable(!hasSelection);

        if (!hasSelection) {
            lblSelectedQuestionTitle.setText("Choose a question");
            lblSelectedQuestionMeta.setText("Double-click a quiz to open this builder, then add questions and choices.");
            lblCorrectChoice.setText("No correct answer selected.");
            return;
        }

        lblSelectedQuestionTitle.setText(question.getQuestionText());
        lblSelectedQuestionMeta.setText("Question " + question.getOrderIndex() + " | " + question.getPoints() + " point(s)");
    }

    private void showQuestionDialog(QuizQuestion questionToEdit) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(questionToEdit == null ? "Add Question" : "Edit Question");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType(questionToEdit == null ? "Save" : "Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextArea questionArea = new TextArea();
        Spinner<Integer> pointsSpinner = new Spinner<>();
        Spinner<Integer> orderSpinner = new Spinner<>();
        List<ChoiceEditorRow> choiceRows = new ArrayList<>();
        ToggleGroup correctChoiceGroup = new ToggleGroup();

        questionArea.setPromptText("Question text");
        questionArea.setWrapText(true);
        questionArea.setPrefRowCount(4);

        pointsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, questionToEdit == null ? 10 : questionToEdit.getPoints(), 1));
        pointsSpinner.setEditable(true);
        pointsSpinner.setMaxWidth(Double.MAX_VALUE);

        orderSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, questionToEdit == null ? questionObservableList.size() + 1 : questionToEdit.getOrderIndex(), 1));
        orderSpinner.setEditable(true);
        orderSpinner.setMaxWidth(Double.MAX_VALUE);

        Label choicesHint = new Label("Add as many answer choices as you want, then mark the one correct answer.");
        choicesHint.getStyleClass().add("dialog-field-hint");

        VBox choicesFields = new VBox(12);
        Button addChoiceButton = new Button("+ Add Choice");
        addChoiceButton.getStyleClass().add("dialog-add-choice-button");
        addChoiceButton.setOnAction(event -> addChoiceEditorRow(choiceRows, choicesFields, correctChoiceGroup, null, false));

        if (questionToEdit != null) {
            questionArea.setText(questionToEdit.getQuestionText());
            List<QuizChoice> existingChoices = quizChoiceService.getByQuestionId(questionToEdit.getId());
            for (QuizChoice existingChoice : existingChoices) {
                addChoiceEditorRow(choiceRows, choicesFields, correctChoiceGroup, existingChoice.getLabel(), existingChoice.isCorrect());
            }
        }

        if (choiceRows.isEmpty()) {
            addChoiceEditorRow(choiceRows, choicesFields, correctChoiceGroup, null, false);
            addChoiceEditorRow(choiceRows, choicesFields, correctChoiceGroup, null, false);
        }

        VBox content = buildDialogContent(
                questionToEdit == null ? "Add Quiz Question" : "Edit Quiz Question",
                "Write the question, add the answer choices, and mark the one correct choice learners must pick.",
                createFieldBlock("Question", questionArea),
                createFieldBlock("Points", buildSpinnerBlock(pointsSpinner, "points")),
                createFieldBlock("Question Order", buildSpinnerBlock(orderSpinner, "question position")),
                createFieldBlock("Answer Choices", new VBox(12, choicesHint, choicesFields, addChoiceButton))
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefViewportHeight(620);
        scrollPane.getStyleClass().add("dialog-scroll-pane");

        configureDialogPane(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(scrollPane);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            saveQuestionFromDialog(questionToEdit, questionArea.getText().trim(), pointsSpinner.getValue(), orderSpinner.getValue(), choiceRows);
        }
    }

    private void saveQuestionFromDialog(QuizQuestion questionToEdit, String questionText, Integer points, Integer orderIndex,
                                        List<ChoiceEditorRow> choiceRows) {
        String error = validateQuestionInputs(questionText, points, orderIndex, choiceRows);
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        try {
            QuizQuestion question = questionToEdit;
            if (question == null) {
                question = new QuizQuestion(questionText, points, orderIndex, quiz.getId());
                quizQuestionService.add(question);
                question = quizQuestionService.getByQuizId(quiz.getId()).stream()
                        .max(Comparator.comparingInt(QuizQuestion::getId))
                        .orElse(null);
            } else {
                question.setQuestionText(questionText);
                question.setPoints(points);
                question.setOrderIndex(orderIndex);
                quizQuestionService.update(question);
            }

            if (question == null) {
                showAlert(Alert.AlertType.ERROR, "Save Error", "The question could not be saved.");
                return;
            }

            List<QuizChoice> choices = new ArrayList<>();
            for (ChoiceEditorRow choiceRow : choiceRows) {
                String label = choiceRow.choiceField.getText().trim();
                if (!label.isEmpty()) {
                    choices.add(new QuizChoice(label, choiceRow.correctButton.isSelected(), question.getId()));
                }
            }
            quizChoiceService.replaceChoicesForQuestion(question.getId(), choices);
            refreshQuestions();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Save Error", e.getMessage());
        }
    }

    private String validateQuestionInputs(String questionText, Integer points, Integer orderIndex,
                                          List<ChoiceEditorRow> choiceRows) {
        if (questionText.isEmpty()) {
            return "Question text is required.";
        }
        if (questionText.length() < 6) {
            return "Question text must contain at least 6 characters.";
        }
        if (points == null || points < 1 || points > 100) {
            return "Points must be between 1 and 100.";
        }
        if (orderIndex == null || orderIndex < 1 || orderIndex > 100) {
            return "Question order must be between 1 and 100.";
        }

        int nonEmptyChoices = 0;
        int correctChoiceCount = 0;
        for (ChoiceEditorRow choiceRow : choiceRows) {
            String label = choiceRow.choiceField.getText().trim();
            if (!label.isEmpty()) {
                nonEmptyChoices++;
                if (choiceRow.correctButton.isSelected()) {
                    correctChoiceCount++;
                }
            } else if (choiceRow.correctButton.isSelected()) {
                return "A correct answer cannot be empty.";
            }
        }

        if (nonEmptyChoices < 2) {
            return "Please provide at least two answer choices.";
        }
        if (correctChoiceCount != 1) {
            return "Please select exactly one correct answer.";
        }
        return null;
    }

    private VBox buildDialogContent(String title, String subtitle, VBox... fieldBlocks) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-form-title");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("dialog-form-subtitle");
        subtitleLabel.setWrapText(true);

        VBox fieldsBox = new VBox(14);
        fieldsBox.getChildren().addAll(fieldBlocks);

        VBox content = new VBox(18, titleLabel, subtitleLabel, fieldsBox);
        content.getStyleClass().add("dialog-form-root");
        return content;
    }

    private VBox createFieldBlock(String labelText, javafx.scene.Node field) {
        Label label = new Label(labelText);
        label.getStyleClass().add("dialog-field-label");
        if (field instanceof Region region) {
            region.setMaxWidth(Double.MAX_VALUE);
        }
        return new VBox(8, label, field);
    }

    private VBox buildSpinnerBlock(Spinner<Integer> spinner, String suffix) {
        Label hintLabel = new Label("Choose a value for the " + suffix + ".");
        hintLabel.getStyleClass().add("dialog-field-hint");
        return new VBox(8, spinner, hintLabel);
    }

    private void addChoiceEditorRow(List<ChoiceEditorRow> choiceRows, VBox choicesFields, ToggleGroup correctChoiceGroup,
                                    String value, boolean selected) {
        ChoiceEditorRow row = new ChoiceEditorRow(correctChoiceGroup);
        if (value != null) {
            row.choiceField.setText(value);
        }
        row.correctButton.setSelected(selected);
        row.removeButton.setOnAction(event -> {
            choiceRows.remove(row);
            choicesFields.getChildren().remove(row.container);
            refreshChoiceRowActions(choiceRows);
        });

        choiceRows.add(row);
        choicesFields.getChildren().add(row.container);
        refreshChoiceRowActions(choiceRows);
    }

    private void refreshChoiceRowActions(List<ChoiceEditorRow> choiceRows) {
        boolean disableRemove = choiceRows.size() <= 2;
        for (int i = 0; i < choiceRows.size(); i++) {
            ChoiceEditorRow row = choiceRows.get(i);
            row.badgeLabel.setText("Option " + (i + 1));
            row.removeButton.setDisable(disableRemove);
        }
    }

    private void configureDialogPane(DialogPane dialogPane, ButtonType saveButtonType) {
        dialogPane.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        dialogPane.getStyleClass().add("app-dialog-pane");
        dialogPane.setPrefWidth(720);
        dialogPane.setPrefHeight(760);
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        saveButton.getStyleClass().add("dialog-primary-button");
        cancelButton.getStyleClass().add("dialog-secondary-button");
    }

    private boolean confirmDelete(String title, String message) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(null);
        confirm.setContentText(message);
        return confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class ChoiceEditorRow {
        private final VBox container;
        private final Label badgeLabel;
        private final TextField choiceField;
        private final RadioButton correctButton;
        private final Button removeButton;

        private ChoiceEditorRow(ToggleGroup toggleGroup) {
            badgeLabel = new Label("Option");
            badgeLabel.getStyleClass().add("dialog-choice-badge");

            choiceField = new TextField();
            choiceField.setPromptText("Write the answer choice");

            correctButton = new RadioButton("Mark as correct answer");
            correctButton.setToggleGroup(toggleGroup);
            correctButton.getStyleClass().add("dialog-choice-correct-radio");

            removeButton = new Button("Remove");
            removeButton.getStyleClass().add("dialog-choice-remove-button");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox header = new HBox(10, badgeLabel, spacer, removeButton);
            header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            container = new VBox(12, header, choiceField, correctButton);
            container.getStyleClass().add("dialog-choice-card");
        }
    }
}
