package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.User;

public class UserTest {
	
	User myTestUser;

	@Before
	public void setUp() throws Exception {
		myTestUser = new User("s_holmes@greatdetective.biz");
	}

	
	/**
	 * Tests for the getEmail method after creating a new user and passing the email through the
	 * constructor. Does not use setEmail, just testing that the constructor is working.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void getEmail_TestGetEmailForCorrectValueAfterPassingEmailInConstructorOnly() {
		assertTrue("The email is null: " + myTestUser.getEmail(), myTestUser.getEmail() != null);
		assertTrue("The constructor did not separate the first name correctly: " + myTestUser.getEmail(), myTestUser
				.getEmail().equals("s_holmes@greatdetective.biz"));
	}
	
	
	/**
	 * Tests for the setFirstName and getFirstName methods that they are doing their jobs correctly.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void setFirstNameAndGetFirstName_TestSetFirstNameAndGetFirstNameForNonNullCorrectStringSetAndReturned() {
		myTestUser.setFirstName("Sherlock");
		assertTrue("First name not set or returned correctly: " + myTestUser.getFirstName(), myTestUser.getFirstName()
				.equals("Sherlock"));
	}
	
	
	/**
	 * Tests for the setEmail to make sure it is setting the new email correctly. getEmail does not have to be tested
	 * specifically because it implicitly works based on the getEmail test using the original email from constructor.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void setEmail_TestSetEmailForNewEmailNonNullAndNotEqualToOriginal() {
		assertTrue("Original email not correct: " + myTestUser.getEmail(), myTestUser.getEmail().equals("s_holmes@greatdetective.biz"));
		
		myTestUser.setEmail("b_wayne@greatestdetective.biz");
		
		assertTrue("New email not correct: " + myTestUser.getEmail(), myTestUser.getEmail().equals("b_wayne@greatestdetective.biz"));
	}
}













