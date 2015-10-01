import java.awt.image.BufferedImage;

public class Animation{
	private BufferedImage[] frames;
	private int[] delays;
	private int totalTime;
	private boolean singleCycle;
	
	public Animation(){
		
	}
	
	public void setFrames(BufferedImage ... frames){
		this.frames = frames;
	}
	
	public void setDelays(int ... delays){
		this.delays = delays;
		this.totalTime = delays[delays.length-1];
	}
	
	public BufferedImage getCurrentFrame(){
		int time = (int)(System.currentTimeMillis()%totalTime);
		for(int i = 0; i < frames.length; i++)
			if(time < delays[i])
				return frames[i];
		return frames[0];
	}
	
	public BufferedImage getCurrentFrame(int offsetFrames){
		for(int i = 0; i < frames.length; i++)
			if(offsetFrames*Refs.game.FRAME_DELAY < delays[i])
				return frames[i];
		return frames[0];
	}
}