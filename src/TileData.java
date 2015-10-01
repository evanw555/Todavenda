
public class TileData{
	//TILE IDs                                 0      1            2      3      4
	//TILE TYPES					  		   GRASS, THICK_GRASS, SAND,  WATER, DIRT_PATCH
	private static boolean[][] collidable =  {{false, false,       false, true,  false},
	
	//OVERTILE IDs							   0        1     2     3           4
	//OVERTILE TYPES						   NOTHING, ROCK, WALL, SOUTH_DOOR, CHEST
											  {false,   true, true, false,      true}
	};
	
	public static boolean isCollidable(int layer, int type){
		return collidable[layer][type];
	}
	
	public static boolean isCollidable(int layer, int type, Entity e){
		//if entity e is instance of anything but a player, force collision with SOUTH_DOOR
		if(!(e instanceof Player) && layer == 1 && type == 3)
			return true;
		//otherwise, maintain normal conditions
		return collidable[layer][type];
	}
}