import java.awt.Color;
import java.awt.Font;


public class ScheduleViz {
	private Color[] colorSelection = {
		Color.BLUE,
		Color.ORANGE,
		Color.CYAN,
		Color.GREEN,
		Color.MAGENTA,
		Color.PINK,
		Color.LIGHT_GRAY,
		Color.RED,
		Color.WHITE,
		Color.YELLOW,
		Color.GRAY,
		Color.DARK_GRAY
	};	
	private Color[] fontColorSelection = {
			Color.WHITE,
			Color.BLACK,
			Color.BLACK,
			Color.BLACK,
			Color.WHITE,
			Color.BLACK,
			Color.BLACK,
			Color.WHITE,
			Color.BLACK,
			Color.BLACK,
			Color.WHITE,
			Color.WHITE
		};	

	
	Ship[][] V;
	final int M_SIZE = 40;
	final int L_SIZE = 60;
	int minX; 
	int minY;
	int maxX;
	int maxY;
	int machines;
	
	public static void drawSchedule(Ship[][] V){
		ScheduleViz viz = new ScheduleViz(V);
		viz.drawSchedule();
	}
	
	private ScheduleViz(Ship[][] V){
		this.V = V;
		minX = 0; 
		minY = 0;
		maxX = Integer.MIN_VALUE;
		maxY = Integer.MIN_VALUE;
		machines = V[0].length;
		//Find the bounding box of the drawing
		for(Ship[] Vi: V){
			for(Ship task : Vi){
				maxX = Math.max(maxX, task.getEndTime());
			}
		}	
		//machines+=2;//getting the number since ID is zero based
		maxY = machines*M_SIZE;
		
		//Setting up the canvas
		StdDraw.setCanvasSize((maxX+L_SIZE), maxY);
		//Setting up the scale with a border of 10 pixels
		StdDraw.setXscale(minX-L_SIZE-10, maxX+10);
		StdDraw.setYscale(minY-10, maxY+10);
		Font font = new Font("Arial",Font.PLAIN,12);
		StdDraw.setFont(font);

	}
	
	private void drawSchedule(){		
		drawGrid();
		//Draw Tasks
		for(int wt = 0; wt<V.length;wt++)
			for(int t = 0; t<V[wt].length;t++)
				drawShip(V[wt][t]);
		
	}
	
	private void drawGrid(){
		//Draw base grid
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		//bounding box
		StdDraw.rectangle((minX-L_SIZE)/2, maxY/2, L_SIZE/2, (maxY-minY)/2);
		StdDraw.rectangle(maxX/2, maxY/2, (maxX-minX)/2, (maxY-minY)/2);
		for(int m = 1; m<=machines; m++){
			StdDraw.line(minX-L_SIZE,maxY-(m)*M_SIZE,maxX,maxY-(m)*M_SIZE);
		}
		
		//Draw Labels
		StdDraw.setPenColor(StdDraw.BLACK);
		for(int m = 0; m<machines; m++){			
			StdDraw.text(minX-L_SIZE/2,maxY-(m*M_SIZE)-M_SIZE/2,"S"+m);
		}		
	}
	
	private void drawShip(Ship Vi){
		int y = maxY-Vi.getId() * M_SIZE-M_SIZE/2;
		int x = Vi.getStartTime()+Vi.getDuration()/2;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(x, y,Vi.getDuration()/2, 10);
		StdDraw.setPenColor(this.colorSelection[Vi.getBerth()%colorSelection.length]);
		StdDraw.filledRectangle(x, y,Vi.getDuration()/2, 10);
		StdDraw.setPenColor(this.fontColorSelection[Vi.getBerth()%colorSelection.length]);
		StdDraw.text(x,y,Vi.getBerth()+":"+Vi.getId());
		//System.out.println(""+ x+" "+y+" "+task.getDuration()/2+" "+ task.getShip());

	}
	
	
}
