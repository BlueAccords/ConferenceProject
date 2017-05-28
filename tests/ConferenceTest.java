package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import model.Conference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Controller;
import model.Author;
import model.Manuscript;
import model.Manuscript.AuthorExistsInListException;
import model.Reviewer;
import model.User;

/**
 * Tests for the Controller class. This will encompass the different business rules
 * that the Controller handles, along with just general testing for state switching.
 *
 * @author Connor Lundberg
 * @version 5/15/2017
 */
public class ConferenceTest {

    ArrayList<Conference> conferenceList;
    Conference RSAConference;
    Conference TesselationsConference;


    @Before
    public void setUp() throws Exception {
        RSAConference = new Conference("RSA", new Date(), new Date(), new Date(), new Date());
        TesselationsConference = new Conference("Tesselations", new Date(), new Date(), new Date(), new Date());
        conferenceList = Conference.getConferences();
    }


    @Test
    public void readConferences_newConference_true(){

    }

    @Test
    public void readConferences_noNewConference_true(){

    }


    @Test
    public void writeConferences_newConference_true(){

    }

    @Test
    public void writeConferences_noNewConference_true(){

    }
    
    
    @Test
    public void getEligibleReviewers_TestThatListIsSmallerThanAllReviewersList_ReturnsEligibleReviewers () {
    	User mainTestUser = new User("connor@gmail.com");
    	Author mainTestAuthor = new Author(mainTestUser);
    	Manuscript testManuscript = new Manuscript ("Test manuscript", new File(""), mainTestAuthor);
    	
    	User testUser1 = new User("jCricket@test.com");
    	User testUser2 = new User("evilKineval@test.com");
    	User testUser3 = new User("gLucas@sosomovies.net");
    	User testUser4 = new User("almostDone@test.com");
    	Author testAuthor1 = new Author(testUser1);
    	Author testAuthor2 = new Author(testUser2);
    	Reviewer testReviewer1 = new Reviewer(testUser3); //good
    	Reviewer testReviewer2 = new Reviewer(testUser4); //good
    	Reviewer testReviewer3 = new Reviewer(testUser2);
    	Reviewer testReviewer4 = new Reviewer(testUser1);
    	RSAConference.addReviewer(testReviewer4);
    	RSAConference.addReviewer(testReviewer3);
    	RSAConference.addReviewer(testReviewer2);
    	RSAConference.addReviewer(testReviewer1);
    	try {
			testManuscript.addAuthor(testAuthor1);
		} catch (AuthorExistsInListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			testManuscript.addAuthor(testAuthor2);
		} catch (AuthorExistsInListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ArrayList<Reviewer> eligibleReviewers = RSAConference.getEligibleReviewers(testManuscript);
    	assertTrue("The list of eligible reviewers is not a size of 2: " + eligibleReviewers.size(), eligibleReviewers.size() == 2);
    	assertTrue("The list of eligible reviewers does not contain gLucas@sosomovies.net", eligibleReviewers.contains(testReviewer1));
    	assertTrue("The list of eligible reviewers does not contain almostDone@test.com", eligibleReviewers.contains(testReviewer2));
    }
}
