import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BoxLayout<T> extends Pane
{
    private String text;
    private double dimension = 100;
    ImageView background = new ImageView(new Image(String.valueOf(getClass().getResource("icons/music-note.png"))));

    public BoxLayout(String title, T element)
    {
        this.text = title;
        Label textLabel = new Label(text);
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
        this.getChildren().add(background);
        this.getChildren().add(textLabel);
        this.setPrefWidth(dimension);
        this.setStyle("-fx-background-color: #33b5e5;");
    }

    public void resizeElement()
    {
        this.dimension = (Main.getMainStage().getWidth()/12)*3;
        this.setPrefHeight(dimension);
        this.setPrefWidth(dimension);
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
    }
}
