package Gui;

import DatabaseConnection.DatabaseDrivers;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;


public class MainGui extends Application implements EventHandler<ActionEvent> {
    private final MainGuiController submitController = new MainGuiController();

    private ComboBox<DatabaseDrivers> possibleDatabases;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        createDatabaseList();

        stage.setTitle("Database Information");
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(possibleDatabases);

        Scene scene = new Scene(layout, 200, 200);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        GridPane layout = new GridPane();

        switch (possibleDatabases.getValue()) {
            case MYSQL, ORACLE -> createLayoutOnDatabaseChange(layout, "Hostname:", "Port:", "Database Name:");
            case SQLSERVER -> createLayoutOnDatabaseChange(layout, "Server Name:", "Instance Name:", "Database Name:");
            default -> createLayoutOnDatabaseChange(layout, "NoInfo:", "NoInfo:", "NoInfo:");
        }

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createLayoutOnDatabaseChange(GridPane layout, String firstInformation, String secondInformation, String thirdInformation) {
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(20);
        layout.setVgap(20);

        layout.add(possibleDatabases, 0, 0, 2, 1);

        Text message = new Text("Enter Database Information");
        layout.add(message, 0, 1, 2, 1);

        TextField input1 = new TextField();
        layout.add(new Label(firstInformation), 0, 2);
        layout.add(input1, 1, 2);

        TextField input2 = new TextField();
        layout.add(new Label(secondInformation), 0, 3);
        layout.add(input2, 1, 3);

        TextField input3 = new TextField();
        layout.add(new Label(thirdInformation), 0, 4);
        layout.add(input3, 1, 4);

        TextField username= new TextField();
        layout.add(new Label("Username"), 0, 5);
        layout.add(username, 1, 5);

        TextField password = new TextField();
        layout.add(new Label("Password"), 0, 6);
        layout.add(password, 1, 6);

        Button button = new Button();
        button.setText("Submit");
        button.setOnAction(event -> submitController.submit(input1.getText(), input2.getText(), input3.getText(), username.getText(), password.getText()));
        layout.add(button, 0, 7, 2, 1);

        return layout;
    }

    private void createDatabaseList() {
        ObservableList<DatabaseDrivers> options = FXCollections.observableArrayList();

        options.addAll(Arrays.asList(DatabaseDrivers.values()));

        possibleDatabases = new ComboBox<>();
        possibleDatabases.setItems(options);
        possibleDatabases.setOnAction(this);
        possibleDatabases.setVisibleRowCount(5);
    }
}
