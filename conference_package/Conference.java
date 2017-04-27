package conference_package;

import java.util.ArrayList;
import java.util.Date;

public class Conference {
	private String myConferenceName;
	private Date myPaperDeadline;
	private Date myReviewDeadline;
	private Date myRecDeadline;
	private Date myFinalDeadline;
	private ArrayList<Paper> myPapers;
	
	public Conference(String theConferenceName, Date thePDead, Date theRevDead, Date theRecDead, Date theFinalDead) {
		myConferenceName = theConferenceName;
		myPaperDeadline = new Date(thePDead.getTime());
		myReviewDeadline = new Date(theRevDead.getTime());
		myRecDeadline = new Date(theRecDead.getTime());
		myFinalDeadline = new Date(theFinalDead.getTime());
		myPapers = new ArrayList<Paper>();
	}
	
	public String getConferenceName() {
		return myConferenceName;
	}
	
	public Date getPaperDeadline() {
		// need to make copy to encapsulate.
		return new Date(myPaperDeadline.getTime());
	}
	
	public Date getReviewDeadline() {
		// need to make copy to encapsulate.
		return new Date(myReviewDeadline.getTime());
	}
	
	public Date getRecDeadline() {
		// need to make copy to encapsulate.
		return new Date(myRecDeadline.getTime());
	}
	
	public Date getFinalDeadline() {
		// need to make copy to encapsulate.
		return new Date(myFinalDeadline.getTime());
	}

	public ArrayList<Paper> getPapers() {
		ArrayList<Paper> copy = new ArrayList<Paper>();
		copy.addAll(myPapers);
		return copy;
	}
}
