package com.pegasus.services;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.pegasus.entities.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CertificatePdfService {
    public Path generateCertificate(
            Course course,
            String learnerName,
            int quizScore,
            int passingScore,
            long timeSpentSeconds,
            long timeRemainingSeconds,
            List<String> skills,
            List<String> recommendations
    ) throws IOException {
        Path outputDir = Path.of("certificates");
        Files.createDirectories(outputDir);

        String fileName = "pegasus-certificate-" + sanitizeFileName(course.getTitle()) + "-" + System.currentTimeMillis() + ".pdf";
        Path outputPath = outputDir.resolve(fileName);

        try (PdfWriter writer = new PdfWriter(outputPath.toString());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4.rotate())) {

            document.setMargins(44, 54, 44, 54);
            addCertificateHeader(document);
            addCompletionBlock(document, course, learnerName);
            addQuizReport(document, quizScore, passingScore, timeSpentSeconds, timeRemainingSeconds);
            addSkillsAndRecommendations(document, skills, recommendations);
            addFooter(document);
        }

        return outputPath;
    }

    private void addCertificateHeader(Document document) {
        document.add(new Paragraph("PEGASUS")
                .setFontSize(34)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(17, 17, 17)));

        document.add(new Paragraph("Certificate of Completion")
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(151, 112, 20)));

        SolidLine line = new SolidLine(1.4f);
        line.setColor(new DeviceRgb(244, 196, 48));
        document.add(new LineSeparator(line).setMarginTop(10).setMarginBottom(20));
    }

    private void addCompletionBlock(Document document, Course course, String learnerName) {
        document.add(new Paragraph("Pegasus certifies that")
                .setFontSize(15)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(75, 85, 99)));

        document.add(new Paragraph(learnerName)
                .setFontSize(30)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(17, 17, 17))
                .setMarginTop(4));

        document.add(new Paragraph("completed")
                .setFontSize(15)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(75, 85, 99))
                .setMarginTop(4));

        document.add(new Paragraph(safe(course.getTitle()))
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(new DeviceRgb(17, 17, 17))
                .setMarginTop(4)
                .setMarginBottom(18));
    }

    private void addQuizReport(Document document, int quizScore, int passingScore, long timeSpentSeconds, long timeRemainingSeconds) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1}))
                .useAllAvailableWidth()
                .setMarginBottom(18);

        table.addCell(metricCell("Quiz score", quizScore >= 0 ? quizScore + "%" : "Not submitted"));
        table.addCell(metricCell("Passing score", passingScore + "%"));
        table.addCell(metricCell("Time spent", formatDuration(timeSpentSeconds)));
        table.addCell(metricCell("Time left", timeRemainingSeconds >= 0 ? formatDuration(timeRemainingSeconds) : "No limit"));

        document.add(table);
    }

    private Cell metricCell(String title, String value) {
        return new Cell()
                .setBorder(new SolidBorder(new DeviceRgb(229, 231, 235), 1))
                .setPadding(12)
                .add(new Paragraph(title).setFontSize(10).setFontColor(new DeviceRgb(100, 116, 139)))
                .add(new Paragraph(value).setFontSize(17).setBold().setFontColor(new DeviceRgb(17, 17, 17)));
    }

    private void addSkillsAndRecommendations(Document document, List<String> skills, List<String> recommendations) {
        document.add(new Paragraph("Skill profile")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(4));
        document.add(new Paragraph(String.join(" | ", skills))
                .setFontSize(12)
                .setFontColor(new DeviceRgb(31, 41, 55))
                .setMarginBottom(14));

        document.add(new Paragraph("Recommended next steps")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(4));

        for (String recommendation : recommendations) {
            document.add(new Paragraph("- " + recommendation)
                    .setFontSize(11)
                    .setFontColor(new DeviceRgb(51, 65, 85))
                    .setMarginBottom(2));
        }
    }

    private void addFooter(Document document) {
        document.add(new Paragraph("Issued on " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY)
                .setMarginTop(18));
    }

    private String formatDuration(long totalSeconds) {
        if (totalSeconds < 0) {
            return "No limit";
        }
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + "m " + seconds + "s";
    }

    private String sanitizeFileName(String value) {
        String safeValue = value == null || value.isBlank() ? "course" : value.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        return safeValue.replaceAll("(^-|-$)", "");
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "this course" : value;
    }
}
