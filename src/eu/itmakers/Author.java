package eu.itmakers;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Author implements SongContainer
{
    private List<Album> albums = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private String authorName;
    private BoxLayout box;

    public Author(String authorName)
    {
        this.authorName = authorName;
        this.box = new BoxLayout(authorName, this);
    }

    public BoxLayout getBox()
    {
        return box;
    }

    public void setBox(BoxLayout box)
    {
        this.box = box;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public List<Album> getAlbums()
    {
        return albums;
    }

    public void setAlbums(List<Album> albums)
    {
        this.albums = albums;
    }

    public List<Song> getSongs()
    {
        return songs;
    }

    @Override
    public Image getImage()
    {
        if (albums.size() > 0)
            for (Album a: albums)
                if (a.getImage() != null)
                    return a.getImage();
        if (songs.size() > 0)
            for (Song s: songs)
                if (s.getImage() != null)
                    return s.getImage();
        return null;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }

    public void createNewAlbum(String albumTitle)
    {
        albums.add(new Album(this, albumTitle));
    }

    public Album getAlbumByName(String albumTitle)
    {
        for (Album a : albums)
            if (a.getAlbumTitle().equalsIgnoreCase(albumTitle))
                return a;
        return null;
    }
}
