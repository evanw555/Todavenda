
public class Coord{
	private int room, y, x;
	
	public Coord(int y, int x){
		this.room = 0;
		this.y = y;
		this.x = x;
	}
	
	public Coord(int room, int y, int x){
		this.room = room;
		this.y = y;
		this.x = x;
	}
	
	public int getRoom(){
		return room;
	}
	
	public int getY(){
		return y;
	}
	
	public int getX(){
		return x;
	}
	
	public String toString(){
		return room+": "+y+", "+x;
	}
}