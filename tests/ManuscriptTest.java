package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Manuscript;
import model.Manuscript.AuthorExistsInListException;
import model.User;


/**
 * The Group 3 custom ManuscriptTest class for the Manuscript object.
 * 
 * @author Connor Lundberg
 * @version 5/13/2017
 */
public class ManuscriptTest {
	
	User myMainUserForManuscript;
	Manuscript myManuscript;
	

	@Before
	public void setUp() throws Exception {
		myMainUserForManuscript = new User("v_helsing@live.com");
		myMainUserForManuscript.setFirstName("Van");
		myMainUserForManuscript.setLastName("Helsing");
		
		myManuscript = new Manuscript ("Existence of \"Mythical\" Creatures", new File("C:/Users/Connor/Documents/test.txt"), myMainUserForManuscript);
	}

	
	/**
	 * Tests getTitle for the correct title used in the constructor. This acts as a test for the constructor and
	 * the corresponding getter.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void getTitle_TestGetTitleForSameTitleAsUsedInConstructor() {
		assertTrue("The title is not the same as the one used in the constructor: " + myManuscript.getTitle(), myManuscript
				.getTitle().equals("Existence of \"Mythical\" Creatures"));
	}
	
	
	/**
	 * Tests getManuscriptFile for the correct file used in the constructor. This tests the absolute path rather using a new
	 * file because getAbsolutePath changes the slash mark.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void getManuscriptFile_TestGetManuscriptFileForSameFileAsUsedInConstructor() {
		assertEquals("File compared is not the same as the one used in constructor: " + myManuscript.getManuscriptFile().getAbsolutePath(), 
				myManuscript.getManuscriptFile().getAbsolutePath(), new File("C:/Users/Connor/Documents/test.txt").getAbsolutePath());
	}
	
	
	/**
	 * Test that getAuthors returns the correct author list. Mainly checks that the main author used in 
	 * the constructor is present within the list.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void getAuthors_TestGetAuthorsForSameMainAuthorAsUsedInConstructor() {
		ArrayList<User> listOfAuthorsForMyManuscript = myManuscript.getAuthors();
		
		assertEquals("Main author was not within the list of authors: " + listOfAuthorsForMyManuscript.toString(), 
				listOfAuthorsForMyManuscript.get(0), myMainUserForManuscript);
	}
	
	
	/**
	 * Test that the new author to add has been added correctly. This checks that the size of the list has 
	 * increased and that the correct new author is within.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void addAuthor_TestAddAuthorForNewAuthorAddedToAuthorList() {
		User newAuthorToAddToAuthorListForMyManuscript = new User("g_OfRivia@witchercareers.com");
		
		try {
			myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscript);
		} catch (AuthorExistsInListException e) {
			e.printStackTrace();
		}
		
		ArrayList<User> listOfAuthorsForMyManuscript = myManuscript.getAuthors();
		assertFalse("Size of author list has not increased: " + listOfAuthorsForMyManuscript.size(), listOfAuthorsForMyManuscript.size() == 1);
		assertTrue("Size of author list is not 2: " + listOfAuthorsForMyManuscript.size(), listOfAuthorsForMyManuscript.size() == 2);
		
		assertEquals("New author is not the same as one added: " + listOfAuthorsForMyManuscript.get(1), listOfAuthorsForMyManuscript.get(1), 
				newAuthorToAddToAuthorListForMyManuscript);
	}
	
	
	/**
	 * Tests that addAuthor throws AuthorExistsInListException when adding a user that is already in the author list.
	 * 
	 * @throws AuthorExistsInListException
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test (expected = AuthorExistsInListException.class)
	public void addAuthor_TestAddAuthorForAuthorExistsInListExceptionThrownWithSameUser() throws AuthorExistsInListException {
		User newAuthorToAddToAuthorListForMyManuscript = new User("g_OfRivia@witchercareers.com");
		
		myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscript);
		myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscript);
	}
	
	
	/**
	 * Tests that addAuthor throws AuthorExistsInListException when adding a new user that has the same email as one 
	 * in the author list already.
	 * 
	 * @throws AuthorExistsInListException
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test (expected = AuthorExistsInListException.class)
	public void addAuthor_TestAddAuthorForAuthorExistsInListExceptionThrownWithNewUserButSameEmail() throws AuthorExistsInListException {
		User newAuthorToAddToAuthorListForMyManuscript = new User("g_OfRivia@witchercareers.com");
		User newAuthorToAddToAuthorListForMyManuscriptWithSameEmail = new User("g_OfRivia@witchercareers.com");
		
		myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscript);
		myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscriptWithSameEmail);
	}
	
	
	/**
	 * Test that the author email list contains the same emails as the users added into the author list.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void getAuthorsEmails_TestGetAuthorsEmailsForCorrectEmailsOfListGreaterThanOne() {
		User newAuthorToAddToAuthorListForMyManuscript = new User("g_OfRivia@witchercareers.com");
		ArrayList<User> userListToPullEmailsFrom = new ArrayList<User>();
		userListToPullEmailsFrom.add(myMainUserForManuscript);
		userListToPullEmailsFrom.add(newAuthorToAddToAuthorListForMyManuscript);

		try {
			myManuscript.addAuthor(newAuthorToAddToAuthorListForMyManuscript);
		} catch (AuthorExistsInListException e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i < myManuscript.getAuthorEmails().size(); i++) {
			assertTrue("User email not the same as that in list: " + userListToPullEmailsFrom.get(i).getEmail(), 
					myManuscript.getAuthorEmails().get(i).equals(userListToPullEmailsFrom.get(i).getEmail()));
		}
	}
	
	
	/**
	 * Test that updateFile is updating myManuscript with the correct new File.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void updateFile_TestUpdateFileForCorrectNewFilePath() {
		File newFileToUpdateMyManuscriptWith = new File("C:/Users/Connor/Documents/test2.txt");
		myManuscript.updatePaper(newFileToUpdateMyManuscriptWith);
		
		assertTrue("File not the same as the one used to update: " + myManuscript.getManuscriptFile().getAbsolutePath(), 
				myManuscript.getManuscriptFile().getAbsolutePath().equals(new File("C:/Users/Connor/Documents/test2.txt").getAbsolutePath()));
	}
	
	
	/**
	 * Tests that the submission date set is the same one returned. This is an implicit test of
	 * getSubmissionDate as well.
	 * 
	 * @author Connor Lundberg
	 * @versio 5/13/2017
	 */
	@Test
	public void setSubmissionDate_TestSetSubmissionDateForCorrectNewDateSubmitted() {
		Date newSubmissionDate = new Date();
		myManuscript.setSubmissionDate(newSubmissionDate);
		
		assertEquals("Submission date is not the same as the one set: " + myManuscript.getSubmissionDate(), myManuscript.getSubmissionDate(), newSubmissionDate);
	}
}















