package refmeister.display;

import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.RatedRelation;
import refmeister.entity.Interfaces.Relatable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Models the command line interface. Handles some user input and works with the controller to
 * show the correct command line interface.
 * @author Peter Gardner
 * @version 19 April 2018
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
        itemList = control.displaySelected();

        for(int i = 0; i < itemList.size(); i++) {
            System.out.printf("%-3d" + ": " + itemList.get(i) + "\n", i);
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
    public void editMenu() {
        String[] newVals = editMenu(control.getAttributeTitles(), control.getAttributes());
        for(int i = 0; i < newVals.length; i++) {
            if(newVals[i] != null) {
                System.out.println(newVals[i] + ": " + newVals[i+1]);
                control.editAttribute(newVals[i].toLowerCase(), newVals[++i]);
            }
        }
    }

    /**
     * This method is used to edit an array of variables which are passed to it.
     * @param optionNames The names of the items you are editing.
     * @param currentValues The values of the items you are editing, before they have been touched.
     * @return
     */
    private String[] editMenu(String[] optionNames, String[] currentValues) {
        for(String str: optionNames)
            System.out.println(str);
        clrscr();
        String[] nameAndVals = new String[optionNames.length*2];
        int j = 0;
        for(int i = 0; i < optionNames.length; i++) {
            System.out.println(optionNames[i]);
            System.out.println(currentValues[i]);
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
        return functionality(itemList.get(choice));
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
     *
     * @param descs
     * @return
     */
    private String[] get(String... descs) {
        String[] result = new String[descs.length];
        for(int i = 0; i < descs.length; i++) {
            result[i] = get(descs[i]);
        }
        return result;
    }

    /**
     *
     * @return
     */
    public double getRating() {

            String strChoice;
            double choice = -1;

            System.out.print("Please enter a real number x, such that 0<=x<=5 (If invalid input, "+
                    "defaults to 3): ");
            strChoice = scanIn.nextLine();
            try {
                choice = Integer.parseInt(strChoice);
                if (0 <= choice && choice <= 5) {
                    return choice;
                }
            } catch (NumberFormatException nfe) {}
            return 3;
    }

    /**
     * Scans in input from the user and sets it as a reference's data.
     * @return an array of strings that contains the questions users will answer to fill in the
     * data a reference needed.
     */
    public String[] getRefData() {
        //scanIn is defined globally, just need the user input mapped into the right places and
        //returned.
        String[] result = get("Enter section number > ", "Enter title of the paper > ",
                              "Enter publication > ", "Enter location > ",
                              "Enter publisher's name > ", "Enter publication date > ",
                              "Enter the page range > ", "Enter the URL > ",
                              "Enter the file path > ", "Enter the last accessed date > ",
                              "Enter the author(s) name [Last][MI][First] > ");
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
            case "Quit":
                return true;
            case "Create Library":
                String[] titleDescription = getTD();

                control.createLibrary(titleDescription[0], titleDescription[1]);
                break;
            case "Edit":
                editMenu();
                break;
            case "Move Up":
                control.traverseUp();
                break;
            case "SortAlphA":
                control.sendFunc("sort", "a-z");
                break;
            case "SortAlphD":
                control.sendFunc("sort", "z-a");
                break;
            case "Delete":
                control.delete();
                break;
            case "Change Rating":
                String relateTitle = selectFromRelatable(); //replace this
                control.sendFunc("rate", "" + (getRating()), relateTitle);
                break;
            case "View Directory":
                control.viewDir();
                break;
            case "Change Relation": //TODO put in SLC
                control.sendFunc("changeRelation", get("Name of new Relation"));
                break;
            case "Add": //TODO put in SLC
                control.sendFunc("add", getTD());
                break;
            case "RefData": //TODO put in SLC
                control.sendFunc("");
                break;
            }
            return false;
        }

    /**
     *
     * @return
     */
    private String selectFromRelatable() {
        List<Relatable> relatables = control.getRatedRelatables();
        List<String> titles = new ArrayList<String>();
        for(Relatable r: relatables) {
            titles.add(r.getTitle());
        }
        int choice = getChoice(titles);
        return titles.get(choice);

    }



    //TODO CRY BECAUSE NOT SURE FUNCTIONALITY WILL BE DONE IN 18 HOURS WHEN I GO TO SLEEP
}
