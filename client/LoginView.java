package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javafx.scene.input.KeyCode;
import model.Conference;
import model.User;

/**
 * This Login view will instantiate a JPanel and hold 2 child panels
 * which are the login form and the buttons to login, register, or exit
 * @author Ryan Tran
 * @version 5/20/17
 */
public class LoginView extends Observable implements Observer {

	// Parent Panel that holds the child panels
	private JPanel myPanel;
	
	// Panel that holds login forms and labels
	private JPanel myFormPanel;
	
	// Panel that holds buttons like login, register and exit
	private JPanel myBtnPanel;
	private JButton myLoginBtn;
	private JButton myExitBtn;
	private JButton myRegisterBtn;
	private JTextField myUsernameField;
	private JLabel myUsernameLabel;
	private JLabel myErrorLabel;
	
	private JLabel myProgramTitleLabel;
	
	public LoginView() {
		// initalize components
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());

		myLoginBtn = new JButton("Login");
		myRegisterBtn = new JButton("Register");
		myExitBtn = new JButton("Exit");
		myUsernameLabel = new JLabel("Username:");
		myUsernameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		myProgramTitleLabel = new JLabel("MSEE Conference Manager", SwingConstants.CENTER);
		myUsernameField = new JTextField(20);
		myErrorLabel = new JLabel();
		myErrorLabel.setForeground(Color.RED);
		
		// add title to parent panel
		myProgramTitleLabel.setFont(new Font("Serif", Font.PLAIN, 26));
		myProgramTitleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		myPanel.add(myProgramTitleLabel, BorderLayout.NORTH);

		
		// Set up panel for form elements(excluding buttons)
		myFormPanel = new JPanel();
		myFormPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints formGbc = new GridBagConstraints();

		formGbc.gridx = 0;
		formGbc.gridy = 0;
		formGbc.anchor = GridBagConstraints.LINE_START;
		myUsernameLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
		myFormPanel.add(myUsernameLabel, formGbc);
		
		// set error label to invisible by default
		formGbc.gridx = 0;
		formGbc.gridy = 1;
		formGbc.anchor = GridBagConstraints.LINE_START;
		myErrorLabel.setVisible(false);
		myFormPanel.add(myErrorLabel, formGbc);

		formGbc.gridx = 1;
		formGbc.gridy = 0;
		formGbc.anchor = GridBagConstraints.LINE_END;
		
		// Key Listener for when user hits Enter when typing in username
		myUsernameField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					myErrorLabel.setVisible(false);
					myUsernameField.getText();
					setChanged();
					notifyObservers(myUsernameField.getText());
					setChanged();
					notifyObservers(Controller.LOG_IN_STATE);
				}
			}

			// events we're not handling but need to be implemented for KeyListener object
			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
			
		});
		myFormPanel.add(myUsernameField, formGbc);
		
		myPanel.add(myFormPanel, BorderLayout.CENTER);
		
		// initalization button actions
		
		// login action
		// should send username in field to controller
		// and reset error message label
		myLoginBtn.addActionListener(e -> {
			myErrorLabel.setVisible(false);
			myUsernameField.getText();
			setChanged();
			notifyObservers(myUsernameField.getText());
			setChanged();
			notifyObservers(Controller.LOG_IN_STATE);
		});
		
		/**
		 * Iterates through parent frames until JFrame parent is found and closes it.
		 */
		myExitBtn.addActionListener(e -> {
			Container nextParentFrame = myExitBtn.getParent();
            do 
                nextParentFrame = nextParentFrame.getParent(); 
            while (!(nextParentFrame instanceof JFrame));                                      
            	((JFrame) nextParentFrame).dispose();
				User.writeUsers();
				Conference.writeConferences();
				System.exit(0);
		});
		
		
		// Set up Buttons Panel
		myBtnPanel = new JPanel();
		myBtnPanel.setLayout(new GridBagLayout());
		GridBagConstraints btnGbc = new GridBagConstraints();
		btnGbc.gridwidth = 1;
		btnGbc.weightx = 1;
		
		btnGbc.gridx = 0;
		btnGbc.gridy = 0;
		btnGbc.gridwidth = 2;
		btnGbc.fill = GridBagConstraints.HORIZONTAL;
		btnGbc.ipady = 20;
		btnGbc.weightx = 0.0;
		myLoginBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		myBtnPanel.add(myLoginBtn, btnGbc);
			
		btnGbc.gridx = 0;
		btnGbc.gridy = 1;
		btnGbc.gridwidth = 1;
		btnGbc.weightx = 1;
		btnGbc.fill = GridBagConstraints.HORIZONTAL;
		btnGbc.ipady = 5;
		myBtnPanel.add(myRegisterBtn, btnGbc);
		
		btnGbc.gridx = 1;
		btnGbc.gridy = 1;
		btnGbc.gridwidth = 1;
		btnGbc.weightx = 1;
		btnGbc.fill = GridBagConstraints.HORIZONTAL;
		btnGbc.ipady = 5;
		myBtnPanel.add(myExitBtn, btnGbc);
		
		// set padding
		myBtnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// add setup button panel to parent panel
		myBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, 400, 50, 400));
		myPanel.add(myBtnPanel, BorderLayout.SOUTH);
		
		
		
	}
	
	/**
	 * Returns the Login View JPanel
	 * @return A JPanel that shows the LoginView
	 */
	public JPanel getPanel() {
		return this.myPanel;
	}
	
	/**
	 * Sets the error label to the passed in error message.
	 * 
	 * PreConditions:
	 * 	String should be non-null
	 * PostConditions:
	 * 	myErrorLabel should be set to theMessage
	 * @param theMessage the string to set the error label to
	 */
	private void setErrorMessage(String theMessage) {
		this.myErrorLabel.setText(theMessage);
		this.myErrorLabel.setVisible(true);
	}

	/**
	 * Update method to be called when the observable the loginView is observing
	 * notifies the LoginVIew of changes.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof String) {
			String controllerMsg = (String) arg1;
			if(controllerMsg.equals("Invalid Username")) {
				setErrorMessage("Invalid Username");
			}
		}
	}
	
}
