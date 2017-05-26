package client;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Manuscript;

public class ManuscriptListView extends Observable {
	/**
	 * List of Conferences available to Author
	 */
	private ArrayList<Manuscript> myManuscriptList;
	
	/**
	 * Counter to help iterate through Lists
	 */
	private int myCounter;
	
	public ManuscriptListView(ArrayList<Manuscript> theManuscriptList) {
		myManuscriptList = theManuscriptList;
		myCounter = 0;
	}
	
	/**
	 * Method to create view to display all manuscripts belonging to author.
	 * @return JPanel to display Manuscripts for selection.
	 * @author Casey Anderson
	 */
	public JPanel viewManuscriptListView() {
		JPanel manuscriptListPanel = new JPanel(new GridBagLayout());
		JPanel manuscriptButtonPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		for (myCounter = 0; myCounter < myManuscriptList.size(); myCounter++) {
			
			JButton button = new JButton(myManuscriptList.get(myCounter).getTitle() + "	" + myManuscriptList.get(myCounter).getSubmissionDate());
			button.setActionCommand(myManuscriptList.get(myCounter).getTitle());
			
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					
					// check index of button against index of conference in conference list
					int manuscriptIndex = getClickedManuscript(group, e);
					if(manuscriptIndex > -1) {
						setChanged();
						notifyObservers(myManuscriptList.get(manuscriptIndex));
						setChanged();
						notifyObservers(Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);
					} else {
						//TODO: Add better error handling here.
						System.out.println("Manuscript Not Found");
					}

				}   
		    });
			
			group.add(button);
			manuscriptButtonPanel.add(button);
			
		}
		
		manuscriptButtonPanel.setOpaque(true);
		
		manuscriptButtonPanel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Manuscript List"));
		manuscriptListPanel.add(manuscriptButtonPanel);
		return manuscriptListPanel;
		
	}
	
	/**
	 * This method will return the index conference linked to the button clicked
	 * by iterating through the button group, and seeing which button was pressed and
	 * finding the same conference in the conference list with that index.
	 * 
	 * @author Ryan Tran
	 * @author Casey Anderson
	 * @version 5/25/17
	 * @param theBtnGroup the button group containing the button that was pressed
	 * @param theAction The action, we are comparing the action's parent button to the button group
	 * @return the index of the conference object that is linked to the pressed button
	 */
	private int getClickedManuscript(ButtonGroup theBtnGroup, ActionEvent theAction) {
		int i = 0;
		boolean manuscriptFound = false;

		Enumeration groupElements = theBtnGroup.getElements();
		while (groupElements.hasMoreElements()) {
			AbstractButton originalGroupBtn = (AbstractButton)groupElements.nextElement();
			JButton actionSrcBtn = ((JButton)theAction.getSource());
			
			if(originalGroupBtn.equals(actionSrcBtn)) {
				System.out.println("match button found");
				manuscriptFound = true;
				break;
			}
			
			i++;
		}
		
		if(manuscriptFound) {
			return i;
		} else {
			return -1;
		}
	}
}
