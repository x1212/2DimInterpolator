import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Viewer {
	private boolean close;
	private float scale, vrot, hrot;

	private boolean edges, faces;
	private int numInterpolate;
	private LinkedList<Point> points;
	private Point[][] gridPoints;

	private long oldMillis = System.currentTimeMillis();

	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 1024));
			Display.setTitle("View");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("The display wasn't initialized correctly. :(");
			Display.destroy();
			System.exit(1);
		}
	}

	public void readPoints(String filePath) {

		points = new LinkedList<Point>();
	}

	public void setDrawMode(boolean connect, boolean fill, int divs) {
		numInterpolate = divs;
		edges = connect;
		faces = fill;
	}

	public void draw() {
		// GLEnable

		glClearColor(1.0f, 1.0f, 1.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		if (edges & faces) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		} else if (edges) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
		}

		// GLBegin
		glPushMatrix();
		// glScalef(0.5f,0.5f,1.0f);
		glRotatef(hrot, 1.0f, 0.0f, 0.0f);
		glRotatef(vrot, 0.0f, 0.0f, 1.0f);

		glBegin(GL_QUADS);

		float value;
		for (int x1 = 0; x1 + 1 < gridPoints.length; x1++) {
			for (int x2 = 0; x2 + 1 < gridPoints[x1].length; x2++) {

				if (gridPoints[x1][x2] != null
						&& gridPoints[x1 + 1][x2] != null
						&& gridPoints[x1 + 1][x2 + 1] != null
						&& gridPoints[x1][x2 + 1] != null) {
					value = (gridPoints[x1][x2].value * 256);
					if (!(edges & faces))
						glColor3b((byte) (0), (byte) 0x00, (byte) (0));
					if (edges & faces)
						glColor3b((byte) (value / 3), (byte) (value / 3),
								(byte) (value / 3));
					glVertex3f((2 * x1 / ((float) gridPoints.length - 1) - 1)/2, (2
							* x2 / ((float) gridPoints.length - 1) - 1)/2,
							(value / 128.0f - 1.0f)/2);// gridPoints[x1][x2].value);

					value = (gridPoints[x1 + 1][x2].value * 256);
					/*
					 * glColor3b((byte) (value / 2), (byte) 0x7f, (byte) (value
					 * / 2));
					 */
					if (edges & faces)
						glColor3b((byte) (value / 3), (byte) (value / 3),
								(byte) (value / 3));
					glVertex3f((2 * (x1 + 1) / ((float) gridPoints.length - 1)
							- 1)/2, (2 * x2 / ((float) gridPoints.length - 1) - 1)/2,
							(value / 128.0f - 1.0f)/2);// gridPoints[x1+1][x2].value);

					value = (gridPoints[x1 + 1][x2 + 1].value * 256);
					/*
					 * glColor3b((byte) (value / 2), (byte) 0x7f, (byte) (value
					 * / 2));
					 */
					if (edges & faces)
						glColor3b((byte) (value / 3), (byte) (value / 3),
								(byte) (value / 3));
					glVertex3f((2 * (x1 + 1) / ((float) gridPoints.length - 1)
							- 1)/2, (2 * (x2 + 1) / ((float) gridPoints.length - 1)
							- 1)/2, (value / 128.0f - 1.0f)/2);// gridPoints[x1+1][x2+1].value);

					value = (gridPoints[x1][x2 + 1].value * 256);
					/*
					 * glColor3b((byte) (value / 2), (byte) 0x7f, (byte) (value
					 * / 2));
					 */
					if (edges & faces)
						glColor3b((byte) (value / 3), (byte) (value / 3),
								(byte) (value / 3));
					glVertex3f((2 * x1 / ((float) gridPoints.length - 1) - 1)/2, (2
							* (x2 + 1) / ((float) gridPoints.length - 1) - 1)/2,
							(value / 128.0f - 1.0f)/2);
				}
			}
		}

		long now = System.currentTimeMillis();
		float delta = 50.0f * (now - oldMillis) / 1000;
		oldMillis = now;
		// System.out.println(delta);
		if (org.lwjgl.input.Keyboard
				.isKeyDown(org.lwjgl.input.Keyboard.KEY_RIGHT))
			vrot -= delta;
		if (org.lwjgl.input.Keyboard
				.isKeyDown(org.lwjgl.input.Keyboard.KEY_LEFT))
			vrot += delta;
		if (org.lwjgl.input.Keyboard
				.isKeyDown(org.lwjgl.input.Keyboard.KEY_DOWN))
			hrot -= delta;
		if (org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_UP))
			hrot += delta;
		glEnd();
		glPopMatrix();
	}

	private int getSquareRoot(int i) {
		int ret;
		System.out.println(i);
		for (ret = 0; ret * ret <= i; ret++) {

		}
		return ret;
	}

	public void setGrid(byte[][] newGrid, int level, BasicInterpolation basInt) {
		// level = level;
		int size1 = newGrid.length;
		int size2 = newGrid[0].length;
		float[][] newFGrid = new float[newGrid.length][newGrid[0].length];
		for (int i = 0; i < newGrid.length; i++) {
			for (int n = 0; n < newGrid[0].length; n++) {
				newFGrid[i][n] = ((float) newGrid[i][n]) / 128.0f;
			}
		}
		gridPoints = new Point[(size1 - 1) * level + 1][(size2 - 1) * level + 1];

		// -------------------Fortschrittsanzeige
		// init-----------------------------------------

		JFrame frame = null;
		int max = (size1 - 1) * (size2 - 1);
		JProgressBar bar = new JProgressBar(0, max);
		if (gridPoints[0].length > 1000) {
			frame = new JFrame();

			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			frame.getContentPane().add(bar);

			frame.pack();

			frame.setVisible(true);
		}

		// -------------------Fortschrittsanzeige init
		// ende-----------------------------------------

		for (int z = 0; z + 1 < size1; z++) {

			for (int s = 0; s + 1 < size2; s++) {

				/*
				 * gridPoints[i / size * (level)][i % size * (level)] = new
				 * Point(); gridPoints[i / size * (level)][i % size *
				 * (level)].value = newGrid[i % size][i / size] / 128.0f;
				 */
				gridPoints[z * level][s * level] = new Point();
				gridPoints[z * level][s * level].value = newFGrid[z][s];

				if (z + 1 < size1 && s + 1 < size2) {
					float vp1 = newFGrid[z][s];
					float vp2 = newFGrid[z + 1][s];
					float vp3 = newFGrid[z + 1][s + 1];
					float vp4 = newFGrid[z][s + 1];

					for (int u = 0; u <= level; u++) {
						for (int t = 0; t <= level; t++) {
							/*
							 * if (!((u == 0 && t == 0) || (u == level && t ==
							 * 0) || (u == level && t == level) || (u == 0 && t
							 * == level))) {
							 */
							gridPoints[z * level + u][s * level + t] = new Point();
							gridPoints[z * level + u][s * level + t].value = basInt
									.getInterpolatedValue(newFGrid, z, s,
											(float) u / ((float) level),
											(float) t / ((float) level));
							// if (gridPoints[z * level + u][s * level +
							// t].value>80.0f/128.0f)
							// System.out.println("z: "+z+" s: "+s+" u: "+u+" u: "+((float)u/(level+1))+" t: "+t+" t: "+((float)t/(level+1)));
							/*
							 * } else { if (u == level) { if (t == 0) {
							 * gridPoints[z * level + u][s * level + t] = new
							 * Point(); gridPoints[z * level + u][s * level +
							 * t].value = newGrid[z+1][s] / 128.0f; } else {
							 * gridPoints[z * level + u][s * level + t] = new
							 * Point(); gridPoints[z * level + u][s * level +
							 * t].value = newGrid[z+1][s+1] / 128.0f; } } else {
							 * if (t == level) { gridPoints[z * level + u][s *
							 * level + t] = new Point(); gridPoints[z * level +
							 * u][s * level + t].value = newGrid[z][s+1] /
							 * 128.0f; } else { gridPoints[z * level + u][s *
							 * level + t] = new Point(); gridPoints[z * level +
							 * u][s * level + t].value = newGrid[z][s] / 128.0f;
							 * } } }
							 */
						}
					}
					bar.setValue(z * (size1) + s);
				}

			}

		}
	}

	public boolean closed() {
		Display.update();
		return Display.isCloseRequested();
	}
}
