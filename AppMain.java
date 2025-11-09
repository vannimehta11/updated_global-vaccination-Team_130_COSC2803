package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class AppMain extends Application {
    @Override
    public void start(Stage stage) {
        TabPane tabs = new TabPane();

        Tab t1 = new Tab("1A — Mission", new Page1A());
        t1.setClosable(false);
        Tab t2 = new Tab("2A — Shallow Glance", new Page2A());
        t2.setClosable(false);
        Tab t3 = new Tab("3A — Deep Dive", new Page3A());
        t3.setClosable(false);

        tabs.getTabs().addAll(t1, t2, t3);

        Scene scene = new Scene(tabs, 1100, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("Global Vaccination — A (JavaFX + CSS)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
