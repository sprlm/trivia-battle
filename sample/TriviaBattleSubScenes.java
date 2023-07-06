package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javax.swing.text.View;

public class TriviaBattleSubScenes extends SubScene {
     private final static String FONT_PATH = "Assets/Font/kenvector_future.tff";
     private final static String BACKGROUND = "Assets/PNG/blue_panel.png";

     private boolean isHidden;

    public TriviaBattleSubScenes() {
        super(new AnchorPane(), 1280, 720);
        prefWidth(1280);
        prefHeight(720);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND,1280,720,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
        isHidden = true;
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        createSubSceneButtons(root2);
        setLayoutX(1280);
        setLayoutY(0);
    }

    private void createSubSceneButtons(AnchorPane root){
        TriviaBattleButton back = new TriviaBattleButton("Back");
        back.setLayoutY(660);
        back.setLayoutX(100);
        root.getChildren().add(back);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MoveSubScene();
            }
        });
    }

    public void MoveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        if(isHidden){
            transition.setToX(-1280);
            isHidden = false;
        }

        else{transition.setToX(0);
        isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getAnchorPane(){
        return (AnchorPane) this.getRoot();
    }
}
