package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Manuscript.AuthorExistsInListException;
import model.User;

public class AuthorSubmitManuscriptView extends Observable {
	private Author myAuthor;
	
	private Conference myConference;

	public AuthorSubmitManuscriptView(Author theAuthor, Conference theConference) {
		myAuthor = theAuthor;
		myConference = theConference;
	}
	
	/**
	 * Method to create view for submitting a manuscript
	 * @return JPanel to display submitting a manuscript view and gather Manuscript information.
	 * @author Casey Anderson
	 */
	public JPanel submitManuscriptView() {
		
		// JPanels for for view.
		JPanel ManuscriptPanelHolder = new JPanel(new GridBagLayout());
		JPanel createManuscriptPanel = new JPanel(new GridBagLayout());
		JPanel manuscriptTitlePanel = new JPanel(new GridBagLayout());
		JPanel manuscriptAuthorsPanel = new JPanel(new GridBagLayout());
		JPanel ManuscriptFilePanel = new JPanel(new GridBagLayout());
		JPanel ManuscriptSubmitPanel = new JPanel(new GridBagLayout());
		
		
		// JTextField and JTextArea for gathering authors Manuscript information.
		JTextField manuscriptTitleField = new JTextField(20);
		JFileChooser manuscriptFileChooser = new JFileChooser();
		JTextArea textArea = new JTextArea(5, 20);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		
		// Submission button.
		JButton ManuscriptSubmitButton = new JButton("Submit");
		ManuscriptSubmitButton.setActionCommand("Submit Manuscript");
		
		ManuscriptSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  //Need checks if any fields are empty, also need to pass current user into this class for the main author
				String[] AuthorList = textArea.getText().split(",");
				Author tempAuthor;
				User tempUser;
				if (!myAuthor.isAuthorsAtLimit(AuthorList, myConference)) {
					Manuscript newManuscript = new Manuscript(manuscriptTitleField.getText(), manuscriptFileChooser.getSelectedFile(), myAuthor);
					if (AuthorList.length > 1) {
						for (int i = 1 ; i < AuthorList.length; i++) {
							if (User.doesEmailBelongToUser(AuthorList[i])) {
								tempUser = User.getUserByEmail(AuthorList[i]);
								if (myConference.isUserAuthor(tempUser)) {
									tempAuthor = myConference.getAuthor(tempUser);
								} else {
									tempAuthor = new Author(tempUser);
								}
								try {
									newManuscript.addAuthor(tempAuthor);
								} catch (AuthorExistsInListException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								tempUser = new User(AuthorList[i]);
								User.addUser(tempUser);
								User.writeUsers();
								tempAuthor = new Author(tempUser);
								try {
									newManuscript.addAuthor(tempAuthor);
								} catch (AuthorExistsInListException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
					setChanged();
					notifyObservers(newManuscript);  
					setChanged();
					notifyObservers(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT_ACTION);
				} else {
					ManuscriptSubmitButton.setEnabled(false);
					JOptionPane.showMessageDialog(ManuscriptPanelHolder,"Sorry one of your authors has to many Manuscripts submitted to this Conference.");  
				}
		    }  
		});
		
		ManuscriptSubmitButton.setEnabled(false);
		JButton AuthorSubmitButton = new JButton("Click when done adding co-authors to activate submit button");
		AuthorSubmitButton.setActionCommand("Verify Authors");
		
		AuthorSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  //Need checks if any fields are empty, also need to pass current user into this class for the main author
				String[] AuthorList = textArea.getText().split(",");
				if (!myAuthor.isAuthorsAtLimit(AuthorList, myConference)) {
					ManuscriptSubmitButton.setEnabled(true);
				} else {
					ManuscriptSubmitButton.setEnabled(false);
					JOptionPane.showMessageDialog(ManuscriptPanelHolder,"Sorry one of your authors has too many Manuscripts submitted to this Conference.");  
				}
		    }  
		});
		
		
		// JLabels to communicate submission process to author.
		JLabel ManuscriptTitleLabel = new JLabel("Enter Name of Tile for Manuscript: ");
		JLabel ManuscriptAuthorsLabel = new JLabel("Enter Names of Co-Authors for Manuscript separated by a comma ',' or leave blank if none: ");
		JLabel ManuscriptFileLabel = new JLabel("Enter File Path: ");
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		
		manuscriptTitlePanel.add(ManuscriptTitleLabel, c);
		c.gridy = 1;
		manuscriptTitlePanel.add(manuscriptTitleField, c);
		c.gridy = 0;
		manuscriptAuthorsPanel.add(ManuscriptAuthorsLabel, c);
		c.gridy = 1;
		c.ipady = 30;
		manuscriptAuthorsPanel.add(textArea, c);
		c.gridy = 2;
		c.ipady = 10;
		manuscriptAuthorsPanel.add(AuthorSubmitButton, c);
		c.gridy = 0;
		ManuscriptFilePanel.add(ManuscriptFileLabel, c);
		c.gridy = 1;
		ManuscriptFilePanel.add(manuscriptFileChooser, c);
		c.gridy = 0;
		ManuscriptSubmitPanel.add(ManuscriptSubmitButton, c);
		c.ipady = 0;
		createManuscriptPanel.add(manuscriptTitlePanel, c);
		c.gridy = 1;
		//c.ipady = 30;
		createManuscriptPanel.add(manuscriptAuthorsPanel, c);
		c.gridy = 2;
		createManuscriptPanel.add(ManuscriptFilePanel, c);
		c.gridy = 3;
		createManuscriptPanel.add(ManuscriptSubmitPanel, c);
		ManuscriptSubmitPanel.setOpaque(true);
		createManuscriptPanel.setOpaque(true);
		manuscriptTitlePanel.setOpaque(true);
		manuscriptAuthorsPanel.setOpaque(true);
		ManuscriptFilePanel.setOpaque(true);
		
		createManuscriptPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Manuscript Submission Page"));
		ManuscriptPanelHolder.add(createManuscriptPanel);
		return ManuscriptPanelHolder;
	}
	


}
