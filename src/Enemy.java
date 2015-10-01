import java.awt.Graphics;

public abstract class Enemy extends Entity{

	public int getHeight(){
		return STANDARD_SIZE;
	}
	
	public int getWidth(){
		return STANDARD_SIZE;
	}
	
	public abstract void impactHit(HarmfulParticle p);
	
	public abstract boolean harmfulOnContact();	
}