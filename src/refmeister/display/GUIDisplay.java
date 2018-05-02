package refmeister.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.controllers.SingleLibraryController;
import refmeister.display.elements.*;
import refmeister.display.elements.Interfaces.OptionsObserver;
import refmeister.display.elements.Interfaces.RefObserver;
import refmeister.display.specialHandlers.ImageBuilder;
import refmeister.display.specialHandlers.ResizeHelper;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Reference;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Models and displays the GUI for the RefMeister program.
 * @author DevSquad Supreme (Red Team)
 * @version 28 April 2018
 */
public class GUIDisplay extends Application implements Displayer, RefObserver, OptionsObserver {
    /** Current Version of the RefMeister program. */
    private String VERSION = "1.0 alpha";

    /** Holds a reference to the Controller object that controls our display. */
    private Controller control;

    /** The stage that we build our scenes in. */
    private Stage theStage;
    private Scene theScene;


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
        theStage.setTitle("RefMeister");
        control = new SingleLibraryController(new WorkingDirectory());

        VBox title = new VBox();

        FileManager.getInstance().start(true);

        theScene = new Scene(title, 500, 150);
        theScene.getStylesheets().add(this.getClass().getResource("resources/titleScene.css")
                .toExternalForm());

        Button loadButton = new Button("Load Library");
        Button newButton = makeNewButton();

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
//            openApp();
            update();
        });



        theStage.setScene(theScene);

        theStage.show();

    }

    private void openApp(){
        BorderPane root = new BorderPane();

        // Set up of the Branch Pane
        BranchPane branchHistory = BranchPane.getInstance(control);
        // TODO Comment out the testTitles. Only there for testing purposes.
        List<String> testTitles = new ArrayList<>();
        testTitles.add("Library Title");
        testTitles.add("Topic Title");
        testTitles.add("Theme Title");
        testTitles.add("Reference Title");
        testTitles.add("Notes/Arg/Idea Title");
        branchHistory.updateBranchPane();
        branchHistory = BranchPane.getInstance(control);

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

        VBox centerBox = new VBox();

        centerBox.getChildren().addAll( multiList);

        root.setCenter(centerBox);

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

        MenuItem save = new MenuItem("Save", ImageBuilder.buildImage(getClass()
                .getResourceAsStream("./resources/save.png"), 0.5));
        MenuItem load = new MenuItem("Load", ImageBuilder.buildImage(getClass()
                .getResourceAsStream("./resources/import.png"), 0.5));
        MenuItem exit = new MenuItem("Exit", ImageBuilder.buildImage(getClass()
                .getResourceAsStream("./resources/exitRight.png"), 0.5));

        save.setOnAction((ev) -> control.saveLibrary());
        load.setOnAction((ev) -> selectLibrary());
        exit.setOnAction((ev) -> Platform.exit());

        file.getItems().addAll(save, load, exit);

        MenuItem version = new MenuItem("About us", ImageBuilder.buildImage(getClass()
                .getResourceAsStream("./resources/information.png"), 0.5));
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

        updated.setTop(getMenuBar());

        VBox centerPane = new VBox();

        OptionsPane optPane = getOptionsPane();
        Pane titleDesc = getTitleDescriptionPane(optPane);
        centerPane.getChildren().add(titleDesc);

        Parent multiList = getMulti();
        centerPane.getChildren().add(multiList);
        updated.setCenter(centerPane);

        theScene.setRoot(updated);
        theStage.setTitle("RefMeister : " + control.getBranch().get(control.getBranch().size()-1).getTitle());

    }

    private Pane getBranchPane() {
        BranchPane.getInstance(control).updateBranchPane();
        return BranchPane.getInstance(control);
    }

    private Pane getTitleDescriptionPane(OptionsPane optPane) {
        TitleDescriptionPane.getInstance().setTitleDescPane(control.getAttributes(), optPane);
        return TitleDescriptionPane.getInstance();
    }

    private OptionsPane getOptionsPane() {
//        System.out.println(control.getFuncs()); //Returning properly
        OptionsPane.getInstance().setOpts(control.getFuncs());
        return OptionsPane.getInstance();
    }

    private Parent getMulti() {
        String tabName = "";
        if(control.getSelected().getEntityChildren() != null && control.getSelected().getEntityChildren().size() > 0) {
            tabName = control.getSelected().getEntityChildren().get(0).getClass().getSimpleName();
        }
        InformationPane.getInstance().createTabs(tabName);
        return InformationPane.getInstance();
    }


    private Button makeNewButton() {
        Button newButton = new Button("Create New Library");
        newButton.setOnAction((ev) -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setHeaderText("Enter Information");
            ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(2);
            grid.setVgap(1);
            // grid.setPadding(new Insets(20, 150, 10,10));

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
            col1.setPercentWidth(25);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(75);

            grid.getColumnConstraints().addAll(col1, col2);
            grid.getRowConstraints().addAll(row1, row2);

            dialog.getDialogPane().setContent(grid);

            dialog.showAndWait();


            control.createLibrary(title1.getText(), description.getText());
            this.update();
//            openApp();
        });
        return newButton;
    }

    public void selectOption(String option) {
        switch (option) {
//            case "create":
//                String[] titleDescription = getTD();
//                control.createLibrary(titleDescription[0], titleDescription[1]);
//                break;
            case "edit":
                String[] newVals = editMenu();
                control.sendFunc("edit", newVals);
                break;
//            case "move":
//                //Commented code is attempted work-around for Relatables without parents.
//
//                if(control.getRatedRelatables() != null) { //this line checks if control.selected
//                    String relates = selectFromRelatable();//is Relatable.
//                    control.sendFunc("select", relates);
//                }
//                control.traverseUp();
//                break;
            case "sortAlphA":
                control.sendFunc("sort", "a-z");
                break;
            case "sortAlphD":
                control.sendFunc("sort", "z-a");
                break;
            case "delete":
                control.delete();
                break;
            case "rate": //TODO make dialog for this
//                String relateTitle = selectFromRelatable(); //replace this
//                control.sendFunc("rate", "" + (getRating()), relateTitle);
                break;
//            case "view":
//                control.viewDir();
//                break;
//            case "change": //TODO put in SLC
//                control.sendFunc("change", get("Name of new Relation"));
//                break;
            case "add":
//                control.sendFunc("add", getTD());
                break;
            case "addA":
//                control.sendFunc("addA", getTD());
                break;
            case "addI":
//                control.sendFunc("addI", getTD());
                break;
            case "generate": //TODO put in SLC
//                String[] refData = getRefData();
//                control.sendFunc("generate", refData);
//                for(String s: refData) {
//                    System.out.println(s);
//                }
//                break;
//            case "moveTheme":
//                control.sendFunc("moveTheme", selectFromTopics());
//                break;
            case "MLA":
                System.out.println(((Reference)control.getSelected()).generateMLA());
                break;
            case "APA":
                System.out.println(((Reference)control.getSelected()).generateAPA());
                break;
        }
    }

    private String[] get(String... descs) {
        String[] result = new String[descs.length];
        for(int i = 0; i < descs.length; i++) {
            result[i] = get(descs[i]);
        }
        return result;
    }

    //TODO fix this for GUI(generify the Dialog Box used to create new library)
    private String get(String desc) {
//        System.out.print(desc + ": ");
//        return scanIn.nextLine();
        return null;
    }
}
