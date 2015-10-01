
public class WallTele extends Trigger{
	private int side, dest;
	
	public WallTele(int side, int dest){
		this.side = side;
		this.dest = dest;
	}
	
	public int getSide(){
		return side;
	}
	
	public int getDest(){
		return dest;
	}
	
	public int getOppositeSide(){
		if(side <= 1) //if north or east
			return side+2;
		else //if south or west
			return side-2;
	}
}