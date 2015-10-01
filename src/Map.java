import java.awt.Graphics;


public class Map{
	private Room[] rooms;
	private String name;
	private int spawnRoom;
	private Coord spawn;
	
	public Map(Room[] rooms, String name, int spawnRoom, Coord spawn){
		Refs.setMap(this);
		this.rooms = rooms;
		this.name = name;
		this.spawnRoom = spawnRoom;
		this.spawn = spawn;
	}
	
	public void paint(Graphics g){
		//if shifting, account for displacement
		if(Refs.game.getPhase() == Game.WALL_SHIFT){
			rooms[Refs.game.getRoomShift().getRoom(0)].paint(g, Refs.game.getRoomShift().getXDisplacement(0),
																Refs.game.getRoomShift().getYDisplacement(0));
			rooms[Refs.game.getRoomShift().getRoom(1)].paint(g, Refs.game.getRoomShift().getXDisplacement(1),
																Refs.game.getRoomShift().getYDisplacement(1));
		}
		//if else, do normal painting
		else
			rooms[Refs.ents.getCurrentRoom()].paint(g, 0, 0);
	}
	
	public String getName(){
		return name;
	}
	
	public Room getRoom(int index){
		return rooms[index];
	}
	
	public int getTileType(int r, int layer, Coord c){
		return rooms[r].getTileType(layer, c);
	}
	
	public int getNumRooms(){
		return rooms.length;
	}
	
	public Player createPlayer(){
		return new Player(spawn);
	}
	
	public int getSpawnRoom(){
		return spawnRoom;
	}
	
	public boolean isCollidingWithMap(Entity e){
		return rooms[Refs.ents.getCurrentRoom()].isCollidingWithMap(e);
	}
	
	public void checkTriggers(Entity e){
		rooms[Refs.ents.getCurrentRoom()].checkTriggers(e);
	}
	
	public boolean isTouchingBounds(Entity e){
		//top side
		if(e.getY()-e.getHeight()/2 < 0)
			return true;
		//bottom side
		if(e.getY()+e.getHeight()/2 > Refs.canvas.getHeight())
			return true;
		//left side
		if(e.getX()-e.getWidth()/2 < 0)
			return true;
		//right side
		if(e.getX()+e.getWidth()/2 > Refs.canvas.getWidth())
			return true;
		//if touching no bounds, return false
		return false;
	}
}