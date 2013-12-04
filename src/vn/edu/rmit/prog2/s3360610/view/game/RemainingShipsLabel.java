/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.view.game;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import vn.edu.rmit.prog2.s3360610.model.Map;

public class RemainingShipsLabel extends JLabel implements Observer {

    private int remainingShips = 0;

    public RemainingShipsLabel(Map map) {
        super();

        map.addObserver(this);
        remainingShips = map.getShips().size();
        setText("Remaining monsters on map: " + remainingShips);
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        Map m = (Map) o;
        remainingShips = m.getShips().size();
        setText("Remaining monsters on map: " + remainingShips);
        repaint();
    }
}
