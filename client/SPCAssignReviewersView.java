/**
 * 
 */
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

import model.Reviewer;

/**
 * Displays panel of all possible valid reviewers for this manuscript
 * For SubprogramChair to choose one to assign as a reviewer to this manuscript
 *  
 * @author Connor Lundberg
 *
 */
public class SPCAssignReviewersView extends Observable {
	
	private ArrayList<Reviewer> myAssignedReviewers;
	private ArrayList<Reviewer> myEligibleReviewers;
	private int myCounter;
	
	
	/**
	 * Default constructor for testing.
	 *  
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	public SPCAssignReviewersView() {
		myEligibleReviewers = new ArrayList<Reviewer>();
		myAssignedReviewers = new ArrayList<Reviewer>();
		myCounter = 0;
	}
	
	
	/**
	 * A constructor that takes a valid Reviewer list. This does not check if the 
	 * list is made up of eligible Reviewers.
	 * 
	 * Pre: This list is not null. This is a list of eligible reviewers by calling Conference's getEligibleReviewers method
	 * before passing it here.
	 * 
	 * @param theEligibleReviewerList The valid Reviewer list to display.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	public SPCAssignReviewersView(ArrayList<Reviewer> theEligibleReviewerList) {
		myEligibleReviewers = theEligibleReviewerList;
		myAssignedReviewers = new ArrayList<Reviewer>();
		myCounter = 0;
	}
	

	/**
	 * This method will return a JPanel of the Reviewers list that the Subprogram Chair
	 * can look at and interact with. *empty for testing*
	 * 
	 * @return JPanel The panel containing the list of Reviewers assigned to this Subprogram Chair.
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public JPanel viewReviewersListView() {
		JPanel reviewersListPanel = new JPanel(new GridLayout(0,3));
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		ButtonGroup group = new ButtonGroup();
		reviewersListPanel.add(new JLabel("User id of Reviewer:"));
		reviewersListPanel.add(new JLabel("number of reviews assigned to Reviewer:"));
		reviewersListPanel.add(new JLabel("click to assign this manuscript to reviewer:"));
		for (myCounter = 0; myCounter < myEligibleReviewers.size(); myCounter++) {
			JLabel reviewerLabel = new JLabel (myEligibleReviewers.get(myCounter).getUser().getEmail());
			JLabel reviewerNumberAssignmentsLabel = new JLabel ("" + myEligibleReviewers.get(myCounter).getNumAssignedManuscripts());
			JButton button = new JButton("Assign"); //will need to display more info here about the reviewer *maybe not a button*
			button.setActionCommand(myEligibleReviewers.get(myCounter).getUser().getEmail());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					int reviewerIndex = getClickedReviewer(group, e);
					
					if (button.getText().equals("Assign")) {
						myAssignedReviewers.add(myEligibleReviewers.get(reviewerIndex));
						button.setText("Remove");
					} else {
						myAssignedReviewers.remove(myEligibleReviewers.get(reviewerIndex));
						button.setText("Assign");
					}
		        }  
		    });
			
			group.add(button);
			
			
			reviewersListPanel.add(reviewerLabel);
			reviewersListPanel.add(reviewerNumberAssignmentsLabel);
			reviewersListPanel.add(button);
		}	
		
		JButton submitReviewers = new JButton ("Submit");
		submitReviewers.addActionListener(e -> {
			setChanged();
			notifyObservers(myAssignedReviewers);
			setChanged();
			notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.LIST_MANUSCRIPT_VIEW);
		});
		JPanel submitPanel = new JPanel();
		submitPanel.add(submitReviewers);
		reviewersListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Assigned Reviewers List"));
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(reviewersListPanel, c);
		c.gridy = 1;
		mainPanel.add(submitPanel, c);
		return mainPanel;
	}
	
	
	/**
	 * This method will return the index Reviewer linked to the button clicked
	 * by iterating through the button group, and seeing which button was pressed and
	 * finding the same Reviewer in the Reviewer list with that index.
	 * 
	 * @param theBtnGroup the button group containing the button that was pressed
	 * @param theAction The action, we are comparing the action's parent button to the button group
	 * @return the index of the Reviewer object that is linked to the pressed button
	 * 
	 * @author Ryan Tran, Connor Lundberg
	 * @version 5/27/17
	 */
	private int getClickedReviewer(ButtonGroup theBtnGroup, ActionEvent theAction) {
		int i = 0;
		boolean reviewerFound = false;

		Enumeration groupElements = theBtnGroup.getElements();
		while (groupElements.hasMoreElements()) {
			AbstractButton originalGroupBtn = (AbstractButton)groupElements.nextElement();
			JButton actionSrcBtn = ((JButton)theAction.getSource());
			
			if(originalGroupBtn.equals(actionSrcBtn)) {
				reviewerFound = true;
				break;
			}
			
			i++;
		}
		
		if(reviewerFound) {
			return i;
		} else {
			return -1;
		}
	}
}
