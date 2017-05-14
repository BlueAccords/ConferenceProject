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
public class Author implements Serializable{
	
	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = -1841250627863643455L;

	/** The User associated with this author. */
	private User myUser;
	
	/** Collection of all papers the user has submitted to the conference. */
	private ArrayList<Manuscript> myManuscripts;
	
	/**
	 * Constructor for an Author.
	 * 
	 * @param aUser the User associated with the Author.
	 */
	public Author(User aUser) {
		//no defensive copy, this acts as a pointer.
		myUser = aUser;
		myManuscripts = new ArrayList<Manuscript>();
	}
	
	
	/**
	 * A getter method to return an user.
	 * 
	 * @return the User associated with the Author.
	 */
	public User getUser() {
		//do we need a defensive copy here?
		return myUser;
	}
	
	
	/**
	 * A getter method to return a collection of manuscripts.
	 * 
	 * @return Collection of the Manuscripts a user has submitted to a conference.
	 * 
	 * @author Ayub Tiba, Jamesm Roberts
	 * @version 4/30/2017
	 */
	public ArrayList<Manuscript> getMyManuscripts() {
		ArrayList<Manuscript> myManuscriptsCopy = new ArrayList<Manuscript>();
		myManuscriptsCopy.addAll(myManuscripts);
		return myManuscriptsCopy;
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
	 * This method will remove the passed paper from the collection of the User's.
	 * submitted papers if it is in the collection. This will check if any manuscripts in
	 * the last match theManuscript provided. If so, it will remove it and break out of the loop.
	 * Other wise it will throw a ManuscriptNotInListException if the list is empty or the
	 * manuscript is not found in the list.
	 * 
	 * @param theManuscript The old manuscript to remove
	 * @throws ManuscriptNotInListException If the list is empty or theManuscript is not in the list
	 * 
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removeManuscript(Manuscript theManuscript) throws ManuscriptNotInListException {
		if (myManuscripts.contains(theManuscript)) {
			myManuscripts.remove(theManuscript);
		} else {
			throw new ManuscriptNotInListException();
		}
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



















