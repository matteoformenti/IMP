package eu.itmakers;

import eu.itmakers.controller.MainController;
import eu.itmakers.webapp.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.Option;
import settings.SettingsReader;

import java.util.List;

public class Main extends Application {

    private static Stage mainStage;
    private static MainController controller;

    public static Stage getMainStage()
    {
        return mainStage;
    }

    public static MainController getController()
    {
        return controller;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/main.fxml"));
        Parent root = fxmlLoader.load();

        controller = fxmlLoader.getController();

        mainStage.setTitle("ITMakers Media Player");
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setScene(new Scene(root));
        controller.showLoadingDialog();
        controller.init();
        /**
         * read all options from the file
         */
        SettingsReader reader = new SettingsReader("src/eu/itmakers/"+Settings.settingsLocation, "$");
        if (reader.loadOptionsList() == 1)
        {
            List<Option> options = reader.getOptionsList();
            for (Option o : options)
                if (o.getName().equals("libraryLocation"))
                    Settings.setMediaDirectory((String) o.getValue());
            controller.musicLibraryLocation.setText(Settings.getMediaDirectory());
            mainStage.show();
            Thread t = new Thread(new Settings());
            t.start();
        }
        else
        {
            /**
             * Or create a new file and show the settings dialog
             */
            mainStage.show();
            controller.showSidebar(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("IMP first run");
            alert.setContentText("Welcome to IMP! Select the directory that contains your music library to start!");
            alert.setHeaderText("No library selected");
            alert.show();
        }
        // Generate songs list for WebApp IMP
        SyncWebApp.generateSongsListJSON(Settings.getSongs());
        Thread tSync = new Thread(new SyncWebApp());
        tSync.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
