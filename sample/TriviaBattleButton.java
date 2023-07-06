package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TriviaBattleButton extends Button {

    private final String FONT_PATH ="src/Assets/Font/kenvector_future.ttf";
    private final String BUTTON_DEFAULT = "-fx-background-color: transparent; -fx-background-image: url('/Assets/PNG/blue_button00.png');";
    private final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/Assets/PNG/blue_button01.png');";
    private final String CLICK_SOUND = "src/Assets/Bonus/click1.mp3";
    private final String ENTER_SOUND = "src/Assets/Bonus/click2.mp3";

    public TriviaBattleButton(String text){
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_DEFAULT);
        initializeButtonListeners();
    }

    private void setSound(String musicFile){
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        }

        catch (FileNotFoundException e) {
           setFont(Font.font("Arial",23));
        }
    }

    private void setButtonDefaultStyle(){
        setStyle(BUTTON_DEFAULT);

        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);

    }

    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setSound(ENTER_SOUND);
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonDefaultStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSound(CLICK_SOUND);
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
