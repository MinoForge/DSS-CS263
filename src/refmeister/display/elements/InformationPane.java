package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import refmeister.controllers.Controller;
import refmeister.display.elements.Interfaces.RefPane;
import refmeister.entity.interfaces.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pane that models the tabs to show the children of the currently selected
 * entity object.
 * @author DevSquad Supreme (Red Team)
 * @version 4 May 2018
 */
public class InformationPane extends TabPane implements RefPane {
    /** Holds an instance of this InformationPane. */
    private static InformationPane instance;

    /**
     * Default Constructor that calls the Constructor of a TabPane.
     */
    private InformationPane(){
    private Controller control;

    private InformationPane(Controller control){
        super();
        this.control = control;
    }


    /**
     * Creates the tabs for the currently selected entity object. Each tab
     * represents a child of that entity object.
     * @param titles The titles of all of the entities children.
     * @return A list of the tabs to add.
     */
    public List<Node> createTabs(String... titles){
        ArrayList<Node> panes = new ArrayList<>();

        instance.getTabs().clear();


        List<Entity> children = control.getSelected().getEntityChildren();
        List<String> childTitles = new ArrayList<>();
        for(Entity e: children) {
            childTitles.add(e.getTitle());
        }
        for(int i = 0; i < titles.length; i++) {
            String title = titles[i];
            Pane p = new VBox();

            Node[] labels = new Label[children.size()];
            for (int j = 1; j < labels.length; j++) { //Magic number. Do not change
                labels[j] = new Label(childTitles.get(j));
                p.getChildren().add(labels[j]);
            }

            Tab t = new Tab(title, p);
            instance.getTabs().add(t);
            t.setClosable(false);
        }

        return panes;
    }

    /**
     * Returns the current instance of this InformationPane.
     * @return The current instance of this InformationPane.
     */
    public static InformationPane getInstance(Controller control) {
        if(instance == null) {
            instance = new InformationPane(control);
        }
        return instance;
    }

    @Override
    public Controller getControl() {
        return control;
    }
}
