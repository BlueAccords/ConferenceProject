package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginView {

	private JPanel myPanel;
	private JPanel myFormPanel;
	private JPanel myBtnPanel;
	private JButton myLoginBtn;
	private JButton myExitBtn;
	private JButton myRegisterBtn;
	private JTextField myUsernameField;
	private JLabel myUsernameLabel;
	
	public LoginView() {
		// initalize components
		myPanel = new JPanel(new BorderLayout());
		myLoginBtn = new JButton("Login");
		myRegisterBtn = new JButton("Register");
		myExitBtn = new JButton("Exit");
		myUsernameLabel = new JLabel("Username:");
		myUsernameField = new JTextField(20);
		
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
