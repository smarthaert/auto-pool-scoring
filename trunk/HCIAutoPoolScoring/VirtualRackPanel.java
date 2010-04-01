import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class VirtualRackPanel extends JPanel {

	private JPanel panel = null;

	private int panel_width;
	private int panel_height;

	private int w, h;
	private BufferedImage ball_images;

	private final int BALL_DIM = 30;

	public VirtualRackPanel(int width, int height) {
		panel_width = width;
		panel_height = height;
		//ball_images = new BufferedImage[15];
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(panel_width, panel_height));
	}

	private void loadBI(String imageSrc) {
		try {
			ball_images= ImageIO.read(new FileInputStream(imageSrc));
			w = (ball_images).getWidth(null);
			h = ball_images.getHeight(null);
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}
	}

	//TODO come back and make this look better...
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x_start, y_start;
		// int OFFSET = 24;
		int X_OFFSET = 34;
		int Y_OFFSET = 24;

		// head of the rack
		x_start = getWidth() / 2;
		y_start = 0;
		loadBI("images/Ball01.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		// row 2
		x_start -= (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		y_start += (int) (0.5 * (BALL_DIM + Y_OFFSET));
		loadBI("images/Ball02.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start += 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball03.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		// row 3
		x_start += (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		y_start += (int) (0.5 * (BALL_DIM + Y_OFFSET));
		loadBI("images/Ball05.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball08.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball04.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		// row 4
		x_start -= (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		y_start += (int) (0.5 * (BALL_DIM + Y_OFFSET));
		loadBI("images/Ball06.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start += 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball07.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start += 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball09.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start += 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball10.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		// row 5
		x_start += (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		y_start += (int) (0.5 * (BALL_DIM + Y_OFFSET));
		loadBI("images/Ball15.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball14.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball13.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball12.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);

		x_start -= 2 * (int) (0.5 * (BALL_DIM + X_OFFSET) * Math.atan(Math.PI / 6));
		loadBI("images/Ball11.png");
		g.drawImage(ball_images, x_start, y_start, x_start + BALL_DIM, y_start + BALL_DIM, 0, 0, w, h, null);
	}
}
