import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends Entity{
	private int direction;
	private boolean moving;

	public Player(Coord spawn){
		y = spawn.getY()*Room.TILE_SIZE+Room.HALF_TILE;
		x = spawn.getX()*Room.TILE_SIZE+Room.HALF_TILE;
		direction = Util.SOUTH;
		moving = false;
		dead = false;
	}
	
	public void update(){
		//check for user-induced motion
		moving = false;
		if(!Refs.map.isCollidingWithMap(this)){
			//move up, then test
			if(KeyHandler.isPressed(KeyEvent.VK_UP)){
				y -= 2.2;
				direction = Util.NORTH;
				moving = true;
			}
			if(Refs.map.isCollidingWithMap(this))
				y += 2.2;
			//move down, then test
			if(KeyHandler.isPressed(KeyEvent.VK_DOWN)){
				y += 2.2;
				direction = Util.SOUTH;
				moving = true;
			}
			if(Refs.map.isCollidingWithMap(this))
				y -= 2.2;
			//move left, then test
			if(KeyHandler.isPressed(KeyEvent.VK_LEFT)){
				x -= 2.2;
				direction = Util.WEST;
				moving = true;
			}
			if(Refs.map.isCollidingWithMap(this))
				x += 2.2;
			//move right, then test
			if(KeyHandler.isPressed(KeyEvent.VK_RIGHT)){
				x += 2.2;
				direction = Util.EAST;
				moving = true;
			}
			if(Refs.map.isCollidingWithMap(this))
				x -= 2.2;
		}
		//check any triggers
		Refs.map.checkTriggers(this);
		//TODO DEBUG TEMP
		if(KeyHandler.isPressed(KeyEvent.VK_3))
			if(Refs.ents.addOneOfClass(new QWhip(this), Refs.ents.getCurrentRoom()))
				AudioHandler.playAudio("whip");
				
	}
	
	public void paint(Graphics g){
		g.setColor(Color.GREEN);
		//g.drawOval(getX()-HALF_SIZE, getY()-HALF_SIZE, STANDARD_SIZE, STANDARD_SIZE); //TODO DEBUG
		if(moving)
			g.drawImage(AnimationCache.getPlayerWalking(direction), getX()-HALF_SIZE, getY()-HALF_SIZE, null);
		else
			g.drawImage(ImageHandler.getWalkingImage(direction, 0), getX()-HALF_SIZE, getY()-HALF_SIZE, null);
		//draw spotlight //TODO TEMP
//		g.drawImage(ImageHandler.getSpotlightImage(),
//				getX()-ImageHandler.getSpotlightImage().getWidth()/2,
//				getY()-ImageHandler.getSpotlightImage().getHeight()/2, null);
	}
	
	public int getHeight(){
		return Room.TILE_SIZE/2;
	}
	
	public int getWidth(){
		return Room.TILE_SIZE/2;
	}
	
	public int getDirection(){
		return direction;
	}
}