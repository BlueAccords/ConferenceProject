package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This Login view will instantiate a JPanel and hold 2 child panels
 * which are the login form and the buttons to login, register, or exit
 * @author Ryan Tran
 * @version 5/20/17
 */
public class LoginView extends Observable {

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
	
	private JLabel myProgramTitleLabel;
	
	public LoginView() {
		// initalize components
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());

		myLoginBtn = new JButton("Login");
		myRegisterBtn = new JButton("Register");
		myExitBtn = new JButton("Exit");
		myUsernameLabel = new JLabel("Username:");
		myProgramTitleLabel = new JLabel("MSEE Conference Manager", SwingConstants.CENTER);
		myUsernameField = new JTextField(20);
		
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
		myFormPanel.add(myUsernameLabel, formGbc);
		
		formGbc.gridx = 1;
		formGbc.anchor = GridBagConstraints.LINE_END;
		myFormPanel.add(myUsernameField, formGbc);
		
		myPanel.add(myFormPanel, BorderLayout.CENTER);
		
		// initalization button actions
		myLoginBtn.addActionListener(e -> {
			myUsernameField.getText();
			setChanged();
			notifyObservers(myUsernameField.getText());
			setChanged();
			notifyObservers(Controller.LOG_IN_STATE);
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
		myPanel.add(myBtnPanel, BorderLayout.SOUTH);
		
		
		
	}
	
	/**
	 * Returns the Login View JPanel
	 * @return A JPanel that shows the LoginView
	 */
	public JPanel getPanel() {
		return this.myPanel;
	}
	
}
