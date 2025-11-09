package app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page3A extends BorderPane {
    private ComboBox<Integer> yearBox;
    private ComboBox<String> diseaseBox;
    private Label thresholdLabel;
    private TableView<List<String>> table;

    public Page3A() {
        setPadding(new Insets(16));

        FlowPane controls = new FlowPane(10, 10);
        controls.getStyleClass().add("toolbar");

        yearBox = new ComboBox<>(FXCollections.observableArrayList(Data.YEARS));
        yearBox.getSelectionModel().select(Integer.valueOf(2023));
        diseaseBox = new ComboBox<>(FXCollections.observableArrayList(Data.DISEASES));
        diseaseBox.getSelectionModel().select("Measles");
        thresholdLabel = new Label("Threshold: —%");

        Button apply = new Button("Apply");
        Button export = new Button("Export CSV");

        controls.getChildren().addAll(new Label("Year:"), yearBox,
                new Label("Disease:"), diseaseBox, thresholdLabel, apply, export);
        setTop(controls);

        table = makeTable(new String[]{"Country", "Region", "Coverage (%)", "Status"});
        setCenter(table);

        TextArea notes = new TextArea(
            "Method: Compare each country's coverage against the fixed threshold for the chosen disease.\n" +
            "Missing values: flagged as 'Missing' and excluded from ranking calculations."
        );
        notes.setEditable(false);
        notes.setWrapText(true);
        setBottom(notes);

        apply.setOnAction(e -> refresh());
        export.setOnAction(e -> CsvExporter.exportTable(table, getScene().getWindow(),
                "3A_" + diseaseBox.getValue() + "_" + yearBox.getValue() + ".csv"));

        refresh();
    }

    private TableView<List<String>> makeTable(String[] headers) {
        TableView<List<String>> tv = new TableView<>();
        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> col = new TableColumn<>(headers[i]);
            col.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(
                colIndex < param.getValue().size() && param.getValue().get(colIndex) != null
                    ? param.getValue().get(colIndex) : ""));
            col.setPrefWidth(180);
            tv.getColumns().add(col);
        }
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tv;
    }

    private void refresh() {
        int year = yearBox.getValue();
        String disease = diseaseBox.getValue();
        double threshold = Data.herdThreshold(disease);
        thresholdLabel.setText("Threshold: " + threshold + "%");

        List<Row> rows = Data.filter(year, disease, "All", "");

        List<List<String>> items = new ArrayList<>();
        for (Row r : rows) {
            String status;
            String coverageStr;
            if (r.vaccinationRate == null) {
                status = "Missing";
                coverageStr = "—";
            } else {
                boolean ok = r.vaccinationRate >= threshold;
                status = ok ? "Meets threshold" : "Below threshold";
                coverageStr = String.valueOf(r.vaccinationRate);
            }
            items.add(Arrays.asList(r.country, r.region, coverageStr, status));
        }
        table.setItems(FXCollections.observableArrayList(items));
    }
}
