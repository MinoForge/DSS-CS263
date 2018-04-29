package refmeister.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Models and displays the GUI for the RefMeister program.
 * @author DevSquad Supreme (Red Team)
 * @version 28 April 2018
 */
public class GUIDisplay extends Application implements Displayer{
    /** Current Version of the RefMeister program. */
    private String VERSION = "1.0 alpha";

    /** Holds a reference to the Controller object that controls our display. */
    private Controller control;

    /** The stage that we build our scenes in. */
    private Stage theStage;


    /**
     * Default Constructor for our GUI display.
     */
    public GUIDisplay(){
        this.control = null;
    }

    /**
     * Driver to run the GUI display.
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.theStage = primaryStage;
        control = new SingleLibraryController(new WorkingDirectory());

        VBox title = new VBox();

        FileManager.getInstance().start(true);

        Scene titleScene = new Scene(title, 500, 150);
        titleScene.getStylesheets().add(this.getClass().getResource("resources/titleScene.css")
                .toExternalForm());

        Button loadButton = new Button("Load Library");
        Button newButton  = new Button("Create New Library");

        Label welcome = new Label("Welcome To RefMeister");
        welcome.getStyleClass().clear();
        welcome.getStyleClass().add("title");

        title.getChildren().add(welcome);
        title.getChildren().add(loadButton);
        title.getChildren().add(newButton);

        title.getStyleClass().clear();
        title.getStyleClass().add("vbox");

        loadButton.setOnAction((ev) -> {
            this.selectLibrary();
            openApp();
        });

        newButton.setOnAction((ev) -> {
            control.createLibrary();
            openApp();
        });

        theStage.setScene(titleScene);
        theStage.show();

    }

    private void openApp(){
        BorderPane root = new BorderPane();

        // Set up of the Branch Pane
        BranchPane branchHistory = BranchPane.getInstance();
        // TODO Comment out the testTitles. Only there for testing purposes.
        List<String> testTitles = new ArrayList<>();
        testTitles.add("Library Title");
        testTitles.add("Topic Title");
        testTitles.add("Theme Title");
        testTitles.add("Reference Title");
        testTitles.add("Notes/Arg/Idea Title");
        branchHistory.updateBranchPane(testTitles);
        branchHistory = BranchPane.getInstance();
        branchHistory.setBackground(new Background(new BackgroundFill(
                Color.DARKSLATEGREY, new CornerRadii(0), Insets.EMPTY)));
        branchHistory.toFront();
        //branchHistory.prefHeightProperty().bind(root.heightProperty());
        branchHistory.setMinSize(200, 500);
        branchHistory.prefHeightProperty().bind(root.heightProperty());
        root.setLeft(branchHistory);

        // Set up of the Information Pane
        InformationPane multiList = InformationPane.getInstance();
        multiList.createTabs("White", "Brown", "Black");
        multiList.prefWidthProperty().bind(root.widthProperty());
        multiList.prefHeightProperty().bind(root.heightProperty());
        root.setCenter(multiList);

        // Set up of the Menu Bar
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

    /**
     * Allows the user to open a library that has previously been built and
     * loads it.
     */
    public void selectLibrary(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Library");
        fc.setInitialDirectory(this.control.getWorkingDirectory().getDirectory());
        File input = fc.showOpenDialog(this.theStage);

        if(input != null && !control.loadLibrary(input)){
            FileManager.getInstance().log(FileManager.Severity.MINOR_ERROR, "Load failed");
        }
    }

    /**
     * Retrieves and returns the current Menu Bar for the program.
     * @return the current menu bar to display.
     */
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


    public void update() {
        BorderPane updated = new BorderPane();
        updated.setLeft(getBranchPane());
        TilePane optPane = getOptionsPane();
        VBox titleDesc = getTitleDescriptionPane(optPane);


        updated.setCenter();


        Scene newScene = new Scene()
    }

    private Pane getBranchPane() {
        BranchPane.getInstance().updateBranchPane(control.getBranch());
        return BranchPane.getInstance();
    }

    private Pane getTitleDescriptionPane(Pane optPane) {
        TitleDescriptionPane.getInstance().setAttributes(control.getAttributes(), optPane);
        return TitleDescriptionPane.getInstance();
    }

    private Pane getOptionsPane() {
        OptionsPane.getInstance().setOpts(control.getFuncs());
        return OptionsPane.getInstance();
    }


}
