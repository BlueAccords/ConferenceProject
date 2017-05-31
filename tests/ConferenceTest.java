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
	Author authorKevin;

	Reviewer reviewerJohn;
	User bob;
	User john;
	User kevin;

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
        
        // IEEE Conference with submission deadline set in the past
        IEEEConference = new Conference("IEEE Conference",
        		TestDataGenerator.generateRandomDateBefore(new Date(), true),
        		new Date(),
        		new Date(),
        		new Date());

        conferenceList = Conference.getConferences();
        bob = new User("Bob@bob.com", false);
        authorBob = new Author(bob);
        kevin = new User("kevin@gmail.com");
        authorKevin = new Author(kevin);
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
		String manuTitle = "Studies in Algorithms";
		// set up date to be 1 second before conference deadline
		Calendar tempCal = Calendar.getInstance();
		Date conferenceDeadline = TesselationsConference.getManuscriptDeadline();

		tempCal.setTime(conferenceDeadline);
		tempCal.add(Calendar.SECOND, -1);
		Date dateOneSecondBeforeDeadline = tempCal.getTime();
		

		Manuscript validManu = new Manuscript(
				manuTitle,
				new File(""),
				authorJohn,
				dateOneSecondBeforeDeadline
				);
		TesselationsConference.addManuscript(validManu);

		assertTrue(validManu.getSubmissionDate().before(conferenceDeadline));
		assertEquals(TesselationsConference.getManuscripts().get(0).getTitle(), manuTitle);
	}
	
	@Test (expected = Exception.class)
	public void addManuscript_submissionAfterConferenceDeadline_shouldThrowException() throws Exception {
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
	public void addManuscript_submissionRightAfterConferenceDeadline_shouldThrowException() throws Exception {
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
	
	/**
	 * Tests for 
	 * User Story: As an Author I want ot submit a manuscript to a conference
	 * Business rule 3b:
	 * 	An author is limited to 5 manuscript submissions as an author or co-author per conference.
	 * @throws Exception 
	 */

	@Test
	public void addManuscript_whereAuthorHasLessThanMaxManuscriptsSubmittedAsAuthorAndCoAuthor_shouldAllowSubmission() throws Exception {
		// add 2 manuscripts with john as author
		for(int i = 0; i < 2; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorJohn,
				new Date()
				);
			TesselationsConference.addManuscript(validManu);
		}

		// add 2 manuscripts with john as co-author
		ArrayList<Author> coAuthorsList = new ArrayList<Author>();
		coAuthorsList.add(authorJohn);
		for(int i = 0; i < 2; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms and Academics-" + i,
				new File(""),
				authorBob,
				new Date(),
				coAuthorsList);
			TesselationsConference.addManuscript(validManu);
		}	

		// submit 5th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);

		TesselationsConference.addManuscript(fifthManuscript);
		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
	}

	@Test
	public void addManuscript_whereAuthorHasLessThanMaxManuscriptsSubmittedAsCoAuthor_shouldAllowSubmission() throws Exception {
		ArrayList<Author> coAuthorsList = new ArrayList<Author>();
		coAuthorsList.add(authorJohn);

		// add 4 manuscripts with john as co-author
		for(int i = 0; i < 4; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorBob,
				new Date(),
				coAuthorsList);
			TesselationsConference.addManuscript(validManu);
		}

		// submit 5th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);

		TesselationsConference.addManuscript(fifthManuscript);
		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
	}

	@Test
	public void addManuscript_whereAuthorHasLessThanMaxManuscriptsSubmittedAsAuthor_shouldAllowSubmission() throws Exception {
		// add 4 manuscripts with john as author
		for(int i = 0; i < 4; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorJohn,
				new Date()
				);
			TesselationsConference.addManuscript(validManu);
		}

		// submit 5th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);

		TesselationsConference.addManuscript(fifthManuscript);
		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
	}
	
	
	@Test (expected = Exception.class)
	public void addManuscript_whereAuthorHasMaxManuscriptsSubmittedAsCoAuthor_shouldThrowException() throws Exception {
		ArrayList<Author> coAuthorsList = new ArrayList<Author>();
		coAuthorsList.add(authorJohn);

		// add 5 manuscripts with john as co-author
		for(int i = 0; i < 5; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorBob,
				new Date(),
				coAuthorsList);
			TesselationsConference.addManuscript(validManu);
		}

		// submit 6th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);
		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
		TesselationsConference.addManuscript(fifthManuscript);
	}
	
	
	
	@Test (expected = Exception.class)
	public void addManuscript_whereAuthorHasMaxManuscriptsSubmittedAsAuthor_shouldThrowException() throws Exception {
		// add 5 manuscripts with john as author
		for(int i = 0; i < 5; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorJohn,
				new Date()
				);
			TesselationsConference.addManuscript(validManu);
		}

		// submit 6th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);
		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
		TesselationsConference.addManuscript(fifthManuscript);
	}
	
	

	@Test (expected = Exception.class)
	public void addManuscript_whereAuthorHasMaxManuscriptsSubmittedAsAuthorAndCoAuthor_shouldThrowException() throws Exception {
		
		// add 2 manuscripts with john as author
		for(int i = 0; i < 2; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms-" + i,
				new File(""),
				authorJohn,
				new Date()
				);
			TesselationsConference.addManuscript(validManu);
		}

		// add 3 manuscripts with john as co-author
		ArrayList<Author> coAuthorsList = new ArrayList<Author>();
		coAuthorsList.add(authorJohn);
		for(int i = 0; i < 3; i++) {
			Manuscript validManu = new Manuscript(
				"Studies in Algorithms and Academics-" + i,
				new File(""),
				authorBob,
				new Date(),
				coAuthorsList);
			TesselationsConference.addManuscript(validManu);
		}	

		// submit 6th manuscript
		Manuscript fifthManuscript = new Manuscript(
				"Big O Complexity and real-time benefits",
				new File(""),
				authorJohn,
				new Date()
				);

		assertEquals(TesselationsConference.getManuscripts().size(), 5);
		assertEquals(TesselationsConference.getManuscriptsBelongingToAuthor(authorJohn).size(), 5);
		TesselationsConference.addManuscript(fifthManuscript);
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
