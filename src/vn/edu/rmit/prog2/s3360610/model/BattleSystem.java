/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.model;

import javax.sound.sampled.Clip;
import vn.edu.rmit.prog2.s3360610.view.game.GameFrame;
import vn.edu.rmit.prog2.s3360610.view.startup.StartupFrame;

public enum BattleSystem {
    
    INSTANCE;
    
    
    public Map playerMap;
    public Map enemyMap;

    public ShipSet playerShipSet;
    public ShipSet enemyShipSet;
    
    public StartupFrame startupFrame;
    public GameFrame gameFrame;
    
    public WhoseTurn turn = new WhoseTurn();
    
//    public Clip startupMusic;
//    public Clip gameMusic;
    
    public Spot[] hoveringSpots = null;
    
}
