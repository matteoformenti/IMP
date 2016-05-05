package eu.itmakers;

import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.Serializable;

public class SongView extends Pane implements Serializable
{
    private Song song;
    private HBox box = new HBox();
    private VBox vbox = new VBox();
    private JFXPopup popup = new JFXPopup();
    private Label songLabel;
    private Label artistLabel;

    public SongView(Song song)
    {
        this.song = song;
        song.setSongView(this);
        box.setSpacing(10);
        songLabel = new Label(song.getTitle());
        artistLabel = new Label(song.getArtist());
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
            addToPlaylistButton.setOnAction((event1 ->
            {
                JFXDialog playlistDialog = new JFXDialog();
                JFXDialogLayout playlistLayout = new JFXDialogLayout();
                JFXListView playlist = new JFXListView();
                for (Playlist p : Settings.getPlaylists())
                {
                    PlayListView pw = p.getPlayListView();
                    pw.setOnMouseClicked((event2) ->
                    {
                        Settings.getPlaylistFromPlaylistView(pw).getSongs().add(this.song);
                        playlistDialog.close();
                        dialog.close();
                    });
                    playlist.getItems().add(p.getPlayListView());
                }
                JFXButton createPlaylistButton = new JFXButton("Create new playlist");
                createPlaylistButton.setOnMouseClicked((e) ->
                {
                    JFXDialog createNewPlaylistDialog = new JFXDialog();
                    JFXDialogLayout createNewPlaylistDialogLayout = new JFXDialogLayout();
                    JFXTextField textField = new JFXTextField();
                    textField.setPromptText("Playlist name");
                    createNewPlaylistDialogLayout.setBody(textField);
                    JFXButton savePlaylistButton = new JFXButton("Create playlist");
                    JFXButton cancelButton = new JFXButton("Cancel");
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(savePlaylistButton, cancelButton);
                    createNewPlaylistDialogLayout.setActions(hbox);
                    createNewPlaylistDialog.setContent(createNewPlaylistDialogLayout);
                    createNewPlaylistDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                    createNewPlaylistDialog.setPrefWidth(Main.getController().topPane.getWidth());
                    createNewPlaylistDialog.setPrefHeight(Main.getController().topPane.getHeight());
                    createNewPlaylistDialog.show(Main.getController().topPane);
                    cancelButton.setOnMouseClicked((event2) ->
                    {
                        playlistDialog.close();
                        dialog.close();
                        createNewPlaylistDialog.close();
                    });
                    savePlaylistButton.setOnMouseClicked((event2) ->
                    {
                        Playlist playlist1 = new Playlist(textField.getText());
                        playlist1.getSongs().add(song);
                        Settings.getPlaylists().add(playlist1);
                        playlistDialog.close();
                        dialog.close();
                        createNewPlaylistDialog.close();
                    });
                });
                playlist.getItems().add(0, createPlaylistButton);
                playlistLayout.setBody(playlist);
                playlistDialog.setContent(playlistLayout);
                playlistDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                playlistDialog.setPrefWidth(Main.getController().topPane.getWidth());
                playlistDialog.setPrefHeight(Main.getController().topPane.getHeight());
                playlistDialog.show(Main.getController().topPane);
            }));
            box2.getChildren().add(addToPlaylistButton);
            HBox box3 = new HBox();
            JFXButton removeSongButton = new JFXButton("Delete song");
            addToPlaylistButton.setOnMouseClicked((event1 ->
            {
                //delete dons
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
            dialog.setPrefWidth(Main.getController().topPane.getWidth());
            dialog.setPrefHeight(Main.getController().topPane.getHeight());
            dialog.show(Main.getController().topPane);
        });
    }

    public void update()
    {
        this.artistLabel.setText(song.getArtist());
        this.songLabel.setText(song.getTitle());
    }
}
