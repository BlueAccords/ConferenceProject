package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class UI_Author_Conference_View extends JPanel implements ActionListener{
	
	  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -703649288417940022L;
	
	public UI_Author() {
		super();
		setupActions();
	
	}
	
	private void setupActions() {
		setBackground(Color.WHITE);
		setOpaque(true);
	}
	
	private void createConferenceOptions() {
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < size; i++) {
			JRadioButton button = new JRadioButton(text);
			button.setActionCommand(text);
			button.addActionListener(this);
			group.add(button);
			radioPanel.add(button);
			
		}
		
		add(radioPanel, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
