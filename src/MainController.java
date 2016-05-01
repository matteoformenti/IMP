import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.util.Duration;
import settings.Option;
import settings.SettingsWriter;

import java.io.File;


public class MainController
{
    public FontAwesomeIconView moreButton;
    public FontAwesomeIconView closeButton;
    public FontAwesomeIconView maximizeButton;
    public FontAwesomeIconView minimizeButton;
    public AnchorPane mainPanel;
    public AnchorPane playlistPane;
    public AnchorPane albumsPane;
    public AnchorPane artistsPane;
    public AnchorPane songsPane;
    public JFXListView<SongView> allSongsListView;
    public MaterialDesignIconView previousSongButton;
    public MaterialDesignIconView playPauseButton;
    public MaterialDesignIconView nextSongLabel;
    public Label songTitleLabel;
    public JFXSlider timeSlider;
    public BorderPane topPane;
    public AnchorPane main;
    public ScrollPane artistListView;
    public ScrollPane albumsView;
    public ScrollPane playlistView;

    //-----Settings-----//
    public JFXTextField musicLibraryLocation;
    public FontAwesomeIconView selectMusicLibraryLocation;
    public JFXDialog settingsDialog;
    public Pane settingsDialogPane;
    public Label infoLabel;

    //-----loading------//
    public JFXDialog loadingDialog;
    public Pane loadingDialogPane;

    private double xOffset = 0;
    private double yOffset = 0;
    public MediaPlayer mediaPlayer;

    public void init()
    {
        timeSlider.setDisable(true);
        timeSlider.setValue(0);
        Timeline oneSecondTick = new Timeline(new KeyFrame(Duration.seconds(1), event ->
        {
            try
            {
                if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                        timeSlider.setValue(timeSlider.getValue() + 1);
            }
            catch (Exception e){}
        }));

        songTitleLabel.setText("");
        topPane.setOnMousePressed((event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
        });
        topPane.setOnMouseDragged((event) -> {
                Main.getMainStage().setX(event.getScreenX() - xOffset);
                Main.getMainStage().setY(event.getScreenY() - yOffset);
        });
        oneSecondTick.setCycleCount(Timeline.INDEFINITE);
        oneSecondTick.play();
        main.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            for (Author a : Settings.getAuthors())
                a.getBox().resizeElement();
            for (Album a : Settings.getAlbums())
                a.getBox().resizeElement();
        });
        main.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            for (Author a : Settings.getAuthors())
                a.getBox().resizeElement();
            for (Album a : Settings.getAlbums())
                a.getBox().resizeElement();
        });
    }

    public void close(InputEvent event)
    {
        Settings.closeApplication();
    }

    public void maximize(InputEvent event)
    {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        if (Main.getMainStage().getWidth()!=primaryScreenBounds.getWidth()||Main.getMainStage().getHeight()!=primaryScreenBounds.getHeight())
        {
            Main.getMainStage().setX(primaryScreenBounds.getMinX());
            Main.getMainStage().setY(primaryScreenBounds.getMinY());
            Main.getMainStage().setWidth(primaryScreenBounds.getWidth());
            Main.getMainStage().setHeight(primaryScreenBounds.getHeight());
        }
        else
        {
            Main.getMainStage().setWidth(800);
            Main.getMainStage().setHeight(500);
            Main.getMainStage().centerOnScreen();
        }
        for (Author a : Settings.getAuthors())
            a.getBox().resizeElement();
        for (Album a : Settings.getAlbums())
            a.getBox().resizeElement();
    }

    public void minimize(InputEvent event)
    {
        Main.getMainStage().setIconified(true);
    }

    public void nextSong(InputEvent event)
    {
    try
    {
        Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))).getSongView().setStyle(null);
        Song song = (Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))  != Settings.getSongs().size()-1) ? Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))+1) : Settings.getSongs().get(0);
        playNewSong(song);
    }
    catch (Exception e){}
    }

    public void playPause(InputEvent event)
    {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.pause();
        else
            mediaPlayer.play();
    }

    public void previousSong(InputEvent event)
    {
        try
        {
            Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))).getSongView().setStyle(null);
            Song song = (Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))  != 0) ? Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))-1) : Settings.getSongs().get(Settings.getSongs().size()-1);
            playNewSong(song);
        }
        catch (Exception e){}
    }

    public void showSidebar(InputEvent event)
    {
        settingsDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        settingsDialog.show(main);
        settingsDialog.setPrefWidth(main.getWidth());
        settingsDialog.setPrefHeight(main.getHeight());
    }

    public void showLoadingDialog()
    {
        loadingDialog.setOverlayClose(false);
        loadingDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        loadingDialog.show(main);
        loadingDialog.setPrefWidth(main.getWidth());
        loadingDialog.setPrefHeight(main.getHeight());
    }

    public void hideLoadingDialog()
    {
        loadingDialog.close();
    }

    public void playNewSong(Song s)
    {
        if (mediaPlayer != null)
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(s.getMedia());
        songTitleLabel.setText((s.getArtist().equals("Unknown")?s.getTitle() : s.getArtist() + " - " + s.getTitle()));
        mediaPlayer.play();
        timeSlider.setMax(s.getLengthInSeconds());
        timeSlider.setValue(0);
        mediaPlayer.setOnEndOfMedia(() ->
        {
            Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))).getSongView().setStyle(null);
            Song song = (Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))  != Settings.getSongs().size()-1) ? Settings.getSongs().get(Settings.getSongs().indexOf(Settings.getSongAssociatedWithMedia(mediaPlayer.getMedia()))+1) : Settings.getSongs().get(0);
            playNewSong(song);
        });
    }

    public void selectLibraryLocation(Event event)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(Main.getMainStage());
        Settings.setMediaDirectory(selectedDirectory.getAbsolutePath());
        Settings.getSongs().removeAll(Settings.getSongs());
        Settings.getAuthors().removeAll(Settings.getAuthors());
        Settings.getAlbums().removeAll(Settings.getAlbums());
        artistListView.setContent(null);
        allSongsListView.getItems().removeAll(allSongsListView.getItems());
        albumsView.setContent(null);
        playlistView.setContent(null);
        musicLibraryLocation.setText(selectedDirectory.getAbsolutePath());
        musicLibraryLocation.setPromptText("");
        Thread t = new Thread(new Settings());
        t.start();
    }

    public void saveSettings(Event event)
    {
        Settings.setMediaDirectory(musicLibraryLocation.getText());
        SettingsWriter writer = new SettingsWriter(Settings.settingsLocation, "$");
        writer.addOption(new Option("libraryLocation", Settings.getMediaDirectory()));
        writer.writeOptions();
        settingsDialog.close();
    }

    public void setInfoData(int songCounter, int artistCounter, int albumCounter, int totalLength)
    {
        infoLabel.setText("Number of songs: "+songCounter+"\nNumber of artists: "+artistCounter+"\nNumber of albums: "+albumCounter+"\nTotal durations: "+totalLength);
    }
}
