/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */

package vn.edu.rmit.prog2.s3360610.model;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundRunnable implements Runnable {

    private String name;

    public SoundRunnable(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            String fileName = "sound/" + name + ".wav";
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
//            AudioInputStream stream = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
}
