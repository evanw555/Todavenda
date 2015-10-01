import java.io.*;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import sun.audio.*;

public class AudioHandler{
	private static boolean DISABLED = false;
	private static Clip bgm, hostileOverlay;
	private static String bgmDest;
	
	public static void playAudio(String dest){
		if(DISABLED)
			return;
		//play audio
		try{
			File file = new File("audio/"+dest+".wav");
			Clip clip = AudioSystem.getClip();
	        // getAudioInputStream() also accepts a File or InputStream
	        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
	        clip.open(ais);
	        clip.loop(0);
	    }catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void attemptSetBGM(String dest){
		//if trying to set song to current song, return to prevent redundancy
		if(dest.equals(bgmDest))
			return;
		//else, change bgm track
		else
			setBGM(dest);
	}
	
	public static void setBGM(String dest){
		if(DISABLED)
			return;
		//save bgm destination
		bgmDest = dest;
		//stop current song
		if(bgm != null)
			bgm.stop();
		//loop new song
		try{
			File file = new File("audio/"+dest+".wav");
			bgm = AudioSystem.getClip();
	        // getAudioInputStream() also accepts a File or InputStream
	        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
	        bgm.open(ais);
	        bgm.loop(Clip.LOOP_CONTINUOUSLY);
	    }catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setBGMHostility(boolean hostile){
		if(DISABLED)
			return;
		//if there is not bgm track playing, return
		if(bgm == null)
			return;
		//if hostile, begin playing overlay track
		if(hostile){
			try{
				File file = new File("audio/"+bgmDest+"_hostile.wav");
				hostileOverlay = AudioSystem.getClip();
		        // getAudioInputStream() also accepts a File or InputStream
		        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		        hostileOverlay.open(ais);
		        hostileOverlay.loop(Clip.LOOP_CONTINUOUSLY);
		        //make sure overlay track is at same microsecond position as bgm track
		        hostileOverlay.setMicrosecondPosition(bgm.getMicrosecondPosition()%bgm.getMicrosecondLength());
		    }catch(Exception e){
				e.printStackTrace();
			}
		//else, stop that track, if track exists
		}else{
			if(hostileOverlay == null)
				return;
			hostileOverlay.stop();
			hostileOverlay = null;
		}
	}
}