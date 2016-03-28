import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * This object is a Playlist, it implements the {@link SongContainer} inteface so it can be displayed in a {@link BoxLayout}
 */
public class Playlist implements SongContainer
{
    /**
     * The list of songs in the playlist
      */
    private List<Song> songs = new ArrayList<>();
    /**
     * The song currently playing
     */
    private Song playingSong;
    /**
     * The playlist name
     */
    private String name;
    /**
     * The playlist icon
     */
    private Image icon;

    /**
     * Default constructor fo {@link Playlist}
     * @param name The name of the playlist
     */
    public Playlist(String name){this.name = name;}

    /**
     * This method is used to add a song to the playlist
     * @param song The song to add
     */
    public void addSong(Song song){songs.add(song);}

    /**
     * This method is used to remove a song
     * @param song The song to remove
     */
    public void removeSong(Song song) {songs.remove(song);}

    /**
     * Implemented getter for the song list
     * @return The list of songs
     */
    @Override
    public List<Song> getSongs(){return songs;}

    /**
     * Implemented getter for the image
     * @return The background image
     */
    @Override
    public Image getImage(){return icon;}
}
