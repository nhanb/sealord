/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidShotException;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.model.Sound;
import vn.edu.rmit.prog2.s3360610.model.Spot;
import vn.edu.rmit.prog2.s3360610.view.shared.SpotView;

public class ShootSpotController extends MouseAdapter {

    SpotView sv;

    public ShootSpotController(SpotView sv) {
        this.sv = sv;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        sv.getSpot().setCrosshairHover(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        sv.getSpot().setCrosshairHover(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (BattleSystem.INSTANCE.turn.isPlayers()) {
            Spot sModel = sv.getSpot();

            // Prevent shooting an already shot spot
            if (sModel.getStatus() == Spot.STATUS_HIT
                    || sModel.getStatus() == Spot.STATUS_MISS
                    || sModel.getStatus() == Spot.STATUS_SUNK) {
                System.out.println("Can't shoot here. It's already been shot.");
                return;
            }

            Map map = sModel.getMap();
            try {


                if (map.shoot(sModel)) {
                    Sound.play("hit");
                    Sound.play("fire");
                } else {
                    Sound.play("shoot2");
                }


            } catch (InvalidShotException ie) {
                ie.printStackTrace();
            }
            sv.repaint();
            BattleSystem.INSTANCE.turn.setPlayersTurn(false);

        } else {
            System.out.println("Not your turn!");
        }
    }
}
