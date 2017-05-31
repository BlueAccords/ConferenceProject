/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;


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
	
	/** This is the actual byte array that stores the file in binary */
	private byte[] myManuscriptByteArray;
	
	/** The minimum number of reviews in order for this manuscript 
	 * to be ready for recommendation.*/
	private static final int SUFFICIENT_REVIEWS = 3;
	
	/**
	 * To determine if recommendation has been made.
	 */
	private boolean myRecommendationAssigned;
	
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
		try {
			saveFileAsByteArr(myManuscriptFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myReviews = new ArrayList<File>();
		mySubmissionDate = new Date();
		myRecommendation = new File("");
		myReviewerList = new ArrayList<Reviewer>();
		myRecommendationAssigned = false;
	}	
	
	
	/**
	 * Alternative Constructor, so we can add manuscripts to conferences
	 * when the submission deadline has already passed for a conference.
	 * 
	 * @param theTitle the manuscript title
	 * @param theManuscriptFile The file object for the manuscript
	 * @param theMainAuthor the main author for the manuscript
	 * @param theSubmissionDate the submission date for the manuscript
	 * 
	 * @author Ryan Tran
	 */
	public Manuscript(String theTitle, File theManuscriptFile, Author theMainAuthor, Date theSubmissionDate) {
		myTitle = theTitle;
		myAuthors = new ArrayList<Author>();
		myAuthors.add(theMainAuthor);
		myManuscriptFile = theManuscriptFile;
		myReviews = new ArrayList<File>();
		mySubmissionDate = theSubmissionDate;
		myRecommendation = new File("");
		myReviewerList = new ArrayList<Reviewer>();
		myRecommendationAssigned = false;
	}	
	
	/**
	 * Constructor that allows passing a list of co-authors in addition to the author
	 * @param theTitle The title of the manuscript
	 * @param theManuscriptFile the manuscript file object
	 * @param theMainAuthor the author of the manuscript
	 * @param theSubmissionDate submission date of the manuscript
	 * @param theCoAuthorsList the list of co-authors
	 */
	public Manuscript(String theTitle, File theManuscriptFile, Author theMainAuthor,
			Date theSubmissionDate, ArrayList<Author> theCoAuthorsList) {
		myTitle = theTitle;
		myAuthors = new ArrayList<Author>();
		myAuthors.add(theMainAuthor);
		myAuthors.addAll(theCoAuthorsList);
		myManuscriptFile = theManuscriptFile;
		myReviews = new ArrayList<File>();
		mySubmissionDate = theSubmissionDate;
		myRecommendation = new File("");
		myReviewerList = new ArrayList<Reviewer>();
		myRecommendationAssigned = false;
	}	

	
	
	
	public void setRecommendationAssigned(boolean theRecommendation) {
		myRecommendationAssigned = theRecommendation;
	}
	
	
	public boolean isrecommendationAssigned() {
		return myRecommendationAssigned;
	}
	
	
	/**
	 * Returns the date the manuscript was submitted.
	 * 
	 * @return the date the manuscript was submitted.
	 */
	public Date getSubmissionDate() {
		return new Date(mySubmissionDate.getTime());
	}
	
	
	/**
	 * Method to determine if any Reviewers have been assigned to this Manuscript.
	 * @return Boolean if any Reviewers have been assigned to this Manuscript.
	 * @author Casey Anderson.
	 */
	public boolean isReviewInProgress() {
		return myReviewerList.size() > 0;
	}
	
	
	/**
	 * Method to add a Reviewer to this Manuscript.
	 * 
	 * Pre:
	 * 	Reviewer must not be a duplicate reviewer already in myReviewerList.
	 * 
	 * @param theReviewer that is to be added to this Manuscript.
	 * 
	 * @author Casey Anderson
	 */
	public void addReviewer(Reviewer theReviewer) {
		// check if reviewer already exists in myReviewerList.
		// Silent omission if trying to add duplicate reviewers
		for(int i = 0; i < myReviewerList.size(); i++) {
			if(!(myReviewerList.get(i).getUser().getEmail().equals(theReviewer.getUser().getEmail()))) {
				theReviewer.addManuscriptToReviewer(this);
				myReviewerList.add(theReviewer);
				break;
			}
		}
		
		// in case list is empty the for loop doesn't run at all
		if(myReviewerList.size() == 0) {
			theReviewer.addManuscriptToReviewer(this);
			myReviewerList.add(theReviewer);
		}
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
	 * Getter for the Manuscript's title.
	 * 
	 * @return The title of the Manuscript.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public String getTitle() {
		return myTitle;
	}
		
	
	/**
	 * Getter for the Manuscript file.
	 * 
	 * @return The file holding the Manuscript.
	 * 
	 * @author James Roberts, Connor Lundberg
	 * @version 5/27/2017
	 */
	public File getManuscriptFile() {
		return myManuscriptFile;
	}
	
	
	/**
	 * Getter for the list of authors.
	 * 
	 * @return The Manuscript's authors.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<Author> getAuthors() {
		return myAuthors;
	}
	
	
	/**
	 * This method will return the Authors email list.
	 * 
	 * @return the ArrayList<String> of Author email for this Manuscript
	 */
	public ArrayList<String> getAuthorEmails() {
		ArrayList<String> emails = new ArrayList<String>();
		for (Author author : myAuthors) {
			emails.add(author.getUser().getEmail());
		}
		return emails;
	}
		
	
	/**
	 * Adds an author to the Manuscript.
	 * 
	 * @param theAuthor The author to be added.
	 * 
	 * @throws AuthorExistsInListException 
	 * 
	 * @author James Roberts, Connor Lundberg
	 * @version 5/25/2017
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
	 * @param theReview The File object containing the review to add to this Manuscript
	 * 
	 * @throws NullPointerException if theReview File object is null
	 * 
	 * @author Morgan Blackmore
	 * @version 5/16/17
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
	 * @return ArrayList<File> of reviews myReviews
	 * 
	 * @author Morgan Blackmore
	 * @version 5/26/17
	 */
	public ArrayList<File> getReviews(){
		return new ArrayList<File>(myReviews);
	}
	
	/**
	 * Instantiates myRecommendation with theRecommendation.
	 * Checks if this manuscript has sufficient reviews, if not, throws Exception
	 * 
	 * @param theRecommendation file
	 * 
	 * @throws Exception if manuscript does not have sufficient reviews
	 * @throws NullPointerException if theRecommendation is null
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
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
		this.setRecommendationAssigned(true);
	}
	
	
	/**
	 * Getter for myRecommendation file 
	 * Will return null if no recommendation has been submitted yet.
	 * 
	 * @return myRecommendation The File object holding the recommendation
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
	 */
	public File getRecommendation(){
		return myRecommendation;
	}
	
	/**
	 * Checks whether or not the passed in author is part of the list of
	 * authors for this manuscript.
	 * 
	 * Pre:
	 * 	theAuthor must be non-null
	 * 
	 * @param theAuthor The author to compare to the list of authors belonging to this manuscript.
	 * @return A boolean true if author exists within manuscript author list, false otherwise
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
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
	
	
	/**
	 * Method to check if theReviewer belongs to this Manuscript.
	 * 
	 * @param theReviewer to check if assigned to this Manuscript.
	 * 
	 * @return if Reviewer belongs to this Manuscript.
	 */
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
	 * Pre:
	 * 	theAuthor object must not be null
	 * 
	 * @param theAuthor The new author to check
	 * 
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
	 * @author James Roberts, Casey Anderson
	 * @version 4/27/2017
	 */
	public void updateManuscript(File theManuscript) {
		myManuscriptFile = theManuscript;
	}

	
	/**
	 * Method to return a list of Reviewers assigned to this Manuscript.
	 * 
	 * @return the myReviewerList
	 */
	public ArrayList<Reviewer> getReviewerList() {
		return myReviewerList;
	}

	
	/**
	 * Method to return the number of Reviews needed before a recommendation can be submitted 
	 * for this Manuscript.
	 * 
	 * @return SUFFICIENT_REVIEWS that is the number of reviews needed be recommendation can be submitted.
	 */
	public static int getSufficientReviews() {
		return SUFFICIENT_REVIEWS;
	}

	
	/**
	 * A general setter for the Reviewer list held within this Manuscript. This will iterate through
	 * the passed in list adding this Manuscript to each Reviewer within the list before assigning
	 * this Manuscript's main Reviewer list to be theReviewerList.
	 * 
	 * Pre:
	 * 	theReviewerList must not be null
	 * 
	 * @param theReviewerList the Reviewer list to set
	 * 
	 * @author Connor Lundberg
	 * @version 5/30/2017
	 */
	public void setReviewerList(ArrayList<Reviewer> theReviewerList) {
		for(Reviewer reviewer : theReviewerList) {
			reviewer.addManuscriptToReviewer(this);
		}
		this.myReviewerList = theReviewerList;
	}
	
	
	/**
	 * This method will check if the manuscript's number of reviews
	 * meets the minimum number required for the SubProgram Chair to make a 
	 * recommendation and return a boolean indicating so.
	 * 
	 * Pre:
	 * 	myReviews list must be instantiated(does not need to have items).
	 * 
	 * @return a boolean indicating if if manuscript is eligible for SPC recommendation or not.
	 * 
	 * @author Ryan Tran
	 * @version 5/28/17
	 */
	public boolean isEligibleForRecommendation() {
		boolean isEligible = false;

		if(this.getReviews().size() >= SUFFICIENT_REVIEWS) {
			isEligible = true;
		}

		return isEligible;
	}
	
	private void saveFileAsByteArr(File theFile) throws IOException {
		File file = theFile;
		 
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }

        //byte[] bytes = bos.toByteArray();
        this.myManuscriptByteArray = bos.toByteArray();
        System.out.println(this.myManuscriptByteArray.toString());
 
        //below is the different part
        /*
        File someFile = new File("java2.pdf");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
        */
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
