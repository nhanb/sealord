/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.controller;

import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.view.game.EndGameFrame;

public class EndGameController {

    public EndGameController(Map loserMap) {

        if (loserMap == BattleSystem.INSTANCE.enemyMap) {
            BattleSystem.INSTANCE.enemyMap.setListenerMode(Map.LISTENER_NONE);
            BattleSystem.INSTANCE.playerMap.setListenerMode(Map.LISTENER_NONE);
            new EndGameFrame(EndGameFrame.WON);
            
        } else {
            BattleSystem.INSTANCE.enemyMap.setListenerMode(Map.LISTENER_NONE);
            BattleSystem.INSTANCE.playerMap.setListenerMode(Map.LISTENER_NONE);
            new EndGameFrame(EndGameFrame.LOST);
        }

    }
}
