package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import refmeister.entity.Interfaces.Entity;

import java.util.List;

/**
 * Models an Options Pane to show the options that you have when looking at a
 * selected item.
 * @author
 * @version 28 April 2018
 */
public class OptionsPane extends TilePane {

    private static OptionsPane optPane;

    private OptionsPane(Button[] options) {
        this.getChildren().addAll(options);
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
            options[i] = new Button("", optPane.getIcon(optList.get(i)));
        }

        optPane = new OptionsPane(options);


    }

    private Node getIcon(String iconName) {
        Node icon = new
    }

}
