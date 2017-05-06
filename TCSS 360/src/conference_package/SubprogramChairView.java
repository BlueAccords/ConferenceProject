package conference_package;

import java.util.ArrayList;

public class SubprogramChairView extends View {
	private ArrayList<Manuscript> papers;
	
	public void SubporgramChair (ArrayList<Manuscript> thePapers) {
		papers = thePapers;
	}
	
	public void displayMenu() {
		System.out.println("/-------\\	       __  __");
		System.out.println("|--MSE--|	|\\ / ||__ |__");
		System.out.println("\\-------/	| \\/ | __||__");
		System.out.println("-------------------------");
		for (int i = 0; i < papers.size(); i++) { 
			
		}
	}
}
