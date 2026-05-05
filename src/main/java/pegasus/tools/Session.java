package pegasus.tools;

import pegasus.entities.User;

public class Session {
    private static User currentUser;

    public static User getCurrentUser() {
        com.pegasus.entities.User appUser = com.pegasus.controllers.SceneNavigator.getCurrentUser();
        if (appUser != null) {
            User bridged = new User();
            bridged.setId(appUser.getId() == null ? 0 : appUser.getId());
            bridged.setNom(appUser.getUsername() == null ? "" : appUser.getUsername());
            bridged.setPrenom("");
            bridged.setEmail(appUser.getEmail() == null ? "" : appUser.getEmail());
            bridged.setTelephone(appUser.getPhone() == null ? "" : appUser.getPhone());
            currentUser = bridged;
        }
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
