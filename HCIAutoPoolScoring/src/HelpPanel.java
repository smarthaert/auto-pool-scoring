import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class HelpPanel extends JPanel {

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.BOLD, 13);
		g2.setFont(font);
		g2.drawString("Help test", 0, 10);
		drawArrow(g2, 60 , 10, 80, 15, -1);
		// g.fillOval(20, 20, 75, 75);
	}

	void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x, int y,
			double stroke) {
		double aDir = Math.atan2(xCenter - x, yCenter - y);
		g2d.drawLine(x, y, xCenter, yCenter);
		g2d.setStroke(new BasicStroke(1f)); // make the arrow head solid even if
											// dash pattern has been specified
		Polygon tmpPoly = new Polygon();
		int i1 = 12 + (int) (stroke * 2);
		int i2 = 6 + (int) stroke; // make the arrow head the same size
									// regardless of the length length
		tmpPoly.addPoint(x, y); // arrow tip
		tmpPoly.addPoint(x + xCor(i1, aDir + .5), y + yCor(i1, aDir + .5));
		tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
		tmpPoly.addPoint(x + xCor(i1, aDir - .5), y + yCor(i1, aDir - .5));
		tmpPoly.addPoint(x, y); // arrow tip
		g2d.drawPolygon(tmpPoly);
		g2d.fillPolygon(tmpPoly); // remove this line to leave arrow head
									// unpainted
	}

	int yCor(int len, double dir) {
		return (int) (len * Math.cos(dir));
	}

	int xCor(int len, double dir) {
		return (int) (len * Math.sin(dir));
	}

}
