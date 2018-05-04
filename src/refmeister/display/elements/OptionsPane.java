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

    private static OptionsPane optPane;

    private Entity theEntity;

    private ArrayList<OptionsObserver> obs;

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

    public static OptionsPane getInstance() {
        if(optPane == null) {
            return new OptionsPane(null, null);
        }
        return optPane;
    }


    public void setOpts(Entity entity) {
        List<String> optList = ((Displayable)entity).getFunc();
        Button[] options = new Button[optList.size()];
        for(int i = 0; i < optList.size(); i++) {

            String opt = optList.get(i);
            options[i] = new Button(opt);
            options[i].setPrefWidth(Double.MAX_VALUE);
            options[i].setPrefHeight(Double.MAX_VALUE);

 //           options[i] = new Button("", optPane.getIcon(optList.get(i)));
            switch(opt) {
                case "edit":
                    options[i].setOnAction(e -> notifyObservers("edit"));
//                    buttonImg(options[i], "../resources/toolPencil.png");
                    break;
                case "delete":
                    options[i].setOnMouseClicked(e -> notifyObservers("delete"));
                    imageHover(options[i], "../resources/trashcan.png",
                            "../resources/trashcanOpen.png");
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
//                    buttonImg(options[i], "../resources/DPAD.png");
                    break;
                case "addA":
                    options[i].setOnMouseClicked(e -> notifyObservers("addA"));
//                    buttonImg(options[i], "../resources/DPAD.png");
                    break;
                case "addI":
                    options[i].setOnMouseClicked(e -> notifyObservers("addI"));
//                    buttonImg(options[i], "../resources/DPAD.png");
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
                case "quit":
                    options[i].setOnAction((ev)->Platform.exit());
            }
        }
//TODO put all these styles inside the switch statement
//        options[0] = new Button();
//        options[0].getStyleClass().add("delete-button");
//        options[1] = new Button();
//        options[1].getStyleClass().add("save-button");

        optPane = new OptionsPane(options, entity);
    }

    private void imageHover(Button b, String old, String newImg){
        ImageView norm = new ImageView(new Image(getClass().getResourceAsStream(old), 25,25,
                true, false));
        ImageView hov = new ImageView(new Image(getClass().getResourceAsStream(newImg), 25, 25,
                true, false));
        b.setGraphic(norm);
        b.setOnMouseEntered((ev)-> b.setGraphic(hov));
        b.setOnMouseExited((ev) -> b.setGraphic(norm));
    }

    private void buttonImg(Button b, String img){
        ImageView hov = new ImageView(new Image(getClass().getResourceAsStream(img), 25, 25,
                true, false));
        b.setGraphic(hov);
    }

    private Node getIcon(String iconName) {
        Image icon = new Image("/resources/" + iconName + ".png");
        Node result = new ImageView(icon);
        return result;
    }

    @Override
    public boolean addObserver(OptionsObserver oo) {
        return obs.add(oo);
    }

    @Override
    public boolean removeObserver(OptionsObserver oo) {
        return obs.remove(oo);
    }

    @Override
    public void notifyObservers(String option, Object... args) {
        System.out.println(option);
        System.out.println(obs.get(0));
        for(OptionsObserver oo: obs) {
            oo.selectOption(option);
        }
    }
}
