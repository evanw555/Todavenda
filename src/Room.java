import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Room{
	public final static int HEIGHT = 10, WIDTH = 14, TILE_SIZE = 64, HALF_TILE = TILE_SIZE/2, NUM_IDS = 3, NUM_LAYERS = 2;
	private int[][][] tiles;
	private int[][][] ids;
	private ArrayList<Trigger> triggers;
	private String bgmDest;
	private EntRoom twin;
	
	public Room(int[][][] tiles){
		this.tiles = tiles;
		this.triggers = new ArrayList<Trigger>();
		this.bgmDest = "song02";
		//create random ids for each tile
		this.ids = new int[NUM_LAYERS][HEIGHT][WIDTH];
		for(int i = 0; i < NUM_LAYERS; i++)
			for(int y = 0; y < HEIGHT; y++)
				for(int x = 0; x < WIDTH; x++)
					ids[i][y][x] = Util.randomIntInclusive(0, NUM_IDS-1);
	}
	
	public void paint(Graphics g, int xDisp, int yDisp){
		for(int i = 0; i < NUM_LAYERS; i++)
			for(int y = 0; y < HEIGHT; y++)
				for(int x = 0; x <WIDTH; x++){
					int type = tiles[i][y][x];
//					g.drawString(""+type, x*TILE_SIZE, y*TILE_SIZE+12);
//					g.setColor(new Color(type*50, type*50, type*50));
//					g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
					g.drawImage(ImageHandler.getTileImage(i, type, ids[i][y][x]), x*TILE_SIZE+xDisp, y*TILE_SIZE+yDisp, null);
				}
	}
	
	public void setTwin(EntRoom twin){
		this.twin = twin;
	}
	
	public int getTileType(int layer, Coord c){
		return tiles[layer][c.getY()][c.getX()];
	}
	
	public boolean isCollidingWithMap(Entity e){
		int entY = e.getCoord().getY();
		int entX = e.getCoord().getX();
		//check if ent collides with all layers of map within a radius of 1 square (3x3)
		for(int i = 0; i < NUM_LAYERS; i++)
			for(int y = entY-1; y <= entY+1; y++)
				for(int x = entX-1; x <= entX+1; x++){
					//if noncollidable tile or out of bounds, continue
					try{
						if(!TileData.isCollidable(i, tiles[i][y][x], e))
							continue;
					}catch(Exception ex){ continue; }
					//check collision
					Rectangle env = new Rectangle(y*TILE_SIZE, x*TILE_SIZE, TILE_SIZE, TILE_SIZE);
					Rectangle ent = new Rectangle(e.getY()-(e.getHeight()/2), e.getX()-(e.getWidth()/2), e.getHeight(), e.getWidth());
					if(ent.intersects(env))
						return true;
				}
		return false;
	}
	
	public void addTrigger(Trigger t){
		triggers.add(t);
	}
	
	public void checkTriggers(Entity e){
		for(Trigger t : triggers){
			//checking for TELES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if(e instanceof Player && t instanceof Tele){
				Tele tele = (Tele)t;
				Rectangle env = new Rectangle(tele.getEntrance().getY()*TILE_SIZE, tele.getEntrance().getX()*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				//Rectangle ent = new Rectangle(e.getY()-Entity.HALF_SIZE, e.getX()-Entity.HALF_SIZE, Entity.STANDARD_SIZE, Entity.STANDARD_SIZE);
				Point ent = e.getPoint();
				if(env.contains(ent)){
					Refs.game.setTeleShift(e, tele);
					return;
				}
			//checking for WALLTELES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			}else if(e instanceof Player && t instanceof WallTele){
				WallTele wall = (WallTele)t;
				if(Util.getBoundaryBreachSide(e) == wall.getSide()){
					Refs.game.setRoomShift(Refs.ents.getCurrentRoom(), wall.getDest(), wall.getSide());
					e.sendToSide(wall.getOppositeSide());
					AudioHandler.playAudio("test"); //TODO AUDIO
				}
			//checking for DESTROYABLES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			}else if(e instanceof HarmfulParticle && t instanceof Destroyable){
				Destroyable des = (Destroyable)t;
				Rectangle env = new Rectangle(des.getLocation().getY()*TILE_SIZE, des.getLocation().getX()*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				Rectangle ent = new Rectangle(e.getY(), e.getX(), e.getHeight(), e.getHeight());
				if(env.contains(ent)){
					tiles[des.getLayer()][des.getLocation().getY()][des.getLocation().getX()] = des.getTypeAfter();
					return;
				}
			}
		}
	}
	
	public void respawnEnemies(){
		for(Trigger t : triggers)
			if(t instanceof EnemySpawn){
				twin.add(new BasicEnemy(((EnemySpawn)t).getLocation()));
			}
	}
	
	public void setBGMTrack(String dest){
		bgmDest = dest;
	}
	
	public void playBGMTrack(){
		AudioHandler.attemptSetBGM(bgmDest);
	}
}