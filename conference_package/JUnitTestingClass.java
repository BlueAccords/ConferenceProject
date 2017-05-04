package conference_package;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests all business rules.
 * @author Ian Waak, Ayub Tiba, Vincent Povio, Vinh Le, James Roberts
 * @version 4/30/2017
 */
public class JUnitTestingClass {
	
	private final static String PAPER_DEADLINE = "30-06-2017 11:59:59";
	private final static String PAPER_SUBMISSION_ON_TIME = "29-06-2017 11:59:59";
	private final static String PAPER_SUBMISSION_JUST_IN_TIME = "30-06-2017 11:59:59";
	private final static String PAPER_SUBMISSION_NOT_ON_TIME = "01-08-2017 12:00:00";
	private final static String PAPER_SUBMISSION_NOT_ON_TIME_BY_ONE_MINUTE = "01-07-2017 12:00:00";
	private final static String CONFERENCE_NAME = "Test Conference";
	
	private Date myTestDeadline;
	private Date myTestSubmission;
	private Conference myConference;
	private User myTestUser;
	private Manuscript myTestManuscript;

	/**
	 * 
	 * @param thePaperDeadline
	 * @param thePaperSubmission
	 */
	private void setupConference(String thePaperSubmission) {

		myTestDeadline = new Date();
        myTestSubmission = new Date();
        
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
        	//Deadline
            myTestDeadline = dateformat.parse(PAPER_DEADLINE);
            
            //Actual submission date
            myTestSubmission = dateformat.parse(thePaperSubmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		myConference = new Conference(CONFERENCE_NAME, myTestDeadline, myTestDeadline, myTestDeadline, myTestDeadline);
		
		myTestUser = new User("simpson@ieee.org");
		myTestManuscript = new Manuscript("Test Paper", "This is a test.", myTestUser);
		myTestManuscript.setSubmissionDate(myTestSubmission);
	}
	
	/**
	 * Test for submitting a paper on time.
	 * @author Ayub Tiba
	 * @version 4/30/2017
	 * @version 5/03/2017
	 */
	@Test
	public void testSubmissionDeadlineOnTime() {
		setupConference(PAPER_SUBMISSION_ON_TIME);
		assertTrue(myConference.isSubmittedOnTime(myTestManuscript));
	}
	
	/**
	 * Test for submitting a paper just in time.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineJustInTime() {
        setupConference(PAPER_SUBMISSION_JUST_IN_TIME);
		assertTrue(myConference.isSubmittedOnTime(myTestManuscript));
	}
	
	/**
	 * Test for submitting a paper passed the deadline.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineNotOnTime() {
        setupConference(PAPER_SUBMISSION_NOT_ON_TIME);
		assertFalse(myConference.isSubmittedOnTime(myTestManuscript));
	}
	
	/**
	 * Test for submitting a paper when deadline missed by one minute.
	 * Test should assert false.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineJustMissed() {
        setupConference(PAPER_SUBMISSION_NOT_ON_TIME_BY_ONE_MINUTE);
		assertFalse(myConference.isSubmittedOnTime(myTestManuscript));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Two papers submitted as an author and two submitted as a co-author.
	 * @author Vincent Povio
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testValidNumberOfPapers() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		
		final User testUser = new User("simpson@ieee.org");
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", testUser);
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");

		conf.addManuscript(testPaper);
		conf.addManuscript(testPaper2);
		conf.addManuscript(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Four papers submitted as a co-author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testValidNumberOfPapersCoAuthor() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "greg@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "joey@ieee.org");
		testPaper2.addAuthor("simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");

		conf.addManuscript(testPaper);
		//conf.addManuscript(testPaper2);
		//conf.addManuscript(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Four papers submitted as a author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testValidNumberOfPapersAuthor() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "simpson@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "simpson@ieee.org");
		
		conf.addManuscript(testPaper);
		conf.addManuscript(testPaper2);
		conf.addManuscript(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Five papers submitted as a author. Final submission should fail.
	 * @author Ian Waak
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testInvalidNumberOfPapersAuthor() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "simpson@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "simpson@ieee.org");
		final Manuscript testPaper5 = new Manuscript("Test Paper 5", "This is a fifth test", "simpson@ieee.org");
		final Manuscript testPaper6 = new Manuscript("Test Paper 6", "This is a sixth test", "simpson@ieee.org");
		conf.addManuscript(testPaper);
		conf.addManuscript(testPaper2);
		conf.addManuscript(testPaper3);
		conf.addManuscript(testPaper4);
		conf.addManuscript(testPaper5);
		//Test for valid submissions for each paper.
		assertFalse(conf.isValidNumberOfSubmissions(testPaper6));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Five papers submitted as a co-author. Final submission should fail.
	 * @author Ian Waak
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testInvalidNumberOfPapersCoAuthor() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "greg@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "joey@ieee.org");
		testPaper2.addAuthor("simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");
		final Manuscript testPaper5 = new Manuscript("Test Paper 5", "This is a fifth test", "hoover@ieee.org");
		testPaper5.addAuthor("simpson@ieee.org");
		final Manuscript testPaper6 = new Manuscript("Test Paper 6", "This is a sixth test", "hoover@ieee.org");
		testPaper6.addAuthor("simpson@ieee.org");

		conf.addManuscript(testPaper);
		conf.addManuscript(testPaper2);
		conf.addManuscript(testPaper3);
		conf.addManuscript(testPaper4);
		conf.addManuscript(testPaper5);
		assertFalse(conf.isValidNumberOfSubmissions(testPaper6));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Two papers submitted as an author, three papers submitted as a co-author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 * @throws Exception 
	 */
	@Test
	public void testInvalidNumberOfPapers() throws Exception {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "simpson@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");
		final Manuscript testPaper5 = new Manuscript("Test Paper 5", "This is a fifth test", "hoover@ieee.org");
		testPaper5.addAuthor("simpson@ieee.org");
		final Manuscript testPaper6 = new Manuscript("Test Paper 6", "This is a sixth test", "jover@ieee.org");
		testPaper6.addAuthor("simpson@ieee.org");
		conf.addManuscript(testPaper);
		conf.addManuscript(testPaper2);
		conf.addManuscript(testPaper3);
		conf.addManuscript(testPaper4);
		conf.addManuscript(testPaper5);
		assertFalse(conf.isValidNumberOfSubmissions(testPaper6));
	}
	
