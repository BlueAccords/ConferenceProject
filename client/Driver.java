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
			User testUser1 = new User("john@email.com");
			User testUser2 = new User("connor@test.com", true);
			User testUser3 = new User("casanova@test.com");
			User testUser4 = new User("mdanger@test.com");
			User testUser5 = new User("jmoney@test.com");
			User testUser6 = new User("lilryerye@test.com");
			User.addUser(testUser6);
			User.addUser(testUser5);
			User.addUser(testUser4);
			User.addUser(testUser3);
			User.addUser(testUser2);
			User.addUser(testUser1);
			User.writeUsers();
			
			// set submission date to current date + 15 days
			Calendar tempCalendar = Calendar.getInstance();
			tempCalendar.setTime(new Date());
			tempCalendar.add(Calendar.DATE, 15); 
			Date submissionDate = tempCalendar.getTime();
			
			// add dummy conferences
			Conference.initializeConferenceListToEmptyList();
			
			Conference acmConf = new Conference("ACM Conference", submissionDate, new Date(), new Date(), new Date());
			Conference cvprConf = new Conference("CPVR - Conference on Computer Vision and Pattern Recognition", submissionDate, new Date(), new Date(), new Date());
			cvprConf.addSubprogramChair(testUser2);
			Conference chiConf = new Conference("CHI - Conference on Computer Human Interaction", submissionDate, new Date(), new Date(), new Date());
			Conference eccvConf = new Conference("EECV - European Conference on Computer Vision ", submissionDate, new Date(), new Date(), new Date());
			Conference icmlConf = new Conference("ICML - International Conference on Machine Learning", submissionDate, new Date(), new Date(), new Date());
			Conference.addConference(acmConf);
			Conference.addConference(cvprConf);
			Conference.addConference(chiConf);
			Conference.addConference(eccvConf);
			Conference.addConference(icmlConf);
		
			// test authors for manuscripts
			Author testAuth1 = new Author(testUser1);
			Author testAuth2 = new Author(testUser2);
			Author testAuth3 = new Author(testUser3);
			
			// Adding manuscripts to users.
			Manuscript manu1 = new Manuscript("Linear Logic", new File(""), testAuth1);
			Manuscript manu2 = new Manuscript("Quantum Crytography: Public Key Distribution and Coin Tossing", new File(""), testAuth1);
			Manuscript manu3 = new Manuscript("A Theory of Limited Automata", new File(""), testAuth2);
			Manuscript manu4 = new Manuscript("Simplified NP-Complete Problems", new File(""), testAuth2);
			Manuscript manu5 = new Manuscript("Theory of Genetic Algorithms", new File(""), testAuth2);
			Manuscript manu6 = new Manuscript("Theory of Cellular Automata: A survey", new File(""), testAuth3);
			
			
			//Add manuscripts to SubprogramChair
			ArrayList<Manuscript> assignedManuscriptsSPC = new ArrayList<Manuscript>();
			assignedManuscriptsSPC.add(manu1);
			assignedManuscriptsSPC.add(manu2);
			assignedManuscriptsSPC.add(manu3);
			assignedManuscriptsSPC.add(manu4);
			acmConf.addSubprogramChair(testUser4);
			SubprogramChair spc = new SubprogramChair(testUser4, assignedManuscriptsSPC);
			acmConf.addSubprogramChair(spc);
			
			
			
			try {
				acmConf.addManuscript(manu1);
				acmConf.addManuscript(manu2);
				acmConf.addManuscript(manu3);
				acmConf.addManuscript(manu4);
				acmConf.addManuscript(manu5);
				//acmConf.addManuscript(manu6);
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
