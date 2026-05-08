package com.pegasus.services;

import com.pegasus.entities.Course;
import com.pegasus.interfaces.CrudService;
import com.pegasus.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CrudService<Course> {

    private final Connection connection;

    public CourseService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(Course course) {
        String sql = "INSERT INTO course (title, description, thumbnail_url, status, created_at, artist_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getThumbnailUrl());
            ps.setString(4, course.getStatus());
            ps.setTimestamp(5, Timestamp.valueOf(course.getCreatedAt()));
            ps.setInt(6, course.getArtistId());

            ps.executeUpdate();
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            System.out.println("Add course error: " + e.getMessage());
        }
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE course SET title=?, description=?, thumbnail_url=?, status=?, artist_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getThumbnailUrl());
            ps.setString(4, course.getStatus());
            ps.setInt(5, course.getArtistId());
            ps.setInt(6, course.getId());

            ps.executeUpdate();
            System.out.println("Course updated successfully.");
        } catch (SQLException e) {
            System.out.println("Update course error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        new LearningProgressService().deleteCourseProgress(id);
        String deleteQuizChoicesSql =
                "DELETE qc FROM quiz_choice qc " +
                "JOIN quiz_question qq ON qc.question_id = qq.id " +
                "JOIN quiz q ON qq.quiz_id = q.id " +
                "WHERE q.course_id = ?";
        String deleteQuizQuestionsSql =
                "DELETE qq FROM quiz_question qq " +
                "JOIN quiz q ON qq.quiz_id = q.id " +
                "WHERE q.course_id = ?";
        String deleteQuizzesSql = "DELETE FROM quiz WHERE course_id=?";
        String deleteCourseVideosSql =
                "DELETE cv FROM course_video cv " +
                "JOIN course_section cs ON cv.section_id = cs.id " +
                "WHERE cs.course_id = ?";
        String deleteCourseSectionsSql = "DELETE FROM course_section WHERE course_id=?";
        String deleteCourseSql = "DELETE FROM course WHERE id=?";
        boolean initialAutoCommit = true;

        try {
            initialAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            PreparedStatement deleteQuizChoicesPs = connection.prepareStatement(deleteQuizChoicesSql);
            deleteQuizChoicesPs.setInt(1, id);
            deleteQuizChoicesPs.executeUpdate();

            PreparedStatement deleteQuizQuestionsPs = connection.prepareStatement(deleteQuizQuestionsSql);
            deleteQuizQuestionsPs.setInt(1, id);
            deleteQuizQuestionsPs.executeUpdate();

            PreparedStatement deleteQuizzesPs = connection.prepareStatement(deleteQuizzesSql);
            deleteQuizzesPs.setInt(1, id);
            deleteQuizzesPs.executeUpdate();

            PreparedStatement deleteCourseVideosPs = connection.prepareStatement(deleteCourseVideosSql);
            deleteCourseVideosPs.setInt(1, id);
            deleteCourseVideosPs.executeUpdate();

            PreparedStatement deleteCourseSectionsPs = connection.prepareStatement(deleteCourseSectionsSql);
            deleteCourseSectionsPs.setInt(1, id);
            deleteCourseSectionsPs.executeUpdate();

            PreparedStatement deleteCoursePs = connection.prepareStatement(deleteCourseSql);
            deleteCoursePs.setInt(1, id);
            deleteCoursePs.executeUpdate();

            connection.commit();
            connection.setAutoCommit(initialAutoCommit);
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(initialAutoCommit);
                }
            } catch (SQLException rollbackException) {
                System.out.println("Rollback delete course error: " + rollbackException.getMessage());
            }
            System.out.println("Delete course error: " + e.getMessage());
        }
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";

        if (connection == null) {
            System.out.println("Get all courses error: database connection is null.");
            return courses;
        }

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("thumbnail_url"),
                        rs.getString("status"),
                        readCreatedAt(rs),
                        rs.getInt("artist_id")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Get all courses error: " + e.getMessage());
        }

        return courses;
    }

    @Override
    public Course getById(int id) {
        String sql = "SELECT * FROM course WHERE id=?";
        Course course = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course = new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("thumbnail_url"),
                        rs.getString("status"),
                        readCreatedAt(rs),
                        rs.getInt("artist_id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get course by id error: " + e.getMessage());
        }

        return course;
    }

    public List<Course> getPublishedCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE status = 'PUBLISHED'";

        if (connection == null) {
            System.out.println("Get published courses error: database connection is null.");
            return courses;
        }

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("thumbnail_url"),
                        rs.getString("status"),
                        readCreatedAt(rs),
                        rs.getInt("artist_id")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Get published courses error: " + e.getMessage());
        }

        return courses;
    }

    public List<Course> getByArtistId(int artistId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE artist_id = ?";

        if (connection == null) {
            System.out.println("Get artist courses error: database connection is null.");
            return courses;
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, artistId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("thumbnail_url"),
                        rs.getString("status"),
                        readCreatedAt(rs),
                        rs.getInt("artist_id")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Get artist courses error: " + e.getMessage());
        }

        return courses;
    }

    private java.time.LocalDateTime readCreatedAt(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        return createdAt == null ? null : createdAt.toLocalDateTime();
    }
}
