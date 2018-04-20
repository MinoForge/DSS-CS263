package refmeister.display;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import refmeister.controllers.Controller;

public class GUIDisplay extends Application implements Displayer{
    private Controller c;

    public GUIDisplay(){
        this.c = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene s = new Scene(root);
    }

    @Override
    public void displayCurrent() {
        launch();
    }

    @Override
    public void editMenu() {

    }

    @Override
    public boolean pickOption() {
        return false;
    }
}
