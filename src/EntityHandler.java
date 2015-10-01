import java.awt.Graphics;


public class EntityHandler{
	private EntRoom[] entRooms;
	private int currentRoom, nextRoom;
	
	public EntityHandler(){
		Refs.setEnts(this);
		//set up entRooms
		entRooms = new EntRoom[Refs.map.getNumRooms()];
		for(int i = 0; i < entRooms.length; i++)
			entRooms[i] = new EntRoom(this, Refs.map.getRoom(i));
		//choose current room, nullify next room
		currentRoom = Refs.map.getSpawnRoom();
		nextRoom = -1;
		//create and add player
		add(Refs.map.createPlayer(), currentRoom);
	}
	
	public void update(){
		entRooms[currentRoom].update();
		//if current room is attempted to be switch, then switch it
		if(nextRoom != -1){
			currentRoom = nextRoom;
			nextRoom = -1;
		}
	}
	
	public void paint(Graphics g){
		entRooms[currentRoom].paint(g);
	}
	
	public int getCurrentRoom(){
		return currentRoom;
	}
	
	public void changeRoom(int room){
		nextRoom = room;
		//transfer player
		entRooms[currentRoom].transferPlayer(nextRoom);
		//clean up entities, respawn enemies, and play bgm track in new room
		entRooms[nextRoom].restartRoom();
		entRooms[nextRoom].playBGMTrack();
	}
	
	public void transferPlayer(Entity player, int transferDest){
		entRooms[transferDest].add(player);
	}
	
	public void add(Entity e, int room){
		entRooms[room].add(e);
	}
	
	public boolean addOneOfClass(Entity e, int room){
		return entRooms[room].addOneOfClass(e);
	}
	
	public boolean containsClass(Entity e, int room){
		return entRooms[room].containsClass(e);
	}
	
	public boolean isRoomOnQueue(){
		return nextRoom != -1;
	}
	
	public void playCurrentBGMTrack(){
		entRooms[currentRoom].playBGMTrack();
	}
}