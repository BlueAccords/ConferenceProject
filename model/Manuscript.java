/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;


/**
 * This class represents a manuscript and stores any relevant information.
 * 
 * @author James Roberts, Ian Waak, Ayub Tiba, Vincent Polio, Vinh Le
 * @version 4/30/2017
 */
public class Manuscript implements Serializable{

	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 96995919879996851L;
	
	/** The manuscripts's title. */
	private String myTitle;
	
	/** The body of the manuscript. */
	private File myManuscriptFile;
	
	/** The body of the recommendation. */
	private File myRecommendation;
	
	/** The list of authors by username, index 0 is the primary author. */
	private ArrayList<Author> myAuthors;
	
	/** The list of reviews given to this manuscript. */
	private ArrayList<File> myReviews;
	
	/** The list of reviewers assigned to this manuscript. */
	private ArrayList<Reviewer> myReviewerList;
	
	/** The minimum number of reviews in order for this manuscript 
	 * to be ready for recommendation.*/
	private static final int SUFFICIENT_REVIEWS = 3;
	
	/** The Manuscript submission date. */
	private Date mySubmissionDate;
	
	/**
	 * Constructors for The class.
	 * 
	 * @param theTitle The manuscript's title.
	 * @param theManuscriptFile The manuscripts's corresponding file.
	 * @param theMainAuthor The main author of the manuscript's username (email).
	 * 
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public Manuscript(String theTitle, File theManuscriptFile, Author theMainAuthor) {
		myTitle = theTitle;
		myAuthors = new ArrayList<Author>();
		myAuthors.add(theMainAuthor);
		myManuscriptFile = theManuscriptFile;
		myReviews = new ArrayList<File>();
		mySubmissionDate = new Date();
		myRecommendation = new File("");
		myReviewerList = new ArrayList<Reviewer>();
	}	
	
	/**
	 * Returns the date the manuscript was submitted.
	 * 
	 * @return the date the manuscript was submitted.
	 */
	public Date getSubmissionDate() {
		return new Date(mySubmissionDate.getTime());
	}
	
	public boolean isReviewInProgress() {
		return myReviewerList.size() > 0;
	}
	
	public void addReviewer(Reviewer theReviewer) {
		myReviewerList.add(theReviewer);
	}
	
	/**
	 * Setter for the date the manuscript was submitted.
	 * 
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	public void setSubmissionDate(Date theDate) {
		mySubmissionDate = theDate;
	}
	
	/**
	 * Getter for the paper's title.
	 * 
	 * @return The title of the paper.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public String getTitle() {
		return myTitle;
	}
	
	
	/**
	 * Getter for the paper file.
	 * 
	 * @return The file holding the paper.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public File getManuscriptFile() {
		return myManuscriptFile;
	}
	
	
	/**
	 * Getter for the list of authors.
	 * 
	 * @return The paper's authors.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<Author> getAuthors() {
		return myAuthors;
	}
	
	
	/**
	 * This method will return the Author email.
	 * 
	 * @return the collection of Author email
	 */
	public ArrayList<String> getAuthorEmails() {
		ArrayList<String> emails = new ArrayList<String>();
		for (Author author : myAuthors) {
			emails.add(author.getUser().getEmail());
		}
		return emails;
	}
	
	
	/**
	 * Adds an author to the paper.
	 * 
	 * @param theAuthor The author to be added.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 * @throws AuthorExistsInListException 
	 */
	public void addAuthor(Author theAuthor) throws AuthorExistsInListException {
		if (authorNotInList(theAuthor))
			myAuthors.add(theAuthor);
		else
			throw new AuthorExistsInListException();
	}
	
	/**
	 * Adds a review to the manuscript by adding file to myReviews list.
	 * 
	 * @author Morgan Blackmore
	 * @version 5/16/17
	 * @throws NullPointerException if theReview is null
	 * 
	 */
	public void addReview(File theReview) throws NullPointerException{
		if (theReview == null){
			throw new NullPointerException();
		}
		myReviews.add(theReview);

	}
	
	/**
	 * Gets the list of reviews.
	 * 
	 * @author Morgan Blackmore
	 * @version 5/26/17
	 * @return ArrayList of reviews myReviews
	 */
	public ArrayList<File> getReviews(){
		return new ArrayList<File>(myReviews);
	}
	
