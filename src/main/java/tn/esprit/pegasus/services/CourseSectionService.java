package tn.esprit.pegasus.services;

import tn.esprit.pegasus.entities.CourseSection;
import tn.esprit.pegasus.interfaces.CrudService;
import tn.esprit.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseSectionService implements CrudService<CourseSection> {

    private final Connection connection;

    public CourseSectionService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(CourseSection section) {
        String sql = "INSERT INTO course_section (id, title, order_index, course_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, section.getId() > 0 ? section.getId() : getNextId());
            ps.setString(2, section.getTitle());
            ps.setInt(3, section.getOrderIndex());
            ps.setInt(4, section.getCourseId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add course section error: " + e.getMessage());
        }
    }

    @Override
    public void update(CourseSection section) {
        String sql = "UPDATE course_section SET title=?, order_index=?, course_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, section.getTitle());
            ps.setInt(2, section.getOrderIndex());
            ps.setInt(3, section.getCourseId());
            ps.setInt(4, section.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update course section error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String deleteVideosSql = "DELETE FROM course_video WHERE section_id=?";
        String deleteSectionSql = "DELETE FROM course_section WHERE id=?";

        try {
            PreparedStatement deleteVideosPs = connection.prepareStatement(deleteVideosSql);
            deleteVideosPs.setInt(1, id);
            deleteVideosPs.executeUpdate();

            PreparedStatement deleteSectionPs = connection.prepareStatement(deleteSectionSql);
            deleteSectionPs.setInt(1, id);
            deleteSectionPs.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete course section error: " + e.getMessage());
        }
    }

    @Override
    public List<CourseSection> getAll() {
        List<CourseSection> sections = new ArrayList<>();
        String sql = "SELECT * FROM course_section ORDER BY course_id, order_index, id";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                sections.add(mapSection(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get all course sections error: " + e.getMessage());
        }

        return sections;
    }

    @Override
    public CourseSection getById(int id) {
        String sql = "SELECT * FROM course_section WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapSection(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get course section by id error: " + e.getMessage());
        }

        return null;
    }

    public List<CourseSection> getByCourseId(int courseId) {
        List<CourseSection> sections = new ArrayList<>();
        String sql = "SELECT * FROM course_section WHERE course_id=? ORDER BY order_index, id";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sections.add(mapSection(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get course sections by course id error: " + e.getMessage());
        }

        return sections;
    }

    private int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM course_section";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Get next course section id error: " + e.getMessage());
        }

        return 1;
    }

    private CourseSection mapSection(ResultSet rs) throws SQLException {
        return new CourseSection(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("order_index"),
                rs.getInt("course_id")
        );
    }
}
