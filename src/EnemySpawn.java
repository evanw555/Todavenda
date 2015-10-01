
public class EnemySpawn extends Trigger{
	private Coord location;
	private int type;
	
	public EnemySpawn(Coord location, int type){
		this.location = location;
		this.type = type;
	}
	
	public Coord getLocation(){
		return location;
	}
	
	public int getType(){
		return type;
	}
}