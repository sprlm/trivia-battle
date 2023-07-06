package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ViewManager {
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private final String FONT_PATH ="src/Assets/Font/kenvector_future.ttf";
    private final String BGM = "src/Assets/Bonus/bgm.mp3";
    private final String ENTER_SOUND = "src/Assets/Bonus/click2.mp3";
    private final String SLIDER_STYLE = " -fx-background-color:null; -fx-background-insets: 1 0 -1 0, 0, 1; -fx-background-radius: 2.5, 2.5, 1.5;-fx-padding: 0.208333em;";
    private final int DEFAULT_X = 510;
    private final int DEFAULT_Y = 450;

    private TriviaBattleSubScenes optionSubScene;
    private TriviaBattleSubScenes subjectChooseSubScene;
    private TriviaBattleSubScenes gameSubScene;

    private int livesP1 = 5;
    private int livesP2 = 5;
    private int currentPlayer = 2;
    private String gameQuestion;
    private String gameAnswer;

    List<TriviaBattleButton> menuButtons;
    List<SubjectPicker> subjectPickerList;
    private SUBJECT chosenSubject;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();

        mainScene = new Scene(mainPane,1280,720);

        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createBackground();
        createButtons();
        createLogo();
        createSubScenes();
        playBGM(BGM);

    }

    public void createVolumeSlider(MediaPlayer mp){
        Slider volume = new Slider();
        volume.setValue(mp.getVolume()*100);
        volume.setMin(0);
        volume.setMax(100);
        volume.setMinWidth(200);
        volume.setMaxWidth(200);
        volume.setStyle(SLIDER_STYLE);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mp.setVolume(volume.getValue() / 100);
            }
        });

        volume.setLayoutX(300);
        volume.setLayoutY(300);
        optionSubScene.getAnchorPane().getChildren().add(volume);
    }

    public void playBGM(String musicFile){
        Media bgm = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(bgm);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });

        createVolumeSlider(mediaPlayer);
        mediaPlayer.play();
    }

    public void createSubScenes(){
        optionSubScene = new TriviaBattleSubScenes();
        Label version = new Label("version 1.0");

        try {
            version.setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        }

        catch (FileNotFoundException e) {
            version.setFont(Font.font("Arial",23));
        }

        version.setLayoutX(100);
        version.setLayoutY(400);
        Label volText = new Label("Volume:");

        try {
            volText.setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        }

        catch (FileNotFoundException e) {
            volText.setFont(Font.font("Arial",23));
        }

        volText.setLayoutX(100);
        volText.setLayoutY(300);

        optionSubScene.getAnchorPane().getChildren().add(version);
        optionSubScene.getAnchorPane().getChildren().add(volText);
        optionSubScene.setOpacity(0.8);

        mainPane.getChildren().add(optionSubScene);

        createSubjectChooserSubScene();

        createGameSubScene();
    }

    private void createSubjectChooserSubScene() {
        subjectChooseSubScene = new TriviaBattleSubScenes();
        
        mainPane.getChildren().add(subjectChooseSubScene);

        SubjectInfo chooseSubjectLabel = new SubjectInfo("Choose a category:");
        chooseSubjectLabel.setLayoutX(100);
        chooseSubjectLabel.setLayoutY(25);
        subjectChooseSubScene.getAnchorPane().getChildren().add(chooseSubjectLabel);
        subjectChooseSubScene.getAnchorPane().getChildren().add(createSubjectsToChoose());
        subjectChooseSubScene.getAnchorPane().getChildren().add(createGoButton());
    }

    private HBox createSubjectsToChoose(){
        HBox box = new HBox();
        box.setSpacing(20);
        subjectPickerList = new ArrayList<>();
        for (SUBJECT subject : SUBJECT.values()){
            SubjectPicker subjectToPick = new SubjectPicker(subject);
            subjectPickerList.add(subjectToPick);
            box.getChildren().add(subjectToPick);
            subjectToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (SubjectPicker subject : subjectPickerList){
                        subject.setIsCircleChosen(false);
                    }

                    setSound(ENTER_SOUND);
                    subjectToPick.setIsCircleChosen(true);
                    chosenSubject = subjectToPick.getSubject();
                    createNewQuestion();
                }
            });
        }

        box.setLayoutX(300 - (105*2));
        box.setLayoutY(100);
        return box;
    }

    private void createGameSubScene() {
        gameSubScene = new TriviaBattleSubScenes();

        // Label for player 1's current hp
        Text player1Life = new Text("Player 1\nHP: " + 5 + "/5");
        try {
            player1Life.setFont(Font.loadFont(new FileInputStream(FONT_PATH),34));
        }
        catch (FileNotFoundException e) {
            player1Life.setFont(Font.font("Arial",34));
        }
        player1Life.setLayoutX(100);
        player1Life.setLayoutY(195);

        // Label for player 2's current hp
        Text player2Life = new Text("Player 2\nHP: " + 5 + "/5");
        try {
            player2Life.setFont(Font.loadFont(new FileInputStream(FONT_PATH),34));
        }
        catch (FileNotFoundException e) {
            player2Life.setFont(Font.font("Arial",34));
        }
        player2Life.setLayoutX(975);
        player2Life.setLayoutY(195);
        player2Life.setTextAlignment(TextAlignment.RIGHT);

        // Player pictures
        ImageView player1 = new ImageView("Assets/Idle (1).png");
        player1.setLayoutX(100);
        player1.setLayoutY(200);

        ImageView player2 = new ImageView("Assets/Player2.png");
        player2.setLayoutX(920);
        player2.setLayoutY(250);

        // Label displaying the current player's turn
        Label currentP = new Label("");
        try {
            currentP.setFont(Font.loadFont(new FileInputStream(FONT_PATH),34));
        }
        catch (FileNotFoundException e) {
            currentP.setFont(Font.font("Arial",34));
        }
        currentP.setLayoutX(460);
        currentP.setLayoutY(80);
        currentP.setTextAlignment(TextAlignment.CENTER);
        currentP.setTextFill(Color.web("#92dffc"));

        // Label displaying the question
        Label questionLabel = new Label("");
        try {
            questionLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH),22));
        }
        catch (FileNotFoundException e) {
            questionLabel.setFont(Font.font("Arial",22));
        }
        questionLabel.setLayoutX(440);
        questionLabel.setLayoutY(200);
        questionLabel.setMaxWidth(400);
        questionLabel.setTextAlignment(TextAlignment.CENTER);
        questionLabel.setWrapText(true);

        // Text field where user types answer
        TextField answerField = new TextField();
        answerField.setMinWidth(375);
        answerField.setPromptText("Type answer here (if number, use 2 decimal places)");
        answerField.setLayoutX(440);
        answerField.setLayoutY(500);

        // Label displaying if right or wrong
        Label resultLabel = new Label("");
        try {
            resultLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH),22));
        }
        catch (FileNotFoundException e) {
            resultLabel.setFont(Font.font("Arial",22));
        }
        resultLabel.setLayoutX(450);
        resultLabel.setLayoutY(650);

        // Submit answer button
        TriviaBattleButton submitButton = new TriviaBattleButton("Start");
        submitButton.setLayoutX(525);
        submitButton.setLayoutY(550);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (answerField.getText().toUpperCase().contains(gameAnswer) && answerField.getText().length() > 0) {
                    if (currentPlayer == 1) {
                        livesP2 -= 1;
                        player2Life.setText("Player 2\nHP: " + livesP2 + "/5");
                        if (livesP2 == 0) {
                            currentP.setText("");
                            try {
                                questionLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH),40));
                            }
                            catch (FileNotFoundException e) {
                                questionLabel.setFont(Font.font("Arial",40));
                            }
                            questionLabel.setText("PLAYER 1 WINS");
                            submitButton.setLayoutY(2000);
                            createGameSubScene();
                            livesP1 = 5;
                            livesP2 = 5;
                            return;
                        }
                    }
                    else {
                        livesP1 -= 1;
                        player1Life.setText("Player 1\nHP: " + livesP1 + "/5");
                        if (livesP1 == 0) {
                            currentP.setText("");
                            try {
                                questionLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH),40));
                            }
                            catch (FileNotFoundException e) {
                                questionLabel.setFont(Font.font("Arial",40));
                            }
                            questionLabel.setText("PLAYER 2 WINS");
                            submitButton.setLayoutY(2000);
                            createGameSubScene();
                            livesP1 = 5;
                            livesP2 = 5;
                            return;
                        }
                    }
                }


                if (!submitButton.getText().equals("Start")) { resultLabel.setText("Correct answer: " + gameAnswer); }
                if (submitButton.getText().equals("Start")) { submitButton.setText("Submit"); }

                createNewQuestion();
                questionLabel.setText(gameQuestion);
                answerField.setText("");

                currentPlayer = 3 - currentPlayer;  // If current player is 1, then make it 2. If current player is 2, make it 1.
                currentP.setText("Player " + currentPlayer + "'s Turn");
            }
        });

        gameSubScene.getAnchorPane().getChildren().add(player1Life);
        gameSubScene.getAnchorPane().getChildren().add(player2Life);
        gameSubScene.getAnchorPane().getChildren().add(player1);
        gameSubScene.getAnchorPane().getChildren().add(player2);
        gameSubScene.getAnchorPane().getChildren().add(currentP);
        gameSubScene.getAnchorPane().getChildren().add(questionLabel);
        gameSubScene.getAnchorPane().getChildren().add(answerField);
        gameSubScene.getAnchorPane().getChildren().add(submitButton);
        gameSubScene.getAnchorPane().getChildren().add(resultLabel);

        mainPane.getChildren().add(gameSubScene);
    }

    private void createNewQuestion() {
        if (chosenSubject == SUBJECT.PHYSICS) {
            PhysicsQuestion pq = new PhysicsQuestion();
            gameQuestion = pq.getQuestion();
            gameAnswer = pq.getAnswer();
        }

        else if (chosenSubject == SUBJECT.DATAANALYSIS) {
            DataAnalysisQuestion dq = new DataAnalysisQuestion();
            gameQuestion = dq.getQuestion();
            gameAnswer = dq.getAnswer();
        }

        else if (chosenSubject == SUBJECT.HISTORY) {
            PhilippinesQuestion phq = new PhilippinesQuestion();
            gameQuestion = phq.getQuestion();
            gameAnswer = phq.getAnswer();
        }

        else if (chosenSubject == SUBJECT.EVERYTHING) {
            int rand = (int)(Math.random() * 3) + 1;
            if (rand == 1) {
                PhysicsQuestion pq = new PhysicsQuestion();
                gameQuestion = pq.getQuestion();
                gameAnswer = pq.getAnswer();
            }
            else if (rand == 2) {
                DataAnalysisQuestion dq = new DataAnalysisQuestion();
                gameQuestion = dq.getQuestion();
                gameAnswer = dq.getAnswer();
            }
            else {
                PhilippinesQuestion phq = new PhilippinesQuestion();
                gameQuestion = phq.getQuestion();
                gameAnswer = phq.getAnswer();
            }
        }

        else {
            gameQuestion = "ERROR: PROGRAM THINKS NO SUBJECT HAS BEEN PICKED";
            gameAnswer = "";
        }
    }

    public Stage getMainStage(){
         return mainStage;
}

    private void addMenuButtons(TriviaBattleButton button){ //used to easily add buttons with proper spacing
        button.setLayoutX(DEFAULT_X);
        button.setLayoutY(DEFAULT_Y + menuButtons.size() * 80);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons(){ //compilation of the create buttons
        createStartButton();
        createOptionButton();
        createExitButton();
        createSubjectChooserButton();
    }

    private void createSubjectChooserButton(){
        TriviaBattleButton subjectChooserButton = new TriviaBattleButton("go");
        subjectChooserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subjectChooseSubScene.MoveSubScene();
            }
        });
    }

    private void createStartButton(){
        TriviaBattleButton startButton = new TriviaBattleButton("Start");
        addMenuButtons(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subjectChooseSubScene.MoveSubScene();
            }
        });
    }

    private TriviaBattleButton createGoButton(){
        TriviaBattleButton goButton = new TriviaBattleButton("Go");
        goButton.setLayoutX(500);
        goButton.setLayoutY(500);

        goButton.setOnAction(new EventHandler<ActionEvent>() { //Shows or hides option subscene when clicked
            @Override
            public void handle(ActionEvent event) {
                if (chosenSubject != null)
                    gameSubScene.MoveSubScene();
            }
        });

        return goButton;
    }

    private void createOptionButton(){
        TriviaBattleButton optionButton = new TriviaBattleButton("Option");
        addMenuButtons(optionButton);

        optionButton.setOnAction(new EventHandler<ActionEvent>() { //Shows or hides option subscene when clicked
            @Override
            public void handle(ActionEvent event) {
                optionSubScene.MoveSubScene();
            }
        });
    }

    private void createExitButton(){
        TriviaBattleButton exitButton = new TriviaBattleButton("Exit");
        addMenuButtons(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() { //closes the program
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createLogo(){
        ImageView logo = new ImageView("Assets/PNG/logo.png");
        logo.setLayoutX(300);
        logo.setLayoutY(100);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }

    private void setSound(String musicFile){
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void createBackground(){
        Image backgroundImage = new Image("Assets/PNG/background.jpg",1280,720,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
    }
}
