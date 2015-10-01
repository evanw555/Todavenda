import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;


public class EntRoom{
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<HarmfulParticle> hfParticles;
	private ArrayList<Entity> purgatory;
	private ArrayList<Entity> drawList;
	
	private EntityHandler parent;
	private Room twin;
	private int transferDest;
	
	public EntRoom(EntityHandler parent, Room twin){
		enemies = new ArrayList<Enemy>();
		hfParticles = new ArrayList<HarmfulParticle>();
		purgatory = new ArrayList<Entity>();
		drawList = new ArrayList<Entity>();
		this.parent = parent;
		this.twin = twin;
		this.twin.setTwin(this);
		transferDest = -1;
		restartRoom();
	}
	
	public void update(){
		//add ents from purgatory into appropriate sub-lists and clear
		for(Entity e : purgatory){
			if(e instanceof Player)
				player = (Player)e;
			else if(e instanceof Enemy)
				enemies.add((Enemy)e);
			else if(e instanceof HarmfulParticle)
				hfParticles.add((HarmfulParticle)e);
		}
		purgatory.clear();
		//update ents
		if(player != null)
			player.update();
		for(Enemy e : enemies)
			e.update();
		for(HarmfulParticle p : hfParticles)
			p.update();
		//every so often, sort enemy list based on Y coordinates of entities
		if(System.currentTimeMillis()%30 == 0)
			Collections.sort(enemies); 
		//check collisions every few frames
		if(System.currentTimeMillis()%5 == 0)
			checkCollisions();
		//removed entities that are marked as dead
		cleanUp();
		//if player marked for transfer, then transfer to specified room (-1 = none, other# = destination)
		if(transferDest > -1 && player != null){
			parent.transferPlayer(player, transferDest);
			player = null;
			transferDest = -1;
		}
	}
	
	public void paint(Graphics g){
		//clear draw list
		drawList.clear();
		//put all appropriate entities into draw list
		if(player != null)
			drawList.add(player);
		for(Enemy e : enemies)
			if(!e.isDead())
				drawList.add(e);
		for(HarmfulParticle p : hfParticles)
			if(!p.isDead())
				drawList.add(p);
		//sort draw list
		Collections.sort(drawList);
		//draw every entry in draw list
		for(Entity e : drawList)
			e.paint(g);
	}
	
	public void cleanUp(){
		//remove dead enemies
		for(int i = 0; i < enemies.size(); i++)
			if(enemies.get(i).isDead()){
				enemies.remove(i);
				i--;
			}
		//remove dead harmful particles
		for(int i = 0; i < hfParticles.size(); i++)
			if(hfParticles.get(i).isDead()){
				hfParticles.remove(i);
				i--;
			}
	}
	
	public void add(Entity e){
		purgatory.add(e);
	}
	
	public boolean addOneOfClass(Entity e){
		if(!containsClass(e)){
			purgatory.add(e);
			return true;
		}
		return false;
	}
	
	public boolean containsClass(Entity ent){
		Class c = ent.getClass();
		for(Enemy e : enemies)
			if(e.getClass() == c)
				return true;
		for(HarmfulParticle p : hfParticles)
			if(p.getClass() == c)
				return true;
		return false;
	}
	
	public void transferPlayer(int transferDest){
		this.transferDest = transferDest;
	}
	
	public void restartRoom(){
		//kill all enemies
		for(Enemy e : enemies)
			e.kill();
		//kill all harmful particles
		for(HarmfulParticle p : hfParticles)
			p.kill();
		//respawn enemies from respawn sites
		twin.respawnEnemies();
	}
	
	public void playBGMTrack(){
		twin.playBGMTrack();
	}
	
	public void checkCollisions(){
		//check collisions between enemies and harmful particles
		checkEnemyToParticleCollisions();
	}
	
	public void checkEnemyToParticleCollisions(){
		for(Enemy e : enemies)
			for(HarmfulParticle p : hfParticles)
				if(p.affectsEnemies()){
					//QWhip ======================================================
					if(p instanceof QWhip){
						if(e.getRectangle().intersects(p.getRectangle())){
							e.impactHit(p);
						}
					//TODO OTHER =================================================
					}else{
						
					}
				}
	}
}