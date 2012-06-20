import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Gui extends JFrame implements ActionListener{
	private boolean close=false, changed=true;
	private ImageIcon faces = new ImageIcon("icons/faces.png");
	//private ImageIcon nofaces = new ImageIcon("icons/nofaces.png");
	private ImageIcon lines = new ImageIcon("icons/lines.png");
	private ImageIcon points = new ImageIcon("icons/points.png");
	//private ImageIcon nolines = new ImageIcon("icons/nolines.png");
	private ImageIcon iRandom = new ImageIcon("icons/random.png");
	private int fillmode = 0, numInt;
	private JButton filler;
	private JButton intmode;
	private JButton random;
	private JTextField tf, tfdim;
	private byte[][] fA = {{30, 30, 40, 40, 30, 30},{30, 100, 100, 100, 100, 30},{30, 100, 100, 100, 100, 30},
			{30, 100, 100, 100, 100, 30},{30, 100, 100, 100, 100, 30},{30, 30, 40, 40, 30, 30}};
	
	private Random r = new Random((int)System.currentTimeMillis());
	
	BasicInterpolation curint = new NearestNeighbour();
	
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		filler = new JButton();
		intmode = new JButton();
		random = new JButton();
		tf = new JTextField();
		tfdim = new JTextField();
		
		
		
		this.getContentPane().add(filler);
		this.getContentPane().add(intmode);
		this.getContentPane().add(random);
		this.getContentPane().add(new JLabel("Anzahl Zwischenwerte:"));
		this.getContentPane().add(tf);
		this.getContentPane().add(new JLabel("Matrixgröße:"));
		this.getContentPane().add(tfdim);
		
		filler.setIcon(faces);
		filler.addActionListener(this);
		filler.setActionCommand("filler");
		intmode.setText("Nearest Neighbour");
		intmode.addActionListener(this);
		intmode.setActionCommand("intmode");
		random.setIcon(iRandom);
		random.addActionListener(this);
		random.setActionCommand("random");
		tf.setText("1");
		tf.setSize(new Dimension(500, 16));
		tf.setActionCommand("tf");
		tfdim.setText("1");
		tfdim.setSize(new Dimension(500, 16));
		tfdim.setActionCommand("tfdim");
		
		
		this.setLayout(new GridLayout(0,1));
		this.pack();
        this.setVisible(true);
	}
	
	public String sourceFile() {
		return null;
	}
	
	public boolean changed() {
		if (changed) {
			changed = false;
			return true;
		} else {
			return false;
		}
	}
	
	public int level() {
		try {
			return Integer.parseInt(tf.getText());
		} catch (Throwable t) {
			return 0;
		}
	}
	
	public boolean connected() {
		return true;
	}
	
	public int filled() {
		return fillmode;
	}
	
	public BasicInterpolation getInterpolator() {
		return curint;
	}
	
	public boolean closed() {
		return close;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		changed = true;
		
		
		if (arg0.getActionCommand().equals("filler")) {
			fillmode++;
			fillmode = fillmode%4;
			
			switch(fillmode) {
			case 0:
				filler.setIcon(faces);
				break;
			case 1:
				filler.setIcon(lines);
				break;
			case 2:
				filler.setIcon(points);
				break;
			case 3:
				filler.setIcon(lines);
				break;
			default: break;
			}
		} else if (arg0.getActionCommand().equals("intmode")) {
			if(curint instanceof NearestNeighbour) {
				curint = new BiLinear();
				intmode.setText("Bilinear");
			}
			else if (curint instanceof BiLinear) {
				curint = new BiCubic();
				intmode.setText("Bicubic");
			}
			else {
				curint = new NearestNeighbour();
				intmode.setText("Nearest Neighbour");
			}
		} else if (arg0.getActionCommand().equals("random")) {
			try {
				int size = 2+Integer.parseInt(tfdim.getText());
				fA = new byte[size][size];
				
				for (byte[] bA: fA) {
					int i = 0;
					for (byte b: bA) {
						b = (byte)(r.nextInt()%128);
						if (b<0) b= (byte) (-b);
						bA[i] = b;
						//System.out.println(b);
						i++;
					}
				}
				/*for (int i=0; i<fA.length; i++) {
					System.out.println("test: "+fA[i][i]);
				}*/
				
			} catch (Throwable t) {
				fA = new byte[2][2];
			}
		}
	}
	public byte[][] getValues() {
		return fA;
	}
}