	/**
	 * Test to add reviewer who is not an author or co-author.
	 * Test asserts false to indicate reviewer is not an author or co-author
	 * and may be assigned to the paper for review.
	 * @author Vinh Le
	 * @version 4/0/2017
	 */
	@Test
	public void testReviewerIsNotAuthorOrCoauthor() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "hardy@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("barwood@ieee.org");
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		//conf.addReviewer(testUser);
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		assertFalse(testSPC.isAuthor(testRev, testPaper));
	}
	
	/**
	 * Test to add reviewer who is author. Test asserts true to indicate reviewer is a author
	 * and therefore cannot be assigned to the paper for review.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerIsAuthor() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "simpson@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("barwood@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		//conf.addReviewer(testUser);
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		
		assertTrue(testSPC.isAuthor(testRev, testPaper));
	}
	
	/**
	 * Test to add reviewer who is a co-author. Test asserts true to indicate reviewer is a co-author
	 * and therefore cannot be assigned to the paper for review.
	 * @author James Roberts
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerIsCoauthor() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "hardy@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		//conf.addReviewer(testUser);
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		
		assertTrue(testSPC.isAuthor(testRev, testPaper));
	}
	
	/**
	 * Tests whether the reviewer has less than the max number of papers allowed assigned to them.
	 * Asserts true if reviewer is assigned less than the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerAssignedFewerThanMaxPapers() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "hardy@ieee.org");
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		
		testRev.addManuscriptToReviewer(testPaper);
		
		assertTrue(testSPC.isUnderAssignedManuscriptLimit(testRev));
	}

	/**
	 * Tests whether the reviewer has one less than the max number of papers allowed assigned to them.
	 * Asserts true if reviewer is assigned one less than the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerAssignedMaxMinusOnePapers() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "hardy@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "maxim@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "james@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "vinh@ieee.org");
		final Manuscript testPaper5 = new Manuscript("Test Paper 5", "This is a fifth test", "ian@ieee.org");
		final Manuscript testPaper6 = new Manuscript("Test Paper 6", "This is another test.", "vincent@ieee.org");
		final Manuscript testPaper7 = new Manuscript("Test Paper 7", "This is a third test.", "ayub@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		
		
		
		testRev.addManuscriptToReviewer(testPaper);
		testRev.addManuscriptToReviewer(testPaper2);
		testRev.addManuscriptToReviewer(testPaper3);
		testRev.addManuscriptToReviewer(testPaper4);
		testRev.addManuscriptToReviewer(testPaper5);
		testRev.addManuscriptToReviewer(testPaper6);
		testRev.addManuscriptToReviewer(testPaper7);
		
		assertTrue(testSPC.isUnderAssignedManuscriptLimit(testRev));
	}
	
	/**
	 * Tests whether the reviewer has the max number of papers allowed assigned to them.
	 * Asserts false if reviewer has been assigned the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	
	@Test
	public void testReviewerAssignedMaxPapers() {
		Date testDate = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";

        try {
        	//Deadline
            testDate = dateformat.parse(paperDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
		final Conference conf = new Conference("Test Conference", testDate, testDate, testDate, testDate);
		
		final Manuscript testPaper = new Manuscript("Test Paper", "This is a test.", "hardy@ieee.org");
		final Manuscript testPaper2 = new Manuscript("Test Paper 2", "This is another test.", "maxim@ieee.org");
		final Manuscript testPaper3 = new Manuscript("Test Paper 3", "This is a third test.", "james@ieee.org");
		final Manuscript testPaper4 = new Manuscript("Test Paper 4", "This is a fourth test.", "vinh@ieee.org");
		final Manuscript testPaper5 = new Manuscript("Test Paper 5", "This is a fifth test", "ian@ieee.org");
		final Manuscript testPaper6 = new Manuscript("Test Paper 6", "This is a sixth test.", "vincent@ieee.org");
		final Manuscript testPaper7 = new Manuscript("Test Paper 7", "This is a seventh test.", "ayub@ieee.org");
		final Manuscript testPaper8 = new Manuscript("Test Paper 8", "This is an eigth test.", "ian@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		final Reviewer testRev = new Reviewer(testUser);
		
		final User testUser2 = new User("bobbarker@ieee.org");
		final SubprogramChair testSPC = new SubprogramChair(testUser2);
		
		
		
		testRev.addManuscriptToReviewer(testPaper);
		testRev.addManuscriptToReviewer(testPaper2);
		testRev.addManuscriptToReviewer(testPaper3);
		testRev.addManuscriptToReviewer(testPaper4);
		testRev.addManuscriptToReviewer(testPaper5);
		testRev.addManuscriptToReviewer(testPaper6);
		testRev.addManuscriptToReviewer(testPaper7);
		testRev.addManuscriptToReviewer(testPaper8);
		assertFalse(testSPC.isUnderAssignedManuscriptLimit(testRev));
	}
}