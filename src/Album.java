import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Album implements SongContainer
{
    private List<Song> songs = new ArrayList<>();
    private String albumTitle;
    private Author author;
    private BoxLayout box;

    public Album(Author author, String albumTitle)
    {
        this.albumTitle = albumTitle;
        this.author = author;
        box = new BoxLayout(albumTitle, this);
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
        if (songs.size() > 0)
            for (Song s: songs)
                if (s.getImage() != null)
                    return s.getImage();
        return null;
    }

    public BoxLayout getBox()
    {
        return box;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }
}
