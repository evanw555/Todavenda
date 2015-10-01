
public class TeleShift{
	private Entity entity;
	private Coord dest;
	private int room, countDown, halfCount, thirdCount;
	
	public TeleShift(Entity entity, Coord dest, int room){
		this.entity = entity;
		this.dest = dest;
		this.room = room;
		this.countDown = 60;
		this.halfCount = countDown/2;
		this.thirdCount = countDown/3;
	}
	
	public void update(){
		countDown--;
	}
	
	public Entity getSubjectEntity(){
		return entity;
	}
	
	public Coord getDest(){
		return dest;
	}
	
	public int getDestRoom(){
		return room;
	}
	
	public int getOverlayThickness(){
		return halfCount-Math.abs(halfCount-countDown);
	}
	
	public int getOverlayY(){
		return (int)(((double)(halfCount-2*countDown)/(double)halfCount)*Room.HEIGHT*Room.TILE_SIZE);
	}
	
	public boolean isDone(){
		return countDown <= 0;
	}
	
	public boolean isAtMiddle(){
		//return countDown == halfCount; //works for the multiple overlay
		return countDown == thirdCount;
	}
}