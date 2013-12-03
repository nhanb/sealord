/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * This is a group of ships that are given to a player to place on their
 * map
 */
public class ShipSet extends ArrayList<Ship> {

    private Ship popped;

    public ShipSet() {

        // Generate 6 ships with length 6, 5, 4, 3, 2, 2
        for (int i = 6; i > 0; i--) {
            if (i == 1) {
                this.add(new Ship(2, Spot.HORIZONTAL));
            } else {
                this.add(new Ship(i, Spot.HORIZONTAL));
            }
        }
    }

    // Remove the and return the last ship
    public Ship pop() {
        this.popped = remove(size() - 1);
        return popped;
    }

    public Ship getLast() {
        if (!this.isEmpty()) {
            return this.get(size() - 1);
        } else {
            return null;
        }
    }

    // Restore previously popped ship
    public void restorePopped() {
        this.add(popped);
    }

    public int changeAlignment() {
        int re;

        Ship next = getLast();
        if (next.getAlignment() == Spot.HORIZONTAL) {
            next.setAlignment(Spot.VERTICAL);
            re = Spot.VERTICAL;
        } else {
            next.setAlignment(Spot.HORIZONTAL);
            re = Spot.HORIZONTAL;
        }

        // Refresh last ship alignment's occupied spots
        if (BattleSystem.INSTANCE.hoveringSpots != null) {
            for (Spot s : BattleSystem.INSTANCE.hoveringSpots) {

                s.setPlaceShipHover(false);

            }
        }

        return re;
    }
}
