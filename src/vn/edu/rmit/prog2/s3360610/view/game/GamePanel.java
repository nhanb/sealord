/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.view.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.view.shared.MapView;

public class GamePanel extends JPanel {
    
    private RemainingShipsLabel leftRemainings;
    private RemainingShipsLabel rightRemainings;
    
    public GamePanel() {
        super();
        
        this.leftRemainings = new RemainingShipsLabel(BattleSystem.INSTANCE.playerMap);
        this.rightRemainings = new RemainingShipsLabel(BattleSystem.INSTANCE.enemyMap);
        
        Dimension d = new Dimension(950, 600);
        setSize(d);
        setPreferredSize(d);
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        // Left panel - for player to put ships
        JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER));
        left.setSize(430, 450);
        //left.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        left.setPreferredSize(new Dimension(450, 450));
        left.add(new RemainingShipsLabel(BattleSystem.INSTANCE.playerMap));
        left.add(new MapView(BattleSystem.INSTANCE.playerMap));
        add(left);
        
        // Right panel - for shooting enemy's ships
        JPanel right = new JPanel(new FlowLayout(FlowLayout.CENTER));
        right.setSize(450, 450);
        //right.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        right.setPreferredSize(new Dimension(450, 450));
        right.add(new RemainingShipsLabel(BattleSystem.INSTANCE.enemyMap));
        right.add(new MapView(BattleSystem.INSTANCE.enemyMap));
        add(right);
        
        
    }


}
