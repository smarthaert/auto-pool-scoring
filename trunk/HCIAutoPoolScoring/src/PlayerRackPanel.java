import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.awt.*;
import java.util.*;

public class PlayerRackPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private int panel_width;
	private int panel_height;
	
	protected final int plrID;
	
	private Vector ball_images;
	private Vector ball_numbers;
	private Vector ball_points;
	
	private final int MAX_BALLS_PER_COLUMN = 4; 
	private final int SCALED_DIMENSIONS = 25;
	
	protected int ballDragging = 0;
	protected int dragX = 0;
	protected int dragY = 0;
	
	protected boolean p1clicked;
	
	public PlayerRackPanel(int width, int height, int plrid) {
		panel_width = width;
		panel_height = height;
		plrID = plrid;
		
		setPreferredSize(new Dimension(panel_width, panel_height));
		ball_images = new Vector();
		ball_numbers = new Vector();
		ball_points = new Vector();
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void addBall( BufferedImage image, int i ) {
		ball_images.add(image);
		ball_numbers.add(new Integer(i));
		
		if(plrID == 1){
			AutoPoolScorer.mainWindow.scoreWindow.p1RackScore++;
			AutoPoolScorer.mainWindow.scoreWindow.p1RackScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1RackScore));
			AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore++;
			AutoPoolScorer.mainWindow.scoreWindow.p1TotalScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore));
			AutoPoolScorer.mainWindow.scoreWindow.p1TotalLabel.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore));
		}else if(plrID == 2){
			AutoPoolScorer.mainWindow.scoreWindow.p2RackScore++;
			AutoPoolScorer.mainWindow.scoreWindow.p2RackScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2RackScore));
			AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore++;
			AutoPoolScorer.mainWindow.scoreWindow.p2TotalScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore));
			AutoPoolScorer.mainWindow.scoreWindow.p2TotalLabel.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore));
		}
		//repaint();
		AutoPoolScorer.mainWindow.scoreWindow.repaint();
	}	
	
	
	public void removeBall(int remove_num) {
		for( int i = 0; i < ball_numbers.size(); i++ ) {
			int num = ((Integer)ball_numbers.elementAt(i)).intValue();
			if (num == remove_num) {
				ball_numbers.remove(i);
				ball_images.remove(i);
				ball_points.remove(i);
				
				if(plrID == 1){
					AutoPoolScorer.mainWindow.scoreWindow.p1RackScore--;
					AutoPoolScorer.mainWindow.scoreWindow.p1RackScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1RackScore));
					AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore--;
					AutoPoolScorer.mainWindow.scoreWindow.p1TotalScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore));
					AutoPoolScorer.mainWindow.scoreWindow.p1TotalLabel.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p1TotalScore));

				}else if(plrID == 2){
					AutoPoolScorer.mainWindow.scoreWindow.p2RackScore--;
					AutoPoolScorer.mainWindow.scoreWindow.p2RackScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2RackScore));
					AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore--;
					AutoPoolScorer.mainWindow.scoreWindow.p2TotalScoreF.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore));
					AutoPoolScorer.mainWindow.scoreWindow.p2TotalLabel.setText(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.p2TotalScore));
				}

			}
		}
	}
	
	public void clearBalls() {
		ball_images.clear();
		ball_numbers.clear();
		//repaint();
		AutoPoolScorer.mainWindow.scoreWindow.repaint();
	}
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		
		final int PADDING = 5;		
		
		ball_points.clear();
		int x,y;
		for (int i = 0; i < ball_images.size(); i++) {
			BufferedImage img = (BufferedImage)ball_images.elementAt(i);
			x = (i / MAX_BALLS_PER_COLUMN) * (SCALED_DIMENSIONS + PADDING) + PADDING;
			y = (i % MAX_BALLS_PER_COLUMN) * (SCALED_DIMENSIONS + PADDING) + PADDING;
			
			if(((Integer)ball_numbers.elementAt(i)).intValue() != ballDragging){
				g.drawImage(img, x, y, x + SCALED_DIMENSIONS, y + SCALED_DIMENSIONS, 0, 0, img.getWidth(), img.getHeight(), null);
			}
			
			ball_points.add(new Point(x, y));
		}
		
		if(ballDragging > 0){
			BufferedImage img = AutoPoolScorer.mainWindow.scoreWindow.virtualRack.getBallImg(ballDragging);
			
			x = dragX-SCALED_DIMENSIONS/2;
			y = dragY-SCALED_DIMENSIONS/2;
			
			g.drawImage(img, x, y, x + SCALED_DIMENSIONS, y + SCALED_DIMENSIONS,
					0, 0, img.getWidth(), img.getHeight(), null);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouse clicked (" + e.getX() + "," + e.getY() + ")");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("plrRack enter");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("virRack exit");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("virRack mouse pressed (" + e.getX() + "," + e.getY() + ")");
				
		int ballNum = ballAt(e.getX(), e.getY());
		if(ballNum>0){			
			ballDragging = ballNum;
			dragX = e.getX();
			dragY = e.getY();
			
			if (e.getComponent() == AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails ) {
				//System.out.println("Player 1 detailed rack panel clicked.");
				p1clicked = true;
			} else {
				//System.out.println("Player 2 detailed rack panel clicked.");
				p1clicked = false;
			}

			//repaint();
			AutoPoolScorer.mainWindow.scoreWindow.repaint();
		}
	}
	
	public int ballAt(int x, int y) {
		for(int i = 0; i < ball_images.size(); i++) {
			Point p = (Point)ball_points.elementAt(i);
			Rectangle r = new Rectangle(p.x, p.y, SCALED_DIMENSIONS, SCALED_DIMENSIONS);
			if (r.contains(new Point(x,y))) {
				return ((Integer)ball_numbers.elementAt(i)).intValue();
			}
		}		
		return -1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("mouse released (" + e.getX() + "," + e.getY() + ")");

		/*
		if (e.getComponent() == AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails ) {
			System.out.println("Player 1 detailed rack panel mouse released.");
		} else {
			System.out.println("Player 2 detailed rack panel mouse released.");
		}
		*/
		
		if (ballDragging > 0) {
			if (p1clicked) {
				int dropPanel =  dragX / (AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				//System.out.println("dropPanel " + dropPanel );
				if (dropPanel <= 0) {
					//dropped on the same person...do nothing
				} else if (dropPanel == 1) {
					//dropped on player 2
					AutoPoolScorer.mainWindow.scoreWindow.sunkp1--;
					AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.removeBall(ballDragging);
					BufferedImage img = AutoPoolScorer.mainWindow.scoreWindow.virtualRack.getBallImg(ballDragging);
					AutoPoolScorer.mainWindow.scoreWindow.sunkp2++;
					AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.addBall(img, ballDragging);
				} else if (dropPanel == 2) {
					//dropped on the virtual rack
					AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.removeBall(ballDragging);
					AutoPoolScorer.mainWindow.scoreWindow.sunkp1--;
					AutoPoolScorer.mainWindow.scoreWindow.virtualRack.addBall(ballDragging);
				}				
			} else {
				int dropPanel =  dragX / (AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				//System.out.println("dropPanel " + dropPanel );
				//System.out.println("dragX " + dragX);
				
				if (dragX < 0) { 
					//dropped on player 1
					AutoPoolScorer.mainWindow.scoreWindow.sunkp2--;
					AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.removeBall(ballDragging);
					BufferedImage img = AutoPoolScorer.mainWindow.scoreWindow.virtualRack.getBallImg(ballDragging);
					AutoPoolScorer.mainWindow.scoreWindow.sunkp1++;
					AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.addBall(img, ballDragging);
				} else if (dropPanel == 1 ) {
					//dropped on the virtual rack
					AutoPoolScorer.mainWindow.scoreWindow.sunkp2--;
					AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.removeBall(ballDragging);
					BufferedImage img = AutoPoolScorer.mainWindow.scoreWindow.virtualRack.getBallImg(ballDragging);
					AutoPoolScorer.mainWindow.scoreWindow.virtualRack.addBall(ballDragging);
				}
			}
			AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.ballDragging = 0;
			AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.ballDragging = 0;
			AutoPoolScorer.mainWindow.scoreWindow.virtualRack.ballDragging = 0;
			AutoPoolScorer.mainWindow.scoreWindow.repaint();
		}
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("plrRack mouseDragged (" + e.getX() + "," + e.getY() + ")");
		
		dragX = e.getX();
		dragY = e.getY();
		
		if (ballDragging > 0) {
			if (p1clicked) {
				AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.ballDragging = ballDragging;
				AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.dragX = dragX - 1*(AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				AutoPoolScorer.mainWindow.scoreWindow.p2RackDetails.dragY = dragY;
				
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.ballDragging = ballDragging;
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.dragX = dragX - 2*(AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.dragY = dragY;
				
			} else {
				AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.ballDragging = ballDragging;
				AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.dragX = dragX + 1*(AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				AutoPoolScorer.mainWindow.scoreWindow.p1RackDetails.dragY = dragY;
			
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.ballDragging = ballDragging;
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.dragX = dragX - 1*(AutoPoolScorer.mainWindow.scoreWindow.VIRTUAL_RACK_WIDTH+2);
				AutoPoolScorer.mainWindow.scoreWindow.virtualRack.dragY = dragY;

			}
			AutoPoolScorer.mainWindow.scoreWindow.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("plrRack mouseMoved (" + e.getX() + "," + e.getY() + ")");
	}	
}
