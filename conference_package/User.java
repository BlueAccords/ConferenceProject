package conference_package;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8870025955073752215L;
	private String lastName;
	private String firstName;
	private String email;
	private View view;
	private ArrayList<Paper> myPapers = new ArrayList<Paper>();
	private ArrayList<Paper> assignedPapersRev = new ArrayList<Paper>();
	private ArrayList<Paper> assignedPapersSPC = new ArrayList<Paper>();
	private ArrayList<User> assignedReviewers = new ArrayList<User>();
	//private ArrayList<Paper> papersToReview = new ArrayList<Paper>();
	
	private Conference myConference;//changes made by Ayub
	
	public User() {
		
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	public ArrayList<Paper> getMyPapers() {
		return myPapers;
	}
	public void setMyPapers(ArrayList<Paper> myPapers) {
		this.myPapers = myPapers;
	}
	public void addPaper(Paper thePaper) {
		myPapers.add(thePaper);
	}
	public void removePaper(Paper thePaper) {
		for (Paper target: myPapers) {
			if (target == thePaper) {
				myPapers.remove(target);
			}
		}
	}
	
	public ArrayList<Paper> getAssignedPapersRev() {
		return assignedPapersRev;
	}
	public void setAssignedPapersRev(ArrayList<Paper> assignedPapersRev) {
		this.assignedPapersRev = assignedPapersRev;
	}
	
	public void addPaperToReviewer(Paper thePaper) {
		assignedPapersRev.add(thePaper);
	}
	
	//Need to look at all authors of the paper and see if the passed reviewer
	//matches any of these.
	//Also need to check the # of manuscripts the reviewer has been assigned.
	// fail if they already have 8 or more.
	/**
	 * @author James Robert, Ayub Tiba
	 * @param theReviewer
	 * @param thePaper
	 */
	public void assignPaperToReviewer(User theReviewer, Paper thePaper) {
		if (!(isAuthor(theReviewer, thePaper))  && !(assignedPapersRev.size() > 8)) {
			theReviewer.addPaperToReviewer(thePaper);
		}
	}
	
	/**
	 * 
	 * @author Ayub Tiba
	 * @param theReviewer
	 * @param thePaper
	 * @return if a reviewer is an Author
	 * @version 4/30/2017
	 */
	public boolean isAuthor(User theReviewer, Paper thePaper){
		for (String authors : thePaper.getAuthors()) {
			if (theReviewer.getEmail().equalsIgnoreCase(authors)) {
				return true;
			}
		}
		return false;
	}
	
	public void removePaperFromReviwer(Paper thePaper) {
		for (Paper target: assignedPapersRev) {
			if (target == thePaper) {
				assignedPapersRev.remove(target);
			}
		}
	}
		
	public ArrayList<Paper> getAssignedPapersSPC() {
		return assignedPapersSPC;
	}
	public void setAssignedPapersSPC(ArrayList<Paper> assignedPapersSPC) {
		this.assignedPapersSPC = assignedPapersSPC;
	}
	public void addPaperToSPC(Paper thePaper) {
		assignedPapersSPC.add(thePaper);
	}
	public void removePaperFromSPC(Paper thePaper) {
		for (Paper target: assignedPapersSPC) {
			if (target == thePaper) {
				assignedPapersSPC.remove(target);
			}
		}
	}
	
}
