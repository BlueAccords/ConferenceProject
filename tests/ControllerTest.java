package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Controller;
import model.Author;
import model.Manuscript;
import model.User;

/**
 * Tests for the Controller class. This will encompass the different business rules
 * that the Controller handles, along with just general testing for state switching.
 * 
 * @author Connor Lundberg
 * @version 5/15/2017
 */
public class ControllerTest {

	Controller myTestController;
	ControllerTestHelper myTestControllerHelper;
	User myTestUser;
	
	@Before
	public void setUp() throws Exception {
		myTestController = new Controller();
		myTestControllerHelper = new ControllerTestHelper();
		myTestUser = new User("Luke Skywalker");
		
		myTestController.addObserver(myTestControllerHelper);
		myTestControllerHelper.addObserver(myTestController);
		
		myTestControllerHelper.makeChange(myTestUser);
	}
	
	@After
	public void tearDown() throws Exception {
		myTestController.deleteObservers();
		myTestControllerHelper.deleteObservers();
	}

	
	@Test
	public void changeState_TestChangeStateForMakeManuscriptReturnsTrueManuscriptWhenStateIsAuthorSubmitManuscript() {
		myTestController.setState(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);
		String manuscriptStringToPassIntoController = ",Why Everything You Know About Space Battle Is Wrong,C:/Users/Connor/Documents/test.txt,"
				+ "Luke Skywalker, Commander Picard";
		
		myTestControllerHelper.makeChange(manuscriptStringToPassIntoController);
		
		assertEquals("The Controller state is not set to " + Controller.AUTHOR + " + " + Controller.LIST_MANUSCRIPT_VIEW + ": " + myTestController.getState(),
				myTestController.getState(), Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);
		
		Manuscript manuscriptWithSameValuesToCompare = new Manuscript("Why Everything You Know About Space Battle Is Wrong", 
				new File("C:/Users/Connor/Documents/test.txt"), new Author(myTestUser));
		assertEquals("The Manuscript is not the same as the one passed: " + myTestController.getManuscript().getTitle(), myTestController.getManuscript(), 
				manuscriptWithSameValuesToCompare);
	}





	/**
	 * A simple helper class to show the changes to the state machine in Controller.
	 * @author Connor
	 *
	 */
	public class ControllerTestHelper extends Observable implements Observer {

		public void makeChange(Object theObjectToPass) {
			setChanged();
			notifyObservers(theObjectToPass);
		}
		
		
		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
