package tn.esprit.pegasus.services;

import tn.esprit.pegasus.entities.Course;
import tn.esprit.pegasus.interfaces.CrudService;
import tn.esprit.pegasus.utils.MyConnection;

import java.sql.*;
import java.time.LocalDateTime;
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
        String sql = "DELETE FROM course WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Delete course error: " + e.getMessage());
        }
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";

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
                        rs.getTimestamp("created_at").toLocalDateTime(),
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
                        rs.getTimestamp("created_at").toLocalDateTime(),
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
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getInt("artist_id")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Get published courses error: " + e.getMessage());
        }

        return courses;
    }
}