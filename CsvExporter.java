package app;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvExporter {
    public static void exportTable(TableView<List<String>> table, Window owner, String defaultName) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName(defaultName);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = chooser.showSaveDialog(owner);
        if (file == null) return;
        try (PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8)) {
            // headers
            for (int c = 0; c < table.getColumns().size(); c++) {
                if (c > 0) pw.print(",");
                pw.print(escape(table.getColumns().get(c).getText()));
            }
            pw.println();
            // rows
            for (List<String> row : table.getItems()) {
                for (int c = 0; c < row.size(); c++) {
                    if (c > 0) pw.print(",");
                    pw.print(escape(row.get(c) == null ? "" : row.get(c)));
                }
                pw.println();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String escape(String s) {
        if (s.contains(",") || s.contains(""") || s.contains("
")) {
            s = s.replace(""", """");
            return """ + s + """;
        }
        return s;
    }
}
