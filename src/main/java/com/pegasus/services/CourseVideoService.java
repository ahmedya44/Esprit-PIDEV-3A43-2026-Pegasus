package com.pegasus.services;

import com.pegasus.entities.CourseVideo;
import com.pegasus.interfaces.CrudService;
import com.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseVideoService implements CrudService<CourseVideo> {

    private final Connection connection;

    public CourseVideoService() {
        connection = MyConnection.getConnection();
    }

    @Override
    public void add(CourseVideo video) {
        String sql = "INSERT INTO course_video (id, title, video_url, duration_sec, order_index, is_preview, section_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, video.getId() > 0 ? video.getId() : getNextId());
            ps.setString(2, video.getTitle());
            ps.setString(3, video.getVideoUrl());
            ps.setInt(4, video.getDurationSec());
            ps.setInt(5, video.getOrderIndex());
            ps.setBoolean(6, video.isPreview());
            ps.setInt(7, video.getSectionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add course video error: " + e.getMessage());
        }
    }

    @Override
    public void update(CourseVideo video) {
        String sql = "UPDATE course_video SET title=?, video_url=?, duration_sec=?, order_index=?, is_preview=?, section_id=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, video.getTitle());
            ps.setString(2, video.getVideoUrl());
            ps.setInt(3, video.getDurationSec());
            ps.setInt(4, video.getOrderIndex());
            ps.setBoolean(5, video.isPreview());
            ps.setInt(6, video.getSectionId());
            ps.setInt(7, video.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update course video error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM course_video WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete course video error: " + e.getMessage());
        }
    }

    @Override
    public List<CourseVideo> getAll() {
        List<CourseVideo> videos = new ArrayList<>();
        String sql = "SELECT * FROM course_video ORDER BY section_id, order_index, id";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                videos.add(mapVideo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get all course videos error: " + e.getMessage());
        }

        return videos;
    }

    @Override
    public CourseVideo getById(int id) {
        String sql = "SELECT * FROM course_video WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapVideo(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get course video by id error: " + e.getMessage());
        }

        return null;
    }

    public List<CourseVideo> getBySectionId(int sectionId) {
        List<CourseVideo> videos = new ArrayList<>();
        String sql = "SELECT * FROM course_video WHERE section_id=? ORDER BY order_index, id";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                videos.add(mapVideo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get course videos by section id error: " + e.getMessage());
        }

        return videos;
    }

    public int countBySectionId(int sectionId) {
        String sql = "SELECT COUNT(*) FROM course_video WHERE section_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Count course videos by section id error: " + e.getMessage());
        }

        return 0;
    }

    private int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM course_video";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Get next course video id error: " + e.getMessage());
        }

        return 1;
    }

    private CourseVideo mapVideo(ResultSet rs) throws SQLException {
        return new CourseVideo(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("video_url"),
                rs.getInt("duration_sec"),
                rs.getInt("order_index"),
                rs.getBoolean("is_preview"),
                rs.getInt("section_id")
        );
    }
}
