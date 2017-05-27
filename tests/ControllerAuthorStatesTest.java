package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Controller;
import client.ParentFrameView;
import model.Author;
import model.Manuscript;
import model.User;

/**
 * Tests for the Controller class. This will encompass the different business rules
 * that the Controller handles for the Author states only. This will also include tests
 * for Login as well.
 * 
 * @author Connor Lundberg
 * @version 5/27/2017
 */
public class ControllerAuthorStatesTest {

	Controller myTestController;
	User myTestUser;
	TestControllerHelper myTestControllerHelper;
	
	@Before
	public void setUp() throws Exception {
		myTestController = new Controller();
		myTestUser = new User("luke_skywalker@lucasfilms.com");
		myTestControllerHelper = new TestControllerHelper();

		myTestControllerHelper.addObserver(myTestController);
		
		myTestController.setTestUser(myTestUser);
	}
	
	@After
	public void tearDown() throws Exception {
		myTestControllerHelper.deleteObservers();
	}
	
	
	/**
	 * Tests that the login state will throw an illegal argument exception when the user is null.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test (expected = IllegalArgumentException.class)
	public void loginState_ChangeStateToLoginStateWithNullUser_PanelNameIsFailInvalidUsername_ThrowsException () {
		User nullUser = null;
		myTestController.setTestUser(nullUser);
		assertTrue("The Controller's current User is not null", myTestController.getCurrentUser() == null);
		
		myTestControllerHelper.changeControllerState(Controller.LOG_IN_STATE);
		
		fail("The panel switched when the user is null");
	}
	
	
	/**
	 * Tests that the login state changes the state into the Author Conference List View.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void loginState_ChangeStateToLoginStateWithValidUser_PanelNameIsAuthorConferenceListView () {
		assertEquals("The Controller's user is not luke_skywalker@lucasfilms.com: " + myTestController.getCurrentUser().getEmail(), 
				"luke_skywalker@lucasfilms.com", myTestController.getCurrentUser().getEmail());
		
		myTestControllerHelper.changeControllerState(Controller.LOG_IN_STATE);
		assertEquals("The current panel name is not AUTHOR_CONFERENCE_LIST_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
						ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	
	@Test
	public void chooseUserState_ChangeStateToChooseUserState_MakesRoleAuthorWithPanelNameCreateConferenceOptionsView () {
		myTestControllerHelper.changeControllerState(Controller.CHOOSE_USER); 
		
	}

	
	/**
	 * A helper class that can manually change the state of the Controller to see that it is switching 
	 * views correctly in the ParentFrameView.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	public class TestControllerHelper extends Observable implements Observer {

		/**
		 * A helper method to manually change the state of the Controller. Use myParentFrameView's
		 * getCurrentPanelName to verify the state did change correctly.
		 * 
		 * Pre: theNewState must be a valid state made from using the Controller constants. 
		 * 
		 * @param theNewState The new state to set the Controller to
		 * 
		 * @author Connor Lundberg
		 * @version 5/27/2017
		 */
		public void changeControllerState (int theNewState) {
			setChanged();
			notifyObservers(theNewState);
		}
		
		
		public void setControllerUser (User theNewUser) {
			setChanged();
			notifyObservers(theNewUser);
		}
		
		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
