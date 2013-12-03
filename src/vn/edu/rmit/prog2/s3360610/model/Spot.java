/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.Observable;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShotException;

public class Spot extends Observable {

    private int status;
    // Default status when it's rendered with a sea tile:
    public static final int STATUS_SEA = 1;
    // When this point has been shot but no ship was hit there:
    public static final int STATUS_MISS = 2;
    // When there is a ship lying here and this point has been shot:
    public static final int STATUS_HIT = 3;
    // When there is a sunk ship here: (Its every spot has been hit)
    public static final int STATUS_SUNK = 4;
    // This only applies to spots belonging to the current player
    public static final int STATUS_OWN = 5;
    private int alignment;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    private int positionInShip;
    public static final int FIRST = 1;
    public static final int MIDDLE = 2;
    public static final int LAST = 3;
    // If there is no ship lying at this spot:
    public static final int NO_SHIP = 5;
    private int x;
    private int y;
    // Reference to the map containing this spot
    private Map map;
    // Reference to the ship placed at this spot
    private Ship ship = null;
    
    private boolean crosshairHover = false;
    private boolean placeShipHover = false;

    public Spot(int x, int y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;

        this.status = STATUS_SEA;
        this.positionInShip = NO_SHIP;
    }

    public boolean isCrosshairHover() {
        return crosshairHover;
    }

    public void setCrosshairHover(boolean crosshairHover) {
        this.crosshairHover = crosshairHover;
        setChanged();
        notifyObservers();
    }

    public boolean isPlaceShipHover() {
        return placeShipHover;
    }

    public void setPlaceShipHover(boolean placeShipHover) {
        this.placeShipHover = placeShipHover;
        setChanged();
        notifyObservers();
    }
    
    

    // This should only be called from the containing Map's placeShip() method
    public void placeShip(int positionInShip, Ship ship) {
        this.ship = ship;
        this.alignment = ship.getAlignment();
        this.positionInShip = positionInShip;
        setChanged();
        notifyObservers();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setPositionInShip(int positionInShip) {
        this.positionInShip = positionInShip;
    }

    @Override
    public String toString() {

        return "(" + x + ";" + y + ")";

    }

    public boolean shoot() throws InvalidShotException {

        if (this.positionInShip != Spot.NO_SHIP) {
            this.status = Spot.STATUS_HIT;
            setChanged();
            notifyObservers();

            return true;
        } else {
            this.status = Spot.STATUS_MISS;
            setChanged();
            notifyObservers();
            return false;
        }


    }

    public String shippedOrNot() {

        switch (this.positionInShip) {
            case Spot.MIDDLE:
                return "M ";
            case Spot.FIRST:
                return "F ";
            case Spot.LAST:
                return "L ";
            case Spot.NO_SHIP:
                return "O ";
            default:
                return "X ";
        }



    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return map.XYToIndex(x, y);
    }

    public int getStatus() {
        return status;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getPositionInShip() {
        return positionInShip;
    }

    public Map getMap() {
        return map;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
