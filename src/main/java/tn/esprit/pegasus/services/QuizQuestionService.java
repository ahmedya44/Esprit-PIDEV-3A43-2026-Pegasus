package tn.esprit.pegasus.services;

import tn.esprit.pegasus.entities.QuizQuestion;
import tn.esprit.pegasus.interfaces.CrudService;
import tn.esprit.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizQuestionService implements CrudService<QuizQuestion> {

    private final Connection connection;

    public QuizQuestionService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(QuizQuestion question) {
        String sql = "INSERT INTO quiz_question (id, question_text, points, order_index, quiz_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, question.getId() > 0 ? question.getId() : getNextId());
            ps.setString(2, question.getQuestionText());
            ps.setInt(3, question.getPoints());
            ps.setInt(4, question.getOrderIndex());
            ps.setInt(5, question.getQuizId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add quiz question error: " + e.getMessage());
        }
    }

    @Override
    public void update(QuizQuestion question) {
        String sql = "UPDATE quiz_question SET question_text=?, points=?, order_index=?, quiz_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, question.getQuestionText());
            ps.setInt(2, question.getPoints());
            ps.setInt(3, question.getOrderIndex());
            ps.setInt(4, question.getQuizId());
            ps.setInt(5, question.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update quiz question error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String deleteChoicesSql = "DELETE FROM quiz_choice WHERE question_id=?";
        String deleteQuestionSql = "DELETE FROM quiz_question WHERE id=?";

        try {
            PreparedStatement deleteChoicesPs = connection.prepareStatement(deleteChoicesSql);
            deleteChoicesPs.setInt(1, id);
            deleteChoicesPs.executeUpdate();

            PreparedStatement deleteQuestionPs = connection.prepareStatement(deleteQuestionSql);
            deleteQuestionPs.setInt(1, id);
            deleteQuestionPs.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete quiz question error: " + e.getMessage());
        }
    }

    @Override
    public List<QuizQuestion> getAll() {
        List<QuizQuestion> questions = new ArrayList<>();
        String sql = "SELECT * FROM quiz_question ORDER BY quiz_id, order_index, id";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                questions.add(mapQuestion(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get all quiz questions error: " + e.getMessage());
        }

        return questions;
    }

    @Override
    public QuizQuestion getById(int id) {
        String sql = "SELECT * FROM quiz_question WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapQuestion(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get quiz question by id error: " + e.getMessage());
        }

        return null;
    }

    public List<QuizQuestion> getByQuizId(int quizId) {
        List<QuizQuestion> questions = new ArrayList<>();
        String sql = "SELECT * FROM quiz_question WHERE quiz_id=? ORDER BY order_index, id";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questions.add(mapQuestion(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get quiz questions by quiz id error: " + e.getMessage());
        }

        return questions;
    }

    private int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM quiz_question";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Get next quiz question id error: " + e.getMessage());
        }

        return 1;
    }

    private QuizQuestion mapQuestion(ResultSet rs) throws SQLException {
        return new QuizQuestion(
                rs.getInt("id"),
                rs.getString("question_text"),
                rs.getInt("points"),
                rs.getInt("order_index"),
                rs.getInt("quiz_id")
        );
    }
}
