package conference_package;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representation of a User in the conference program. 
 * @author Vincent Povio, Ayub Tiba, James Roberts, Vinh Le
 * @version 4/30/2017
 *
 */
public class User implements Serializable{

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 8870025955073752215L;
	/**
	 * The user's last name.
	 */
	private String lastName;
	/**
	 * The user's first name. 
	 */
	private String firstName;
	/**
	 * The user's email, used as a unique identifier for them. 
	 */
	private String email;
	/**
	 * The user's current view.
	 */
	private View view;
	/**
	 * Collection of all papers the user has submitted to the conference.
	 */
	private ArrayList<Paper> myPapers;
	/**
	 * Collection of any papers the user has been assigned to review.
	 */
	private ArrayList<Paper> assignedPapersRev;
	/**
	 * Collection of any papers assigned to the user as a Subprogram Chair.
	 */
	private ArrayList<Paper> assignedPapersSPC;
	/**
	 * Collection of any Users assigned as Reviewers to a Subprogram Chair.
	 */
	private ArrayList<User> assignedReviewers;
	
	/**
	 * Constructor for a User.
	 * @param theEmail the user's email.
	 * @author Vincent Povio, Ayub Tiba
	 * @version 4/25/2017
	 */
	public User(String theEmail) {
		email = theEmail;
		myPapers = new ArrayList<Paper>();
		assignedPapersRev = new ArrayList<Paper>();
		assignedPapersSPC = new ArrayList<Paper>();
	    assignedReviewers = new ArrayList<User>();
	}
	
	/**
	 * 
	 * @return the User's last name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Set's the User's last name. 
	 * @param lastName
	 * @author Ayub Tiba
	 * @version 4/25/2017
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return the first name of the User.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the User's first name.
	 * @param firstName The User's first name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return the User's email address
	 * @author Ayub Tiba
	 * @version 4/25/2017 
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the user's email address
	 * @param email the User's email address
	 * @version 4/25/2017
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @return the currently selected view.
	 * @author Vincent Povio
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * Sets the User's current view
	 * @param view the User's current view
	 * @author Vincent Povio
	 * @version 4/25/2017
	 * 
	 */
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * 
	 * @return Collection of the Manuscripts a user has submitted to a conference.
	 * @author Ayub Tiba
	 * @version 4/30/2017
	 */
	public ArrayList<Paper> getMyPapers() {
		return myPapers;
	}
	
	/**
	 * Adds a passed paper to the User's collection of papers. 
	 * @param thePaper
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addPaper(Paper thePaper) {
		myPapers.add(thePaper);
	}
	
	/**
	 * Removes the passed paper from the collection of the User's
	 * submitted papers if it is in the collection. 
	 * @param thePaper
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removePaper(Paper thePaper) {
		for (Paper target: myPapers) {
			if (target == thePaper) {
				myPapers.remove(target);
			}
		}
	}
	
	/**
	 * 
	 * @return the collection of Paper's assigned to the User to review
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Paper> getAssignedPapersRev() {
		return assignedPapersRev;
	}
	
	/**
	 * Adds the passed Paper to the User's collection of Papers
	 * they have been assigned to review
	 * @param thePaper
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addPaperToReviewer(Paper thePaper) {
		assignedPapersRev.add(thePaper);
	}

	/**
	 * Removes the passed Paper from the User's collection
	 * of Papers to review if it exists in the collection.
	 * @param thePaper the Paper to be removed
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removePaperFromReviwer(Paper thePaper) {
		for (Paper target: assignedPapersRev) {
			if (target == thePaper) {
				assignedPapersRev.remove(target);
			}
		}
	}
	
	/**
	 * Returns a collection of Papers assigned to the User as a Subprogram Chair.
	 * @return a collection of Papers assigned to the User as a Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Paper> getAssignedPapersSPC() {
		return assignedPapersSPC;
	}

	/**
	 * Adds the passed Paper to the collection of Papers the Subprogram chair has been 
	 * assigned to.
	 * @param thePaper the Paper to be assigned to the Subprogram Chair.
	 * @author Vincent Povio
	 * @version 4/29/2017
	 */
	public void addPaperToSPC(Paper thePaper) {
		assignedPapersSPC.add(thePaper);
	}
	
	/**
	 * Adds the passed User to the SPC's collection of assigned Reviewers.
	 * @param theReviewer the User to assign as a Reviewer.
	 * @author Vinh Le
	 * @version 4/30/2017
	 */
	public void addReviewerToSPC(User theReviewer) {
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
	public void removePaperFromSPC(Paper thePaper) {
		for (Paper target: assignedPapersSPC) {
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
	 * @return If the Paper was assigned to the Reviewer.
	 */
	public boolean assignPaperToReviewer(User theReviewer, Paper thePaper) {
		if (!(isAuthor(theReviewer, thePaper))  && isUnderAssignedPaperLimit(theReviewer)) {
			theReviewer.addPaperToReviewer(thePaper);
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Checks if the Passed User can be assigned another paper to review. Returns true if the
		 * User has been assigned 7 or fewer Papers, false otherwise.
		 * @param theReviewer the Reviewer to test.
		 * @return T if User assigned < 8 papers, F otherwise.
		 * @author James Roberts, Ayub Tiba
		 * @version 4/30/2017
		 */
		public boolean isUnderAssignedPaperLimit(User theReviewer) {
			if (theReviewer.assignedPapersRev.size() > 8) {
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
		public boolean isAuthor(User theReviewer, Paper thePaper){
			for (String author : thePaper.getAuthors()) {
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
		public ArrayList<User> getEligibleReviewers (Paper thePaper) {
			//List of eligible reviewers
			ArrayList<User> eligibleReviewers = new ArrayList<>();

			//List of author names for the paper in need of review
			ArrayList<String> authors = new ArrayList<String>();
			authors.addAll(thePaper.getAuthors());
			
			boolean flag = false;
			//Iterate through list of reviewers assigned to the User
			for (User possibleReviewer : assignedReviewers) {
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
