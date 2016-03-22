import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SongView extends Pane
{
    private Song song;

    public SongView(Song song)
    {
        this.song = song;
        HBox box = new HBox();
        box.setSpacing(10);
        Label songLabel = new Label();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("icons/music-note.png").toString()));
        if (song.getImage() != null)
            imageView.setImage(song.getImage());
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        songLabel.setText(song.getTitle());
        box.getChildren().add(imageView);
        box.getChildren().add(songLabel);
        this.getChildren().add(box);
        this.setOnMouseClicked((event) ->
            Platform.runLater(() ->
                Main.getController().playNewSong(song)
        ));
    }

}
