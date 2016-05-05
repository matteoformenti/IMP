package eu.itmakers;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;

/**
 * This object is a song, it contains all the information of a single track
 */
public class Song
{
    /**
     * The path of the media
     */
    private String path;
    /**
     * The {@link Media} of the song
     */
    private Media media;
    /**
     * The duration in seconds
     */
    private int lengthInSeconds;
    /**
     * The artist
     */
    private String artist;
    /**
     * The title
     */
    private String title;
    /**
     * The album name
     */
    private String album;
    /**
     * The cover image
     */
    private Image image;
    /**
     * The {@link SongView} associated with this song
     */
    private SongView songView;

    /**
     * Default constructor
     * @param resource The location of the song
     */
    public Song(String resource)
    {
        path = resource;
        try
        {
            media = new Media(new File(resource).toURI().toURL().toString());
            getMetadata();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method gets all the metadata from the {@link Media} and saves them
     */
    public void getMetadata()
    {
        final MediaPlayer mp = new MediaPlayer(media);
        mp.setOnReady(() ->
        {
            lengthInSeconds = (int) media.getDuration().toSeconds();
            setArtist((String) media.getMetadata().get("artist"));
            setImage((Image) media.getMetadata().get("image"));
            setTitle((String) media.getMetadata().get("title"));
            setAlbum((String) media.getMetadata().get("album"));
            lengthInSeconds = (int)media.getDuration().toSeconds();
            if (title == null)
                setTitle(path.split("\\\\")[path.split("\\\\").length - 1]);
            if (artist == null)
                setArtist("Unknown");
            if (album == null)
                setAlbum("Unknown");
            if (Settings.getAuthorByName(artist) != null)
            {
                Settings.getAuthorByName(artist).getSongs().add(this);
                if (Settings.getAuthorByName(artist).getAlbumByName(album) != null)
                    Settings.getAuthorByName(artist).getAlbumByName(album).getSongs().add(this);
                else
                {
                    Album a = new Album(Settings.getAuthorByName(artist), album);
                    Settings.getAuthorByName(artist).getAlbums().add(a);
                    Settings.getAlbums().add(a);
                    a.getSongs().add(this);
                }
            }
            else
            {
                Author a = new Author(artist);
                Album album = new Album(a, getAlbum());
                Settings.getAlbums().add(album);
                album.getSongs().add(this);
                Settings.getAuthors().add(a);
                a.getAlbums().add(album);
                a.getSongs().add(this);
            }
            Settings.addSong(this);
        });
    }

    /**
     * Getter for the {@link Media}
     * @return the Media
     */
    public Media getMedia()
    {
        return media;
    }

    /**
     * Getter for the length
     * @return The duration
     */
    public int getLengthInSeconds()
    {
        return lengthInSeconds;
    }

    /**
     * Setter for the artist
     * @param artist The artist to set
     */
    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    /**
     * Getter for the artist
     * @return The artist
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * Setter for the title
     * @param title The title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Getter for the title
     * @return The title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Setter for the album
     * @param album The album
     */
    public void setAlbum(String album)
    {
        this.album = album;
    }

    /**
     * Getter for the album
     * @return The album
     */
    public String getAlbum()
    {
        return album;
    }

    /**
     * Setter for the poster image
     * @param image The poster {@link Image}
     */
    public void setImage(Image image)
    {
        this.image = image;
    }

    /**
     * Getter for the image
     * @return The image
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Setter for the {@link SongView}
     * @param songView The songView
     */
    public void setSongView(SongView songView)
    {
        this.songView = songView;
    }

    /**
     * Getter for the {@link SongView}
     * @return  The SongView
     */
    public SongView getSongView() {return songView;}
}
