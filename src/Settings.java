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

public class Settings implements Runnable
{
    public static String mediaDirectory = "D:\\Documents\\Music";

    private static List<Song> songs = new ArrayList<>();
    private static List<Author> authors = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();

    @Override
    public void run()
    {
        Platform.runLater(() -> Main.getController().showLoadingDialog());
        try
        {
            Files.walk(Paths.get(Settings.mediaDirectory)).forEach(filePath ->{
            if (Files.isRegularFile(filePath) && new File(String.valueOf(filePath)).getAbsolutePath().endsWith(".mp3"))
                songs.add(new Song(filePath.toString()));});
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        for (Song s : songs)
            Platform.runLater(() -> Main.getController().allSongsListView.getItems().add(new SongView(s)));

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
        vbox.setPadding(new Insets(20,0,20,0));
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
        Platform.runLater(() -> Main.getController().hideLoadingDialog());
    }

    public static synchronized void addSong(Song song)
    {

    }

    public static void setSongs(List<Song> songs)
    {
        Settings.songs = songs;
    }

    public static List<Song> getSongs()
    {
        return songs;
    }

    public static String getMediaDirectory()
    {
        return mediaDirectory;
    }

    public static void setMediaDirectory(String mediaDirectory)
    {
        Settings.mediaDirectory = mediaDirectory;
    }

    public static List<Author> getAuthors()
    {
        return authors;
    }

    public static void setAuthors(List<Author> authors)
    {
        Settings.authors = authors;
    }

    public static List<Album> getAlbums()
    {
        return albums;
    }

    public static void setAlbums(List<Album> albums)
    {
        Settings.albums = albums;
    }

    public static void closeApplication()
    {
//        for (Author a : authors)
//        {
//            for (Album a1 : a.getAlbums())
//                for (Song s : a1.getSongs())
//                    System.out.println("Song: "+s.getTitle()+" by "+a.getAuthorName()+" album: "+a1.getAlbumTitle());
//        }
        Main.getMainStage().close();
    }

    public static Author getAuthorByName(String authorName)
    {
        for(Author a : authors)
            if (a.getAuthorName().equalsIgnoreCase(authorName))
                return a;
                return null;
    }

    public static Song getSongAssociatedWithMedia(Media media)
    {
        for (Song s : songs)
            if (s.getMedia().equals(media))
                return s;
        return null;
    }
}