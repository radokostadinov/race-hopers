package org.hoppers.utility;

import lombok.Data;

@Data
public class RaseCase {

    private GridCoordinates<Integer, Integer> gridSize;
    private GridCoordinates<GridCoordinates<Integer, Integer>, GridCoordinates<Integer, Integer>> startEndCoordinates;
    int[][] obstaclesLocation;
}
