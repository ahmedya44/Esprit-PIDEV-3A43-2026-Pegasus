package tn.esprit.pegasus.services;

import tn.esprit.pegasus.entities.Course;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class CourseCategoryClassifier {
    public static final String OTHERS_CATEGORY = "Others";

    private static final Map<String, String[]> CATEGORY_KEYWORDS = new LinkedHashMap<>();

    static {
        CATEGORY_KEYWORDS.put("Music", new String[]{
                "music", "piano", "guitar", "violin", "drum", "drums", "sing", "singing",
                "vocal", "song", "songs", "beat", "melody", "rhythm", "audio", "dj",
                "fl studio", "ableton", "music production", "keyboard"
        });
        CATEGORY_KEYWORDS.put("Design", new String[]{
                "design", "photoshop", "illustrator", "figma", "branding", "ui", "ux",
                "graphic", "poster", "logo", "typography", "layout", "adobe", "canva"
        });
        CATEGORY_KEYWORDS.put("Photography", new String[]{
                "photo", "photography", "camera", "portrait", "lightroom", "lens",
                "shooting", "retouch", "retouching", "exposure", "composition"
        });
        CATEGORY_KEYWORDS.put("Animation", new String[]{
                "animation", "animate", "motion", "2d", "3d animation", "after effects",
                "storyboard", "frame", "frames", "rigging"
        });
        CATEGORY_KEYWORDS.put("Illustration", new String[]{
                "illustration", "illustrator drawing", "sketch", "drawing", "paint",
                "painting", "digital painting", "character design", "concept art", "art",
                "watercolor", "coloring"
        });
        CATEGORY_KEYWORDS.put("3D Modeling", new String[]{
                "3d", "modeling", "modelling", "blender", "maya", "zbrush", "sculpt",
                "sculpting", "render", "rendering", "topology", "texturing"
        });
        CATEGORY_KEYWORDS.put("Video Editing", new String[]{
                "video", "editing", "premiere", "davinci", "final cut", "montage",
                "cinematic", "film", "filmmaking", "color grading"
        });
        CATEGORY_KEYWORDS.put("Programming", new String[]{
                "java", "python", "programming", "coding", "development", "web dev",
                "javascript", "spring", "database", "api", "software","html", "css", 
                "react", "angular", "vue", "c++", "c#", "ruby", "php"
        });
        CATEGORY_KEYWORDS.put("Business", new String[]{
                "business", "marketing", "sales", "startup", "entrepreneur", "finance",
                "branding strategy", "management", "productivity"
        });
    }

    private CourseCategoryClassifier() {
    }

    public static String classify(Course course) {
        if (course == null) {
            return OTHERS_CATEGORY;
        }

        String searchableText = (safe(course.getTitle()) + " " + safe(course.getDescription()))
                .toLowerCase(Locale.ROOT);

        String bestCategory = OTHERS_CATEGORY;
        int bestScore = 0;

        for (Map.Entry<String, String[]> entry : CATEGORY_KEYWORDS.entrySet()) {
            int score = computeScore(searchableText, entry.getValue());
            if (score > bestScore) {
                bestScore = score;
                bestCategory = entry.getKey();
            }
        }

        return bestScore == 0 ? OTHERS_CATEGORY : bestCategory;
    }

    private static int computeScore(String searchableText, String[] keywords) {
        int score = 0;
        for (String keyword : keywords) {
            if (searchableText.contains(keyword.toLowerCase(Locale.ROOT))) {
                score++;
            }
        }
        return score;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
