import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener{
	private int width, height;
	
	public Canvas(int height, int width){
		Refs.setCanvas(this);
		this.width = width;
		this.height = height;
		this.addKeyListener(this);
	}
	
	public boolean isFocusable(){
		return true;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}
	
	public Dimension getMinimumSize(){
		return new Dimension(width, height);
	}
	
	public Dimension getMaximumSize(){
		return new Dimension(width, height);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Refs.map.paint(g);
		if(Refs.game.getPhase() != Game.WALL_SHIFT)
			Refs.ents.paint(g);
		if(Refs.game.getPhase() == Game.TELE_SHIFT)
			g.drawImage(ImageHandler.getOverlaySwipeImage(), 0, Refs.game.getTeleShift().getOverlayY(), null);
		//TODO DEBUG printing FPS
		g.setColor(Color.WHITE);
		g.setFont(new Font("COURIER", Font.BOLD, 16));
		g.drawString(Refs.game.getLagMillis()+" millis LAG", 16, 16);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public void keyPressed(KeyEvent e){
		KeyHandler.setKeyPressed(e.getKeyCode(), true);
		//TODO DEBUG
		if(e.getKeyCode() == KeyEvent.VK_1)
			AudioHandler.setBGMHostility(false);
		if(e.getKeyCode() == KeyEvent.VK_2)
			AudioHandler.setBGMHostility(true);
	}

	public void keyReleased(KeyEvent e){
		KeyHandler.setKeyPressed(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}