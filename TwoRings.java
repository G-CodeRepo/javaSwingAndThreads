// Gerald Abut
// ICS 432 Assignment 2
// 2/3/2015

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class TwoRings implements ActionListener{
	private int panel_width;
	private int panel_height;
	private int small_circle;
	private int big_circle;
	private JFrame frame;
	private JPanel buttonPanel;
	private JButton button;
	private Ring ring1;
	private Ring ring2;
	private Color color_1;
	private Color color_2;
	private int pause_time1;
	private int pause_time2;
	private JPanel ringsPanel;
	private Color ring_color;
	
	// CONSTRUCTOR
	public TwoRings(int pause_1, int pause_2, final Color color_1, final Color color_2) {
		this.pause_time1 = pause_1;
		this.pause_time2 = pause_2;
		this.color_1 = color_1;
		this.color_2 = color_2;
		
		this.panel_width = 200;
		this.panel_height = 200;
		this.big_circle = 100;
		this.small_circle = 70;
		
	    this.frame = new JFrame();
		this.frame.setSize(panel_width + 300, panel_height);
		this.frame.getContentPane().setBackground(Color.BLACK);	// main background color	
		this.frame.setLayout(new BorderLayout(1,3));
		
		// button panel
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout());
	    this.buttonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));
		this.buttonPanel.setSize(panel_width/2, panel_height);
		
		// set array of panels and add a button to middle panel
		int row = 3;
		int col = 1;
		JPanel[][] panelArray = new JPanel[row][col];    
		buttonPanel.setLayout(new GridLayout(row,col));
		buttonPanel.setSize(panel_width/100, panel_height);
		for(int i = 0; i < row; i++) {
		   for(int j = 0; j < col; j++) {
		      panelArray[i][j] = new JPanel();
		      buttonPanel.add(panelArray[i][j]);
		   }
		}
		
		// add action listener to button
		this.button = new JButton("START");
		this.button.addActionListener(this);
		panelArray[1][0].add(this.button);
	
		// create button and ring panels;
		ringsPanel = new JPanel();
		ringsPanel.setLayout(new GridLayout(1, 2));
		
		// create rings
		ring1 = new Ring(pause_time1, color_1, panel_width, panel_height, big_circle, small_circle);
		ring2 = new Ring(pause_time2, color_2, panel_width, panel_height, big_circle, small_circle);
			
        SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		// create a main panel for the rings that contain 2 ring panels
        		frame.add(buttonPanel, BorderLayout.LINE_START);
        		ringsPanel.add(ring1);
        		ringsPanel.add(ring2);
        		frame.add(ringsPanel,BorderLayout.CENTER);
				frame.setVisible(true);
           }
        });
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component)e.getSource();
		if (c == this.button) {
			// FLASH EFFECT
			Thread runRingFlash1 = new Thread(this.ring1);
			runRingFlash1.start();	
			
			Thread runRingFlash2 = new Thread(this.ring2);
			runRingFlash2.start();
			this.button.setEnabled(false);	// disable button
		}
	}
	
	// RING CLASS  ***********************************************************************
	public class Ring extends JPanel implements Runnable{
		private int PANEL_WIDTH;
		private int PANEL_HEIGHT;
		private int BIG_CIRCLE;
		private int SMALL_CIRCLE;
		private int pause;
		private Color color;
		
		// CONSTRUCTOR
		public Ring(int pause, Color color, int panel_width, int panel_height, int big_circle, int small_circle) {
			this.pause = pause;
			this.color = color;
			this.PANEL_WIDTH = panel_width;
			this.PANEL_HEIGHT = panel_height;
			this.BIG_CIRCLE = big_circle;
			this.SMALL_CIRCLE = small_circle;
			this.setSize(this.PANEL_WIDTH, this.PANEL_HEIGHT);
		}
			    
		// PAINT GRAPHICS
		public void paintComponent(Graphics g) {
			// big circle
			super.paintComponent(g);
			g.setColor(this.color);
			g.fillOval(BIG_CIRCLE/2,BIG_CIRCLE/3, BIG_CIRCLE, BIG_CIRCLE);
			this.setBackground(Color.BLACK);
		    this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

		    // small circle
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
			g2d.setColor(Color.BLACK);
			g2d.fillOval(SMALL_CIRCLE-5,SMALL_CIRCLE-22, SMALL_CIRCLE, SMALL_CIRCLE); 
		}

		@Override
		public void run() {			
			Color previousColor = this.color;
			while (true) {
				this.color = Color.BLACK;
				this.repaint();
				try {
					Thread.sleep(this.pause);
				} catch (InterruptedException e) {
					System.err.println("Thread Interrupt Error");
				}
				this.color = previousColor;
				this.repaint();
				try {
					Thread.sleep(this.pause);
				} catch (InterruptedException e) {
					System.err.println("Thread Interrupt Error");
				}
			}	
		}
	}
	// ***********************************************************************************

	// MAIN
	public static void main(String[] args) {		
		if (args.length <= 0) {
			System.out.println("Two Arguments Needed");
		} else {
			TwoRings mg = new TwoRings(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Color.RED, Color.BLUE);

		}
	}	
}
