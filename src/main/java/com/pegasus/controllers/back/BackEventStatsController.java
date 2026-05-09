package com.pegasus.controllers.back;

import com.pegasus.services.EventStatsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.Map;

public class BackEventStatsController {

    @FXML private Label totalEventsLabel;
    @FXML private Label totalParticipantsLabel;
    @FXML private Label totalSponsorsLabel;
    @FXML private Label totalRevenueLabel;
    
    @FXML private BarChart<String, Number> participantsChart;
    @FXML private BarChart<Number, String> packsChart;
    @FXML private PieChart venuePieChart;
    @FXML private CategoryAxis eventXAxis;

    @FXML private VBox participantsDetailList;
    @FXML private VBox packsDetailList;
    @FXML private VBox venueDetailList;

    private EventStatsService statsService = new EventStatsService();

    @FXML
    public void initialize() {
        refreshStats();
    }

    private void refreshStats() {
        // Totals
        totalEventsLabel.setText(String.valueOf(statsService.getTotalEvents()));
        totalParticipantsLabel.setText(String.valueOf(statsService.getTotalParticipants()));
        totalSponsorsLabel.setText(String.valueOf(statsService.getTotalSponsors()));
        totalRevenueLabel.setText(String.format("%.2f", statsService.getTotalRevenue()));

        // 1. Participants Chart (Vertical)
        XYChart.Series<String, Number> pSeries = new XYChart.Series<>();
        Map<String, Integer> topEvents = statsService.getTopEventsByParticipants();
        participantsDetailList.getChildren().removeIf(node -> node instanceof HBox);
        
        topEvents.forEach((title, count) -> {
            pSeries.getData().add(new XYChart.Data<>(title, count));
            participantsDetailList.getChildren().add(createDetailItem(title, String.valueOf(count), "#0ea5e9"));
        });
        participantsChart.getData().setAll(pSeries);

        // 2. Packs Chart (Horizontal)
        XYChart.Series<Number, String> packSeries = new XYChart.Series<>();
        Map<String, Integer> packs = statsService.getPacksPopularity();
        packsDetailList.getChildren().removeIf(node -> node instanceof HBox);
        
        packs.forEach((name, count) -> {
            packSeries.getData().add(new XYChart.Data<>(count, name));
            packsDetailList.getChildren().add(createDetailItem(name, count + " sold", "#6366f1"));
        });
        packsChart.getData().setAll(packSeries);

        // 3. Venue Pie Chart
        venuePieChart.getData().clear();
        venueDetailList.getChildren().clear();
        Map<String, Integer> venues = statsService.getEventsByLieu();
        venues.forEach((lieu, count) -> {
            venuePieChart.getData().add(new PieChart.Data(lieu, count));
            venueDetailList.getChildren().add(createDetailItem(lieu, count + " events", "#f97316"));
        });

        // Styling and Labels
        Platform.runLater(() -> {
            String[] colors = {"#0ea5e9", "#f97316", "#8b5cf6", "#10b981", "#f43f5e"};
            int i = 0;
            for (XYChart.Data<String, Number> data : pSeries.getData()) {
                Node bar = data.getNode();
                if (bar != null) bar.setStyle("-fx-bar-fill: " + colors[i % colors.length] + ";");
                i++;
            }
            for (XYChart.Data<Number, String> data : packSeries.getData()) {
                Node bar = data.getNode();
                if (bar != null) bar.setStyle("-fx-bar-fill: #6366f1;");
            }
            
            // Re-apply data labels with better positioning
            addLabelsToVerticalChart(participantsChart);
            addLabelsToHorizontalChart(packsChart);
        });
    }

    private void addLabelsToVerticalChart(BarChart<String, Number> chart) {
        for (XYChart.Series<String, Number> series : chart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    Text text = new Text(data.getYValue().toString());
                    text.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #1e293b;");
                    ((javafx.scene.Group) node.getParent()).getChildren().add(text);
                    node.boundsInParentProperty().addListener((obs, old, n) -> {
                        text.setLayoutX(n.getMinX() + (n.getWidth() / 2) - (text.getLayoutBounds().getWidth() / 2));
                        text.setLayoutY(n.getMinY() - 5);
                    });
                }
            }
        }
    }

    private void addLabelsToHorizontalChart(BarChart<Number, String> chart) {
        for (XYChart.Series<Number, String> series : chart.getData()) {
            for (XYChart.Data<Number, String> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    Text text = new Text(data.getYValue() + " (" + data.getXValue() + ")");
                    text.setStyle("-fx-font-size: 11px; -fx-font-weight: 900; -fx-fill: white;");
                    ((javafx.scene.Group) node.getParent()).getChildren().add(text);
                    node.boundsInParentProperty().addListener((obs, old, n) -> {
                        // Place inside the bar, left-aligned with padding
                        text.setLayoutX(n.getMinX() + 15); 
                        text.setLayoutY(n.getMinY() + (n.getHeight() / 2) + (text.getLayoutBounds().getHeight() / 4));
                        
                        // Ensure text doesn't overflow the bar if it's too short
                        if (text.getLayoutBounds().getWidth() > n.getWidth() - 20) {
                            text.setOpacity(0.8); // Subtle hint if overflowing
                        }
                    });
                }
            }
        }
    }

    private HBox createDetailItem(String label, String value, String color) {
        HBox hbox = new HBox(12);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setMinWidth(220);
        
        Circle dot = new Circle(4, Color.web(color));
        
        Label lblName = new Label(label);
        lblName.setStyle("-fx-font-size: 13; -fx-font-weight: 600; -fx-text-fill: #334155;");
        lblName.setWrapText(true);
        lblName.setMaxWidth(140);
        HBox.setHgrow(lblName, Priority.ALWAYS);

        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 13; -fx-font-weight: 800; -fx-text-fill: #1e293b;");
        lblValue.setMinWidth(Region.USE_PREF_SIZE);

        hbox.getChildren().addAll(dot, lblName, lblValue);
        return hbox;
    }
}
