
public class Destroyable extends Trigger{
	private Coord location;
	private boolean refreshable;
	private int layer;
	private int type1, type2;
	
	public Destroyable(Coord location, boolean refreshable, int layer, int type1, int type2){
		this.location = location;
		this.refreshable = refreshable;
		this.layer = layer;
		this.type1 = type1;
		this.type2 = type2;
	}
	
	public Coord getLocation(){
		return location;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public void setTypeBefore(int type){
		this.type1 = type;
	}
	
	public int getTypeBefore(){
		return type1;
	}
	
	public int getTypeAfter(){
		return type2;
	}
}