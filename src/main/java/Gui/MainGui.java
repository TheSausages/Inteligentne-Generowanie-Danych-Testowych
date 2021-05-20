package Gui;

import DatabaseConnection.SupportedDatabases;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Main Gui class
 */
public class MainGui extends Application {
    /**
     * Controller for the Gui. See {@link MainGuiController}
     */
    private final MainGuiController submitController = new MainGuiController();

    /**
     * A Combobox possessing all the supported Databases
     */
    private ComboBox<SupportedDatabases> possibleDatabases;

    /**
     * Stage of the Controller
     */
    private Stage stage;

    /**
     * Method that starts the gui Scene
     * @param primaryStage The primary stage of the Gui
     */
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

    /**
     * Method that rerenders the Stage upon database change
     */
    public void databaseChange() {
        GridPane layout = new GridPane();

        createLayoutOnDatabaseChange(layout);

        Scene scene = new Scene(layout, 770, 420);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that created the Scene depending on the selected database from {@link MainGui#possibleDatabases}
     * @param layout the layout of the scene
     */
    private void createLayoutOnDatabaseChange(GridPane layout) {
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(15);

        layout.getColumnConstraints().add(new ColumnConstraints(200));
        layout.getColumnConstraints().add(new ColumnConstraints(120));
        layout.getColumnConstraints().add(new ColumnConstraints(50));
        layout.getColumnConstraints().add(new ColumnConstraints(200));
        layout.getColumnConstraints().add(new ColumnConstraints(120));

        layout.add(possibleDatabases, 0, 0, 6, 1);
        GridPane.setHalignment(possibleDatabases, HPos.CENTER);

        Text startMessage = new Text("* mean the information must be entered!");
        layout.add(startMessage, 0, 1, 6, 1);
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
                label1.setText("Server Name ( default: Desk. name )");
                label2.setText("Instance Name ( default: sqlexpress ):");
                label3.setText("Database Name *:");
            }
        }

        // Database Info
        Text databaseInformation = new Text("Database Information");
        layout.add(databaseInformation, 0, 3, 2, 1);
        GridPane.setHalignment(databaseInformation, HPos.CENTER);

        TextField hostname = new TextField();
        layout.add(label1, 0, 4);
        layout.add(hostname, 1, 4);

        TextField portOrInstance = new TextField();
        layout.add(label2, 0, 5);
        layout.add(portOrInstance, 1, 5);

        TextField databaseName = new TextField();
        layout.add(label3, 0, 6);
        layout.add(databaseName, 1, 6);

        TextField username= new TextField();
        layout.add(new Label("Username *:"), 0, 7);
        layout.add(username, 1, 7);

        TextField password = new TextField();
        layout.add(new Label("Password *:"), 0, 8);
        layout.add(password, 1, 8);

        //Separator
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(Double.MAX_VALUE);
        layout.add(separator, 2, 3, 1, 6);
        GridPane.setHalignment(separator, HPos.CENTER);

        //Generation Information
        Text generationInformation = new Text("Generation Information");
        layout.add(generationInformation, 3, 3, 2, 1);
        GridPane.setHalignment(generationInformation, HPos.CENTER);

        TextField seed = new TextField();
        layout.add(new Label("Seed:"), 3, 4);
        layout.add(seed, 4, 4);

        TextField locale = new TextField();
        layout.add(new Label("Locale:"), 3, 5);
        layout.add(locale, 4, 5);


        layout.add(new Label("AutoFill *:"), 3, 6, 2, 1);
        ObservableList<String> autoFill = FXCollections.observableArrayList("To SQL file", "Directly to Database");
        ComboBox<String> autoFillBox = new ComboBox<>(autoFill);
        autoFillBox.getSelectionModel().selectFirst();
        layout.add(autoFillBox, 4, 6);

        TextField mappingFile = new TextField();
        layout.add(new Label("Path to Table Mapping File (.json):"), 3, 7);
        layout.add(mappingFile, 4, 7);

        TextField insertFile = new TextField();
        layout.add(new Label("Path to Insert File (.json):"), 3, 8);
        layout.add(insertFile, 4, 8);

        Button button = new Button();
        button.setText("Submit");
        button.setOnAction(event -> submitController.submit(possibleDatabases.getValue() ,hostname.getText(), portOrInstance.getText()
                , databaseName.getText(), username.getText(), password.getText(), seed.getText(), locale.getText(), autoFillBox.getValue(), mappingFile.getText(), insertFile.getText()));
        layout.add(button, 0, 10, 5, 1);
        GridPane.setHalignment(button, HPos.CENTER);
    }

    /**
     * Method that collects all the possible databases from {@link SupportedDatabases} to {@link MainGui#possibleDatabases}
     */
    private void createDatabaseList() {
        ObservableList<SupportedDatabases> options = FXCollections.observableArrayList();

        options.addAll(Arrays.asList(SupportedDatabases.values()));

        possibleDatabases = new ComboBox<>();
        possibleDatabases.setItems(options);
        possibleDatabases.setOnAction(event -> this.databaseChange());
        possibleDatabases.setVisibleRowCount(5);
        possibleDatabases.setPromptText("Select the Database");
    }
}
