
public class Refs{
	public static Game game;
	public static Canvas canvas;
	public static Map map;
	public static EntityHandler ents;
	
	public static void setGame(Game g){
		game = g;
	}
	
	public static void setCanvas(Canvas c){
		canvas = c;
	}
	
	public static void setMap(Map m){
		map = m;
	}
	
	public static void setEnts(EntityHandler e){
		ents = e;
	}
}