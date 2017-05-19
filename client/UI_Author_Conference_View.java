package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
		JPanel buttonPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < size; i++) {
			JButton button = new JButton(text);
			button.setActionCommand(text);
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
		            update(text);  
		        }  
		    }); 
			group.add(button);
			buttonPanel.add(button);
			
		}
		buttonPanel.setOpaque(true);
		add(buttonPanel, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
