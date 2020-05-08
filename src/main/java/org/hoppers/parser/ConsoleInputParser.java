package org.hoppers.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hoppers.utility.GridCoordinates;

import java.util.Arrays;

/**
 * This class is used to parse the ser input for the Fluts Calculator
 */
public class ConsoleInputParser implements Parser {

    private static final Logger LOGGER = LogManager.getLogger(ConsoleInputParser.class);

    private static final String EMPTY_INPUT_MESSAGE = "User input is empty!";
    private static final String COORDINATES_NUMBER_MISSMATCH_MESSAGE = "Not enough values for coordinates!";
    private static final String ONLY_SINGLE_INTEGER_MESSAGE = "The input must be a single integer";
    private static final String INVALID_INPUT_MESSAGE = "The input must be only from integer positive values!";
    private static final String CONVERT_STRING_LIST_MESSAGE = "Convert the String input fluts list  to Integer list";
    private static final String SET_THE_INPUT_FLUTS_TO_STRING_LIST_MESSAGE = "Set the input fluts to String list";

    /**
     * Parse the user input for correctly entered single integer
     *
     * @param input user console input string
     * @return the parsed integer input
     */
    public Integer parseCases(String input) {

        if (input.trim().isEmpty()) {
            LOGGER.error(EMPTY_INPUT_MESSAGE);

            throw new IllegalArgumentException(EMPTY_INPUT_MESSAGE);
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException nfe) {
            LOGGER.error(ONLY_SINGLE_INTEGER_MESSAGE);

            throw new IllegalArgumentException(ONLY_SINGLE_INTEGER_MESSAGE);
        }
    }

    public GridCoordinates<Integer, Integer> parseGridCoordinates(String input) {

        if (input.trim().isEmpty()) {
            LOGGER.error(EMPTY_INPUT_MESSAGE);

            throw new IllegalArgumentException(EMPTY_INPUT_MESSAGE);
        }

        try {
            String[] coordinates = input.trim().split(" ");

            if (coordinates.length != 2) {
                LOGGER.error(COORDINATES_NUMBER_MISSMATCH_MESSAGE);

                throw new IllegalArgumentException(COORDINATES_NUMBER_MISSMATCH_MESSAGE);
            }

            int[] array = Arrays.stream(coordinates).mapToInt(Integer::parseInt).toArray();
            return new GridCoordinates(array[0], array[1]);

        } catch (NumberFormatException nfe) {
            LOGGER.error(COORDINATES_NUMBER_MISSMATCH_MESSAGE);

            throw new IllegalArgumentException(COORDINATES_NUMBER_MISSMATCH_MESSAGE);
        }
    }


    public GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> parseStartEndCoordinates(String input) {

        if (input.trim().isEmpty()) {
            LOGGER.error(EMPTY_INPUT_MESSAGE);

            throw new IllegalArgumentException(EMPTY_INPUT_MESSAGE);
        }

        try {
            String[] coordinates = input.trim().split(" ");

            if (coordinates.length != 4) {
                LOGGER.error(COORDINATES_NUMBER_MISSMATCH_MESSAGE);

                throw new IllegalArgumentException(COORDINATES_NUMBER_MISSMATCH_MESSAGE);
            }

            int[] array = Arrays.stream(coordinates).mapToInt(Integer::parseInt).toArray();
            GridCoordinates startCoordinates = new GridCoordinates(array[0], array[1]);
            GridCoordinates endCoordinates = new GridCoordinates(array[2], array[3]);

            return new GridCoordinates(startCoordinates, endCoordinates);

        } catch (NumberFormatException nfe) {
            LOGGER.error(COORDINATES_NUMBER_MISSMATCH_MESSAGE);

            throw new IllegalArgumentException(COORDINATES_NUMBER_MISSMATCH_MESSAGE);
        }
    }
}
