import javafx.application.Platform;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Settings implements Runnable
{
    public static String mediaDirectory = "D:\\Documents\\Music";

    private static List<Song> songs = new ArrayList<>();
    public static List<Author> authors = new ArrayList<>();
    public static List<Album> albums = new ArrayList<>();

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
    }

    public static synchronized void addSong(Song song)
    {
        Platform.runLater(() -> Main.getController().allSongsListView.getItems().add(new SongView(song)));
    }

    public static void setSongs(List<Song> songs)
    {
        Settings.songs = songs;
    }

    public static List<Song> getSongs()
    {
        return songs;
    }
}