package sample;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SubjectPicker extends VBox {
    private ImageView circleImage;
    private ImageView subjectImage;

    private final static String circleChosen = "Assets/PNG/blue_boxTick.png";
    private final static String circleNotChosen = "Assets/PNG/grey_circle.png";
    private SUBJECT subject;

    private boolean isCircleChosen;

    public SubjectPicker(SUBJECT subject){
        circleImage = new ImageView(circleNotChosen);
        subjectImage = new ImageView(subject.getUrlSubject());
        this.subject = subject;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(subjectImage);
    }

    public SUBJECT getSubject(){
        return subject;
    }

    public boolean getIsCircleChosen(){
        return isCircleChosen;
    }

    public void setIsCircleChosen(boolean isCircleChosen){
        this.isCircleChosen = isCircleChosen;
        String imagetoSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imagetoSet));

    }
}
