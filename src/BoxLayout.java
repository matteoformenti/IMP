import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BoxLayout<T> extends Pane
{
    private String text;
    private double dimension = 80;

    public BoxLayout(String title, T element)
    {
        this.text = title;
        Label textLabel = new Label(text);
        ImageView background = new ImageView(new Image(String.valueOf(getClass().getResource("icons/music-note.png"))));
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
        this.getChildren().add(background);
        this.setPrefWidth(dimension);
    }
}
