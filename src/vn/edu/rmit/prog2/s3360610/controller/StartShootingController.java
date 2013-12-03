/**
 *
 * @author nhanb
 */

package vn.edu.rmit.prog2.s3360610.controller;

import java.util.Observable;
import java.util.Observer;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.model.WhoseTurn;

public class StartShootingController implements Observer {

    public StartShootingController() {
       
        
        WhoseTurn turn = BattleSystem.INSTANCE.turn;
        turn.addObserver(this);
        
        turn.setPlayersTurn(true);

    }

    @Override
    public void update(Observable o, Object arg) {
        WhoseTurn turn = (WhoseTurn) o;
        
        // If it's the player's turn then let them shoot
        if (turn.isPlayers()) {
            BattleSystem.INSTANCE.enemyMap.setListenerMode(Map.LISTENER_SHOOT);
        } else {
            BattleSystem.INSTANCE.enemyMap.setListenerMode(Map.LISTENER_NONE);
        }
    }
    
}
