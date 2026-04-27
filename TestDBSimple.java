import com.pegasus.tools.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBSimple {
    public static void main(String[] args) {
        System.out.println("Test de connexion et vérification de la base de données...");
        
        try {
            Connection conn = dbConnection.getConnection();
            System.out.println("Connexion réussie !");
            
            Statement stmt = conn.createStatement();
            
            // Vérifier si la base de données artwork existe
            System.out.println("\nVerification des tables dans la base de données 'artwork'...");
            ResultSet tables = stmt.executeQuery("SHOW TABLES");
            boolean tableExists = false;
            while (tables.next()) {
                String tableName = tables.getString(1);
                System.out.println("Table trouvee: " + tableName);
                if ("art".equals(tableName)) {
                    tableExists = true;
                }
            }
            
            if (tableExists) {
                // Compter les enregistrements
                ResultSet count = stmt.executeQuery("SELECT COUNT(*) FROM art");
                if (count.next()) {
                    int total = count.getInt(1);
                    System.out.println("\nNombre d'oeuvres dans la table 'art': " + total);
                    
                    if (total > 0) {
                        // Afficher les 3 dernières œuvres
                        ResultSet latest = stmt.executeQuery("SELECT title, status, created_at FROM art ORDER BY created_at DESC LIMIT 3");
                        System.out.println("\nDernieres oeuvres ajoutees:");
                        while (latest.next()) {
                            System.out.println("- " + latest.getString("title") + " (Status: " + latest.getString("status") + ")");
                        }
                    }
                }
            } else {
                System.out.println("\nLa table 'art' n'existe pas !");
                System.out.println("Creation de la table...");
                stmt.executeUpdate("CREATE TABLE art (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255) NOT NULL," +
                    "description TEXT," +
                    "image_url VARCHAR(500)," +
                    "status VARCHAR(50) DEFAULT 'pending'," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
                System.out.println("Table 'art' creee avec succes !");
            }
            
            conn.close();
            System.out.println("\nTest termine avec succes !");
            
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
