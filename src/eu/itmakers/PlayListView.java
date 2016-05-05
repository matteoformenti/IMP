package eu.itmakers;

import com.jfoenix.controls.JFXPopup;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.Serializable;

/**
 * Created by Matteo on 02/04/2016.
 */
public class PlayListView extends Pane implements Serializable
{
    private Playlist playlist;
    private HBox box = new HBox();
    private VBox vbox = new VBox();
    private JFXPopup popup = new JFXPopup();
    private Label playlistName = new Label();

    public PlayListView(Playlist playlist)
    {
        this.playlist = playlist;
        playlist.setPlayListView(this);
        box.setSpacing(10);
        playlistName.setText(playlist.getName());
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("itmakers/imp/icons/music-note.png").toString()));
        if (playlist.getImage() != null)
            imageView.setImage(playlist.getImage());
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        box.getChildren().add(imageView);
        box.getChildren().add(vbox);
        vbox.getChildren().add(playlistName);

        this.getChildren().add(box);

    }

    public void update()
    {
        this.playlistName.setText(playlist.getName());
    }
}
