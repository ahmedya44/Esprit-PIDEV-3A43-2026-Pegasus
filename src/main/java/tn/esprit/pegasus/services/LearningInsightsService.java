package tn.esprit.pegasus.services;

import tn.esprit.pegasus.entities.Course;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LearningInsightsService {
    public List<String> inferSkills(Course course, int quizScore) {
        Set<String> skills = new LinkedHashSet<>();
        String category = CourseCategoryClassifier.classify(course);
        String text = searchableCourseText(course);

        addCategorySkills(skills, category);
        addKeywordSkills(skills, text);

        if (quizScore >= 85) {
            skills.add("Portfolio-ready execution");
        } else if (quizScore >= 0) {
            skills.add("Practice and review");
        }

        if (skills.isEmpty()) {
            skills.add("Creative foundations");
        }
        return new ArrayList<>(skills);
    }

    public List<String> recommendCourses(Course currentCourse, List<Course> availableCourses, int quizScore) {
        List<String> recommendations = new ArrayList<>();
        String currentCategory = CourseCategoryClassifier.classify(currentCourse);

        for (Course candidate : availableCourses) {
            if (currentCourse != null && candidate.getId() == currentCourse.getId()) {
                continue;
            }

            String candidateCategory = CourseCategoryClassifier.classify(candidate);
            if (candidateCategory.equals(currentCategory) && recommendations.size() < 2) {
                recommendations.add("Try " + candidate.getTitle() + " next to deepen your " + currentCategory + " skills.");
            }
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Try another creative course next to expand your portfolio range.");
        }

        if (quizScore >= 85) {
            recommendations.add("You seem strong here. Move toward an advanced project or portfolio piece.");
        } else if (quizScore >= 0) {
            recommendations.add("Review the lesson notes, then retake practice around the weakest concepts.");
        } else {
            recommendations.add("Complete the quiz to unlock more precise recommendations.");
        }

        return recommendations;
    }

    private void addCategorySkills(Set<String> skills, String category) {
        switch (category) {
            case "Illustration" -> {
                skills.add("Illustration");
                skills.add("Digital Painting");
                skills.add("Color Theory");
            }
            case "Design" -> {
                skills.add("Graphic Design");
                skills.add("Photoshop");
                skills.add("Visual Composition");
            }
            case "Animation" -> {
                skills.add("Animation");
                skills.add("Storyboarding");
                skills.add("Motion Design");
            }
            case "Photography" -> {
                skills.add("Photography");
                skills.add("Lighting");
                skills.add("Photo Retouching");
            }
            case "3D Modeling" -> {
                skills.add("3D Modeling");
                skills.add("Texturing");
                skills.add("Rendering");
            }
            case "Programming" -> {
                skills.add("Programming");
                skills.add("Problem Solving");
                skills.add("API Integration");
            }
            default -> skills.add("Creative Learning");
        }
    }

    private void addKeywordSkills(Set<String> skills, String text) {
        if (text.contains("fantasy") || text.contains("character")) {
            skills.add("Fantasy Character Design");
        }
        if (text.contains("photoshop")) {
            skills.add("Photoshop");
        }
        if (text.contains("painting") || text.contains("paint")) {
            skills.add("Digital Painting");
        }
        if (text.contains("color")) {
            skills.add("Color Theory");
        }
        if (text.contains("concept art")) {
            skills.add("Concept Art");
        }
    }

    private String searchableCourseText(Course course) {
        if (course == null) {
            return "";
        }
        return ((course.getTitle() == null ? "" : course.getTitle()) + " "
                + (course.getDescription() == null ? "" : course.getDescription()))
                .toLowerCase(Locale.ROOT);
    }
}
