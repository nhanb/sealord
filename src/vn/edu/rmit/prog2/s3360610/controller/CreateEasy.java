/**
 *
 * @author nhanb
 */

package vn.edu.rmit.prog2.s3360610.controller;

import javax.swing.JFrame;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.model.EasyAI;

public class CreateEasy extends CreateGameController {

    public CreateEasy(JFrame prev) {
        super(prev);
        
        System.out.println("Starting easy game...");
        EasyAI a = new EasyAI(BattleSystem.INSTANCE.enemyMap, BattleSystem.INSTANCE.playerMap);
        a.placeShips();
        
    }
    
}
