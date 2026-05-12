package com.pegasus.services;

import com.pegasus.entities.Course;
import com.pegasus.services.moderation.ModerationApiClient;
import com.pegasus.services.moderation.ModerationResponseParser;
import com.pegasus.services.moderation.ModerationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ModerationService {
    private final ModerationApiClient apiClient;
    private final ModerationResponseParser responseParser;
    private static final List<LocalRule> LOCAL_BLOCK_RULES = List.of(
            new LocalRule("Self-harm content detected", Pattern.compile("\\b(kill\\s+(yourself|myself|my\\s*self|ur\\s*self|your\\s*self)|suicide|self[-\\s]?harm|end\\s+(my|your)\\s+life)\\b", Pattern.CASE_INSENSITIVE)),
            new LocalRule("Violence or harm instructions detected", Pattern.compile("\\b(how\\s+to\\s+kill|kill\\s+(someone|somebody|a\\s+person|people|him|her|them)|murder|assassinate|stab|poison|strangle)\\b", Pattern.CASE_INSENSITIVE)),
            new LocalRule("Weapon or attack instructions detected", Pattern.compile("\\b(make\\s+(a\\s+)?bomb|build\\s+(a\\s+)?bomb|explosive|school\\s+shooting|mass\\s+shooting)\\b", Pattern.CASE_INSENSITIVE))
    );

    public ModerationService() {
        this.apiClient = new ModerationApiClient();
        this.responseParser = new ModerationResponseParser();
    }

    public ModerationResult analyzeCourse(Course course) throws Exception {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        String payload = buildModerationPayload(course.getTitle(), course.getDescription());
        ModerationResult localResult = analyzeWithLocalRules(payload);
        if (!localResult.isSafe()) {
            return localResult;
        }
        String rawResponse = apiClient.analyzeText(payload);
        return responseParser.parse(rawResponse);
    }

    private ModerationResult analyzeWithLocalRules(String payload) {
        String normalized = payload == null ? "" : payload.toLowerCase(Locale.ROOT);
        List<String> issues = new ArrayList<>();
        for (LocalRule rule : LOCAL_BLOCK_RULES) {
            if (rule.pattern.matcher(normalized).find()) {
                issues.add(rule.issue);
            }
        }
        if (issues.isEmpty()) {
            return ModerationResult.safe("local-rules: no issues");
        }
        return ModerationResult.unsafe(issues, "local-rules: " + String.join(", ", issues));
    }

    private String buildModerationPayload(String title, String description) {
        String cleanTitle = title == null ? "" : title.trim();
        String cleanDescription = description == null ? "" : description.trim();
        return "Course title: " + cleanTitle + "\nCourse description: " + cleanDescription;
    }

    private record LocalRule(String issue, Pattern pattern) {
    }
}
