import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SongView extends Pane
{
    private Song song;
    public HBox box = new HBox();
    private VBox vbox = new VBox();
    public JFXPopup popup = new JFXPopup();

    public SongView(Song song)
    {
        this.song = song;
        song.setSongView(this);
        box.setSpacing(10);
        Label songLabel = new Label(song.getTitle());
        Label artistLabel = new Label(song.getArtist());
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("icons/music-note.png").toString()));
        if (song.getImage() != null)
            imageView.setImage(song.getImage());
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        songLabel.setText(song.getTitle());
        box.getChildren().add(imageView);
        box.getChildren().add(vbox);
        vbox.getChildren().add(songLabel);
        vbox.getChildren().add(artistLabel);

        this.getChildren().add(box);
        this.setOnMouseClicked((event) ->
        {
            JFXDialog dialog = new JFXDialog();
            JFXDialogLayout layout = new JFXDialogLayout();
            VBox box = new VBox();
            box.setSpacing(5);
            box.setPadding(new Insets(0));
            layout.setBody(box);
            dialog.setContent(layout);
            HBox box1 = new HBox();
            JFXButton playButton = new JFXButton("Play");
            playButton.setOnMouseClicked((event1 -> {
                Main.getController().playNewSong(song);
                dialog.close();
            }));
            box1.getChildren().add(playButton);
            HBox box2 = new HBox();
            JFXButton addToPlaylistButton = new JFXButton("Add to playlist");
            addToPlaylistButton.setOnMouseClicked((event1 -> {
                //add to playlist
                dialog.close();
            }));
            box2.getChildren().add(addToPlaylistButton);
            HBox box3 = new HBox();
            JFXButton removeSongButton = new JFXButton("Delete song");
            addToPlaylistButton.setOnMouseClicked((event1 -> {
                //delete file
                dialog.close();
            }));
            box3.getChildren().add(removeSongButton);
            HBox box4 = new HBox();
            JFXButton viewSongMetadata = new JFXButton("View song metadata");
            viewSongMetadata.setOnMouseClicked((event1 -> {
                //view metadata
                dialog.close();
            }));
            box4.getChildren().add(viewSongMetadata);
            box.getChildren().addAll(box1, box2, box3, box4);

            HBox.setHgrow(box1, Priority.ALWAYS);
            HBox.setHgrow(box2, Priority.ALWAYS);
            HBox.setHgrow(box3, Priority.ALWAYS);
            HBox.setHgrow(box4, Priority.ALWAYS);
            playButton.setPrefWidth(200);
            addToPlaylistButton.setPrefWidth(200);
            removeSongButton.setPrefWidth(200);
            viewSongMetadata.setPrefWidth(200);

            box1.setAlignment(Pos.BASELINE_CENTER);
            box2.setAlignment(Pos.BASELINE_CENTER);
            box3.setAlignment(Pos.BASELINE_CENTER);
            box4.setAlignment(Pos.BASELINE_CENTER);
            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
            dialog.setPrefWidth(Main.getController().main.getWidth());
            dialog.setPrefHeight(Main.getController().main.getHeight());
            dialog.show(Main.getController().main);
        });
    }

}
