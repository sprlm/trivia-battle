package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SubjectInfo extends Label {
    public final static String FONT_PATH = "src/Assets/Font/kenvector_future.ttf";
    public final static String BACKGROUND ="Assets/PNG/blue_button13.png";

    public SubjectInfo(String text){
        setPrefWidth(380);
        setPrefHeight(49);
        setText(text);
        setWrapText(true);
        setFontLabel();
        setAlignment(Pos.CENTER);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND,380,49,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
    }

    private void setFontLabel(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        }

        catch (FileNotFoundException e) {
            setFont(Font.font("Arial",23));
        }
    }
}
