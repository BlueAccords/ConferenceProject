package client;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ConferenceOptionVIew extends Observable {
	
	/**
	 * Method to create a view for the options available for Conferences.
	 * @return JPanel for displaying all the available Conference options for selection.
	 * @author Casey Anderson
	 */
	public JPanel createConferenceOptions() {
		
		JPanel conferenceOptionButtonPanel = new JPanel(new GridLayout(0,1));		
		JPanel ConferenceOptionPanel = new JPanel(new GridBagLayout());
		JButton submitButton = new JButton("Submit Manuscript");
		submitButton.setActionCommand("Submit Manuscript");
		
		submitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
		        notifyObservers(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);  
		    }  
		});
		
		conferenceOptionButtonPanel.add(submitButton);
		JButton viewManuscriptButton = new JButton("View Manuscripts");
		viewManuscriptButton.setActionCommand("View Manuscripts");
		
		viewManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers(Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);  
		    }  
		});
		
		conferenceOptionButtonPanel.add(viewManuscriptButton);		
		conferenceOptionButtonPanel.setOpaque(true);
		
		conferenceOptionButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		ConferenceOptionPanel.add(conferenceOptionButtonPanel);
		return ConferenceOptionPanel;
		
	}
}
