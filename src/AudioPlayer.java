
import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer {
    AudioPlayer(String str) throws Exception{
    	File audioFile = null;
    	if (str.equals("move"))
    		audioFile = new File("audio/move-self.wav");
    	else if(str.equals("capture"))
    		audioFile = new File("audio/capture.wav");
    	else if(str.equals("promote"))
    		audioFile = new File("audio/promote.wav");
    	
    	if(!str.equals("")) {
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioStream);
    		clip.start();
    	}
        
    }
}