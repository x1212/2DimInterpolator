
public class Main {
	
	Gui gui;
	Viewer view;
	BasicInterpolation lastInt;
	byte[][] data;
	
	
	
	public void init() {
		gui = new Gui();
		view = new Viewer();
		view.init();
		/*byte[][] fA = {{80, 80, 90, 80, 80, 80},{60, 60, 70, 60, 60, 60},{40, 40, 50, 40, 40, 40},
				{20, 20, 30, 20, 20, 20},{0, 0, 10, 0, 0, 0},{-20, -20, -10, -20, -20, -20}};*///{80, 60, 40, 20, 0, -20}};
		//byte[][] fA = {{0, 30, 60, 90}, {0, 30, 60, 90}, {0, 30, 60, 0}, {0, 30, 60, 90}};
		lastInt = gui.getInterpolator();
		view.setGrid(gui.getValues(), gui.level(), lastInt);//new BasicInterpolation());
		view.setDrawMode(true, false, 0);
	}
	
	public void run() {
		while (!view.closed()) {
			view.draw();
			
			if(gui.changed()) {
				//drawmode, matix, interpolator, speed, ...
				switch (gui.filled()) {
				case 0:
					view.setDrawMode(true, true, 0);
					break;
				case 1:
					view.setDrawMode(true, false, 0);
					break;
				case 2:
					view.setDrawMode(false, false, 0);
					break;
				default:
					view.setDrawMode(true, false, 0);
					break;
				}
				
				if (gui.getInterpolator()!=lastInt || data != gui.getValues()) view.setGrid(gui.getValues(), gui.level(), gui.getInterpolator());
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("lwjgl "+org.lwjgl.Sys.getVersion());
			Main prog = new Main();
			prog.init();
			prog.run();
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t);
		}
	}

}
