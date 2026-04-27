import com.pegasus.tools.dbConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Test de connexion à la base de données...");
        
        try {
            Connection conn = dbConnection.getConnection();
            System.out.println("Connexion réussie !");
            
            // Test simple query
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM art");
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Nombre d'oeuvres dans la base: " + count);
            }
            
            conn.close();
            System.out.println("Test terminé avec succès !");
            
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
