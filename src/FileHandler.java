import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHandler{

	public FileHandler(){
		
	}
	
	public static File loopingFileChooserSequence(){
		try{
			boolean repeat = true;
			String[] options = {"Yes", "Choose Another File", "Quit"};
			JFileChooser fc = new JFileChooser("maps");
			while(repeat){
				fc.showOpenDialog(null);
				int choice = JOptionPane.showOptionDialog(null,
						fc.getSelectedFile().getName()+"\n"+
						"is this the correct file?",
						"Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null,
						options, options[1]);
				switch(choice){
				case 0:
					repeat = false;
					break;
				case 1:
					break;
				case 2: case -1:
					System.exit(0);
				}
			}
			return fc.getSelectedFile();
		}catch(Exception e){}
		return null;
	}
	
	public static Map createMap(File file){
		FileInput in = new FileInput(file.getPath());
		//get adventure name
		String name = in.readLine();
		//SKIP COMMENT LINE
		in.readLine();
		//get spawn point (room, y, x)
		int spawnRoom = in.readInt();
		Coord spawnCoord = new Coord(in.readInt(), in.readInt());
//		//TODO DEBUG, cumulative check
//		JOptionPane.showMessageDialog(null, name+"\n"+
//											"Room "+spawnRoom+"\n"+
//											"SpawnCoords "+spawnCoord, "Info", JOptionPane.PLAIN_MESSAGE);
		//create empty list of rooms
		ArrayList<Room> rooms = new ArrayList<Room>();
		//start checking rooms
		while(!in.eof()){
			if(!in.readLine().equals("ROOMSTART"))
				break;
			//add new room to list
			int[][][] tiles = new int[Room.NUM_LAYERS][Room.HEIGHT][Room.WIDTH];
			for(int i = 0; i < Room.NUM_LAYERS; i++)
				for(int y = 0; y < Room.HEIGHT; y++)
					for(int x = 0; x < Room.WIDTH; x++)
						tiles[i][y][x] = in.readInt();
			rooms.add(new Room(tiles));
			//read and add BGM destination (cut out string "BGM " from the data line)
			rooms.get(rooms.size()-1).setBGMTrack(in.readLine().replaceAll("BGM ", ""));
			//read for extra features, until ROOMEND
			boolean repeat = true;
			String line;
			while(repeat){
				line = in.readWord();
				if(line.equals("ROOMEND"))
					repeat = false;
				else if(line.equals("TELE")){
					Tele t = new Tele(new Coord(in.readInt(), in.readInt()),
									new Coord(in.readInt(), in.readInt(), in.readInt()));
					System.out.println("TELE Entrance "+t.getEntrance().getY()+", "+t.getEntrance().getX()+"\n"+
												"Dest "+t.getDest().getRoom()+": "+t.getDest().getY()+", "+t.getDest().getX()); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(t);
				}else if(line.equals("WALL")){
					WallTele w = new WallTele(in.readInt(), in.readInt());
					System.out.println("WALLTELE Side "+w.getSide()+"\n"+
												"Dest "+w.getDest()); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(w);
				}else if(line.equals("ENEMYSPAWN")){
					EnemySpawn e = new EnemySpawn(new Coord(in.readInt(), in.readInt()), in.readInt());
					System.out.println("ENEMYSPAWN Location "+e.getLocation().getY()+" "+e.getLocation().getX()+"\n"+
												"Enemy Type "+e.getType()); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(e);
				}else if(line.equals("DESTROYABLE")){
					Destroyable d = new Destroyable(new Coord(in.readInt(), in.readInt()), false, in.readInt(), 0, in.readInt());
					d.setTypeBefore(tiles[d.getLayer()][d.getLocation().getY()][d.getLocation().getX()]);
					System.out.println("DESTROYABLE Location "+d.getLocation().getY()+" "+d.getLocation().getX()+"\n"+
							"Layer "+d.getLayer()+", TypeAfter "+d.getTypeAfter()); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(d);
				}
			}
		}
//		//TODO DEBUG, show that map file was read
//		JOptionPane.showMessageDialog(null, "Chill", "Info", JOptionPane.PLAIN_MESSAGE);
		//conver arraylist into array
		Room[] roomsFinal = new Room[rooms.size()];
		for(int i = 0; i < rooms.size(); i++)
			roomsFinal[i] = rooms.get(i);
		return new Map(roomsFinal, name, spawnRoom, spawnCoord);
	}
}