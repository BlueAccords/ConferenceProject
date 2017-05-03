package conference_package;

import java.io.Serializable;
import java.util.ArrayList;

public class Reviewer implements Serializable{
	/**
	 * The Serial ID.
	 */
	private static final long serialVersionUID = 7044360208215202991L;

	/**
	 * The User associated with this reviewer.
	 */
	User myUser;
	
	/**
	 * Collection of any manuscript the user has been assigned to review.
	 */
	private ArrayList<Manuscript> assignedManuscripts;
	
	/**
	 * Constructor for the Reviewer.
	 * @param aUser The User associated with the Reviewer.
	 */
	public Reviewer(User aUser) {
		//no defensive copy made, we want this to act as a pointer.
		myUser = aUser;
		assignedManuscripts = new ArrayList<Manuscript>();
	}
	
	/**
	 * 
	 * @return the User associated with the Reviewer.
	 */
	public User getUser() {
		//maybe need a defensive copy here?
		return myUser;
	}
	
	/**
	 * 
	 * @return the collection of Manuscripts's assigned to the User to review
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedManuscripts() {
		ArrayList<Manuscript> reviewManuscriptsCopy = new ArrayList<Manuscript>();
		reviewManuscriptsCopy.addAll(assignedManuscripts);
		return reviewManuscriptsCopy;
	}
	
	/**
	 * Adds the passed Manuscript to the User's collection of Manuscripts
	 * they have been assigned to review
	 * @param theManuscript
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addManuscriptToReviewer(Manuscript theManuscript) {
		assignedManuscripts.add(theManuscript);
	}
	
	/**
	 * Removes the passed Manuscript from the User's collection
	 * of Manuscripts to review if it exists in the collection.
	 * @param theManuscript the Paper to be removed
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removeManuscriptFromReviwer(Manuscript theManuscript) {
		for (Manuscript target: assignedManuscripts) {
			if (target == theManuscript) {
				assignedManuscripts.remove(target);
			}
		}
	}
	
	public int getNumAssignedManuscripts() {
		return assignedManuscripts.size();
	}

}
