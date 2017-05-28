/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubprogramChair extends User implements Serializable{
	
	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = -6999273827761122770L;

	/** The maximum number of manuscripts for a reviewer. */
	private static final int MAX_REVIEW_PAPERS = 8;
	
	
	/** The user ID (email) associated with this SubprogramChair. */
	User myUser;
	
	/** Collection of any manuscripts assigned to the user as a Subprogram Chair. */
	private ArrayList<Manuscript> assignedManuscriptsSPC;
	
	/** Collection of any Users assigned as Reviewers to a Subprogram Chair. */
	private ArrayList<Reviewer> assignedReviewers;
	
	private File myRecommendation;
	
	/**
	 * Constructor for a SubprogramChair.
	 * @param aUser the User associated with this SPC.
	 */
	public SubprogramChair(User aUser) {
		super("");
		//no defensive copy made, we want this to act as a pointer.
		myRecommendation = new File("");
		myUser = aUser;
		assignedManuscriptsSPC = new ArrayList<Manuscript>();
	    assignedReviewers = new ArrayList<Reviewer>();
	}
	
	/**
	 * Constructor that takes an arrayList of assignedManuscripts.
	 * 
	 * @author Morgan Blackmore
	 * @version 5/27/17
	 * 
	 */
	public SubprogramChair(User aUser, ArrayList<Manuscript> theAssignedManuscripts) {
		super("");
		myRecommendation = new File("");
		myUser = aUser;
		assignedManuscriptsSPC = theAssignedManuscripts;
	    assignedReviewers = new ArrayList<Reviewer>();
		
	}
	/**
	 * Getter for a User type.
	 * @return the User associated with the SPC.
	 */
	public User getUser() {
		
		return myUser;
	}
	
	
	public void setRecommendation (File theRecommendation) {
		myRecommendation = theRecommendation;
	}
	
	
	public File getRecommendation () {
		return myRecommendation;
	}
	
	
	/**
	 * Returns a collection of Manuscripts assigned to the User as a Subprogram Chair.
	 * @return a collection of Manuscripts assigned to the User as a Subprogram Chair.
	 * @author Vincent Povio
	 * @author Casey Anderson
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedManuscriptSPC() {
		ArrayList<Manuscript> assignedMansSPCcopy = new ArrayList<Manuscript>();
		assignedMansSPCcopy.addAll(assignedManuscriptsSPC);
		return assignedMansSPCcopy;
	}

	
	/**
	 * Adds the passed Manuscript to the collection of Manuscript the Subprogram chair has been 
	 * assigned to.
	 * @param theManuscript the Paper to be assigned to the Subprogram Chair.
	 * @author Vincent Povio
	 * @author Casey Anderson
	 * @version 4/29/2017
	 */
	public void addManuscriptToSPC(Manuscript theManuscript) {
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
			theManuscript.addReviewer(theReviewer);
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
		 * Getter for MAX_REVIEW_PAPERS Limit
		 * 
		 * @return int value of MAX_REVIEW_PAPERS 
		 */
		public int getMaxReviewPapers(){
			return MAX_REVIEW_PAPERS;
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
		 * Checks whether theConference deadline for manuscript submission
		 * is after theDate.  If so (the deadline is still in the future)
		 * and will return false.
		 * 
		 *
		 * @author Morgan Blackmore
		 * @version 5/19/17
		 * @return boolean true if manuscriptSubmissionDeadline is after theDate
		 */
		public boolean isAfterSubmissionDeadline(Conference theConference, Date theDate){
			if (theConference.getManuscriptDeadline().before(theDate)){//is conferenceDL before theDate, if so, return False
				return false; 
			} else {
				return true;	
			}
		
			
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
			ArrayList<Reviewer> eligibleReviewers = new ArrayList<Reviewer>();

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
			removeReviewersWhoHaveAuthoredAPaperWithThisPapersAuthor(eligibleReviewers, theManuscript);
			return eligibleReviewers;
		}

		private void removeReviewersWhoHaveAuthoredAPaperWithThisPapersAuthor(ArrayList<Reviewer> reviewerList, Manuscript theManuscript){
			Set<User> conflictingReviewersByUser = new HashSet<User>();
			for(Author currentAuthor: theManuscript.getAuthors()){
				for(Manuscript currentAuthorsCurrentManuscript: currentAuthor.getMyManuscripts()){
					for(Author sharedAuthor: currentAuthorsCurrentManuscript.getAuthors()){
						conflictingReviewersByUser.add(sharedAuthor.getUser());
					}
				}
			}
			for(int i = 0; i < reviewerList.size(); i++){
				if(conflictingReviewersByUser.contains(reviewerList.get(i).getUser())){
					reviewerList.remove(i);
					i--;
				}
			}
		}

}
