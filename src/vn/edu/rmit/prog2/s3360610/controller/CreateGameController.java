/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import vn.edu.rmit.prog2.s3360610.main.Main;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.Map;
import vn.edu.rmit.prog2.s3360610.model.ShipSet;
import vn.edu.rmit.prog2.s3360610.view.game.GameFrame;

public abstract class CreateGameController {
    protected GameFrame gameFrame;
    private JFrame prev;

    public CreateGameController(JFrame prev) {
        this.prev = prev;
        
//        BattleSystem.INSTANCE.startupMusic.stop();

        BattleSystem.INSTANCE.playerMap = new Map(Main.MAP_X, Main.MAP_Y, Map.PLAYER);
        BattleSystem.INSTANCE.enemyMap = new Map(Main.MAP_X, Main.MAP_Y, Map.ENEMY);

        BattleSystem.INSTANCE.playerShipSet = new ShipSet();
        BattleSystem.INSTANCE.enemyShipSet = new ShipSet();

        // Hide startup window
        prev.setVisible(false);

        gameFrame = new GameFrame(prev);
        
    }
}
