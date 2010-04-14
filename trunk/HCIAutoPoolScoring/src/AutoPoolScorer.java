import javax.swing.*;

public class AutoPoolScorer {

	/**
	 * @param args
	 */
	static MainWindow mainWindow = null;
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainWindow = new MainWindow();
			}
		});
	}
}
