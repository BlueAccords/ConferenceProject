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

public class UI_Author_Conference_View extends Observable  implements Observer {
	
	  private ArrayList<Conference> myConferenceList;
	  private ArrayList<Manuscript> myManuscriptList;
	  private int myCounter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -703649288417940022L;
	
	public UI_Author_Conference_View() {
		myConferenceList = new ArrayList<Conference>();
		myManuscriptList = new ArrayList<Manuscript>();
		myCounter = 0;

		
	}
	
	public JPanel createConferenceList() {
		JPanel conferenceListPanel = new JPanel(new GridLayout(0,1));
		int i;
		ButtonGroup group = new ButtonGroup();
		for (myCounter = 0; myCounter < myConferenceList.size(); myCounter++) {
			JButton button = new JButton(myConferenceList.get(myCounter).getConferenceName() + "	" + myConferenceList.get(myCounter).getManuscriptDeadline());
			button.setActionCommand(myConferenceList.get(myCounter).getConferenceName());
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					setChanged();
				    notifyObservers(myConferenceList.get(myCounter).getConferenceName());  
		        }  
		    }); 
			group.add(button);
			conferenceListPanel.add(button);
			
		}
		conferenceListPanel.setOpaque(true);
		conferenceListPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference List"));
		return conferenceListPanel;
		
	}
	
	public JPanel createConferenceOptions() {
		JPanel conferenceOptionPanel = new JPanel(new GridLayout(0,1));
		
		JButton submitButton = new JButton("Submit Manuscript");
		submitButton.setActionCommand("Submit Manuscript");
		submitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
		        notifyObservers("Submit Manuscript");  
		    }  
		}); 
		conferenceOptionPanel.add(submitButton);
		
		JButton viewManuscriptButton = new JButton("View Manuscripts");
		viewManuscriptButton.setActionCommand("View Manuscripts");
		viewManuscriptButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers("View Manuscripts");  
		    }  
		}); 
		conferenceOptionPanel.add(viewManuscriptButton);
		
		conferenceOptionPanel.setOpaque(true);
		conferenceOptionPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Conference Options"));
		return conferenceOptionPanel;
		
	}
	
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
	
	public JPanel submitManuscriptView() {
		JPanel createManuscriptPanel = new JPanel();
		JPanel manuscriptTitlePanel = new JPanel();
		JPanel manuscriptAuthorsPanel = new JPanel();
		JPanel ManuscriptFilePanel = new JPanel();
		JPanel ManuscriptSubmitPanel = new JPanel();
		JButton ManuscriptSubmitButton = new JButton("Submit");
		JTextField manuscriptTitleField = new JTextField(20);
		JTextField manuscriptFileField = new JTextField(20);
		JTextArea textArea = new JTextArea("Please Enter Author name and all Co-Authors seperated by a comma ','", 5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setLineWrap(true);
		ManuscriptSubmitButton.setActionCommand("Delete Manuscript");
		ManuscriptSubmitButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				setChanged();
				notifyObservers(manuscriptTitleField.getText() + "," + textArea.getText() + "," + manuscriptFileField.getText());  
		    }  
		}); 
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
	
	
	public JPanel viewManuscriptListView() {
		
		JPanel manuscriptListPanel = new JPanel(new GridLayout(0,1));
		int i;
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

	@Override
	public void update(Observable arg0, Object arg1) {

		//if (arg1 instanceof ArrayList<Conference>) {
			//myConferenceList = (ArrayList<Conference>) arg1;
		//}
		
		//else if (arg1 instanceof ArrayList<Manuscript>) {
		//	myManuscriptList = (ArrayList<Manuscript>) arg1
		//}
		
		
	}
	
	
	
}
