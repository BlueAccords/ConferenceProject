package client;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Displays panel for SubprogramChair to submit a recommendation for a manuscript 
 * 
 * @author Morgan Blackmore
 * @version 5/26/17
 *
 */
public class SPCSubmitRecommendationView {
	
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
	private JPanel myJPanel;
	
	/**
	 * default constructor
	 */
	public SPCSubmitRecommendationView() {
		myRecommendation = new File("");
		myJPanel = new JPanel(new BoxLayout(myJPanel, BoxLayout.Y_AXIS));
		
		//labels to guide user
		JLabel scorePrompt = new JLabel("What is your recommendation?");
		JLabel filePrompt = new JLabel("Select a file to upload");
		JLabel filePath = new JLabel("File path");
		
		JSlider scaleSlider = new JSlider(0, 10);
		JFileChooser jfc = new JFileChooser();
		
		
	}
	
	

}
