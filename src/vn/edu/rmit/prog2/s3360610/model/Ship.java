/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.List;

public class Ship {

    private Spot[] spots;
    private boolean sunk;
    private int length;
    private int alignment;
    private boolean placedOnMap = false;

    public Ship(List<Spot> spots) {
        this.length = spots.size();
        this.spots = new Spot[this.length];
        for (int i = 0; i < this.length; i++) {
            this.spots[i] = spots.get(i);
        }
        setSpotPositions();
    }

    public Ship(int length, int alignment) {
        this.length = length;
        this.spots = new Spot[length];
        this.alignment = alignment;
    }

    public final void setSpotPositions() {

        for (int i = 0; i < length; i++) {

            if (i == 0) {
                spots[i].setPositionInShip(Spot.FIRST);
            } else if (i == length - 1) {
                spots[i].setPositionInShip(Spot.LAST);
            } else {
                spots[i].setPositionInShip(Spot.MIDDLE);
            }

            spots[i].setShip(this);
            spots[i].setAlignment(alignment);

        }
    }

    public boolean sunkCheck() {

        for (int i = 0; i < length; i++) {

            if (spots[i].getStatus() == Spot.STATUS_SEA
                    || spots[i].getStatus() == Spot.STATUS_OWN) {
                this.sunk = false;
                return false;
            }
        }

        this.sunk = true;
        sink();
        return true;

    }

    public boolean isSunk() {
        return sunk;
    }

    private void sink() {
        for (int i = 0; i < length; i++) {
            spots[i].setStatus(Spot.STATUS_SUNK);
            spots[i].update();
        }

        // Check if all ships in this map have been sunk
    }

    public int getLength() {
        return length;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public Spot[] getSpots() {
        return spots;
    }
}
