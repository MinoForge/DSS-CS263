package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.List;

/**
 * Pane that models the tabs to show the children of the currently selected
 * entity object.
 * @author DevSquad Supreme (Red Team)
 * @version 4 May 2018
 */
public class InformationPane extends TabPane {
    /** Holds an instance of this InformationPane. */
    private static InformationPane instance;

    /**
     * Default Constructor that calls the Constructor of a TabPane.
     */
    private InformationPane(){
        super();
    }

    /**
     * Creates the tabs for the currently selected entity object. Each tab
     * represents a child of that entity object.
     * @param titles The titles of all of the entities children.
     * @return A list of the tabs to add.
     */
    public List<Node> createTabs(String... titles){
        ArrayList<Node> panes = new ArrayList<>();

        for(int i = 0; i < titles.length; i++){
            String title = titles[i];
            Pane p = new Pane();
        }
        return panes;
    }

    /**
     * Returns the current instance of this InformationPane.
     * @return The current instance of this InformationPane.
     */
    public static InformationPane getInstance() {
        if(instance == null)
            instance = new InformationPane();
        return instance;
    }
}
