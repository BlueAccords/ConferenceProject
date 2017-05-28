package client;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
			User testUser6 = new User("lilryerye@test.com");
			
			//Reviewer set up
			Reviewer testReviewer1 = new Reviewer(testUser1);
			Reviewer testReviewer2 = new Reviewer(testUser2);
			Reviewer testReviewer3 = new Reviewer(testUser3);
			Reviewer testReviewer4 = new Reviewer(testUser4);
			Reviewer testReviewer5 = new Reviewer(testUser5);
			Reviewer testReviewer6 = new Reviewer(testUser6);
			
			
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
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(new Date());
			tempCalendar.add(Calendar.DATE, 15); 
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
			Author testAuthWithMax = new Author(testUserWithMaxManuscripts);
			
			// Adding manuscripts to users.
			Manuscript manu1 = new Manuscript("Linear Logic", new File(""), testAuth1);
			Manuscript manu2 = new Manuscript("Quantum Crytography: Public Key Distribution and Coin Tossing", new File(""), testAuth1);
			Manuscript manu3 = new Manuscript("A Theory of Limited Automata", new File(""), testAuth2);
			Manuscript manu4 = new Manuscript("Simplified NP-Complete Problems", new File(""), testAuth2);
			Manuscript manu5 = new Manuscript("Theory of Genetic Algorithms", new File(""), testAuth2);
			Manuscript manu6 = new Manuscript("Theory of Cellular Automata: A survey", new File(""), testAuth3);
			Manuscript manu7 = new Manuscript("Ranking of Accessibility in Sorting Algorithms", new File(""), testAuth5);
			
			/* Temporarily removing redundant reviewer/reviews for manu1
			
			manu1.addReviewer(testReviewer1);
			manu1.addReviewer(testReviewer2);
			manu1.addReviewer(testReviewer3);
			manu1.addReview(new File(""));
			manu1.addReview(new File(""));
			manu1.addReview(new File(""));
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
