package com.pegasus.services;

import com.pegasus.tools.MyConnection;
import java.sql.*;
import java.util.*;

public class EventStatsService {
    private Connection connection = MyConnection.getInstance().getConnection();

    private String packTable() {
        // Logic similar to ServiceSponsoringPack to detect table
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "sponsoring_pack", null);
            if (tables.next()) return "sponsoring_pack";
            return "pack";
        } catch (SQLException e) {
            return "pack";
        }
    }

    public int getTotalEvents() {
        return getCount("SELECT COUNT(*) FROM evenement");
    }

    public int getTotalParticipants() {
        return getCount("SELECT COUNT(*) FROM participation");
    }

    public int getTotalSponsors() {
        return getCount("SELECT COUNT(DISTINCT id_sponsor) FROM " + packTable() + " WHERE id_sponsor IS NOT NULL");
    }

    public double getTotalRevenue() {
        String req = "SELECT SUM(prix) FROM " + packTable() + " WHERE id_sponsor IS NOT NULL";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Error total revenue: " + e.getMessage());
        }
        return 0.0;
    }

    public Map<String, Integer> getEventsByLieu() {
        return getMap("SELECT lieu, COUNT(*) FROM evenement GROUP BY lieu");
    }

    public Map<String, Integer> getTopEventsByParticipants() {
        return getMap("SELECT e.titre, COUNT(p.user_id) as count FROM evenement e JOIN participation p ON e.id = p.evenement_id GROUP BY e.id ORDER BY count DESC LIMIT 5");
    }

    public Map<String, Integer> getPacksPopularity() {
        return getMap("SELECT nom_pack, COUNT(*) FROM " + packTable() + " WHERE id_sponsor IS NOT NULL GROUP BY nom_pack");
    }

    private int getCount(String req) {
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error count: " + e.getMessage());
        }
        return 0;
    }

    private Map<String, Integer> getMap(String req) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                result.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Error map: " + e.getMessage());
        }
        return result;
    }
}
