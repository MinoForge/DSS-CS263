package refmeister.display.elements;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The left most box in the window that shows the branch structure of the
 * current directory you're in.
 * @author Brandon Townsend
 * @version 28 April 2018
 */
public class BranchPane extends VBox{
    /** Default size of a branch pane's array of titles. */
    private static final int DEFAULT_SIZE = 10;

    /** Titles of the branches being displayed. */
    private String[] titles;

    /** Holds the single instance of this branch pane. */
    private static Pane obj;

    /**
     * Default constructor for a BranchPane object.
     */
    public BranchPane() {
        this(new String[DEFAULT_SIZE]);
    }

    public BranchPane(String[] titles) {
        this.titles = titles;
        setSpacing(3);
        setAlignment(Pos.TOP_CENTER);
        obj = this;
    }

    /**
     * Returns the current instance of BranchPane.
     * @return the current instance of BranchPane.
     */
    public static Pane getInstance() {
        if(obj == null) {
            synchronized (Pane.class) {
                if(obj == null) {
                    obj = new BranchPane();
                }
            }
        }
        return obj;
    }

    /**
     * Sets the new titles for each of the Branch.
     * @param titles The new string of titles to be set.
     */
    public void setBranchTitles(String[] titles) {
        this.titles = titles;
    }

    /**
     * Updates the output of this branch pane's children.
     * @return Returns an array of the buttons that can be accessed.
     */
    public Node[] updateBranchPane() {
        Node[] buttons = new Button[this.titles.length];
        for(int i = 0; i < this.titles.length; i++) {
            obj.getChildren().add(new Rectangle(3, 25, Color.BISQUE));
            Node button = new Button((titles[i]));
            button.setStyle("-fx-text-fill: bisque;" +
                    "-fx-background-color: #2F4F4F;" +
                    "-fx-font-size: 14;" +
                    "-fx-border-color: bisque;" +
                    "-fx-border-radius: 5 5 5 5 ;" +
                    "-fx-padding: 5 15 5 15;");
            buttons[i] = button;
            obj.getChildren().add(button);
        }
        return buttons;
    }
}