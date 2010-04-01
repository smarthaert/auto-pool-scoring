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
	
	private Vector ball_images;
	private Vector ball_numbers;
	
	private final int MAX_BALLS_PER_COLUMN = 4; 
	private final int SCALED_DIMENSIONS = 25;
	
	
	public PlayerRackPanel(int width, int height) {
		panel_width = width;
		panel_height = height;
		setPreferredSize(new Dimension(panel_width, panel_height));
		ball_images = new Vector();
		ball_numbers = new Vector();
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void addBall( BufferedImage image, int i ) {
		ball_images.add(image);
		ball_numbers.add(i);
		repaint();
	}	
	
	public void clearBalls() {
		ball_images.clear();
		ball_numbers.clear();
		repaint();
	}
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		
		final int PADDING = 5;		
		
		int x,y;
		for (int i = 0; i < ball_images.size(); i++) {
			BufferedImage img = (BufferedImage)ball_images.elementAt(i);
			x = (i / MAX_BALLS_PER_COLUMN) * (SCALED_DIMENSIONS + PADDING) + PADDING;
			y = (i % MAX_BALLS_PER_COLUMN) * (SCALED_DIMENSIONS + PADDING) + PADDING;
			
			g.drawImage(img, x, y, x + SCALED_DIMENSIONS, y + SCALED_DIMENSIONS,
					0, 0, img.getWidth(), img.getHeight(), null);												
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouse clicked (" + e.getX() + "," + e.getY() + ")");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("plrRack enter");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("virRack exit");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed (" + e.getX() + "," + e.getY() + ")");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse released (" + e.getX() + "," + e.getY() + ")");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("plrRack mouseDragged (" + e.getX() + "," + e.getY() + ")");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("plrRack mouseMoved (" + e.getX() + "," + e.getY() + ")");
	}	
}
