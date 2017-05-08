/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */
package conference_package;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class represents a manuscript and stores any relevant information.
 * @author James Roberts, Ian Waak, Ayub Tiba, Vincent Polio, Vinh Le
 * @version 4/30/2017
 */
public class Manuscript implements Serializable{

	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 96995919879996851L;
	
	/** The manuscripts's title. */
	private String myTitle;
	
	/** The body of the manuscript. */
	private String myManuscript;
	
	/** The list of authors by username, index 0 is the primary author. */
	private ArrayList<User> myAuthors;
	
	/** The Manuscript submission date. */
	private Date mySubmissionDate;
	
	/**
	 * Constructors for The class.
	 * @param theTitle The manuscript's title.
	 * @param theManuscript The manuscripts's body.
	 * @param theMainAuthor The main author of the manuscript's username (email).
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public Manuscript(String theTitle, String theManuscript, User theMainAuthor) {
		myTitle = theTitle;
		myAuthors = new ArrayList<User>();
		myAuthors.add(theMainAuthor);
		myManuscript = theManuscript;
		mySubmissionDate = new Date();
		
	}
	
	/**
	 * Returns the date the manuscript was submitted.
	 * @return the date the manuscript was submitted.
	 */
	public Date getSubmissionDate() {
		return new Date(mySubmissionDate.getTime());
	}
	
	/**
	 * Setter for the date the manuscript was submitted.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	public void setSubmissionDate(Date theDate) {
		mySubmissionDate = theDate;
	}
	
	/**
	 * Getter for the paper's title.
	 * @return The title of the paper.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public String getTitle() {
		return myTitle;
	}
	
	/**
	 * Getter for the paper.
	 * @return The body of the paper.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public String getManuscript() {
		return myManuscript;
	}
	
	/**
	 * Getter for the list of author's
	 * @return The paper's authors.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<User> getAuthors() {
		ArrayList<User> copy = new ArrayList<User>();
		copy.addAll(myAuthors);
		return copy;
	}
	
	/**
	 * This method will return the Author emails.
	 * @return the collection of Author emails
	 */
	public ArrayList<String> getAuthorEmails() {
		ArrayList<String> emails = new ArrayList<String>();
		for (User author : myAuthors) {
			emails.add(author.getEmail());
		}
		return emails;
	}
	
	/**
	 * Adds a co-author to the paper.
	 * @param theAuthor The co-author to be added.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public void addAuthor(User theAuthor) {
		//need to make sure this doesn't already exist in the collection
		myAuthors.add(theAuthor);
	}
	
	/**
	 * Replaces the body of the paper with an updated version.
	 * @param thePaper The new paper body.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public void updatePaper(String thePaper) {
		myManuscript = thePaper;
	}

}
