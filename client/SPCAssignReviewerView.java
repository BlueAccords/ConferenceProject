/**
 * 
 */
package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Reviewer;

/**
 * Displays panel of all possible valid reviewers for this manuscript
 * For SubprogramChair to choose one to assign as a reviewer to this manuscript
 *  
 * @author Connor Lundberg
 *
 */
public class SPCAssignReviewerView extends Observable {
	
	private ArrayList<Reviewer> myReviewerList;
	private int myCounter;
	
	
	/**
	 * Default constructor for testing.
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public SPCAssignReviewerView() {
		myReviewerList = new ArrayList<Reviewer>();
		myCounter = 0;
	}
	
	
	/**
	 * A constructor that takes a valid Reviewer list. This does not check if the 
	 * list is made up of eligible Reviewers.
	 * 
	 * Pre: This list is not null.
	 * 
	 * @param theReviewerList The valid Reviewer list to display.
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public SPCAssignReviewerView(ArrayList<Reviewer> theReviewerList) {
		myReviewerList = theReviewerList;
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
		JPanel reviewersListPanel = new JPanel(new GridLayout(0,1));
		

		//JPanel manuscriptListPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		for (myCounter = 0; myCounter < myReviewerList.size(); myCounter++) {
			
			JButton button = new JButton(myReviewerList.get(myCounter).getUser().getWholeName()); //will need to display more info here about the reviewer *maybe not a button*
			button.setActionCommand(myReviewerList.get(myCounter).getUser().getWholeName());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					setChanged();
				    notifyObservers(myReviewerList.get(myCounter).getUser().getWholeName());  
				    setChanged();
				    notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.LIST_ASSIGNED_REVIEWERS_VIEW);
		        }  
		    });
			
			group.add(button);
			reviewersListPanel.add(button);
		}	
		
		reviewersListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Assigned Reviewers List"));
		
		return reviewersListPanel;
	}
}
