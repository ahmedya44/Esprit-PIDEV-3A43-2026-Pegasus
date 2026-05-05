package com.pegasus.services;

import com.pegasus.entities.QuizChoice;
import com.pegasus.interfaces.CrudService;
import com.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizChoiceService implements CrudService<QuizChoice> {

    private final Connection connection;

    public QuizChoiceService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(QuizChoice choice) {
        String sql = "INSERT INTO quiz_choice (id, label, is_correct, question_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, choice.getId() > 0 ? choice.getId() : getNextId());
            ps.setString(2, choice.getLabel());
            ps.setBoolean(3, choice.isCorrect());
            ps.setInt(4, choice.getQuestionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add quiz choice error: " + e.getMessage());
        }
    }

    @Override
    public void update(QuizChoice choice) {
        String sql = "UPDATE quiz_choice SET label=?, is_correct=?, question_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, choice.getLabel());
            ps.setBoolean(2, choice.isCorrect());
            ps.setInt(3, choice.getQuestionId());
            ps.setInt(4, choice.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update quiz choice error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM quiz_choice WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete quiz choice error: " + e.getMessage());
        }
    }

    @Override
    public List<QuizChoice> getAll() {
        List<QuizChoice> choices = new ArrayList<>();
        String sql = "SELECT * FROM quiz_choice ORDER BY question_id, id";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                choices.add(mapChoice(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get all quiz choices error: " + e.getMessage());
        }

        return choices;
    }

    @Override
    public QuizChoice getById(int id) {
        String sql = "SELECT * FROM quiz_choice WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapChoice(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get quiz choice by id error: " + e.getMessage());
        }

        return null;
    }

    public List<QuizChoice> getByQuestionId(int questionId) {
        List<QuizChoice> choices = new ArrayList<>();
        String sql = "SELECT * FROM quiz_choice WHERE question_id=? ORDER BY id";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                choices.add(mapChoice(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get quiz choices by question id error: " + e.getMessage());
        }

        return choices;
    }

    public void replaceChoicesForQuestion(int questionId, List<QuizChoice> choices) {
        String deleteSql = "DELETE FROM quiz_choice WHERE question_id=?";

        try {
            PreparedStatement deletePs = connection.prepareStatement(deleteSql);
            deletePs.setInt(1, questionId);
            deletePs.executeUpdate();
            for (QuizChoice choice : choices) {
                add(new QuizChoice(choice.getLabel(), choice.isCorrect(), questionId));
            }
        } catch (SQLException e) {
            System.out.println("Replace quiz choices error: " + e.getMessage());
        }
    }

    private int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM quiz_choice";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Get next quiz choice id error: " + e.getMessage());
        }

        return 1;
    }

    private QuizChoice mapChoice(ResultSet rs) throws SQLException {
        return new QuizChoice(
                rs.getInt("id"),
                rs.getString("label"),
                rs.getBoolean("is_correct"),
                rs.getInt("question_id")
        );
    }
}
