/**
 * 
 */
package client;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;

/**
 * This view will allow the user to select a user role after they've already selected a conference OR return the conference list
 * @author Ryan Tran
 * @version 5/25/17
 *
 */
public class UserRoleView extends Observable{
	private JPanel myPanel;
	
	private JLabel myViewTitle;
	
	// user role buttons
	private JButton myAuthorBtn;
	private JButton myProgramChairBtn;
	private JButton mySubProgramChairBtn;
	private JButton myReviewerBtn;
	
	private JButton myConferenceListBtn;
	
	/**
	 * Constructor to init the view after a user has chosen a conference
	 * and that conference is passed into the view
	 * preconditions:
	 * 	theSelectedConference must be non-null
	 * @author Ryan Tran
	 * @version 5/25/17
	 */
	public UserRoleView(Conference theSelectedConference) {
		myPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// init view title and add to panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		myViewTitle = new JLabel("Choose a User Role for... " + theSelectedConference.getConferenceName());
		myViewTitle.setFont(new Font("Serif", Font.PLAIN, 24));
		myPanel.add(myViewTitle, gbc);
		
		gbc.gridy++;
		myAuthorBtn = new JButton("Author Role");
		myAuthorBtn.addActionListener(e -> {
			setChanged();
			notifyObservers(Controller.CHOOSE_USER + Controller.AUTHOR);
		});
		myPanel.add(myAuthorBtn, gbc);
		
		/*gbc.gridy++;
		myProgramChairBtn = new JButton("Program Chair Role");
		myProgramChairBtn.addActionListener(e -> {
			System.out.println("program chair role btn clicked");
		});
		myPanel.add(myProgramChairBtn, gbc);*/
		
		gbc.gridy++;
		mySubProgramChairBtn = new JButton("SubProgram Chair Role");
		mySubProgramChairBtn.addActionListener(e -> {
			setChanged();
			notifyObservers(Controller.CHOOSE_USER * Controller.SUBPROGRAM_CHAIR);
		});
		myPanel.add(mySubProgramChairBtn, gbc);
		
		/*gbc.gridy++;
		myReviewerBtn = new JButton("Reviewer Role");
		myReviewerBtn.addActionListener(e -> {
			System.out.println("reviewer btn role clicked");
		});
		myPanel.add(myReviewerBtn, gbc);*/
	}
	
	/**
	 * 
	 * Returns this view's JPanel
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
	 * @return returns the user role view's JPanel
	 */
	public JPanel getPanel() {
		return this.myPanel;
	}
}