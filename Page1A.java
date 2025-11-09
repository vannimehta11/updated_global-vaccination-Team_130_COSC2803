package app;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Page1A extends BorderPane {
    public Page1A() {
        setPadding(new Insets(16));

        Label title = new Label("Global Vaccination — A (Mission)");
        title.getStyleClass().add("title");
        setTop(title);

        TextArea ta = new TextArea(
            "Goal: Help people make sense of vaccination coverage without jargon.\n\n" +
            "How to use:\n" +
            "  1) Go to 2A tab to filter by Year / Disease / Region and see coverage tables.\n" +
            "  2) Go to 3A tab to compare countries against a herd-immunity threshold.\n" +
            "  3) Export tables to CSV for reporting.\n\n" +
            "Threshold defaults (illustrative): Measles 95%, Polio 90%, DTP3 90%.\n" +
            "Missing values are surfaced and excluded from averages."
        );
        ta.setWrapText(true);
        ta.setEditable(false);
        ta.getStyleClass().add("mono");
        setCenter(ta);

        HBox footer = new HBox();
        footer.setPadding(new Insets(12, 0, 0, 0));
        footer.getChildren().add(new Label("Personas: NGO Coordinator, School Teacher — plain English + quick exports."));
        setBottom(footer);
    }
}
