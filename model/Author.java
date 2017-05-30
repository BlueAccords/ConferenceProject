/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class implements an Author user type.
 * 
 * @author Ayub Tiba, Ian Waak, James Robert, Vincent Povio, Vinh Le
 * @Version May 7 2017
 *
 */
public class Author extends User implements Serializable{
	
	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = -1841250627863643455L;

	public static final int MAX_MANUSCRIPT_LIMIT = 5;
	
	/** The User associated with this author. */
	private User myUser;
	
	/** Collection of all papers the user has submitted to the conference. */
	private ArrayList<Manuscript> myManuscripts;
		
	/**
	 * Constructor for an Author that takes theUser in order to link
	 * this Author to a User when working with Co-Authors and determining who has written
	 * what Manuscript.
	 * 
	 * @param theUser the User associated with the Author.
	 */
	public Author(User theUser) {
		this("", "", theUser);
	}
		
	/**
	 * Constructor for an Author that takes theFirstName and theLastName of the Author.
	 * This is used for adding Authors to a Manuscript for Authors who aren't Users in 
	 * the system.
	 * 
	 * Post: The User associated with this Author is being set to null, so do not try
	 * to use getUser for this Author or a NullPointerException will be thrown.
	 * 
	 * @param theFirstName The first name of the Author.
	 * @param theLastName The last name of the Author.
	 * 
	 * @author Connor Lundberg
	 * @version 5/15/2017
	 */
	public Author(String theFirstName, String theLastName) {
		this(theFirstName, theLastName, null);
	}
	
	/**
	 * Constructor for an Author that takes theFirstName, theLastName, and theUser.
	 * This is public for testing, but it will generally not be needed as it is mainly
	 * used by the other two constructors in Author to call super.
	 * 
	 * @param theFirstName The first name of the Author.
	 * @param theLastName The last name of the Author.
	 * @param theUser The User corresponding to this Author.
	 */
	public Author(String theFirstName, String theLastName, User theUser) {
		super("");
		super.setFirstName(theFirstName);
		super.setLastName(theLastName);
		myUser = theUser;
		myManuscripts = new ArrayList<Manuscript>();
	}
		
	/**
	 * A getter method to return an user.
	 * 
	 * @return the User associated with the Author.
	 */
	public User getUser() {
		return myUser;
	}
	
	/**
	 * A getter method to return a collection of manuscripts.
	 * 
	 * @return Collection of the Manuscripts a user has submitted to a conference.
	 * 
	 * @author Ayub Tiba, Jamesm Roberts, Casey Anderson
	 * @version 4/30/2017
	 */
	public ArrayList<Manuscript> getMyManuscripts() {
		return myManuscripts;
	}
	
	/**
	 * Checks for an existing Manuscript with the same title as the String passed.
	 * 
	 * @param theManuscriptTitleToCheck The String title to check
	 * @return True if there exists a submitted Manuscript with the same name, false otherwise.
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public boolean checkForExistingManuscript (String theManuscriptTitleToCheck) {
		boolean manuscriptExists = false;
		ArrayList<String> manuscriptTitles = new ArrayList<String> ();
		for (Manuscript singleManuscript : myManuscripts) {
			manuscriptTitles.add(singleManuscript.getTitle());
		}
		
		for (String title : manuscriptTitles) {
			if (title.equals(theManuscriptTitleToCheck)) {
				manuscriptExists = true;
				break;
			}
		}
		
		return manuscriptExists;
	}
	
	/**
	 * This method will add a passed Manuscript to the User's collection of Manuscript. 
	 * 
	 * @param theManuscript
	 * 
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addManuscript(Manuscript theManuscript) {
		myManuscripts.add(theManuscript);
	}
	
	/**
	 * Method to print all Manuscript titles belonging to this Author.
	 */
	public void printManuscriptTitles () {
		for (Manuscript manu : myManuscripts) {
			System.out.print(" - " + manu.getTitle());
		}
		System.out.println(" - ");
	}
	
	/**
	 * This method will remove the passed paper from the collection of the User's.
	 * submitted papers if it is in the collection. This will check if any manuscripts in
	 * the last match theManuscript provided. If so, it will remove it and break out of the loop.
	 * Other wise it will throw a ManuscriptNotInListException if the list is empty or the
	 * manuscript is not found in the list.
	 * 
	 * @param theManuscript The old manuscript to remove
	 * @throws ManuscriptNotInListException If the list is empty or theManuscript is not in the list
	 * 
	 * @author Ayub Tiba, Connor Lundberg
	 * @version 5/13/2017
	 */
	public void removeManuscript(Manuscript theManuscript) throws ManuscriptNotInListException {
		if (myManuscripts.contains(theManuscript)) {
			myManuscripts.remove(theManuscript);
		} else {
			throw new ManuscriptNotInListException();
		}
	}
	
	public int isAuthorsAtLimit(String[] theAuthors, Conference theConference) {
		int atLimit = -1;
		Author tempAuthor;
		User tempUser;
		for (int i = 0; i < theAuthors.length; i++) {
			
			if (User.doesEmailBelongToUser(theAuthors[i])) {
				
				tempUser = User.getUserByEmail(theAuthors[i]);
				if (theConference.isUserAuthor(tempUser)) {
					
					Author authToTest = new Author(tempUser);
					tempAuthor = theConference.getAuthor(authToTest);
					System.out.println("" + tempAuthor.getNumSubmittedManuscripts());
					if (tempAuthor.getNumSubmittedManuscripts() >= MAX_MANUSCRIPT_LIMIT) {
						System.out.println("" + MAX_MANUSCRIPT_LIMIT);
						System.out.println("" + tempAuthor.getNumSubmittedManuscripts());
						atLimit = i;
					}
				}
			}
				
		}
		
		return atLimit;
		
	}
		
	/**
	 * This method will return the number of submitted manuscripts.
	 * 
	 * @return the number of manuscripts
	 */
	public int getNumSubmittedManuscripts() {
		return myManuscripts.size();
	}
	
	/**
	 * A custom exception to use in removeManuscript if the passed manuscript is either
	 * not in the manuscript list or if the list is empty.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	public class ManuscriptNotInListException extends Exception {
		
		/**
		 * Serialized UID for this Exception
		 */
		private static final long serialVersionUID = -8210859321934678990L;
		
		private static final String ERROR_MESSAGE = "Manuscript not in the manuscript list for this author";
		
		
		public ManuscriptNotInListException() {
			super(ERROR_MESSAGE);
		}
	}
}