	/**
	 * Instantiates myRecommendation with theRecommendation.
	 * Checks if this manuscript has sufficient reviews, if not, throws Exception
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception if manuscript does not have sufficient reviews
	 * @throws NullPointerException if theRecommendation is null
	 * @param theRecommendation file
	 * 
	 */
	public void addRecommendation(File theRecommendation) throws NullPointerException, Exception{
		if (theRecommendation == null ){
			throw new NullPointerException();
		}
		if (myReviews.size()< SUFFICIENT_REVIEWS) {
			throw new Exception("Insuffiecient reviews.  Need: " + SUFFICIENT_REVIEWS + " Have: " +myReviews.size());
		}
		//if exception not thrown, myRecommendation instantiated
		myRecommendation = theRecommendation;
	}
	
	/**
	 * Getter for myRecommendation file 
	 * Will return null if no recommendation has been submitted yet.
	 * 
	 * @return myRecommendation file
	 * @author Morgan Blackmore
	 * @version 5/42/17
	 */
	public File getRecommendation(){
		return myRecommendation;
	}
	
	/**
	 * Checks whether or not the passed in author is part of the list of
	 * authors for this manuscript.
	 * 
	 * Preconditions:
	 * 	theAuthor must be non-null
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
	 * @param theAuthor The author to compare to the list of authors belonging to this manuscript.
	 * @return A boolean true if author exists within manuscript author list, false otherwise
	 */
	public boolean doesManuscriptBelongToAuthor(Author theAuthor) {
		boolean authorIsFound = false;

		for (Author author : myAuthors) {
			if(author.getUser().getEmail().equals(theAuthor.getUser().getEmail())) {
				authorIsFound = true;
			}
		}

		return authorIsFound;
	}
	
	
	public boolean doesManuscriptBelongToReviewer(Reviewer theReviewer) {
		boolean reviewerIsFound = false;

		for (Author author : myAuthors) {
			author.getUser();
			author.getUser().getEmail();
			theReviewer.getUser();
			theReviewer.getUser().getEmail();
			if (author.getUser().getEmail().equals(theReviewer.getUser().getEmail())) {
				reviewerIsFound = true;
				break;
			}
		}
		
		return reviewerIsFound;
	}
	
	/**
	 * Helper method to determine that theAuthor is not already part of the author list.
	 * It does this by checking that it is first not an object already within the list, and 
	 * next that the email of theAuthor does not already belong to one in myAuthors.
	 * 
	 * @param theAuthor The new author to check
	 * @return If theAuthor is already within myAuthors list
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	private boolean authorNotInList (Author theAuthor) {
		boolean authorNotInList = true;
		
		//checks if the same User object is already in list
		authorNotInList = !(myAuthors.contains(theAuthor));	
		
		//checks if a User in myAuthors has the same email
		for (Author authors : myAuthors) {
			authorNotInList = !(authors.getUser().getEmail().equals(theAuthor.getUser().getEmail()));	
		}
		
		return authorNotInList;
	}
	
	
	/**
	 * Replaces the file of the paper with an updated version.
	 * 
	 * @param thePaper The new paper file.
	 * 
	 * @author James Roberts
	 * @author Casey Anderson
	 * @version 4/27/2017
	 */
	public void updateManuscript(File theManuscript) {
		myManuscriptFile = theManuscript;
	}

	
	/**
	 * @return the myReviewerList
	 */
	public ArrayList<Reviewer> getReviewerList() {
		return myReviewerList;
	}


	public static int getSufficientReviews() {
		return SUFFICIENT_REVIEWS;
	}


	/**
	 * @param myReviewerList the myReviewerList to set
	 */
	public void setReviewerList(ArrayList<Reviewer> myReviewerList) {
		this.myReviewerList = myReviewerList;
	}
	
	/**
	 * This method will check if the manuscript's number of reviews
	 * meets the minimum number required for the SubProgram Chair to make a 
	 * recommendation and return a boolean indicating so.
	 * 
	 * PreConditions:
	 * 	myReviews list must be instantiated(does not need to have items).
	 * 
	 * @author Ryan Tran
	 * @version 5/28/17
	 * @return a boolean indicating if if manuscript is eligible for SPC recommendation or not.
	 */
	public boolean isEligibleForRecommendation() {
		boolean isEligible = false;

		if(this.getReviews().size() >= SUFFICIENT_REVIEWS) {
			isEligible = true;
		}

		return isEligible;
	}
	
	/**
	 * Custom Exception to throw when author is found within the author list already.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	public class AuthorExistsInListException extends Exception {

		/**
		 * Serialized UID for this exception class
		 */
		private static final long serialVersionUID = 5998939099739282922L;
		
		private static final String ERROR_MESSAGE = "Author already exists in the author list";
		
		/**
		 * AuthorExistsInListException constructor. Passes the ERROR_MESSAGE into the super
		 * class constructor.
		 * 
		 * @author Connor Lundberg
		 * @version 5/13/2017
		 */
		public AuthorExistsInListException () {
			super (ERROR_MESSAGE);
		}
	}



}
