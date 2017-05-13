package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Manuscript;
import model.User;

public class AuthorTest {
	
	User myUserForAuthor;
	Author myTestAuthor;

	@Before
	public void setUp() throws Exception {
		myUserForAuthor = new User ("r_sanchezluvsszechuansauce@gmail.com");
		myTestAuthor = new Author (myUserForAuthor);
	}

	
	
	@Test
	public void addManuscript_TestAddManuscriptForCorrectlyAddedManuscript() {
		Manuscript newManuscriptToAdd = new Manuscript (null, null, myUserForAuthor);
	}

}
