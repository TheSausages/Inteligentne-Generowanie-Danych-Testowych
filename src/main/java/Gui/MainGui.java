package Gui;

import DatabaseConnection.SupportedDatabases;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
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


public class MainGui extends Application {
    private final MainGuiController submitController = new MainGuiController();

    private ComboBox<SupportedDatabases> possibleDatabases;

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

    public void databaseChange() {
        GridPane layout = new GridPane();

        createLayoutOnDatabaseChange(layout);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void createLayoutOnDatabaseChange(GridPane layout) {
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(20);
        layout.setVgap(20);

        layout.add(possibleDatabases, 0, 0, 2, 1);
        GridPane.setHalignment(possibleDatabases, HPos.CENTER);


        Text enter = new Text("Enter Database Information");
        layout.add(enter, 0, 1, 2, 1);
        GridPane.setHalignment(enter, HPos.CENTER);

        Text startMessage = new Text("* mean the information must be entered!");
        layout.add(startMessage, 0, 2, 2, 1);
        GridPane.setHalignment(startMessage, HPos.CENTER);

        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        switch (possibleDatabases.getValue()) {
            case ORACLE -> {
                label1.setText("Hostname ( default: localhost ):");
                label2.setText("Port ( default: 1521 ):");
                label3.setText("Database Name *:");
            }

            case MYSQL -> {
                label1.setText("Hostname ( default: localhost ):");
                label2.setText("Port ( default: 3306 ):");
                label3.setText("Database Name *:");
            }

            case SQLSERVER -> {
                label1.setText("Server Name ( default - Desk. name )");
                label2.setText("Instance Name ( default: sqlexpress ):");
                label3.setText("Database Name *:");
            }
        }

        TextField input1 = new TextField();
        layout.add(label1, 0, 3);
        layout.add(input1, 1, 3);

        TextField input2 = new TextField();
        layout.add(label2, 0, 4);
        layout.add(input2, 1, 4);

        TextField input3 = new TextField();
        layout.add(label3, 0, 5);
        layout.add(input3, 1, 5);

        TextField username= new TextField();
        layout.add(new Label("Username *:"), 0, 6);
        layout.add(username, 1, 6);

        TextField password = new TextField();
        layout.add(new Label("Password *:"), 0, 7);
        layout.add(password, 1, 7);

        Button button = new Button();
        button.setText("Submit");
        button.setOnAction(event -> submitController.submit(possibleDatabases.getValue() ,input1.getText(), input2.getText(), input3.getText(), username.getText(), password.getText()));
        layout.add(button, 0, 8, 2, 1);
        GridPane.setHalignment(button, HPos.CENTER);
    }


    private void createDatabaseList() {
        ObservableList<SupportedDatabases> options = FXCollections.observableArrayList();

        options.addAll(Arrays.asList(SupportedDatabases.values()));

        possibleDatabases = new ComboBox<>();
        possibleDatabases.setItems(options);
        possibleDatabases.setOnAction(event -> this.databaseChange());
        possibleDatabases.setVisibleRowCount(5);
    }
}
