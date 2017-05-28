/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.User;

/**
 * Tests all business rule 1a.
 * @author Ian Waak, Ayub Tiba, Vincent Povio, Vinh Le, James Roberts
 * @version 4/30/2017
 */
public class BusinessRule1aTest {
	BusinessRule1aTest
	/** The paper deadline. */
	private final static String PAPER_DEADLINE = "30-06-2017 11:59:59";

	/** The paper submission on time. */
	private final static String PAPER_SUBMISSION_ON_TIME = "29-06-2017 11:59:59";

	/** The paper submission just in time. */
	private final static String PAPER_SUBMISSION_JUST_IN_TIME = "30-06-2017 11:59:59";

	/** The paper submission late. */
	private final static String PAPER_SUBMISSION_NOT_ON_TIME = "01-08-2017 12:00:00";

	/** The paper submission late by 1 minute. */
	private final static String PAPER_SUBMISSION_NOT_ON_TIME_BY_ONE_MINUTE = "01-07-2017 12:00:00";

	/** The conference name. */
	private final static String CONFERENCE_NAME = "Test Conference";

	/**
	 * Initializes all the fields.
	 */
	private Date myTestDeadline;
	private Date myTestSubmission;
	private Conference myConference;
	private User myTestUser;
	private Manuscript myTestManuscript;

	/**
	 * This method will set up the date, conference's name,
	 * test user's names, and manuscript's names.
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