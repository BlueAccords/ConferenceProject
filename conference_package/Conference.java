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
	private static final int MAX_AUTHOR_SUBMISSIONS = 5;
	private static final int MAX_REVIEWER_PAPERS = 8;
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
	private Date myManuscriptDeadline;
	/**
	 * The deadline for reviews.
	 */
	private Date myReviewDeadline;
	/**
	 * The deadline for recommendations.
	 */
	private Date myRecDeadline;
	/**
	 * The deadline for recommendations.
	 */
	private Date myFinalDeadline;
	
	/**
	 * All papers submitted to the conference.
	 */
	private ArrayList<Manuscript> myManuscripts;
	
	/**
	 * All Authors with Manuscripts's submitted to the conference.
	 */
	private ArrayList<Author> conferenceAuthors;
	/**
	 * All eligible reviewers in the conference.
	 */
	private ArrayList<Reviewer> conferenceReviewers;	
	
	/**
	 * All conference SPCs.
	 */
	private ArrayList<SubprogramChair> conferenceSubprogramChairs;
	
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
		myManuscriptDeadline = new Date(thePDead.getTime());
		myReviewDeadline = new Date(theRevDead.getTime());
		myRecDeadline = new Date(theRecDead.getTime());
		myFinalDeadline = new Date(theFinalDead.getTime());
		myManuscripts = new ArrayList<Manuscript>();
		conferenceAuthors = new ArrayList<Author>();
		conferenceReviewers = new ArrayList<Reviewer>();
		conferenceSubprogramChairs = new ArrayList<SubprogramChair>();
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
		return new Date(myManuscriptDeadline.getTime());
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
	 * Returns a collection of all Manuscripts submitted to the conference.
	 * 
	 * @return All Manuscripts currently submitted to the conference.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<Manuscript> getManuscripts() {
		ArrayList<Manuscript> copy = new ArrayList<Manuscript>();
		copy.addAll(myManuscripts);
		return copy;
	}
	
	/**
	 * Adds a Manuscript to the conference if submitted before the deadline
	 * and if every associated author has not submitted more > max papers.
	 * Pre: Passed Paper is not null.
	 * @param theManuscript The paper being submitted.
	 * @author James Roberts
	 * @version 5/1/2017
	 * @throws Exception if any Author of thePaper has already submitted max Papers
	 * or if the Paper is submitted past the submission deadline. 
	 */
	public void addManuscript(Manuscript theManuscript) throws Exception {
		if (!isSubmittedOnTime(theManuscript)) {
			throw new Exception("Paper submitted past deadline.");
		}
		if (!isValidNumberOfSubmissions(theManuscript)) {
			throw new Exception("An Author or Coauthor has already submitted max Papers.");
		}
		//No exceptions thrown so it is ok to add the paper.
		myManuscripts.add(theManuscript);
	}
	
	/**
	 * This method will get the author's id's from the paper, look at each paper
	 * in the conference and check all things in the author's array list while keeping track
	 * if number of submitted papers > 4 then it should return false.
	 * @param theManuscript The Paper being submitted
	 * @return 
	 * @author Vinh Le, Ian Waak
	 * @version 4/29/2017
	 * @version 4/30/2017 - added newAuthors/existingAuthors to fix problem when comparing ID/submittedPaperID
	 */
	public boolean isValidNumberOfSubmissions(Manuscript theManuscript) {
		boolean check = false;	
		int counter = 0;
		
		//List of author names for new paper
		ArrayList<String> newAuthors = new ArrayList<String>();
		newAuthors.addAll(theManuscript.getAuthors());
		
		//List of authors for existing papers in conference
		ArrayList<String> existingAuthors = new ArrayList<String>();
		for(Manuscript submittedPapers : myManuscripts) {
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
		if (counter < MAX_AUTHOR_SUBMISSIONS) {
			check = true;
		} else {
			check = false;
		}
		
		return check;
	}
	
	/**
	 * Tests if a manuscript was submitted on time. Returns true if it is before
	 * the submission deadline. If past deadline, false is returned.
	 * @param theManuscript The paper being submitted.
	 * @return t/f if paper was accepted.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public boolean isSubmittedOnTime(Manuscript theManuscript) {
		if(theManuscript.getSubmissionDate().getTime() <= myManuscriptDeadline.getTime()) { 
			
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
	public ArrayList<SubprogramChair> getConfSPCs () {
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
	 * Adds the passed User as a reviewer for the conference.
	 * @param theReviewer the ID for the reviewer to be added
	 * @author: Ian Waak
	 * @version: 4/30/2017
	 */
	public void addReviewer(User theUser) {
		
		conferenceReviewers.add(new Reviewer(theUser));
	}
	
	/**
	 * Adds the passed User as a Subprogram Chair for the conference
	 * @param theReviewer the ID for the reviewer to be added
	 * @author: James Roberts
	 * @version: 5/1/2017
	 */
	public void addSubprogramChair(User theUser) {
		
		conferenceSubprogramChairs.add(new SubprogramChair(theUser));
	}
	 
}
