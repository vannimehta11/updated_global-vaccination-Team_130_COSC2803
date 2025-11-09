package app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.SplitPane;

import java.util.*;
import java.util.stream.Collectors;

public class Page2A extends BorderPane {
    private ComboBox<Integer> yearBox;
    private ComboBox<String> diseaseBox;
    private ComboBox<String> regionBox;
    private TextField searchField;
    private TableView<java.util.List<String>> countryTable;
    private TableView<java.util.List<String>> regionSummary;

    public Page2A() {
        setPadding(new Insets(16));

        FlowPane controls = new FlowPane(10, 10);
        controls.getStyleClass().add("toolbar");

        yearBox = new ComboBox<>(FXCollections.observableArrayList(Data.YEARS));
        yearBox.getSelectionModel().select(Integer.valueOf(2023));
        diseaseBox = new ComboBox<>(FXCollections.observableArrayList(Data.DISEASES));
        diseaseBox.getSelectionModel().select("Measles");

        java.util.List<String> regionsPlusAll = new java.util.ArrayList<>();
        regionsPlusAll.add("All");
        regionsPlusAll.addAll(Arrays.asList(Data.REGIONS));
        regionBox = new ComboBox<>(FXCollections.observableArrayList(regionsPlusAll));
        regionBox.getSelectionModel().select("All");

        searchField = new TextField();
        searchField.setPromptText("Search country…");

        Button apply = new Button("Apply");
        Button export = new Button("Export Countries CSV");

        controls.getChildren().addAll(
            labelWrap("Year:"), yearBox,
            labelWrap("Disease:"), diseaseBox,
            labelWrap("Region:"), regionBox,
            labelWrap("Search:"), searchField,
            apply, export
        );
        setTop(controls);

        countryTable = makeTable(new String[]{"Country", "Region", "Coverage (%)", "Status"});
        regionSummary = makeTable(new String[]{"Region", "Avg coverage (%)", "Rows (missing)"});

        SplitPane split = new SplitPane(countryTable, regionSummary);
        split.setDividerPositions(0.6);
        setCenter(split);

        TextArea note = new TextArea("Footnote: Missing values are displayed as ‘Missing’ and excluded from averages.");
        note.setEditable(false);
        note.setWrapText(true);
        setBottom(note);

        apply.setOnAction(e -> refresh());
        export.setOnAction(e -> CsvExporter.exportTable(countryTable, getScene().getWindow(),
                "2A_" + diseaseBox.getValue() + "_" + yearBox.getValue() + ".csv"));

        refresh();
    }

    private TableView<java.util.List<String>> makeTable(String[] headers) {
        TableView<java.util.List<String>> tv = new TableView<>();
        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<java.util.List<String>, String> col = new TableColumn<>(headers[i]);
            col.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(
                colIndex < param.getValue().size() && param.getValue().get(colIndex) != null
                    ? param.getValue().get(colIndex) : ""));
            col.setPrefWidth(180);
            tv.getColumns().add(col);
        }
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tv;
    }

    private Node labelWrap(String s) {
        Label l = new Label(s);
        l.getStyleClass().add("label");
        return l;
    }

    private void refresh() {
        int year = yearBox.getValue();
        String disease = diseaseBox.getValue();
        String region = regionBox.getValue();
        String query = searchField.getText();

        java.util.List<Row> filtered = Data.filter(year, disease, region, query);

        double threshold = Data.herdThreshold(disease);
        java.util.List<java.util.List<String>> rows = new java.util.ArrayList<>();
        for (Row r : filtered) {
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
            rows.add(java.util.Arrays.asList(r.country, r.region, coverageStr, status));
        }
        countryTable.setItems(FXCollections.observableArrayList(rows));

        java.util.Map<String, java.util.List<Row>> byRegion = filtered.stream()
            .collect(Collectors.groupingBy(r -> r.region));
        java.util.List<java.util.List<String>> summaryRows = new java.util.ArrayList<>();
        for (java.util.Map.Entry<String, java.util.List<Row>> e : byRegion.entrySet()) {
            int count = e.getValue().size();
            int present = 0;
            double sum = 0;
            for (Row r : e.getValue()) {
                if (r.vaccinationRate != null) {
                    present++;
                    sum += r.vaccinationRate;
                }
            }
            String avg = present > 0 ? String.format("%.1f", sum / present) : "—";
            summaryRows.add(java.util.Arrays.asList(e.getKey(), avg, count + " (" + (count - present) + ")"));
        }
        regionSummary.setItems(FXCollections.observableArrayList(summaryRows));
    }
}
