package Gui;

import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.SupportedDatabases;
import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import InsertCreation.Data;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainGuiController {

    public void submit(SupportedDatabases supportedDatabases, String hostnameOrServerName, String portOrInstance, String databaseName, String username, String password) {

        try {
            DatabaseInfo databaseInfo = DatabaseInfo.builder()
                    .database(supportedDatabases)
                    .username(username)
                    .password(password)
                    .hostOrServerName(hostnameOrServerName)
                    .portOrInstance(portOrInstance)
                    .name(databaseName)
                    .build();

            ConnectionInformation connectionInformation = new ConnectionInformation(databaseInfo);

            connectionInformation.connect();
            String[]test = Data.QuasiPesel();
            String[]test2 = Data.QuasiName();
            //String str = new InsertCreationClass().InsertCreationClass(connectionInformation.getTableInfo(),test,test2);
            //new InsertSavingClass().InsertSavingClass(str);

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

