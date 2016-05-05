package eu.itmakers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * This object is used to represent a collection of songs, like a Playlist or an album
 */
public class BoxLayout extends AnchorPane
{
    /**
     * The title of the collection
     */
    private String text;

    /**
     * A default dimension for the box (the box is dynamically resized)
     */
    private double dimension = 100;

    /**
     * The popup dialog
     */
    private JFXDialog dialog;

    /**
     * The background with a default icon
     */
    ImageView background = new ImageView(new Image(String.valueOf(getClass().getResource("icons/headphones.png"))));

    /**
     * The container of songs
     */
    SongContainer container;

    /**
     * The default constructor
     * @param container The {@link SongContainer}
     * @param title The title of the collection
     */
    public BoxLayout(String title, SongContainer container)
    {
        this.text = title;
        this.container = container;
        Label textLabel = new Label(text);
        textLabel.setFont(new Font("Roboto", 15));
        textLabel.setTextFill(Color.WHITE);
        textLabel.setPadding(new Insets(1));
        textLabel.setAlignment(Pos.CENTER);
        if (container.getImage() != null)
            background.setImage(container.getImage());
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
        background.toBack();
        this.setBottomAnchor(textLabel, 0.0);
        this.setLeftAnchor(textLabel, 0.0);
        this.setRightAnchor(textLabel, 0.0);
        textLabel.setStyle("-fx-background-color: #2196F3");
        this.getChildren().add(background);
        this.getChildren().add(textLabel);
        this.setPrefWidth(dimension);
        this.setStyle("-fx-background-color: #fefefe");
        this.setEffect(new DropShadow(10, new Color(0.01,0.01,0.01,0.25)));
        this.setOnMouseClicked((event) ->
        {
            dialog = new JFXDialog();
            JFXDialogLayout layout = new JFXDialogLayout();
            JFXListView<SongView> songList = new JFXListView<SongView>();
            dialog.setContent(layout);
            layout.setBody(songList);
            layout.setHeading(new Label(title));
            for (Song s : container.getSongs())
                songList.getItems().add(s.getSongView());
            dialog.setPrefHeight(Main.getMainStage().getHeight());
            dialog.setPrefWidth(Main.getMainStage().getWidth());
            dialog.show(Main.getController().topPane);
        });
    }

    /**
     * This method is used to resize the BoxLayout at runtime
     */
    public void resizeElement()
    {
        this.dimension = (Main.getMainStage().getWidth()/12)*3;
        this.setPrefHeight(dimension);
        this.setPrefWidth(dimension);
        background.setFitWidth(dimension);
        background.setFitHeight(dimension);
    }

    /**
     * This method is used to update the background
     */
    public void updateBackground()
    {
        if (container.getImage() != null)
            background.setImage(container.getImage());
    }
}
