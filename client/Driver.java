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
import model.User;

public class Driver {

	public static void main(String[] args) {
		Controller systemController = new Controller();
		
		// add dummy users
		ArrayList<User> testUsers = new ArrayList<User>();

		User testUser1 = new User("john@email.com");
		User testUser2 = new User("connor@test.com");
		User testUser3 = new User("casanova@test.com");
		User testUser4 = new User("mdanger@test.com");
		User testUser5 = new User("jmoney@test.com");
		User testUser6 = new User("lilryerye@test.com");
		testUsers.add(testUser6);
		testUsers.add(testUser5);
		testUsers.add(testUser4);
		testUsers.add(testUser3);
		testUsers.add(testUser2);
		testUsers.add(testUser1);
		User.writeUsers(testUsers);
		
		// set submission date to current date + 15 days
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(new Date());
		tempCalendar.add(Calendar.DATE, 15); 
		Date submissionDate = tempCalendar.getTime();
		
		// add dummy conferences
		ArrayList<Conference> confList = new ArrayList<Conference>();
		Conference acmConf = new Conference("ACM Conference", submissionDate, new Date(), new Date(), new Date());
		Conference cvprConf = new Conference("CPVR - Conference on Computer Vision and Pattern Recognition", submissionDate, new Date(), new Date(), new Date());
		Conference chiConf = new Conference("CHI - Conference on Computer Human Interaction", submissionDate, new Date(), new Date(), new Date());
		Conference eccvConf = new Conference("EECV - European Conference on Computer Vision ", submissionDate, new Date(), new Date(), new Date());
		Conference icmlConf = new Conference("ICML - International Conference on Machine Learning", submissionDate, new Date(), new Date(), new Date());
		confList.add(acmConf);
		confList.add(cvprConf);
		confList.add(chiConf);
		confList.add(eccvConf);
		confList.add(icmlConf);
		Conference.writeConferences(confList);
	
		// test authors for manuscripts
		Author testAuth1 = new Author(testUser1);
		Author testAuth2 = new Author(testUser1);
		Author testAuth3 = new Author(testUser3);
		
		// Adding manuscripts to users.
		Manuscript manu1 = new Manuscript("Linear Logic", new File(""), testAuth1);
		Manuscript manu2 = new Manuscript("Quantum Crytography: Public Key Distribution and Coin Tossing", new File(""), testAuth1);
		Manuscript manu3 = new Manuscript("A Theory of Limited Automata", new File(""), testAuth1);
		Manuscript manu4 = new Manuscript("Simplified NP-Complete Problems", new File(""), testAuth1);
		Manuscript manu5 = new Manuscript("Theory of Genetic Algorithms", new File(""), testAuth2);
		Manuscript manu6 = new Manuscript("Theory of Cellular Automata: A survey", new File(""), testAuth3);
		
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
				
		systemController.startProgram();
	}

}
