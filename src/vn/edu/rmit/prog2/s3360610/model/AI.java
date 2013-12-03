/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShipPositionException;

public abstract class AI implements Observer {

    protected Map targetMap;
    protected Map map;
    protected List<Integer> targets;

    public AI(Map map, Map targetMap) {
        this.map = map;
        this.targetMap = targetMap;
        
        // Wait for its turn
        BattleSystem.INSTANCE.turn.addObserver(this);
        
        // Generate list of available target indexes to shoot
        targets = new ArrayList<>();
        int counter = 0;
        for (ListIterator i = targetMap.getSpots().listIterator(); i.hasNext();) {
            i.next();
            targets.add(new Integer(counter++));
        }
        
    }

    
    public void placeShips() {

        // List of available indexes for placing a new ship
        List<Integer> availableIndexes = new ArrayList<>();

        // All available spots:
        List<Spot> spots = BattleSystem.INSTANCE.enemyMap.getSpots();

        // Get all available indexes:
        int counter = 0;
        for (ListIterator i = spots.listIterator(); i.hasNext();) {
            i.next();
            availableIndexes.add(new Integer(counter++));
        }

        ShipSet shipSet = BattleSystem.INSTANCE.enemyShipSet;

        while (shipSet.size() > 0) {

            Ship nextShip = shipSet.pop();
            int index = randomIndex(availableIndexes);

            try {
                Spot chosen = map.getSpots().get(index);
                map.placeShip(chosen, nextShip);
                System.out.println("*** SPOILER ALERT *** | placed ship at " + chosen);

                // Remove index from availableIndexes:
                availableIndexes.remove(new Integer(index));

            } catch (InvalidShipPositionException ie) {

                // Remove index from availableIndexes:
                availableIndexes.remove(new Integer(index));

                // Restore ship into ShipSet
                shipSet.restorePopped();

            } catch (Exception e) {
                break;
            }
        }
    }

    public int randomIndex(List<Integer> indexes) {
        Date rightNow = new Date();
        int size = indexes.size();

        int timestamp = (int) rightNow.getTime();
        timestamp *= (int) (Math.random() * 10);
        timestamp = Math.abs(timestamp);

        return indexes.get(timestamp % size);
    }

    public void shoot() {
        try {
//            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        WhoseTurn t = (WhoseTurn) o;
        if (!t.isPlayers()) {
            shoot();
            t.switchTurn();
        }
    }
}
