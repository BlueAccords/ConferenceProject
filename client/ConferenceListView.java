package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Conference;

public class ConferenceListView extends Observable  implements Observer {
	/**
	 * List of Conferences available to Author
	 */
	private ArrayList<Conference> myConferenceList;
	
	/**
	 * Counter to help iterate through Lists
	 */
	private int myCounter;
	
	public ConferenceListView() {
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
	public ConferenceListView(ArrayList<Conference> theConferenceList) {
		myConferenceList = theConferenceList;
		myCounter = 0;
	}
	
	/**
	 * Method to create a view for Conference selection.
	 * @return JPanel displaying all the available Conferences to select.
	 * @author Casey Anderson
	 */
	public JPanel createConferenceListView() {
		
		JPanel conferenceListPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		for (myCounter = 0; myCounter < myConferenceList.size(); myCounter++) {
			
			JButton button = new JButton(myConferenceList.get(myCounter).getConferenceName() + "	" + myConferenceList.get(myCounter).getManuscriptDeadline());
			button.setActionCommand(myConferenceList.get(myCounter).getConferenceName());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					setChanged();
				    notifyObservers(myConferenceList.get(myCounter).getConferenceName());  
		        }  
		    }); 
			
			group.add(button);
			conferenceListPanel.add(button);
			
		}
		
		conferenceListPanel.setOpaque(true);
		
		conferenceListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference List"));
		
		return conferenceListPanel;
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		myConferenceList =   (ArrayList<Conference>) arg1;
		
	}
}
