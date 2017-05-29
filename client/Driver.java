package client;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JPanel;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Manuscript.AuthorExistsInListException;
import model.Reviewer;
import model.SubprogramChair;
import model.User;


public class Driver {
	/**
	 * If debug is set to true, will init all data inside of debug if statement.
	 * If false will init data from stored data in serialized object
	 */
	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		Controller systemController = new Controller();
		
		// add dummy users
		if(DEBUG) {
			User.initializeUserListToEmptyList();
			
			//User set up
			User testUser1 = new User("john@email.com");
			User testUser2 = new User("connor@test.com", true);
			User testUser3 = new User("casanova@test.com");
			User testUser4 = new User("mdanger@test.com", true);
			User testUser5 = new User("jmoney@test.com");
			User testUser6 = new User("ryan@email.com");
			User testUserForMaxReviews = new User("AlfredMax@email.com");
			
			//Reviewer set up
			Reviewer testReviewer1 = new Reviewer(testUser1);
			Reviewer testReviewer2 = new Reviewer(testUser2);
			Reviewer testReviewer3 = new Reviewer(testUser3);
			Reviewer testReviewer4 = new Reviewer(testUser4);
			Reviewer testReviewer5 = new Reviewer(testUser5);
			Reviewer testReviewer6 = new Reviewer(testUser6);
			Reviewer testReviewerForMaxReviews = new Reviewer(testUserForMaxReviews);
			
			
			User testUserWithMaxManuscripts = new User("max@email.com");
			User.addUser(testUser6);
			User.addUser(testUser5);
			User.addUser(testUser4);
			User.addUser(testUser3);
			User.addUser(testUser2);
			User.addUser(testUser1);
			User.addUser(testUserWithMaxManuscripts);
			User.writeUsers();
			
			// set submission date to current date + 15 days
			// Setting time to 00:00
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(new Date());
	        tempCalendar.setTimeZone(TimeZone.getTimeZone("Etc/GMT+12"));
			tempCalendar.add(Calendar.DATE, 15); 
			tempCalendar.set(Calendar.HOUR_OF_DAY, 0);
			tempCalendar.set(Calendar.MILLISECOND, 0);
	        tempCalendar.set(Calendar.SECOND, 0);
	        tempCalendar.set(Calendar.MINUTE, 0);
			Date submissionDate = tempCalendar.getTime();
			
			// submission date before current date.
			tempCalendar.add(Calendar.DATE, -20);
			Date submissionBeforeDateNow = tempCalendar.getTime();
			
			// add dummy conferences
			Conference.initializeConferenceListToEmptyList();
			
			Conference acmConf = new Conference("ACM Conference", submissionDate, new Date(), new Date(), new Date());
			acmConf.addReviewer(testReviewer1);
			acmConf.addReviewer(testReviewer2);
			acmConf.addReviewer(testReviewer3);
			acmConf.addReviewer(testReviewer4);
			acmConf.addReviewer(testReviewer5);
			acmConf.addReviewer(testReviewer6);
			Conference cvprConf = new Conference("CPVR - Conference on Computer Vision and Pattern Recognition", submissionDate, new Date(), new Date(), new Date());
			cvprConf.addSubprogramChair(new SubprogramChair(testUser2));
			Conference chiConf = new Conference("CHI - Conference on Computer Human Interaction", submissionDate, new Date(), new Date(), new Date());
			Conference eccvConf = new Conference("EECV - European Conference on Computer Vision ", submissionDate, new Date(), new Date(), new Date());
			Conference icmlConf = new Conference("ICML - International Conference on Machine Learning", submissionDate, new Date(), new Date(), new Date());
			Conference pastDeadlineConf = new Conference("ICSE : International Conference on Software Engineering", submissionBeforeDateNow, new Date(), new Date(), new Date());
			Conference.addConference(acmConf);
			Conference.addConference(cvprConf);
			Conference.addConference(chiConf);
			Conference.addConference(eccvConf);
			Conference.addConference(icmlConf);
			Conference.addConference(pastDeadlineConf);
		
			// test authors for manuscripts
			Author testAuth1 = new Author(testUser1);
			Author testAuth2 = new Author(testUser2);
			Author testAuth3 = new Author(testUser3);
			Author testAuth5 = new Author(testUser5);
			Author testAuth6 = new Author(testUser6);
			Author testAuthWithMax = new Author(testUserWithMaxManuscripts);
			
			// Adding manuscripts to users.
			Manuscript manu1 = new Manuscript("Linear Logic", new File(""), testAuth1);
			Manuscript manu2 = new Manuscript("Quantum Crytography: Public Key Distribution and Coin Tossing", new File(""), testAuth1);
			Manuscript manu3 = new Manuscript("A Theory of Limited Automata", new File(""), testAuth2);
			Manuscript manu4 = new Manuscript("Simplified NP-Complete Problems", new File(""), testAuth2);
			Manuscript manu5 = new Manuscript("Theory of Genetic Algorithms", new File(""), testAuth2);
			Manuscript manu6 = new Manuscript("Theory of Cellular Automata: A survey", new File(""), testAuth3);
			Manuscript manu7 = new Manuscript("Ranking of Accessibility in Sorting Algorithms", new File(""), testAuth5);
			
			/**
			 * ==============================================================================================================
			 * SETUP for business rule:
			 * 	A Reviewer can be assigned to review a maximum of 8 manuscripts for any conference.
			 * spcForMaxReviews/testUser5-"jmoney@test.com" 				   : Subprogram Chair to assigned Reviewer Manuscripts
			 * icmlConf-"ICML  - International Conference on Machine Learning" : Conference
			 * testReviewer2   -"connor@test.com" 							   : Reviewer to be assigned 8+ manuscripts
			 */
			
			// init manuscripts
			Manuscript manuMaxReview1 = new Manuscript("Teaching software engineering—experience from the past, needs for the future", new File(""), testAuth6);
			Manuscript manuMaxReview2 = new Manuscript("Software engineering education—adjusting our sails", new File(""), testAuth6);
			Manuscript manuMaxReview3 = new Manuscript("Human-computer interaction in the Informatics curriculum", new File(""), testAuth6);
			Manuscript manuMaxReview4 = new Manuscript("Why we should no longer only repair, polish and iron current computer science educations", new File(""), testAuth6);
			Manuscript manuMaxReview5 = new Manuscript("Visualization education in the USA", new File(""), testAuth6);
			Manuscript manuMaxReview6 = new Manuscript("A meta-study of algorithm visualization effectiveness", new File(""), testAuth3);
			Manuscript manuMaxReview7 = new Manuscript("Concept mapping: A useful tool for computer science education", new File(""), testAuth3);
			Manuscript manuMaxReview8 = new Manuscript("A methodological review of computer science education research", new File(""), testAuth3);
			Manuscript manuMaxReview9 = new Manuscript("Teacher beliefs and intentions regarding the implementation of computer science education reform strands", new File(""), testAuth3);
			
			// assign manuscripts to spc
			ArrayList<Manuscript> maxAssignedManuscriptForSPC = new ArrayList<Manuscript>();
			maxAssignedManuscriptForSPC.add(manuMaxReview1);
			maxAssignedManuscriptForSPC.add(manuMaxReview2);
			maxAssignedManuscriptForSPC.add(manuMaxReview3);
			maxAssignedManuscriptForSPC.add(manuMaxReview4);
			maxAssignedManuscriptForSPC.add(manuMaxReview5);
			maxAssignedManuscriptForSPC.add(manuMaxReview6);
			maxAssignedManuscriptForSPC.add(manuMaxReview7);
			maxAssignedManuscriptForSPC.add(manuMaxReview8);
			maxAssignedManuscriptForSPC.add(manuMaxReview9);
			
			// init testUser5[jmoney@test.com] as subprogram chair and add to icmlConf
			SubprogramChair spcWithMaxReviews = new SubprogramChair(testUser5, maxAssignedManuscriptForSPC);
			icmlConf.addSubprogramChair(spcWithMaxReviews);
			
			// add testReviewer2[connor@test.com] as reviewer to icmlConf
			icmlConf.addReviewer(testReviewerForMaxReviews);
			
			// Add manuscripts to icmlConf
			try {
				icmlConf.addManuscript(manuMaxReview1);
				icmlConf.addManuscript(manuMaxReview2);
				icmlConf.addManuscript(manuMaxReview3);
				icmlConf.addManuscript(manuMaxReview4);
				icmlConf.addManuscript(manuMaxReview5);
				icmlConf.addManuscript(manuMaxReview6);
				icmlConf.addManuscript(manuMaxReview7);
				icmlConf.addManuscript(manuMaxReview8);
				icmlConf.addManuscript(manuMaxReview9);
			} catch (Exception e1) {
				System.out.println("failed to add manuscripts to icmlConf for max of 8 assigned manuscripts per reviewer");
				e1.printStackTrace();
			}
			
			/**
			 * END OF BUS RULE: A Reviewer can be assigned to review a maximum of 8 manuscripts for any conference.
			 * ===================================================================================================
			 */
						
			// Manuscripts for user with max # of manuscripts
			Manuscript maxManu1 = new Manuscript("Theory of Computing in Parallel", new File(""), testAuthWithMax);
			Manuscript maxManu2 = new Manuscript("Theory of Finite State Machines", new File(""), testAuthWithMax);
			Manuscript maxManu3 = new Manuscript("Study of Model View Controller Patterns", new File(""), testAuthWithMax);
			Manuscript maxManu4 = new Manuscript("Case Study on Electron as a GUI framework for Desktops", new File(""), testAuthWithMax);
			Manuscript maxManu5 = new Manuscript("Inner-Workings of Echo-Based Chat Client", new File(""), testAuthWithMax);
			try {
				manu1.addAuthor(testAuth2);
			} catch (AuthorExistsInListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				manu6.addAuthor(testAuth2);
			} catch (AuthorExistsInListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Add manuscripts to SubprogramChair 
			ArrayList<Manuscript> assignedManuscriptsSPC = new ArrayList<Manuscript>();
			assignedManuscriptsSPC.add(manu1);
			assignedManuscriptsSPC.add(manu2);
			assignedManuscriptsSPC.add(manu3);
			assignedManuscriptsSPC.add(manu4);
			SubprogramChair spc = new SubprogramChair(testUser4, assignedManuscriptsSPC);
			//add SubprogramChair to Conference with open deadline 
			acmConf.addSubprogramChair(spc);
			
			//Add reviewers to manu1
			manu1.addReviewer(testReviewer1);
			manu1.addReviewer(testReviewer2);
			manu1.addReviewer(testReviewer3);
			
			//Add reviews to manu1
			File testReview1 = new File("");
			File testReview2 = new File("");
			File testReview3 = new File("");
			manu1.addReview(testReview1);
			manu1.addReview(testReview2);
			manu1.addReview(testReview3);
			
			try {
				spc.assignManuscriptToReviewer(new Reviewer(testUser1), manu6);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			try {
				acmConf.addManuscript(manu1);
				acmConf.addManuscript(manu2);
				acmConf.addManuscript(manu3);
				acmConf.addManuscript(manu4);
				acmConf.addManuscript(manu5);
				acmConf.addManuscript(manu6);

				// add max manuscripts to max@email.com
				eccvConf.addManuscript(maxManu1);
				eccvConf.addManuscript(maxManu2);
				eccvConf.addManuscript(maxManu3);
				eccvConf.addManuscript(maxManu4);
				eccvConf.addManuscript(maxManu5);
			} catch(Exception theException) {
				theException.printStackTrace();
				System.out.println("Error in adding test Manuscripts");
			}
			
			Conference.writeConferences();
		} else {
			User.initializeUserListFromSerializableObject();
			Conference.initializeConferenceListFromSerializableObject();
		}
		systemController.startProgram();
	}

}
