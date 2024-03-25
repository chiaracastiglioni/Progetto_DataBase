package it.unibo;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public final void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void run(final String[] args) {
        launch(args);
    }

    /**
     * Entry point class.
     */
    public static final class Main {
        
        /**
         * Program's entry point.
         * @param args
         */
        public static void main(final String... args) {
            App.run(args);
            ControllerTable controllerTable = new ControllerTable();
            //controllerTable.drop();

        }
    }
}
