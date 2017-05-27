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
	
	
	/**
	 * Tests that the choose user state changes the state into the Conference Options View with the role set to Author.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void chooseUserState_ChangeStateToChooseUserState_MakesRoleAuthorWithPanelNameCreateConferenceOptionsView () {
		myTestControllerHelper.changeControllerState(Controller.CHOOSE_USER + Controller.AUTHOR); 
		assertEquals("The role is not Author: " + myTestController.getUserRole() + "       ", ParentFrameView.AUTHOR_ROLE, myTestController.getUserRole());
		assertEquals("The current panel name is not CREATE_CONFERENCE_OPTIONS_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	/**
	 * Tests that the choose user state changes the state into the View Reviewers List View with the role set to SPC.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void chooseUserState_ChangeStateToChooseUserState_MakesRoleSPCWithPanelNameViewReviewersListView () {
		myTestControllerHelper.changeControllerState(Controller.CHOOSE_USER + (Controller.SUBPROGRAM_CHAIR * -1));
		assertEquals("The role is not SPC: " + myTestController.getUserRole() + "       ", ParentFrameView.SUBPROGRAM_CHAIR_ROLE, myTestController.getUserRole());
		assertEquals("The current panel name is not VIEW_REVIEWERS_LIST_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.VIEW_REVIEWERS_LIST_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	/**
	 * Tests that the logout state changes the state back to the login panel view with the Parent Frame's 
	 * User being reset. (current User in Controller does not get changed, the name just doesn't get displayed
	 * in frame header)
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void logoutState_ChangeStateToLogoutState_PanelNameIsLoginState () {
		myTestControllerHelper.changeControllerState(Controller.LOG_OUT_STATE);
		assertEquals("The current panel name is not LOGIN_PANEL_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.LOGIN_PANEL_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	/**
	 * Tests that the Author + Submit Manuscript state is working for both branches inside of it.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void authorSubmitManuscriptTest_ChangeStateToSubmitManuscript_PanelNameIsSubmitManuscriptViewThenCreateConferenceOptionsView () {
		myTestControllerHelper.changeControllerState(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);
		assertEquals("The current panel name is not SUBMIT_MANUSCRIPT_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.SUBMIT_MANUSCRIPT_VIEW, myTestController.getCurrentPanelName());
		
		myTestControllerHelper.changeControllerState(Controller.AUTHOR + Controller.SUBMIT_MANUSCRIPT);
		assertEquals("The current panel name is not CREATE_CONFERENCE_OPTIONS_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	/**
	 * Tests that the Author + List Manuscript View state is working for both branches inside of it.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void authorListManuscriptViewTest_ChangeStateToListManuscriptView_PanelNameIsViewManuscriptListViewThenCreateConferenceOptionsView () {
		myTestControllerHelper.changeControllerState(Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);
		assertEquals("The current panel name is not VIEW_MANUSCRIPT_LIST_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW, myTestController.getCurrentPanelName());
		
		myTestControllerHelper.changeControllerState(Controller.AUTHOR + Controller.LIST_MANUSCRIPT_VIEW);
		assertEquals("The current panel name is not CREATE_MANUSCRIPT_OPTIONS_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW, myTestController.getCurrentPanelName());
	}
	
	
	/**
	 * Tests that the Author + List Conference View state changes to the User Role View state.
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	@Test
	public void authorListConferenceViewTest_ChangeStateToListConferenceView_PanelNameIsUserRoleView () {
		myTestControllerHelper.changeControllerState(Controller.AUTHOR + Controller.LIST_CONFERENCE_VIEW);
		assertEquals("The current panel name is not USER_ROLE_VIEW: " + myTestController.getCurrentPanelName() + "       ", 
				ParentFrameView.USER_ROLE_VIEW, myTestController.getCurrentPanelName());
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
