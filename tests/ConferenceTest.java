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
import utility.TestDataGenerator;

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
    Conference IEEEConference;
    Manuscript aManuscript;
    Author authorBob;
	Reviewer reviewerBob;
	Author authorJohn;
	Manuscript johns;

	Reviewer reviewerJohn;
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
        
        // Manuscript submission deadline is set in the future
        TesselationsConference = new Conference("Tesselations",
        		TestDataGenerator.generateRandomDateBefore(new Date(), false),
        		new Date(),
        		new Date(),
        		new Date());
        
        // IEEE Conference with submissoin deadline set in the past
        IEEEConference = new Conference("IEEE Conference",
        		TestDataGenerator.generateRandomDateBefore(new Date(), true),
        		new Date(),
        		new Date(),
        		new Date());

        conferenceList = Conference.getConferences();
        authorBob  = new Author("Josh", "Smith");
        bob = new User("Bob@bob.com", false);
        authorBob = new Author(bob);
        reviewerBob = new Reviewer(bob);
        reviewerJohn = new Reviewer(john);
        aManuscript = new Manuscript("Conference test.java manuscript", new File("ConferenceTest.java"), authorBob);
    }


	@Test
	public void addManuscript() throws Exception {
    	TesselationsConference.addManuscript(aManuscript);
    	assertTrue(TesselationsConference.getManuscripts().contains(aManuscript));
	}
	
	/**
	 * Tests for 
	 * User Story: As an Author I want ot submit a manuscript to a conference
	 * Business rule 3a:
	 * 	All manuscript submissions must be made on or before the submission deadline before midnight UTC-12
	 * @throws Exception 
	 */

	@Test
	public void addManuscript_submissionBeforeConferenceDeadline_shouldSucceed() throws Exception {
		String manuTitle = "Studies in visualizing algorithms";
		Manuscript validManu = new Manuscript(
				manuTitle,
				new File(""),
				authorJohn,
				TestDataGenerator.generateRandomDateBefore(TesselationsConference.getManuscriptDeadline(), true)
				);

		TesselationsConference.addManuscript(validManu);
		assertEquals(TesselationsConference.getManuscripts().get(0).getTitle(), manuTitle);
	}
	
	@Test
	public void addManuscript_submissionRightBeforeConferenceDeadline_shouldSucceed() throws Exception {
		// set up date to be 1 second before conference deadline
		Calendar tempCal = Calendar.getInstance();
		Date conferenceDeadline = TesselationsConference.getManuscriptDeadline();

		tempCal.setTime(conferenceDeadline);
		tempCal.add(Calendar.SECOND, -1);
		Date dateOneSecondBeforeDeadline = tempCal.getTime();
		

		Manuscript validManu = new Manuscript(
				"Studies in Algorithms",
				new File(""),
				authorJohn,
				dateOneSecondBeforeDeadline
				);
		assertTrue(validManu.getSubmissionDate().before(conferenceDeadline));
		TesselationsConference.addManuscript(validManu);
	}
	
	@Test (expected = Exception.class)
	public void addManuscript_submissionAfterConferenceDeadline_shouldFail() throws Exception {
		Manuscript validManu = new Manuscript(
				"Studies in Algorithms",
				new File(""),
				authorJohn,
				new Date()
				);

		assertTrue(validManu.getSubmissionDate().after(IEEEConference.getManuscriptDeadline()));
		IEEEConference.addManuscript(validManu);
	}
	
	@Test (expected = Exception.class)
	public void addManuscript_submissionRightAfterConferenceDeadline_shouldFail() throws Exception {
		// set up date to be 1 second after conference deadline
		Calendar tempCal = Calendar.getInstance();
		Date conferenceDeadline = TesselationsConference.getManuscriptDeadline();

		tempCal.setTime(conferenceDeadline);
		tempCal.add(Calendar.SECOND, 1);
		Date dateOneSecondAfterDeadline = tempCal.getTime();
		

		Manuscript validManu = new Manuscript(
				"Studies in Algorithms",
				new File(""),
				authorJohn,
				dateOneSecondAfterDeadline
				);
		assertTrue(validManu.getSubmissionDate().after(conferenceDeadline));
		TesselationsConference.addManuscript(validManu);

	}
	
	@Test
	public void removeManuscript() throws Exception {
    	RSAConference.addManuscript(aManuscript);
    	RSAConference.removeManuscript(aManuscript);
    	assertFalse(RSAConference.getManuscripts().contains(aManuscript));
	}

	@Test
	public void addReviewer() throws Exception {
    	TesselationsConference.addReviewer(reviewerJohn);
    	System.out.println(TesselationsConference.getEligibleReviewers(aManuscript).size());
    	assertTrue(TesselationsConference.getEligibleReviewers(aManuscript).contains(reviewerJohn));
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
