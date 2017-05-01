package conference_package;

import java.util.ArrayList;

public class Author {
	/**
	 * Collection of all papers the user has submitted to the conference.
	 */
	private ArrayList<Manuscript> myPapers;
	public Author() {
		// TODO Auto-generated constructor stub
		myPapers = new ArrayList<Manuscript>();
	}
	
	/**
	 * 
	 * @return Collection of the Manuscripts a user has submitted to a conference.
	 * @author Ayub Tiba, Jamesm Roberts
	 * @version 4/30/2017
	 */
	public ArrayList<Manuscript> getMyPapers() {
		ArrayList<Manuscript> myPapersCopy = new ArrayList<Manuscript>();
		myPapersCopy.addAll(myPapers);
		return myPapersCopy;
	}
	
	//Need to check that the paper doesn't already exist in the collection.
		/**
		 * Adds a passed paper to the User's collection of papers. 
		 * @param thePaper
		 * @author Vincent Povio
		 * @version 4/25/2017
		 */
		public void addPaper(Manuscript thePaper) {
			myPapers.add(thePaper);
		}
		
		/**
		 * Removes the passed paper from the collection of the User's
		 * submitted papers if it is in the collection. 
		 * @param thePaper
		 * @author Ayub Tiba
		 * @version 4/29/2017
		 */
		public void removePaper(Manuscript thePaper) {
			for (Manuscript target: myPapers) {
				if (target == thePaper) {
					myPapers.remove(target);
				}
			}
		}

}
