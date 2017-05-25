package client;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A simple class that allows the User to choose a role (either Author or Subprogram Chair *if applicable*).
 * 
 * @author Connor Lundberg
 * @version 5/25/2017
 *
 */
public class UserRoleView extends Observable {
	//JPanel myPanel;
	
	public UserRoleView() {
		//myPanel = new JPanel();
	}
	
	
	/**
	 * This method creates the UserRoleView panel that will be sent back to the parent frame.
	 * 
	 * @return The UserRoleView panel
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public JPanel createUserRoleView() {
		JPanel userRoleView = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		JButton authorButton = new JButton("Author");
		
		return userRoleView;
	}
}
