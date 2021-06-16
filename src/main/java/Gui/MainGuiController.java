package Gui;

import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import GenerateInformation.IntelligentGeneration;
import com.mysql.cj.util.StringUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;

/**
 * The Controller for the Gui. Activates upon submiting data in {@link MainGui}
 */
public class MainGuiController {
    /**
     * StartUp.Main Method that activates upon submitting the Gui data
     * @param supportedDatabases The Selected database. See {@link SupportedDatabases}
     * @param hostnameOrServerName Hostname or Server name where the database is running (default: depends on the database)
     * @param portOrInstance Port or Instance where the database is running (default: depends on the database)
     * @param databaseName Name of the database (Necessary)
     * @param username Username of the account used to log into the database (Necessary)
     * @param password Password of the account used to log into the database (Necessary)
     * @param seed Seed used to generate data (default: random value created using {@link Random})
     * @param locale Locale used to generate data (default: is pl-PL)
     * @param autoFill if set to true will connect and execute the Inserts directly to the database
     * @param mappingFile which will point to file where the mapped information will be placed (default: TableMapping.json file in the folder the program is located)
     * @param insertFile which point to a file where insert are created - valid if autoFill is true (default: Inserts.txt file in the folder the program is located)
     */
    public void submit(SupportedDatabases supportedDatabases, String hostnameOrServerName, String portOrInstance, String databaseName
            , String username, String password, String seed, String locale, String autoFill, String mappingFile, String insertFile) {

        boolean autofillBool = (autoFill.equals("Directly to Database"));

        if (StringUtils.isNullOrEmpty(seed)) seed = String.valueOf(new Random().nextLong());
        if (StringUtils.isNullOrEmpty(locale)) locale = "pl-PL";
        if (StringUtils.isNullOrEmpty(mappingFile)) mappingFile = "TableMapping.json";
        if (StringUtils.isNullOrEmpty(insertFile)) insertFile = "Inserts.sql";

        try {
            new IntelligentGeneration().generateForGui(supportedDatabases, hostnameOrServerName, portOrInstance, databaseName, username, password, Long.parseLong(seed), locale, mappingFile, insertFile, autofillBool);
        } catch (ConnectionException | NumberFormatException e) {
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

    public static void showAlertMessage(String headerMessage, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Check the database mapping!");
        alert.setHeaderText(headerMessage);
        alert.setContentText(contentMessage);

        ButtonType confirmation = new ButtonType("Generate Data");
        alert.getButtonTypes().setAll(confirmation);

        Optional<ButtonType> result = alert.showAndWait();
        result.get();
    }

    public static void showClosingMessage(String headerMessage, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("The Data has been Generated");
        alert.setHeaderText(headerMessage);
        alert.setContentText(contentMessage);

        ButtonType close = new ButtonType("Close the Program");
        alert.getButtonTypes().setAll(close);

        Optional<ButtonType> result = alert.showAndWait();

        System.exit(0);
    }
}

