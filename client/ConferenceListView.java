package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Observable;
import java.util.TimeZone;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
		JPanel conferencePanel = new JPanel(new BorderLayout());
		JPanel conferenceButtonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		String confDeadlineDate = convertDateToExplicitFormat(new Date());
    	String viewHeaderTitle = "<html><div style='text-align: center;'>"
    		+ "Current Active Conferences for <br>" + myUser.getEmail()
    		+ "<br>Current Date: " + confDeadlineDate + "</html>";
    	JLabel myConfTitleLabel = new JLabel(viewHeaderTitle, SwingConstants.CENTER);
		myConfTitleLabel.setFont(new Font("Serif", Font.PLAIN, 26));
        myConfTitleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        conferencePanel.add(myConfTitleLabel, BorderLayout.NORTH);
        JPanel tempPanel;
		JLabel tempLabel;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		tempPanel = new JPanel();
		tempPanel.add(conferenceNameLabel);
		tempPanel.setBorder(blackLine);
		conferenceButtonPanel.add(tempPanel, c);
		c.gridx = 1;
		tempPanel = new JPanel();
		tempPanel.add(isManuscriptSubmittedLabel);
		tempPanel.setBorder(blackLine);
		conferenceButtonPanel.add(tempPanel, c);
		c.gridx = 2;
		tempPanel = new JPanel();
		tempPanel.add(isSubprogramChairLabel);
		tempPanel.setBorder(blackLine);
		conferenceButtonPanel.add(tempPanel, c);
		c.gridx = 3;
		tempPanel = new JPanel();
		tempPanel.add(isRiewerLabel);
		tempPanel.setBorder(blackLine);
		conferenceButtonPanel.add(tempPanel, c);
		c.gridx = 4;
		tempPanel = new JPanel();
		tempPanel.add(conferenceDeadLineLabel);
		tempPanel.setBorder(blackLine);
		conferenceButtonPanel.add(tempPanel, c);
		ButtonGroup group = new ButtonGroup();
		for (myCounter = 0; myCounter < myConferenceList.size(); myCounter++) {
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
			group.add(button);
			c.gridx = 0;
			c.gridy = myCounter + 1;
			conferenceButtonPanel.add(button, c);
			c.gridx = 1;
			
			if (myConferenceList.get(myCounter).isUserAuthor(myUser)) {	
				tempPanel = new JPanel();
				tempLabel = new JLabel("Yes");
				tempLabel.setForeground(Color.GREEN);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			} else {
				tempPanel = new JPanel();
				tempLabel = new JLabel("No");
				tempLabel.setForeground(Color.RED);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			}
			c.gridx = 2;
			if (myConferenceList.get(myCounter).isUserSubprogramChair(myUser)) {
				tempPanel = new JPanel();
				tempLabel = new JLabel("Yes");
				tempLabel.setForeground(Color.GREEN);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			} else {
				tempPanel = new JPanel();
				tempLabel = new JLabel("No");
				tempLabel.setForeground(Color.RED);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			}
			c.gridx = 3;
			if (myConferenceList.get(myCounter).isUserReviewer(myUser)) {
				tempPanel = new JPanel();
				tempLabel = new JLabel("Yes");
				tempLabel.setForeground(Color.GREEN);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			} else {
				tempPanel = new JPanel();
				tempLabel = new JLabel("No");
				tempLabel.setForeground(Color.RED);
				tempPanel.add(tempLabel);
				tempPanel.setBorder(blackLine);
				tempPanel.setBackground(Color.WHITE);
				conferenceButtonPanel.add(tempPanel, c);
			}
			c.gridx = 4;
			String displayDate = convertDateToExplicitFormat(myConferenceList.get(myCounter).getManuscriptDeadline());
			tempPanel = new JPanel();
			tempLabel = new JLabel("" + displayDate);
			tempPanel.add(tempLabel);
			tempPanel.setBorder(blackLine);
			tempPanel.setBackground(Color.WHITE);
			conferenceButtonPanel.add(tempPanel, c);
			
		}
		conferenceButtonPanel.setOpaque(true);
		/*
		conferenceButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference List"));
		*/
		conferencePanel.add(conferenceButtonPanel);
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
	
	/**
	 * Returns a string representing a date formatted to include GMT time zone
	 * day, month, and year. 
	 * PreConditions:
	 * 	theDate is non-null
	 * @param theDate
	 * @return A string representing theDate.
	 */
	private String convertDateToExplicitFormat(Date theDate) {
		//formatter = new SimpleDateFormat("dd/MM/yy", currentLocale);
		TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat formatter = 
		  new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'UTC' Z");
		formatter.setTimeZone(utcTimeZone);
		// GMT is equivalent to UTC
		formatter.setTimeZone(TimeZone.getTimeZone("Etc/GMT+12"));

		String result = formatter.format(theDate);
		
		return result;
	}
}
