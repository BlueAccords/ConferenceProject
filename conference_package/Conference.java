package conference_package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a conference object and stores all relevant information, related papers and users.
 * @author James Roberts
 * @version 4/27/2017
 */
public class Conference implements Serializable{
	/**
	 * The classes serial Id.
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
	 * @return t/f if paper was accepted.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public boolean addPaper(Paper thePaper) {
		if(thePaper.getSubmissionDate().getTime() <= myPaperDeadline.getTime()) { 
			myPapers.add(thePaper);
			return true;
		} else {
			return false;
		}
	}
}
