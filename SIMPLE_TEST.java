import javax.swing.*;

public class SIMPLE_TEST {
    public static void main(String[] args) {
        System.out.println("Test simple de l'interface !");
        
        JFrame frame = new JFrame("Test Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JLabel label = new JLabel("Votre application fonctionne !", JLabel.CENTER);
        JButton button = new JButton("Ajouter une oeuvre");
        
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Le bouton fonctionne ! Votre projet est prêt !");
        });
        
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);
        frame.add(panel);
        
        frame.setVisible(true);
        System.out.println("Fenêtre affichée !");
    }
}
