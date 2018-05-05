package refmeister.display.elements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import refmeister.controllers.Controller;
import refmeister.display.elements.Interfaces.RefObserver;
import refmeister.display.elements.Interfaces.RefPane;
import refmeister.display.elements.Interfaces.RefSubject;
import refmeister.entity.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;


/**
 * The left most box in the window that shows the branch structure of the
 * current directory you're in.
 * @author Brandon Townsend
 * @version 28 April 2018
 */
public class BranchPane extends Pane implements RefPane {
    /** Holds the single instance of this branch pane. */
    private static BranchPane obj;
    /** List of observers that are observing this BranchPane. */
    private ArrayList<RefObserver> obs;
    /** A reference to the controller. */
    private final Controller control;

    private VBox inner;

    /**
     * Constructor in which a string array of titles are passed in. Spacing
     * and alignment are also set up properly.
     * @param control The controller to be assigned to the control field.
     */
    private BranchPane(Controller control) {
        this.control = control;
        inner = new VBox();
        inner.setSpacing(3);
        inner.setPadding(new Insets(0, 10, 10, 10));
        this.setBackground(new Background(new BackgroundFill(
                Color.DARKSLATEGREY, new CornerRadii(0), Insets.EMPTY)));
        this.getStylesheets().add(getClass().getResource("../resources/branchPane.css")
                .toExternalForm());
        this.getStyleClass().add("branchpane");
        inner.getStyleClass().add("branchpane");
        getChildren().add(inner);
        obj = this;
    }

    /**
     * Returns the current instance of BranchPane.
     * @return the current instance of BranchPane.
     */
    public static BranchPane getInstance(Controller control) {
        if(obj == null) {
            obj = new BranchPane(control);
        }
        return obj;
    }

    /**
     * Updates the output of this branch pane's children.
     * @return Returns an array of the buttons that can be accessed.
     */
    public Node[] updateBranchPane() {
        obj = new BranchPane(control);
        List<Entity> branch = control.getBranch();
        Node[] buttons = new Button[branch.size()];
        for(int i = branch.size() - 1; i >= 0; i--) {
            obj.inner.getChildren().add(new Rectangle(3, 25, Color.BISQUE));
            Entity temp = branch.get(i);
            Button button = new Button(temp.getTitle());
            buttons[i] = button;
            button.setOnMouseClicked(e -> control.setSelected(temp));
            obj.inner.getChildren().add(button);
            Tooltip tTip = new Tooltip("Select " + temp.getTitle());
            Tooltip.install(button, tTip);

            obj.getChildren().add(button);
        }
        return buttons;
    }

    /**
     * TODO
     * @return
     */
    public Controller getControl() {
        return control;
    }
}
