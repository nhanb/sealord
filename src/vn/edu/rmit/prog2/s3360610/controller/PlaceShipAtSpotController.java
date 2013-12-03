/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShipPositionException;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.model.Ship;
import vn.edu.rmit.prog2.s3360610.model.ShipSet;
import vn.edu.rmit.prog2.s3360610.model.Spot;
import vn.edu.rmit.prog2.s3360610.view.shared.SpotView;

public class PlaceShipAtSpotController extends MouseAdapter {

    private SpotView sv;

    public PlaceShipAtSpotController(SpotView sv) {
        this.sv = sv;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        Spot sp = sv.getSpot();


        Ship currentShip = BattleSystem.INSTANCE.playerShipSet.getLast();

        try {
            Spot[] shipSpots = sp.getMap().getSpotsOfShipStartingFrom(sp.getX(), sp.getY(), currentShip);
            BattleSystem.INSTANCE.hoveringSpots = shipSpots;
            for (Spot s : shipSpots) {
                s.setPlaceShipHover(true);
            }
            
        } catch (Exception ex) {
            sp.setPlaceShipHover(true);
        } 

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Spot sp = sv.getSpot();


        Ship currentShip = BattleSystem.INSTANCE.playerShipSet.getLast();

        try {
            Spot[] shipSpots = sp.getMap().getSpotsOfShipStartingFrom(sp.getX(), sp.getY(), currentShip);
            for (Spot s : shipSpots) {
                s.setPlaceShipHover(false);
            }
            
        } catch (Exception ex) {
            sp.setPlaceShipHover(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Spot sp = sv.getSpot();
        Map m = sp.getMap();

        ShipSet playerShipSet = BattleSystem.INSTANCE.playerShipSet;

        try {
            // Place the next ship available into the map
            m.placeShip(sp.getX(), sp.getY(), playerShipSet.getLast());

            // If placement was successful then ditch the hover highlight
            Spot[] shipSpots = playerShipSet.getLast().getSpots();
            for (Spot s: shipSpots) {
                s.setPlaceShipHover(false);
            }

            
            // Remove placed ship from ship set
            playerShipSet.pop();
            
            // When all ships have been placed, disable all mouse listeners
            // on this map to prevent messing it up
            if (playerShipSet.size() == 0) {
                System.out.println("No more ships to place");
                m.setListenerMode(Map.LISTENER_NONE);

                new StartShootingController();

            }
            
        } catch (InvalidShipPositionException ie) {
            System.out.println("Could not place ship at (" + sp.getX() + ";" + sp.getY() + ")");
            //playerShipSet.restorePopped();
        }
    }
}
