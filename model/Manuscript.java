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
	private File myManuscript;
	
	/** The list of authors by username, index 0 is the primary author. */
	private ArrayList<User> myAuthors;
	
	/** The Manuscript submission date. */
	private Date mySubmissionDate;
	
	
	/**
	 * Constructors for The class.
	 * 
	 * @param theTitle The manuscript's title.
	 * @param theManuscript The manuscripts's corresponding file.
	 * @param theMainAuthor The main author of the manuscript's username (email).
	 * 
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public Manuscript(String theTitle, File theManuscript, User theMainAuthor) {
		myTitle = theTitle;
		myAuthors = new ArrayList<User>();
		myAuthors.add(theMainAuthor);
		myManuscript = theManuscript;
		mySubmissionDate = new Date();
		
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
		return myManuscript;
	}
	
	
	/**
	 * Getter for the list of authors.
	 * 
	 * @return The paper's authors.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<User> getAuthors() {
		ArrayList<User> copy = new ArrayList<User>();
		copy.addAll(myAuthors);
		return copy;
	}
	
	
	/**
	 * This method will return the Author email.
	 * 
	 * @return the collection of Author email
	 */
	public ArrayList<String> getAuthorEmails() {
		ArrayList<String> emails = new ArrayList<String>();
		for (User author : myAuthors) {
			emails.add(author.getEmail());
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
	public void addAuthor(User theAuthor) throws AuthorExistsInListException {
		if (authorNotInList(theAuthor))
			myAuthors.add(theAuthor);
		else
			throw new AuthorExistsInListException();
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
	private boolean authorNotInList (User theAuthor) {
		boolean authorNotInList = true;
		
		//checks if the same User object is already in list
		authorNotInList = !(myAuthors.contains(theAuthor));	
		
		//checks if a User in myAuthors has the same email
		for (User authors : myAuthors) {
			authorNotInList = !(authors.getEmail().equals(theAuthor.getEmail()));	
		}
		
		return authorNotInList;
	}
	
	
	/**
	 * Replaces the file of the paper with an updated version.
	 * 
	 * @param thePaper The new paper file.
	 * 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public void updatePaper(File thePaper) {
		myManuscript = thePaper;
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
