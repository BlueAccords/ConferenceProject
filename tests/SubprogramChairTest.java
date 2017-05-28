/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import model.Manuscript;
import model.Reviewer;
import model.SubprogramChair;
import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Morgan
 *
 */
public class SubprogramChairTest {

	private User bob;
	private Author authorBob;
	private Reviewer reviewerBob;
	private User jim;
	private Author authorJim;
	private Reviewer reviewerJim;
	private User john;
	private Author authorJohn;
	private Reviewer reviewerJohn;
	private User zeb;
	private Author authorZeb;
	private User darth;
	private Author authorDarth;
	private User lilRyeRye;
	private SubprogramChair subprogramChairLilRyeRye;

	private Conference RSAConference;
	private Manuscript rsa;
	private Manuscript tessellations;
	private Manuscript electrocution;
	private Manuscript sithFingers;
	private Manuscript hairstyle;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bob = new User("bob@bob.com");
		authorBob = new Author(bob);
		reviewerBob = new Reviewer(bob);
		jim = new User("jim@jim.com");
		authorJim = new Author(jim);
		reviewerJim = new Reviewer(jim);
		john = new User("john@john.com");
		authorJohn = new Author(john);
		reviewerJohn = new Reviewer(john);
		
		zeb = new User("zeb@zeb.com");
		authorZeb = new Author(zeb);
		
		darth = new User("darth@dstar.com");
		authorDarth = new Author(darth);
		
		lilRyeRye = new User("lilRyeRye@example.com");
		subprogramChairLilRyeRye = new SubprogramChair(lilRyeRye);

		subprogramChairLilRyeRye.addReviewerToSPC(reviewerBob);
		subprogramChairLilRyeRye.addReviewerToSPC(reviewerJim);
		subprogramChairLilRyeRye.addReviewerToSPC(reviewerJohn);


		Calendar submissionDeadline = Calendar.getInstance();
		submissionDeadline.add(Calendar.HOUR, -14);//sets submissionDL 14 hrs earlier than now
		RSAConference = new Conference("RSA", submissionDeadline.getTime(), new Date(), new Date(), new Date());

