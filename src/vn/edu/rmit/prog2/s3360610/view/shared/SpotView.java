/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.view.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vn.edu.rmit.prog2.s3360610.exceptions.InvalidSpotException;
import vn.edu.rmit.prog2.s3360610.model.Spot;

public class SpotView extends JPanel implements Observer {

    private int width;
    private int height;
    private Spot spot;

    JLabel hitGif = new JLabel(new ImageIcon("img/spot_tiles/fire.gif"));
        

    @Override
    public void update(Observable o, Object o1) {
        this.spot = (Spot) o;
        repaint();
    }

    public SpotView(int width, int height, Spot spot) {
        super();
        hitGif.setSize(width, height);

        this.width = width;
        this.height = height;
        this.spot = spot;
        this.spot.addObserver(this);

        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);

    }

    @Override
    public void paint(Graphics g) {

        // Set bilinear antialiasing for the drawings
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setColor(Color.RED);

        g2d.setBackground(Color.YELLOW);

        // When the spot view is first created and update() hasn't been called
        if (spot == null) {
            drawSea(g2d);
            return;
        }

        // Decide how to draw this tile

        switch (spot.getStatus()) {
            case Spot.STATUS_SEA:
                drawSea(g2d);
                break;
            case Spot.STATUS_MISS:
                drawMiss(g2d);
                break;
            case Spot.STATUS_HIT:
                drawHit(g2d);
                break;
            case Spot.STATUS_SUNK:
                drawSunk(g2d);
                break;
            case Spot.STATUS_OWN:
                drawOwn(g2d);
        }

        if (spot.isCrosshairHover()) {
            try {
                g2d.drawImage(ImageIO.read(new File("img/spot_tiles/crosshair.png")), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (spot.isPlaceShipHover()) {
            
            g2d.setColor(new Color(255, 255, 0, 200));
            g2d.fillRect(0, 0, width, height);
        }

        paintChildren(g);
    }

    private void drawOwn(Graphics2D g2) {
        drawSea(g2);

        if (spot.getPositionInShip() != Spot.NO_SHIP) {
            drawShipOnly(g2);
        }

    }

    private void drawSea(Graphics2D g2) {
        try {
            Image tile = ImageIO.read(new File("img/spot_tiles/sea.jpg"));
            g2.drawImage(tile, 0, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawHit(Graphics2D g2) {
        drawSea(g2);
        drawHitOnly(g2);
    }

    private void drawHitOnly(Graphics2D g2) {
        
        add(hitGif);
//        try {
//            Image tile = ImageIO.read(new File("img/spot_tiles/fire.png"));
//            g2.drawImage(tile, 0, 0, this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void drawSunk(Graphics2D g2) {
        drawSea(g2);
        drawShipOnly(g2);
        drawHitOnly(g2);
    }

    private void drawShipOnly(Graphics2D g2) {
        try {

            // Name of the appropriate img file for this spot
            String tileName;

            // Get prefix for position in ship
            switch (spot.getPositionInShip()) {
                case Spot.FIRST:
                    tileName = "first_";
                    break;
                case Spot.MIDDLE:
                    tileName = "mid_";
                    break;
                case Spot.LAST:
                    tileName = "last_";
                    break;
                default:
                    throw new InvalidSpotException("There is no spot position in ship with code = " + spot.getPositionInShip());
            }

            // Get suffix for alignment
            switch (spot.getAlignment()) {
                case Spot.HORIZONTAL:
                    tileName += "h.png";
                    break;
                case Spot.VERTICAL:
                default:
                    tileName += "v.png";
                    break;
            }

            Image tile = ImageIO.read(new File("img/ship_tiles/" + tileName));
            g2.drawImage(tile, 0, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMiss(Graphics2D g2) {
        drawSea(g2);
        try {
            Image tile = ImageIO.read(new File("img/spot_tiles/miss.png"));
            g2.drawImage(tile, 5, 5, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Spot getSpot() {
        return spot;
    }
}
