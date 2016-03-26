import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;

public class Playlist
{
    private Queue<Song> songs = new LinkedList<>();
    private Song playingSong;
    private String name;
    private Image icon;

    public Playlist(String name)
    {
        this.name = name;
    }

    public void addSong(Song song)
    {
        songs.add(song);
    }

    public void playPlaylist()
    {
        playingSong = songs.element();
    }
}
