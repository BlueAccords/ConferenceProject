package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Conference;
import model.User;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;


public class ParentFrameView extends Observable implements Observer {
	private static final long serialVersionUID = 6981714533618801412L;

	private JFrame myFrame;
	private Map<String, JPanel> myPanelList;
	private JPanel myCardPanel, myButtonPanel, myHeaderPanel;
	private JLabel myHeaderCurrentUsernameLabel;
	private JButton myHeaderLogoutBtn;

	
	ParentFrameView(String theTitle, int theX, int theY) {
		// init parent frame values
		myFrame = new JFrame(theTitle);
		myFrame.setSize(theX, theY);
		myFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
		// init list of panels to swtich to
		myPanelList = new TreeMap<String, JPanel>();
		
		// main card panel and layout, used to switch between different panels for any navigation.
        myCardPanel = new JPanel();
        myCardPanel.setLayout(new CardLayout());
        myFrame.getContentPane().add(myCardPanel, BorderLayout.CENTER);

        // header panel that will always be present, and display user's username and logout button
        // init header panel with no user and hide it
        myHeaderPanel = new JPanel();
        myHeaderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myHeaderPanel.setLayout(new BoxLayout(myHeaderPanel, BoxLayout.LINE_AXIS));
               
        // init header panel elements
        myHeaderCurrentUsernameLabel = new JLabel();
        myHeaderCurrentUsernameLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
        myHeaderLogoutBtn = new JButton("Logout");
        
        myHeaderLogoutBtn.addActionListener(e -> {
        	setChanged();
			notifyObservers(Controller.LOG_OUT_STATE);
        	//logoutUser();
        });
        
        // add elements to the header panel
        myHeaderPanel.add(new JLabel("MSEE Conference Manager"));
        myHeaderPanel.add(Box.createHorizontalGlue());
        myHeaderPanel.add(myHeaderCurrentUsernameLabel);
        myHeaderPanel.add(myHeaderLogoutBtn);

        // hide header panel by default until a user is logged in
        myHeaderPanel.setVisible(false);
        
        // add header panel to parent frame
        myFrame.getContentPane().add(myHeaderPanel, BorderLayout.NORTH);

    }
	
	/**
	 * Returns the class's JFrame
	 * @author Ryan Tran
	 * @version 5/25/17
	 * @return JFrame belonging to class
	 */
	public JFrame getJFrame() {
		return this.myFrame;
	}
	
	/**
	 * This method will logout the current user and switch the gui to the login screen
	 * and hide the header panel as no user is logged in.
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
	 */
	public void logoutUser() {
		this.myHeaderCurrentUsernameLabel.setText("");
		this.myHeaderPanel.setVisible(false);
		
		myCardPanel.revalidate();
        myCardPanel.repaint();
	}

	/**
	 * Sets the passed in user to be logged in in the header Panel
	 * 
	 * Preconditions:
	 * 	User object must have an email.
	 * @param theUser the User Object who's email is going to be set to the display name in the header panel
	 * @author Ryan Tran
	 * @version 5/25/17
	 */
	public void setUserToBeLoggedIn(User theUser) {
		this.myHeaderCurrentUsernameLabel.setText(theUser.getEmail());
		this.myHeaderPanel.setVisible(true);
		
		myCardPanel.revalidate();
        myCardPanel.repaint();
	}
	
	/**
	 * This method will add a panel to the JFrame as well ass the panel and its associated name
	 * to the parent view's list of panels which will allow switching to the JPanel.
	 * 
	 * PreConditions:
	 * 	thePanel and thePanelName must be non-null 
	 * @param thePanel The JPanel to add to the parent Jframe's list of panels that it can switch between.
	 * @param thePanelName The String name that will be used to allow switching to the given JPanel
	 */
	public void addPanel(JPanel thePanel, String thePanelName) {
		this.myPanelList.put(thePanelName, thePanel);
        myCardPanel.add(thePanel, thePanelName);
        
        myCardPanel.revalidate();
        myCardPanel.repaint();
	}
	
	/**
	 * This method will switch the view to the panel that belongs to the passed in panel name.
	 * @param thePanelName
	 */
	public void switchToPanel(String thePanelName) {
		JPanel panelToSwitchTo = this.myPanelList.get(thePanelName);
		
		if(panelToSwitchTo == null) {
			throw new IllegalArgumentException("Panel Name argument does not belong to a valid JPanel");
		}
		
		CardLayout cl = (CardLayout) myCardPanel.getLayout();
    	cl.show(myCardPanel, thePanelName);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UI Parent Frame was notified of update");
		if(arg instanceof Integer) {
			System.out.println("Received new state from " + o.getClass().getSimpleName());
			setChanged();
			notifyObservers((Integer) arg);
		} else if(arg instanceof String) {
			setChanged();
			notifyObservers((String) arg);
		} else if(o instanceof ConferenceListView) { //Coming from ConferenceListView
			if (arg instanceof String) {
				setChanged();
				notifyObservers((String) arg);
			} else {
				//System.out.println("Received a Conference");
				setChanged();
				notifyObservers((Conference) arg);
			}
		}
		
	}
	
}
