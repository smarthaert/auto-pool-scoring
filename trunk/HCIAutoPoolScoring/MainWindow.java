import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
	
	private JFrame f;	
	private JPanel webcamPanel;
	
	private ScoringWindow scoreWindow = null;
	private RemoteCommandInterceptor rmc = null;
	
	public MainWindow() {

		f = new JFrame("Automatic Straight Pool Scoring System");
		f.setLayout(new BorderLayout());
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		
		scoreWindow = new ScoringWindow();		
		rmc = new RemoteCommandInterceptor(scoreWindow);
		
		f.add(scoreWindow, BorderLayout.CENTER);		
		
		//webcamPanel = new SwingCapture();
		//f.add(webcamPanel, BorderLayout.SOUTH);
		
		f.pack();		
		f.setVisible(true);
		
	}	
}
