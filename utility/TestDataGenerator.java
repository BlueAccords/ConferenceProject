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
import model.Manuscript.AuthorExistsInListException;
import model.Reviewer;
import model.SubprogramChair;

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
				Arrays.asList("john@gmail.com", "james@gmail.com", "robert@gmail.com", "david@gmail.com",
						   "connor@gmail.com", "morgan@gmail.com", "josiah@gmail.com", "casey@gmail.com",
						   "ryan@gmail.com", "lisa@gmail.com", "mary@gmail.com", "lucas@gmail.com",
						   "aria@gmail.com", "caden@gmail.com", "zoe@gmail.com", "jacob@gmail.com",
						   "lily@gmail.com", "logan@gmail.com", "nora@gmail.com", "levi@gmail.com"));

		User.initializeUserListToEmptyList();
		initUsers(myUsernameList);
		initReviewers();
		
		// init Conferences before and after current date.
		Conference.initializeConferenceListToEmptyList();
		initConferences();
		
		// init manuscripts and manuscript names
		myManuscriptNameList = generateManuscriptNamesList();
		myManuscriptList = new TreeMap<String, Manuscript>();
				
		
		
		// Using users myUserList[0..6] excluding #6
		// Using Conference[0] acm and Conference[4] icml
		// using Manuscripts 0-8 inclusive.
		User  userAsSPC = myUserList.get(myUsernameList.get(0));
		setupUserStoryOne(userAsSPC);
		
		// using josiah@email.com as spc for user story 2
		// using myUserList[6..11] excluding #11
		// using Conference[1] cpvr
		// using Manuscripts 9-12 inclusive
		User userAsSPC2 = myUserList.get(myUsernameList.get(6));
		setupUserStoryTwo(userAsSPC2, myConferenceList.get(myConferenceNameList.get(1)));
		
		// using caden@email.com as the author
		// using Manuscripts 13-17 inclusive
		User userAsAuthor1 = myUserList.get(myUsernameList.get(13));
		setupUserStoryThree(userAsAuthor1, myConferenceList.get(myConferenceNameList.get(3)));
		
		
		Conference.writeConferences();
		User.writeUsers();
	}
	
	
	/**
	 * set up User Story 3:
	 *	As an Author, I want to submit a manuscript to a conference. 
	 * @author Ryan Tran
	 * @param theUser
	 * @param theConference
	 */
	private static void setupUserStoryThree(User theUser, Conference theConference) {
		Author mainAuth = new Author(theUser);
		
		/**
		 * Business Rule 3a:
		 * 	All manuscript submissions must be made on or before the submission deadline before midnight UTC-12.
		 */
		
		// submit 4 manuscripts using main auth.
		for(String manuName : myManuscriptNameList.subList(13, 17)) {
			Manuscript manu = new Manuscript(
				manuName,
				new File(""),
				mainAuth,
				generateRandomDateBefore(theConference.getManuscriptDeadline(), true));
			
			try {
				theConference.addManuscript(manu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		myConferenceList.put(theConference.getConferenceName(), theConference);
	}
	
	/**
	 * Setup for User Story 2:
	 * 		As a Subprogram Chair, I want to submit a recommendation for a manuscript to which I have been assigned.
	 * 
	 * @author Ryan Tran
	 * @param theUserToBeSPC the User to be the subprogram chair for this story
	 * @param theConf the conference this story is using.
	 */
	private static void setupUserStoryTwo(User theUserToBeSPC, Conference theConf) {
		/**
		 * Business Rule 2a:
		 *   A manuscript recommendation cannot be submitted unless there exists a minimum of three reviews for this manuscript.
		 */

		// Manu 1: 0 reviews submitted
		// Manu 2: 2 reviews submitted
		// Manu 3: 3 reviews submitted
		// Manu 4: 4 reviews submitted
		// Manu 5: 3 reviews submitted(spare to demonstrate submit recommendation)

		// Add 5 users as reviwers to conf
		for(String username : myUsernameList.subList(0, 6)) {
			myReviewerList.put(username, new Reviewer(myUserList.get(username)));
			theConf.addReviewer(myReviewerList.get(username));
		}
		
		Author mainAuthor = new Author(myUserList.get(myUsernameList.get(1)));

		// Manuscript 1 will have 0 reviews submitted
		Manuscript manu1 = new Manuscript(
				myManuscriptNameList.get(9),
				new File(""),
				mainAuthor,
				generateRandomDateBefore(theConf.getManuscriptDeadline(), true));
		
		
		// Manuscript 2 will have 2 reviews submitted
		Manuscript manu2 = new Manuscript(
				myManuscriptNameList.get(10),
				new File(""),
				mainAuthor,
				generateRandomDateBefore(theConf.getManuscriptDeadline(), true));

		for(String username: myUsernameList.subList(2, 4)) {
			int reviewScore = (int)((Math.random() * 9 + 1));
			addReviewerToManuscriptAndSubmitReview(manu2, myReviewerList.get(username), reviewScore);
		}

		// Manuscript 3 will have 3 reviews submitted
		Manuscript manu3 = new Manuscript(
				myManuscriptNameList.get(11),
				new File(""),
				mainAuthor,
				generateRandomDateBefore(theConf.getManuscriptDeadline(), true));

		for(String username: myUsernameList.subList(2, 5)) {
			int reviewScore = (int)((Math.random() * 9 + 1));
			addReviewerToManuscriptAndSubmitReview(manu3, myReviewerList.get(username), reviewScore);
		}
		
		// Manuscript 3 will have 4 reviews submitted
		Manuscript manu4 = new Manuscript(
				myManuscriptNameList.get(12),
				new File(""),
				mainAuthor,
				generateRandomDateBefore(theConf.getManuscriptDeadline(), true));

		for(String username: myUsernameList.subList(2, 6)) {
			int reviewScore = (int)((Math.random() * 9 + 1));
			addReviewerToManuscriptAndSubmitReview(manu4, myReviewerList.get(username), reviewScore);
		}
		
		// Manuscript 3 will have 4 reviews submitted
		Manuscript manu5 = new Manuscript(
				myManuscriptNameList.get(13),
				new File(""),
				mainAuthor,
				generateRandomDateBefore(theConf.getManuscriptDeadline(), true));

		for(String username: myUsernameList.subList(2, 5)) {
			int reviewScore = (int)((Math.random() * 9 + 1));
			addReviewerToManuscriptAndSubmitReview(manu5, myReviewerList.get(username), reviewScore);
		}
		
		// Add manuscripts to conference
		try {
			theConf.addManuscript(manu1);
			theConf.addManuscript(manu2);
			theConf.addManuscript(manu3);
			theConf.addManuscript(manu4);
			theConf.addManuscript(manu5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		theConf.addSubprogramChair(new SubprogramChair(theUserToBeSPC));
		myConferenceList.put(theConf.getConferenceName(), theConf);
		// make method to add manuscript with reviewers and submitted their reviews
	}
	
	private static void addReviewerToManuscriptAndSubmitReview(Manuscript theManuscript,
			Reviewer theReviewer, int theScore) {

		theManuscript.addReviewer(theReviewer);
		theManuscript.addReview(new File(""));
		theReviewer.setReviewerScore(theManuscript, theScore);
	}
	
	/**
	 * Set up for...
	 * User Story 1:
	 * 		As a Subprogram Chair, I want to assign a reviewer to a manuscript to which I have been assigned.
	 *  
	 *  @author Ryan Tran
	 *  @version 5/29/17
	 *  @param theUserToBeSPC the subprogram chair to login as for the user story
	 */
	private static void setupUserStoryOne(User theUserToBeSPC) {
		SubprogramChair theSPC = new SubprogramChair(theUserToBeSPC);
		
		// ACM Conf
		Conference confAfterDeadline = myConferenceList.get(myConferenceNameList.get(0));
		// ICML Conf
		Conference confBeforeDeadline = myConferenceList.get(myConferenceNameList.get(4));
		
		/**
		 * Business Rule 1a
		 * 		A Reviewer cannot review a manuscript that he or she authored or co-authored.
		 * 
		 * Cases: 
		 * 	1. Reviewer is valid reviewer
		 *  2. Reviewer is also author of manuscript
		 *  3. Reviewer is also co-author of manuscript
		 */
		
		/**
		 * Set up for james@email.com as..
		 * Author of Linear Logic
		 * And invalid Reviewer for Linear Logic
		 * In Conference ICML
		 */
		// james@email.com as reviewer and author of manuscript to be assigned to.
		User userAsAuthAndReviewer = myUserList.get(myUsernameList.get(1));
		Author authAsInvalidReviewer = new Author(userAsAuthAndReviewer);
		// Linear Logic Manuscript
		String manuWithConflictAuthName = myManuscriptNameList.get(0);
		
		// init manuscript and set up james@email.com as reviewer  
		Manuscript manuWithInvalidAuthor = new Manuscript(
				manuWithConflictAuthName,
				new File(""),
				authAsInvalidReviewer,
				generateRandomDateBefore(confAfterDeadline.getManuscriptDeadline(), true));

		// add john@email.com as the SPC to ACM conf and ICML conf
		confAfterDeadline.addSubprogramChair(theSPC);
		
		// add manuscript to ICML conf.
		try {
			confAfterDeadline.addManuscript(manuWithInvalidAuthor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Business Rule 1b:
		 * 		A Reviewer can be assigned to review a maximum of 8 manuscripts for any conference.
		 * 		Using morgan@email.com with 7 assigned manuscripts to demonstrate rule.
		 */
		
		// morgan@email.com as reviewer
		Reviewer reviewerWithSeven = new Reviewer(myUserList.get(myUsernameList.get(5)));
		// add random manuscripts to show author is valid for others
		// author is josiah@email.com
		addManuscriptsToConfForAuthorAndReviewer(
				confAfterDeadline.getConferenceName(),
				myManuscriptNameList.subList(1, 5),
				myUsernameList.get(2),
				reviewerWithSeven);
		
		addManuscriptsToConfForAuthorAndReviewer(
				confAfterDeadline.getConferenceName(),
				myManuscriptNameList.subList(5, 8),
				myUsernameList.get(3),
				reviewerWithSeven);
		
		/**
		 * Setup for connor@email.com as...
		 * CoAuthor of Manuscript "Software engineering education—adjusting our sails"
		 * Thus will be invalid as reviewer for that manuscript.
		 */
		Author coAuthAsInvalidReviewer = new Author(myUserList.get(myUsernameList.get(4)));
		
		Manuscript manuWithInvalidCoAuth = new Manuscript(
				myManuscriptNameList.get(9),
				new File(""),
				authAsInvalidReviewer,
				generateRandomDateBefore(confAfterDeadline.getManuscriptDeadline(), true)
				);
		
		// adding david@email.com as coAuthor to manuscript
		try {
			manuWithInvalidCoAuth.addAuthor(coAuthAsInvalidReviewer);
		} catch (AuthorExistsInListException e) {
			System.out.println("Failed to add coauthor");
			e.printStackTrace();
		}
		
		try {
			confAfterDeadline.addManuscript(manuWithInvalidCoAuth);
		} catch (Exception e) {
			System.out.println("Failed to add manu to conf");
			e.printStackTrace();
		}
		
		// Add random reviewers to conference to show the invalid author is only visible for some manuscripts
		addUsersAsReviewersToConf(myUsernameList.subList(1, 6), confAfterDeadline);
		
		/**
		 * Business rule 1c:
		 * 		A Reviewer cannot be assigned until after the author submission deadline.
		 */
		// add john@email.com as spc to ICML conf.
		confBeforeDeadline.addSubprogramChair(theSPC);

		// Set david@email.com as reviewer though won't be assignable due to deadline.
		Reviewer validReviewer = new Reviewer(myUserList.get(myUsernameList.get(3)));
		
		addManuscriptsToConfForAuthor(
				confBeforeDeadline.getConferenceName(),
				myManuscriptNameList.subList(8, 9),
				myUsernameList.get(4));

		confBeforeDeadline.addReviewer(validReviewer);
		
		// update objects in current test data lists
		myConferenceList.put(confAfterDeadline.getConferenceName(), confAfterDeadline);
		myConferenceList.put(confBeforeDeadline.getConferenceName(), confBeforeDeadline);
		myManuscriptList.put(manuWithConflictAuthName, 	manuWithInvalidAuthor);	
	}
	
	
	/**
	 * Adds the list of usernames as reviewers to the given conference.
	 * @param theUsernames the list of usernames to add as reviewers to the given conf.
	 * @param theConf the conference.
	 */
	private static void addUsersAsReviewersToConf(List<String> theUsernames, Conference theConf) {
		for(String username : theUsernames) {
			theConf.addReviewer(myReviewerList.get(username));
		}
		
		// update internal conf list.
		myConferenceList.put(theConf.getConferenceName(), theConf);
	}
	
	/**
	 * Adds the list of manuscripts to the passed in conference and sets the author of the manuscript
	 * to the given author name.
	 * 
	 * @author Ryan Tran
	 * @param theConfName the conference name
	 * @param theManuList the list of manuscript names to add to the conference
	 * @param theUsername the user name to set as the author
	 */
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
		
		if(DEBUG) {
			System.out.println(confByName.getManuscripts().size() + " Manuscripts added "
				+ " to " + confByName.getConferenceName() + " for Author " + theUsername);
		}
	}
	
	/**
	 * Adds the manuscripts to the given conference with the given username
	 * as the author of the manuscripts. Additionally, adds the given reviewer name
	 * to the manuscripts as their reviewer.
	 * @param theConfName conf to add manuscript list to
	 * @param theManuList the list of manuscripts to add to conference
	 * @param theUsername the user to be the author of manuscripts
	 * @param theReviewerName the reviewer to be assigned to the list of manuscripts
	 */
	private static void addManuscriptsToConfForAuthorAndReviewer(String theConfName,
			List<String> theManuList, String theUsername, Reviewer theReviewer) {

		Author auth = new Author(myUserList.get(theUsername));
		Conference confByName = myConferenceList.get(theConfName);
		int initialConfManuListSize = confByName.getManuscripts().size();
		
		// Add manuscripts to generator manuscript list.
		for(String manuName : theManuList) {
			Manuscript manuToBeSaved = new Manuscript(
					manuName,
					new File(""),
					auth,
					generateRandomDateBefore(confByName.getManuscriptDeadline(), true));

			manuToBeSaved.addReviewer(theReviewer);
			myManuscriptList.put(manuName, manuToBeSaved);
			
			// add manuscript to passed in conference and update test data generator list of conferences
			try {
				confByName.addManuscript(manuToBeSaved);
				myConferenceList.put(theConfName, confByName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// add reviewer to updated reviewer list
		myReviewerList.put(theReviewer.getUser().getEmail(), theReviewer);
		
		if(DEBUG) {
			int manusAdded = confByName.getManuscripts().size() - initialConfManuListSize;
			System.out.println(manusAdded + " Manuscripts added "
				+ " to " + confByName.getConferenceName() + " for Author " + theUsername);
			System.out.println(theReviewer.getUser().getEmail() + " added as reviewer for said manuscripts.");

		}
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
		listToReturn.add("LAFF: Programming for Correctness");
		listToReturn.add("Introduction to Mathematical Thinking");
		listToReturn.add("Importance of Mathematics for Computer Science");
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
	public static Date generateRandomDateBefore(Date theDate, boolean theBeforeToday) {
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
