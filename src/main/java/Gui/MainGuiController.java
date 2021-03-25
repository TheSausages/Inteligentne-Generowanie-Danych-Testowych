package Gui;

import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseDrivers;
import Exceptions.ConnectionException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class MainGuiController {

    public void submit(DatabaseDrivers databaseDrivers, String hostnameOrServerName, String portOrInstance, String databaseName, String username, String password) {
        ConnectionInformation connectionInformation = new ConnectionInformation(databaseDrivers);
        connectionInformation.createAndSaveURL(hostnameOrServerName, portOrInstance, databaseName);
        connectionInformation.setAccountInfo(username, password);

        try {
            connectionInformation.connect();

            System.out.println(connectionInformation.getConnection().isValid(1));

            connectionInformation.closeConnection();
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

