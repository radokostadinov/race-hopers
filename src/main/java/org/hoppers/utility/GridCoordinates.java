package org.hoppers.utility;

import lombok.Getter;

/**
 * FlutsPilePAir class used to save the number and all fluts price
 *
 * @param <L>
 * @param <R>
 */
public class GridCoordinates<L, R> {

    @Getter
    private final L width;

    @Getter
    private final R height;

    public GridCoordinates(L width, R height) {
        this.width = width;
        this.height = height;
    }
}
