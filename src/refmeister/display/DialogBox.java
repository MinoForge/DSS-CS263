package refmeister.display;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * Created by Caleb on 4/28/2018.
 */
public class DialogBox {

    private Dialog<Pair<String, String>> dialog = new Dialog();


    public DialogBox(){
        Dialog<Pair<String, String>> dialog = new Dialog();
        dialog.setHeaderText("Enter New Information");
        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(1);
        grid.setPadding(new Insets(20, 150, 10,10));

        TextField title1 = new TextField();
        title1.setPromptText("Title");
        TextField description = new TextField();
        description.setPromptText("Description");
        description.setMinSize(250, 100);

        grid.add(new Text("Title:"), 0, 0,1 ,1);
        grid.add(title1, 1,0, 1, 1);
        grid.add(new Text("Description:"), 0, 1, 1, 1);
        grid.add(description, 1, 1, 1, 1);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80);

        grid.getColumnConstraints().addAll(col1, col2);
        grid.getRowConstraints().addAll(row1, row2);




    }


}
