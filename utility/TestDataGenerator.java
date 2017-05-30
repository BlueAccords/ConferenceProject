package utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import model.User;
import model.Author;
import model.Conference;
import model.Manuscript;
import model.Reviewer;

public class TestDataGenerator {
	
	private static ArrayList<String> myUsernameList;
	private static ArrayList<String> myConferenceNameList;
	private static ArrayList<String> myManuscriptNameList;
	private static TreeMap<String, User> myUserList;
	private static TreeMap<String, Reviewer> myReviewerList;
	private static TreeMap<String, Conference> myConferenceList;
	private static TreeMap<String, Manuscript> myManuscriptList;
	private static boolean DEBUG;
	
	public static void generateMasterTestData(boolean isDebug) {
		DEBUG = isDebug;
		myUsernameList = new ArrayList<String>(
				Arrays.asList("john@email.com", "james@email.com", "robert@email.com", "david@email",
						   "connor@email.com", "morgan@email.com", "josiah@email.com", "casey@email.com",
						   "ryan@email.com", "lisa@email.com", "mary@email.com"));

		User.initializeUserListToEmptyList();
		initUsers(myUsernameList);
		initReviewers();
		
		// init Conferences before and after current date.
		Conference.initializeConferenceListToEmptyList();
		initConferences();
		
		// init manuscripts and manuscript names
		myManuscriptNameList = generateManuscriptNamesList();
		myManuscriptList = new TreeMap<String, Manuscript>();
		
		addManuscriptsToConfForAuthor(
				myConferenceNameList.get(0),
				myManuscriptNameList.subList(0, 5),
				myUsernameList.get(3));
		
		addManuscriptsToConfForAuthor(
				myConferenceNameList.get(4),
				myManuscriptNameList.subList(5, 7),
				myUsernameList.get(3));
		
		
		/**
		 * User Story 1:
		 * 		As a Subprogram Chair, I want to assign a reviewer to a manuscript to which I have been assigned.
		 */
		
		
		
		
		Conference.writeConferences();
		User.writeUsers();
	}
	
