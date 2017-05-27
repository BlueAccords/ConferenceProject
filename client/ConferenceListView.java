package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.User;

public class ConferenceListView extends Observable {
	/**
	 * List of Conferences available to Author
	 */
	private ArrayList<Conference> myConferenceList;
	
	/**
	 * Counter to help iterate through Lists
	 */
	private int myCounter;
	
	private User myUser;
	
	public ConferenceListView(User theUser) {
		myUser = theUser;
		myConferenceList = new ArrayList<Conference>();
		myCounter = 0;
	}
	
	/**
	 * Extra Constructor that takes a Conference list as a parameter so it does
	 * not have to be passed through update.
	 * 
	 * @param theConferenceList Must be a non-null list of Conferences
	 * 
	 * @author Connor Lundberg
	 * @version 5/23/2017
	 */
	public ConferenceListView(ArrayList<Conference> theConferenceList, User theUser) {
		myUser = theUser;
		if (theConferenceList != null)	
			myConferenceList = theConferenceList;
		else
			myConferenceList = new ArrayList<Conference> ();
		myCounter = 0;
	}
	
	/**
	 * Method to create a view for Conference selection.
	 * @return JPanel displaying all the available Conferences to select.
	 * @author Casey Anderson
	 */
	public JPanel createConferenceListView() {
		JLabel conferenceNameLabel = new JLabel("Conference Name");
		JLabel isManuscriptSubmittedLabel = new JLabel("Manuscript Submited  ");
		JLabel isSubprogramChairLabel = new JLabel("Subprogram Chair Role  ");
		JLabel isRiewerLabel = new JLabel("Reviewer Role  ");
		JLabel conferenceDeadLineLabel = new JLabel("Conference Deadline");
		JPanel conferencePanel = new JPanel(new GridBagLayout());
		JPanel conferenceButtonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		conferenceButtonPanel.add(conferenceNameLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		conferenceButtonPanel.add(isManuscriptSubmittedLabel, c);
		c.gridx = 2;
		c.gridy = 0;
		conferenceButtonPanel.add(isSubprogramChairLabel, c);
		c.gridx = 3;
		c.gridy = 0;
		conferenceButtonPanel.add(isRiewerLabel, c);
		c.gridx = 4;
		c.gridy = 0;
		conferenceButtonPanel.add(conferenceDeadLineLabel, c);
		ButtonGroup group = new ButtonGroup();
		System.out.println("at the start");
		System.out.println(myConferenceList == null);
		for (myCounter = 0; myCounter < myConferenceList.size(); myCounter++) {
			System.out.println("in here");
			JButton button = new JButton(myConferenceList.get(myCounter).getConferenceName());
			button.setActionCommand(myConferenceList.get(myCounter).getConferenceName());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					
					// check index of button against index of conference in conference list
					int confIndex = getClickedConference(group, e);
					if(confIndex > -1) {
						setChanged();
						notifyObservers(myConferenceList.get(confIndex));
						setChanged();
						notifyObservers(Controller.AUTHOR + Controller.LIST_CONFERENCE_VIEW);
					} else {
						//TODO: Add better error handling here.
						System.out.println("Conference Not Found");
					}

				} 
		    }); 
			System.out.println("in the middle");
			group.add(button);
			c.gridx = 0;
			c.gridy = myCounter + 1;
			conferenceButtonPanel.add(button, c);
			c.gridx = 1;
			c.gridy = myCounter + 1;
			if (myConferenceList.get(myCounter).isUserAuthor(myUser)) {			
				conferenceButtonPanel.add(new JLabel("Yes"), c);
			} else {
				conferenceButtonPanel.add(new JLabel("No"), c);
			}
			c.gridx = 2;
			c.gridy = myCounter + 1;
			if (myConferenceList.get(myCounter).isUserSubprogramChair(myUser)) {
				conferenceButtonPanel.add(new JLabel("Yes"), c);
			} else {
				conferenceButtonPanel.add(new JLabel("No"), c);
			}
			c.gridx = 3;
			c.gridy = myCounter + 1;
			if (myConferenceList.get(myCounter).isUserReviewer(myUser)) {
				conferenceButtonPanel.add(new JLabel("Yes"), c);
			} else {
				conferenceButtonPanel.add(new JLabel("No"), c);
			}
			c.gridx = 4;
			c.gridy = myCounter + 1;
			conferenceButtonPanel.add(new JLabel("" + myConferenceList.get(myCounter).getManuscriptDeadline()), c);
			
		}
		System.out.println("At the end");
		conferenceButtonPanel.setOpaque(true);
		
		conferenceButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference List"));
		conferencePanel.add(conferenceButtonPanel);
		//conferencePanel.setBackground(Color.WHITE);
		return conferencePanel;
		
	}
	
	/**
	 * This method will return the index conference linked to the button clicked
	 * by iterating through the button group, and seeing which button was pressed and
	 * finding the same conference in the conference list with that index.
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
	 * @param theBtnGroup the button group containing the button that was pressed
	 * @param theAction The action, we are comparing the action's parent button to the button group
	 * @return the index of the conference object that is linked to the pressed button
	 */
	private int getClickedConference(ButtonGroup theBtnGroup, ActionEvent theAction) {
		int i = 0;
		boolean confFound = false;

		Enumeration groupElements = theBtnGroup.getElements();
		while (groupElements.hasMoreElements()) {
			AbstractButton originalGroupBtn = (AbstractButton)groupElements.nextElement();
			JButton actionSrcBtn = ((JButton)theAction.getSource());
			
			if(originalGroupBtn.equals(actionSrcBtn)) {
				confFound = true;
				break;
			}
			
			i++;
		}
		
		if(confFound) {
			return i;
		} else {
			return -1;
		}
	}
}
