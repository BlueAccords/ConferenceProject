package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

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
    Manuscript aManuscript;
    Author authorBob;
	Reviewer reviewerBob;
	Author authorJohn;
	Manuscript johns;

	User bob;
	User john;

    @Before
    public void setUp() throws Exception {
    	Calendar future = Calendar.getInstance();
    	future.add(Calendar.HOUR, 5000);

        RSAConference = new Conference("RSA", future.getTime(), future.getTime(), future.getTime(), future.getTime());

        john = new User("John@john.com", false);
        authorJohn = new Author(john);
        johns = new Manuscript("Johns awesome paper", new File("Author.java"), authorJohn);
        TesselationsConference = new Conference("Tesselations", new Date(), new Date(), new Date(), new Date());
        conferenceList = Conference.getConferences();
        authorBob  = new Author("Josh", "Smith");
        bob = new User("Bob@bob.com", false);
        authorBob = new Author(bob);
        reviewerBob = new Reviewer(bob);
        aManuscript = new Manuscript("Conference test.java manuscript", new File("ConferenceTest.java"), authorBob);
    }


	@Test
	public void addManuscript() throws Exception {
    	TesselationsConference.addManuscript(aManuscript);
    	assertTrue(TesselationsConference.getManuscripts().contains(aManuscript));
	}

	@Test
	public void removeManuscript() throws Exception {
    	RSAConference.addManuscript(aManuscript);
    	RSAConference.removeManuscript(aManuscript);
    	assertFalse(RSAConference.getManuscripts().contains(aManuscript));
	}

	@Test
	public void addReviewer() throws Exception {
    	TesselationsConference.addReviewer(reviewerBob);
    	System.out.println(TesselationsConference.getEligibleReviewers(aManuscript).size());
    	assertTrue(TesselationsConference.getEligibleReviewers(aManuscript).contains(reviewerBob));
	}

	@Test
	public void isUserAuthor() throws Exception {
		assertFalse(RSAConference.isUserAuthor(bob));
	}

	@Test
	public void isUserAuthor_yes() throws Exception {
    	RSAConference.addManuscript(johns);
		assertTrue(RSAConference.isUserAuthor(john));
	}

	@Test
	public void isUserReviewer() throws Exception {
		assertFalse(RSAConference.isUserReviewer(bob));
	}

	@Test
	public void isUserReviewer_yes() throws Exception {
    	RSAConference.addReviewer(reviewerBob);
		assertTrue(RSAConference.isUserReviewer(bob));
	}


	@Test
	public void getManuscriptsBelongingToAuthor() throws Exception {
		TesselationsConference.addManuscript(johns);
		List<Manuscript> submitted = TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn);
		assertTrue(submitted.size() == 1);
		assertTrue(submitted.contains(johns));
    }

	@Test
	public void addConference() throws Exception {
    	Conference.addConference(RSAConference);
    	assertTrue(Conference.getConferences().contains(RSAConference));
	}

	@Test
	public void updateConferenceInList() throws Exception {
    	Conference.addConference(RSAConference);
    	RSAConference.addManuscript(aManuscript);
    	Conference.updateConferenceInList(RSAConference);
    	assertTrue(Conference.getConferences().get(0).getManuscripts().contains(aManuscript));
	}

	@Test
	public void initializeConferenceListToEmptyList() throws Exception {
    	RSAConference.initializeConferenceListToEmptyList();
		assertTrue(RSAConference.getConferences().isEmpty());
	}



	@Test
	public void getEligibleReviewers() throws Exception {

	}



	@Test
	public void isValidNumberOfSubmissions() throws Exception {
	}

}
