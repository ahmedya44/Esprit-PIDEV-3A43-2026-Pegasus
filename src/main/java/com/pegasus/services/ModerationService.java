package com.pegasus.services;

import com.pegasus.entities.Course;
import com.pegasus.services.moderation.ModerationApiClient;
import com.pegasus.services.moderation.ModerationResponseParser;
import com.pegasus.services.moderation.ModerationResult;

public class ModerationService {
    private final ModerationApiClient apiClient;
    private final ModerationResponseParser responseParser;

    public ModerationService() {
        this.apiClient = new ModerationApiClient();
        this.responseParser = new ModerationResponseParser();
    }

    public ModerationResult analyzeCourse(Course course) throws Exception {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        String payload = buildModerationPayload(course.getTitle(), course.getDescription());
        String rawResponse = apiClient.analyzeText(payload);
        return responseParser.parse(rawResponse);
    }

    private String buildModerationPayload(String title, String description) {
        String cleanTitle = title == null ? "" : title.trim();
        String cleanDescription = description == null ? "" : description.trim();
        return "Course title: " + cleanTitle + "\nCourse description: " + cleanDescription;
    }
}
