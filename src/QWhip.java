import java.awt.Color;
import java.awt.Graphics;


public class QWhip extends HarmfulParticle{
	private int lifetime;
	
	public QWhip(Player parent){
		this.parent = parent;
		this.lifetime = 500/Refs.game.FRAME_DELAY;
		this.y = parent.getY()+(Util.dirToY(parent.getDirection())*parent.getHeight());
		this.x = parent.getX()+(Util.dirToX(parent.getDirection())*parent.getWidth());
	}
	
	public void update(){
		y = parent.getY()+(Util.dirToY(parent.getDirection())*parent.getHeight());
		x = parent.getX()+(Util.dirToX(parent.getDirection())*parent.getWidth());
		//decrease life span, kill when done
		if(lifetime > 0)
			lifetime--;
		else
			kill();
		//check for collisions with destroyables and other triggers
	}

	public void paint(Graphics g){
		g.setColor(Color.GRAY);
		g.fillOval(getX()-getWidth()/2, getY()-getHeight()/2, getWidth(), getHeight());
	}

	public int getHeight(){
		return parent.getHeight();
	}

	public int getWidth(){
		return parent.getWidth();
	}
	
	public boolean affectsEnemies(){
		return true;
	}
	
	public boolean affectsPlayer(){
		return false;
	}
}