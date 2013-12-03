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

public class HardAI extends AI {

    private HardAI.Shot previousShot = null;

    public HardAI(Map map, Map targetMap) {
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
                || previousShot.noSmartTargets()) {

            Integer targetIndex = randomIndex(targets);

            try {

                Spot targetSpot = targetMap.getSpots().get(targetIndex);

                // if it's a hit
                if (targetMap.shoot(targetSpot)) {
                    previousShot = new HardAI.Shot(true, targetSpot);
                }

                targets.remove(targetIndex);

            } catch (Exception e) {
                e.printStackTrace();

            }

            // If there is some clue from last shots
        } else {

            // Randomly shoot one of the surrounding slots of last hit
            int direction = previousShot.chooseDirection.nextInt(4);
            while (previousShot.smartTargets[direction] == 0) {
                direction = previousShot.chooseDirection.nextInt(4);
            }


            Integer targetIndex = previousShot.smartTargets[direction];

            try {

                Spot targetSpot = targetMap.getSpots().get(targetIndex);

                // If it's a hit then remove the spots in the
                // irrelevant direction
                if (targetMap.shoot(targetSpot)) {

                    if (direction == 0 || direction == 2) {
                        previousShot.removeHorizontal();
                    } else {
                        previousShot.removeVertical();
                    }

                    if (targetSpot.getShip().isSunk()) {
                        previousShot = null;

                        targets.remove(targetIndex);
                        return;
                    }

                } else {
                }

                previousShot.smartTargets[direction] = 0;
                targets.remove(targetIndex);

            } catch (Exception e) {
                e.printStackTrace();


            }

        }
    }

// Dedicated class to represent the previous shot as well as possible next
// targets surrounding this spot
    public class Shot {

        Random chooseDirection = new Random();
        boolean hit;
        Spot target;
        Integer[] smartTargets = new Integer[4];

        public Shot(boolean hit, Spot target) {

            this.hit = hit;
            this.target = target;

            /*
             * Getting surrounding spots
             */

            // All possible target indexes from map
            List<Integer> targets = HardAI.super.targets;
            Map targetMap = HardAI.super.targetMap;
            int maxX = Main.MAP_X;
            int maxY = Main.MAP_Y;

            smartTargets = getSurroundingIndexes(target.getIndex());

            System.out.println("Got a clue! Origin: " + target.getIndex() + ". Possible targets: ");
            for (Integer i : smartTargets) {
                System.out.println(i);
            }
        }

        public final boolean isValid(Integer index) {
            if (index < 0 || index > Main.MAP_X * Main.MAP_Y - 1) {
                return false;
            } else {
                return true;
            }
        }

        public void removeVertical() {
            smartTargets[0] = 0;
            smartTargets[2] = 0;
        }

        public void removeHorizontal() {
            smartTargets[1] = 0;
            smartTargets[3] = 0;
        }

        public boolean noSmartTargets() {
            for (Integer i : smartTargets) {
                if (i != 0) {
                    return false;
                }
            }
            return true;
        }

        public final Integer[] getSurroundingIndexes(Integer origin) {

            Integer[] returns = new Integer[4];

            for (Integer i : returns) {
                i = 0;
            }

            // All possible target indexes from map
            List<Integer> targets = HardAI.super.targets;
            int maxX = Main.MAP_X;

            // 1 spot above target
            Integer current = origin - maxX;
            if (isValid(current) || targets.contains(current)) {
                returns[0] = current;
            }

            // 1 spot below
            current = origin + maxX;
            if (isValid(current) || targets.contains(current)) {
                returns[2] = current;
            }

            // 1 spot to the right
            if (origin % maxX != maxX - 1) {
                current = origin + 1;
                if (isValid(current) || targets.contains(current)) {
                    returns[1] = current;
                }
            }

            // 1 spot to the left
            if (origin % maxX != 0) {
                current = origin - 1;
                if (isValid(current) || targets.contains(current)) {
                    returns[3] = current;
                }
            }

            return returns;
        }
    }
}
