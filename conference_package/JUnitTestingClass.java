package conference_package;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * Tests all business rules.
 * @author Ian Waak, Ayub Tiba, Vincent Povio, Vinh Le, James Roberts
 * @version 4/30/2017
 */
public class JUnitTestingClass {

	/**
	 * Test for submitting a paper on time.
	 * @author Ayub Tiba
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineOnTime() {
		Date testDeadline = new Date();
        Date testSubmission = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";
        final String paperSubmission = "29-06-2017 11:59:59";

        try {
        	//Deadline
            testDeadline = dateformat.parse(paperDeadline);
            
            //Actual submission date
            testSubmission = dateformat.parse(paperSubmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDeadline, testDeadline, testDeadline, testDeadline);
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "Author McAuthorson");
		testPaper.setSubmissionDate(testSubmission);
		assertTrue(conf.isSubmittedOnTime(testPaper));
	}
	
	/**
	 * Test for submitting a paper just in time.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineJustInTime() {
        Date testDeadline = new Date();
        Date testSubmission = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";
        final String paperSubmission = "30-06-2017 11:59:59";

        try {
        	//Deadline
            testDeadline = dateformat.parse(paperDeadline);
            
            //Actual submission date
            testSubmission = dateformat.parse(paperSubmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDeadline, testDeadline, testDeadline, testDeadline);
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "Author McAuthorson");
		testPaper.setSubmissionDate(testSubmission);
		assertTrue(conf.isSubmittedOnTime(testPaper));
	}
	
	/**
	 * Test for submitting a paper passed the deadline.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineNotOnTime() {
        Date testDeadline = new Date();
        Date testSubmission = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "30-06-2017 11:59:59";
        final String paperSubmission = "01-07-2017 12:00:00";

        try {
        	//Deadline
            testDeadline = dateformat.parse(paperDeadline);
            
            //Actual submission date
            testSubmission = dateformat.parse(paperSubmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDeadline, testDeadline, testDeadline, testDeadline);
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "Author McAuthorson");
		testPaper.setSubmissionDate(testSubmission);
		
		assertFalse(conf.isSubmittedOnTime(testPaper));
	}
	
	/**
	 * Test for submitting a paper when deadline missed by one minute.
	 * Test should assert false.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testSubmissionDeadlineJustMissed() {
        Date testDeadline = new Date();
        Date testSubmission = new Date();
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        final String paperDeadline = "01-05-2017 11:59:59";
        final String paperSubmission = "02-05-2017 12:00:00";

        try {
        	//Deadline
            testDeadline = dateformat.parse(paperDeadline);
            
            //Actual submission date
            testSubmission = dateformat.parse(paperSubmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		final Conference conf = new Conference("Test Conference", testDeadline, testDeadline, testDeadline, testDeadline);
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "Author McAuthorson");
		testPaper.setSubmissionDate(testSubmission);
		
		assertFalse(conf.isSubmittedOnTime(testPaper));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Two papers submitted as an author and two submitted as a co-author.
	 * @author Vincent Povio
	 * @version 4/30/2017
	 */
	@Test
	public void testValidNumberOfPapers() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");

		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Four papers submitted as a co-author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testValidNumberOfPapersCoAuthor() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "greg@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "joey@ieee.org");
		testPaper2.addAuthor("simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");

		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Four papers submitted as a author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testValidNumberOfPapersAuthor() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "simpson@ieee.org");
		
		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		
		assertTrue(conf.isValidNumberOfSubmissions(testPaper4));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Five papers submitted as a author. Final submission should fail.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testInvalidNumberOfPapersAuthor() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "simpson@ieee.org");
		final Paper testPaper5 = new Paper("Test Paper 5", "This is a fifth test", "simpson@ieee.org");
		
		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		conf.addPaper(testPaper4);
		
		//Test for valid submissions for each paper.
		assertFalse(conf.isValidNumberOfSubmissions(testPaper5));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Five papers submitted as a co-author. Final submission should fail.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testInvalidNumberOfPapersCoAuthor() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "greg@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "joey@ieee.org");
		testPaper2.addAuthor("simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");
		final Paper testPaper5 = new Paper("Test Paper 5", "This is a fifth test", "hoover@ieee.org");
		testPaper5.addAuthor("simpson@ieee.org");
		

		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		conf.addPaper(testPaper4);
		
		assertFalse(conf.isValidNumberOfSubmissions(testPaper5));
	}
	
	/**
	 * Test for valid number of papers submitted by a user.
	 * Two papers submitted as an author, three papers submitted as a co-author.
	 * @author Ian Waak
	 * @version 4/30/2017
	 */
	@Test
	public void testInvalidNumberOfPapers() {
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "simpson@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "simpson@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "robberjames@ieee.org");
		testPaper3.addAuthor("simpson@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "benlee@ieee.org");
		testPaper4.addAuthor("simpson@ieee.org");
		final Paper testPaper5 = new Paper("Test Paper 5", "This is a fifth test", "hoover@ieee.org");
		testPaper5.addAuthor("simpson@ieee.org");

		conf.addPaper(testPaper);
		conf.addPaper(testPaper2);
		conf.addPaper(testPaper3);
		conf.addPaper(testPaper4);
		
		assertFalse(conf.isValidNumberOfSubmissions(testPaper5));
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "hardy@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("barwood@ieee.org");
		final User testUser = new User("simpson@ieee.org");
		conf.addReviewers(testUser);
		
		assertFalse(testUser.isAuthor(testUser, testPaper));
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "simpson@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("barwood@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		conf.addReviewers(testUser);
		
		assertTrue(testUser.isAuthor(testUser, testPaper));
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "hardy@ieee.org");
		testPaper.addAuthor("fleegle@ieee.org");
		testPaper.addAuthor("simpson@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		conf.addReviewers(testUser);
		
		assertTrue(testUser.isAuthor(testUser, testPaper));
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
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "hardy@ieee.org");
		final User testUser = new User("simpson@ieee.org");
		conf.addReviewers(testUser);
		testUser.addPaperToReviewer(testPaper);
		
		assertTrue(testUser.isUnderAssignedPaperLimit(testUser));
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
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "hardy@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "maxim@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "james@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "vinh@ieee.org");
		final Paper testPaper5 = new Paper("Test Paper 5", "This is a fifth test", "ian@ieee.org");
		final Paper testPaper6 = new Paper("Test Paper 6", "This is another test.", "vincent@ieee.org");
		final Paper testPaper7 = new Paper("Test Paper 7", "This is a third test.", "ayub@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		
		conf.addReviewers(testUser);
		testUser.addPaperToReviewer(testPaper);
		testUser.addPaperToReviewer(testPaper2);
		testUser.addPaperToReviewer(testPaper3);
		testUser.addPaperToReviewer(testPaper4);
		testUser.addPaperToReviewer(testPaper5);
		testUser.addPaperToReviewer(testPaper6);
		testUser.addPaperToReviewer(testPaper7);
		
		assertTrue(testUser.isUnderAssignedPaperLimit(testUser));
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
		
		final Paper testPaper = new Paper("Test Paper", "This is a test.", "hardy@ieee.org");
		final Paper testPaper2 = new Paper("Test Paper 2", "This is another test.", "maxim@ieee.org");
		final Paper testPaper3 = new Paper("Test Paper 3", "This is a third test.", "james@ieee.org");
		final Paper testPaper4 = new Paper("Test Paper 4", "This is a fourth test.", "vinh@ieee.org");
		final Paper testPaper5 = new Paper("Test Paper 5", "This is a fifth test", "ian@ieee.org");
		final Paper testPaper6 = new Paper("Test Paper 6", "This is a sixth test.", "vincent@ieee.org");
		final Paper testPaper7 = new Paper("Test Paper 7", "This is a seventh test.", "ayub@ieee.org");
		final Paper testPaper8 = new Paper("Test Paper 8", "This is an eigth test.", "ian@ieee.org");
		
		final User testUser = new User("simpson@ieee.org");
		
		conf.addReviewers(testUser);
		testUser.addPaperToReviewer(testPaper);
		testUser.addPaperToReviewer(testPaper2);
		testUser.addPaperToReviewer(testPaper3);
		testUser.addPaperToReviewer(testPaper4);
		testUser.addPaperToReviewer(testPaper5);
		testUser.addPaperToReviewer(testPaper6);
		testUser.addPaperToReviewer(testPaper7);
		testUser.addPaperToReviewer(testPaper8);
		
		assertTrue(testUser.isUnderAssignedPaperLimit(testUser));
	}
}

