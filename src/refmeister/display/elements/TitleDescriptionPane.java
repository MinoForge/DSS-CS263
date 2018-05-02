package refmeister.display.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TitleDescriptionPane extends VBox {

    private Label title;

    private Label desc;

    private Label rating;

    private OptionsPane option;

    private static TitleDescriptionPane obj;

    /**
     * Default constructor for a TitleDescriptionPane object.
     */
    private TitleDescriptionPane(){
        this(null, "TITLE", "DESCRIPTION");
    }

    private TitleDescriptionPane(OptionsPane option){
        this(option, "TITLE","DESCRIPTION");
    }



    private TitleDescriptionPane(OptionsPane option ,String... attributes) {
        AnchorPane anchor = new AnchorPane();
        this.option = option;

        this.title = new Label(attributes[0]);
        this.desc = new Label(attributes[1]);
        if(attributes.length < 3){
            AnchorPane.setLeftAnchor(this.title, 5.0);
            if(this.option != null) {
                AnchorPane.setRightAnchor(this.option, .0);
            }
            if(this.option != null) {
                anchor.getChildren().addAll(this.title, this.option);
            }
            this.getChildren().addAll(anchor, this.desc);
        }
        else {

            this.rating = new Label(attributes[2]);
            HBox left = new HBox();
            left.getChildren().addAll(title, rating);
            AnchorPane.setLeftAnchor(left, 5.0);
            AnchorPane.setRightAnchor(this.option, .0);

            anchor.getChildren().addAll(left, this.option);
            this.getChildren().addAll(anchor, this.desc);
        }
        obj = this;

    }


    /**
     * Returns the current instance of TitleDescriptionPane.
     * @return the current instance of TitleDescriptionPane.
     */
    public static TitleDescriptionPane getInstance() {
        if(obj == null) {
            synchronized (Pane.class) {
                if(obj == null) {
                    obj = new TitleDescriptionPane();
                }
            }
        }
        return obj;
    }

    public void setTitleDescPane(String[] attributes, OptionsPane options) {
        for(int i = 0; i < attributes.length; i++){
            if(i == 0)
                this.title = new Label(attributes[i]);
            if(i == 1)
                this.desc = new Label(attributes[i]);
            if(i == 2)
                this.rating = new Label(attributes[i]);
        }

        TitleDescriptionPane.obj = new TitleDescriptionPane(options, attributes);

        this.option = options;
    }

}
