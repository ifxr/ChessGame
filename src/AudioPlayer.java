
import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer {
    AudioPlayer(String str) throws Exception{
    	if(!str.equals("")) {
    	AudioInputStream audioStream;
    	File audioFile = null;
	    	if (str.equals("move")) {
	    		audioStream = 
	    		AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("move-self.wav"));
	    		Clip clip = AudioSystem.getClip();
	    		clip.open(audioStream);
	    		clip.start();
	    	}
	    	else if(str.equals("capture")){
	    		audioStream = 
	    		AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("capture.wav"));
	    		Clip clip = AudioSystem.getClip();
	    		clip.open(audioStream);
	    		clip.start();
	    	}
	    	else if(str.equals("promote")) {
	    		audioStream = 
	    		AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("promote.wav"));
	    		Clip clip = AudioSystem.getClip();
	    		clip.open(audioStream);
	    		clip.start();
	    	}
	    }
    }
}