import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class BasicEnemy extends Enemy{
	public final static int NUM_PHASES = 5;
	public final static int STILL = 0, MOVE = 1, MOVE_2 = 2, KNOCKBACK = 3, DEATH = 4;
	private int phase, dir;
	private double vy, vx;
	private byte deathdelay;
	
	public BasicEnemy(){
		phase = STILL;
		dir = Util.randomIntInclusive(0, 3);
		vy = vx = 0;
		deathdelay = -1;
		dead = false;
	}
	
	public BasicEnemy(Coord c){
		y = c.getY()*Room.TILE_SIZE+Room.HALF_TILE;
		x = c.getX()*Room.TILE_SIZE+Room.HALF_TILE;
		phase = STILL;
		dir = Util.randomIntInclusive(0, 3);
		vy = vx = 0;
		deathdelay = -1;
	}
	
	public void update(){
		//randomly change phase
		if(phase == STILL){ //if still, then higher chance of changing phase/direction
			if(Util.randomDouble(0, 1) < .05){
				phase = Util.randomIntInclusive(0, 2);
				dir = Util.randomIntInclusive(Util.NORTH, Util.WEST);
			}
		}else if(phase == MOVE || phase == MOVE_2) //if moving already, then much lower chance of changing phase/direction
			if(Util.randomDouble(0, 1) < .006){
				phase = Util.randomIntInclusive(0, 2);
				dir = Util.randomIntInclusive(Util.NORTH, Util.WEST);
			}
		//execute phase procedures
		switch(phase){
		case STILL:
			vy = vx = 0;
			break;
		case MOVE:
		case MOVE_2:
			//set velocity of movement based on cardinal direction
			vy = Util.dirToY(dir);
			vx = Util.dirToX(dir);
		case KNOCKBACK:
			//dampen velocity if in knock back phase
			double v;
			if(phase == KNOCKBACK){
				vy *= .95;
				vx *= .95;
			}
			//execute procedures
			if(!Refs.map.isCollidingWithMap(this)){
				boolean collidedX = false, collidedY = false;
				//move vertically, then test
				y += vy;
				if(Refs.map.isCollidingWithMap(this) || Refs.map.isTouchingBounds(this))
					collidedY = true;
				y -= vy;
				//move horizontally, then test
				x += vx;
				if(Refs.map.isCollidingWithMap(this) || Refs.map.isTouchingBounds(this))
					collidedX = true;
				x -= vx;
				//if collided in motion and not already set to die, automatically change phase/direction
				if((collidedX || collidedY) && phase < KNOCKBACK){
					phase = Util.randomIntInclusive(0, 2);
					dir = Util.randomIntInclusive(Util.NORTH, Util.WEST);
				}
				//if slowed down too much, die
				if(phase == KNOCKBACK && Math.abs(vy) < .1 && Math.abs(vx) < .1)
					phase = STILL;
				//update positions cased on velocities
				if(!collidedX)
					x += vx;
				if(!collidedY)
					y += vy;
			}
			break;
		case DEATH:
			//if first time in this phase, begin count down
			if(deathdelay == -1)
				deathdelay = (byte)(1000/Refs.game.FRAME_DELAY);
			//else, count down
			else if(deathdelay > 0)
				deathdelay--;
			//else if death delay at 0, kill enemy
			else if(deathdelay == 0)
				kill();
			break;
		}
	}

	public void paint(Graphics g){
		//if(phase != KNOCKBACK || System.currentTimeMillis()%250 < 150) //TODO only use if basicenemies are to flash when hurt
			g.drawImage(AnimationCache.getEnemy(phase, dir), getX()-HALF_SIZE, getY()-HALF_SIZE, null);
	}
	
	public void impactHit(HarmfulParticle p){
		//if already knocked back or dead, ignore this
		if(phase == KNOCKBACK || phase == DEATH)
			return;
		//change cardinal direction for animation purposes
		dir = Util.getCardinalKnockbackDir(this, p);
		//change velocities for physics purposes
		vy = 4*(getY() - p.getParent().getY())/Util.distance(getX(), getY(), p.getParent().getX(),
																p.getParent().getY());
		vx = 4*(getX() - p.getParent().getX())/Util.distance(getX(), getY(), p.getParent().getX(),
																p.getParent().getY());
		//set phase to knock back
		phase = KNOCKBACK;
	}
	
	public boolean harmfulOnContact(){
		return (phase <= MOVE_2);
	}
}