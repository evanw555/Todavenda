import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Entity implements Comparable<Entity>{
	public final static int STANDARD_SIZE = (int)(.75*Room.TILE_SIZE), HALF_SIZE = STANDARD_SIZE/2;
	protected double y, x;
	protected boolean dead;

	public abstract void update();
	
	public abstract void paint(Graphics g);
	
	public int getY(){
		return (int)y;
	}
	
	public int getX(){
		return (int)x;
	}
	
	/**
	 * Used to comapare two entities based on Y coordinate for drawing
	 * entities onto the canvas in proper order.
	 * 
	 * @param other   entity to compare with this instance
	 * @return int specifying order in list relative to parameter entity
	 */
	public int compareTo(Entity other){
		if(getY() > other.getY())
			return 1;
		else if(getY() < other.getY())
			return -1;
		return 0;
	}
	
	public abstract int getHeight();
	
	public abstract int getWidth();
	
	public int getDirection(){
		return Util.NORTH;
	}
	
	/*
	 * DO NOT USE THIS. FOR COLLISIONS ONLY. MESSED UP AND BACKWARDS
	 */
	public Point getPoint(){
		return new Point(getY(), getX());
	}
	
	/*
	 * DO NOT USE THIS. FOR COLLISIONS ONLY. MESSED UP AND BACKWARDS
	 */
	public Rectangle getRectangle(){
		return new Rectangle(getY()-getHeight()/2, getX()-getWidth()/2, getHeight(), getWidth());
	}
	
	public Coord getCoord(){
		return new Coord(getY()/Room.TILE_SIZE, getX()/Room.TILE_SIZE);
	}
	
	public void sendTo(Coord c){
		y = c.getY()*Room.TILE_SIZE+Room.HALF_TILE;
		x = c.getX()*Room.TILE_SIZE+Room.HALF_TILE;
	}
	
	public void sendToSide(int side){
		switch(side){
		case Util.NORTH:
			y = Room.HALF_TILE;
			break;
		case Util.EAST:
			x = Refs.canvas.getWidth()-Room.HALF_TILE;
			break;
		case Util.SOUTH:
			y = Refs.canvas.getHeight()-Room.HALF_TILE;
			break;
		case Util.WEST:
			x = Room.HALF_TILE;
			break;
		}
	}
	
	public void kill(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
}