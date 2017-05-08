package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import conference_package.Conference;
import conference_package.Manuscript;
import conference_package.User;

/**
 * Tests all business rules.
 * @author Ian Waak, Ayub Tiba, Vincent Povio, Vinh Le, James Roberts
 * @version 4/30/2017
 */
public class BusinessRule1aTest {
	
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
}