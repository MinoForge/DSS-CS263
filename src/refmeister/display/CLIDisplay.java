package refmeister.display;

import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.entity.interfaces.Relatable;
import refmeister.entity.interfaces.Entity;
import refmeister.entity.Reference;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Models the command line interface. Handles some user input and works with the controller to
 * show the correct command line interface.
 * @author Peter Gardner
 * @version 20 April, 2018
 */
public class CLIDisplay implements Displayer {

    /** The main controller for the program. **/
    private Controller control;

    /** The current list of options as a String[] array. **/
    private List<String> itemList;

    /** The scanner which may be accessed from everywhere, to avoid annoying Scanner behavior **/
    private Scanner scanIn;

    /**
     * A constructor which starts a display connected to the controller passed to it.
     * @param control The controller being connected to.
     */
    public CLIDisplay(Controller control) {
        this.control = control;
        this.scanIn = new Scanner(System.in);
    }

    /**
     * Method which displays the currently selected Entity and the options which may be selected.
     */
    @Override
    public void displayCurrent() {
        clrscr();
        displayAttributes();
        itemList = control.displaySelected();
        printList(itemList);
    }

    private void printList(List<String> list) {
        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%-3d" + ": " + list.get(i) + "\n", i);
        }
    }

    /**
     * The publicly facing method which is used to pick an option out of those available.
     * @return true if "quit" is chosen. False otherwise.
     */
    @Override
    public boolean pickOption() {
        int choice = getChoice(itemList);

        if(choice != -1 && choice < itemList.size()) {
            return choose(choice);
        }

        return false;

    }

    /**
     * Allows the menu to be edited based on the attributes passed to it.
     */
    public String[] editMenu() {
        displayAttributes();
        return editMenu(control.getAttributeTitles(), control.getAttributes());
    }

    /**
     * Prints out the current attributes of a specified entity. This will usually be the title
     * and description of an entity.
     */
    public void displayAttributes() {

        String[] attrTitles = control.getAttributeTitles();
        if(attrTitles != null) {
            String[] atts = control.getAttributes();
            for(int i = 0; i < atts.length; i++) {
                System.out.println("Current " + attrTitles[i] + ": " + atts[i]);
            }
        }
    }

    /**
     * This method is used to edit an array of variables which are passed to it.
     * @param optionNames   The names of the items you are editing.
     * @param currentValues The values of the items you are editing, before they have been touched.
     * @return              The new values
     */
    private String[] editMenu(String[] optionNames, String[] currentValues) {

        clrscr();
        for(String str: optionNames) {
            System.out.println(str);
        }
        String[] nameAndVals = new String[optionNames.length*2];
        int j = 0;
        for(int i = 0; i < optionNames.length; i++) {
            System.out.print("New Value: ");
            String newVal = scanIn.nextLine();
            if(!newVal.equals("")) {
                nameAndVals[j++] = optionNames[i];
                nameAndVals[j++] = newVal;
            }
        }



        return nameAndVals;
    }

    /**
     * Prints out the name of the choice the user selected and calls functionality on that choice.
     * @param choice The integer value that correlates to the choice the user makes.
     * @return The call to functionality based on the choice.
     */
    private boolean choose(int choice) {
        System.out.println("Choice: " + itemList.get(choice));
        List<String> funcList = control.getFuncs();
        if(funcList.size() < choice) {
            List<String> children = new ArrayList<String>();
            for(int i = choice; i < itemList.size(); i++) {
                children.add(itemList.get(i));
            }
            control.sendFunc("select", "" + children.get(choice - funcList.size()));
            return false;
        }


        return functionality(funcList.get(choice));
    }




    /**
     * This method gets the user's choice as an int from the User.
     * @return -1 if an invalid choice is picked. Otherwise returns the index of the choice.
     */
    private int getChoice(List<String> items) {
        String strChoice;
        int choice = -1;

        System.out.print("Please choose an option: ");
        if(scanIn.hasNextLine()) {
            strChoice = scanIn.nextLine();
            try {
                choice = Integer.parseInt(strChoice);
                if (0 <= choice && choice < items.size()) {
                    return choice;
                }
            } catch (NumberFormatException nfe) {
                FileManager.getInstance().log(FileManager.Severity.LOG, "User input invalid " +
                        "number: " + strChoice);
            }

            for (int i = 0; i < items.size(); i++) {
                if (strChoice.equals(items.get(i))) {
                    return i;
                }
            }
        }

        return choice;
    }

    /**
     * Clears the screen (linux command line). Even on different operating systems!
     */
    private static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            clrscr();
        }
    }

    /**
     * Returns a string array that contains the title and description of an entity.
     * @return a string array that contains the title and description of an entity.
     */
    public String[] getTD() {
        return get("Title", "Description");
    }

    /**
     * Prints out a single string passed to it and gets the next line the user inputs.
     * @param desc The string to be printed out.
     * @return The line the user inputs.
     */
    private String get(String desc) {
        System.out.print(desc + ": ");
        return scanIn.nextLine();
    }

    /**
     * Returns an array of strings passed on the strings that are passed in. Calls on the get()
     * method above to read a line of input from the user.
     * @param descs The strings passed in.
     * @return A string array built on user input from the get() method above.
     */
    private String[] get(String... descs) {
        String[] result = new String[descs.length];
        for(int i = 0; i < descs.length; i++) {
            result[i] = get(descs[i]);
        }
        return result;
    }

    /**
     * Prompts the user for input for a rating to give to a RelatedRelation.
     * @return The float double that that user entered, or 3.
     */
    public double getRating() {

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
    }

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




    /**
     * @param choice The functionality being requested.
     * @return true if "quit" is the choice
     */
    public boolean functionality (String choice) {
        switch (choice) {
            case "select":


            case "quit":
                return true;
            case "load":

                break;
            case "create":
                String[] titleDescription = getTD();
                control.createLibrary(titleDescription[0], titleDescription[1]);
                break;
            case "edit":
                String[] newVals = editMenu();
                control.sendFunc("edit", newVals);
                break;
            case "move":
//                //Commented code is attempted work-around for Relatables without parents.
//
//                if(control.getRatedRelatables() != null) { //this line checks if control.selected
//                    String relates = selectFromRelatable();//is Relatable.
//                    control.sendFunc("select", relates);
//                }
                control.traverseUp();
                break;
            case "sortAlphA":
                control.sendFunc("sort", "a-z");
                break;
            case "sortAlphD":
                control.sendFunc("sort", "z-a");
                break;
            case "delete":
                control.delete();
                break;
            case "rate":
                String relateTitle = selectFromRelatable(); //replace this
                control.sendFunc("rate", "" + (getRating()), relateTitle);
                break;
            case "view":
                control.viewDir();
                break;
            case "change": //TODO put in SLC
                control.sendFunc("change", get("Name of new Relation"));
                break;
            case "add":
                control.sendFunc("add", getTD());
                break;
            case "addA":
                control.sendFunc("addA", getTD());
                break;
            case "addI":
                control.sendFunc("addI", getTD());
                break;
            case "generate": //TODO put in SLC
                String[] refData = getRefData();
                control.sendFunc("generate", refData);
                for(String s: refData) {
                    System.out.println(s);
                }
                break;
            case "moveTheme":
                control.sendFunc("moveTheme", selectFromTopics());
                break;
            case "MLA":
                System.out.println(((Reference)control.getSelected()).generateMLA());
                break;
            case "APA":
                System.out.println(((Reference)control.getSelected()).generateAPA());
                break;
        }
            return false;
        }

    /**
     * Selects
     * @return
     */
    private String selectFromTopics() {
        List<String> titles = new ArrayList<String>();
        for(Entity e: control.getParentEntities()) {
            titles.add(e.getTitle());
        }
        int choice = getChoice(titles);
        System.out.println(titles.get(choice));
        return titles.get(choice);
    }

    /**
     * Selects
     * @return
     */
    private String selectFromRelatable() {
        List<Relatable> relatables = control.getRatedRelatables();
        List<String> titles = new ArrayList<String>();
        for(Relatable r: relatables) {
            titles.add(r.getTitle());
        }
        printList(titles);
        int choice = getChoice(titles);
        return titles.get(choice);

    }

    /**
     * TODO Gets the library to load.
     * @return
     */
    private String getLibToLoad() {
        return null; //TODO
    }

}
