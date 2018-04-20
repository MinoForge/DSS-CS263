package refmeister.display;

import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.entity.Interfaces.Relatable;
import refmeister.entity.Interfaces.Entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    //TODO
    public String[] editMenu() {
        displayAttributes();
        return editMenu(control.getAttributeTitles(), control.getAttributes());
    }

    public void displayAttributes() {
        String[] attrTitles = control.getAttributeTitles();
        String[] atts = control.getAttributes();
        for(int i = 0; i < atts.length; i++) {
            System.out.println(attrTitles + ": " + atts);
        }
    }

    /**
     * This method is used to edit an array of variables which are passed to it.
     * @param optionNames   The names of the items you are editing.
     * @param currentValues The values of the items you are editing, before they have been touched.
     * @return              The new values
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

    private boolean choose(int choice) {
        System.out.println("Choice: " + itemList.get(choice));
        List<String> funcList = control.getFuncs();
//
//        for(String str: itemList)
//        System.out.println(str);
//
//        for(String str: funcList)
//        System.out.println(str);


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


    public String[] getTD() {
        return get("Title", "Description");
    }

    private String get(String desc) {
        System.out.print(desc + ": ");
        return scanIn.nextLine();
    }

    private String[] get(String... descs) {
        String[] result = new String[descs.length];
        for(int i = 0; i < descs.length; i++) {
            result[i] = get(descs[i]);
        }
        return result;
    }

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
            case "quit":
                return true;
            case "load":
                //todo
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
            case "refdata": //TODO put in SLC
                control.sendFunc("refdata", getRefData());
                break;
            case "moveTheme":
                control.sendFunc("moveTheme", selectFromTopics());
            }

            return false;
        }

    private String selectFromTopics() {
        List<String> titles = new ArrayList<String>();
        for(Entity e: control.getParentEntities()) {
            titles.add(e.getTitle());
        }
        int choice = getChoice(titles);
        return titles.get(choice);
    }

    private String selectFromRelatable() {
        List<Relatable> relatables = control.getRatedRelatables();
        List<String> titles = new ArrayList<String>();
        for(Relatable r: relatables) {
            titles.add(r.getTitle());
        }
        int choice = getChoice(titles);
        return titles.get(choice);

    }

    private String getLibToLoad() {
        return null; //TODO
    }



    //TODO CRY BECAUSE NOT SURE FUNCTIONALITY WILL BE DONE IN 18 HOURS WHEN I GO TO SLEEP
}
