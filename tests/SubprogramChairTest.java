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
	 */
	@Test
	public void testAssignManuscriptToReviewer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.SubprogramChair#isUnderAssignedManuscriptLimit(model.Reviewer)}.
	 */
	@Test
	public void testIsUnderAssignedManuscriptLimit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.SubprogramChair#isAuthor(model.Reviewer, model.Manuscript)}.
	 */
	@Test
	public void testIsAuthor() {
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

}
