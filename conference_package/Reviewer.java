package conference_package;

import java.util.ArrayList;

public class Reviewer {
	/**
	 * Collection of any papers the user has been assigned to review.
	 */
	private ArrayList<Manuscript> assignedPapersRev;
	public Reviewer() {
		
		// TODO Auto-generated constructor stub
		assignedPapersRev = new ArrayList<Manuscript>();
	}
	
	/**
	 * 
	 * @return the collection of Paper's assigned to the User to review
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedPapersRev() {
		ArrayList<Manuscript> reviewPapersCopy = new ArrayList<Manuscript>();
		reviewPapersCopy.addAll(assignedPapersRev);
		return reviewPapersCopy;
	}
	
	/**
	 * Adds the passed Paper to the User's collection of Papers
	 * they have been assigned to review
	 * @param thePaper
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addPaperToReviewer(Manuscript thePaper) {
		assignedPapersRev.add(thePaper);
	}
	
	/**
	 * Removes the passed Paper from the User's collection
	 * of Papers to review if it exists in the collection.
	 * @param thePaper the Paper to be removed
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removePaperFromReviwer(Manuscript thePaper) {
		for (Manuscript target: assignedPapersRev) {
			if (target == thePaper) {
				assignedPapersRev.remove(target);
			}
		}
	}

}
