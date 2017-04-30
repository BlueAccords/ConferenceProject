package conference_package


public class SubprogramChair extends View {
	private ArrayList<Paper> papers;
	
	public void SubporgramChair (ArrayList<Paper> thePapers) {
		papers = thePapers;
	}
	
	public void displayMenu() {
		System.out.println("/-------\	       __  __");
		System.out.println("|--MSE--|	|\ / ||__ |__");
		System.out.println("\-------/	| \/ | __||__");
		System.out.println("-------------------------");
		for (int i = 0; i < papers.length(); i++) { 
			
		}
	}
}
