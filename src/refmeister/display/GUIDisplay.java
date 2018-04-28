package refmeister.display;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import refmeister.controllers.Controller;
import refmeister.display.elements.*;
import refmeister.display.specialHandlers.ResizeHelper;

import java.util.Scanner;

/**
 * TODO Have not done the GUI yet -> On track to complete this the third sprint.
 */
public class GUIDisplay extends Application implements Displayer{
    private Controller control;
    private Scene currScene;
    private Stage appWindow;
    private Scanner scanIn;

    public GUIDisplay(){
        this.control = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new VBox();

        Pane titleBar = TitleBarPane.getPane("RefMeister",
                new boolean[]{true, true, true});
        root.getChildren().add(titleBar);

        Pane mainWindow = new HBox();
        Pane branchHistory = new BranchPane(new String[] {"Library",
                "Topic", "Theme", "Reference", "Notes"});
        branchHistory.setBackground(new Background(new BackgroundFill(
                Color.DARKSLATEGREY, new CornerRadii(0), Insets.EMPTY)));
        branchHistory.toFront();
        branchHistory.setMaxSize(200, 570);
        branchHistory.setMinSize(200, 570);
        mainWindow.getChildren().add(branchHistory);
        ((BranchPane) branchHistory).updateBranchPane();


        Pane mainArea = new VBox();
        Pane titleDesc = new TitleDescriptionPane();

        Pane options = new OptionsPane();
        titleDesc.getChildren().add(options);
        mainArea.getChildren().add(titleDesc);

        Pane multiList = new SwitchListPane();
        mainArea.getChildren().add(multiList);

        mainWindow.getChildren().add(mainArea);

        root.getChildren().add(mainWindow);



        currScene = new Scene(root, 800, 600);




        appWindow = new Stage(StageStyle.UNDECORATED);

        appWindow.setScene(currScene);
        ResizeHelper.addResizeListener(appWindow);
        appWindow.show();
    }

    @Override
    public void displayCurrent() {
        launch();
    }

    @Override
    public String[] editMenu() {
        return null;
    }

    @Override
    public boolean pickOption() {
        return false;
    }
}
