package com.pegasus.controllers;

import com.pegasus.entities.User;

public final class EventsRoleRouter {
    private EventsRoleRouter() {
    }

    public static String resolveEventsEntryFxml() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || currentUser.getDtype() == null) {
            return "/views/liste-evenement-participant.fxml";
        }

        String dtype = currentUser.getDtype().trim().toLowerCase();
        return switch (dtype) {
            case "sponsor" -> "/views/liste-evenement-sponsor.fxml";
            case "artiste" -> "/views/liste-evenement-artiste.fxml";
            case "admin" -> "/views/backevent-view.fxml";
            default -> "/views/liste-evenement-participant.fxml";
        };
    }
}
