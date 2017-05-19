package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	
	private void createConferenceList() {
		JPanel conferenceListPanel = new JPanel(new GridLayout(0,1));
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
			conferenceListPanel.add(button);
			
		}
		conferenceListPanel.setOpaque(true);
		conferenceListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference List"));
		add(conferenceListPanel, BorderLayout.CENTER);
		
	}
	
	private void createConferenceOptions() {
		JPanel conferenceOptionPanel = new JPanel(new GridLayout(0,1));
		
		JButton submitButton = new JButton("Submit Manuscript");
		submitButton.setActionCommand("Submit Manuscript");
		submitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
		        update("Submit Manuscript");  
		    }  
		}); 
		conferenceOptionPanel.add(submitButton);
		
		JButton viewManuscriptButton = new JButton("View Manuscripts");
		viewManuscriptButton.setActionCommand("View Manuscripts");
		viewManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
		        update("View Manuscripts");  
		    }  
		}); 
		conferenceOptionPanel.add(viewManuscriptButton);
		
		conferenceOptionPanel.setOpaque(true);
		conferenceOptionPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		add(conferenceOptionPanel, BorderLayout.CENTER);
		
	}
	
	private void createManuscriptOptions() {
		JPanel manuscriptOptionPanel = new JPanel(new GridLayout(0,1));
		
		JButton submitButton = new JButton("Submit Manuscript");
		submitButton.setActionCommand("Submit Manuscript");
		submitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
		        update("Submit Manuscript");  
		    }  
		}); 
		manuscriptOptionPanel.add(submitButton);
		
		JButton viewManuscriptButton = new JButton("View Manuscripts");
		viewManuscriptButton.setActionCommand("View Manuscripts");
		viewManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
		        update("View Manuscripts");  
		    }  
		}); 
		manuscriptOptionPanel.add(viewManuscriptButton);
		
		manuscriptOptionPanel.setOpaque(true);
		manuscriptOptionPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		add(manuscriptOptionPanel, BorderLayout.CENTER);
		
	}
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
