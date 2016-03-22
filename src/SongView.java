import com.jfoenix.controls.JFXPopup;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SongView extends Pane
{
    private Song song;
    public HBox box = new HBox();

    public SongView(Song song)
    {
        this.song = song;
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
//        this.setOnMouseClicked((event) ->
//            Platform.runLater(() ->
//                Main.getController().playNewSong(song)
//        ));
        JFXPopup popup = new JFXPopup();
        this.setOnMouseClicked((event) ->
        {
            System.out.println("showing popup");
            popup.getChildren().add(new Label ("Play"));
            popup.getChildren().add(new Label ("Add to playlist"));
            popup.getChildren().add(new Label ("Delete"));
            System.out.println(this.song.getTitle());
            popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
        });
    }

}
