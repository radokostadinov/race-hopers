package org.hoppers.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hoppers.parser.ConsoleInputParser;
import org.hoppers.parser.Parser;
import org.hoppers.reader.ConsoleReader;
import org.hoppers.reader.Reader;
import org.hoppers.utility.GridCoordinates;
import org.hoppers.utility.RaseCase;

import java.util.*;

/**
 * This class can calculate the fluts number you need to buy the best profit
 */
public class FlutsProfitCalculator {

    private static final Logger LOGGER = LogManager.getLogger(FlutsProfitCalculator.class);

    private static final int FLUTS_SELL_PRICE = 10;

    private static final String NUMBER_MISMATCH_ERROR_MESSAGE = "The total number of values does not match!";
    private static final String ZERO_VALUE_NOT_ALLOWED_ERROR_MESSAGE = "Flut price equals to 0 is not allowed!";

    private static final String INPUT_COORDINATES_GRID_MESSAGE = "Enter the case coordinates:";

    private Reader consoleReader = new ConsoleReader();

    private Parser inputParser = new ConsoleInputParser();


    public void startCalculator() {


        // Get the number of the schuurs for the current case
        LOGGER.info("Enter number of cases");
        int casesCount = getCasesCount();

        List<RaseCase> numberCases = new ArrayList<>();
        for (int currentCase = 0; currentCase < casesCount; currentCase++) {

            numberCases.add(addNewCase());
        }

        calculateHops(numberCases);
    }


    private void calculateHops(List<RaseCase> numberCases) {


        for (RaseCase rCase : numberCases) {

            GridCoordinates<Integer, Integer> gridSize = rCase.getGridSize();
            int[][] obstaclesLocation = rCase.getObstaclesLocation();
            GridCoordinates<Integer, Integer> starLocation = rCase.getStartEndCoordinates().getWidth();
            GridCoordinates<Integer, Integer> endLocation = rCase.getStartEndCoordinates().getHeight();

            GridCoordinates<Integer, Integer> currentSpeed = new GridCoordinates<>(0, 0);
            GridCoordinates<Integer, Integer> currentLocation = starLocation;

            while (true) {

                boolean possibleHorizontal = false;
                boolean possibleVertical = false;

                int currentHorizont = currentLocation.getWidth();
                GridCoordinates<Integer, Integer> possibleHorizontMove = moveHorizont(currentLocation, currentSpeed, gridSize);
                if (possibleHorizontMove != null) {
                    possibleHorizontal = true;
                }

                int currentVertical = currentLocation.getHeight();
                GridCoordinates<Integer, Integer> possibleVerticalMove = moveVertical(currentLocation, currentSpeed, gridSize);
                if (possibleVerticalMove != null) {
                    possibleVertical = true;
                }

                if (obstaclesLocation[possibleHorizontMove.getHeight()][possibleHorizontMove.getWidth()] != 0) {
                    currentSpeed = new GridCoordinates<>(possibleHorizontMove.getWidth(), possibleVerticalMove.getHeight());
                    currentLocation = new GridCoordinates<>(possibleHorizontMove.getWidth(), possibleHorizontMove.getHeight());
                }

                if (!(possibleHorizontal && possibleVertical) ){

                    System.out.println("No moves");
                    break;
                }

                if(endLocation.getWidth() == possibleHorizontMove.getWidth() && endLocation.getHeight() == possibleHorizontMove.getHeight()){

                    System.out.println("There");
                    break;
                }

            }
        }
    }

    private GridCoordinates moveHorizont(GridCoordinates<Integer, Integer> currentLocation,
                                         GridCoordinates<Integer, Integer> currentSpeed,
                                         GridCoordinates<Integer, Integer> gridSize) {


        int leftSum = currentLocation.getHeight() + currentSpeed.getHeight();


        if ((leftSum + 1) <= gridSize.getHeight()) {
            return new GridCoordinates(leftSum + 1, currentSpeed.getHeight() + 1);
        } else if ((leftSum) <= gridSize.getHeight()) {
            return new GridCoordinates(leftSum, currentSpeed.getHeight());
        } else if ((leftSum - 1) <= gridSize.getHeight()) {
            return new GridCoordinates(leftSum - 1, currentSpeed.getHeight() - 1);
        } else {
            return null;
        }
    }

