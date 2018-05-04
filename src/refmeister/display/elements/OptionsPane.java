package refmeister.display.elements;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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
public class OptionsPane extends TilePane implements OptionsSubject {
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
        this.setPrefRows(2);
        this.setPrefColumns(4);
        this.setPrefTileWidth(100);
        this.setPrefTileHeight(40);
        theEntity = entity;
        this.setAlignment(Pos.CENTER_RIGHT);
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
            options[i].setPrefWidth(Double.MAX_VALUE);
            options[i].setPrefHeight(Double.MAX_VALUE);
            options[i] = new Button("");
            switch(opt) {
                case "Edit":
                    options[i].setOnAction(e -> notifyObservers("Edit"));
                    buttonImg(options[i], "../resources/toolPencil.png");
                    break;
                case "Delete":
                    options[i].setOnMouseClicked(e -> notifyObservers("Delete"));
                    imageHover(options[i], "../resources/trashcan.png",
                            "../resources/trashcanOpen.png");
                    break;
                case "Sort A-Z":
                    options[i].setOnMouseClicked(e -> notifyObservers("Sort A-Z"));
                    break;
                case "Sort Z-A":
                    options[i].setOnMouseClicked(e -> notifyObservers("Sort Z-A"));
                    break;
                case "Rate":
                    options[i].setOnMouseClicked(e -> notifyObservers("Rate"));
                    break;
                case "Add":
                    options[i].setOnMouseClicked(e -> notifyObservers("Add"));
                    buttonImg(options[i], "../resources/DPAD.png");
                    break;
                case "Add Argument":
                    options[i].setOnMouseClicked(e -> notifyObservers("Add Argument"));
                    buttonImg(options[i], "../resources/DPAD.png");
                    break;
                case "Add Idea":
                    options[i].setOnMouseClicked(e -> notifyObservers("Add Idea"));
                    buttonImg(options[i], "../resources/DPAD.png");
                    break;
                case "Edit Reference Data":
                    options[i].setOnMouseClicked(e -> notifyObservers("Edit Reference Data"));
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

    /**
     * Updates the image if a mouse hovers over the button
     * @param b The button whose image is being updated.
     * @param old The old image to be replaced.
     * @param newImg The new image to be displayed.
     */
    private void imageHover(Button b, String old, String newImg){
        ImageView norm = new ImageView(new Image(getClass().getResourceAsStream(old), 25,25,
                true, false));
        ImageView hov = new ImageView(new Image(getClass().getResourceAsStream(newImg), 25, 25,
                true, false));
        b.setGraphic(norm);
        b.setOnMouseEntered((ev)-> b.setGraphic(hov));
        b.setOnMouseExited((ev) -> b.setGraphic(norm));
    }

    /**
     * Retrieves an image from memory.
     * @param b The button who contains the image.
     * @param img The image to retrieve.
     */
    private void buttonImg(Button b, String img){
        ImageView hov = new ImageView(new Image(getClass().getResourceAsStream(img), 25, 25,
                true, false));
        b.setGraphic(hov);
    }

    /*private Node getIcon(String iconName) {
        Image icon = new Image("/resources/" + iconName + ".png");
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
//        System.out.println(option);
//        System.out.println(obs.get(0));
        for(OptionsObserver oo: obs) {
            oo.selectOption(option);
        }
    }
}
