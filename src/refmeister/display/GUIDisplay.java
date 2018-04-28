package refmeister.display;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.controllers.SingleLibraryController;
import refmeister.display.elements.*;
import refmeister.display.specialHandlers.ResizeHelper;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * TODO Have not done the GUI yet -> On track to complete this the third sprint.
 */
public class GUIDisplay extends Application implements Displayer{
    private Controller control;
    private Scene currScene;
    private Stage appWindow;

    public GUIDisplay(){
        this.control = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        control = new SingleLibraryController(new WorkingDirectory());
        Pane root = new VBox();

        Pane titleBar = TitleBarPane.getPane("RefMeister",
                new boolean[]{true, true, true});
        root.getChildren().add(titleBar);

        FileManager.getInstance().start(true);

        Pane mainWindow = new HBox();
        Pane branchHistory = new BranchPane();
        branchHistory.setBackground(new Background(new BackgroundFill(
                Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
        branchHistory.toFront();
        branchHistory.setMaxSize(200, 570);
        branchHistory.setMinSize(200, 570);
        mainWindow.getChildren().add(branchHistory);


        Pane mainArea = new VBox();
        Pane titleDesc = new TitleDescriptionPane();

        Pane options = new OptionsPane();
        titleDesc.getChildren().add(options);
        mainArea.getChildren().add(titleDesc);

        InformationPane multiList = InformationPane.getInstance();
        multiList.createTabs("Data1", "data2");
        multiList.prefWidthProperty().bind(root.widthProperty());
        multiList.prefHeightProperty().bind(root.heightProperty());

        mainArea.getChildren().add(multiList);

        mainWindow.getChildren().add(mainArea);

        root.getChildren().add(mainWindow);

        currScene = new Scene(root, 800, 600);




        appWindow = new Stage();

        appWindow.setScene(currScene);
        ResizeHelper.addResizeListener(appWindow);

        appWindow.show();
        selectLibrary();

    }

    @Override
    public void stop(){
        FileManager.getInstance().stop();
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

    public void selectLibrary(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Library");
        fc.setInitialDirectory(this.control.getWorkingDirectory().getDirectory());
        File input = fc.showOpenDialog(this.appWindow);

        if(!control.loadLibrary(input)){
            FileManager.getInstance().log(FileManager.Severity.MINOR_ERROR, "Load failed");
        }
    }
}
