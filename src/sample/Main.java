package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller=loader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setOnCloseRequest(e->controller.handleExit());
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
