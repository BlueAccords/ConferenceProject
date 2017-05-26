package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ManuscriptOptionView extends Observable {
	
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

}
