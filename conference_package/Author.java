package conference_package;

import java.io.Serializable;
import java.util.ArrayList;

public class Author implements Serializable{
	/**
	 * The Serial ID.
	 */
	private static final long serialVersionUID = -1841250627863643455L;

	/**
	 * The User associated with this author.
	 */
	private User myUser;
	
	/**
	 * Collection of all papers the user has submitted to the conference.
	 */
	private ArrayList<Manuscript> myManuscripts;
	
	/**
	 * Constructor for an Author.
	 * @param aUser the User associated with the Author.
	 */
	public Author(User aUser) {
		//no defensive copy, this acts as a pointer.
		myUser = aUser;
		myManuscripts = new ArrayList<Manuscript>();
	}
	
	/**
	 * 
	 * @return the User associated with the Author.
	 */
	public User getUser() {
		//maybe need a defensive copy here?
		return myUser;
	}
	
	/**
	 * 
	 * @return Collection of the Manuscripts a user has submitted to a conference.
	 * @author Ayub Tiba, Jamesm Roberts
	 * @version 4/30/2017
	 */
	public ArrayList<Manuscript> getMyManuscripts() {
		ArrayList<Manuscript> myManuscriptsCopy = new ArrayList<Manuscript>();
		myManuscriptsCopy.addAll(myManuscripts);
		return myManuscriptsCopy;
	}
	
	//Need to check that the paper doesn't already exist in the collection.
	//also it cannot be null.
	/**
	 * Adds a passed Manuscript to the User's collection of Manuscript. 
	 * @param thePaper
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addManuscript(Manuscript theManuscript) {
		myManuscripts.add(theManuscript);
	}
		
	/**
	 * Removes the passed paper from the collection of the User's
	 * submitted papers if it is in the collection. 
	 * @param thePaper
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removeManuscript(Manuscript theManuscript) {
		for (Manuscript target: myManuscripts) {
			if (target == theManuscript) {
				myManuscripts.remove(target);
			}
		}
	}

}
