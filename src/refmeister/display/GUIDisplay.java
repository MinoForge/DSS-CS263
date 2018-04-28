package refmeister.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

/**
 * TODO Have not done the GUI yet -> On track to complete this the third sprint.
 */
public class GUIDisplay extends Application implements Displayer{
    private String VERSION = "1.0 alpha";

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
        branchHistory.setMinSize(200, 500);
        ((BranchPane) branchHistory).updateBranchPane();
        branchHistory.prefHeightProperty().bind(root.heightProperty());
        root.setLeft(branchHistory);

        InformationPane multiList = InformationPane.getInstance();
        multiList.createTabs("White", "Brown", "Black");
        multiList.prefWidthProperty().bind(root.widthProperty());
        multiList.prefHeightProperty().bind(root.heightProperty());
        root.setCenter(multiList);

        root.setTop(getMenuBar());

        //root.getChildren().add(mainWindow);

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

        if(input != null && !control.loadLibrary(input)){
            FileManager.getInstance().log(FileManager.Severity.MINOR_ERROR, "Load failed");
        }
    }

    private MenuBar getMenuBar(){
        MenuBar out = new MenuBar();

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem exit = new MenuItem("Exit");

        save.setOnAction((ev) -> control.saveLibrary());
        load.setOnAction((ev) -> selectLibrary());
        exit.setOnAction((ev) -> Platform.exit());

        file.getItems().addAll(save, load, exit);

        MenuItem version = new MenuItem("About");
        version.setOnAction((ev) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("RefMeister About Us");
            alert.setHeaderText(null);
            alert.setContentText("Refmeister is a Reference Manager designed by Peter Gardner, " +
                    "Caleb Dinehart, Brandon Townsend, and Wesley Rogers.\n\nThe current " +
                    "Refmeister version is " + VERSION + ".");

            alert.showAndWait();
        });

        help.getItems().addAll(version);

        out.getMenus().addAll(file, edit, help);
        return out;
    }
}
