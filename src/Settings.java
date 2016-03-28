import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores all the variables to correctly use IMP
 */
public class Settings implements Runnable
{
    /**
     * The location of the library
     */
    public static String mediaDirectory = "";

    /**
     * The location of the configuration file
     */
    public static final String settingsLocation = "settings.set";

    /**
     * The list of all songs
     */
    private static List<Song> songs = new ArrayList<>();

    /**
     * The list of all authors
     */
    private static List<Author> authors = new ArrayList<>();

    /**
     * The list of all albums
     */
    private static List<Album> albums = new ArrayList<>();

    /**
     * The list of all Playlists
     */
    private  static List<Playlist> playlists = new ArrayList<>();

    /**
     * The threaded initializer, this method loads all the songs in the library folder
     */
    @Override
    public void run()
    {
        try
        {
            Files.walk(Paths.get(Settings.mediaDirectory)).forEach(filePath ->{
            if (Files.isRegularFile(filePath) && new File(String.valueOf(filePath)).getAbsolutePath().endsWith(".mp3"))
                songs.add(new Song(filePath.toString()));});
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (true)
        {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            Platform.runLater(() -> Main.getController().artistListView.setFitToWidth(true));
            Platform.runLater(() -> Main.getController().artistListView.setContent(vbox));
            Platform.runLater(() -> Main.getController().artistListView.setPannable(true));
            int counter = 0;
            vbox.setSpacing(20);
            HBox hbox = new HBox();
            vbox.setFillWidth(true);
            hbox.setPrefWidth(vbox.getWidth());
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(hbox);
            vbox.setPadding(new Insets(20, 0, 20, 0));
            for (Author a : authors)
            {
                if (counter == 3)
                {
                    hbox = new HBox();
                    vbox.getChildren().add(hbox);
                    counter = 0;
                    hbox.setSpacing(20);
                    hbox.setPrefWidth(vbox.getWidth());
                    hbox.setAlignment(Pos.CENTER);
                }
                hbox.getChildren().add(a.getBox());
                counter++;
                a.getBox().resizeElement();
                a.getBox().updateBackground();
            }
        }
        Platform.runLater(() ->
        {
        if (true)
        {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            Platform.runLater(() -> Main.getController().albumsView.setFitToWidth(true));
            Platform.runLater(() -> Main.getController().albumsView.setContent(vbox));
            Platform.runLater(() -> Main.getController().albumsView.setPannable(true));
            int counter = 0;
            vbox.setSpacing(20);
            HBox hbox = new HBox();
            vbox.setFillWidth(true);
            hbox.setPrefWidth(vbox.getWidth());
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(hbox);
            vbox.setPadding(new Insets(20, 0, 20, 0));
            for (Album a : albums)
            {
                if (counter == 3)
                {
                    hbox = new HBox();
                    vbox.getChildren().add(hbox);
                    counter = 0;
                    hbox.setSpacing(20);
                    hbox.setPrefWidth(vbox.getWidth());
                    hbox.setAlignment(Pos.CENTER);
                }
                hbox.getChildren().add(a.getBox());
                counter++;
                a.getBox().resizeElement();
                a.getBox().updateBackground();
            }
        }});
        Platform.runLater(() -> Main.getController().hideLoadingDialog());
        Platform.runLater(() -> Main.getController().setInfoData(songs.size(), authors.size(), albums.size(), 0));
    }

    /**
     * This method is used to add a song to the list
     * @param song The song to add
     */
    public static synchronized void addSong(Song song)
    {
        Platform.runLater(() -> Main.getController().allSongsListView.getItems().add(new SongView(song)));
        Platform.runLater(() -> song.getSongView().update());
    }

    /**
     * Getter for the songs list
     * @return The list of song
     */
    public static List<Song> getSongs()
    {
        return songs;
    }

    /**
     * Getter for the Media Directory
     * @return The directory
     */
    public static String getMediaDirectory()
    {
        return mediaDirectory;
    }

    /**
     * Setter for the MediaDirectory property
     * @param mediaDirectory The directory
     */
    public static void setMediaDirectory(String mediaDirectory)
    {
        Settings.mediaDirectory = mediaDirectory;
    }

    /**
     * Getter for the list of authors
     * @return The list of authors
     */
    public static List<Author> getAuthors()
    {
        return authors;
    }

    /**
     * Getter for the albums list
     * @return The list of albums
     */
    public static List<Album> getAlbums()
    {
        return albums;
    }

    /**
     * Close the application
     */
    public static void closeApplication(){Main.getMainStage().close();}

    /**
     * This method returns the {@link Author} that has the name in the parameter
     * @param authorName The author name
     * @return Null is no author is found, an {@link Author} if something is found
     */
    public static Author getAuthorByName(String authorName)
    {
        for(Author a : authors)
            if (a.getAuthorName().equalsIgnoreCase(authorName))
                return a;
                return null;
    }

    /**
     * This method returns the {@link Album} that correspond to the albumName
     * @param albumName The anme of the Album
     * @return Null is no album is found, an {@link Album} if something is found
     */
    public static Album getAlbumByName(String albumName)
    {
        for (Author a : authors)
            if (a.getAlbumByName(albumName) != null)
                return a.getAlbumByName(albumName);
        return null;
    }

    /**
     * This method returns the song associated with a Media
     * @param media The media
     * @return The song associated with that media
     */
    public static Song getSongAssociatedWithMedia(Media media)
    {
        for (Song s : songs)
            if (s.getMedia().equals(media))
                return s;
        return null;
    }
}