package conference_package;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	private static ArrayList<Conference> conferenceList = new ArrayList<>();
	private static ArrayList<User> userList = new ArrayList<>();
	private final static Scanner scan = new Scanner(System.in);
	private static String userLogin = null;
	private static String conferenceName;
	private static User theUser = null;
	private static Conference currentConference = null;
	private static String currentView = null;

	public static void main(String[] args) throws Exception {

		do {
			//Get user login
			System.out.println("Welcome to the IEEE Conference System.");
			System.out.println("Please login using your email or press 9 to exit: ");
			userLogin = scan.nextLine();
			//Checks if user entered 9 to exit.
			if (userLogin != "9") {
				//Checks user against registered user list, if they are on the list sets theUser variable.
				for (User targetUser : userList) {
					if (targetUser.getEmail() == userLogin) {
						theUser = targetUser;
					}
				}
				//Checks if a valid user ID was entered.
				if (theUser != null) {
					do {
						//Get user to select conference
						System.out.println("Welcome " + userLogin);
						System.out.println("Please select a conference by entering its name or press 9 to exit: ");
						for (Conference targetConference: conferenceList) {
							System.out.println(targetConference.getConferenceName());
						}
						conferenceName = scan.nextLine();

						//Checks if user entered 9 to exit.
						if (conferenceName != "9") {
							for (Conference targetConference : conferenceList) {
								if (targetConference.getConferenceName() == conferenceName) {
									currentConference = targetConference;
								}
							}
							if (currentConference != null) {
								if (currentConference.getConfPC() == theUser) {
									currentView = "Program Chair";
								} else if (currentConference.getSubprogramChair(theUser) != null) {
									currentView = "Subprogram Chair";
								} else if (currentConference.getReviewer(theUser) != null) {
									currentView = "Reviewer";
								} else {
									currentView = "Author";
								}
								boolean shouldStop = false;
								do {
									shouldStop = printViewScreen();
								} while (!shouldStop);
							} else {
								System.out.println("Invalid Conference, please try again.");
							}
						}
					} while (conferenceName != "9");
				} else {
					System.out.println("You entered " + userLogin + ". This is an invalid username, please try again.");
				}
			}
		} while (userLogin != "9");
		System.out.println("Thank you, and goodbye.");
	}

	private static boolean printViewScreen() throws Exception {
		int n = 1, selection = 0;
		String title;
		switch (currentView) {
		case "Author":
			System.out.println("Current view: Author");
			Author theAuthor = currentConference.getAuthor(theUser);
			for (Manuscript targetManuscript : theAuthor.getMyManuscripts()) {
				System.out.println(n + ") " + targetManuscript.getTitle());
				n++;
			}
			System.out.println("Please select from the following options:");
			System.out.println("1) Add a manuscript.");
			System.out.println("9) Exit");
			selection = scan.nextInt();
			switch (selection) {
			case 1:
				String manuscript;
				System.out.println("To add a manuscript, please enter a title: ");
				title = scan.nextLine();
				System.out.println("Please enter a file name: ");
				manuscript = scan.nextLine();
				Manuscript theManuscript = new Manuscript(title, manuscript, theUser);
				if (theManuscript != null) {
					currentConference.addManuscript(theManuscript);
				}
				return false;
			case 9:
				return true;
			default:
				System.out.println("Invalid entry, please try again.");
				return false;
			}
		case "Reviewer":
			return true;
		case "Subprogram Chair":			
			System.out.println("Current view: Subprogram Chair");
			SubprogramChair theSPC = currentConference.getSubprogramChair(theUser);
			for (Manuscript targetManuscript : theSPC.getAssignedPapersSPC()) {
				System.out.println(n + ") " + targetManuscript.getTitle());
				n++;
			}
			System.out.println("Please select from the following options:");
			System.out.println("1) Assign a reviewer to a paper.");
			System.out.println("9) Exit");
			selection = scan.nextInt();
			switch (selection) {
			case 1:
				System.out.print("Please enter the name of the paper you would like to add a reviewer to: ");
				title = scan.nextLine();
				
				
				return false;
			case 9:
				return true;
			default:
				System.out.println("Invalid entry, please try again.");
				return false;
			}
		case "Program Chair":
			return true;

		}
		return false;
	}
}