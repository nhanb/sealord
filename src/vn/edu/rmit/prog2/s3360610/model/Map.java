/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import vn.edu.rmit.prog2.s3360610.controller.EndGameController;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShipPositionException;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShotException;

public class Map extends Observable {

    public static final int PLAYER = 1;
    public static final int ENEMY = 2;
    
    public static final int LISTENER_PLACE_SHIP = 1;
    public static final int LISTENER_SHOOT = 2;
    public static final int LISTENER_NONE = 3;
    
    private int maxX;
    private int maxY;
    private ArrayList<Spot> spots;
    private List<Ship> ships;
    private int listenerMode; // Decides its view's behaviour

    public Map(int maxX, int maxY, int type) {

        this.maxX = maxX;
        this.maxY = maxY;
        this.ships = new ArrayList<>();
        this.spots = new ArrayList<>(maxX * maxY);

        // Instantiate Spots for the map in the order from left to right and
        // top to bottom. Each Spot has a reference to this containing map
        for (int y = 1; y <= maxY; y++) {
            for (int x = 1; x <= maxX; x++) {
                Spot newSpot = new Spot(x, y, this);
                this.spots.add(newSpot);
                
                // If this map belongs to the player
                if (type == PLAYER) {
                    newSpot.setStatus(Spot.STATUS_OWN);
                }
            }
        }
    }

    public int getListenerMode() {
        return listenerMode;
    }

    public void setListenerMode(int listenerMode) {
        this.listenerMode = listenerMode;
        setChanged();
        notifyObservers(this.listenerMode);
    }
    
    // Place a ship on this map with starting spot coordinate (x;y)
    public void placeShip(int x, int y, Ship ship)
            throws InvalidShipPositionException {

        int alignment = ship.getAlignment();

        Spot[] potentialSpots = getSpotsOfShipStartingFrom(x, y, ship);

        for (Spot spot : potentialSpots) {
            if (spot.getPositionInShip() != Spot.NO_SHIP) {
                throw new InvalidShipPositionException("Overlapping ships");
            }
        }

        // If it can come to this line then there is no clash and the ship
        // can be place as specified
        for (int i = 0; i < ship.getLength(); i++) {
            if (i == 0) {
                potentialSpots[i].placeShip(Spot.FIRST, ship);
            } else if (i == ship.getLength() - 1) {
                potentialSpots[i].placeShip(Spot.LAST, ship);
            } else {
                potentialSpots[i].placeShip(Spot.MIDDLE, ship);
            }
            ship.getSpots()[i] = potentialSpots[i];
        }
        
        this.ships.add(ship);

        setChanged();
        notifyObservers(this);
    }
    
    // Place a ship on this map with starting spot as passed in
    public void placeShip(Spot s, Ship ship)
            throws InvalidShipPositionException {

        placeShip(s.getX(), s.getY(), ship);
    }

    // Convert a spot's (x;y) coordinate to its index in this map's arraylist
    public int XYToIndex(int x, int y) {
        return (y - 1) * maxX + x - 1;
    }

    public void shoot(int x, int y) throws InvalidShotException {

        int hitIndex = XYToIndex(x, y);

        if (x < 1 || y < 1 || x > maxX || y > maxY) {
            throw new InvalidShotException("Cannot shoot at (" + x + "; " + y + ")"
                    + " - the map's maximum size is (" + maxX + ", " + maxY + ")");
        }

        Spot spot = this.spots.get(hitIndex);

        this.shoot(spot);
    }

    // Shoot this spot. If there is a ship there, check if it's completely
    // sunk
    public boolean shoot(Spot s) throws InvalidShotException {
        
        // If it was a hit
        if (s.shoot()) {
            Ship sh = s.getShip();
            
            // If the ship has been sunk:
            if (sh.sunkCheck()) {
                
                // Check if all others have sunk too so we can end the game
                if (allSunk()) {
                    System.out.println("ended");
                    new EndGameController(this);
                }
                
                // Remove this ship from the "alive ships" list
                this.ships.remove(sh);
            }
            
            return true;
        } else {
            return false;
        }
    }

    public void printMap() {
        for (Iterator i = spots.iterator(); i.hasNext();) {
            Spot s = (Spot) i.next();
            System.out.print(s);
            if (s.getX() == this.maxX) {
                System.out.println();
            }
        }
    }
    
    public boolean allSunk() {
        for (Iterator i = ships.iterator(); i.hasNext(); ) {
            Ship ship = (Ship) i.next();
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Return array of spots that would be occupied if a ship were to be put
    // at point (x;y)
    public Spot[] getSpotsOfShipStartingFrom(int x, int y, Ship ship) throws InvalidShipPositionException {
        Spot[] selected = new Spot[ship.getLength()];

        int alignment = ship.getAlignment();

        // For horizontal ship:
        if (alignment == Spot.HORIZONTAL) {

            // If the user tries to place ship that goes beyond the x barrier:
            if (x + ship.getLength() - 1 > maxX) {
                throw new InvalidShipPositionException(
                        "Placing ship at spot with x=" + (x + ship.getLength() - 1)
                        + " ( > maxX )");
            }

            // Get slots from left to right, starting at (x;y)
            int currentSpot = XYToIndex(x, y);
            int max = currentSpot + ship.getLength() - 1;
            int indexInSelected = 0;
            while (currentSpot <= max) {
                selected[indexInSelected] = spots.get(currentSpot);
                currentSpot++;
                indexInSelected++;
            }

            // Place vertical ship:
        } else if (alignment == Spot.VERTICAL) {

            // If the user tries to place ship that goes beyond the x barrier:
            if (y + ship.getLength() - 1 > maxY) {
                throw new InvalidShipPositionException(
                        "Placing ship at spot with y=" + (x + ship.getLength() - 1)
                        + " ( > maxY )");
            }

            // Find occupied spots from top to bottom,
            // starting at (x;y)
            int currentSpot = XYToIndex(x, y);
            int max = currentSpot + ship.getLength() * (maxX - 1);
            int indexInSelected = 0;
            while (currentSpot <= max) {
                Spot spot = spots.get(currentSpot);
                selected[indexInSelected] = spot;
                currentSpot += maxX;
                indexInSelected++;
            }
        }

        return selected;
    }

    public Spot getSpotByXY(int x, int y) {
        int index = XYToIndex(x, y);
        return spots.get(index);
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public ArrayList<Spot> getSpots() {
        return spots;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
