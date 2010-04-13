import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
	
	private JFrame f;	
	private JPanel webcamPanel;
	
	protected ScoringWindow scoreWindow = null;
	private RemoteCommandInterceptor rmc = null;
	protected SetupWindow setupWindow = null;
	
	public MainWindow() {
		f = new JFrame("Automatic Straight Pool Scoring System");
		f.setLayout(new BorderLayout());
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		
		scoreWindow = new ScoringWindow();
		setupWindow = new SetupWindow();
		
		rmc = new RemoteCommandInterceptor(scoreWindow);
		
		goSetup();
	}
	
	public void goScore(){
		f.remove(setupWindow);
		f.add(scoreWindow, BorderLayout.CENTER);
		f.pack();	
		f.setSize(648,480);
		f.setVisible(true);
	}
	
	public void goSetup(){
		f.remove(scoreWindow);
		f.add(setupWindow, BorderLayout.CENTER);
		f.pack();		
		f.setSize(648,480);
		f.setVisible(true);
	}
	
}
