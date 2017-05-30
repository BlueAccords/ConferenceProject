package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;

import model.User;
import model.Conference;
import model.Reviewer;

public class TestDataGenerator {
	
	private static ArrayList<String> myUsernameList;
	private static ArrayList<String> myConferenceNameList;
	private static TreeMap<String, User> myUserList;
	private static TreeMap<String, Reviewer> myReviewerList;
	private static TreeMap<String, Conference> myConferenceList;
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
		
		
		Conference.writeConferences();
		User.writeUsers();
	}
	
	/**
	 * This method will generate the list of conferences and their names.
	 * @author Ryan Tran
	 * @version 5/29/17
	 */
	private static void initConferences() {
		myConferenceList = new TreeMap<String, Conference>();
		myConferenceNameList = new ArrayList<String>(Arrays.asList(
				"ACM Conference",
				"CPVR - Conference on Computer Vision and Pattern Recognition",
				"CHI - Conference on Computer Human Interaction",
				"EECV - European Conference on Computer Vision ",
				"ICML - International Conference on Machine Learning"
				));
		
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
		
		System.out.println(Conference.getConferences().size() + " Conferences created without manuscripts");
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
	 * This method will generate a random day 1-30 days before the given
	 * date argument.
	 * 
	 * Depending on boolean theBeforeToday will generate random date
	 * 1-30 days in the future or in the past.
	 * 
	 * @author Ryan Tran
	 * @version 5/29/17
	 * @param theDate the Date argument base to generate a date off.
	 * @param theBeforeToday
	 * @return a Date object 1-30 days before the given date argument
	 */
	private static Date generateRandomDateBefore(Date theDate, boolean theBeforeToday) {
		Date DateToReturn;
		
		// generate random number between 1-30
		int randomDaysBack = (int)((Math.random() * 30 + 1));
		
		// if true, make it so we generate a random date before the given date.
		// else generate a random date AFTER given date.
		if(theBeforeToday) {
			randomDaysBack = randomDaysBack * -1;
		}

		// Generate new date at 00:00 UTC -12 minus 1-30 days from the passed in date argument
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
