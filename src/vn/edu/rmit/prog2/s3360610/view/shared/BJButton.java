/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.view.shared;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

public class BJButton extends JButton {

    public BJButton(String text, int width, int height) {
        super(text);
        Dimension d = new Dimension(width, height);
        setPreferredSize(d);
        setSize(d);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
