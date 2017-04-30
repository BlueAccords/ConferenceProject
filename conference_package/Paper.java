package conference_package;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

/**
 * This class represents a manuscript and stores any relevant information.
 * @author James Roberts
 * @version 4/27/2017
 */
public class Paper implements Serializable{

	/**
	 * The serial Id number
	 */
	private static final long serialVersionUID = 96995919879996851L;
	/**
	 * The paper's title.
	 */
	private String myTitle;
	/**
	 * The body of the paper.
	 */
	private String myPaper;
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
	 * @param theTitle The paper's title.
	 * @param thePaper The paper's body.
	 * @param theMainAuthor The main author of the paper's username.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public Paper(String theTitle, String thePaper, String theMainAuthor) {
		myTitle = theTitle;
		myAuthors = new ArrayList<String>();
		myAuthors.add(theMainAuthor);
		myPaper = thePaper;
		mySubmissionDate = new Date();
		
	}
	
	/**
	 * Returns the date the paper was submitted.
	 * @return the date the paper was submitted.
	 */
	public Date getSubmissionDate() {
		return new Date(mySubmissionDate.getTime());
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
	public String getPaper() {
		return myPaper;
	}
	
	/**
	 * Getter for the list of author's
	 * @return The paper's authors.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList <String> getAuthors() {
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
		myPaper = thePaper;
	}
	
}