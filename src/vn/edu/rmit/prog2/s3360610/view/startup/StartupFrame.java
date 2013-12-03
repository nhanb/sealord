/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.view.startup;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vn.edu.rmit.prog2.s3360610.controller.CreateEasy;
import vn.edu.rmit.prog2.s3360610.controller.CreateGameController;
import vn.edu.rmit.prog2.s3360610.controller.CreateHard;
import vn.edu.rmit.prog2.s3360610.main.Main;
import vn.edu.rmit.prog2.s3360610.model.BattleSystem;
import vn.edu.rmit.prog2.s3360610.view.shared.BJButton;

public class StartupFrame extends JFrame {

    public StartupFrame() {
        setTitle(Main.GAME_TITLE);
        setLayout(null);
        
        BattleSystem.INSTANCE.startupFrame = this;

        // Background panel
        JPanel bg = new JPanel() {
            
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File("img/startupbg.jpg")), 0, 0, null);
                } catch (IOException e) {
                    System.out.println("Could not load img/startupbg.jpg");
                }
            }
            
        };
        bg.setSize(400, 600);
        add(bg);


        BJButton easy = new BJButton("Easy", 70, 30);
        BJButton hard = new BJButton("Hard", 70, 30);
        BJButton exit = new BJButton("Exit", 70, 30);

        bg.add(easy);
        bg.add(hard);
        bg.add(exit);


        final JFrame thisFrame = this;
        easy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CreateGameController startEasy = new CreateEasy(thisFrame);
            }
        });
        
        hard.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CreateGameController startHard = new CreateHard(thisFrame);
            }
        });
        
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.dispose();
            }
        });

        setSize(400, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
//    @Override
//    public void paint(Graphics g) {
//
//        super.paint(g);
//
//        try {
//            g.drawImage(ImageIO.read(new File("img/startupbg.jpg")), 0, 0, null);
//
//        } catch (Exception e) {
//        }
//    }
}
