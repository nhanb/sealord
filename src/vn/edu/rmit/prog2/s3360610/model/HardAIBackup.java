/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShipPositionException;
import vn.edu.rmit.prog2.s3360610.main.Main;

public class HardAIBackup extends AI {

    private Shot previousShot = null;

    public HardAIBackup(Map map, Map targetMap) {
        super(map, targetMap);
    }

    @Override
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

        // 50/50 decider
        Random tossACoin = new Random();

        while (!shipSet.isEmpty()) {

            // Decide whether to change ship alignment
            boolean changeAlignment = tossACoin.nextBoolean();
            if (changeAlignment) {
                shipSet.changeAlignment();
            }


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

    @Override
    public void shoot() {
        super.shoot();

        // If there is no clue from the previous shot then shoot randomly
        if (previousShot == null
                || previousShot.possibleNextTargets.isEmpty()) {

            Integer targetIndex = randomIndex(targets);

            try {

                Spot targetSpot = targetMap.getSpots().get(targetIndex);

                // if it's a hit
                if (targetMap.shoot(targetSpot)) {
                    previousShot = new Shot(true, targetSpot);
                }

                targets.remove(targetIndex);

            } catch (Exception e) {
                e.printStackTrace();

            }

            // If there is some clue from last shots
        } else {

            // Randomly shoot one of the surrounding slots of last hit
            Integer targetIndex = randomIndex(previousShot.possibleNextTargets);

            try {

                Spot targetSpot = targetMap.getSpots().get(targetIndex);

                // If it's a hit then create new Shot instance to guide the
                // next shot
                if (targetMap.shoot(targetSpot)) {
                    previousShot = new Shot(true, targetSpot);

                } else {
                }

                previousShot.possibleNextTargets.remove(targetIndex);
                targets.remove(targetIndex);

            } catch (Exception e) {
                e.printStackTrace();







            }

        }
    }

    // Dedicated class to represent the previous shot as well as possible next
    // targets surrounding this spot
    public class Shot {

        boolean hit;
        Spot target;
        List<Integer> possibleNextTargets = new ArrayList<>();

        public Shot(boolean hit, Spot target) {
            this.hit = hit;
            this.target = target;

            /*
             * Getting surrounding spots
             */

            // All possible target indexes from map
            List<Integer> targets = HardAIBackup.super.targets;
            Map targetMap = HardAIBackup.super.targetMap;
            int maxX = Main.MAP_X;
            int maxY = Main.MAP_Y;

            possibleNextTargets.addAll(getSurroundingIndexes(target.getIndex()));
        }

        public final boolean isValid(Integer index) {
            if (index < 1 || index > Main.MAP_X * Main.MAP_Y) {
                return false;
            } else {
                return true;
            }
        }

        public final List<Integer> getSurroundingIndexes(Integer origin) {

            // All possible target indexes from map
            List<Integer> targets = HardAIBackup.super.targets;
            int maxX = Main.MAP_X;

            List<Integer> returns = new ArrayList<>();

            // 1 spot above target
            Integer current = origin - maxX;
            if (isValid(current) || targets.contains(current)) {
                returns.add(current);
            }

            // 1 spot below
            current = origin - maxX;
            if (isValid(current) || targets.contains(current)) {
                returns.add(current);
            }

            // 1 spot to the right
            if (current % maxX != 0) {
                current = origin + 1;
                if (isValid(current) || targets.contains(current)) {
                    returns.add(current);
                }
            }

            // 1 spot to the left
            if (current % maxX != 1) {
                current = origin - maxX;
                if (isValid(current) || targets.contains(current)) {
                    returns.add(current);
                }
            }

            return returns;
        }

        // 1 spot above target
        public Integer getUp(Integer origin, int maxX) {
            Integer current = origin - maxX;
            if (isValid(current) || targets.contains(current)) {
                return current;
            } else {
                return null;
            }
        }
    }
}
