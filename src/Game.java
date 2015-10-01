import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Game{
	public final static int FRAME_DELAY = 1000/54;
	public final static int PLAY = 0, WALL_SHIFT = 1, TELE_SHIFT = 2;
	private Map map;
	private EntityHandler ents;
	private JFrame frame;
	private Canvas canvas;
	private RoomShift roomShift;
	private TeleShift teleShift;
	private long startMillis, endMillis, diffMillis;
	private int phase;
	
	public Game(Map map){
		this.map = map;
		ents = new EntityHandler();
		ImageHandler.loadImages();
		AnimationCache.loadAnimations();
		
		frame = new JFrame(map.getName());
		Container cont;
		cont = frame.getContentPane();
		
		canvas = new Canvas(Room.HEIGHT*Room.TILE_SIZE, Room.WIDTH*Room.TILE_SIZE);
		cont.add(canvas);
		
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.toFront();
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public void run(){
		Refs.setGame(this);
		frame.setVisible(true);
		phase = PLAY;
		ents.playCurrentBGMTrack();
		while(true){
			startMillis = System.currentTimeMillis();
			//do updates/paints based on game phase
			switch(phase){
			case PLAY:
				ents.update();
				canvas.repaint();
				break;
			case WALL_SHIFT:
				roomShift.update();
				if(roomShift.isDone()){
					ents.changeRoom(roomShift.getRoom(1));
					roomShift = null;
					phase = PLAY;
				}
				if(!ents.isRoomOnQueue()) //don't paint if ents about to change rooms, otherwise it flashes previous room
					canvas.repaint();
				break;
			case TELE_SHIFT:
				teleShift.update();
				if(teleShift.isAtMiddle()){
					ents.changeRoom(teleShift.getDestRoom());
					teleShift.getSubjectEntity().sendTo(teleShift.getDest());
					ents.update();
				}else if(teleShift.isDone()){
					teleShift = null;
					phase = PLAY;
				}
				canvas.repaint();
				break;
			}
			endMillis = System.currentTimeMillis();
			diffMillis = endMillis - startMillis;
			//System.out.println(diffFrames+" "+phase);
			Util.sleep(FRAME_DELAY-((int)diffMillis));
			//Util.sleep(FRAME_DELAY);
		}
	}
	
	public void setRoomShift(int a, int b, int side){
		roomShift = new RoomShift(a, b, side, 40);
		phase = WALL_SHIFT;
	}
	
	public void setTeleShift(Entity e, Tele t){
		teleShift = new TeleShift(e, t.getDest(), t.getDest().getRoom());
		phase = TELE_SHIFT;
	}
	
	public int getPhase(){
		return phase;
	}
	
	public RoomShift getRoomShift(){
		return roomShift;
	}
	
	public TeleShift getTeleShift(){
		return teleShift;
	}
	
	public long getLagMillis(){
		return diffMillis;
	}
}