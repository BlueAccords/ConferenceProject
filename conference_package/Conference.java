package conference_package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a conference object and stores all relevant information, related papers and users.
 * @author James Roberts, Vinh Le, Ian Waak, Vincent Povio
 * @version 4/30/2017
 */
public class Conference implements Serializable{
	/**
	 * The class's serial Id.
	 */
	private static final long serialVersionUID = -8616952866177111334L;
	/**
	 * The name of the conference.
	 */
	private String myConferenceName;
	/**
	 * The deadline for manuscript submissions.
	 */
	private Date myPaperDeadline;
	/**
	 * The deadline for reviews.
	 */
	private Date myReviewDeadline;
	/**
	 * The deadline for reviews.
	 */
	private Date myRecDeadline;
	/**
	 * The deadline for recommendations.
	 */
	private Date myFinalDeadline;
	/**
	 * All papers submitted to the conference.
	 */
	private ArrayList<Paper> myPapers;
	
	/**
	 * All eligible reviewers in the conference.
	 */
	private ArrayList<User> conferenceReviewers;	
	/**
	 * All conference SPCs.
	 */
	private ArrayList<User> conferenceSubprogramChairs;
	/**
	 * The conference Program Chair.
	 */
	private User conferenceProgramChair;
	
	/**
	 * Constructor for the object.
	 * @param theConferenceName The name of the conference.
	 * @param thePDead The paper deadline.
	 * @param theRevDead The review deadline.
	 * @param theRecDead The recommendation deadline.
	 * @param theFinalDead The final decision deadline.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Conference(String theConferenceName, Date thePDead, Date theRevDead, Date theRecDead, Date theFinalDead) {
		myConferenceName = theConferenceName;
		myPaperDeadline = new Date(thePDead.getTime());
		myReviewDeadline = new Date(theRevDead.getTime());
		myRecDeadline = new Date(theRecDead.getTime());
		myFinalDeadline = new Date(theFinalDead.getTime());
		myPapers = new ArrayList<Paper>();
		conferenceReviewers = new ArrayList<User>();
	}
	
	/**
	 * Getter for the name of the conference.
	 * @return The conference's name.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public String getConferenceName() {
		return myConferenceName;
	}
	
	/**
	 * Getter for the deadline of paper submissions.
	 * @return The deadline of paper submissions.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Date getPaperDeadline() {
		// need to make copy to encapsulate.
		return new Date(myPaperDeadline.getTime());
	}
	
	/**
	 * Getter for the deadline for reviews.
	 * @return the review deadline.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Date getReviewDeadline() {
		// need to make copy to encapsulate.
		return new Date(myReviewDeadline.getTime());
	}
	
	/**
	 * Getter for the deadline for recommendations.
	 * @return the deadline for recommendations.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Date getRecDeadline() {
		// need to make copy to encapsulate.
		return new Date(myRecDeadline.getTime());
	}
	
	/**
	 * Getter for the final decision's deadline.
	 * @return the final decision's deadline.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Date getFinalDeadline() {
		// need to make copy to encapsulate.
		return new Date(myFinalDeadline.getTime());
	}
	
	/** 
	 * Returns a collection of all papers submitted to the conference.
	 * 
	 * @return All papers currently submitted to the conference.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<Paper> getPapers() {
		ArrayList<Paper> copy = new ArrayList<Paper>();
		copy.addAll(myPapers);
		return copy;
	}
	
	/**
	 * Adds a paper to the conference and returns true if it is before
	 * the submission deadline. If past deadline, the paper is not added and
	 * false is returned.
	 * @param thePaper The paper being submitted.
	 * @author James Roberts
	 * @version 5/1/2017
	 * @throws Exception if any Author of thePaper has already submitted max Papers
	 * or if the Paper is submitted past the submission deadline. 
	 */
	public void addPaper(Paper thePaper) throws Exception {
		if (!isSubmittedOnTime(thePaper)) {
			throw new Exception("Paper submitted past deadline.");
		}
		if (!isValidNumberOfSubmissions(thePaper)) {
			throw new Exception("An Author or Coauthor has already submitted max Papers.");
		}
		//No exceptions thrown so it is ok to add the paper.
		myPapers.add(thePaper);
			
	}
	
	/**
	 * This method will get the author's id from the paper, look at each paper
	 * in the conference and check all things in the author's array list while keeping track
	 * if number of submitted papers > 4 then it should return false.
	 * @param thePaper The Paper being submitted
	 * @return 
	 * @author Vinh Le, Ian Waak
	 * @version 4/29/2017
	 * @version 4/30/2017 - added newAuthors/existingAuthors to fix problem when comparing ID/submittedPaperID
	 */
	public boolean isValidNumberOfSubmissions(Paper thePaper) {
		boolean check = false;	
		int counter = 0;
		
		//List of author names for new paper
		ArrayList<String> newAuthors = new ArrayList<String>();
		newAuthors.addAll(thePaper.getAuthors());
		
		//List of authors for existing papers in conference
		ArrayList<String> existingAuthors = new ArrayList<String>();
		for(Paper submittedPapers : myPapers) {
			existingAuthors.addAll(submittedPapers.getAuthors());
		}
		
		//Iterate through new paper authors
		for(String ID : newAuthors) {
			//Iterate through existing paper authors
			for(String submittedPaperID : existingAuthors) {
				//If new author equals existing author, add 1 to counter
				if (ID.equals(submittedPaperID)) {
					counter++;
				}
			}		
		}
		//Because counter starts at zero, we need to check for one less than the max allowed papers.
		if (counter < 4) {
			check = true;
		} else {
			check = false;
		}
		
		return check;
	}
	
	/**
	 * Adds a paper to the conference and returns true if it is before
	 * the submission deadline. If past deadline, the paper is not added and
	 * false is returned.
	 * @param thePaper The paper being submitted.
	 * @return t/f if paper was accepted.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public boolean isSubmittedOnTime(Paper thePaper) {
		if(thePaper.getSubmissionDate().getTime() <= myPaperDeadline.getTime()) { 
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns all Subprogram Chairs of the conference.
	 * @return all Subprogram Chairs of the conference.
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public ArrayList<User> getConfSPCs () {
		return conferenceSubprogramChairs;
	}
	
	/**
	 * Returns the Program Chair of the conference.
	 * @return the Program Chair of the conference. 
	 * @author Ayub Tiba
	 * @version 4/28/2017
	 */
	public User getConfPC () {
		return conferenceProgramChair;
	}
	
	/**
	 * Adds reviewers to the conference by their user ID.
	 * @param theReviewer the ID for the reviewer to be added
	 * @author: Ian Waak
	 * @version: 4/30/2017
	 */
	public void addReviewers(User theReviewer) {
		conferenceReviewers.add(theReviewer);
	}
	

}
