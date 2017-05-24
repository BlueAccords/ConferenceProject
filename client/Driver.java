package client;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import model.Conference;
import model.User;

public class Driver {

	public static void main(String[] args) {
		Controller systemController = new Controller();
		/*User testUser1 = new User("john@email.com");
		User testUser2 = new User("connor@test.com");
		User testUser3 = new User("casanova@test.com");
		User testUser4 = new User("mdanger@test.com");
		User testUser5 = new User("jmoney@test.com");
		User testUser6 = new User("lilryerye@test.com");
		ArrayList<User> testUsers = new ArrayList<User>();
		testUsers.add(testUser6);
		testUsers.add(testUser5);
		testUsers.add(testUser4);
		testUsers.add(testUser3);
		testUsers.add(testUser2);
		testUsers.add(testUser1);
		User.writeUsers(testUsers);
		
		ArrayList<Conference> exampleList = new ArrayList<Conference>();
		exampleList.add(new Conference("ACM Conference", new Date(), new Date(), new Date(), new Date()));
		Conference.writeConferences(exampleList);*/
	
		
		systemController.startProgram();
	}

}
