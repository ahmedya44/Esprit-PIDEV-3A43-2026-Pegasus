package com.pegasus.services;

import com.pegasus.entities.Quiz;
import com.pegasus.interfaces.CrudService;
import com.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizService implements CrudService<Quiz> {

    private final Connection connection;

    public QuizService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(Quiz quiz) {
        String sql = "INSERT INTO quiz (title, time_limit_min, passing_score, attempt_limit, course_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());

            if (quiz.getTimeLimitMin() == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, quiz.getTimeLimitMin());
            }

            ps.setInt(3, quiz.getPassingScore());

            if (quiz.getAttemptLimit() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, quiz.getAttemptLimit());
            }

            ps.setInt(5, quiz.getCourseId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add quiz error: " + e.getMessage());
        }
    }

    @Override
    public void update(Quiz quiz) {
        String sql = "UPDATE quiz SET title=?, time_limit_min=?, passing_score=?, attempt_limit=?, course_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());

            if (quiz.getTimeLimitMin() == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, quiz.getTimeLimitMin());
            }

            ps.setInt(3, quiz.getPassingScore());

            if (quiz.getAttemptLimit() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, quiz.getAttemptLimit());
            }

            ps.setInt(5, quiz.getCourseId());
            ps.setInt(6, quiz.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update quiz error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String deleteChoicesSql =
                "DELETE qc FROM quiz_choice qc " +
                "JOIN quiz_question qq ON qc.question_id = qq.id " +
                "WHERE qq.quiz_id = ?";
        String deleteQuestionsSql = "DELETE FROM quiz_question WHERE quiz_id=?";
        String deleteQuizSql = "DELETE FROM quiz WHERE id=?";

        try {
            PreparedStatement deleteChoicesPs = connection.prepareStatement(deleteChoicesSql);
            deleteChoicesPs.setInt(1, id);
            deleteChoicesPs.executeUpdate();

            PreparedStatement deleteQuestionsPs = connection.prepareStatement(deleteQuestionsSql);
            deleteQuestionsPs.setInt(1, id);
            deleteQuestionsPs.executeUpdate();

            PreparedStatement deleteQuizPs = connection.prepareStatement(deleteQuizSql);
            deleteQuizPs.setInt(1, id);
            deleteQuizPs.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete quiz error: " + e.getMessage());
        }
    }

    @Override
    public List<Quiz> getAll() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quiz";

        if (connection == null) {
            System.out.println("Get all quizzes error: database connection is null.");
            return quizzes;
        }

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                quizzes.add(mapQuiz(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get all quizzes error: " + e.getMessage());
        }

        return quizzes;
    }

    @Override
    public Quiz getById(int id) {
        String sql = "SELECT * FROM quiz WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapQuiz(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get quiz by id error: " + e.getMessage());
        }

        return null;
    }

    public Quiz getByCourseId(int courseId) {
        String sql = "SELECT * FROM quiz WHERE course_id=? ORDER BY id LIMIT 1";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapQuiz(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get quiz by course id error: " + e.getMessage());
        }

        return null;
    }

    private Quiz mapQuiz(ResultSet rs) throws SQLException {
        Integer timeLimit = rs.getObject("time_limit_min") == null ? null : rs.getInt("time_limit_min");
        Integer attemptLimit = rs.getObject("attempt_limit") == null ? null : rs.getInt("attempt_limit");

        return new Quiz(
                rs.getInt("id"),
                rs.getString("title"),
                timeLimit,
                rs.getInt("passing_score"),
                attemptLimit,
                rs.getInt("course_id")
        );
    }
}
