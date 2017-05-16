/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a conference object and stores all relevant information, related papers and users.
 * @author Ayub Tiba, Ian Waak, James Robert, Vincent Povio, Vinh Le
 * @version May 7 2017
 */

public class Conference implements Serializable{
	private static final String PERSISTENT_DATA_LOCATION = "./persistent_storage_folder/conferenceData.ser";
	
	/** The maximum manuscript submissions. */
	private static final int MAX_AUTHOR_SUBMISSIONS = 5;
	
	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = -8616952866177111334L;
	
	/** The name of the conference. */
	private String myConferenceName;
	
	/** The deadline for manuscript submissions. */
	private Date myManuscriptDeadline;
	
	/** The deadline for reviews. */
	private Date myReviewDeadline;
	
	/** The deadline for recommendations. */
	private Date myRecDeadline;
	
	/** The deadline for recommendations. */
	private Date myFinalDeadline;
	
	/** All papers submitted to the conference. */
	private ArrayList<Manuscript> myManuscripts;
	
	/** All Authors with Manuscripts's submitted to the conference. */
	private ArrayList<Author> conferenceAuthors;
	
	/** All eligible reviewers in the conference. */
	private ArrayList<Reviewer> conferenceReviewers;	
	
	/** All conference SPCs. */
	private ArrayList<SubprogramChair> conferenceSubprogramChairs;
	
	/** The conference Program Chair. */
	private User conferenceProgramChair;
	
	/**
	 * Constructors for the object.
	 * @param theConferenceName The name of the conference.
	 * @param theManuscriptDeadline The paper deadline.
	 * @param theReviewerDeadline The review deadline.
	 * @param theRecommendationDeadline The recommendation deadline.
	 * @param theFinalDecisionDeadline The final decision deadline.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Conference(String theConferenceName, Date theManuscriptDeadline, Date theReviewerDeadline, Date theRecommendationDeadline, Date theFinalDecisionDeadline) {
		myConferenceName = theConferenceName;
		myManuscriptDeadline = new Date(theManuscriptDeadline.getTime());
		myReviewDeadline = new Date(theReviewerDeadline.getTime());
		myRecDeadline = new Date(theRecommendationDeadline.getTime());
		myFinalDeadline = new Date(theFinalDecisionDeadline.getTime());
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
	 * This method will search and return the Author in the conference that corresponds to the passed User.
	 * Returns null if theUser is not an Author in this conference.
	 * @param theUser
	 * @return The Author object associated with theUser, null if theUser
	 * is not an Author in this conference. 
	 */
	public Author getAuthor(User theUser) {
		Author matchingAuthor = null;
		for (Author author : conferenceAuthors) {
			if (author.getUser().getEmail().equals(theUser.getEmail())) {
				matchingAuthor = author;
			}
		}
		return matchingAuthor;
	}
	
	/**
	 * This method will search and return the Reviewer in the conference 
	 * that corresponds to the passed User.
	 * Returns null if theUser is not a Reviewer in this conference.
	 * @param theUser
	 * @return The Reviewer object associated with theUser, null if theUser
	 * is not an Reviewer in this conference. 
	 */
	public Reviewer getReviewer(User theUser) {
		Reviewer matchingReviewer = null;
		for (Reviewer reviewer : conferenceReviewers) {
			if (reviewer.getUser().getEmail().equals(theUser.getEmail())) {
				matchingReviewer = reviewer;
			}
		}
		return matchingReviewer;
	}
	
	/**
	 * This method will search and return the SubprogramChair in the conference 
	 * that corresponds to the passed User.
	 * Returns null if theUser is not a SubprogramChair in this conference.
	 * @param theUser
	 * @return The SubprogramChair object associated with theUser, null if theUser
	 * is not a SubprogramChair in this conference. 
	 */
	public SubprogramChair getSubprogramChair(User theUser) {
		SubprogramChair matchingSPC = null;
		for (SubprogramChair subPC : conferenceSubprogramChairs) {
			if (subPC.getUser().getEmail().equals(theUser.getEmail())) {
				matchingSPC = subPC;
			}
		}
		return matchingSPC;
	}
	
