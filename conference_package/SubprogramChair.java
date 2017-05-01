package conference_package;

import java.util.ArrayList;

public class SubprogramChair {
	/**
	 * Collection of any papers assigned to the user as a Subprogram Chair.
	 */
	private ArrayList<Manuscript> assignedPapersSPC;
	/**
	 * Collection of any Users assigned as Reviewers to a Subprogram Chair.
	 */
	private ArrayList<Reviewer> assignedReviewers;
	public SubprogramChair() {
		// TODO Auto-generated constructor stub
		assignedPapersSPC = new ArrayList<Manuscript>();
	    assignedReviewers = new ArrayList<Reviewer>();
	}
	
	/**
	 * Returns a collection of Papers assigned to the User as a Subprogram Chair.
	 * @return a collection of Papers assigned to the User as a Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedPapersSPC() {
		ArrayList<Manuscript> assignedPapersSPCcopy = new ArrayList<Manuscript>();
		assignedPapersSPCcopy.addAll(assignedPapersSPC);
		return assignedPapersSPCcopy;
	}

	//need to make sure the paper does not already exist in this collection.
	/**
	 * Adds the passed Paper to the collection of Papers the Subprogram chair has been 
	 * assigned to.
	 * @param thePaper the Paper to be assigned to the Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/29/2017
	 */
	public void addPaperToSPC(Manuscript thePaper) {
		assignedPapersSPC.add(thePaper);
	}
	
	//need to make sure the reviewer doesn't already exist in this collection
	/**
	 * Adds the passed User to the SPC's collection of assigned Reviewers.
	 * @param theReviewer the User to assign as a Reviewer.
	 * @author Vinh Le
	 * @version 4/30/2017
	 */
	public void addReviewerToSPC(Reviewer theReviewer) {
		assignedReviewers.add(theReviewer);
	}
	
	/**
	 * Removes the passed Paper from the collection of Papers assigned
	 * to the Subprogram Chair, no change if the Paper
	 * hasn't already been assigned to the Subprogram Chair.
	 * @param thePaper the Paper to remove from the SPC.
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removePaperFromSPC(Manuscript thePaper) {
		for (Manuscript target: assignedPapersSPC) {
			if (target == thePaper) {
				assignedPapersSPC.remove(target);
			}
		}
	}
	
	/**
	 * Attempts to add the passed Paper to the passed User's collection of Paper's to review.
	 * Paper will not be added if the Reviewer has already been assigned 8 papers or if they are the author
	 * or coauthor of the paper. 
	 * @author James Robert, Ayub Tiba
	 * @param theReviewer The Reviewer to assign the Paper to.
	 * @param thePaper The Paper to assign.
	 * @throws Exception 
	 */
	public void assignPaperToReviewer(Reviewer theReviewer, Manuscript thePaper) throws Exception {
		if (theReviewer.getAssignedPapersRev().contains(thePaper)) {
			throw new Exception("Paper already assigned to this Reviewer");
		}
		if (isAuthor(theReviewer, thePaper)) {
			throw new Exception("The Reviewer is an Author of this Paper.");
		}
		
		if (!isUnderAssignedPaperLimit(theReviewer)) {
			throw new Exception("The Reviewer has already been assigned the max number of papers.");
		}
			//no exceptions thrown, ok to add. 
			theReviewer.addPaperToReviewer(thePaper);
			
		}
		
		/**
		 * Checks if the Passed User can be assigned another paper to review. Returns true if the
		 * User has been assigned 7 or fewer Papers, false otherwise.
		 * @param theReviewer the Reviewer to test.
		 * @return T if User assigned < 8 papers, F otherwise.
		 * @author James Roberts, Ayub Tiba
		 * @version 4/30/2017
		 */
		public boolean isUnderAssignedPaperLimit(Reviewer theReviewer) {
			if (theReviewer.getAssignedPapersRev().size() > 8) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * Checks if the Passed User is an Author or CoAuthor of the passed Paper.
		 * @author Ayub Tiba
		 * @param theReviewer The Reviewer to check.
		 * @param thePaper The Paper to check. 
		 * @return if a reviewer is an Author
		 * @version 4/30/2017
		 */
		public boolean isAuthor(User theReviewer, Manuscript thePaper){

			//List of author names for the paper in need of review
			ArrayList<String> authorList = new ArrayList<String>();
			authorList.addAll(thePaper.getAuthors());
			
			for (String author : authorList) {
				if (theReviewer.getEmail().equalsIgnoreCase(author)) {
					return true;
				}
			}
			return false;
			
		}
		
		/**
		 * This function gets a list of all eligible reviewers.
		 * It validates that the reviewers meet the following business rules:
		 * 	1) The reviewer isn't an author on the paper.
		 * 	2) The reviewer doesn't have >7 papers assigned to them.
		 * @param thePaper the Paper to generate a list of eligible Reviewers for.
		 * @return a collection of eligible reviewers for the Paper.
		 * @author Vincent Povio, Ian Waak
		 * @version 4/30/2017
		 */
		public ArrayList<User> getEligibleReviewers (Manuscript thePaper) {
			//List of eligible reviewers
			ArrayList<User> eligibleReviewers = new ArrayList<>();

			//List of author names for the paper in need of review
			ArrayList<String> authors = new ArrayList<String>();
			authors.addAll(thePaper.getAuthors());
			
			boolean flag = false;
			//Iterate through list of reviewers assigned to the User
			for (Reviewer possibleReviewer : assignedReviewers) {
				//Iterate through list of paper's authors
				for (String paperAuthor: authors) {
					//If reviewer is author or co-author, set flag to true
					if (possibleReviewer.getEmail().equals(paperAuthor)) {
						flag = true;
					}
				}
				
				//If reviewer has max or more papers already assigned, set flag to true
				if(possibleReviewer.getAssignedPapersRev().size() >= 8) {
					flag = true;
				}
				
				//If flag is false, add reviewer to list of eligible reviewers
				if (!flag) {
					eligibleReviewers.add(possibleReviewer);
				}
			}
			
			return eligibleReviewers;
		}

}
