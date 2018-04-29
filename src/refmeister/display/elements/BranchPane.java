package refmeister.display.elements;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


/**
 * The left most box in the window that shows the branch structure of the
 * current directory you're in.
 * @author Brandon Townsend
 * @version 28 April 2018
 */
public class BranchPane extends VBox{
    /** Holds the single instance of this branch pane. */
    private static BranchPane obj;


    /**
     * Constructor in which a string array of titles are passed in. Spacing
     * and alignment are also set up properly.
     */
    private BranchPane() {
        setSpacing(3);
        obj = this;
        this.getStylesheets().add(getClass().getResource("../resources/branchPane.css")
                .toExternalForm());
        this.getStyleClass().add("branchpane");
    }

    /**
     * Returns the current instance of BranchPane.
     * @return the current instance of BranchPane.
     */
    public static BranchPane getInstance() {
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
     * Updates the output of this branch pane's children.
     * @return Returns an array of the buttons that can be accessed.
     */
    public Node[] updateBranchPane(List<String> titles) {
        new BranchPane();
        Node[] buttons = new Button[titles.size()];
        for(int i = 0; i < titles.size(); i++) {
            obj.getChildren().add(new Rectangle(3, 25, Color.BISQUE));
            Node button = new Button((titles.get(i)));
            buttons[i] = button;
            obj.getChildren().add(button);
        }
        return buttons;
    }
}
