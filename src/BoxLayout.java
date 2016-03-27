import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BoxLayout extends AnchorPane
{
    private String text;
    private double dimension = 100;
    private JFXDialog dialog;
    ImageView background = new ImageView(new Image(String.valueOf(getClass().getResource("icons/ic_recent_actors_black_48dp_2x.png"))));
    SongContainer container;

    public BoxLayout(String title, SongContainer container)
    {
        this.text = title;
        this.container = container;
        Label textLabel = new Label(text);
        if (container.getImage() != null)
            background.setImage(container.getImage());
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
        background.toBack();
        this.setBottomAnchor(textLabel, 0.0);
        this.setLeftAnchor(textLabel, 0.0);
        this.setRightAnchor(textLabel, 0.0);
        textLabel.setStyle("-fx-background-color: #03A9F4");
        this.getChildren().add(background);
        this.getChildren().add(textLabel);
        this.setPrefWidth(dimension);
        this.setStyle("-fx-background-color: #fefefe");
        this.setEffect(new DropShadow(10, new Color(0.01,0.01,0.01,0.25)));
        this.setOnMouseClicked((event) ->
        {
            dialog = new JFXDialog();
            JFXDialogLayout layout = new JFXDialogLayout();
            JFXListView<SongView> songList = new JFXListView<SongView>();
            dialog.setContent(layout);
            layout.setBody(songList);
            layout.setHeading(new Label(title));
            for (Song s : container.getSongs())
                songList.getItems().add(s.getSongView());
            dialog.setPrefHeight(Main.getMainStage().getHeight());
            dialog.setPrefWidth(Main.getMainStage().getWidth());
            dialog.show(Main.getController().main);
        });
    }

    public void resizeElement()
    {
        this.dimension = (Main.getMainStage().getWidth()/12)*3;
        this.setPrefHeight(dimension);
        this.setPrefWidth(dimension);
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
    }

    public void updateBackground()
    {
        if (container.getImage() != null)
            background.setImage(container.getImage());
    }
}
