package conference_package;

import java.io.Serializable;
import java.util.ArrayList;

public class SubprogramChair implements Serializable{
	
	/**
	 * The Serial ID.
	 */
	private static final long serialVersionUID = -6999273827761122770L;

	private static final int MAX_REVIEW_PAPERS = 8;
	/**
	 * The user ID (email) associated with this SubprogramChair.
	 */
	User myUser;
	
	/**
	 * Collection of any manuscripts assigned to the user as a Subprogram Chair.
	 */
	private ArrayList<Manuscript> assignedManuscriptsSPC;
	/**
	 * Collection of any Users assigned as Reviewers to a Subprogram Chair.
	 */
	private ArrayList<Reviewer> assignedReviewers;
	
	/**
	 * Constructor for a SubprogramChair.
	 * @param aUser the User associated with this SPC.
	 */
	public SubprogramChair(User aUser) {
		//no defensive copy made, we want this to act as a pointer.
		myUser = aUser;
		assignedManuscriptsSPC = new ArrayList<Manuscript>();
	    assignedReviewers = new ArrayList<Reviewer>();
	}
	
	/**
	 * 
	 * @return the User associated with the SPC.
	 */
	public User getUser() {
		
		return myUser;
	}
	
	/**
	 * Returns a collection of Manuscripts assigned to the User as a Subprogram Chair.
	 * @return a collection of Manuscripts assigned to the User as a Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedPapersSPC() {
		ArrayList<Manuscript> assignedMansSPCcopy = new ArrayList<Manuscript>();
		assignedMansSPCcopy.addAll(assignedManuscriptsSPC);
		return assignedMansSPCcopy;
	}

	
	/**
	 * Adds the passed Manuscript to the collection of Manuscript the Subprogram chair has been 
	 * assigned to.
	 * @param theManuscript the Paper to be assigned to the Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/29/2017
	 */
	public void addPaperToSPC(Manuscript theManuscript) {
		//need to make sure the paper does not already exist in this collection.
		//need to test if it is null or set that as a precondition.
		assignedManuscriptsSPC.add(theManuscript);
	}
	
	/**
	 * Returns a collection of all Reviewers assigned to this SPC.
	 * @return a collection of all Reviewers assigned to this SPC.
	 * @author James Roberts
	 * @version 5/7/2017
	 */
	public ArrayList<Reviewer> getAssignedReviewers() {
		ArrayList<Reviewer> copy = new ArrayList<Reviewer>();
		copy.addAll(assignedReviewers);
		return assignedReviewers;
	}
	/**
	 * Adds the passed User to the SPC's collection of assigned Reviewers.
	 * @param theReviewer the User to assign as a Reviewer.
	 * @author Vinh Le
	 * @version 4/30/2017
	 */
	public void addReviewerToSPC(Reviewer theReviewer) {
		//need to make sure the reviewer doesn't already exist in this collection.
		assignedReviewers.add(theReviewer);
	}
	
	/**
	 * Removes the passed Manuscript from the collection of Papers assigned
	 * to the Subprogram Chair, no change if the Paper
	 * hasn't already been assigned to the Subprogram Chair.
	 * @param theManuscript the Paper to remove from the SPC.
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removeManuscriptFromSPC(Manuscript theManuscript) {
		for (Manuscript target: assignedManuscriptsSPC) {
			if (target == theManuscript) {
				assignedManuscriptsSPC.remove(target);
			}
		}
	}
	

	/**
	 * Attempts to add the passed Manuscript to the passed User's collection of Manuscripts's to review.
	 * Manuscript will not be added if the Reviewer has already been assigned max Manuscripts or if they 
	 * are the author or coauthor of the paper. 
	 * @author James Robert, Ayub Tiba
	 * @param theReviewer The Reviewer to assign the Paper to.
	 * @param theManuscript The Paper to assign.
	 * @throws Exception 
	 */
	public void assignManuscriptToReviewer(Reviewer theReviewer, Manuscript theManuscript) throws Exception {
		if (theReviewer.getAssignedManuscripts().contains(theManuscript)) {
			throw new Exception("Paper already assigned to this Reviewer");
		}
		if (isAuthor(theReviewer, theManuscript)) {
			throw new Exception("The Reviewer is an Author of this Paper.");
		}
		
		if (!isUnderAssignedManuscriptLimit(theReviewer)) {
			throw new Exception("The Reviewer has already been assigned the max number of papers.");
		}
			//no exceptions thrown, ok to add. 
			theReviewer.addManuscriptToReviewer(theManuscript);
			
		}
		
		/**
		 * Checks if the Passed User can be assigned another Manuscript to review. Returns true if the
		 * User has been assigned < Max Papers, false otherwise.
		 * @param theReviewer the Reviewer to test.
		 * @return T if User assigned < Max papers, F otherwise.
		 * @author James Roberts, Ayub Tiba
		 * @version 4/30/2017
		 */
		public boolean isUnderAssignedManuscriptLimit(Reviewer theReviewer) {
			if (theReviewer.getNumAssignedManuscripts() >= MAX_REVIEW_PAPERS) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * Checks if the Passed User is an Author or CoAuthor of the passed Paper.
		 * @author Ayub Tiba
		 * @param theReviewer The Reviewer to check.
		 * @param theManuscript The Paper to check. 
		 * @return if a reviewer is an Author
		 * @version 4/30/2017
		 */
		public boolean isAuthor(Reviewer theReviewer, Manuscript theManuscript){

			//List of author names for the paper in need of review
			ArrayList<String> authorList = new ArrayList<String>();
			authorList.addAll(theManuscript.getAuthorEmails());
			
			for (String author : authorList) {
				if (theReviewer.getUser().getEmail().equalsIgnoreCase(author)) {
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
		 * @param theManuscript the Paper to generate a list of eligible Reviewers for.
		 * @return a collection of eligible reviewers for the Paper.
		 * @author Vincent Povio, Ian Waak
		 * @version 4/30/2017
		 */
		public ArrayList<Reviewer> getEligibleReviewers (Manuscript theManuscript) {
			//List of eligible reviewers
			ArrayList<Reviewer> eligibleReviewers = new ArrayList<>();

			//List of author names for the paper in need of review
			ArrayList<String> authors = new ArrayList<String>();
			authors.addAll(theManuscript.getAuthorEmails());
			
			boolean flag = false;
			//Iterate through list of reviewers assigned to the User
			for (Reviewer possibleReviewer : assignedReviewers) {
				//Iterate through list of paper's authors
				for (String paperAuthor: authors) {
					//If reviewer is author or co-author, set flag to true
					if (possibleReviewer.getUser().getEmail().equals(paperAuthor)) {
						flag = true;
					}
				}
				
				//If reviewer has max or more papers already assigned, set flag to true
				if(possibleReviewer.getAssignedManuscripts().size() >= MAX_REVIEW_PAPERS) {
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
