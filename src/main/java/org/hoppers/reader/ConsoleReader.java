package org.hoppers.reader;

import java.util.Scanner;


public class ConsoleReader implements Reader {

    // Scanner object for the user input
    private Scanner consoleInput = new Scanner(System.in);

    @Override
    public String getNumberOfCases() {

        return consoleInput.nextLine();
    }

    @Override
    public String getGridSize() {

        return consoleInput.nextLine();
    }

    @Override
    public String getStartEndLocation() {

        return consoleInput.nextLine();
    }
}
