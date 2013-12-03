/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

import vn.edu.rmit.prog2.s3360610.main.Main;

public class Sound {

    public static void play(String name) {

        if (!Main.ENABLE_SOUND) {
            return;
        }
        
        Thread soundThread = new Thread(new SoundRunnable(name));

        soundThread.start();

        try {
            soundThread.join();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
