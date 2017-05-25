package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Conference;
import model.Manuscript;


/**
 * The UI Author View is a class to make all the different JPanels  
 * to display views for Author user. It communicates to Controller the authors selections.
 * 
 * @author Casey Anderson
 * @version 5/17/2017
 */
public class UI_Author extends Observable {
	
	/**
	 * List of Manuscripts belonging to Author
	 */
	private ArrayList<Manuscript> myManuscriptList;
	
	/**
	 * Counter to help iterate through Lists
	 */
	private int myCounter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -703649288417940022L;
	
	/**
	 * Constructor to initialize fields.
	 * @author Casey Anderson
	 */
	public UI_Author() {
		myManuscriptList = new ArrayList<Manuscript>();
		myCounter = 0;		
	}
	
	
	/**
	 * Constructor that takes a Manuscript list so it does not have to go through update.
	 * 
	 * @param theManuscriptList Must be a non-null list of Manuscripts
	 * 
	 * @author Connor Lundberg
	 * @version 5/23/201
	 */
	public UI_Author(ArrayList<Manuscript> theManuscriptList) {
		myManuscriptList = theManuscriptList;
		myCounter = 0;
	}
	
	
	/**
	 * Method to create a view for the options available for Conferences.
	 * @return JPanel for displaying all the available Conference options for selection.
	 * @author Casey Anderson
	 */
	public JPanel createConferenceOptions() {
		
		JPanel conferenceOptionPanel = new JPanel(new GridLayout(0,1));		
		JButton submitButton = new JButton("Submit Manuscript");
		submitButton.setActionCommand("Submit Manuscript");
		
		submitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
		        notifyObservers(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);  
		    }  
		});
		
		conferenceOptionPanel.add(submitButton);
		JButton viewManuscriptButton = new JButton("View Manuscripts");
		viewManuscriptButton.setActionCommand("View Manuscripts");
		
		viewManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers(Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);  
		    }  
		});
		
		conferenceOptionPanel.add(viewManuscriptButton);		
		conferenceOptionPanel.setOpaque(true);
		
		conferenceOptionPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		
		return conferenceOptionPanel;
		
	}
	
	/**
	 * Method to create view for Manuscript options.
	 * @return JPanel that displays Manuscript options for selection.
	 * @author Casey Anderson
	 */
	public JPanel createManuscriptOptions() {
		
		JPanel manuscriptOptionPanel = new JPanel(new GridLayout(0,1));
		JButton editButton = new JButton("Edit Manuscript");
		editButton.setActionCommand("Edit Manuscript");
		
		editButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers("Edit Manuscript");  
		    }  
		});
		
		manuscriptOptionPanel.add(editButton);
		JButton authorListButton = new JButton("Author List");
		authorListButton.setActionCommand("Author List");
		
		authorListButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers("Author List");  
		    }  
		});
		
		manuscriptOptionPanel.add(authorListButton);
		JButton deleteManuscriptButton = new JButton("Delete Manuscript");
		deleteManuscriptButton.setActionCommand("Delete Manuscript");
		
		deleteManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers("Delete Manuscript");  
		    }  
		});
		
		manuscriptOptionPanel.add(deleteManuscriptButton);
		manuscriptOptionPanel.setOpaque(true);
		
		manuscriptOptionPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Manuscript Options"));
		
		return manuscriptOptionPanel;
		
	}
	
	/**
	 * Method to create view for submitting a manuscript
	 * @return JPanel to display submitting a manuscript view and gather Manuscript information.
	 * @author Casey Anderson
	 */
	public JPanel submitManuscriptView() {
		
		// JPanels for for view.
		JPanel createManuscriptPanel = new JPanel();
		JPanel manuscriptTitlePanel = new JPanel();
		JPanel manuscriptAuthorsPanel = new JPanel();
		JPanel ManuscriptFilePanel = new JPanel();
		JPanel ManuscriptSubmitPanel = new JPanel();
		
		// JTextField and JTextArea for gathering authors Manuscript information.
		JTextField manuscriptTitleField = new JTextField(20);
		JTextField manuscriptFileField = new JTextField(20);
		JTextArea textArea = new JTextArea("Please Enter Author name and all Co-Authors seperated by a comma ','", 5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setLineWrap(true);
		
		// Submission button.
		JButton ManuscriptSubmitButton = new JButton("Submit");
		ManuscriptSubmitButton.setActionCommand("Delete Manuscript");
		
		ManuscriptSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers(manuscriptTitleField.getText() + "," + textArea.getText() + "," + manuscriptFileField.getText());  
		    }  
		});
		
		// JLabels to communicate submission process to author.
		JLabel ManuscriptTitleLabel = new JLabel("Enter Name of Tile for Manuscript: ");
		JLabel ManuscriptAuthorsLabel = new JLabel("Enter Name of Author and Co-Authors for Manuscript: ");
		JLabel ManuscriptFileLabel = new JLabel("Enter File Path: ");
			
		manuscriptTitlePanel.add(ManuscriptTitleLabel);
		manuscriptTitlePanel.add(manuscriptTitleField);
		manuscriptAuthorsPanel.add(ManuscriptAuthorsLabel);
		manuscriptAuthorsPanel.add(textArea);
		ManuscriptFilePanel.add(ManuscriptFileLabel);
		ManuscriptFilePanel.add(manuscriptFileField);
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
	
	/**
	 * Method to create view to display all manuscripts belonging to author.
	 * @return JPanel to display Manuscripts for selection.
	 */
	public JPanel viewManuscriptListView() {
		
		JPanel manuscriptListPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		for (myCounter = 0; myCounter < myManuscriptList.size(); myCounter++) {
			
			JButton button = new JButton(myManuscriptList.get(myCounter).getTitle() + "	" + myManuscriptList.get(myCounter).getSubmissionDate());
			button.setActionCommand(myManuscriptList.get(myCounter).getTitle());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					setChanged();
				    notifyObservers(myManuscriptList.get(myCounter).getTitle());  
		        }  
		    });
			
			group.add(button);
			manuscriptListPanel.add(button);
			
		}
		
		manuscriptListPanel.setOpaque(true);
		
		manuscriptListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Manuscript List"));
		
		return manuscriptListPanel;
		
	}
	
}
