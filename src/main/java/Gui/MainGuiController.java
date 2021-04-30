package Gui;

import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.SupportedDatabases;
import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import InsertCreation.InsertCreationClass;
import InsertCreation.InsertSavingClass;
import InsertCreation.Data;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainGuiController {

    public void submit(SupportedDatabases supportedDatabases, String hostnameOrServerName, String portOrInstance, String databaseName
            , String username, String password, String seed, String locale, String autoFill, String mappingFile, String insertFile) {

        try {
            System.out.println(supportedDatabases);
            System.out.println(hostnameOrServerName);
            System.out.println(portOrInstance);
            System.out.println(databaseName);
            System.out.println(username);
            System.out.println(password);
            System.out.println(seed);
            System.out.println(locale);
            System.out.println(autoFill);
            System.out.println(mappingFile);
            System.out.println(insertFile);
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

