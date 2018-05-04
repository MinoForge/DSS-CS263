package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import refmeister.display.elements.Interfaces.*;
import refmeister.entity.interfaces.Displayable;
import refmeister.entity.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Models an Options Pane to show the options that you have when looking at a
 * selected item.
 * @author Peter Gardner
 * @version 28 April 2018
 */
public class OptionsPane extends HBox implements OptionsSubject {
    /** Holds the instance of the current OptionsPane. */
    private static OptionsPane optPane;
    /** The entity for which we are displaying the options it can have. */
    private Entity theEntity;
    /** List of the observers that will be notified by changes in this
     * subject. */
    private ArrayList<OptionsObserver> obs;

    /**
     * Constructor for an OptionsPane that gets a list of buttons
     * representing the options and the entity to which those options belong.
     * @param options List of buttons representing the options.
     * @param entity Entity to which the passed options belong.
     */
    private OptionsPane(Button[] options, Entity entity) {
        super();
        if(options != null) {
            for (Button b : options) {
                this.getChildren().add(b);
            }
        }
        this.getStylesheets().add(getClass().getResource
                ("../resources/optionsPane.css").toExternalForm());
        this.getStyleClass().add("");
        optPane = this;
        theEntity = entity;
        obs = new ArrayList<OptionsObserver>();
    }

    /**
     * Returns the current instance of this OptionsPane.
     * @return The current instance of this OptionsPane.
     */
    public static OptionsPane getInstance() {
        if(optPane == null) {
            return new OptionsPane(null, null);
        }
        return optPane;
    }

    /**
     * Allows the user to select an option and notifies the observers based
     * on the option selected.
     * @param entity The entity whose options we are able to select.
     */
    public void setOpts(Entity entity) {
        List<String> optList = ((Displayable)entity).getFunc();
        Button[] options = new Button[optList.size()];
        for(int i = 0; i < optList.size(); i++) {

            String opt = optList.get(i);
            options[i] = new Button(opt);
            //options[i] = new Button("", optPane.getIcon(optList.get(i)));
            options[i].getStyleClass().add(".button");

            switch(opt) {
                case "edit":
                    options[i].setOnAction(e -> notifyObservers("edit"));
                    break;
                case "delete":
                    options[i].setOnMouseClicked(e -> notifyObservers("delete"));
                    break;
                case "sortAlphA":
                    options[i].setOnMouseClicked(e -> notifyObservers("sortAlphA"));
                    break;
                case "sortAlphD":
                    options[i].setOnMouseClicked(e -> notifyObservers("sortAlphD"));
                    break;
                case "rate":
                    options[i].setOnMouseClicked(e -> notifyObservers("rate"));
                    break;
                case "add":
                    options[i].setOnMouseClicked(e -> notifyObservers("add"));
                    break;
                case "addA":
                    options[i].setOnMouseClicked(e -> notifyObservers("addA"));
                    break;
                case "addI":
                    options[i].setOnMouseClicked(e -> notifyObservers("addI"));
                    break;
                case "generate":
                    options[i].setOnMouseClicked(e -> notifyObservers("generate"));
                    break;
                case "MLA":
                    options[i].setOnMouseClicked(e -> notifyObservers("MLA"));
                    break;
                case "APA":
                    options[i].setOnMouseClicked(e -> notifyObservers("APA"));
                    break;
            }
        }
        optPane = new OptionsPane(options, entity);
    }

    /*private Node getIcon(String iconName) {
        Image icon = new Image("../resources/" + iconName + ".png");
        Node result = new ImageView(icon);
        return result;
    }*/

    /**
     * Adds an observer to view this subject.
     * @param oo The OptionsObserver object to be added.
     * @return true if added, false otherwise.
     */
    @Override
    public boolean addObserver(OptionsObserver oo) {
        return obs.add(oo);
    }

    /**
     * Removes an observer from viewing this subject.
     * @param oo The OptionsObserver object to be removed.
     * @return true if removed, false otherwise.
     */
    @Override
    public boolean removeObserver(OptionsObserver oo) {
        return obs.remove(oo);
    }

    /**
     * Notifies each observer that is viewing this subject.
     * @param option The string of the option we are selecting.
     * @param args
     */
    @Override
    public void notifyObservers(String option, Object... args) {
        System.out.println(option);
        System.out.println(obs.get(0));
        for(OptionsObserver oo: obs) {
            oo.selectOption(option);
        }
    }
}
