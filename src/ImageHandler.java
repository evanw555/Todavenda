import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler{
	private static BufferedImage mainWalkingImg;
	private static BufferedImage[][] walkingImgs;
	
	private static BufferedImage mainTileImg;
	private static BufferedImage[][][] tileImgs;
	
	private static BufferedImage mainBasicEnemyImg;
	private static BufferedImage[][] basicenemyImgs;
	
	private static BufferedImage blackOverlay;
	private static BufferedImage overlaySwipe;
	private static BufferedImage spotlight;
	
	public static void loadImages(){
		loadWalkingImages();
		loadTileImages();
		loadBasicEnemyImgs();
		blackOverlay = loadImage("images/effects/blackOverlay_10%.PNG");
		overlaySwipe = loadImage("images/effects/overlayswipe640_896.PNG");
		spotlight = loadImage("images/effects/spotlight.PNG");
	}
	
	public static void loadWalkingImages(){
		int dims = Player.STANDARD_SIZE;
		mainWalkingImg = loadImage("images/player/walking.PNG");
		walkingImgs = new BufferedImage[mainWalkingImg.getHeight()/dims][mainWalkingImg.getWidth()/dims];
		for(int i = 0; i < mainWalkingImg.getHeight()/dims; i++){
			for(int j = 0; j < mainWalkingImg.getWidth()/dims; j++){
				walkingImgs[i][j] = mainWalkingImg.getSubimage(j*dims, i*dims, dims, dims);
			}
		}
	}
	
	public static void loadTileImages(){
		int dims = Room.TILE_SIZE;
		mainTileImg = loadImage("images/map/tiles.PNG");
		tileImgs = new BufferedImage[mainTileImg.getWidth()/dims/Room.NUM_IDS][mainTileImg.getHeight()/dims][Room.NUM_IDS];
		for(int i = 0; i < Room.NUM_LAYERS; i++)
			for(int j = 0; j < mainTileImg.getHeight()/dims; j++)
				for(int k = 0; k < Room.NUM_IDS; k++)
					tileImgs[i][j][k] = mainTileImg.getSubimage(i*dims*Room.NUM_IDS + k*dims, j*dims, dims, dims);
	}
	
	public static void loadBasicEnemyImgs(){
		int dims = Enemy.STANDARD_SIZE;
		mainBasicEnemyImg = loadImage("images/enemies/basicenemy.PNG");
		basicenemyImgs = new BufferedImage[mainBasicEnemyImg.getHeight()/dims][mainBasicEnemyImg.getWidth()/dims];
		for(int i = 0; i < mainBasicEnemyImg.getHeight()/dims; i++){
			for(int j = 0; j < mainBasicEnemyImg.getWidth()/dims; j++){
				basicenemyImgs[i][j] = mainBasicEnemyImg.getSubimage(j*dims, i*dims, dims, dims);
			}
		}
	}
	
	public static BufferedImage getWalkingImage(int direction, int frame){
		return walkingImgs[direction][frame];
	}
	
	public static BufferedImage getTileImage(int layer, int type){
		return tileImgs[layer][type][0];
	}
	
	public static BufferedImage getTileImage(int layer, int type, int id){
		return tileImgs[layer][type][id];
	}
	
	public static BufferedImage getBasicEnemyImage(int phase, int direction, int frame){
		return basicenemyImgs[phase*4 + direction][frame];
	}
	
	public static BufferedImage getBlackOverlayImage(){
		return blackOverlay;
	}
	
	public static BufferedImage getOverlaySwipeImage(){
		return overlaySwipe;
	}
	
	public static BufferedImage getSpotlightImage(){
		return spotlight;
	}
	
	/**
	 * 
	 * @param thickness number of times to draw overlay (0 <= thickness <= 10)
	 * @param g Graphics object
	 */
	public static void drawBlackOverlay(int thickness, Graphics g){
		for(int i = 0; i < thickness; i++)
			g.drawImage(blackOverlay, 0, 0, Refs.canvas.getWidth(), Refs.canvas.getHeight(), null);
	}
	
	public static BufferedImage loadImage(String location){
		try{
			 return ImageIO.read(new File(location));
		}catch(IOException e){ System.out.println("Failed: "+location); }
		return null;
	}
	
	public static BufferedImage loadImageNoCatch(String location) throws Exception{
		return ImageIO.read(new File(location));
	}
}