package refmeister.display;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.controllers.SingleLibraryController;
import refmeister.display.elements.*;
import refmeister.display.specialHandlers.ResizeHelper;
import refmeister.entity.WorkingDirectory;

import java.io.File;

import static java.sql.JDBCType.INTEGER;

/**
 * TODO Have not done the GUI yet -> On track to complete this the third sprint.
 */
public class GUIDisplay extends Application implements Displayer{
    private Controller control;
    private Stage theStage;

    public GUIDisplay(){
        this.control = null;
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.theStage = primaryStage;
        control = new SingleLibraryController(new WorkingDirectory());

        VBox title = new VBox();

        FileManager.getInstance().start(true);

        Pane mainWindow = new HBox();
        Scene titleScene = new Scene(title, 500, 150);
        theStage.setScene(titleScene);
        Button loadButton = new Button("Load Library");
        loadButton.setStyle("-fx-text-fill: bisque;" +
                "-fx-background-color: transparent;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: bisque;" +
                "-fx-border-radius: 5 5 5 5 ;" +
                "-fx-padding: 5 40 5 40;");
        loadButton.setOnMouseMoved(e -> loadButton.setStyle
                ("-fx-font-underline: true"));
        Button newButton  = new Button("Create New Library");
        newButton.setStyle("-fx-text-fill: bisque;" +
                "-fx-background-color: #2F4F4F;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: bisque;" +
                "-fx-border-radius: 5 5 5 5 ;" +
                "-fx-padding: 5 15 5 15;");
        Text welcome = new Text("Welcome To RefMeister");
        welcome.setFill(Color.BISQUE);
        welcome.setStyle("-fx-font: 42px Serif;");

        title.setAlignment(Pos.CENTER);
        title.setSpacing(7);
        title.getChildren().add(welcome);
        title.getChildren().add(loadButton);
        title.getChildren().add(newButton);
        title.setStyle("-fx-background-color: DARKSLATEGRAY;");

        loadButton.setOnAction((ev) -> {
            this.selectLibrary();
            openApp();
        });

        newButton.setOnAction((ev) -> {
            control.createLibrary();
            openApp();
        });

        theStage.show();

    }

    private void openApp(){
        BorderPane root = new BorderPane();

        BranchPane branchHistory = BranchPane.getInstance();
        branchHistory.setBranchPane(new String[] {"Library",
                "Topic", "Theme", "Reference", "Notes"});
        branchHistory = BranchPane.getInstance();
        branchHistory.setBackground(new Background(new BackgroundFill(
                Color.DARKSLATEGREY, new CornerRadii(0), Insets.EMPTY)));
        branchHistory.toFront();
        branchHistory.prefHeightProperty().bind(root.heightProperty());
        branchHistory.setMinSize(200, 500);
        ((BranchPane) branchHistory).updateBranchPane();
        root.setLeft(branchHistory);

        InformationPane multiList = InformationPane.getInstance();
        multiList.createTabs("Data1", "data2");
        multiList.prefWidthProperty().bind(root.widthProperty());
        multiList.prefHeightProperty().bind(root.heightProperty());
        root.setCenter(multiList);

        Scene currScene = new Scene(root, 800, 600);
        theStage.hide();

        theStage.setTitle("RefMeister");

        theStage.setScene(currScene);
        ResizeHelper.addResizeListener(theStage);
        theStage.show();
    }

    @Override
    public void stop() {
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
        File input = fc.showOpenDialog(this.theStage);

        if(!control.loadLibrary(input)){
            FileManager.getInstance().log(FileManager.Severity.MINOR_ERROR, "Load failed");
            control.createLibrary();
        }
    }
}
