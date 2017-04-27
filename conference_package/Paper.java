package conference_package;

import java.util.ArrayList;


public class Paper {
	private String myPaper;
	private ArrayList<String> myAuthors;
	
	
	
	public Paper(String thePaper, String theMainAuthor) {
		myAuthors = new ArrayList<String>();
		myAuthors.add(theMainAuthor);
		myPaper = thePaper;
		
	}
	
	public String getPaper() {
		return myPaper;
	}
	
	public ArrayList<String>getAuthors() {
		ArrayList<String> copy = new ArrayList<String>();
		copy.addAll(myAuthors);
		return copy;
	}
	
	public void addAuthor(String theAuthor) {
		myAuthors.add(theAuthor);
	}
	
	public void updatePaper(String thePaper) {
		myPaper = thePaper;
	}
	
}
