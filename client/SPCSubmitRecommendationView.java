package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import model.Manuscript;

/**
 * Displays panel for SubprogramChair to submit a recommendation for a manuscript 
 * 
 * @author Morgan Blackmore
 * @version 5/26/17
 *
 */
public class SPCSubmitRecommendationView extends Observable{

	/*
	 * see jfile chooser
	 * see file path
	 * submit button
	 * 
	 * display Success pop-up and return to SPCHomeView
	 */

	/** File to submit as recommendation.*/
	private File myRecommendation;
	/**Panel to display recommendation view.*/
	private JPanel myPanel;



	/**
	 * default constructor
	 */
	public SPCSubmitRecommendationView(){
		myRecommendation = new File("");
		myPanel = new JPanel(new GridLayout(0,7));
	}


	/**
	 * Creates a panel to display submit Recommendation options
	 * 
	 * @return JPanel myPanel
	 */

	public JPanel submitRecommendationView() {


		//labels to guide user
		JLabel scorePrompt = new JLabel("What is your recommendation?");
		JLabel filePrompt = new JLabel("Select a file to upload");
		JLabel filePathPrompt = new JLabel("File path");

		JSlider scaleSlider = new JSlider(0, 10);
		JFileChooser fileChooser = new JFileChooser();
		JLabel filePathDisplay = new JLabel();

		JButton submitBtn = new JButton("Submit");
		submitBtn.setActionCommand("Submit Recommendation");
		submitBtn.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				myRecommendation =  fileChooser.getSelectedFile();
				setChanged();
				notifyObservers(myRecommendation);  
				setChanged();
				notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.SUBMIT_RECOMMENDATION);

			}  
		});

		myPanel.add(scorePrompt);
		myPanel.add(scaleSlider);
		myPanel.add(filePrompt);
		myPanel.add(fileChooser);
		myPanel.add(filePathPrompt);
		myPanel.add(filePathDisplay);
		myPanel.add(submitBtn);

		return myPanel;

	}//end method



}
