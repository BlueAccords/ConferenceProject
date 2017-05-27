package client;

import java.io.File;

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
	
	/**
	 * default constructor
	 */
	public SPCSubmitRecommendationView() {
		myRecommendation = new File("Empty");
		
	}
	
	

}
