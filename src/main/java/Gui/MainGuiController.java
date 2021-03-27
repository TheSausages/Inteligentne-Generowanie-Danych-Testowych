package Gui;

import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseDrivers;
import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainGuiController {

    public void submit(DatabaseDrivers databaseDrivers, String hostnameOrServerName, String portOrInstance, String databaseName, String username, String password) {

        try {
            DatabaseInfo databaseInfo = new DatabaseInfo(databaseDrivers);
            databaseInfo.setAccountInfo(username, password);
            databaseInfo.createAndSaveURL(hostnameOrServerName, portOrInstance, databaseName);
            databaseInfo.setDatabaseName(databaseName);

            ConnectionInformation connectionInformation = new ConnectionInformation();
            connectionInformation.createDataSource(databaseInfo);

            connectionInformation.connect();

            connectionInformation.getTableInfo();

            System.out.println("wyjdz");
            connectionInformation.closeConnection();
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

