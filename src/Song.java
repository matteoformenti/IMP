import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;

public class Song
{
    private String path;
    private Media media;
    private int lengthInSeconds;
    private int bitrate;
    private int sampleRate;
    private String track;
    private String artist;
    private String title;
    private String album;
    private String year;
    private String genre;
    private Image image;
    private SongView songView;

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
            if (title == null)
            {
                setTitle(path.split("\\\\")[path.split("\\\\").length - 1]);
            }
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

    public void setMedia(Media media)
    {
        this.media = media;
    }

    public Media getMedia()
    {
        return media;
    }

    public void setLengthInSeconds(int lengthInSeconds)
    {
        this.lengthInSeconds = lengthInSeconds;
    }

    public int getLengthInSeconds()
    {
        return lengthInSeconds;
    }

    public void setBitrate(int bitrate)
    {
        this.bitrate = bitrate;
    }

    public int getBitrate()
    {
        return bitrate;
    }

    public void setSampleRate(int sampleRate)
    {
        this.sampleRate = sampleRate;
    }

    public int getSampleRate()
    {
        return sampleRate;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }

    public void setTrack(String track)
    {
        this.track = track;
    }

    public String getTrack()
    {
        return track;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getAlbum()
    {
        return album;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Image getImage()
    {
        return image;
    }

    public void setSongView(SongView songView)
    {
        this.songView = songView;
    }

    public SongView getSongView() {return songView;}
}
