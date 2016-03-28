import javafx.scene.image.Image;

import java.util.List;

/**
 * This interface is used to correctly build the {@link BoxLayout}
 */
public interface SongContainer
{
    /**
     * A getter for all the song inside the element
     */
    List<Song> getSongs();

    /**
     * A getter for the background image
     */
    Image getImage();
}
