import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SetupWindow extends JPanel 
implements MouseListener, Runnable, ActionListener, ComponentListener{

	private JLayeredPane layeredPane = null;
	private JLabel enterNames = null;
	private JLabel player1lbl = null;
	private JLabel player2lbl = null;
	private JTextField player1name = null;
	private JTextField player2name = null;
	private JButton continueButton = null;
	
	
	public SetupWindow(){
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(500,200));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridConstraints = new GridBagConstraints();
		layeredPane.setLayout(gridBagLayout);
		
		
		JSeparator horiz1 = new JSeparator(JSeparator.HORIZONTAL);

		
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 2;
		layeredPane.add(enterNames = new JLabel("Enter Player Names", JLabel.LEFT), gridConstraints);
		clearGridConstraints(gridConstraints);

		gridConstraints.gridy = 1;
		gridConstraints.gridx = 0;
		gridConstraints.gridwidth = 4;
		layeredPane.add(horiz1, gridConstraints, 0);
		clearGridConstraints(gridConstraints);
		
		gridConstraints.gridy = 2;
		gridConstraints.gridx = 0;
		layeredPane.add(player1lbl = new JLabel("Player One", JLabel.LEFT), gridConstraints);
		clearGridConstraints(gridConstraints);
		
		gridConstraints.gridy = 2;
		gridConstraints.gridx = 3;
		layeredPane.add(player2lbl = new JLabel("Player Two", JLabel.LEFT), gridConstraints);
		clearGridConstraints(gridConstraints);
		
		gridConstraints.gridy = 3;
		gridConstraints.gridx = 0;
		layeredPane.add(player1name = new JTextField(""), gridConstraints);
		clearGridConstraints(gridConstraints);
		
		gridConstraints.gridy = 3;
		gridConstraints.gridx = 3;
		layeredPane.add(player2name = new JTextField(""), gridConstraints);
		clearGridConstraints(gridConstraints);
		
		
		gridConstraints.gridy = 4;
		gridConstraints.gridx = 2;
		gridConstraints.fill = GridBagConstraints.CENTER;
		gridConstraints.weighty = 1.0;   //request any extra vertical space
		gridConstraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
		layeredPane.add(continueButton = new JButton("Continue"), gridConstraints);
		clearGridConstraints(gridConstraints);
		

		continueButton.addActionListener(this);
		
		continueButton.setActionCommand("continue");
		
		addMouseListener(this);
		addComponentListener(this);
		setDoubleBuffered(true);
		add(layeredPane, BorderLayout.CENTER);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("mouse entered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("mouse exited");
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
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "continue") {
			System.out.println("continue");
			AutoPoolScorer.mainWindow.goScore();
			
		} else if (e.getActionCommand() == "quit") {
			System.out.println("quit");
			
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
