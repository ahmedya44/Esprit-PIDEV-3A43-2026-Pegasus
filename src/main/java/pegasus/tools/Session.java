package pegasus.tools;

import pegasus.entities.User;

public class Session {
    private static User currentUser;

    // Simulation d'une session (on définit un utilisateur par défaut pour le test)
    static {
        currentUser = new User(1, "Doe", "John", "john.doe@email.com", "55123456");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
