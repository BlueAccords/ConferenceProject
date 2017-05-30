/**
 * 
 */
package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Author;
import model.Conference;
import model.SubprogramChair;
import model.User;

/**
 * This view will allow the user to select a user role after they've already selected a conference OR return the conference list
 * @author Ryan Tran
 * @version 5/25/17
 *
 */
public class UserRoleView extends Observable{
	private Conference myConference;
	private JLabel myViewTitle;
	
	// user role buttons
	private JButton myAuthorBtn;
	private User myUser;
	
	/**
	 * Constructor to init the view after a user has chosen a conference
	 * and that conference is passed into the view
	 * preconditions:
	 * 	theSelectedConference must be non-null
	 * @author Ryan Tran
	 * @version 5/25/17
	 */
	public UserRoleView(Conference theSelectedConference, User theUser) {
		myUser = theUser;
		myConference = theSelectedConference;
		myViewTitle = new JLabel("Available Roles for: " + theSelectedConference.getConferenceName());
		myAuthorBtn = new JButton("Author Role");

	}
	
	/**
	 * Method to create view for Manuscript options.
	 * @return JPanel that displays Manuscript options for selection.
	 * @author Casey Anderson
	 */
	public JPanel createSelectRolePanel() {
		
		JPanel selectedUserRolePanel = new JPanel(new BorderLayout());
		JPanel bagPanel = new JPanel(new GridBagLayout());
		JPanel selectRoleButtonPanel = new JPanel(new GridLayout(0,1));
		selectRoleButtonPanel.add(myViewTitle);
		
		String viewHeaderTitle = "<html><div style='text-align: center;'>"
	    		+ "Current Active Roles for <br>" + myUser.getEmail();
	    	JLabel myConfTitleLabel = new JLabel(viewHeaderTitle, SwingConstants.CENTER);
			myConfTitleLabel.setFont(new Font("Serif", Font.PLAIN, 26));
	        myConfTitleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
	        selectedUserRolePanel.add(myConfTitleLabel, BorderLayout.NORTH);
		
		myAuthorBtn.addActionListener(e -> {
			setChanged();
			notifyObservers(new Author(myUser));
			setChanged();
			notifyObservers(Controller.CHOOSE_USER + Controller.AUTHOR);
		});
		
		selectRoleButtonPanel.add(myAuthorBtn);

		if (myConference.isUserSubprogramChair(myUser)) {
			JButton subProgramChairBtn = new JButton("SubProgram Chair Role");
			subProgramChairBtn.addActionListener(e -> {
				setChanged();
				notifyObservers(new SubprogramChair(myUser));
				setChanged();
				notifyObservers(Controller.CHOOSE_USER + (Controller.SUBPROGRAM_CHAIR * -1));
			});
			selectRoleButtonPanel.add(subProgramChairBtn);
		}

		selectRoleButtonPanel.setOpaque(true);
		
		selectRoleButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Select Role"));
		bagPanel.add(selectRoleButtonPanel);
		selectedUserRolePanel.add(bagPanel);
		
		return selectedUserRolePanel;
		
	}
}