package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import refmeister.entity.Interfaces.Entity;

import java.util.List;

public class OptionsPane extends TilePane {

    private static OptionsPane optPane;

    private OptionsPane(Button[] options) {
        if(options != null) {
            for (Button b : options) {
                this.getChildren().add(b);
            }
        }
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

        optPane = new OptionsPane(options);


    }

    private Node getIcon(String iconName) {
        Image icon = new Image("/resources/" + iconName + ".png");
        Node result = new ImageView(icon);
        return result;
    }

}
