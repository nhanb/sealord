/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.main;

import vn.edu.rmit.prog2.s3360610.model.Sound;
import vn.edu.rmit.prog2.s3360610.view.startup.StartupFrame;

public class Main {

    public static final String GAME_TITLE = "Sea Hunt";
    public static final int MAP_X = 10;
    public static final int MAP_Y = 10;
    public static final boolean ENABLE_SOUND = false;

    public static void main(String[] args) {
        
        
        StartupFrame stf = new StartupFrame();
        
        if (ENABLE_SOUND) {
            Sound.play("startup");
        }

    }
}
