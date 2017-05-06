package conference_package;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class BusinessRule2bTest {

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
	
	private Date myTestDeadline;
	private Conference myConference;
	private User myTestUser1;
	private User myTestUser2;
	private User myTestUser3;
	private User myTestUser4;
	private User myTestUser5;
	private User myTestUser6;
	private User myTestUser7;
	private User myTestUser8;
	private Manuscript myTestManuscript;
	private Manuscript myTestManuscript2;
	private Manuscript myTestManuscript3;
	private Manuscript myTestManuscript4;
	private Manuscript myTestManuscript5;
	private Manuscript myTestManuscript6;
	private Manuscript myTestManuscript7;
	private Manuscript myTestManuscript8;
	private SubprogramChair myTestSPC;
	private User myTestUserSPC;
	private Reviewer myTestReviewer;
	private User myTestUserReviewer;

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
		
		myTestUser1 = new User("simpson@ieee.org");
		myTestUser2 = new User("robberjames@ieee.org");
		myTestUser3 = new User("benlee@ieee.org");
		myTestUser4 = new User("fleegle@ieee.org");
		myTestUser5 = new User("vinh@ieee.org");
		myTestUser6 = new User("vincent@ieee.org");
		myTestUser7 = new User("ayub@ieee.org");
		myTestUser8 = new User("luffy@ieee.org");

		myTestUserReviewer = new User("barwood@ieee.org");		
		myTestReviewer = new Reviewer(myTestUserReviewer);
		
		myTestUserSPC = new User("ian@ieee.org");	
		myTestSPC = new SubprogramChair(myTestUserSPC);
				
	}
	
	private Manuscript createManuscriptHelper(final String theManuscriptTitle, 
			final String theManuscriptContents, final User theTestUser) {
		
		final Manuscript manuscript = new Manuscript("Test Paper", "This is a test.", theTestUser);
		
		return manuscript;
	}
	/**
	 * Tests whether the reviewer has less than the max number of papers allowed assigned to them.
	 * Asserts true if reviewer is assigned less than the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerAssignedFewerThanMaxPapers() {
		
		setupConference();
		
		//Create manuscript and add co-authors
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, CONFERENCE_NAME, myTestUser1);
			
		//Assign manuscript to a reviewer.
		myTestReviewer.addManuscriptToReviewer(myTestManuscript);
		
		assertTrue(myTestSPC.isUnderAssignedManuscriptLimit(myTestReviewer));
	}
	
	/**
	 * Tests whether the reviewer has one less than the max number of papers allowed assigned to them.
	 * Asserts true if reviewer is assigned one less than the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerAssignedMaxMinusOnePapers() {
        setupConference();
        
        //Create manuscript and add co-authors
        myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, CONFERENCE_NAME, myTestUser1);
        myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, CONFERENCE_NAME, myTestUser2);
        myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, CONFERENCE_NAME, myTestUser3);
        myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, CONFERENCE_NAME, myTestUser4);
        myTestManuscript5 = createManuscriptHelper(MANUSCRIPT_TITLE_5, CONFERENCE_NAME, myTestUser5);
        myTestManuscript6 = createManuscriptHelper(MANUSCRIPT_TITLE_6, CONFERENCE_NAME, myTestUser6);
        myTestManuscript7 = createManuscriptHelper(MANUSCRIPT_TITLE_7, CONFERENCE_NAME, myTestUser7);
        
        //Assign manuscript to a reviewer.
        myTestReviewer.addManuscriptToReviewer(myTestManuscript);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript2);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript3);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript4);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript5);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript6);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript7);
		
		assertTrue(myTestSPC.isUnderAssignedManuscriptLimit(myTestReviewer));
	}
	
	/**
	 * Tests whether the reviewer has the max number of papers allowed assigned to them.
	 * Asserts false if reviewer has been assigned the maximum number of papers.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testReviewerAssignedMaxPapers() {      
        setupConference();
        
        //Create manuscript and add co-authors
        myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, CONFERENCE_NAME, myTestUser1);
        myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, CONFERENCE_NAME, myTestUser2);
        myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, CONFERENCE_NAME, myTestUser3);
        myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, CONFERENCE_NAME, myTestUser4);
        myTestManuscript5 = createManuscriptHelper(MANUSCRIPT_TITLE_5, CONFERENCE_NAME, myTestUser5);
        myTestManuscript6 = createManuscriptHelper(MANUSCRIPT_TITLE_6, CONFERENCE_NAME, myTestUser6);
        myTestManuscript7 = createManuscriptHelper(MANUSCRIPT_TITLE_7, CONFERENCE_NAME, myTestUser7);
        myTestManuscript8 = createManuscriptHelper(MANUSCRIPT_TITLE_8, CONFERENCE_NAME, myTestUser8);
        
        //Assign manuscript to a reviewer.
        myTestReviewer.addManuscriptToReviewer(myTestManuscript);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript2);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript3);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript4);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript5);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript6);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript7);
        myTestReviewer.addManuscriptToReviewer(myTestManuscript8);
        
        assertFalse(myTestSPC.isUnderAssignedManuscriptLimit(myTestReviewer));
	}
}

