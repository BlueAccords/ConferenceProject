package client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Reviewer;
import model.SubprogramChair;
import model.User;

/**
 * This is the driver and view component of the program. 
 * @author James Roberts, Vincent Povio
 * @version 5/7/2017
 */
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
	
	/**
	 * Logic and display for the view.
	 * @param args the arguments (not used)
	 * @author Vincent Povio, James Roberts
	 * @version 5/7/2017
	 */
	public static void main(String[] args) {
		//Read in the files of Users and Conferences.
		setUp();
		do {
			//Get user login
			System.out.println("Welcome to the IEEE Conference System.");
			System.out.println("Please login using your email or press 9 to exit: ");
			userLogin = scan.nextLine();
			//Checks if user entered 9 to exit.
			if (!userLogin.equals("9")) {
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
						if (!conferenceName.equals("9")) {
							for (Conference targetConference : conferenceList) {
								if (targetConference.getConferenceName().equals(conferenceName)) {
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
					} while (!conferenceName.equals("9"));
				} else {
					System.out.println("You entered " + userLogin + ". This is an invalid username, please try again.");
				}
			}
		} while (!userLogin.equals("9"));
		System.out.println("Thank you, and goodbye.");
		//This has to happen when the program exits. If not any changes made to a conference will not be saved.
		Conference.writeConferences(conferenceList);
		User.writeUsers(userList);
	}

	private static boolean printViewScreen() {
		int n = 1, selection = 0;
		String title = null;
		String manuscript = null;
		String email = null;
		Manuscript theManuscript = null;
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		
		switch (currentView) {
		case "Author":
			System.out.println("Current view: Author");
			Author theAuthor = currentConference.getAuthor(theUser);

			System.out.println("Conference Submission Deadline: " 
					+ dateformat.format(currentConference.getPaperDeadline()));
			System.out.println();
			System.out.println("Your Submissions:");
			if (theAuthor != null) {
				for (Manuscript targetManuscript : theAuthor.getMyManuscripts()) {
					System.out.println(n + ") " + targetManuscript.getTitle() + " Submitted: " 
						+ dateformat.format(targetManuscript.getSubmissionDate()));
					n++;
				}
			}
			System.out.println();
			System.out.println("Please select from the following options:");
			System.out.println("1) Add a manuscript.");
			System.out.println("9) Exit");
			selection = scan.nextInt();
			switch (selection) {
			case 1:
				scan.nextLine();
				System.out.println("To add a manuscript, please enter a title: ");
				title = scan.nextLine();
				System.out.println("Please copy and paste the manuscript's body: ");
				manuscript = scan.nextLine();
				theManuscript = new Manuscript(title, manuscript, theUser);
				
				int secondSel;
				for (int i = 0; i < 5; i++) {
					System.out.println("Select 2 to add a CoAuthor or 3 to submit the manuscript:  ");
					secondSel = scan.nextInt();
					scan.nextLine(); // clear new line char.
					String coAuthorID;
					if (secondSel == 2) {
						System.out.println("Enter the User ID of the CoAuthor: ");
						coAuthorID = scan.nextLine();
						//Need to see if CoAuthor exists in the system
						
						boolean found = false;
						User coAuthor = null;
						for (int j = 0; j < userList.size(); j++) {
							if(userList.get(j).getEmail().equals(coAuthorID)) {
								coAuthor = userList.get(j);
								j = userList.size();
							}
						}
						
						//They don't exist, make a user for them. 
						if(!found) {
							coAuthor = new User(coAuthorID);
							userList.add(coAuthor);
						}
						//Can now add them as an author
						theManuscript.addAuthor(coAuthor);
						
					} else if (secondSel == 3) {
						  i = 6;
					} else {
						System.out.println("Invalid Input, please select 2 to add a" + 
											" CoAuthor or 3 to submit the manuscript:  ");
						secondSel = scan.nextInt();
						scan.nextLine(); // clear new line char
					}
				}
					
				
				try {
					currentConference.addManuscript(theManuscript);
				} catch (Exception e) {
					System.out.println("Your Submission was not accepted: " + e.getMessage());
					System.out.println("Press any key to continue: ");
					scan.nextLine();
				}
				
			
				
				return false;
			case 9:
				//Clear buffer
				scan.nextLine();
				currentConference = null;
				//Write the list of conference in case any changes have been made.
				Conference.writeConferences(conferenceList);
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
			System.out.println();
			System.out.println("Recommendation Submission Deadline: " 
					+ dateformat.format(currentConference.getRecDeadline()));
			System.out.println();
			System.out.println("Your Assigned Papers:");
			for (Manuscript targetManuscript : theSPC.getAssignedPapersSPC()) {
				System.out.println(n + ") " + targetManuscript.getTitle());
				n++;
			}
			
			int j = 1, k = 1;
			System.out.println();
			System.out.println("Your Reviewers:");
			for (Reviewer targetReviewer : theSPC.getAssignedReviewers()) {
				System.out.println(j + ") " + targetReviewer.getUser().getEmail());
				System.out.println("    Assigned Papers: ");
				for (Manuscript man : targetReviewer.getAssignedManuscripts()) {
					System.out.println("             " + k + ") " + man.getTitle());
					k++;
				}
				k = 1;
				System.out.println();
				j++;
			}
			System.out.println();
			System.out.println("Please select from the following options:");
			System.out.println("1) Assign a reviewer to a paper.");
			System.out.println("9) Exit");
			selection = scan.nextInt();
			scan.nextLine();
			switch (selection) {
			case 1:
				System.out.print("Please enter the name of the manuscript you would like to add a reviewer to: ");
				title = scan.nextLine();
				boolean found = false;
				while(!found) {
					for (Manuscript targetManuscript : currentConference.getManuscripts()) {
						if (targetManuscript.getTitle().equals(title)) {
							theManuscript = targetManuscript;
							found = true;
						}
					}
					if (theManuscript == null) {
						System.out.print("Please enter a valid manuscript name: ");
						title = scan.nextLine();
					}
				}
				System.out.println("Eligible reviewers for this manuscript: ");
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
				//Write any possible changes
				Conference.writeConferences(conferenceList);
				User.writeUsers(userList);
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
				
		conferenceList = Conference.readConferences();
		userList = User.readUsers();
		//The file doesn't exist, this shouldn't happen since the .ser files should be uploaded to git
		if (conferenceList == null || userList == null) {
			try {
				buildTestingFiles();
			} catch (Exception e) {
				System.out.println("Creating Files Failed");
			}
			userList = User.readUsers();
			conferenceList = Conference.readConferences();
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
		User testU8 = new User("linda@msn.com");
		testU8.setFirstName("Linda");
		testU8.setLastName("Snow");
		users.add(testU8);
		//Write the users file
		User.writeUsers(users);
		
		
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
		
		Manuscript testMan1 = new Manuscript("Neat Computer Stuff", "stuff", testU1);
		seattleConf.addManuscript(testMan1);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan1);

		Manuscript testMan2 = new Manuscript("Wow Coding is neat", "stuff", testU1);
		seattleConf.addManuscript(testMan2);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan2);

		Manuscript testMan3 = new Manuscript("Report on My Hello World", "stuff", testU2);
		seattleConf.addManuscript(testMan3);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan3);
		
		Manuscript testMan4 = new Manuscript("Why you should only code in C", "stuff", testU2);
		seattleConf.addManuscript(testMan4);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan4);
		Manuscript testMan4a = new Manuscript("Why you should only code in C", "stuff", testU2);
		seattleConf.addManuscript(testMan4a);
		Manuscript testMan4b = new Manuscript("Why you should only code in C", "stuff", testU2);
		seattleConf.addManuscript(testMan4b);
		Manuscript testMan4c = new Manuscript("Why you should only code in C", "stuff", testU2);
		seattleConf.addManuscript(testMan4c);
		Manuscript testMan5 = new Manuscript("Great Ascii Art via the Console", "stuff", testU3);
		seattleConf.addManuscript(testMan5);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan5);
		
		Manuscript testMan6 = new Manuscript("My Python Hello World", "stuff", testU8);
		seattleConf.addManuscript(testMan6);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan6);
		
		Manuscript testMan7 = new Manuscript("Object Oriented Stuff", "stuff", testU8);
		seattleConf.addManuscript(testMan7);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan7);
		
		Manuscript testMan8 = new Manuscript("An Exploration in Agile", "stuff", testU3);
		seattleConf.addManuscript(testMan8);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan8);

		Manuscript testMan9 = new Manuscript("JavaDocs for Clarity", "stuff", testU8);
		seattleConf.addManuscript(testMan9);
		seattleConf.getConfSPCs().get(0).addPaperToSPC(testMan9);
		
		Reviewer rev2 = seattleConf.addReviewer(testU2);
		seattleConf.getConfSPCs().get(0).addReviewerToSPC(rev2);
		rev2.addManuscriptToReviewer(testMan7);
		rev2.addManuscriptToReviewer(testMan8);
		
		Reviewer rev3 = seattleConf.addReviewer(testU3);
		seattleConf.getConfSPCs().get(0).addReviewerToSPC(rev3);
		rev3.addManuscriptToReviewer(testMan1);
		rev3.addManuscriptToReviewer(testMan2);
		
		Reviewer rev4 = seattleConf.addReviewer(testU4);
		seattleConf.getConfSPCs().get(0).addReviewerToSPC(rev4);
		rev4.addManuscriptToReviewer(testMan1);
		Reviewer rev5 = seattleConf.addReviewer(testU5);
		seattleConf.getConfSPCs().get(0).addReviewerToSPC(rev5);
		
		Reviewer rev6 = seattleConf.addReviewer(testU6);
		rev6.addManuscriptToReviewer(testMan1);
		rev6.addManuscriptToReviewer(testMan2);
		rev6.addManuscriptToReviewer(testMan3);
		rev6.addManuscriptToReviewer(testMan4);
		rev6.addManuscriptToReviewer(testMan5);
		rev6.addManuscriptToReviewer(testMan6);
		rev6.addManuscriptToReviewer(testMan7);
		rev6.addManuscriptToReviewer(testMan8);
		seattleConf.getConfSPCs().get(0).addReviewerToSPC(rev6);
		
		//TestU1's Manuscript, it has been added to the conference
		//It has also been assigned to the SPC so that they have something to assign to a reviewer
		
		
		
		
		// A conference with a submission deadline in the past
		Date paperDead2 = new Date();
        Conference detroitConf = new Conference("Detroit Conference", paperDead2, revDead, recDead, finalDead);
        conferences.add(seattleConf);
        conferences.add(detroitConf);
		Conference.writeConferences(conferences);
	}

}