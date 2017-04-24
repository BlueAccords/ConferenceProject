package conference_package;

import java.awt.List;
import java.util.ArrayList;

public abstract class User {
	private String lastName;
	private String firstName;
	private String email;
	private View view;
	private ArrayList<Paper> myPapers = new ArrayList<Paper>();
	private ArrayList<Paper> assignedPapersRev = new ArrayList<Paper>();
	private ArrayList<Paper> assignedPapersSPC = new ArrayList<Paper>();
	
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
