package com.pegasus;

import com.pegasus.controllers.SceneNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneNavigator.init(stage);
        SceneNavigator.goTo("/views/home-view.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
