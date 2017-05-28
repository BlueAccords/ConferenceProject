package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Manuscript;
import model.Reviewer;
import model.User;

/**
 * Displays a table of manuscripts to which the SubprogramChair has been assigned along with their status.
 * Table contains links to all other views the SPC needs.
 * 
 * 
 * @author Morgan Blackmore
 * @version 5/27/17
 * 
 *
 */
public class SPCHomeView extends Observable {
	/**List of Manuscripts assigned to SPC **/
	private ArrayList<Manuscript> myManuscriptList;

	/**User ID **/
	private User myUser;

	/** Conference to with SPC is assigned. **/
	private Conference myConference;

	/**
	 * Default Constructor
	 * 
	 */
	public SPCHomeView(User theUser, Conference theConference){
		myManuscriptList = new ArrayList<Manuscript>();
		myUser = theUser;
		myConference = theConference;
	}

	/**
	 * Constructor that takes an ArrayList<Manuscript>
	 * 
	 * @param theManuscriptList must be non-null list of Manuscripts
	 */
	public SPCHomeView(User theUser, ArrayList<Manuscript> theManuscriptList, Conference theConference){
		myManuscriptList = theManuscriptList;
		myUser = theUser;
		myConference = theConference;
	}

	/**
	 * JPanel with SPC_HomeView Layout, calls displayTable() to create inner display panel
	 * 
	 * @return JPanel displaying internal JPanel
	 */
	public JPanel homeViewLayout(){
		JPanel baseLayer = new JPanel(new BorderLayout());
		JPanel displayTable = displayTable();
		baseLayer.add(displayTable, BorderLayout.CENTER);

		return baseLayer;

	}

	public JPanel displayTable() {
		//
		JPanel displayTable = new JPanel(new GridLayout(0,6));

		//Column Titles
		JLabel title = new JLabel("Title");
		JLabel reviewer1 = new JLabel("Reviewer 1");
		JLabel reviewer2 = new JLabel("Reviewer 2");
		JLabel reviewer3 = new JLabel("Reviewer 3");
		JLabel recommendation = new JLabel("Recommendation");
		JLabel ManSubDeadline = new JLabel("Manuscript Submission Deadline");

		//Add labels to displayTable to act as column headers
		displayTable.add(title);
		displayTable.add(reviewer1);
		displayTable.add(reviewer2);
		displayTable.add(reviewer3);
		displayTable.add(recommendation);
		displayTable.add(ManSubDeadline);

		//build that MFn' table
		ButtonGroup bGroup = new ButtonGroup();
		for (int i = 0; i < myManuscriptList.size(); i++){

			Manuscript thisManuscript = myManuscriptList.get(i);

			/*First column: 
			 * display labels of manuscript titles
			 */
			JLabel thisTitle = new JLabel(thisManuscript.getTitle());
			displayTable.add(thisTitle);
			
			/*
			 * Check if manuscript submission is still open, if so, display: 
			 * "manuscript submission is still open" 
			 */
			/*if manuscript deadline is after today, it is still open.
			 * Display across all three reviewer columns
			 */
			if (myConference.getManuscriptDeadline().after(new Date())){
				JLabel subDeadline = new JLabel("Manuscript submission is still open.");
				displayTable.add(subDeadline);
				displayTable.add(subDeadline);
				displayTable.add(subDeadline);

			} else {

				/*Second, third, and fourth columns:
				 * display status of Reviews
				 * 3 options - awaiting review, assign reviewer, review submitted
				 */

				ArrayList<Reviewer> reviewerList = thisManuscript.getReviewerList();
				ArrayList<File> reviewList = myManuscriptList.get(i).getReviews(); 

				/*this set-up will misrepresent which reviewers have submitted their reviews
				 * Won't display properly if Reviewer1 is not the first to submit review 
				 * Fix would mean connecting the review file with the Reviewer and right now our model class can't do that
				 * Will get back to it when time allows.  For now, this displays the data, just misleadingly. 
				 */

				/*for each reviewer look to see if there is a review.
				 * If there is a reviewer but no review display "awaiting review"
				 * If there is a reviewer and a review, display "review submitted"
				 */

				
				/*magic number 3 comes from the number of reviewers to display
				 * counts down to indicate how many reviewer spots are empty in table
				 * and need assignReviewer buttons.
				 */
				int AssignReviewerButtonsNeededCounter = 3;
				
				for (Reviewer reviewer : reviewerList){
					
					//reviewer but no review submitted
					if (!reviewList.get(i).exists()){
						JLabel awaitingReview = new JLabel("Awaiting review.");
						displayTable.add(awaitingReview);
						AssignReviewerButtonsNeededCounter--;
					} 
					//reviewer and review submitted
					else {
						JLabel reviewSubmitted = new JLabel("Review submitted.");
						displayTable.add(reviewSubmitted);
						AssignReviewerButtonsNeededCounter--;
					}

				}//endfor
				
				/*
				 * Now backfill for any empty spots in table where there was no
				 * reviewer in reviewerList
				 */
				for (int j = 0; j<AssignReviewerButtonsNeededCounter; j++){
					
					JButton assignReviewerButton = new JButton("Assign Reviewer");
					assignReviewerButton.setActionCommand("Assign Reviewer");
					assignReviewerButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							/*
							 * The code below all follows Ryan's example from the ConferenceListView class
							 * But I'm not sure its necessary here, I wrote a simple
							 * replacement below
							 */
//							int manuscriptIndex = getClickedManuscript(bGroup, e);
//							if(manuscriptIndex > -1 ) {
//								setChanged();
//								notifyObservers(myManuscriptList.get(manuscriptIndex));
//								setChanged();
//								notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.ASSIGN_REVIEWER);
//							} else {
//								//should display to gui not console
//								System.out.println("Manuscript not found.");
//							}
							setChanged();
							notifyObservers(thisManuscript);
							setChanged();
							notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.ASSIGN_REVIEWER);
								
						}
					});
					
