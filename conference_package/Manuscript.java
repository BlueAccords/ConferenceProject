package conference_package;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

/**
 * This class represents a manuscript and stores any relevant information.
 * @author James Roberts, Ian Waak, Ayub Tiba
 * @version 4/30/2017
 */
public class Manuscript implements Serializable{

	/**
	 * The serial Id number
	 */
	private static final long serialVersionUID = 96995919879996851L;
	/**
	 * The manuscripts's title.
	 */
	private String myTitle;
	/**
	 * The body of the manuscript.
	 */
	private String myManuscript;
	/**
	 * The list of authors by username, index 0 is the primary author.
	 */
	private ArrayList<String> myAuthors;
	/**
	 * When the paper was submitted.
	 */
	private Date mySubmissionDate;
	
	/**
	 * Constructor for The class.
	 * @param theTitle The manuscript's title.
	 * @param theManuscript The manuscripts's body.
	 * @param theMainAuthor The main author of the manuscript's username (email).
	 * @author Ayub Tiba
	 * @version 4/27/2017
	 */
	public Manuscript(String theTitle, String theManuscript, String theMainAuthor) {
		myTitle = theTitle;
		myAuthors = new ArrayList<String>();
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
	public ArrayList<String> getAuthors() {
		ArrayList<String> copy = new ArrayList<String>();
		copy.addAll(myAuthors);
		return copy;
	}
	
	/**
	 * Adds a co-author to the paper.
	 * @param theAuthor The co-author to be added.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public void addAuthor(String theAuthor) {
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
