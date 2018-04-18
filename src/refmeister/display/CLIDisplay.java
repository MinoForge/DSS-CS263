package refmeister.display;

import refmeister.controllers.Controller;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CLIDisplay implements Displayer {

    /** The main controller for the program. **/
    private Controller control;

    /** The current list of options as a String[] array. **/
    private List<String> itemList;

    /**
     * A constructor which starts a display connected to the controller passed to it.
     * @param control The controller being connected to.
     */
    public CLIDisplay(Controller control) {
        this.control = control;
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
        int choice = getChoice();

        if(choice != -1) {
            return choose(choice);
        }
        return false;

    }

    //TODO
    public void editMenu() {
        String[] newVals = editMenu(control.getAttributeTitles(), control.getAttributes());
        for(int i = 0; i < newVals.length; i++) {
            control.editAttribute(newVals[i].toLowerCase(), newVals[++i]);
        }
    }

    /**
     * This method is used to edit an array of variables which are passed to it.
     * @param optionNames The names of the items you are editing.
     * @param currentValues The values of the items you are editing, before they have been touched.
     * @return
     */
    private String[] editMenu(String[] optionNames, String[] currentValues) {
        clrscr();
        Scanner scanIn = new Scanner(System.in);
        String[] nameAndVals = new String[optionNames.length*2];
        int j = 0;
        for(int i = 0; i < optionNames.length; i++) {
            System.out.println(optionNames[i]);
            System.out.println(currentValues[i]);
            System.out.print("New Value: ");
            String newVal = scanIn.nextLine();
            if(!newVal.equals("")) {
                nameAndVals[j] = optionNames[i];
                nameAndVals[++j] = newVal;
            }
        }
        scanIn.close();



        return nameAndVals;
    }

    private boolean choose(int choice) {
        return control.functionality(itemList.get(choice));
    }


    /**
     * This method gets the user's choice as an int from the User.
     * @return -1 if an invalid choice is picked. Otherwise returns the index of the choice.
     */
    private int getChoice() {
        Scanner scanIn = new Scanner(System.in);
        String strChoice;
        int choice = -1;

        System.out.print("Please choose an option: ");
        strChoice = scanIn.nextLine();

        try {
            choice = Integer.parseInt(strChoice);
            if(0 <= choice && choice < itemList.size())
            return choice;
        } catch (NumberFormatException nfe) {}

        for (int i = 0; i < itemList.size(); i++) {
            if(strChoice.equals(itemList.get(i))) {
                return i;
            }
        }

        return choice;
    }


    private static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            clrscr();


































        }
    }


}
