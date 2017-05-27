package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Author;
import model.Conference;
import model.Manuscript;

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
		JPanel createManuscriptPanel = new JPanel(new GridLayout(3,1));
		JPanel manuscriptTitlePanel = new JPanel();
		JPanel manuscriptAuthorsPanel = new JPanel();
		JPanel ManuscriptFilePanel = new JPanel();
		JPanel ManuscriptSubmitPanel = new JPanel();
		
		// JTextField and JTextArea for gathering authors Manuscript information.
		JTextField manuscriptTitleField = new JTextField(20);
		JFileChooser manuscriptFileChooser = new JFileChooser();
		JTextArea textArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setLineWrap(true);
		
		// Submission button.
		JButton ManuscriptSubmitButton = new JButton("Submit");
		ManuscriptSubmitButton.setActionCommand("Submit Manuscript");
		
		ManuscriptSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  //Need checks if any fields are empty, also need to pass current user into this class for the main author
				Manuscript newManuscript = new Manuscript(manuscriptTitleField.getText(), manuscriptFileChooser.getSelectedFile(), myAuthor);
				setChanged();
				notifyObservers(newManuscript);  
				setChanged();
				notifyObservers(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);
		    }  
		});
		
		ManuscriptSubmitButton.setEnabled(false);
		JButton AuthorSubmitButton = new JButton("Click to verify Authors");
		AuthorSubmitButton.setActionCommand("Verify Authors");
		
		AuthorSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  //Need checks if any fields are empty, also need to pass current user into this class for the main author
				String[] AuthorList = textArea.getText().split(",");
				if (!myAuthor.isAuthorsAtLimit(AuthorList, myConference)) {
					ManuscriptSubmitButton.setEnabled(true);
				} else {
					
				}
		    }  
		});
		
		// JLabels to communicate submission process to author.
		JLabel ManuscriptTitleLabel = new JLabel("Enter Name of Tile for Manuscript: ");
		JLabel ManuscriptAuthorsLabel = new JLabel("Enter Name of Author and Co-Authors for Manuscript separated by a comma ',': ");
		JLabel ManuscriptFileLabel = new JLabel("Enter File Path: ");
			
		manuscriptTitlePanel.add(ManuscriptTitleLabel);
		manuscriptTitlePanel.add(manuscriptTitleField);
		manuscriptAuthorsPanel.add(ManuscriptAuthorsLabel);
		manuscriptAuthorsPanel.add(textArea);
		ManuscriptFilePanel.add(ManuscriptFileLabel);
		ManuscriptFilePanel.add(manuscriptFileChooser);
		ManuscriptSubmitPanel.add(ManuscriptSubmitButton);
		createManuscriptPanel.add(manuscriptTitlePanel);
		createManuscriptPanel.add(manuscriptAuthorsPanel);
		createManuscriptPanel.add(ManuscriptFilePanel);
		createManuscriptPanel.add(ManuscriptSubmitPanel);
		ManuscriptSubmitPanel.setOpaque(true);
		createManuscriptPanel.setOpaque(true);
		manuscriptTitlePanel.setOpaque(true);
		manuscriptAuthorsPanel.setOpaque(true);
		ManuscriptFilePanel.setOpaque(true);
		
		createManuscriptPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Manuscript Submission Page"));
		
		return createManuscriptPanel;
	}
	


}
