import javafx.application.Platform;

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
}