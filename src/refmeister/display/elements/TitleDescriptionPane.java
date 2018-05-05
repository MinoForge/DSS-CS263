package refmeister.display.elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * A Pane that contains the title, description, and options buttons for the
 * entity we are currently looking at.
 * @author DevSquadSupreme (Red Team)
 * @version 4 May 2018
 */
public class TitleDescriptionPane extends VBox {
    /** The title of the current entity. */
    private Label title;
    /** The description of the current entity. */
    private Label desc;
    /** The rating of the current description if it has one. */
    private Label rating;
    /** The list of options buttons that each entity has. */
    private OptionsPane option;
    /** The current instance of a TitleDescriptionPane. */
    private static TitleDescriptionPane obj;

    /**
     * Default constructor for a TitleDescriptionPane object.
     */
    private TitleDescriptionPane(){
        this(null, "TITLE", "DESCRIPTION");
    }

    /**
     * Constructor that gets passed the list of options buttons that can be
     * used by the current entity.
     * @param option The OptionsPane that contains all of the options that
     *               are open to this entity.
     */
    private TitleDescriptionPane(OptionsPane option){
        this(option, "TITLE","DESCRIPTION");
    }

    /**
     * Constructor that gets passed the list of options buttons that can be
     * used by the current entity and a list of the attributes that the
     * entity has.
     * @param option The OptionsPane that contains all of the options that
     *               are open to this entity.
     * @param attributes The list of attributes that the entity has.
     */
    private TitleDescriptionPane(OptionsPane option ,String... attributes) {
        AnchorPane anchor = new AnchorPane();
        this.option = option;

        this.setPadding(new Insets(10));

        this.title = new Label(attributes[0]);
        this.desc = new Label(attributes[1]);
        this.title.setMaxWidth(250);

        if(attributes.length < 3){
            AnchorPane.setLeftAnchor(this.title, 5.0);
            if(this.option != null) {
                AnchorPane.setRightAnchor(this.option, .0);
            }
            if(this.option != null) {
                anchor.getChildren().addAll(this.title, this.option);
            }
            this.getChildren().addAll(anchor, this.desc);
        }
        else {
            this.rating = new Label(attributes[2]);
            HBox left = new HBox();
            left.getChildren().addAll(title, rating);
            AnchorPane.setLeftAnchor(left, 5.0);
            AnchorPane.setRightAnchor(this.option, .0);

            anchor.getChildren().addAll(left, this.option);
            this.getChildren().addAll(anchor, this.desc);
        }
        obj = this;
    }


    /**
     * Returns the current instance of TitleDescriptionPane.
     * @return the current instance of TitleDescriptionPane.
     */
    public static TitleDescriptionPane getInstance() {
        if(obj == null) {
            synchronized (Pane.class) {
                if(obj == null) {
                    obj = new TitleDescriptionPane();
                }
            }
        }
        return obj;
    }

    /**
     * Sets the attributes of the entity that is currently being displayed by
     * the TitleDescriptionPane.
     * @param attributes The string array containing the new attribute values.
     * @param options The OptionsPane containing the new options values.
     */
    public void setTitleDescPane(String[] attributes, OptionsPane options) {
        for(int i = 0; i < attributes.length; i++){
            if(i == 0)
                this.title = new Label(attributes[i]);
            if(i == 1)
                this.desc = new Label(attributes[i]);
            if(i == 2)
                this.rating = new Label(attributes[i]);
        }

        TitleDescriptionPane.obj = new TitleDescriptionPane(options, attributes);

        this.option = options;
    }

}
