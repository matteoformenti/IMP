import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Album implements SongContainer
{
    private List<Song> songs = new ArrayList<>();
    private String albumTitle;
    private Author author;

    public Album(Author author, String albumTitle)
    {
        this.albumTitle = albumTitle;
        this.author = author;
    }

    public String getAlbumTitle()
    {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle)
    {
        this.albumTitle = albumTitle;
    }

    public List<Song> getSongs()
    {
        return songs;
    }

    @Override
    public Image getImage()
    {
        return null;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }
}
