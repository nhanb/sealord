/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.view.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.view.shared.BJButton;

public class EndGameFrame extends JFrame {

    public static final int WON = 1;
    public static final int LOST = 0;
    
    public String bgPath;
    
    public EndGameFrame(int type) {
        super("Game Over");
        setAlwaysOnTop(true);
        
        if (type == WON) {
            bgPath = "img/winbg.png";
        } else {
            bgPath = "img/losebg.png";
        }
        // Background panel
        JPanel bg = new JPanel(new BorderLayout(20, 20)) {
            
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(bgPath)), 0, 0, null);
                } catch (IOException e) {
                    System.out.println("Could not load " + bgPath);
                }
            }
            
        };
        Dimension d = new Dimension(400, 300);
        bg.setSize(d);
        bg.setPreferredSize(d);
        
        BJButton back = new BJButton("Back To Main", 170, 30);
        BJButton exit = new BJButton("Exit", 70, 30);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.add(back);
        buttons.add(exit);
        
        bg.add(buttons, BorderLayout.SOUTH);
        bg.repaint();
        
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BattleSystem.INSTANCE.startupFrame.setVisible(true);
                BattleSystem.INSTANCE.startupFrame.dispose();
                EndGameFrame.this.dispose();
                
            }
        });
        
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        add(bg);
        
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // When closing this window, dispose old game frame and
        // return to startup frame
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                BattleSystem.INSTANCE.gameFrame.dispose();
                BattleSystem.INSTANCE.startupFrame.setVisible(true);
            }
            
        });
    }
    
    
}
