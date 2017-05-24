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
	private User lilRyeRye;
	private SubprogramChair subprogramChairLilRyeRye;

	private Conference RSAConference;
	private Manuscript rsa;
	private Manuscript tessellations;


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
		lilRyeRye = new User("lilRyeRye@example.com");
		subprogramChairLilRyeRye = new SubprogramChair(lilRyeRye);

		subprogramChairLilRyeRye.addReviewerToSPC(reviewerBob);
		subprogramChairLilRyeRye.addReviewerToSPC(reviewerJim);
		subprogramChairLilRyeRye.addReviewerToSPC(reviewerJohn);


		Calendar submissionDeadline = Calendar.getInstance();
		submissionDeadline.add(Calendar.HOUR, -14);
		RSAConference = new Conference("RSA", submissionDeadline.getTime(), new Date(), new Date(), new Date());

		rsa = new Manuscript("RSA", new File("ControllerTest.java"), authorBob);
		rsa.addAuthor(authorJim);
		tessellations = new Manuscript("Tessellations", new File("UserTest.java"), authorBob);
		tessellations.addAuthor(authorJohn);
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
		assertEquals("rsa should be added to reviewerJohns list", reviewerJohn.getAssignedManuscripts().get(1), rsa );
	}
	


	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 * 
	 * Test valid case where reviewer is 1 below limit
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testIsUnderAssignedManuscriptLimit_1BelowLimit_manuscriptAddedToReviewer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 * 
	 * Test valid case where reviewer is at limit
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testIsUnderAssignedManuscriptLimit_atLimit_manuscriptNotAddedToReviewer() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 * 
	 * Test valid case where reviewer is over limit
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testIsUnderAssignedManuscriptLimit_overLimit_manuscriptNotAddedToReviewer() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link model.SubprogramChair#isAuthor(model.Reviewer, model.Manuscript)}.
	 * Give a manuscript in which Reviewer is an Author of the manuscript
	 * 
	 * @author Morgan Blackmore
	 * @version 5/24/17
	 * 
	 */
	@Test
	public void testIsAuthor_ReviewerIsAuthor_manuscriptNotAddedToReviewer() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link model.SubprogramChair#isAuthor(model.Reviewer, model.Manuscript)}.
	 * Give a manuscript in which Reviewer is an Coauthor of the manuscript
 	 * @author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testIsAuthor_ReviewerIsCoauthor_manuscriptNotAddedToReviewer() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
	public void testisAfterSubmissionDeadline_atSubmissionDeadline_false() {
		fail("Not yet implemented");
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
	public void testisAfterSubmissionDeadline_beforeSubmissionDeadline_false() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test valid case where manuscript has SUFFICIENT_REVIEWS.  theRecommendation file should be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test
	public void testSubmitRecommendation_manuscriptHasSufficientReviews_addTheRecommendationToManuscript() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test  case where manuscript has fewer than SUFFICIENT_REVIEWS.  theRecommendation file should not be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test (expected = Exception.class)
	public void testSubmitRecommendation_manuscriptHasInsufficientReviews_doNotAddTheRecommendationToManuscript() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for submitRecommendation.
	 * 
	 * Test valid case where theRecommendation file is null.  It should not be stored in 
	 * manuscripts myRecommendation field
	 * 
	 *@author Morgan Blackmore
	 * @version 5/24/17
	 */
	@Test (expected = NullPointerException.class)
	public void testSubmitRecommendation_nullRecommedation_doNotAddTheRecommendationToManuscript() {
		fail("Not yet implemented");
	}
}