	private static void addManuscriptsToConfForAuthor(String theConfName, List<String> theManuList, String theUsername) {
		Author auth = new Author(myUserList.get(theUsername));
		Conference confByName = myConferenceList.get(theConfName);
		
		// Add manuscripts to generator manuscript list.
		for(String manuName : theManuList) {
			myManuscriptList.put(manuName, new Manuscript(
					manuName,
					new File(""),
					auth,
					generateRandomDateBefore(confByName.getManuscriptDeadline(), true)
			));
			
			// add manuscript to passed in conference and update test data generator list of conferences
			try {
				confByName.addManuscript(myManuscriptList.get(manuName));
				myConferenceList.put(theConfName, confByName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(confByName.getManuscripts().size() + " Manuscripts added "
				+ " to " + confByName.getConferenceName() + " for Author " + theUsername);
	}
	
	/**
	 * Generates and returns an array list of manuscript names as strings
	 * @return an arraylist of strings of manuscript names
	 */
	private static ArrayList<String> generateManuscriptNamesList() {
		ArrayList<String> listToReturn = new ArrayList<String>();

		listToReturn.add("Linear Logic");
		listToReturn.add("Quantum Crytography: Public Key Distribution and Coin Tossing");
		listToReturn.add("A Theory of Limited Automata");
		listToReturn.add("Simplified NP-Complete Problems");
		listToReturn.add("Theory of Genetic Algorithms");
		listToReturn.add("Ranking of Accessibility in Sorting Algorithms");
		listToReturn.add("Theory of Cellular Automata: A survey");
		listToReturn.add("Constructivism in computer science education");
		listToReturn.add("Teaching software engineering—experience from the past, needs for the future");
		listToReturn.add("Software engineering education—adjusting our sails");
		listToReturn.add("Human-computer interaction in the Informatics curriculum");
		listToReturn.add("Why we should no longer only repair, polish and iron current computer science educations");
		listToReturn.add("Visualization education in the USA");
		listToReturn.add("A meta-study of algorithm visualization effectiveness");
		listToReturn.add("Concept mapping: A useful tool for computer science education");
		listToReturn.add("A methodological review of computer science education research");
		listToReturn.add("Teacher beliefs and intentions regarding the implementation of computer science education reform strands");
		return listToReturn;
	}
	
	/**
	 * This method will generate a list of conferences,
	 * with some with their submission deadline past and some with their submission deadline in the future.
	 * @author Ryan Tran
	 * @version 5/29/17
	 */
	private static void initConferences() {
		int confsWithDeadlinePast = 0;
		int confsWithDeadlineInFuture = 0;
		
		myConferenceList = new TreeMap<String, Conference>();
		myConferenceNameList = new ArrayList<String>(Arrays.asList(
				"ACM Conference",
				"CPVR - Conference on Computer Vision and Pattern Recognition",
				"CHI - Conference on Computer Human Interaction",
				"EECV - European Conference on Computer Vision ",
				"ICML - International Conference on Machine Learning"
				));
		
		/**
		 * Create conferences with submission deadlines in the PAST.
		 */
		for(int i = 0; i < myConferenceNameList.size() - 2; i++) {
			// manuscript submission date for 0 to size - 2 should be before current date.
			
			// each date should be AFTER the previous one per conference
			Date manuSubDate = generateRandomDateBefore(new Date(), true);
			Date reviewerSubDate = generateRandomDateBefore(new Date(), false);
			Date recommSubDate = generateRandomDateBefore(reviewerSubDate, false);
			Date finalSubDate = generateRandomDateBefore(recommSubDate, false);
			
			// create actual conference and add to test data list
			myConferenceList.put(myConferenceNameList.get(i), new Conference(
					myConferenceNameList.get(i),
					manuSubDate,
					reviewerSubDate,
					recommSubDate,
					finalSubDate));
			
			// add to in-memory list
			Conference.addConference(myConferenceList.get(myConferenceNameList.get(i)));
		}
		
		confsWithDeadlinePast = Conference.getConferences().size();
		
		/**
		 * Create Conferences with submission deadlines in the FUTURE.
		 */
		for(int i = myConferenceNameList.size() - 2 ; i < myConferenceNameList.size(); i++) {
			// manuscript submission date for 0 to size - 2 should be before current date.
			
			// each date should be AFTER the previous one per conference
			Date manuSubDate = generateRandomDateBefore(new Date(), false);
			Date reviewerSubDate = generateRandomDateBefore(new Date(), false);
			Date recommSubDate = generateRandomDateBefore(reviewerSubDate, false);
			Date finalSubDate = generateRandomDateBefore(recommSubDate, false);
			
			// create actual conference and add to test data list
			myConferenceList.put(myConferenceNameList.get(i), new Conference(
					myConferenceNameList.get(i),
					manuSubDate,
					reviewerSubDate,
					recommSubDate,
					finalSubDate));
			
			// add to in-memory list
			Conference.addConference(myConferenceList.get(myConferenceNameList.get(i)));
		}
		
		confsWithDeadlineInFuture = Conference.getConferences().size() - confsWithDeadlinePast;
		
		if(DEBUG) {
			System.out.println(confsWithDeadlinePast + " Conferences with deadlines in the PAST created");
			System.out.println(confsWithDeadlineInFuture + " Conferences with deadlines in the FUTURE created");
		}
	
	}
	
	/**
	 * This method will init a list of reviewers based on the existing
	 * list of users.
	 * Pre:
	 * 	myUserList must be non-null
	 * 
	 * @author Ryan Tran
	 * @version 5/29/17
	 */
	private static void initReviewers() {
		myReviewerList = new TreeMap<String, Reviewer>();

		for(User aUser : myUserList.values()) {
			myReviewerList.put(aUser.getEmail(), new Reviewer(aUser));
		}
		
		if(DEBUG) {
			System.out.println(myReviewerList.size() + " Reviewers created");
		}
	}
	
	/**
	 * This method will initialize the in memory list of users to empty,
	 * create a new list of users, then write the users to the serialized User list object.
	 * 
	 * @author Ryan Tran
	 * @version 5/29/17
	 */
	private static void initUsers(ArrayList<String> theUsernameList) {
		myUserList = new TreeMap<String, User>();
		// list of all usernames
		
		for(String username : theUsernameList) {
			myUserList.put(username, new User(username));
			User.addUser(myUserList.get(username));
		}
		
		if(DEBUG) {
			System.out.println(User.getUsers().size() + " Users created.");
		}
	}

	/**
	 * This method will generate a random day 5-30 days before the given
	 * date argument.
	 * 
	 * Depending on boolean theBeforeToday will generate random date
	 * 5-30 days in the future or in the past.
	 * 
	 * @author Ryan Tran
	 * @version 5/29/17
	 * @param theDate the Date argument base to generate a date off.
	 * @param theBeforeToday
	 * @return a Date object 5-30 days before the given date argument
	 */
	private static Date generateRandomDateBefore(Date theDate, boolean theBeforeToday) {
		Date DateToReturn;
		
		// generate random number between 5-30
		int randomDaysBack = (int)((Math.random() * 30 + 5));
		
		// if true, make it so we generate a random date before the given date.
		// else generate a random date AFTER given date.
		if(theBeforeToday) {
			randomDaysBack = randomDaysBack * -1;
		}

		// Generate new date at 00:00 UTC -12 minus 5-30 days from the passed in date argument
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(theDate);
        tempCalendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT+12"));
		tempCalendar.add(Calendar.DATE, randomDaysBack); 
		tempCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);
        tempCalendar.set(Calendar.SECOND, 0);
        tempCalendar.set(Calendar.MINUTE, 0);
        DateToReturn = tempCalendar.getTime();

        return DateToReturn;
	}

}
