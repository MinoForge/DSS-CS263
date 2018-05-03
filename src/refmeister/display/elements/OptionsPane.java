package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import refmeister.display.elements.Interfaces.*;
import refmeister.entity.Interfaces.Displayable;
import refmeister.entity.Interfaces.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Models an Options Pane to show the options that you have when looking at a
 * selected item.
 * @author Peter Gardner
 * @version 28 April 2018
 */
public class OptionsPane extends HBox
        implements OptionsSubject {

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
        this.getStyleClass().add("optionspane");
        optPane = this;
        theEntity = entity;
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
            options[i] = new Button(opt.charAt(0) + "");
//            options[i] = new Button("", optPane.getIcon(optList.get(i)));
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
//TODO put all these styles inside the switch statement
//        options[0] = new Button();
//        options[0].getStyleClass().add("delete-button");
//        options[1] = new Button();
//        options[1].getStyleClass().add("save-button");

        optPane = new OptionsPane(options, entity);
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
