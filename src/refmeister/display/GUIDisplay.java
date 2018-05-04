package refmeister.display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
import refmeister.display.specialhandlers.ImageBuilder;
import refmeister.entity.Reference;
import refmeister.entity.WorkingDirectory;
import refmeister.entity.interfaces.Editable;
import refmeister.entity.interfaces.Entity;

import java.io.File;
import java.util.ArrayList;

/**
 * Models and displays the GUI for the RefMeister program.
 * @author DevSquad Supreme (Red Team)
 * @version 4 May 2018
 */
public class GUIDisplay extends Application implements Displayer, RefObserver, OptionsObserver {
    /** Current Version of the RefMeister program. */
    private String VERSION = "1.0 alpha";

    /** Holds a reference to the Controller object that controls our display. */
    private Controller control;

    /** The stage that we build our scenes in. */
    private Stage theStage;

    /** The scene that we are currently looking at. */
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

    /**
     * Sets up a primary starting stage for the program and prepares it for
     * future updates.
     * @param primaryStage The primary stage to be displayed.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.theStage = primaryStage;
        theStage.setTitle("RefMeister");
        control = new SingleLibraryController(new WorkingDirectory());
        control.addObserver(this);
        update();
    }

    /**
     * The GUI to be displayed when the program first opens up.
     * @return The stage to be displayed when first opening.
     */
    private Stage displayOnOpen() {
        VBox title = new VBox();

        FileManager.getInstance().start(true);

        theScene = new Scene(title, 800, 600);
        theScene.getStylesheets().add(this.getClass().getResource("resources/titleScene.css")
                .toExternalForm());

        Button newButton = new Button("Create New Library");
        Button loadButton = new Button("Load Library");
        newButton.setOnMouseClicked(e -> selectOption("create"));

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
            if(control.getSelected() != null) {
                update();
            }
        });

        theStage.setScene(theScene);
        return theStage;
    }

    /**
     * TODO
     */
    @Override
    public void stop() {
        FileManager.getInstance().stop();
    }

    /**
     * TODO
     */
    @Override
    public void displayCurrent() {
        launch();
    }

    /**
     * As of right now, editing the menu just returns null
     * @return null
     */
    @Override
    public String[] editMenu() {
        return null;
    }

    /**
     * As of right now, picking and option just returns false;
     * @return false
     */
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

    /**
     * Updates the stage to display the correct elements on it according to
     * user changes.
     */
    public void update() {
        if(control.getSelected() == null) {
            theStage = displayOnOpen();
            theStage.show();
        } else {
            BorderPane updated = new BorderPane();
            Pane branchP = getBranchPane();
            branchP.setMaxWidth(250);
            updated.setLeft(branchP);

            updated.setTop(getMenuBar());

            VBox centerPane = new VBox();

            OptionsPane optPane = getOptionsPane();
            Pane titleDesc = getTitleDescriptionPane(optPane);
            centerPane.getChildren().add(titleDesc);

            Parent multiList = getMulti();
            centerPane.getChildren().add(multiList);
            updated.setCenter(centerPane);

            theScene.setRoot(updated);
            theStage.setTitle("RefMeister : " + control.getBranch().get(control.getBranch().size() - 1).getTitle());

            theStage.setMinWidth(1000);
        }
    }

    /**
     * Returns the current BranchPane.
     * @return the current BranchPane.
     */
    private Pane getBranchPane() {
        BranchPane.getInstance(control).updateBranchPane();
        return BranchPane.getInstance(control);
    }

    /**
     * Returns the current TitleDescriptionPane.
     * @param optPane optionsPane to be updated with the TitleDescriptio Pane.
     * @return The current TitleDescriptionPane.
     */
    private Pane getTitleDescriptionPane(OptionsPane optPane) {
        TitleDescriptionPane.getInstance().setTitleDescPane(control.getAttributes(), optPane);
        return TitleDescriptionPane.getInstance();
    }

    /**
     * Returns the current OptionsPane.
     * @return The current OptionsPane.
     */
    private OptionsPane getOptionsPane() {
        OptionsPane.getInstance().addObserver(this);
        OptionsPane.getInstance().setOpts(control.getSelected());
        return OptionsPane.getInstance();
    }

    /**
     * Returns an InformationPane that contains all the tabs that represent
     * its children.
     * @return The InformationPane containing all of its children as tabs.
     */
    private Parent getMulti() {
        String tabName = "";
        if(control.getSelected().getEntityChildren() != null &&
                control.getSelected().getEntityChildren().size() > 0) {
            tabName = control.getSelected().getEntityChildren().get(0).getClass().getSimpleName()
                    + "s";
        } else {
            Entity e = ((Editable)control.getSelected()).createChild("NULL", "NULL");
            tabName = control.getSelected().getEntityChildren().get(0).getClass().
                    getSimpleName() + "s"; //Stupid magic to get 'Topics' for instance,
            control.getSelected().removeChild(e);
        }
        InformationPane.getInstance(control).createTabs(tabName);
        return InformationPane.getInstance(control);
    }

    /**
     * Creates a dialog box to popup when user input is needed.
     * @param dialogLabel Label for the dialog box.
     * @param labels List of labels that the dialog box will need for the
     *               inputs it is receiving from the user.
     * @return A string array that contains the user input.
     */
    public String[] createDialog(String dialogLabel, String... labels) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText(dialogLabel);
        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(1);
        RowConstraints[] rConstraints = new RowConstraints[labels.length];
        TextField[] tFields = new TextField[labels.length];
        double rowPercent = 100/labels.length;

        for(int i = 0; i < labels.length; i++) {
            String text = labels[i];
            grid.add(new Text(text + ": "), 0, i, 1, 1);
            tFields[i] = new TextField();
            tFields[i].setPromptText(text);
            grid.add(tFields[i], 1, i, 1, 1);
            rConstraints[i] = new RowConstraints();
            rConstraints[i].setPercentHeight(rowPercent);
        }

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);

        grid.getColumnConstraints().addAll(col1, col2);
        grid.getRowConstraints().addAll(rConstraints);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        String[] fieldResults = new String[tFields.length * 2];
        int j = 0;
        for(int i = 0; i < tFields.length * 2; i++, j++) {
            fieldResults[i] = labels[j];
            fieldResults[++i] = tFields[j].getText();
        }

        return fieldResults;
    }

    /**
     * Allows editing of an entities information.
     * @param option The entity to edit.
     * @param params The information about that entity to edit.
     */
    private void dialogAction(String option, String... params) {
        if(params != null) {
            switch(option) {
                case "Enter Library Information":
                    if(params[0] != null && !params[0].equals("")) {

                        control.createLibrary(params[1], params[3]);
                        update();
                    }
                    break;
                case "Edit Information":
                    for(int i = 0; i < params.length; i++) {
                        if(params[i+1].equals("")) {
                            params[i+1] = control.getAttributes()[(i+1)/2];
                        }
                        control.editAttribute(params[i++], params[i]);
                    }
                    break;
            }
        }
    }

    /**
     * Allows the user to select an option from the list of options buttons.
     * @param option The option of the observer to update.
     * @param args The list of the observers to update.
     */
    public void selectOption(String option, Object... args) {
        String[] dResult = null;
        switch (option) {
            case "create":
                dResult = createDialog("Enter Library Information",
                        "Title", "Description");
                control.createLibrary(dResult[1], dResult[3]);
//                update();
                break;
            case "edit":
                dResult = createDialog("Edit Information", "Title", "Description");
                for(int i = 0; i < dResult.length; i++) {
                    if(dResult[i+1].equals("")) {
                        dResult[i+1] = control.getAttributes()[(i+1)/2];
                    }
                    control.editAttribute(dResult[i++], dResult[i]);
                }
                update();
                break;
            case "sortAlphA":
                control.sendFunc("sort", "a-z");
                break;
            case "sortAlphD":
                control.sendFunc("sort", "z-a");
                break;
            case "delete":
                control.delete();
                update();
                break;
            case "rate": //TODO make dialog for this
//                String relateTitle = selectFromRelatable(); //replace this
//                control.sendFunc("rate", "" + (getRating()), relateTitle);
                break;
            case "add":
                dResult = createDialog("New Information", "Title", "Description");
                if(dResult[1].equals("")) {
                    dResult[1] = "DEFAULT";
                }
                control.sendFunc("add", dResult[1], dResult[3]);
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

    /**
     * Adds the strings from descs into an array and returns it.
     * @param descs The strings to place in an array
     * @return The string array populated by the list of strings.
     */
    private String[] get(String... descs) {
        String[] result = new String[descs.length];
        for(int i = 0; i < descs.length; i++) {
            result[i] = get(descs[i]);
        }
        return result;
    }

    /**
     * As of now, just returns null
     * @param desc
     * @return null
     */
    //TODO fix this for GUI(generify the Dialog Box used to create new library)
    private String get(String desc) {
//        System.out.print(desc + ": ");
//        return scanIn.nextLine();
        return null;
    }

    /**
     * Prompts the user for input for a rating to give to a RelatedRelation.
     * @return The float double that that user entered, or 3.
     */
/*    public double setRating() {

        String strChoice;
        double choice;

        System.out.print("Please enter a real number x, such that 0<=x<=5 (If invalid input, "+
                "defaults to 3): ");
        strChoice = scanIn.nextLine();
        try {
            choice = Integer.parseInt(strChoice);
            if (0 <= choice && choice <= 5) {
                return choice;
            }
        } catch (NumberFormatException nfe) {
            FileManager.getInstance().log(FileManager.Severity.LOG, "User input invalid " +
                    "number, got: " + strChoice);
        }
        return 3;
    }*/

    /**
     * Scans in input from the user and sets it as a reference's data.
     * @return the reference data
     */
    public String[] getRefData() {
        String[] paperInfo = get("Enter section number > ", "Enter title of the paper > ",
                "Enter publication > ", "Enter location > ",
                "Enter publisher's name > ", "Enter publication date > ",
                "Enter the page range > ", "Enter the URL > ",
                "Enter the file path > ", "Enter the last accessed date > ");
        ArrayList<String> authorInfo = new ArrayList<>();
        boolean moreAuthors = true;
        do {
            String[] authorName = get("Enter the author(s) name [Last] > ",
                    "Enter the author(s) name [MI]",
                    "Enter the author(s) [First]");

            for(int i = 0; i < authorName.length; i++) {
                authorInfo.add(authorName[i]);
                //todo if(authorName == null); moreAuthors = false;

            }
            moreAuthors = false;
        } while(moreAuthors);

        String[] authorArray = authorInfo.toArray(new String[0]);
        String[] result = new String[paperInfo.length + authorArray.length];

        int index = 0;
        for(String s: paperInfo) {
            result[index++] = s;
        }
        for(String s: authorArray) {
            result[index++] = s;
        }

        // ^^ TODO PETER loop through the end to get all the others.
        //  with <3 - Brandon & with </3 - Caleb
        return result;
    }
}