	/** 
	 * Returns a collection of all Manuscripts submitted to the conference.
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
	 * This method will add  a Manuscript to the conference 
	 * If submitted before the deadline and 
	 * if every associated author has not submitted more > max papers.
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
		
		//Now add paper to its respective author and create a new author if necessary. 
		for (User author : theManuscript.getAuthors()) {
			Author potentialAuthor = getAuthor(author);
			if (potentialAuthor != null) { //Author already exists, add the manuscript to them.
				potentialAuthor.addManuscript(theManuscript);
			} else { //This user is not yet an author, create a new Author in the conference for them.
				potentialAuthor = new Author(author);
				potentialAuthor.addManuscript(theManuscript);
				conferenceAuthors.add(potentialAuthor);
				
			}
		}
		
		//No exceptions thrown so it is ok to add the paper.
		myManuscripts.add(theManuscript);
	}
	
	/**
	 * This method will get the author's email's from the paper, look at each paper
	 * in the conference and check all things in the author's array list while keeping track
	 * if number of submitted papers > max - 1 then it should return false.
	 * @param theManuscript The Paper being submitted
	 * @return boolean that check if the manuscipt satisfies the conditions
	 * @author Vinh Le, Ian Waak
	 * @version 4/29/2017
	 * @version 4/30/2017 - added newAuthors/existingAuthors to fix problem when comparing ID/submittedPaperID
	 */
	public boolean isValidNumberOfSubmissions(Manuscript theManuscript) {
		boolean check = true;	
		ArrayList<User> authors = new ArrayList<User>();
		authors.addAll(theManuscript.getAuthors());
		//System.out.println(authors.size());
		for (User author : authors) {
			//look up author that corresponds with this user & make sure they exist.
			Author potentialA = getAuthor(author);
			//System.out.println(potentialA.getNumSubmittedManuscripts());
			if (potentialA != null) {
				if (potentialA.getNumSubmittedManuscripts() >= MAX_AUTHOR_SUBMISSIONS) {
					check = false;
				}
			}
		}				
		return check;
	}
	
	/**
	 * This method will test if a manuscript was submitted on time. Returns true if it is before
	 * the submission deadline. If past deadline, false is returned.
	 * @param theManuscript The paper being submitted.
	 * @return boolean if paper was accepted.
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
	 * This method will return all Subprogram Chairs of the conference.
	 * @return all Subprogram Chairs of the conference.
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public ArrayList<SubprogramChair> getConfSPCs () {
		return conferenceSubprogramChairs;
	}
	
	/**
	 * This method will return the Program Chair of the conference.
	 * @return all Program Chair of the conference. 
	 * @author Ayub Tiba
	 * @version 4/28/2017
	 */
	public User getConfPC () {
		return conferenceProgramChair;
	}
	
	/**
	 * This method will add the passed User as a reviewer for the conference.
	 * @param theReviewer the ID for the reviewer to be added
	 * @author: Ian Waak
	 * @version: 4/30/2017
	 */
	public Reviewer addReviewer(User theUser) {
		Reviewer newRev = new Reviewer(theUser);
		conferenceReviewers.add(newRev);
		return newRev;
	}
	
	/**
	 * This method will add the passed User as a Subprogram Chair for the conference
	 * @param theReviewer the ID for the reviewer to be added
	 * @author: James Roberts
	 * @version: 5/1/2017
	 */
	public SubprogramChair addSubprogramChair(User theUser) {
		SubprogramChair newSPC = new SubprogramChair(theUser);
		conferenceSubprogramChairs.add(new SubprogramChair(theUser));
		return newSPC;
	}


	/**
	 * Writes the passed list of conferences to a file for storage and retrieval.
	 * returns true if write successful, false otherwise.
	 * @param theConferences List of all conferences.
	 * @return t/f if write successful.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public static boolean writeConferences(ArrayList<Conference> theConferences) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		//Try to open both streams and write the ArrayList
		try {
			fout = new FileOutputStream(PERSISTENT_DATA_LOCATION);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(theConferences);

		} catch (Exception e) {
			//e.printStackTrace(); //Maybe remove this in final version?
			return false;
		} finally {
			// Close both streams, return false if there is an issue.
			if(fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
					return false;
				}
			}

			if(oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Reads the ArrayList of Conferences stored in the file destination the object
	 * was initialized with, returns null if the operation failed.
	 * @return The list of stored conferences.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public static ArrayList<Conference> readConferences() {
		ArrayList<Conference> allConfs = new ArrayList<Conference>();
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		//attempt to open the file and read in the ArrayList
		try {
			fin = new FileInputStream(PERSISTENT_DATA_LOCATION);
			ois = new ObjectInputStream(fin);
			//This unchecked cast should be ok since we are the ones in control of the system.
			allConfs = (ArrayList<Conference>) ois.readObject();

		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		} finally {
			//close both streams
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					// need to do something here to indicate failure?
				}
			}

			if (fin != null) {
				try {
					fin.close();
				} catch (Exception e) {
					// need to do something here to indicate failure?
				}
			}
		}

		return allConfs;
	}

}
