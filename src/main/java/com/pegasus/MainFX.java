package com.pegasus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FrontLayout.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 700);

        scene.getStylesheets().add(getClass().getResource("/css/courses.css").toExternalForm());

        stage.setTitle("Pegasus App");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
