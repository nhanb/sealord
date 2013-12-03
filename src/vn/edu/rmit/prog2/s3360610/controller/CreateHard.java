/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.controller;

import vn.edu.rmit.prog2.s3360610.model.HardAI;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.HardAIBackup;

public class CreateHard extends CreateGameController {

    public CreateHard(JFrame prev) {
        super(prev);

        gameFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    BattleSystem.INSTANCE.playerShipSet.changeAlignment();
                    System.out.println("Ship alignment changed");
                }
            }
            
        });
        
        System.out.println("Starting hard game...");
        HardAI a = new HardAI(BattleSystem.INSTANCE.enemyMap, BattleSystem.INSTANCE.playerMap);
        a.placeShips();

        
    }
}
