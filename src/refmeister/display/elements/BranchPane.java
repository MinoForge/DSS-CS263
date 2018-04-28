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
    private final int DEFAULT_SIZE = 10;

    /** Titles of the branches being displayed. */
    private String[] titles;

    /** Holds the single instance of this branch pane. */
    private static Pane obj;

    /**
     * Default constructor for a BranchPane object.
     */
    public BranchPane() {
        this(new String[10]);
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

    public void setBranchTitles(String[] titles) {
        this.titles = titles;
    }

    /**
     * Updates Branch Pane's children.
     */
    public void updateBranchPane() {
        for(int i = 0; i < this.titles.length; i++) {
            obj.getChildren().add(new Rectangle(3, 25, Color.BISQUE));
            Node button = new Button((titles[i]));
            button.setStyle("-fx-text-fill: bisque;" +
                    "-fx-background-color: #2F4F4F;" +
                    "-fx-font-size: 14;" +
                    "-fx-border-color: bisque;" +
                    "-fx-border-radius: 5 5 5 5 ;" +
                    "-fx-padding: 5 15 5 15;");
            obj.getChildren().add(button);
        }
    }
}
