package com.pegasus;

import com.pegasus.tools.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MyConnection.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage.setTitle("Pegasus");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}