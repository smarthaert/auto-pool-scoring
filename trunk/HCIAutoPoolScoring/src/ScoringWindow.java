import javax.swing.*;
import java.awt.image.*;
import javax.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class ScoringWindow extends JPanel 
implements MouseListener, Runnable, ActionListener, ComponentListener {
	
	protected String p1Name = null;
	protected String p2Name = null;
	
	public int p1RackScore = 0;
	public int p2RackScore = 0;
	public int p1TotalScore = 0;
	public int p2TotalScore = 0;
	
	private int p1WinsAt = 250;
	private int p2WinsAt = 250;
	
	public int sunkp1 = 0;
	public int sunkp2 = 0;
	public int missp1 = 0;
	public int missp2 = 0;
	public int faultp1 = 0;
	public int faultp2 = 0;
	public float avgrunp1 = 0;
	public float avgrunp2 = 0;
	public int maxrunp1 = 0;
	public int maxrunp2 = 0;
	public int currunp1 = 0;
	public int currunp2 = 0;
	public int nbrunp1 = 0;
	public int nbrunp2 = 0;
	
	private GridBagLayout gridBagLayout = null;
	private GridBagConstraints gridConstraints = null;

	private JLabel rackScore = null;
	private JLabel rackDetails = null;
	private JLabel totalScore = null;
	private JLabel gameGoal = null;
	
	protected JTextField p1IDF = null;
	protected JTextField p2IDF = null;
	protected JTextField p1RackScoreF = null;
	protected JTextField p2RackScoreF = null;		
	protected JTextField p1TotalScoreF = null;
	protected JTextField p2TotalScoreF = null;	
	private JTextField p1GoalF = null;
	private JTextField p2GoalF = null;	
	
	protected PlayerRackPanel p1RackDetails = null;
	protected PlayerRackPanel p2RackDetails = null;
	protected VirtualRackPanel virtualRack = null;
	
	private JSeparator horiz1 = null;
	private JSeparator horiz2 = null;
	private JSeparator horiz3 = null;
	private JSeparator horiz4 = null;
	private JSeparator horiz5 = null;
	
	private JSeparator vert1 = null;
	private JSeparator vert2 = null;
	private JSeparator vert3 = null;
	private JSeparator vert4 = null;

	private JPanel buttonPanel = null;
	private JButton restartButton = null;
	private JButton editButton = null;
	private JButton statsButton = null;
	private JButton replayButton = null;
	private JButton doneButton = null;
	
	private JLayeredPane layeredPane = null;
	
	protected final int PANEL_WIDTH = 1024;
	protected final int PANEL_HEIGHT = 768;
	protected final int VIRTUAL_RACK_WIDTH = 125;
	protected final int VIRTUAL_RACK_HEIGHT = 125;
	
	// makes the turn selector appear on top of the player's column for mac osx
	private final int XFUDGE = 4;
	private final int YFUDGE = 0;
	
	// turn selector width in pixels
	private final int STROKE_WIDTH = 8;
	
	// size of the turn selector indicator
	private final int TRIANGLE_LENGTH = 20;

	// players' turn selector
	private Rectangle2D.Float rect2d = null;
	private Polygon p = null;
	
	// needed for animation purposes
	private Thread runner;
	
	public boolean p1selected = true;
	private int selector_x;
	private int selector_y;
	private int selector_width;
	private int selector_height;
	private boolean initSelectorPlacement = false;
	private boolean showSelector = true;
	
	private boolean allowRemoteCommands = true;
	
	//TODO add a default font
	
	public ScoringWindow() {
		this.p1Name = "Player1";
		this.p2Name = "Player2";
		createAndShowGUI();
	}
		
	public ScoringWindow(String p1Name, String p2Name, int goal) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.p1WinsAt = goal;
		this.p2WinsAt = goal;	
		createAndShowGUI();		
	}
	
	public ScoringWindow(String p1Name, String p2Name, int p1objective, int p2objective) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.p1WinsAt = p1objective;
		this.p2WinsAt = p2objective;
		createAndShowGUI();	
	}
	
	private void createAndShowGUI() {
		layeredPane = new JLayeredPane();
		setLayout(new BorderLayout());
		
		gridBagLayout = new GridBagLayout();
		gridConstraints = new GridBagConstraints();
		layeredPane.setLayout(gridBagLayout);
		layeredPane.setSize(PANEL_WIDTH*2, PANEL_HEIGHT*2);
		
		rackScore = new JLabel("Rack Score:", JLabel.RIGHT);
		rackDetails = new JLabel("Rack Details:", JLabel.RIGHT);
		totalScore = new JLabel("Total Score:", JLabel.RIGHT);
		gameGoal = new JLabel("Goal:", JLabel.RIGHT);
		
		int ncols = Math.max( p1Name.length(), p2Name.length() );		
		p1IDF = new JTextField(p1Name, ncols);
		p2IDF = new JTextField(p2Name, ncols);
		p1RackScoreF = new JTextField("0", 3);
		p2RackScoreF = new JTextField("0", 3);
		p1RackDetails = new PlayerRackPanel(VIRTUAL_RACK_WIDTH, VIRTUAL_RACK_HEIGHT, 1);
		p2RackDetails = new PlayerRackPanel(VIRTUAL_RACK_WIDTH, VIRTUAL_RACK_HEIGHT, 2);
		p1TotalScoreF = new JTextField("0", 3);
		p2TotalScoreF = new JTextField("0", 3);		
		p1GoalF = new JTextField(Integer.toString(p1WinsAt), 3);
		p2GoalF = new JTextField(Integer.toString(p2WinsAt), 3);
		virtualRack = new VirtualRackPanel(VIRTUAL_RACK_WIDTH, VIRTUAL_RACK_HEIGHT);
		
		p1IDF.setHorizontalAlignment(JTextField.CENTER);
		p2IDF.setHorizontalAlignment(JTextField.CENTER);
		p1RackScoreF.setHorizontalAlignment(JTextField.CENTER);
		p2RackScoreF.setHorizontalAlignment(JTextField.CENTER);
		p1TotalScoreF.setHorizontalAlignment(JTextField.CENTER);
		p2TotalScoreF.setHorizontalAlignment(JTextField.CENTER);
		p1GoalF.setHorizontalAlignment(JTextField.CENTER);
		p2GoalF.setHorizontalAlignment(JTextField.CENTER);		
		
		p1IDF.setEditable(false);
		p2IDF.setEditable(false);
		p1RackScoreF.setEditable(false);
		p2RackScoreF.setEditable(false);
		p1TotalScoreF.setEditable(false);
		p2TotalScoreF.setEditable(false);
		p1GoalF.setEditable(false);
		p2GoalF.setEditable(false);
		
		p1RackDetails.setOpaque(true);
		p2RackDetails.setOpaque(true);
		virtualRack.setOpaque(true);
		
		horiz1 = new JSeparator(JSeparator.HORIZONTAL);
		horiz2 = new JSeparator(JSeparator.HORIZONTAL);
		horiz3 = new JSeparator(JSeparator.HORIZONTAL);
		horiz4 = new JSeparator(JSeparator.HORIZONTAL);
		horiz5 = new JSeparator(JSeparator.HORIZONTAL);
		
		vert1 = new JSeparator(JSeparator.VERTICAL);
		vert2 = new JSeparator(JSeparator.VERTICAL);
		vert3 = new JSeparator(JSeparator.VERTICAL);
		vert4 = new JSeparator(JSeparator.VERTICAL);

		gridConstraints.gridy = 0;
		gridConstraints.gridx = 2;
		layeredPane.add(p1IDF, gridConstraints, 0);
		clearGridConstraints();

		gridConstraints.gridy = 0;
		gridConstraints.gridx = 4;
		layeredPane.add(p2IDF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 2;
		gridConstraints.gridx = 0;
		layeredPane.add(rackScore, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 2;
		gridConstraints.gridx = 2;
		layeredPane.add(p1RackScoreF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 2;
		gridConstraints.gridx = 4;
		layeredPane.add(p2RackScoreF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 0;
		layeredPane.add(rackDetails, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 2;
		layeredPane.add(p1RackDetails, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 4;
		layeredPane.add(p2RackDetails, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 6;
		layeredPane.add(virtualRack, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 6;
		gridConstraints.gridx = 0;
		layeredPane.add(totalScore, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 6;
		gridConstraints.gridx = 2;
		layeredPane.add(p1TotalScoreF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 6;
		gridConstraints.gridx = 4;
		layeredPane.add(p2TotalScoreF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 8;
		gridConstraints.gridx = 0;
		layeredPane.add(gameGoal, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 8;
		gridConstraints.gridx = 2;
		layeredPane.add(p1GoalF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 8;
		gridConstraints.gridx = 4;
		layeredPane.add(p2GoalF, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 1;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 6;
		layeredPane.add(horiz1, gridConstraints, 0);
		clearGridConstraints();

		gridConstraints.gridy = 3;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 8;
		layeredPane.add(horiz2, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 5;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 8;
		layeredPane.add(horiz3, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 7;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 6;
		layeredPane.add(horiz4, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 9;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 6;
		layeredPane.add(horiz5, gridConstraints, 0);
		clearGridConstraints();		
		
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 1;
		gridConstraints.gridheight = 10;
		layeredPane.add(vert1, gridConstraints, 0);
		clearGridConstraints();

		gridConstraints.gridy = 0;
		gridConstraints.gridx = 3;
		gridConstraints.gridheight = 10;
		layeredPane.add(vert2, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 5;
		gridConstraints.gridheight = 10;
		layeredPane.add(vert3, gridConstraints, 0);
		clearGridConstraints();
		
		gridConstraints.gridy = 3;
		gridConstraints.gridx = 7;
		gridConstraints.gridheight = 3;
		layeredPane.add(vert4, gridConstraints, 0);
		clearGridConstraints();		
		
		addMouseListener(this);
		addComponentListener(this);
		setDoubleBuffered(true);
		add(layeredPane, BorderLayout.CENTER);		
		
		buttonPanel = new JPanel();
		restartButton = new JButton ("Restart");
		statsButton = new JButton ("Player Statistics");
		editButton = new JButton ("Edit Score");
		replayButton = new JButton ("Shot Replay");
		doneButton = new JButton ("Done");
		
		restartButton.addActionListener(this);
		statsButton.addActionListener(this);
		editButton.addActionListener(this);
		replayButton.addActionListener(this);
				
		restartButton.setActionCommand("restart");
		statsButton.setActionCommand("stats");
		editButton.setActionCommand("edit");
		replayButton.setActionCommand("replay");
		
		buttonPanel.add(restartButton);
		buttonPanel.add(statsButton);
		buttonPanel.add(editButton);
		buttonPanel.add(replayButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void clearGridConstraints() {
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.gridwidth = 1;
		gridConstraints.gridheight = 1;
		gridConstraints.fill = GridBagConstraints.BOTH;
		gridConstraints.ipadx = 0;
		gridConstraints.ipady = 0;
		gridConstraints.insets = new Insets(0,0,0,0);
		gridConstraints.anchor = GridBagConstraints.CENTER;
		gridConstraints.weightx = 0;
		gridConstraints.weighty = 0;	
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		
		if (!initSelectorPlacement) {
			selector_x = vert1.getX() + XFUDGE;
			selector_y = vert1.getY() + YFUDGE;
			selector_width = vert2.getX() - vert1.getX();
			selector_height = vert1.getHeight();		
			initSelectorPlacement = true;
		}
	}
	
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		
		if (showSelector) {
			Graphics2D g2 = (Graphics2D) g;
		
			int x = selector_x;
			int y = selector_y;
			int width = selector_width;
			int height = selector_height;
		
			rect2d = new Rectangle2D.Float(x, y, width, height);
			g2.setColor(Color.green);
			BasicStroke s = new BasicStroke(STROKE_WIDTH);
			g2.setStroke(s);
			g2.draw(rect2d);
	    
			int [] x_points = new int[3];
			int [] y_points = new int[3];
			x_points[0] = x + width/2;
			y_points[0] = y;
	    
			x_points[1] = (int)(x_points[0] - Math.sin(45) * TRIANGLE_LENGTH);
			y_points[1] = (int)(y_points[0] - Math.cos(45) * TRIANGLE_LENGTH);
			
			x_points[2] = (int)(x_points[0] + Math.sin(45) * TRIANGLE_LENGTH);
			y_points[2] = (int)(y_points[0] - Math.cos(45) * TRIANGLE_LENGTH);
			
			p = new Polygon(x_points, y_points, 3);
			g.fillPolygon(p);
			g2.draw(p);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed (" + e.getX() + "," + e.getY() + ")");
	}
	
	public void mouseClicked(MouseEvent e) { 
		Rectangle2D.Float rect = new Rectangle2D.Float(vert1.getX(), vert1.getY(), vert3.getX()-vert1.getX(), vert1.getHeight());
		if ((rect.contains(e.getX(), e.getY()) || p.contains(e.getX(), e.getY())) && showSelector) {
			System.out.println("Clicked on the turn selector");	
			runner = new Thread(this);
			runner.start();
		}
	}
	
	public void mouseEntered(MouseEvent e) { }
	
	public void mouseExited(MouseEvent e) { }

	public void mouseReleased(MouseEvent e) { }
	
	public void animateTurnSelector() {
		if (p1selected)
		{
			missp1++;
			if(maxrunp1<currunp1)
			{
				maxrunp1 = currunp1;
			}
			nbrunp1++;
			avgrunp1 = sunkp1/nbrunp1;
			currunp1 = 0;		
		}
		else
		{
			missp2++;
			if(maxrunp2<currunp2)
			{
				maxrunp2 = currunp2;
			}
			nbrunp2++;
			avgrunp2 = sunkp1/nbrunp2;
			currunp2 = 0;
		}
		runner = new Thread(this);
		runner.start();
	}
	
    public void ballSunk(int ball) {
    	if (p1selected) {
    		if (!virtualRack.ballAlreadySunk(ball)) {
    			//p1RackScore++;
    			//p1RackScoreF.setText(Integer.toString(p1RackScore));
    			sunkp1++;
    			currunp1++;
    			//p1TotalScore++;
    			//p1TotalScoreF.setText(Integer.toString(p1RackScore));
    			
    			virtualRack.removeBall(ball);
    			BufferedImage img = virtualRack.getBallImg(ball);    		
    			p1RackDetails.addBall(img, ball);    		
    			repaint();
    		}
    	} else {
    		if (!virtualRack.ballAlreadySunk(ball)) {
    			//p2RackScore++;
    			//p2RackScoreF.setText(Integer.toString(p2RackScore));
    			sunkp2++;
    			currunp2++;
    			//p2TotalScore++;
    			//p2TotalScoreF.setText(Integer.toString(p2TotalScore));
    			
    			virtualRack.removeBall(ball);
    			BufferedImage img = virtualRack.getBallImg(ball);    		
    			p2RackDetails.addBall(img, ball);      		
    			repaint();
    		}
    	}
    }
    
    public void clearRack() {
    	p1RackDetails.clearBalls();
    	p2RackDetails.clearBalls();
    	virtualRack.newRack();
    	
    	p1RackScore = 0;
		p1RackScoreF.setText(Integer.toString(p1RackScore));

    	p2RackScore = 0;
		p2RackScoreF.setText(Integer.toString(p2RackScore));
		repaint();

    }

    public void processFault() {
    	if (p1selected) {
    		faultp1++;
			if(maxrunp1<currunp1)
			{
				maxrunp1 = currunp1;
			}
			currunp1 = 0;
			nbrunp1++;
			avgrunp1 = sunkp1/nbrunp1;
    		p1RackScore -= 2;
    		p1TotalScore -= 2;
    		p1RackScoreF.setText(Integer.toString(p1RackScore));
    		p1TotalScoreF.setText(Integer.toString(p1TotalScore));
    		
    	} else {
    		faultp2++;
			if(maxrunp2<currunp2)
			{
				maxrunp2 = currunp2;
			}
			nbrunp2++;
			currunp2 = 0;
			avgrunp2 = sunkp1/nbrunp2;
    		p2RackScore -= 2;
    		p2TotalScore -= 2;
    		p2RackScoreF.setText(Integer.toString(p2RackScore));
    		p2TotalScoreF.setText(Integer.toString(p2TotalScore));
    	}
    	animateTurnSelector();
    }
    
    public void ballMissed() {
    	
    }
    
	public void run() {
		Thread thisThread = Thread.currentThread();
	
		if (p1selected) {
			while (selector_x <= vert2.getX() + XFUDGE) {
				selector_x += 1;
				repaint();
				
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				}
			}
			p1selected = false;
		} else {
			while (selector_x >= vert1.getX() + XFUDGE) {
				selector_x -= 1;
				repaint();
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				}
			}
			p1selected = true;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "restart") {
			
			Object[] options = {"No", "Yes"};			
			int n = JOptionPane.showOptionDialog(this,
					"Quit the current game? All game statistics and scores will be lost.",
					"Quit",					
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);		
			if (n == 1) { // really quit the game
				//TODO this should cause the system to display the login/setup menu system
				resetStats();
				resetScores();
				AutoPoolScorer.mainWindow.goSetup();
				
			}
		} else if (e.getActionCommand() == "replay") {
			
		} else if (e.getActionCommand() == "stats") {
			
			new StatsWindow();
			
		} else if (e.getActionCommand() == "edit") {
			editScore();
		}
	}
	
	private void resetScores() {
		p1TotalScore = 0;
		p2TotalScore = 0;
		p1RackDetails.clearBalls();
		p2RackDetails.clearBalls();
		p1RackScore = 0;
		p2RackScore = 0;
		p1WinsAt = 250;
		p2WinsAt = 250;
		p1Name = "";
		p2Name = "";
		
		p1TotalScoreF.setText(Integer.toString(p1TotalScore));
		p2TotalScoreF.setText(Integer.toString(p2TotalScore));
		p1RackScoreF.setText(Integer.toString(p1RackScore));
		p2RackScoreF.setText(Integer.toString(p2RackScore));
		p1GoalF.setText(Integer.toString(p1WinsAt));
		p2GoalF.setText(Integer.toString(p2WinsAt));
	}
	
	private void resetStats() {
		
		sunkp1 = 0;
		sunkp2 = 0;
		missp1 = 0;
		missp2 = 0;
		faultp1 = 0;
		faultp2 = 0;
		avgrunp1 = 0;
		avgrunp2 = 0;
		maxrunp1 = 0;
		maxrunp2 = 0;
	}

	public void componentHidden(ComponentEvent e) { }
    public void componentShown(ComponentEvent e) { }
    public void componentMoved(ComponentEvent e) { }

    public void componentResized(ComponentEvent e) {
    	if (p1selected) {
    		selector_x = vert1.getX() + XFUDGE;
    		selector_y = vert1.getY() + YFUDGE;
    	} else { //p2 selected
    		selector_x = vert2.getX() + XFUDGE;
    		selector_y = vert2.getY() + YFUDGE;
    	}
    }

    private void editScore() {
    	//System.out.println("edit score called");
    	showSelector = false;
    	repaint();
    	
    	/*
    	p1RackScoreF.setEditable(true);
    	p2RackScoreF.setEditable(true);
    	*/
    	
    	p1TotalScoreF.setEditable(true);
    	p2TotalScoreF.setEditable(true);
    	p1GoalF.setEditable(true);
    	p2GoalF.setEditable(true);
    	    	
    	replayButton.setEnabled(false);
    	statsButton.setEnabled(false);
    	
    	buttonPanel.remove(editButton);
    	buttonPanel.add(doneButton, 2);
    	buttonPanel.validate();
    	
    	allowRemoteCommands = false;
    	
    	doneButton.addActionListener(new ActionListener () {
    		public void actionPerformed(ActionEvent e) {
    			
    			/*
    			if (isValidNumber(p1RackScoreF.getText())) {
    				p1RackScore = Integer.parseInt(p1RackScoreF.getText());
    			} else {
    				p1RackScoreF.setText(Integer.toString(p1RackScore));
    				JOptionPane.showMessageDialog(null, "Error, " + p1Name + "'s rack score is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);
    			}
    			
    			if (isValidNumber(p2RackScoreF.getText())) {
    				p2RackScore = Integer.parseInt(p2RackScoreF.getText());
    			} else {
    				p2RackScoreF.setText(Integer.toString(p2RackScore));
    				JOptionPane.showMessageDialog(null, "Error, " + p2Name + "'s rack score is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);
    			}
    			*/
    			
    			if (isValidNumber(p1TotalScoreF.getText())) {
        			p1TotalScore = Integer.parseInt(p1TotalScoreF.getText());
    			} else {
    				p1TotalScoreF.setText(Integer.toString(p1TotalScore));
    				JOptionPane.showMessageDialog(null, "Error, " + p1Name + "'s total score is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);

    			}
    			
    			if (isValidNumber(p2TotalScoreF.getText())) {
    				p2TotalScore = Integer.parseInt(p2TotalScoreF.getText());
    			} else {
    				p2TotalScoreF.setText(Integer.toString(p2TotalScore));
    				JOptionPane.showMessageDialog(null, "Error, " + p2Name + "'s total score is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);

    			}
    			
    			if (isValidNumber(p1GoalF.getText())) {
    				p1WinsAt = Integer.parseInt(p1GoalF.getText());
    			} else {
    				p1GoalF.setText(Integer.toString(p1WinsAt));
    				JOptionPane.showMessageDialog(null, "Error, " + p1Name + "'s goal is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);
    			}

    			if (isValidNumber(p2GoalF.getText())) {
    				p2WinsAt = Integer.parseInt(p2GoalF.getText());
    			} else {
    				p2GoalF.setText(Integer.toString(p2WinsAt));
    				JOptionPane.showMessageDialog(null, "Error, " + p2Name + "'s goal is not a valid number.",
    						"User Edit Score Error", JOptionPane.ERROR_MESSAGE);
    			}

    			
    			
    	    	showSelector = true;
    	    	repaint();  
    	    	/*
    	    	p1RackScoreF.setEditable(false);
    	    	p2RackScoreF.setEditable(false);
    	    	*/
    	    	
    	    	p1TotalScoreF.setEditable(false);
    	    	p2TotalScoreF.setEditable(false);
    	    	p1GoalF.setEditable(false);
    	    	p2GoalF.setEditable(false);    	    	
    	    	replayButton.setEnabled(true);
    	    	statsButton.setEnabled(true);    	    	
    	    	buttonPanel.remove(doneButton);
    	    	buttonPanel.add(editButton, 2);
    	    	buttonPanel.validate();
    			
    		}
    	});    	
    }
    
    public boolean isValidNumber( String s ) {
    	boolean isNumber = false;
    	
    	try {
    		Integer.parseInt(s);
    		return true;
    	} catch (NumberFormatException e) {
    		return false;
    	}    	
    }
}
