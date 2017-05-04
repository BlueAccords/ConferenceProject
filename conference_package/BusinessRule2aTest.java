package conference_package;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BusinessRule2aTest {

	private final static String PAPER_DEADLINE = "30-06-2017 11:59:59";
	private final static String CONFERENCE_NAME = "Test Conference";
	private final static String MANUSCRIPT_TITLE_1 = "Manuscript 1";
	private final static String MANUSCRIPT_TITLE_2 = "Manuscript 2";
	private final static String MANUSCRIPT_TITLE_3 = "Manuscript 3";
	private final static String MANUSCRIPT_TITLE_4 = "Manuscript 4";
	private final static String MANUSCRIPT_TITLE_5 = "Manuscript 5";
	private final static String MANUSCRIPT_TITLE_6 = "Manuscript 6";
	private final static String MANUSCRIPT_TITLE_7 = "Manuscript 7";
	private final static String MANUSCRIPT_TITLE_8 = "Manuscript 8";
	private final static String MANUSCRIPT_TITLE_9 = "Manuscript 9";
	private final static String MANUSCRIPT_CONTENTS = "This is my manuscript";
	
	private Date myTestDeadline;
	private Date myTestSubmission;
	private Conference myConference;
	private User myTestUser;
	private User myTestUser2;
	private User myTestUser3;
	private User myTestUser4;
	private User myTestUser5;
	private User myTestUser6;
	private Manuscript myTestManuscript;
	private Manuscript myTestManuscript2;
	private Manuscript myTestManuscript3;
	private Manuscript myTestManuscript4;
	private Manuscript myTestManuscript5;
	private SubprogramChair myTestSPC;
	private User myTestUserSPC;

	/**
	 * 
	 * @param thePaperDeadline
	 * @param thePaperSubmission
	 */
	private void setupConference() {

		myTestDeadline = new Date();
		
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
        	//Deadline
            myTestDeadline = dateformat.parse(PAPER_DEADLINE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		myConference = new Conference(CONFERENCE_NAME, myTestDeadline, myTestDeadline, 
									  myTestDeadline, myTestDeadline);
		
		myTestUser = new User("simpson@ieee.org");
		myTestUser2 = new User("robberjames@ieee.org");
		myTestUser3 = new User("benlee@ieee.org");
		myTestUser4 = new User("fleegle@ieee.org");
		myTestUser5 = new User("simpson@ieee.org");
		myTestUser6 = new User("barwood@ieee.org");

		myTestUserSPC = new User("ian@ieee.org");
		
		myTestSPC = new SubprogramChair();
	}
	
	private Manuscript createManuscriptHelper(final String theManuscriptTitle, 
			final String theManuscriptContents, final User theTestUser) {
		
		final Manuscript manuscript = new Manuscript("Test Paper", "This is a test.", theTestUser);
		
		return manuscript;
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
		
		setupConference();
		
		//Create manuscript and add co-authors
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, CONFERENCE_NAME, myTestUser2);
		myTestManuscript.addAuthor(myTestUser3);
		myTestManuscript.addAuthor(myTestUser4);
		
		//Add reviewer to conference
		myConference.addReviewers(myTestUser);
		
		assertFalse(myTestSPC.isAuthor(myTestUser, myTestManuscript));
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
}
