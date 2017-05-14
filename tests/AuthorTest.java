package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Author.ManuscriptNotInListException;
import model.Manuscript;
import model.User;

public class AuthorTest {
	
	User myUserForAuthor;
	Author myTestAuthor;

	@Before
	public void setUp() throws Exception {
		myUserForAuthor = new User ("rickluvsszechuansauce@gmail.com");
		myTestAuthor = new Author (myUserForAuthor);
	}

	
	/**
	 * Tests the addManuscript method for a single manuscript added. Implicitly tests
	 * getMyManuscripts as well.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void addManuscript_TestAddManuscriptForCorrectlyAddedManuscript() {
		Manuscript newManuscriptToAdd = new Manuscript ("The Joys of Squanching",
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		myTestAuthor.addManuscript(newManuscriptToAdd);
		assertTrue("The list of manuscripts attached to this author does not contain the new manuscript", 
				myTestAuthor.getMyManuscripts().contains(newManuscriptToAdd));
	}
	
	
	/**
	 * Tests addManuscript for multiple manuscripts. This checks that the size of the returned
	 * manuscript list is the same as the number of manuscripts added. It also makes sure that 
	 * the manuscripts contained in the returned list are the same as the ones added.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void addManuscript_TestAddManuscriptForCorrectListValuesAndListSize() {
		Manuscript newManuscriptToAdd = new Manuscript ("The Joys of Squanching",
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		Manuscript differentNewManuscriptToAdd = new Manuscript ("Environmental Impacts of Mega Seed Harvests", 
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		myTestAuthor.addManuscript(newManuscriptToAdd);
		myTestAuthor.addManuscript(differentNewManuscriptToAdd);
		
		//checks that the size of the list is 2
		assertEquals("The list is not the correct size", myTestAuthor.getMyManuscripts().size(), 2);
		
		ArrayList<Manuscript> addedManuscriptsList = new ArrayList<Manuscript>();
		addedManuscriptsList.add(newManuscriptToAdd);
		addedManuscriptsList.add(differentNewManuscriptToAdd);
		
		//checks that the values are the same, and in the same order
		for (int i = 0; i < addedManuscriptsList.size(); i++) {
			assertEquals("The two manuscripts are not the same: " + myTestAuthor.getMyManuscripts().get(i).getTitle() 
					+ ", " + addedManuscriptsList.get(i).getTitle(), myTestAuthor.getMyManuscripts().get(i), addedManuscriptsList.get(i));
		}
	}

	
	/**
	 * Tests that the manuscript list for myTestAuthor is emtpy after adding a manuscript, then 
	 * using removeManuscript.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test
	public void removeManuscript_TestRemoveManuscriptForCorrectEmptyListAfterOperation() {
		Manuscript newManuscriptToAdd = new Manuscript ("The Joys of Squanching",
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		myTestAuthor.addManuscript(newManuscriptToAdd);
		assertEquals("The list size is not 1: " + myTestAuthor.getNumSubmittedManuscripts(), myTestAuthor.getNumSubmittedManuscripts(), 1);
		
		try {
			myTestAuthor.removeManuscript(newManuscriptToAdd);
		} catch (ManuscriptNotInListException e) {
			e.printStackTrace();
		}
		assertEquals("The list size is no longer 0: " + myTestAuthor.getMyManuscripts().size(), myTestAuthor.getMyManuscripts().size(), 0);
	}
	
	
	/**
	 * Tests that removeManuscript throws a ManuscriptNotInListException after trying to remove a manuscript from 
	 * the empty manuscript list.
	 * 
	 * @throws ManuscriptNotInListException Because the list is empty
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test (expected = ManuscriptNotInListException.class)
	public void removeManuscript_TestRemoveManuscriptForExceptionByRemovingFromEmptyList() throws ManuscriptNotInListException {
		Manuscript newManuscriptToAdd = new Manuscript ("The Joys of Squanching",
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		myTestAuthor.removeManuscript(newManuscriptToAdd);
		fail("ManuscriptNotInListException not thrown after removing from an empty list");
	}
	
	
	/**
	 * Tests that removeManuscript throws a ManuscriptNotInListException after trying to remove a manuscript
	 * from the manuscript list when the manuscript is not in the list.
	 * 
	 * @throws ManuscriptNotInListException Because the manuscript is not in the list
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	@Test (expected = ManuscriptNotInListException.class)
	public void removeManuscript_TestRemoveManuscriptForExceptionByRemovingManuscriptThatIsNotInList() throws ManuscriptNotInListException {
		Manuscript newManuscriptToAdd = new Manuscript ("The Joys of Squanching",
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		Manuscript differentNewManuscriptToAdd = new Manuscript ("Environmental Impacts of Mega Seed Harvests", 
				new File("C:/users/connor/documents/test.txt"), myUserForAuthor);
		
		
		myTestAuthor.addManuscript(newManuscriptToAdd);
		
		myTestAuthor.removeManuscript(differentNewManuscriptToAdd);
		fail("ManuscriptNotInListException not thrown after removing manuscript from list it is not in");
	}
}


