					displayTable.add(assignReviewerButton);	
					
				} //endfor

				/*
				 * 5th Column: 
				 * display Recommendation status
				 * 3 options - rec submitted, awaiting reviews, submit rec 
				 */
				//if there is already a recommendation
				if (thisManuscript.getRecommendation().exists()){
					JLabel recommendationSubmitted = new JLabel("Recommendation Submitted.");
					displayTable.add(recommendationSubmitted);
					
				}
				//if there are insufficient reviews
				if (thisManuscript.getReviews().size()<3) {
					JLabel awaitingReviews = new JLabel("Awaiting Reviews.");
					displayTable.add(awaitingReviews);
				}
				//if there are sufficient reviews and no recommendation exists
				if (thisManuscript.getReviews().size() >= 3 && !thisManuscript.getRecommendation().exists()){
					JButton submitRecommendation = new JButton("Submit recommendation.");
					submitRecommendation.setActionCommand("Submit recommendation.");
					submitRecommendation.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							
//							int manuscriptIndex = getClickedManuscript(bGroup, e);
//							if(manuscriptIndex > -1 ) {
//								setChanged();
//								notifyObservers(myManuscriptList.get(manuscriptIndex));
//								setChanged();
//								notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.SUBMIT_RECOMMENDATION);
//							} else {
//								//should display to gui not console
//								System.out.println("Manuscript not found.");
//							}
							setChanged();
							notifyObservers(thisManuscript);
							setChanged();
							notifyObservers(Controller.SUBPROGRAM_CHAIR + Controller.ASSIGN_REVIEWER);
								
						}
					});
					
					displayTable.add(submitRecommendation);	
				}
				

				/*
				 * 6th Column:
				 * display ManuscriptSubmissionDeadline
				 */
				JLabel thisMansDeadline = new JLabel(myConference.getManuscriptDeadline()+ "");
				displayTable.add(thisMansDeadline);
				

			} // Endelse
		} // Endfor



		return displayTable;
	}

}
