package conference_package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserInterface {
	/**
	 * Collection of all existing conferences.
	 */
	private static ArrayList<Conference> conferenceList = new ArrayList<>();
	/**
	 * Collection of all existing Users.
	 */
	private static ArrayList<User> userList = new ArrayList<>();
	/**
	 * The Scanner.
	 */
	private final static Scanner scan = new Scanner(System.in);
	/**
	 * Allows for .ser file of conferences to be read and updated
	 */
	private final static ConferenceReadWrite crw = new ConferenceReadWrite("./confData.ser");
	/**
	 * Allows for the .ser file of users to be read and updated.
	 */
	private final static UserReadWrite urw = new UserReadWrite("./userData.ser");
	/**
	 * The id of the logged in user.
	 */
	private static String userLogin = null;
	/**
	 * The name of the currently selected conference.
	 */
	private static String conferenceName;
	/**
	 * The currently logged in user.
	 */
	private static User theUser = null;
	/**
	 * The currently selected conference.
	 */
	private static Conference currentConference = null;
	/**
	 * The currently selected view.
	 */
	private static String currentView = null;

	public static void main(String[] args) throws Exception {
		//Read in the files of Users and Conferences.
		setUp();
		do {
			//Get user login
			System.out.println("Welcome to the IEEE Conference System.");
			System.out.println("Please login using your email or press 9 to exit: ");
			userLogin = scan.nextLine();
			//Checks if user entered 9 to exit.
			if (userLogin != "9") {
				//Checks user against registered user list, if they are on the list sets theUser variable.
				for (User targetUser : userList) {
					if (targetUser.getEmail().equals(userLogin)) {
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
		//This has to happen when the program exits. If not any changes made to a conference will not be saved.
		crw.writeConferences(conferenceList);
		urw.writeUsers(userList);
	}

	private static boolean printViewScreen() throws Exception {
		int n = 1, selection = 0;
		String title = null;
		String manuscript = null;
		String email = null;
		Manuscript theManuscript = null;
		
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
				System.out.println("To add a manuscript, please enter a title: ");
				title = scan.nextLine();
				System.out.println("Please enter a file name: ");
				manuscript = scan.nextLine();
				theManuscript = new Manuscript(title, manuscript, theUser);
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
				for (Manuscript targetManuscript : currentConference.getManuscripts()) {
					if (targetManuscript.getTitle().equals(title)) {
						theManuscript = targetManuscript;
					}
				}
				for (Reviewer targetReviewer : theSPC.getEligibleReviewers(theManuscript)) {
					System.out.println(targetReviewer.getUser().getEmail());
				}
				System.out.println("Please enter the email of the reviewer you would like to add: ");
				email = scan.nextLine();
				for (Reviewer targetReviewer : theSPC.getEligibleReviewers(theManuscript)) {
					if (targetReviewer.getUser().getEmail().equals(email)) {
						targetReviewer.addManuscriptToReviewer(theManuscript);
					}
				}
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
	
	/**
	 * This class reads in the stored list of conferences and users, if the appropriate
	 * files are not found then a test file is created. 
	 */
	private static void setUp() {
				
		conferenceList = crw.readConferences();
		userList = urw.readUsers();
		//The file doesn't exist, this shouldn't happen since the .ser files should be uploaded to git
		if (conferenceList == null || userList == null) {
			try {
				buildTestingFiles();
			} catch (Exception e) {
				System.out.println("Creating Files Failed");
			}
			userList = urw.readUsers();
		}
		
	}
	
	/**
	 * This method builds a file of conferences for the program and users.
	 * @throws Exception 
	 */
	private static void buildTestingFiles() throws Exception {
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		//Make the Users. 
		User testU1 = new User("simpson@ieee.org");
		testU1.setFirstName("Homer");
		testU1.setLastName("Simpson");
		users.add(testU1);
		User testU2 = new User("robberjames@ieee.org");
		testU2.setFirstName("Jimmy");
		testU2.setLastName("Bob");
		users.add(testU2);
		User testU3 = new User("benlee@ieee.org");
		testU3.setFirstName("Ben");
		testU3.setLastName("Lee");
		users.add(testU3);
		User testU4 = new User("fleegle@ieee.org");
		testU4.setFirstName("Frita");
		testU4.setLastName("Logan");
		users.add(testU4);
		User testU5 = new User("Flandersn@ieee.org");
		testU5.setFirstName("Ned");
		testU5.setLastName("Flanders");
		users.add(testU5);
		User testU6 = new User("barwood@ieee.org");
		testU6.setFirstName("Ashley");
		testU6.setLastName("Barwood");
		users.add(testU6);
		User testU7 = new User("joe@hotmail.com");
		testU7.setFirstName("Joe");
		testU7.setLastName("Rogan");
		users.add(testU7);
		//Write the users file
		urw.writeUsers(users);
		
		
		//Make the conferences
		//Make the deadlines for Seattle Conference
		//Seattle Conference has testU7 as Spc
		//TestU1 - TestU6 as reviewers that are assigned to testU7
		//one Manuscript submitted with TestU1 as its only author
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date paperDead = new Date();
		Date revDead = new Date();
		Date recDead = new Date();
		Date finalDead = new Date();
        try {
            paperDead = dateformat.parse("30-06-2017 11:59:59");
            revDead = dateformat.parse("15-07-2017 11:59:59");
            recDead = dateformat.parse("01-08-2017 11:59:59");
            finalDead = dateformat.parse("7-08-2017 11:59:59");
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
        Conference seattleConf = new Conference("Seattle Conference", paperDead, revDead, recDead, finalDead);
        //joe@hotmail.com is the spc of this conference.
		SubprogramChair spc = seattleConf.addSubprogramChair(testU7);
		//All other Users are Reviewers in this conference and are assigned to testU7.
		//This is a pain but it simulates how the full program would work, a user would NEED to 
		//be made a reviewer in a conference and then that reviewer that exists in the conference
		//would need to be assigned to a SPC so they could have manuscripts assigned to them
		Reviewer rev1 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev1);
		Reviewer rev2 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev2);
		Reviewer rev3 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev3);
		Reviewer rev4 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev4);
		Reviewer rev5 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev5);
		Reviewer rev6 = seattleConf.addReviewer(testU1);
		spc.addReviewerToSPC(rev6);
		
		//TestU1's Manuscript, it has been added to the conference
		//It has also been assigned to the SPC so that they have something to assign to a reviewer
		Manuscript testMan1 = new Manuscript("a paper", "stuff", testU1);
		seattleConf.addManuscript(testMan1);
		spc.addPaperToSPC(testMan1);
		conferences.add(seattleConf);
		crw.writeConferences(conferences);
		
	}

}