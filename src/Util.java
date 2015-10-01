
public class Util{
	public final static int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
	
	public static void sleep(long millis){
		try{
			Thread.sleep(millis);
		}catch(Exception e){}
	}
	
	public static int randomIntInclusive(int lower, int upper){
		assert upper >= lower;
		return (int)(Math.random()*(1+upper-lower))+lower;
	}
	
	public static double randomDouble(double lower, double upper){
		assert upper >= lower;
		return (Math.random()*(upper-lower))+lower;
	}
	
	public static double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	
	public static int getBoundaryBreachSide(Entity e){
		if(e.getY() < 0) //breaches NORTH boundary
			return NORTH;
		else if(e.getX() > Refs.canvas.getWidth()) //breaches EAST boundary
			return EAST;
		else if(e.getY() > Refs.canvas.getHeight()) //breaches SOUTH boundary
			return SOUTH;
		else if(e.getX() < 0) //breaches WEST boundary
			return WEST;
		else
			return -1;
	}
	
	public static int trimInt(int a, int min, int max){
		if(a < min)
			return min;
		if(a > max)
			return max;
		return a;
	}
	
	public static int dirToY(int dir){
		switch(dir){
		case NORTH:
			return -1;
		case SOUTH:
			return 1;
		case EAST:
		case WEST:
			return 0;
		}
		return 0;
	}
	
	public static int dirToX(int dir){
		switch(dir){
		case NORTH:
		case SOUTH:
			return 0;
		case EAST:
			return 1;
		case WEST:
			return -1;
		}
		return 0;
	}
	
	public static int getCardinalKnockbackDir(Enemy e, HarmfulParticle p){
		int dx = p.getX()-e.getX();
		int dy = p.getY()-e.getY();
		//if difference in x is greater than difference in y
		if(Math.abs(dx) > Math.abs(dy)){
			if(dx > 0)
				return WEST;
			else
				return EAST;
		//else
		}else{
			if(dy > 0)
				return NORTH;
			else
				return SOUTH;
		}
	}
}