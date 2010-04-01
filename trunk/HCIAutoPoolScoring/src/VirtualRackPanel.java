import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class VirtualRackPanel extends JPanel implements MouseListener, MouseMotionListener {

	private JPanel panel = null;

	private int panel_width;
	private int panel_height;

	private int ball_width;
	private int ball_height;
	
	private final int SCALED_DIMENSIONS = 25;

	private boolean [] ball_displayed;
	private BufferedImage[] ball_images;
	private Point[] ball_points;
	
	private int ballDragging = 0;
	private int dragX=0;
	private int dragY=0;

	public VirtualRackPanel(int width, int height) {
		panel_width = width;
		panel_height = height;
		ball_images = new BufferedImage[16];
		ball_width = -1;
		ball_height = -1;
		ball_points = new Point[16];
		ball_displayed = new boolean[16];
		
		for( int i = 0; i <= 15; i++) {
			ball_images[i] = null;
			ball_points[i] = null;
			ball_displayed[i] = true;
		}
		
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(panel_width, panel_height));
		addMouseListener(this);
		addMouseMotionListener(this);
		
		loadBalls();
	}
	
	public void loadBalls() {		
	
		for (int i = 1; i <= 15; i++) {
			String imageSrc = null;
			try {
				imageSrc = "images" + File.separator + "Ball";
				if (i < 10)
					imageSrc += "0";
				imageSrc += Integer.toString(i) + ".png";
				
				BufferedImage img = ImageIO.read(new FileInputStream(imageSrc));
				
				if (ball_width == -1) {
					ball_width = img.getWidth();
				} else if (ball_width != img.getWidth()) {
					throw new Exception("All ball images must have the same dimension(width) " + imageSrc);
				}
				
				if (ball_height == -1) {
					ball_height = img.getHeight();
				} else if (ball_height != img.getHeight()) {
					throw new Exception("All ball images must have the same dimension(height) " + imageSrc);
				}
				
				ball_images[i] = img;
				
			} catch (IOException e) {
				System.out.println("Error reading file " + imageSrc);
			} catch (Exception e) {
				//die painfully
			}
		}
	}
	
	public BufferedImage getBallImg(int i) {
		if (i == 0) {
			System.out.println("Cannot retreive image for ball 0 (it doesn't exist)");
		}
		return ball_images[i];
	}
	
	private void calculateBallPositions() {
		
		int width = getWidth();
		int height = getHeight();
		Point middlePoint = new Point(width/2, height/2);		
		
		ball_points[5] = new Point(middlePoint.x, middlePoint.y - SCALED_DIMENSIONS/4);
		ball_points[9] = bottomRightOf(ball_points[5]);
		ball_points[8] = bottomLeftOf(ball_points[5]);
		ball_points[2] = topLeftOf(ball_points[5]);
		ball_points[3] = topRightOf(ball_points[5]);
		ball_points[1] = topRightOf(ball_points[2]);
		ball_points[4] = bottomLeftOf(ball_points[2]);
		ball_points[6] = bottomRightOf(ball_points[3]);
		ball_points[7] = bottomLeftOf(ball_points[4]);
		ball_points[8] = bottomRightOf(ball_points[4]);
		ball_points[10] = bottomRightOf(ball_points[6]);
		ball_points[11] = bottomLeftOf(ball_points[7]);
		ball_points[12] = bottomRightOf(ball_points[7]);
		ball_points[13] = bottomRightOf(ball_points[8]);
		ball_points[14] = bottomRightOf(ball_points[9]);
		ball_points[15] = bottomRightOf(ball_points[10]);
				
		// now that all the points have been calculated, update positions to be relative
		// to the middle of the image
		for (int i = 1; i <= 15; i++) {
			if (ball_points[i] != null) {
				offsetImg(ball_points[i]);
			}
		}	
	}
		
	private Point topLeftOf(Point p) {
		double dy = Math.cos(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		double dx = Math.sin(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		Point np = new Point(p.x - (int)dx, p.y - (int)dy);	
		return np;
	}
	
	private Point bottomLeftOf(Point p) {
		double dy = Math.cos(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		double dx = Math.sin(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		Point np = new Point(p.x - (int)dx, p.y + (int)dy);	
		return np;

	}
	private Point topRightOf(Point p) {
		double dy = Math.cos(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		double dx = Math.sin(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		Point np = new Point(p.x + (int)dx, p.y - (int)dy);	
		return np;
	}
	
	private Point bottomRightOf(Point p) {
		double dy = Math.cos(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		double dx = Math.sin(30 * Math.PI / 180) * SCALED_DIMENSIONS;
		Point np = new Point(p.x + (int)dx, p.y + (int)dy);	
		return np;
	}
	
	private void offsetImg(Point p) {		
		p.translate(-SCALED_DIMENSIONS/2, -SCALED_DIMENSIONS/2);
	}
	
	public void removeBall(int i) {
		if (i > 15 || i < 1) {
			System.out.println("Balls must be between 1-15");
		}
		ball_displayed[i] = false;
		repaint();
	}
	
	public boolean ballAlreadySunk(int ball) {
		return !ball_displayed[ball];
	}
	
	public void newRack() {
		for (int i = 1; i <= 15; i++) {
			ball_displayed[i] = true;
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		calculateBallPositions();
		for (int i = 1; i <= 15; i++) {
			if (ball_points[i] != null && ball_displayed[i]) {
				Point p = ball_points[i];
				if(ballDragging == i){
					// draw later
				}else{
					g.drawImage(ball_images[i], p.x, p.y, p.x + SCALED_DIMENSIONS, p.y + SCALED_DIMENSIONS, 
							0, 0, ball_width, ball_height, null);
				}
			}
		}
		
		if(ballDragging > 0){
			Point p = ball_points[ballDragging];
			System.out.println("dragging " + ballDragging);
			p.x = dragX-SCALED_DIMENSIONS/2;
			p.y = dragY-SCALED_DIMENSIONS/2;
			g.drawImage(ball_images[ballDragging], p.x, p.y, p.x + SCALED_DIMENSIONS, p.y + SCALED_DIMENSIONS, 
					0, 0, ball_width, ball_height, null);
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed (" + e.getX() + "," + e.getY() + ")");
		
		int selected = ballAt(e.getX(), e.getY());
		if(selected>0){
			System.out.println("selected: " + selected);
			ballDragging = selected;
			repaint();
			dragX = e.getX();
			dragY = e.getY();
		}
		
	}
	
	private int ballAt(int x, int y){
		
		for (int i = 1; i <= 15; i++) {
			int xDif = x-(ball_points[i].x+SCALED_DIMENSIONS/2);
			int yDif = y-(ball_points[i].y+SCALED_DIMENSIONS/2);
			
			double dist = Math.sqrt(xDif*xDif + yDif*yDif);
			if(dist < SCALED_DIMENSIONS/2){
				return i;
			}
		}
		
		return 0;
	}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse released (" + e.getX() + "," + e.getY() + ")");
		
		ballDragging = 0;
		repaint();
	}
	
	public void mouseEntered(MouseEvent e) { }
	
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseDragged (" + e.getX() + "," + e.getY() + ")");
		dragX = e.getX();
		dragY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mouseMoved (" + e.getX() + "," + e.getY() + ")");
	}
}
