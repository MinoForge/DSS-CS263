package refmeister.display;

import refmeister.controllers.Controller;
import refmeister.controllers.SingleLibraryController;
import refmeister.entity.WorkingDirectory;


import java.io.IOException;
import java.util.Scanner;

public class CLIDisplay implements Displayer {

    private Controller control;

    private String[] itemList;

    public CLIDisplay(WorkingDirectory wDir) {
        control = new SingleLibraryController(wDir);
    }

    @Override
    public void displayCurrent() {
        clrscr();
        itemList = control.displaySelected();

        for(int i = 0; i < itemList.length; i++) {
            System.out.printf("%-3d" + ": " + itemList[i] + "\n", i);
        }
    }

    @Override
    public boolean pickOption() {
        int choice = getChoice();

        if(choice != -1) {
            return pickOption(choice);
        }
        return false;

    }

    public boolean pickOption(int choice) {
        return control.choose(itemList[choice]);
    }

    public int getChoice() {
        Scanner scanIn = new Scanner(System.in);
        String strChoice;
        int choice = -1;

        System.out.print("Please choose an option: ");
        strChoice = scanIn.nextLine();

        try {
            choice = Integer.parseInt(strChoice);
            return choice;
        } catch (NumberFormatException nfe) {}

        for (int i = 0; i < itemList.length; i++) {
            if(strChoice.equals(itemList[i])) {
                return i;
            }
        }

        return choice;
    }


    public static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }


}
