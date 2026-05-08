package com.pegasus;

import com.pegasus.controllers.SceneNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneNavigator.init(stage);
        SceneNavigator.goTo("/views/front/home-view.fxml");
    }

    public static void main(String[] args) {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win") && System.getProperty("prism.order") == null) {
            System.setProperty("prism.order", "sw");
        }
        launch(args);
    }
}
