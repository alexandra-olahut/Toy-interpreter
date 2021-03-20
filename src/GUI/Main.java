package GUI;


//TO DO
/*
add file.in for example
modify command -> program can be run again   /  make exception
style.css
check all programs
check warnings
make structure pretty

*/
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.setScene(new Scene(root, 800, 500));

        primaryStage.setOnCloseRequest(event-> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
