// Gerald Abut
// ICS 432 Assignment 2
// 2/5/2015

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ManyPanels implements ActionListener {
	private final int PANEL_WIDTH = 200;
	private final int PANEL_HEIGHT = 200;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private final int P;
	private final int NUM_PANELS = 16 ;
	private JPanel panelArrayContainer;
	private JPanel comboBoxArrayContainer;
	private JPanel textLabelArrayContainer;
	private JPanel checkBoxArrayContainer;
	private List<CustomPanel> panelList;
	private List<JLabel> labelList;
	private List<Integer> priorityList;
	private List<JComboBox> comboBoxList;
	private List<JCheckBox> checkBoxList;
	private HashMap<Integer, Boolean> doNotRunList; 

	// CONSTRUCTOR
	public ManyPanels(final int P) {
		this.panelList = new ArrayList<CustomPanel>();
		this.labelList = new ArrayList<JLabel>();
		this.comboBoxList = new ArrayList<JComboBox>();
		this.checkBoxList = new ArrayList<JCheckBox>();
		this.doNotRunList = new HashMap<>();
		
		this.priorityList = Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);	//new ArrayList<Integer>();
		this.P = P;
	    this.frame = new JFrame();
		this.frame.setSize((PANEL_WIDTH * 6) + 75, PANEL_HEIGHT + 40);
		this.frame.setLayout(new BorderLayout(1,16));
		boolean debugPanel = false;	// sets colors to panels (FOR DEBUGGING ONLY)
		
		// button panel
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout());
	    this.buttonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		this.buttonPanel.setSize(PANEL_WIDTH/2, PANEL_HEIGHT);
		
		// set array of panels and add a button to middle panel
		int row = 3;
		int col = 1;
		JPanel[][] panelArray = new JPanel[row][col];    
		buttonPanel.setLayout(new GridLayout(row,col));
		for(int i = 0; i < row; i++) {
		   for(int j = 0; j < col; j++) {
		      panelArray[i][j] = new JPanel();
		      buttonPanel.add(panelArray[i][j]);
		   }
		}
		
		// add action listener to button
		JButton button = new JButton("START");
		button.setName("START");	// button name
		button.addActionListener(this);
		panelArray[1][0].add(button);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setPreferredSize(new Dimension(this.PANEL_WIDTH, this.PANEL_HEIGHT + 100));	
		this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		// container arrays
		this.panelArrayContainer = new JPanel();
		this.panelArrayContainer.setLayout(new FlowLayout());
		if (debugPanel) {panelArrayContainer.setBackground(Color.GREEN);}
		
		this.comboBoxArrayContainer = new JPanel();
		this.comboBoxArrayContainer.setLayout(new FlowLayout());
		if (debugPanel) {comboBoxArrayContainer.setBackground(Color.CYAN);}

		this.textLabelArrayContainer = new JPanel();
		if (debugPanel) {textLabelArrayContainer.setBackground(Color.MAGENTA);}
		
		this.checkBoxArrayContainer = new JPanel();
		this.checkBoxArrayContainer.setLayout(new FlowLayout());
		if (debugPanel) {textLabelArrayContainer.setBackground(Color.RED);}

		for (int j = 0; j < this.NUM_PANELS; j++) {				
			// combo boxes
			JComboBox<Integer> comboBox = new JComboBox<Integer>();
			comboBox.setName("comboBox_" + j);	// combo box names
			comboBox.setPreferredSize(new Dimension(68, 25));
			comboBox.addActionListener(this);
			
			for (int i = 1; i <= 10; i++) {	// shows 1-10
				comboBox.addItem(i);	// populate with numbers
			}
			this.comboBoxArrayContainer.add(comboBox);
			this.comboBoxList.add(comboBox);
			
			// text labels
			JLabel text = new JLabel("0");	// initial value
			text.setName("label_" + String.valueOf(j));		// label names
			if (debugPanel) {text.setBorder(BorderFactory.createLineBorder(Color.BLACK));}
			text.setPreferredSize(new Dimension(68, 25));
			text.setHorizontalAlignment(SwingConstants.CENTER);
			this.textLabelArrayContainer.add(text);
			this.labelList.add(text);
			
			// check boxes
			JCheckBox checkbox = new JCheckBox();
			checkbox.setName("checkBox_" + String.valueOf(j));	// check box name
			checkbox.setHorizontalAlignment(SwingConstants.CENTER);
			checkbox.addActionListener(this);
			this.checkBoxList.add(checkbox);
			
			JPanel checkBoxPanel = new JPanel();
			if (debugPanel) {checkBoxPanel.setBackground(Color.YELLOW);}
			checkBoxPanel.setPreferredSize(new Dimension(68, 25));

			checkBoxPanel.add(checkbox);
			this.checkBoxArrayContainer.add(checkBoxPanel);
			
			// custom panels
			CustomPanel panel = new CustomPanel(P, text);
			panel.setName("panel_" + String.valueOf(j));	// panel names
			this.panelArrayContainer.add(panel);
			this.panelList.add(panel);
		}
				
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				// add components to main panel
				mainPanel.add(comboBoxArrayContainer, BorderLayout.PAGE_START);
				mainPanel.add(panelArrayContainer, BorderLayout.CENTER);
				
				// bottom components contains 2 panel arrays
				JPanel bottomComponents = new JPanel();		
				bottomComponents.setLayout(new GridLayout(2, 16));
				bottomComponents.add(textLabelArrayContainer);
				bottomComponents.add(checkBoxArrayContainer);
				mainPanel.add(bottomComponents, BorderLayout.PAGE_END);
				
				// add main panel and start button panel to frame
				frame.add(mainPanel, BorderLayout.CENTER);
				frame.add(buttonPanel, BorderLayout.LINE_START);
				
				//frame.add(mainPanel, BorderLayout.CENTER);
				frame.setVisible(true);
			}
		});
	}
	
	// ACTION PERFORM FOR COMPONENTS WITH ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent e) {
	
		Component c = (Component)e.getSource();
		String s = c.getName();	
		boolean print = false;	// print priority values (FOR DEBUGGING ONLY)

		//  START APP
		if (s.compareTo("START") == 0) {
			// create and start threads for each panel and disabling all listening components
			int i = 0;
			for (CustomPanel p : this.panelList) {
				Thread t = new Thread(p);
				this.customSetPriority(t, this.priorityList.get(i));
				
				if ((doNotRunList.get(i) == null) || (doNotRunList.get(i) == false)) { // run only if true
					this.checkBoxList.get(i).setEnabled(false); 	// disable checkbox
				} else {
					t.start();	// start thread
					this.checkBoxList.get(i).setEnabled(false); // disable checkbox
				}
			
				this.comboBoxList.get(i).setEnabled(false);// disable combo box
				i++;	// set to next priority in list
			}
			c.setEnabled(false);	// disable button
		} 
		
		// SET WHICH THREAD TO RUN BEFORE STARTING APP
		if (s.contains("checkBox")) {
			JCheckBox checkbox = (JCheckBox)e.getSource();
			
			if (s.compareTo("checkBox_0") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(0, true);
				} else {
					this.doNotRunList.put(0, false);
				}
			}
			if (s.compareTo("checkBox_1") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(1, true);
				} else {
					this.doNotRunList.put(1, false);
				}
			}
			if (s.compareTo("checkBox_2") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(2, true);
				} else {
					this.doNotRunList.put(2, false);
				}
			}
			if (s.compareTo("checkBox_3") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(3, true);
				} else {
					this.doNotRunList.put(3, false);
				}
			}
			if (s.compareTo("checkBox_4") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(4, true);
				} else {
					this.doNotRunList.put(4, false);
				}
			}
			if (s.compareTo("checkBox_5") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(5, true);
				} else {
					this.doNotRunList.put(5, false);
				}
			}
			if (s.compareTo("checkBox_6") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(6, true);
				} else {
					this.doNotRunList.put(6, false);
				}
			}
			if (s.compareTo("checkBox_7") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(7, true);
				} else {
					this.doNotRunList.put(7, false);
				}
			}
			if (s.compareTo("checkBox_8") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(8, true);
				} else {
					this.doNotRunList.put(8, false);
				}
			}
			if (s.compareTo("checkBox_9") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(9, true);
				} else {
					this.doNotRunList.put(9, false);
				}
			}
			if (s.compareTo("checkBox_10") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(10, true);
				} else {
					this.doNotRunList.put(10, false);
				}
			}
			if (s.compareTo("checkBox_11") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(11, true);
				} else {
					this.doNotRunList.put(11, false);
				}
			}
			if (s.compareTo("checkBox_12") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(12, true);
				} else {
					this.doNotRunList.put(12, false);
				}
			}
			if (s.compareTo("checkBox_13") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(13, true);
				} else {
					this.doNotRunList.put(13, false);
				}
			}
			if (s.compareTo("checkBox_14") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(14, true);
				} else {
					this.doNotRunList.put(14, false);
				}
			}
			if (s.compareTo("checkBox_15") == 0) {
				if (checkbox.isSelected()) {
					this.doNotRunList.put(15, true);
				} else {
					this.doNotRunList.put(15, false);
				}
			}
		}
		// SETUP PRIORITY BEFORE STARTING APP
		if (s.contains("comboBox")) {
			JComboBox selection = (JComboBox)e.getSource();
			int priority = (int)selection.getSelectedItem();
			if (print) {System.out.println("priority: " + priority);}
			if (s.compareTo("comboBox_0") == 0) {
				this.priorityList.set(0, priority);
			}
			if (s.compareTo("comboBox_1") == 0) {
				this.priorityList.set(1, priority);
			}
			if (s.compareTo("comboBox_2") == 0) {
				this.priorityList.set(2, priority);
			}
			if (s.compareTo("comboBox_3") == 0) {
				this.priorityList.set(3, priority);
			}
			if (s.compareTo("comboBox_4") == 0) {
				this.priorityList.set(4, priority);
			}
			if (s.compareTo("comboBox_5") == 0) {
				this.priorityList.set(5, priority);
			}
			if (s.compareTo("comboBox_6") == 0) {
				this.priorityList.set(6, priority);
			}
			if (s.compareTo("comboBox_7") == 0) {
				this.priorityList.set(7, priority);
			}
			if (s.compareTo("comboBox_8") == 0) {
				this.priorityList.set(8, priority);
			}
			if (s.compareTo("comboBox_9") == 0) {
				this.priorityList.set(9, priority);
			}
			if (s.compareTo("comboBox_10") == 0) {
				this.priorityList.set(10, priority);
			}
			if (s.compareTo("comboBox_11") == 0) {
				this.priorityList.set(11, priority);
			}
			if (s.compareTo("comboBox_12") == 0) {
				this.priorityList.set(12, priority);
			}
			if (s.compareTo("comboBox_13") == 0) {
				this.priorityList.set(13, priority);
			}
			if (s.compareTo("comboBox_14") == 0) {
				this.priorityList.set(14, priority);
			}
			if (s.compareTo("comboBox_15") == 0) {
				this.priorityList.set(15, priority);
			}
		}
	}
	
	private void customSetPriority(Thread t, int priority) {
		// Note: 10 = highest priority value in java , 1 = lowest priority value in java
		switch (priority) {
		case 1:
			t.setPriority(Thread.MIN_PRIORITY);
			break;
		case 2:
			t.setPriority(Thread.MIN_PRIORITY+1);
			break;
		case 3:
			t.setPriority(Thread.MIN_PRIORITY+2);
			break;
		case 4:
			t.setPriority(Thread.MIN_PRIORITY+3);
			break;
		case 5:
			t.setPriority(Thread.MIN_PRIORITY+4);
			break;
		case 6: 
			t.setPriority(Thread.MIN_PRIORITY+5);
			break;
		case 7:
			t.setPriority(Thread.MIN_PRIORITY+6);
			break;
		case 8:
			t.setPriority(Thread.MIN_PRIORITY+7);
			break;
		case 9:
			t.setPriority(Thread.MIN_PRIORITY+8);
			break;
		case 10:
			t.setPriority(Thread.MAX_PRIORITY);
			break;
		default:
			System.err.println("ERROR: Invalid Priority");	// should never go here
		}	
	}

	// PANEL CLASS ***********************************************************************************
	private class CustomPanel extends JPanel implements Runnable {
		private final int HEIGHT= 100;
		private final int WIDTH= 68;
		private int startPosition = this.HEIGHT-100;
		private int endPosition = this.HEIGHT+100;
		private int lineAdjustment;
		private int P;
		private JLabel label;
		
		// CONSTRUCTOR
		public CustomPanel(int P, JLabel label) {
			this.P = P;
			this.label = label;
			this.lineAdjustment = 0;	// initially zero adjustment
			this.setSize(this.WIDTH, this.HEIGHT);
			this.setBackground(Color.GRAY);
			this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
			this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));	
		}
		
		@Override
		public void run() {
			// RUN LINE ANIMATION (computation will create an effect of moving line)
			int i = this.startPosition;
			int j = 0;
			int numComputation = 1000;
			int lineCount = 0;
			int loopCount = 0;
			long timeStart = System.currentTimeMillis();
			long timeElapsed = 0;
			long timeStop = 180000; 	// 180000 ms = 3 min
			
			while (j < this.endPosition) {
				// compute cosine of first 1000 integers, P number of times
				for (int k = 0; k < this.P; k++) {
					double cosineSum = 0;
					for (int l = 0; l < numComputation; l++) {
						cosineSum += Math.cos(l);
					}
				}
				this.lineAdjustment = i;
				this.repaint();
				
				// updates
				i++;
				j++;
				loopCount++;
				if (j == this.endPosition) {		// force infinite loop
					j = 0;
					i= this.startPosition;
					lineCount++;					// line counter
					this.label.setText(String.valueOf(lineCount));
				}
		
				timeElapsed = System.currentTimeMillis() - timeStart;
				
				if (this.getName().length() == 7) {
					System.out.println(this.getName() + "\t\ttime Elapsed (ms): " + timeElapsed);
				} else {
					System.out.println(this.getName() + "\ttime Elapsed (ms): " + timeElapsed);
				}
				
				if (timeElapsed < timeStop) {
					// keep running
				} else {
					break;	// 3 minutes is up. stop computations
				}
			}
			
			System.out.println("Horizontal Line Count: " + lineCount);
			System.out.println("Loop Count: " + loopCount);
		}
		
		// DRAW SHAPES
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			int adjustHeight = this.startPosition + this.lineAdjustment; // calculation to move horizontal line
			int lineThickness = 1;
			g.fillRect(this.WIDTH-70, adjustHeight, this.HEIGHT, lineThickness);
		}
		
	}
	// ***********************************************************************************
	
	// MAIN
	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("Needs One Argument");
		} else {
			try {
				int num = Integer.parseInt(args[0]);
				if (num < 0) {
					System.err.println("Error: Negative Number");
				} else {
					ManyPanels p = new ManyPanels(num);
				}
			} catch (NumberFormatException e) {
				System.err.println("Error: Given string cannot be converted to a number");
			}
		}
	}
}
