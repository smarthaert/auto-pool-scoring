import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class StatsWindow extends JDialog
{
	private JButton backButton = null;
	
	
	public StatsWindow(){
		setTitle("Stats");

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridConstraints = new GridBagConstraints();
		setLayout(gridBagLayout);
		
		gridConstraints.weightx = 1;
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 2;
		add(new JLabel(AutoPoolScorer.mainWindow.scoreWindow.p1Name, JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(AutoPoolScorer.mainWindow.scoreWindow.p2Name, JLabel.LEFT), gridConstraints);

		gridConstraints.gridy = 1;
		gridConstraints.gridx = 0;
		add(new JLabel("Total balls sunk", JLabel.RIGHT), gridConstraints);
		gridConstraints.gridx = 2;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.sunkp1), JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.sunkp2), JLabel.LEFT), gridConstraints);

		gridConstraints.gridy = 2;
		gridConstraints.gridx = 0;
		add(new JLabel("Total miss", JLabel.RIGHT), gridConstraints);
		gridConstraints.gridx = 2;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.missp1), JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.missp2), JLabel.LEFT), gridConstraints);

		gridConstraints.gridy = 3;
		gridConstraints.gridx = 0;
		add(new JLabel("Total fault", JLabel.RIGHT), gridConstraints);
		gridConstraints.gridx = 2;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.faultp1), JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.faultp2), JLabel.LEFT), gridConstraints);
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 0;
		add(new JLabel("Average run", JLabel.RIGHT), gridConstraints);
		gridConstraints.gridx = 2;
		add(new JLabel(Float.toString(AutoPoolScorer.mainWindow.scoreWindow.avgrunp1), JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(Float.toString(AutoPoolScorer.mainWindow.scoreWindow.avgrunp2), JLabel.LEFT), gridConstraints);

		gridConstraints.gridy = 5;
		gridConstraints.gridx = 0;
		add(new JLabel("Max run", JLabel.RIGHT), gridConstraints);
		gridConstraints.gridx = 2;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.maxrunp1), JLabel.LEFT), gridConstraints);
		gridConstraints.gridx = 4;
		add(new JLabel(Integer.toString(AutoPoolScorer.mainWindow.scoreWindow.maxrunp2), JLabel.LEFT), gridConstraints);
		
		clearGridConstraints(gridConstraints);
		gridConstraints.weightx = 0.1;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 5;
		gridConstraints.gridx = 1;
		add(new JSeparator(JSeparator.VERTICAL));
		gridConstraints.gridx = 3;
		add(new JSeparator(JSeparator.VERTICAL));

		clearGridConstraints(gridConstraints);	
		
		
		gridConstraints.gridy = 6;
		gridConstraints.gridx = 1;
		gridConstraints.fill = GridBagConstraints.CENTER;
		gridConstraints.weighty = 1.0;   //request any extra vertical space
		gridConstraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
		add(backButton = new JButton("Back"), gridConstraints);
		clearGridConstraints(gridConstraints);

		//add(layeredPane);
		
		
		backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

		setModal(true);
        setSize(300, 200);
		setPreferredSize(new Dimension(500,200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

	}
	
	private void clearGridConstraints(GridBagConstraints gridConstraints) {
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

}
