import javafx.application.Platform;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Settings implements Runnable
{
    private static List<Song> songs = new ArrayList<>();
    public static String mediaDirectory = "D:\\Documents\\Music";

    public static List<Song> getSongs()
    {
        return songs;
    }

    public static void setSongs(List<Song> songs)
    {
        Settings.songs = songs;
    }

    @Override
    public void run()
    {
        try
        {
            Files.walk(Paths.get(Settings.mediaDirectory)).forEach(filePath ->{
            if (Files.isRegularFile(filePath) && new File(String.valueOf(filePath)).getAbsolutePath().endsWith(".mp3"))
                songs.add(new Song(filePath.toString()));
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static synchronized void addSong(Song song)
    {
        Platform.runLater(() ->
        {
            Main.getController().allSongsListView.getItems().add(new SongView(song));
        });
    }
}