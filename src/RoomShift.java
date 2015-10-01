
public class RoomShift{
	private int roomA, roomB, direction, initialCountDown, countDown;
	private double motionRatio; //goes from 0 to 1
	
	public RoomShift(int roomA, int roomB, int direction, int countDown){
		this.roomA = roomA;
		this.roomB = roomB;
		this.direction = direction;
		this.countDown = this.initialCountDown = countDown;
		this.motionRatio = ((double)(initialCountDown-countDown))/((double)initialCountDown);
	}
	
	public void update(){
		countDown--;
		this.motionRatio = ((double)(initialCountDown-countDown))/((double)initialCountDown);
	}
	
	public int getRoom(int room){
		if(room == 0)
			return roomA;
		else
			return roomB;
	}
	
	public int getXDisplacement(int room){
		switch(direction){
		case Util.NORTH:
		case Util.SOUTH:
			return 0;
		case Util.EAST:
			if(room == 0)
				return (int)((-1.)*((double)Refs.canvas.getWidth())*motionRatio);
			else
				return (int)(((double)Refs.canvas.getWidth())*(1.-motionRatio));
		case Util.WEST:
			if(room == 0)
				return (int)(((double)Refs.canvas.getWidth())*motionRatio);
			else
				return (int)(((double)Refs.canvas.getWidth())*(motionRatio-1.));
		}
		return 0;
	}
	
	public int getYDisplacement(int room){
		switch(direction){
		case Util.NORTH:
			if(room == 0)
				return (int)(((double)Refs.canvas.getHeight())*motionRatio);
			else
				return (int)(((double)Refs.canvas.getHeight())*(motionRatio-1.));
		case Util.SOUTH:
			if(room == 0)
				return (int)((-1.)*((double)Refs.canvas.getHeight())*motionRatio);
			else
				return (int)(((double)Refs.canvas.getHeight())*(1.-motionRatio));
		case Util.EAST:
		case Util.WEST:
			return 0;
		}
		return 0;
	}
	
	public boolean isDone(){
		return countDown <= 0;
	}
}