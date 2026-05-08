package com.pegasus.controllers;

import com.pegasus.entities.User;

public final class EventsRoleRouter {
    private EventsRoleRouter() {
    }

    public static String resolveEventsEntryFxml() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || currentUser.getDtype() == null) {
            return "/views/front/liste-evenement-participant.fxml";
        }

        String dtype = currentUser.getDtype().trim().toLowerCase();
        return switch (dtype) {
            case "sponsor" -> "/views/front/liste-evenement-sponsor.fxml";
            case "artiste" -> "/views/front/liste-evenement-artiste.fxml";
            default -> "/views/front/liste-evenement-participant.fxml";
        };
    }
}
