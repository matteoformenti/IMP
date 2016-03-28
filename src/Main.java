import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.Option;
import settings.SettingsReader;

import java.io.IOException;
import java.util.List;

/**
 * Main class for IMP music player
 */
public class Main extends Application
{
    private static MainController controller;
    private static Stage mainStage;

    /**
     * Entry point
     */
    public static void main(String a[])
    {
        launch(a);
    }

    /**
     * Getter for the main Stage
     * @return The main stage
     */
    public static Stage getMainStage()
    {
        return mainStage;
    }

    /**
     * Getter for the main controller
     * @return The controller
     */
    public static MainController getController()
    {
        return controller;
    }

    /**
     * JavaFX start method
     */
    public void start(Stage primaryStage) throws IOException
    {
        mainStage = primaryStage;
        mainStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/MainLayout.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene mainScene = new Scene(root, 800, 500);
        mainStage.setScene(mainScene);
        controller.showLoadingDialog();
        mainStage.setResizable(false);
        controller.init();
        /**
         * read all options from teh file
         */
        SettingsReader reader = new SettingsReader(Settings.settingsLocation, "$");
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
    }
}
