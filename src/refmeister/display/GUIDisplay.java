package refmeister.display;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Stage titleWindow;
    private Scene titleScene;

    public GUIDisplay(){
        this.control = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        titleWindow = new Stage();
        //titleWindow.setMaxHeight(150);
        //titleWindow.setMaxWidth(300);

        VBox title = new VBox();

        titleScene = new Scene(title, 500, 150);
        titleWindow.setScene(titleScene);
        Button loadButton = new Button("Load Library");
        loadButton.setRotate(180);
        Button newButton  = new Button("Create New Library");
        newButton.setRotate(180);
        Text welcome = new Text("Welcome To RefMeister");
        welcome.setFill(Color.BISQUE);
        welcome.setStyle("-fx-font: 40px Serif;");

        titleWindow.show();
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(welcome);
        title.getChildren().add(loadButton);
        title.getChildren().add(newButton);
        title.setStyle("-fx-background-color: DARKSLATEGRAY;");

        Pane root = new HBox();

        //Pane titleBar = TitleBarPane.getPane("RefMeister",
        //        new boolean[]{true, true, true});
        //root.getChildren().add(titleBar);

        //Pane mainWindow = new HBox();
        Pane branchHistory = new BranchPane();
        branchHistory.setBackground(new Background(new BackgroundFill(
                Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
        branchHistory.toFront();
        branchHistory.setMaxSize(200, 570);
        branchHistory.setMinSize(200, 570);
        root.getChildren().add(branchHistory);


        Pane mainArea = new VBox();
        Pane titleDesc = new TitleDescriptionPane();
        Pane options = new OptionsPane();
        titleDesc.getChildren().add(options);
        mainArea.getChildren().add(titleDesc);

        Pane multiList = new SwitchListPane();
        mainArea.getChildren().add(multiList);

        root.getChildren().add(mainArea);

        //root.getChildren().add(mainWindow);



        currScene = new Scene(root, 800, 600);




        appWindow = new Stage();
        appWindow.setTitle("RefMeister");

        appWindow.setScene(currScene);
        ResizeHelper.addResizeListener(appWindow);
        //appWindow.show();
        Pane titleBox = new HBox();

       // mainArea.getChildren().add(welcome);

       // titleBox.getChildren().add(loadButton);
       // titleBox.getChildren().add(newButton);
       // mainArea.getChildren().add(titleBox);
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