    private GridCoordinates moveVertical(GridCoordinates<Integer, Integer> currentLocation,
                                         GridCoordinates<Integer, Integer> currentSpeed,
                                         GridCoordinates<Integer, Integer> gridSize) {


        int leftSum = currentLocation.getWidth() + currentSpeed.getWidth();


        if ((leftSum + 1) <= gridSize.getWidth()) {
            return new GridCoordinates(leftSum + 1, currentSpeed.getWidth() + 1);
        } else if ((leftSum) <= gridSize.getWidth()) {
            return new GridCoordinates(leftSum, currentSpeed.getWidth());
        } else if ((leftSum - 1) <= gridSize.getWidth()) {
            return new GridCoordinates(leftSum - 1, currentSpeed.getWidth() - 1);
        } else {
            return null;
        }
    }


    private RaseCase addNewCase() {

        LOGGER.info(INPUT_COORDINATES_GRID_MESSAGE);
        String loginFlutPilePair = consoleReader.getGridSize();
        GridCoordinates<Integer, Integer> gridSize = inputParser.parseGridCoordinates(loginFlutPilePair);
        verifyGridSize(gridSize);

        String startEndLocation = consoleReader.getStartEndLocation();
        GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> startEndCoordinates = inputParser.parseStartEndCoordinates(startEndLocation);
        verifyStartEndLocation(gridSize, startEndCoordinates);

        LOGGER.info("Enter number of obstacles!");
        int obstacleCount = getCasesCount();

        int[][] obstaclesLocation = new int[gridSize.getHeight()][gridSize.getWidth()];

        for (int currentCase = 0; currentCase < obstacleCount; currentCase++) {

            String obstacleLocation = consoleReader.getStartEndLocation();
            GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> obstacleLocationCoordinates = inputParser.parseStartEndCoordinates(obstacleLocation);
            verifyStartEndLocation(gridSize, obstacleLocationCoordinates);

            GridCoordinates<Integer, Integer> first = obstacleLocationCoordinates.getHeight();
            GridCoordinates<Integer, Integer> second = obstacleLocationCoordinates.getWidth();

            obstaclesLocation[first.getHeight()][second.getHeight()] = 1;
            obstaclesLocation[first.getWidth()][second.getWidth()] = 1;
        }


        RaseCase raceCase = new RaseCase();
        raceCase.setGridSize(gridSize);
        raceCase.setStartEndCoordinates(startEndCoordinates);
        raceCase.setObstaclesLocation(obstaclesLocation);

        return raceCase;
    }


    private void verifyGridSize(GridCoordinates<Integer, Integer> gridSize) {

        if (gridSize.getWidth() < 1 || gridSize.getWidth() > 30) {
            throw new IllegalArgumentException("grid width not good!");
        }

        if (gridSize.getHeight() < 1 || gridSize.getHeight() > 30) {
            throw new IllegalArgumentException("grid height not good!");
        }
    }


    private void verifyStartEndLocation(GridCoordinates<Integer, Integer> gridSize, GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> startEndCoordinates) {

        GridCoordinates<Integer, Integer> startCoordinates = startEndCoordinates.getHeight();
        GridCoordinates<Integer, Integer> endCoordinates = startEndCoordinates.getWidth();


        if (startCoordinates.getWidth() >= 0 && endCoordinates.getWidth() >= 0) {
            throw new IllegalArgumentException("Start grid width not good!");
        }

        if (startCoordinates.getWidth() < gridSize.getWidth() && endCoordinates.getWidth() < gridSize.getWidth()) {
            throw new IllegalArgumentException("Start grid width not good!");
        }

        if (startCoordinates.getHeight() >= 0 && endCoordinates.getHeight() >= 0) {
            throw new IllegalArgumentException("Start grid width not good!");
        }

        if (startCoordinates.getHeight() < gridSize.getHeight() && endCoordinates.getHeight() < gridSize.getHeight()) {
            throw new IllegalArgumentException("Start grid width not good!");
        }
    }


    private void verifyFlutsPileInput(int flutsCount, List<Integer> flutsPileList) {
        if (flutsPileList.size() != flutsCount) {
            LOGGER.error(NUMBER_MISMATCH_ERROR_MESSAGE);
            throw new IllegalArgumentException(NUMBER_MISMATCH_ERROR_MESSAGE);
        }

        LOGGER.debug("Check for zero values in the fluts pile.");
        boolean exists = flutsPileList.stream().anyMatch(x -> Objects.equals(x, 0));
        if (exists) {
            LOGGER.error(ZERO_VALUE_NOT_ALLOWED_ERROR_MESSAGE);
            throw new IllegalArgumentException(ZERO_VALUE_NOT_ALLOWED_ERROR_MESSAGE);
        }
    }


    private int getCasesCount() {

        String consoleInput = consoleReader.getNumberOfCases();

        int cases = inputParser.parseCases(consoleInput);
        if (cases < 0) {
            throw new IllegalArgumentException("Not accetable");
        }

        return cases;
    }
}
