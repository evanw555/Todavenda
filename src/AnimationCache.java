import java.awt.image.BufferedImage;

public class AnimationCache{
	private static Animation[] walking;
	private static Animation[][] basicenemy;
	
	public static void loadAnimations(){
		//load walking TODO set in separate functions?
		walking = new Animation[4];
		for(int i = 0; i < walking.length; i++){
			walking[i] = new Animation();
			walking[i].setFrames(ImageHandler.getWalkingImage(i, 0),
							ImageHandler.getWalkingImage(i, 1),
							ImageHandler.getWalkingImage(i, 2),
							ImageHandler.getWalkingImage(i, 3));
			walking[i].setDelays(150, 300, 450, 600);
		}
		//load basic enemy animation
		loadBasicEnemyAnimation();
	}
	
	public static void loadBasicEnemyAnimation(){
		basicenemy = new Animation[BasicEnemy.NUM_PHASES][4];
		//[phase][direction]
		for(int i = 0; i < basicenemy.length; i++){
			for(int j = 0; j < basicenemy[0].length; j++){
				//different animation setup for each phase
				switch(i){
				case BasicEnemy.STILL:
					basicenemy[i][j] = new Animation();
					basicenemy[i][j].setFrames(ImageHandler.getBasicEnemyImage(i, j, 0),
							ImageHandler.getBasicEnemyImage(i, j, 1));
					basicenemy[i][j].setDelays(800, 1000);
					break;
				case BasicEnemy.MOVE:
				case BasicEnemy.MOVE_2:
					basicenemy[i][j] = new Animation();
					basicenemy[i][j].setFrames(ImageHandler.getBasicEnemyImage(i, j, 0),
							ImageHandler.getBasicEnemyImage(i, j, 1),
							ImageHandler.getBasicEnemyImage(i, j, 2),
							ImageHandler.getBasicEnemyImage(i, j, 3));
					basicenemy[i][j].setDelays(200, 400, 600, 800);
					break;
				case BasicEnemy.KNOCKBACK:
					basicenemy[i][j] = new Animation();
					basicenemy[i][j].setFrames(ImageHandler.getBasicEnemyImage(i, j, 0),
							ImageHandler.getBasicEnemyImage(i, j, 1),
							ImageHandler.getBasicEnemyImage(i, j, 2),
							ImageHandler.getBasicEnemyImage(i, j, 3));
					basicenemy[i][j].setDelays(100, 200, 300, 400);
					break;
				case BasicEnemy.DEATH:
					basicenemy[i][j] = new Animation();
					basicenemy[i][j].setFrames(ImageHandler.getBasicEnemyImage(i, j, 0),
							ImageHandler.getBasicEnemyImage(i, j, 1),
							ImageHandler.getBasicEnemyImage(i, j, 2),
							ImageHandler.getBasicEnemyImage(i, j, 3));
					basicenemy[i][j].setDelays(300, 600, 800, 1000);
					break;
				}
				
			}
		}
	}
	
	public static BufferedImage getPlayerWalking(int direction){
		return walking[direction].getCurrentFrame();
	}
	
	public static BufferedImage getEnemy(int phase, int direction){
		return basicenemy[phase][direction].getCurrentFrame();
	}
}
