package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.setTitle("Trivia Battle");
        primaryStage.getIcons().add(new Image("Assets/PNG/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
