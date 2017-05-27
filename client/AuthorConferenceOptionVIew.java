package client;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Conference;
import model.User;

public class AuthorConferenceOptionVIew extends Observable {
	private Conference myConference;
	private User myUser;
	
	public AuthorConferenceOptionVIew(Conference theConference, User theUser) {
		myConference = theConference;
		myUser = theUser;
	}
	
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
		if (myConference.isUserAuthor(myUser)) {
			viewManuscriptButton.setEnabled(true);
		} else {
			viewManuscriptButton.setEnabled(false);
		}
		
		conferenceOptionButtonPanel.setOpaque(true);
		
		conferenceOptionButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		ConferenceOptionPanel.add(conferenceOptionButtonPanel);
		return ConferenceOptionPanel;
		
	}
}
