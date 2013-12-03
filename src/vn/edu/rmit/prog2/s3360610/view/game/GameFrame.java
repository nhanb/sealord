/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.view.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import vn.edu.rmit.prog2.s3360610.main.Main;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;

public class GameFrame extends JFrame {
    
    private JFrame prevFrame;
    
    public GameFrame(final JFrame prev) {
        this.prevFrame = prev;
        
        BattleSystem.INSTANCE.gameFrame = this;
        
        setTitle(Main.GAME_TITLE);
        
        GamePanel gPanel = new GamePanel();
        add(gPanel);
        
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // When this windows closes, show previous window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prev.setVisible(true);
                super.windowClosing(e);
            }
        });
    }
    
    
}
