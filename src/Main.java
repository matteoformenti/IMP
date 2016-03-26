import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application
{
    public static void main (String a[]){launch(a);}

    public static Stage getMainStage()
    {
        return mainStage;
    }

    private static MainController controller;

    public static MainController getController()
    {
        return controller;
    }

    private static Stage mainStage;

    public void start(Stage primaryStage) throws IOException
    {
            mainStage = primaryStage;
            mainStage.initStyle(StageStyle.UNDECORATED);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/MainLayout.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.init();
            Scene mainScene = new Scene(root, 800, 500);
            mainStage.setScene(mainScene);
            mainStage.setResizable(false);
            mainStage.show();
            Thread t = new Thread(new Settings());
            t.start();

    }
}
