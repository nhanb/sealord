/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.view.shared;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import vn.edu.rmit.prog2.s3360610.controller.PlaceShipAtSpotController;
import vn.edu.rmit.prog2.s3360610.controller.ShootSpotController;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.model.Spot;

public class MapView extends JPanel implements Observer {


    private List<SpotView> spotViews = new ArrayList<>();
    private Map mapModel;

    public MapView(Map mapModel) {

        this.mapModel = mapModel;
        mapModel.addObserver(this);
        List<Spot> spotModels = mapModel.getSpots();

        setLayout(new GridLayout(10, 10, 1, 1));

        // Populate the spots:
        for (Iterator i = spotModels.iterator(); i.hasNext();) {
            Spot currentSpot = (Spot) i.next();
            SpotView sv = new SpotView(40, 40, currentSpot);
            add(sv);
            spotViews.add(sv);
        }
        
        // At the beginning, the player's map will be open for placing ships,
        // while the enemy's map is closed
        if (mapModel == BattleSystem.INSTANCE.playerMap) {
            chooseSpotViewMouseListener(Map.LISTENER_PLACE_SHIP);
        } else if (mapModel == BattleSystem.INSTANCE.enemyMap) {
            chooseSpotViewMouseListener(Map.LISTENER_NONE);
        }


    }

    public final void chooseSpotViewMouseListener(int choice) {
        switch (choice) {

            case Map.LISTENER_PLACE_SHIP:
                for (Iterator i = spotViews.iterator(); i.hasNext();) {
                    SpotView currentSpotView = (SpotView) i.next();
                    // Remove all previously added mouse listeners:
                    for (MouseListener ml : currentSpotView.getMouseListeners()) {
                        currentSpotView.removeMouseListener(ml);
                    }
                    currentSpotView.addMouseListener(new PlaceShipAtSpotController(currentSpotView));
                }
                break;

            case Map.LISTENER_SHOOT:
                for (Iterator i = spotViews.iterator(); i.hasNext();) {
                    SpotView currentSpotView = (SpotView) i.next();
                    // Remove all previously added mouse listeners:
                    for (MouseListener ml : currentSpotView.getMouseListeners()) {
                        currentSpotView.removeMouseListener(ml);
                    }
                    currentSpotView.addMouseListener(new ShootSpotController(currentSpotView));
                }
                break;

            case Map.LISTENER_NONE:
                for (Iterator i = spotViews.iterator(); i.hasNext();) {
                    SpotView currentSpotView = (SpotView) i.next();
                    // Remove all previously added mouse listeners:
                    for (MouseListener ml : currentSpotView.getMouseListeners()) {
                        currentSpotView.removeMouseListener(ml);
                    }
                }
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map map = (Map) o;
        chooseSpotViewMouseListener(map.getListenerMode());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public Map getMapModel() {
        return mapModel;
    }
}
