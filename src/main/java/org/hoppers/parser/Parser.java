package org.hoppers.parser;

import org.hoppers.utility.GridCoordinates;

public interface Parser {

    Integer parseCases(String input);

    GridCoordinates<Integer, Integer> parseGridCoordinates(String input);

    GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> parseStartEndCoordinates(String input);

}
