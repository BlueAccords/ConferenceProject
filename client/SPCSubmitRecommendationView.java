package client;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
		
		//
		
		
	}
	
	

}
