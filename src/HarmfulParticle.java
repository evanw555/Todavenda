import java.awt.Graphics;


public abstract class HarmfulParticle extends Entity{
	protected Entity parent;
	
	public abstract boolean affectsEnemies();
	
	public abstract boolean affectsPlayer();
	
	public Entity getParent(){
		return parent;
	}
}