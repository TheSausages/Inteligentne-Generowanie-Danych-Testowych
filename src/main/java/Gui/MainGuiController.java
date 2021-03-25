package Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class MainGuiController {

    public void submit(String hostnameOrServerName, String portOrInstance, String databaseName, String username, String password) {
        System.out.println(hostnameOrServerName);
        System.out.println(portOrInstance);
        System.out.println(databaseName);
        System.out.println(username);
        System.out.println(password);
    }
}

