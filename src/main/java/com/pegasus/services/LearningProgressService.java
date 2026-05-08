package com.pegasus.services;

import com.pegasus.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class LearningProgressService {
    private final Connection connection;

    public LearningProgressService() {
        connection = MyConnection.getConnection();
        ensureSchema();
    }

    public CourseProgressSnapshot loadCourseProgress(int userId, int courseId, Integer quizId) {
        CourseProgressSnapshot snapshot = new CourseProgressSnapshot();
        if (!isReady()) {
            return snapshot;
        }

        String courseSql = "SELECT completed_at FROM course_progress WHERE user_id=? AND course_id=?";
        try (PreparedStatement ps = connection.prepareStatement(courseSql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    snapshot.started = true;
                    snapshot.courseCompleted = rs.getTimestamp("completed_at") != null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Load course progress error: " + e.getMessage());
        }

        snapshot.openedVideoIds.addAll(loadIdSet(
                "SELECT video_id FROM course_video_progress WHERE user_id=? AND course_id=?",
                userId,
                courseId
        ));
        snapshot.completedVideoIds.addAll(loadIdSet(
                "SELECT video_id FROM course_video_progress WHERE user_id=? AND course_id=? AND completed_at IS NOT NULL",
                userId,
                courseId
        ));
        snapshot.completedSectionIds.addAll(loadIdSet(
                "SELECT section_id FROM course_section_progress WHERE user_id=? AND course_id=?",
                userId,
                courseId
        ));

        if (quizId != null) {
            snapshot.quizAttempt = getQuizAttempt(userId, quizId);
        }
        if (!snapshot.openedVideoIds.isEmpty()
                || !snapshot.completedVideoIds.isEmpty()
                || !snapshot.completedSectionIds.isEmpty()
                || snapshot.quizAttempt != null) {
            snapshot.started = true;
        }
        return snapshot;
    }

    public void markCourseStarted(int userId, int courseId) {
        if (!isReady()) {
            return;
        }

        String sql = """
                INSERT INTO course_progress (user_id, course_id, started_at, last_opened_at)
                VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE last_opened_at = CURRENT_TIMESTAMP
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Mark course started error: " + e.getMessage());
        }
    }

    public void markCourseCompleted(int userId, int courseId) {
        if (!isReady()) {
            return;
        }

        String sql = """
                INSERT INTO course_progress (user_id, course_id, started_at, last_opened_at, completed_at)
                VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    last_opened_at = CURRENT_TIMESTAMP,
                    completed_at = COALESCE(completed_at, CURRENT_TIMESTAMP)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Mark course completed error: " + e.getMessage());
        }
    }

    public void markSectionCompleted(int userId, int courseId, int sectionId) {
        if (!isReady()) {
            return;
        }

        markCourseStarted(userId, courseId);
        String sql = """
                INSERT INTO course_section_progress (user_id, course_id, section_id, completed_at)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    course_id = VALUES(course_id),
                    completed_at = COALESCE(completed_at, CURRENT_TIMESTAMP)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setInt(3, sectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Mark section completed error: " + e.getMessage());
        }
    }

    public void markVideoOpened(int userId, int courseId, int sectionId, int videoId) {
        if (!isReady()) {
            return;
        }

        markCourseStarted(userId, courseId);
        String sql = """
                INSERT INTO course_video_progress (user_id, course_id, section_id, video_id, opened_at)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    course_id = VALUES(course_id),
                    section_id = VALUES(section_id)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setInt(3, sectionId);
            ps.setInt(4, videoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Mark video opened error: " + e.getMessage());
        }
    }

    public void markVideoCompleted(int userId, int courseId, int sectionId, int videoId) {
        if (!isReady()) {
            return;
        }

        markCourseStarted(userId, courseId);
        String sql = """
                INSERT INTO course_video_progress (user_id, course_id, section_id, video_id, opened_at, completed_at)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    course_id = VALUES(course_id),
                    section_id = VALUES(section_id),
                    completed_at = COALESCE(completed_at, CURRENT_TIMESTAMP)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setInt(3, sectionId);
            ps.setInt(4, videoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Mark video completed error: " + e.getMessage());
        }
    }

    public boolean recordQuizAttempt(int userId, int courseId, int quizId, int scorePercent,
                                     int earnedPoints, int totalPoints, boolean passed,
                                     long timeSpentSeconds, long timeRemainingSeconds) {
        if (!isReady()) {
            return false;
        }

        markCourseCompleted(userId, courseId);
        String sql = """
                INSERT IGNORE INTO quiz_attempt
                    (user_id, quiz_id, course_id, score_percent, earned_points, total_points,
                     passed, time_spent_sec, time_remaining_sec, submitted_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            ps.setInt(3, courseId);
            ps.setInt(4, scorePercent);
            ps.setInt(5, earnedPoints);
            ps.setInt(6, totalPoints);
            ps.setBoolean(7, passed);
            ps.setLong(8, Math.max(0, timeSpentSeconds));
            if (timeRemainingSeconds < 0) {
                ps.setNull(9, java.sql.Types.INTEGER);
            } else {
                ps.setLong(9, timeRemainingSeconds);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Record quiz attempt error: " + e.getMessage());
            return false;
        }
    }

    public QuizAttemptSnapshot getQuizAttempt(int userId, int quizId) {
        if (!isReady()) {
            return null;
        }

        String sql = """
                SELECT user_id, quiz_id, course_id, score_percent, earned_points, total_points,
                       passed, time_spent_sec, time_remaining_sec, submitted_at
                FROM quiz_attempt
                WHERE user_id=? AND quiz_id=?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapQuizAttempt(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get quiz attempt error: " + e.getMessage());
        }
        return null;
    }

    public OverallLearningStats getOverallStats() {
        OverallLearningStats empty = new OverallLearningStats(0, 0, 0, 0, 0, 0, 0);
        if (!isReady()) {
            return empty;
        }

        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM course) AS total_courses,
                    (SELECT COUNT(*) FROM course WHERE UPPER(status) = 'PUBLISHED') AS published_courses,
                    (SELECT COUNT(*) FROM course_progress) AS started_courses,
                    (SELECT COUNT(*) FROM course_progress WHERE completed_at IS NOT NULL) AS completed_courses,
                    (SELECT COUNT(*) FROM quiz_attempt) AS quiz_attempts,
                    (SELECT COALESCE(AVG(score_percent), 0) FROM quiz_attempt) AS average_score,
                    (SELECT COALESCE(AVG(CASE WHEN passed = 1 THEN 1 ELSE 0 END) * 100, 0) FROM quiz_attempt) AS pass_rate
                """;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return new OverallLearningStats(
                        rs.getInt("total_courses"),
                        rs.getInt("published_courses"),
                        rs.getInt("started_courses"),
                        rs.getInt("completed_courses"),
                        rs.getInt("quiz_attempts"),
                        rs.getDouble("average_score"),
                        rs.getDouble("pass_rate")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get learning overview stats error: " + e.getMessage());
        }
        return empty;
    }

    public List<CourseStats> getCourseStats() {
        List<CourseStats> stats = new ArrayList<>();
        if (!isReady()) {
            return stats;
        }

        String sql = """
                SELECT c.id AS course_id,
                       c.title AS course_name,
                       c.status AS status,
                       c.artist_id AS artist_id,
                       COALESCE(cp.started_users, 0) AS started_users,
                       COALESCE(cp.completed_users, 0) AS completed_users,
                       COALESCE(vp.opened_lessons, 0) AS opened_lessons,
                       COALESCE(vp.completed_lessons, 0) AS completed_lessons,
                       COALESCE(qa.quiz_attempts, 0) AS quiz_attempts,
                       COALESCE(qa.average_score, 0) AS average_score,
                       COALESCE(qa.pass_rate, 0) AS pass_rate
                FROM course c
                LEFT JOIN (
                    SELECT course_id,
                           COUNT(DISTINCT user_id) AS started_users,
                           SUM(CASE WHEN completed_at IS NOT NULL THEN 1 ELSE 0 END) AS completed_users
                    FROM course_progress
                    GROUP BY course_id
                ) cp ON cp.course_id = c.id
                LEFT JOIN (
                    SELECT course_id,
                           COUNT(*) AS opened_lessons,
                           SUM(CASE WHEN completed_at IS NOT NULL THEN 1 ELSE 0 END) AS completed_lessons
                    FROM course_video_progress
                    GROUP BY course_id
                ) vp ON vp.course_id = c.id
                LEFT JOIN (
                    SELECT course_id,
                           COUNT(*) AS quiz_attempts,
                           AVG(score_percent) AS average_score,
                           AVG(CASE WHEN passed = 1 THEN 1 ELSE 0 END) * 100 AS pass_rate
                    FROM quiz_attempt
                    GROUP BY course_id
                ) qa ON qa.course_id = c.id
                ORDER BY started_users DESC, completed_users DESC, c.id DESC
                """;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                stats.add(new CourseStats(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("status"),
                        rs.getInt("artist_id"),
                        rs.getInt("started_users"),
                        rs.getInt("completed_users"),
                        rs.getInt("opened_lessons"),
                        rs.getInt("completed_lessons"),
                        rs.getInt("quiz_attempts"),
                        rs.getDouble("average_score"),
                        rs.getDouble("pass_rate")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Get course stats error: " + e.getMessage());
        }
        return stats;
    }

    public List<QuizStats> getQuizStats() {
        List<QuizStats> stats = new ArrayList<>();
        if (!isReady()) {
            return stats;
        }

        String sql = """
                SELECT q.id AS quiz_id,
                       q.title AS quiz_name,
                       q.passing_score AS passing_score,
                       q.course_id AS course_id,
                       c.title AS course_name,
                       c.artist_id AS artist_id,
                       COUNT(qa.id) AS attempts,
                       COALESCE(SUM(CASE WHEN qa.passed = 1 THEN 1 ELSE 0 END), 0) AS passed_attempts,
                       COALESCE(SUM(CASE WHEN qa.id IS NOT NULL AND qa.passed = 0 THEN 1 ELSE 0 END), 0) AS failed_attempts,
                       COALESCE(AVG(qa.score_percent), 0) AS average_score,
                       COALESCE(MAX(qa.score_percent), 0) AS highest_score,
                       COALESCE(MIN(qa.score_percent), 0) AS lowest_score,
                       COALESCE(AVG(qa.time_spent_sec), 0) AS average_time_sec
                FROM quiz q
                LEFT JOIN course c ON c.id = q.course_id
                LEFT JOIN quiz_attempt qa ON qa.quiz_id = q.id
                GROUP BY q.id, q.title, q.passing_score, q.course_id, c.title, c.artist_id
                ORDER BY attempts DESC, average_score DESC, q.id DESC
                """;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                stats.add(new QuizStats(
                        rs.getInt("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getInt("passing_score"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("artist_id"),
                        rs.getInt("attempts"),
                        rs.getInt("passed_attempts"),
                        rs.getInt("failed_attempts"),
                        rs.getDouble("average_score"),
                        rs.getDouble("highest_score"),
                        rs.getDouble("lowest_score"),
                        rs.getDouble("average_time_sec")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Get quiz stats error: " + e.getMessage());
        }
        return stats;
    }

    public void deleteCourseProgress(int courseId) {
        if (!isReady()) {
            return;
        }
        executeDelete("DELETE FROM quiz_attempt WHERE course_id=?", courseId);
        executeDelete("DELETE FROM course_video_progress WHERE course_id=?", courseId);
        executeDelete("DELETE FROM course_section_progress WHERE course_id=?", courseId);
        executeDelete("DELETE FROM course_progress WHERE course_id=?", courseId);
    }

    public void deleteQuizAttempts(int quizId) {
        if (!isReady()) {
            return;
        }
        executeDelete("DELETE FROM quiz_attempt WHERE quiz_id=?", quizId);
    }

    private void ensureSchema() {
        if (connection == null) {
            return;
        }

        try (Statement st = connection.createStatement()) {
            st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS course_progress (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        course_id INT NOT NULL,
                        started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        last_opened_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        completed_at TIMESTAMP NULL,
                        UNIQUE KEY uq_course_progress_user_course (user_id, course_id),
                        INDEX idx_course_progress_course (course_id),
                        INDEX idx_course_progress_user (user_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
                    """);

            st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS course_section_progress (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        course_id INT NOT NULL,
                        section_id INT NOT NULL,
                        completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        UNIQUE KEY uq_course_section_progress_user_section (user_id, section_id),
                        INDEX idx_course_section_progress_course (course_id),
                        INDEX idx_course_section_progress_user (user_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
                    """);

            st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS course_video_progress (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        course_id INT NOT NULL,
                        section_id INT NOT NULL,
                        video_id INT NOT NULL,
                        opened_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        completed_at TIMESTAMP NULL,
                        UNIQUE KEY uq_course_video_progress_user_video (user_id, video_id),
                        INDEX idx_course_video_progress_course (course_id),
                        INDEX idx_course_video_progress_user (user_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
                    """);

            st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS quiz_attempt (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        quiz_id INT NOT NULL,
                        course_id INT NOT NULL,
                        score_percent INT NOT NULL,
                        earned_points INT NOT NULL,
                        total_points INT NOT NULL,
                        passed TINYINT(1) NOT NULL DEFAULT 0,
                        time_spent_sec INT NOT NULL DEFAULT 0,
                        time_remaining_sec INT NULL,
                        submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        UNIQUE KEY uq_quiz_attempt_user_quiz (user_id, quiz_id),
                        INDEX idx_quiz_attempt_quiz (quiz_id),
                        INDEX idx_quiz_attempt_course (course_id),
                        INDEX idx_quiz_attempt_user (user_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
                    """);
        } catch (SQLException e) {
            System.out.println("Learning progress schema error: " + e.getMessage());
        }
    }

    private Set<Integer> loadIdSet(String sql, int userId, int courseId) {
        Set<Integer> ids = new HashSet<>();
        if (!isReady()) {
            return ids;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Load progress ids error: " + e.getMessage());
        }
        return ids;
    }

    private QuizAttemptSnapshot mapQuizAttempt(ResultSet rs) throws SQLException {
        Object remaining = rs.getObject("time_remaining_sec");
        Timestamp submittedAt = rs.getTimestamp("submitted_at");
        return new QuizAttemptSnapshot(
                rs.getInt("user_id"),
                rs.getInt("quiz_id"),
                rs.getInt("course_id"),
                rs.getInt("score_percent"),
                rs.getInt("earned_points"),
                rs.getInt("total_points"),
                rs.getBoolean("passed"),
                rs.getLong("time_spent_sec"),
                remaining == null ? -1 : rs.getLong("time_remaining_sec"),
                submittedAt == null ? null : submittedAt.toLocalDateTime()
        );
    }

    private void executeDelete(String sql, int id) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete learning progress error: " + e.getMessage());
        }
    }

    private boolean isReady() {
        return connection != null;
    }

    public static final class CourseProgressSnapshot {
        private boolean started;
        private boolean courseCompleted;
        private QuizAttemptSnapshot quizAttempt;
        private final Set<Integer> openedVideoIds = new HashSet<>();
        private final Set<Integer> completedVideoIds = new HashSet<>();
        private final Set<Integer> completedSectionIds = new HashSet<>();

        public boolean isStarted() {
            return started;
        }

        public boolean isCourseCompleted() {
            return courseCompleted;
        }

        public QuizAttemptSnapshot getQuizAttempt() {
            return quizAttempt;
        }

        public Set<Integer> getOpenedVideoIds() {
            return openedVideoIds;
        }

        public Set<Integer> getCompletedVideoIds() {
            return completedVideoIds;
        }

        public Set<Integer> getCompletedSectionIds() {
            return completedSectionIds;
        }
    }

    public static final class QuizAttemptSnapshot {
        private final int userId;
        private final int quizId;
        private final int courseId;
        private final int scorePercent;
        private final int earnedPoints;
        private final int totalPoints;
        private final boolean passed;
        private final long timeSpentSeconds;
        private final long timeRemainingSeconds;
        private final LocalDateTime submittedAt;

        public QuizAttemptSnapshot(int userId, int quizId, int courseId, int scorePercent,
                                   int earnedPoints, int totalPoints, boolean passed,
                                   long timeSpentSeconds, long timeRemainingSeconds,
                                   LocalDateTime submittedAt) {
            this.userId = userId;
            this.quizId = quizId;
            this.courseId = courseId;
            this.scorePercent = scorePercent;
            this.earnedPoints = earnedPoints;
            this.totalPoints = totalPoints;
            this.passed = passed;
            this.timeSpentSeconds = timeSpentSeconds;
            this.timeRemainingSeconds = timeRemainingSeconds;
            this.submittedAt = submittedAt;
        }

        public int getUserId() {
            return userId;
        }

        public int getQuizId() {
            return quizId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getScorePercent() {
            return scorePercent;
        }

        public int getEarnedPoints() {
            return earnedPoints;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public boolean isPassed() {
            return passed;
        }

        public long getTimeSpentSeconds() {
            return timeSpentSeconds;
        }

        public long getTimeRemainingSeconds() {
            return timeRemainingSeconds;
        }

        public LocalDateTime getSubmittedAt() {
            return submittedAt;
        }
    }

    public static final class OverallLearningStats {
        private final int totalCourses;
        private final int publishedCourses;
        private final int startedCourses;
        private final int completedCourses;
        private final int quizAttempts;
        private final double averageScore;
        private final double passRate;

        public OverallLearningStats(int totalCourses, int publishedCourses, int startedCourses,
                                    int completedCourses, int quizAttempts,
                                    double averageScore, double passRate) {
            this.totalCourses = totalCourses;
            this.publishedCourses = publishedCourses;
            this.startedCourses = startedCourses;
            this.completedCourses = completedCourses;
            this.quizAttempts = quizAttempts;
            this.averageScore = averageScore;
            this.passRate = passRate;
        }

        public int getTotalCourses() {
            return totalCourses;
        }

        public int getPublishedCourses() {
            return publishedCourses;
        }

        public int getStartedCourses() {
            return startedCourses;
        }

        public int getCompletedCourses() {
            return completedCourses;
        }

        public int getQuizAttempts() {
            return quizAttempts;
        }

        public double getAverageScore() {
            return averageScore;
        }

        public double getPassRate() {
            return passRate;
        }
    }

    public static final class CourseStats {
        private final int courseId;
        private final String courseName;
        private final String status;
        private final int artistId;
        private final int startedUsers;
        private final int completedUsers;
        private final int openedLessons;
        private final int completedLessons;
        private final int quizAttempts;
        private final double averageScore;
        private final double passRate;

        public CourseStats(int courseId, String courseName, String status, int artistId,
                           int startedUsers, int completedUsers, int openedLessons,
                           int completedLessons, int quizAttempts, double averageScore,
                           double passRate) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.status = status;
            this.artistId = artistId;
            this.startedUsers = startedUsers;
            this.completedUsers = completedUsers;
            this.openedLessons = openedLessons;
            this.completedLessons = completedLessons;
            this.quizAttempts = quizAttempts;
            this.averageScore = averageScore;
            this.passRate = passRate;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getStatus() {
            return status;
        }

        public int getArtistId() {
            return artistId;
        }

        public int getStartedUsers() {
            return startedUsers;
        }

        public int getCompletedUsers() {
            return completedUsers;
        }

        public int getOpenedLessons() {
            return openedLessons;
        }

        public int getCompletedLessons() {
            return completedLessons;
        }

        public int getQuizAttempts() {
            return quizAttempts;
        }

        public double getAverageScore() {
            return averageScore;
        }

        public double getPassRate() {
            return passRate;
        }

        public String getLessonProgressText() {
            return completedLessons + " / " + openedLessons;
        }

        public String getAverageScoreText() {
            return quizAttempts == 0 ? "--" : Math.round(averageScore) + "%";
        }

        public String getPassRateText() {
            return quizAttempts == 0 ? "--" : Math.round(passRate) + "%";
        }
    }

    public static final class QuizStats {
        private final int quizId;
        private final String quizName;
        private final int passingScore;
        private final int courseId;
        private final String courseName;
        private final int artistId;
        private final int attempts;
        private final int passedAttempts;
        private final int failedAttempts;
        private final double averageScore;
        private final double highestScore;
        private final double lowestScore;
        private final double averageTimeSeconds;

        public QuizStats(int quizId, String quizName, int passingScore, int courseId,
                         String courseName, int artistId, int attempts, int passedAttempts,
                         int failedAttempts, double averageScore, double highestScore,
                         double lowestScore, double averageTimeSeconds) {
            this.quizId = quizId;
            this.quizName = quizName;
            this.passingScore = passingScore;
            this.courseId = courseId;
            this.courseName = courseName;
            this.artistId = artistId;
            this.attempts = attempts;
            this.passedAttempts = passedAttempts;
            this.failedAttempts = failedAttempts;
            this.averageScore = averageScore;
            this.highestScore = highestScore;
            this.lowestScore = lowestScore;
            this.averageTimeSeconds = averageTimeSeconds;
        }

        public int getQuizId() {
            return quizId;
        }

        public String getQuizName() {
            return quizName;
        }

        public int getPassingScore() {
            return passingScore;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName == null || courseName.isBlank() ? "Course #" + courseId : courseName;
        }

        public int getArtistId() {
            return artistId;
        }

        public int getAttempts() {
            return attempts;
        }

        public int getPassedAttempts() {
            return passedAttempts;
        }

        public int getFailedAttempts() {
            return failedAttempts;
        }

        public double getAverageScore() {
            return averageScore;
        }

        public double getHighestScore() {
            return highestScore;
        }

        public double getLowestScore() {
            return lowestScore;
        }

        public double getAverageTimeSeconds() {
            return averageTimeSeconds;
        }

        public String getAverageScoreText() {
            return attempts == 0 ? "--" : Math.round(averageScore) + "%";
        }

        public String getScoreRangeText() {
            return attempts == 0 ? "--" : Math.round(lowestScore) + "% - " + Math.round(highestScore) + "%";
        }

        public String getAverageTimeText() {
            if (attempts == 0) {
                return "--";
            }
            int totalSeconds = (int) Math.round(averageTimeSeconds);
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            return minutes == 0 ? seconds + " sec" : String.format("%d:%02d", minutes, seconds);
        }
    }
}
