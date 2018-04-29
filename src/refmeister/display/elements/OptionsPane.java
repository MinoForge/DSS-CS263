package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import refmeister.entity.Interfaces.Entity;

import java.util.List;

public class OptionsPane extends TilePane {

    private static OptionsPane optPane;

    Button[] optionButtons;

    private OptionsPane() {

    }

    public static OptionsPane getInstance() {
        if(optPane == null) {
            return new OptionsPane();
        }
        return optPane;
    }


    public void setOpts(List<String> optList) {
        Button[] options = new Button[optList.size()];
        for(int i = 0; i < optList.size(); i++) {
            options[i] = new Button("", optPane.getIcon(optList.get(i)));
        }


    }

    private Node getIcon(String iconName) {
        Node icon = new
    }

}
