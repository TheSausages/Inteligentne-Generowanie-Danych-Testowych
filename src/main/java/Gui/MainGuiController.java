package Gui;

import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import GenerateInformation.IntelligentGeneration;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainGuiController {

    public void submit(SupportedDatabases supportedDatabases, String hostnameOrServerName, String portOrInstance, String databaseName
            , String username, String password, String seed, String locale, String autoFill, String mappingFile, String insertFile) {

        boolean autofillBool = (autoFill.equals("Directly to Database"));

        try {
            switch (supportedDatabases) {
                case MYSQL -> new IntelligentGeneration().generateForMySQLDatabase(hostnameOrServerName, portOrInstance, databaseName, username, password, Long.parseLong(seed), locale, mappingFile, insertFile, autofillBool);
                case ORACLE -> new IntelligentGeneration().generateForOracleDatabase(hostnameOrServerName, portOrInstance, databaseName, username, password, Long.parseLong(seed), locale, mappingFile, insertFile, autofillBool);
                case SQLSERVER -> new IntelligentGeneration().generateForSQLServerDatabase(hostnameOrServerName, portOrInstance, databaseName, username, password, Long.parseLong(seed), locale, mappingFile, insertFile, autofillBool);
            }
        } catch (ConnectionException e) {
            Stage dialog = new Stage();
            dialog.setTitle("Error!");
            dialog.initModality(Modality.APPLICATION_MODAL);

            VBox dialogVBox = new VBox(20);
            dialogVBox.getChildren().add(new Text(e.getMessage()));
            dialogVBox.setAlignment(Pos.CENTER);
            Scene dialogScene = new Scene(dialogVBox, 400, 150);

            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
}

