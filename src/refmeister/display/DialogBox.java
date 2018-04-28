package refmeister.display;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by Caleb on 4/28/2018.
 */
public class DialogBox {

    private Dialog<Pair<String, String>> dialog = new Dialog();


    public DialogBox(){
        dialog.setHeaderText("Enter New Information");
        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10,10));

        TextField title = new TextField();
        title.setPromptText("Title");
        TextField description = new TextField();
        description.setMinSize(250, 100);

        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1,0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(description, 1, 1);



    }


}
