import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestSimpleApp extends Application {
    
    @Override
    public void start(Stage stage) {
        System.out.println("Application JavaFX démarre...");
        
        // Créer une interface simple
        Label label = new Label("Bienvenue dans votre application !");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Button button = new Button("Ajouter une oeuvre (TEST)");
        button.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-color: #667eea; -fx-text-fill: white;");
        button.setOnAction(e -> {
            System.out.println("Bouton cliqué !");
            label.setText("Bouton fonctionne ! Votre projet est prêt !");
        });
        
        VBox root = new VBox(20, label, button);
        root.setStyle("-fx-padding: 50px; -fx-alignment: center;");
        
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Test Application");
        stage.setScene(scene);
        stage.show();
        
        System.out.println("Fenêtre affichée avec succès !");
    }
    
    public static void main(String[] args) {
        System.out.println("Lancement de l'application de test...");
        launch(args);
    }
}