		rsa = new Manuscript("RSA", new File("ConferenceTest.java"), authorBob);
		rsa.addAuthor(authorJim);
		tessellations = new Manuscript("Tessellations", new File("UserTest.java"), authorBob);
		tessellations.addAuthor(authorJohn);
		electrocution = new Manuscript("Electrocution", new File("UserTest.java"), authorZeb);
		sithFingers = new Manuscript("How to shoot lighting from your fingers", new File("UserTest.java"), authorDarth);
		hairstyle = new Manuscript("Interesting Hair Using Electricity", new File("UserTest.java"), authorJim);
	}

	/**
	 * Test method for {@link model.SubprogramChair#removeManuscriptFromSPC(model.Manuscript)}.
	 */
	@Test
	public void testRemoveManuscriptFromSPC() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.SubprogramChair#assignManuscriptToReviewer(model.Reviewer, model.Manuscript)}.
	 * 
	 * Test valid assignment of a manuscript to a reviewer
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 */
	@Test
	public void testAssignManuscriptToReviewer_validAssignment_manuscriptAddedToReviewer() throws Exception {
		subprogramChairLilRyeRye.assignManuscriptToReviewer(reviewerJohn, rsa);
		assertEquals("rsa should be added to reviewerJohns list", reviewerJohn.getAssignedManuscripts().get(0), rsa );
	}
	


	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 * 
	 * Test valid case where reviewer is under assigned MAX_REVIEW_PAPERS
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 */
	@Test
	public void testIsUnderAssignedManuscriptLimit_underLimit_return() throws Exception {
		subprogramChairLilRyeRye.assignManuscriptToReviewer(reviewerJohn, rsa);
		subprogramChairLilRyeRye.assignManuscriptToReviewer(reviewerJohn, electrocution);
		assertTrue("reviewerJohn has two assignments, should be under limit",
				subprogramChairLilRyeRye.isUnderAssignedManuscriptLimit(reviewerJohn));
		
		
	}

	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 * 
	 * Test case where reviewer is at limit
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test 
	public void testIsUnderAssignedManuscriptLimit_atLimit_returnFalse() throws Exception {
		
		for (int i = 0; i<subprogramChairLilRyeRye.getMaxReviewPapers(); i++) {
			String title = new String("Manuscript" + i);
			Manuscript autoScrip = new Manuscript(title, new File("UserTest.java"), authorDarth);
			subprogramChairLilRyeRye.assignManuscriptToReviewer(reviewerJohn, autoScrip);
		}
		assertFalse("reviewerJohn has max assigned manuscripts", subprogramChairLilRyeRye.isUnderAssignedManuscriptLimit(reviewerJohn));
	}
	
	
	/**
	 * Test method for {@link model.SubprogramChair#isAuthor(model.Reviewer, model.Manuscript)}.
	 * Give a manuscript in which Reviewer is an Author of the manuscript
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testIsAuthor_ReviewerIsAuthor_returnTrue(){
		assertTrue("The author is the reviewer", subprogramChairLilRyeRye.isAuthor(reviewerBob, rsa));
		
	}
	
	/**
	 * Test method for {@link model.SubprogramChair#isAuthor(model.Reviewer, model.Manuscript)}.
	 * Give a manuscript in which Reviewer is an Coauthor of the manuscript
 	 * @author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testIsAuthor_ReviewerIsCoauthor_returnTrue() {
		assertTrue("The author is the reviewer", subprogramChairLilRyeRye.isAuthor(reviewerJim, rsa));
	}

	@Test
	public void getEligibleReviewers_RSA_shouldntContainBobOrJimButContainsJohn(){
		ArrayList<Reviewer> result = subprogramChairLilRyeRye.getEligibleReviewers(rsa);
		assertTrue(result.contains(reviewerJohn));
		assertFalse(result.contains(reviewerBob));
		assertFalse(result.contains(reviewerJim));
	}

	@Test
	public void getEligibleReviewers_Tessellations_shouldntContainBobOrJohnButContainsJim(){
		ArrayList<Reviewer> result = subprogramChairLilRyeRye.getEligibleReviewers(tessellations);

		assertTrue(result.contains(reviewerJim));
		assertFalse(result.contains(reviewerBob));
		assertFalse(result.contains(reviewerJohn));
	}
	
	/**
	 * Test method for isAfterSubmissionDeadline.
	 * 
	 * Test valid case where todays date is after submissionDeadline.  Method should return true.
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testisAfterSubmissionDeadline_afterSubmissionDeadline_true() {
		Calendar afterDeadline = Calendar.getInstance(); 
		
		assertFalse(subprogramChairLilRyeRye.isAfterSubmissionDeadline(RSAConference, afterDeadline.getTime()));


	}

	/**
	 * Test method for isAfterSubmissionDeadline.
	 * 
	 * Test case where todays date is at submissionDeadline.  Method should return false.
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testisAfterSubmissionDeadline_atSubmissionDeadline_true() {
		Date atDeadline = RSAConference.getManuscriptDeadline();
		assertTrue("SubmissionDL and current time should be same, so return True", 
				subprogramChairLilRyeRye.isAfterSubmissionDeadline(RSAConference, atDeadline));
	}
	
	/**
	 * Test method for isAfterSubmissionDeadline.
	 * 
	 * Test case where todays date is before submissionDeadline.  Method should return false.
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testisAfterSubmissionDeadline_beforeSubmissionDeadline_true() {
	
		Calendar beforeDeadline = Calendar.getInstance();
		beforeDeadline.add(Calendar.HOUR, -18);
		//ConfDL is 14 hours ago
		//thisDL is 18 hours ago 
		//so will return true b/c ConfDL is after thisDL
		assertTrue("beforeDeadline set before conferenceDeadline, should return false",subprogramChairLilRyeRye.isAfterSubmissionDeadline(RSAConference, beforeDeadline.getTime()));
		
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test valid case where manuscript has SUFFICIENT_REVIEWS.  theRecommendation file should be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 * @throws NullPointerException 
	 */
	@Test
	public void testSubmitRecommendation_manuscriptHasSufficientReviews_addTheRecommendationToManuscript() throws NullPointerException, Exception {
		File review1 = new File("UserTest.java");
		File review2 = new File("UserTest.java");
		File review3 = new File("UserTest.java");
		File recommendation = new File("UserTest.java");
		rsa.addReview(review1);
		rsa.addReview(review2);
		rsa.addReview(review3);
		rsa.addRecommendation(recommendation);
		assertEquals("recommendation in manuscript should equal recommendation submitted", rsa.getRecommendation(), recommendation);
		
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test  case where manuscript has fewer than SUFFICIENT_REVIEWS.  theRecommendation file should not be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 * @throws NullPointerException 
	 */
	@Test (expected = Exception.class)
	public void testSubmitRecommendation_manuscriptHasInsufficientReviews_throwException() throws NullPointerException, Exception {
		File recommendation = new File("UserTest.java");
		rsa.addRecommendation(recommendation);
		
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test valid case where theRecommendation file is null.  It should not be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 * @throws Exception 
	 * @throws NullPointerException 
	 */
	@Test (expected = NullPointerException.class)
	public void testSubmitRecommendation_nullRecommedation_doNotAddTheRecommendationToManuscript() throws NullPointerException, Exception {
		File nullRecommendation = null;
		rsa.addRecommendation(nullRecommendation);
		
	}
}
