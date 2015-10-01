
public class Tele extends Trigger{
	private Coord entrance, dest;
	
	public Tele(Coord entrance, Coord dest){
		this.entrance = entrance;
		this.dest = dest;
	}
	
	public Coord getEntrance(){
		return entrance;
	}
	
	public Coord getDest(){
		return dest;
	}
}