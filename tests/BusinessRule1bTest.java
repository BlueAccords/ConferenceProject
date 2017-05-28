/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.User;

/**
 * Tests all business rule 1b.
 * @author Ian Waak, Ayub Tiba, Vincent Povio, Vinh Le, James Roberts
 * @version 4/30/2017
 */
public class BusinessRule1bTest {

	/** The paper deadline. */
	private final static String PAPER_DEADLINE = "30-06-2017 11:59:59";

	/** The conference name. */
	private final static String CONFERENCE_NAME = "Test Conference";

	/** The test manuscript number 1. */
	private final static String MANUSCRIPT_TITLE_1 = "Manuscript 1";

	/** The test manuscript number 2. */
	private final static String MANUSCRIPT_TITLE_2 = "Manuscript 2";

	/** The test manuscript number 3. */
	private final static String MANUSCRIPT_TITLE_3 = "Manuscript 3";

	/** The test manuscript number 4. */
	private final static String MANUSCRIPT_TITLE_4 = "Manuscript 4";

	/** The test manuscript number 5. */
	private final static String MANUSCRIPT_TITLE_5 = "Manuscript 5";

	/** The test manuscript number 6. */
	private final static String MANUSCRIPT_TITLE_6 = "Manuscript 6";

	/** The test manuscript number 7. */
	private final static String MANUSCRIPT_TITLE_7 = "Manuscript 7";

	/** The test manuscript number 8. */
	private final static String MANUSCRIPT_TITLE_8 = "Manuscript 8";

	/** The test manuscript number 9. */
	private final static String MANUSCRIPT_TITLE_9 = "Manuscript 9";

	/** The manuscript content. */
	private final static String MANUSCRIPT_CONTENTS = "This is my manuscript";

	/**
	 * Initializes all fields.
	 */
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
	private Manuscript myTestManuscript6;

	/**
	 * This method will set up the date, conference's name,
	 * test user's names, and manuscript's names.
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
	}

	private Manuscript createManuscriptHelper(final String theManuscriptTitle,
			final String theManuscriptContents, final User theTestUser) {

		final Manuscript manuscript = new Manuscript("Test Paper", "This is a test.", theTestUser);

		return manuscript;
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
		setupConference();
		//Two manuscripts as author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser);

		//Two manuscripts as co-author
		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser2);
		myTestManuscript3.addAuthor(myTestUser);
		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser3);
		myTestManuscript4.addAuthor(myTestUser);

		//Add manuscripts to conference
		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);

		assertTrue(myConference.isValidNumberOfSubmissions(myTestManuscript4));
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
		setupConference();

		//Four manuscripts as co-author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser2);
		myTestManuscript.addAuthor(myTestUser);

		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser3);
		myTestManuscript2.addAuthor(myTestUser);

		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser4);
		myTestManuscript3.addAuthor(myTestUser);

		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser5);
		myTestManuscript4.addAuthor(myTestUser);

		//Add manuscripts to conference
		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);

		assertTrue(myConference.isValidNumberOfSubmissions(myTestManuscript4));
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

		setupConference();
		//Four manuscripts as author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser);

		//Add manuscripts to conference
		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);

		assertTrue(myConference.isValidNumberOfSubmissions(myTestManuscript4));
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
		//myTestUser = new User("bob@hotmail.com");
		setupConference();
		//Four manuscripts as author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript5 = createManuscriptHelper(MANUSCRIPT_TITLE_5, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript6 = createManuscriptHelper(MANUSCRIPT_TITLE_6, MANUSCRIPT_CONTENTS, myTestUser);
		//Add manuscripts to conference

		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);
		myConference.addManuscript(myTestManuscript4);
		myConference.addManuscript(myTestManuscript5);


		assertFalse(myConference.isValidNumberOfSubmissions(myTestManuscript5));
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

		setupConference();
		//Five manuscripts as author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser2);
		myTestManuscript.addAuthor(myTestUser);
		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser3);
		myTestManuscript2.addAuthor(myTestUser);
		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser4);
		myTestManuscript3.addAuthor(myTestUser);
		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser5);
		myTestManuscript4.addAuthor(myTestUser);
		myTestManuscript5 = createManuscriptHelper(MANUSCRIPT_TITLE_5, MANUSCRIPT_CONTENTS, myTestUser6);
		myTestManuscript5.addAuthor(myTestUser);

		//Add manuscripts to conference
		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);
		myConference.addManuscript(myTestManuscript4);
		//myConference.addManuscript(myTestManuscript5);

		assertFalse(myConference.isValidNumberOfSubmissions(myTestManuscript5));
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

		setupConference();
		//Two manuscripts as author
		myTestManuscript = createManuscriptHelper(MANUSCRIPT_TITLE_1, MANUSCRIPT_CONTENTS, myTestUser);
		myTestManuscript2 = createManuscriptHelper(MANUSCRIPT_TITLE_2, MANUSCRIPT_CONTENTS, myTestUser);
		//Three manuscripts as co-author
		myTestManuscript3 = createManuscriptHelper(MANUSCRIPT_TITLE_3, MANUSCRIPT_CONTENTS, myTestUser2);
		myTestManuscript3.addAuthor(myTestUser);
		myTestManuscript4 = createManuscriptHelper(MANUSCRIPT_TITLE_4, MANUSCRIPT_CONTENTS, myTestUser3);
		myTestManuscript4.addAuthor(myTestUser);
		myTestManuscript5 = createManuscriptHelper(MANUSCRIPT_TITLE_5, MANUSCRIPT_CONTENTS, myTestUser4);
		myTestManuscript5.addAuthor(myTestUser);

		//Add manuscripts to conference
		myConference.addManuscript(myTestManuscript);
		myConference.addManuscript(myTestManuscript2);
		myConference.addManuscript(myTestManuscript3);
		myConference.addManuscript(myTestManuscript4);
		myConference.addManuscript(myTestManuscript5);


		//We will never get to this assertion because exception is thrown by add paper
		assertFalse(myConference.isValidNumberOfSubmissions(myTestManuscript5));
	}
}
