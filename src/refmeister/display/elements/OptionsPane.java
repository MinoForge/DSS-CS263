package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import refmeister.entity.Interfaces.Entity;

import java.util.List;

/**
 * Models an Options Pane to show the options that you have when looking at a
 * selected item.
 * @author
 * @version 28 April 2018
 */
public class OptionsPane extends HBox {

    private static OptionsPane optPane;

    private OptionsPane(Button[] options) {
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
    }

    public static OptionsPane getInstance() {
        if(optPane == null) {
            return new OptionsPane(null);
        }
        return optPane;
    }


    public void setOpts(List<String> optList) {
        Button[] options = new Button[optList.size()];
        for(int i = 0; i < optList.size(); i++) {
//            options[i] = new Button("", optPane.getIcon(optList.get(i)));
            options[i] = new Button(optList.get(i).charAt(0) + "");
            //,
            //optPane.getIcon(optList.get(i))
        }

//        options[0] = new Button();
//        options[0].getStyleClass().add("delete-button");
//        options[1] = new Button();
//        options[1].getStyleClass().add("save-button");

        optPane = new OptionsPane(options);


    }

    private Node getIcon(String iconName) {
        Image icon = new Image("/resources/" + iconName + ".png");
        Node result = new ImageView(icon);
        return result;
    }

}
