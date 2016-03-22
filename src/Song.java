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

    public Image getImage()
    {
        return image;
    }

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
            if (title == null)
                setTitle(path.split("\\\\")[path.split("\\\\").length-1]);
            if (artist == null)
                setArtist("Unknown");
            Settings.addSong(this);
        });
    }

    public Media getMedia()
    {
        return media;
    }

    public void setMedia(Media media)
    {
        this.media = media;
    }

    public int getLengthInSeconds()
    {
        return lengthInSeconds;
    }

    public void setLengthInSeconds(int lengthInSeconds)
    {
        this.lengthInSeconds = lengthInSeconds;
    }

    public int getBitrate()
    {
        return bitrate;
    }

    public void setBitrate(int bitrate)
    {
        this.bitrate = bitrate;
    }

    public int getSampleRate()
    {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate)
    {
        this.sampleRate = sampleRate;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getTrack()
    {
        return track;
    }

    public void setTrack(String track)
    {
        this.track = track;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
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
}
