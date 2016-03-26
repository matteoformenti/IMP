import java.util.ArrayList;
import java.util.List;

public class Author
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
