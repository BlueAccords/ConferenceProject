package client;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import model.*;
import model.Manuscript.AuthorExistsInListException;

/**
 * The System controller that handles the different states of the 
 * program. It is the bridge between the UI and the Model. 
 * 
 * @author Connor Lundberg
 * @version 5/15/2017
 */
public class Controller extends Observable implements Observer {

	//View States
	public static final int GO_BACK = -7;
	public static final int LOG_OUT_STATE = -6;
	public static final int FAIL_REVIEWER_IS_AUTHOR_ON_MANUSCRIPT= -5;
	public static final int FAIL_SUBMITED_PAST_DEADLINE = -4;
	public static final int FAIL_AUTHOR_HAS_TO_MANY_MANUSCRIPTS = -3;
    public static final int LOG_IN_STATE = -2;
    public static final int CHOOSE_USER = -1;
	public static final int AUTHOR = 0;
	public static final int REVIEWER = 100;
	public static final int SUBPROGRAM_CHAIR = 200;
	
	//Action States
	public static final int USER_OPTIONS = 0;
	public static final int SUBMIT_MANUSCRIPT_VIEW = 1;
	public static final int LIST_MANUSCRIPT_VIEW = 2;
	public static final int LIST_CONFERENCE_VIEW = 3;
	public static final int ASSIGN_REVIEWERS = 4;
	public static final int LIST_ASSIGNED_REVIEWERS_VIEW = 5;
	public static final int SUBMIT_RECOMMENDATION = 6;
	public static final int MANUSCRIPT_OPTIONS_VIEW = 7;

	// This view is for when the controller has had myCurrentManuscript set to the new manuscript to be
	// submitted and then this state should called to actually submit the manuscript
	public static final int SUBMIT_MANUSCRIPT_ACTION = 8;
	public static final int DELETE_MANUSCRIPT = 9;
	public static final int VIEW_MANUSCRIPT_INFO = 10;
	
	//Used to parse the current role
	private static final int ROLE_POS = 100;


	
	//Objects we are adding in the System. We are saving them because we need persistence between states.
	private int myCurrentState;
	private User myCurrentUser;
	private Conference myCurrentConference;
	private Manuscript myCurrentManuscript;
	private Author myCurrentAuthor;
	private SubprogramChair myCurrentSubprogramChair;
	private Reviewer myCurrentReviewer;
	private ParentFrameView myParentFrame;
	
	//Used for the stack
	private Stack<String> myPreviousStates;
	private String myLastState;
	
	
	private boolean isOpen;
	

	/**
	 * The Controller constructor. This takes no arguments and sets all of
	 * the fields.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	public Controller () {
		myCurrentState = AUTHOR;
		myCurrentUser = null;
		myCurrentConference = new Conference("", new Date(), new Date(), new Date(), new Date());
		myCurrentManuscript = null;
		myCurrentAuthor = null;
		myCurrentSubprogramChair = null;
		myCurrentReviewer = null;
		myPreviousStates = new Stack<String> ();
		
		// initialize data from serialized objects
		// if in debug, driver will init user and conference list
		
		// initialize parent JFrame window and initialize observer connection between the two
		myParentFrame = new ParentFrameView("MSEE Conference Program", 1280, 720);
		myParentFrame.addObserver(this);
		isOpen = false;
	}
	
	
	/**
	 * Call this method after instantiating the Controller
	 * to start the program. It sets myCurrentState to 0 and
	 * makes the UI object.
	 * 
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	public void startProgram () {
		myCurrentState = LOG_IN_STATE;
		
		// set intial view to login panel
		LoginView loginView = new LoginView();
		JPanel loginPanel = loginView.getPanel();
		loginView.addObserver(this);
		this.addObserver(loginView);
		myParentFrame.addPanel(loginPanel, ParentFrameView.LOGIN_PANEL_VIEW);
		myParentFrame.getJFrame().setVisible(true);
		myLastState = ParentFrameView.LOGIN_PANEL_VIEW;

		/*setChanged();
		notifyObservers(myCurrentState);*/
	}
	
	
	/**
	 * This method is the finite state machine. It takes a integer 
	 * representing the next state to change to and passes information to
	 * the UI accordingly.
	 * 
	 * @author Connor Lundberg
	 * @author Josiah Hopkins
	 * @author Morgan Blackmore
	 * @author Ryan Tran
	 * @version 5/6/2017
	 * @param theNextState The next state the program will be in.
	 */
	private void changeState (int theNextState) {
		//String[] pieces = theNextState.split(",");
		
		if (theNextState < 0) {
			switch (theNextState % -ROLE_POS) {
				case LOG_IN_STATE:
					if(myCurrentUser != null) {
						ConferenceListView confListView = new ConferenceListView(Conference.getConferences(), myCurrentUser);
						myPreviousStates.push(myLastState);
						myLastState = ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW;
						confListView.addObserver(myParentFrame);
						myParentFrame.addPanel(confListView.createConferenceListView(), ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW);
						myParentFrame.switchToPanel(ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW);
						//System.out.println("ParentFrame panel name: " + myParentFrame.getCurrentPanelName());
					}
					
					//setChanged();					//This is commented out because I don't think Controller needs to be observable.
					//notifyObservers(myCurrentState);
					break;
				case CHOOSE_USER:
					switch (((theNextState * -1) / ROLE_POS) * ROLE_POS) { //will need to break this up more here.
						case AUTHOR:
							//System.out.println("User chose Author role");
							//myCurrentAuthor = (Author) myCurrentUser;
							ArrayList<Manuscript> authorManuscriptList = myCurrentConference.getManuscriptsBelongingToAuthor(myCurrentAuthor);

							// init manuscript list view
							AuthorManuscriptListTableView manuscriptListView = new AuthorManuscriptListTableView(authorManuscriptList);
							
							// store state history
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW;
							
							// switch to manuscript list view
							manuscriptListView.addObserver(myParentFrame);
							myParentFrame.addPanel(manuscriptListView.getMyPanel(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							myParentFrame.setUserRole(ParentFrameView.AUTHOR_ROLE);
							myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							isOpen = true;
							break;
						case SUBPROGRAM_CHAIR:
							SPCHomeView subprogramChairView = new SPCHomeView(myCurrentConference.getManuscripts(),
									myCurrentConference);
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.SPC_HOME_VIEW;
							subprogramChairView.addObserver(myParentFrame);
							myParentFrame.addPanel(subprogramChairView.getMyPanel(), ParentFrameView.SPC_HOME_VIEW);
							myParentFrame.setUserRole(ParentFrameView.SUBPROGRAM_CHAIR_ROLE);
							myParentFrame.switchToPanel(ParentFrameView.SPC_HOME_VIEW);
							break;
					}
					//myCurrentState += LIST_CONFERENCE_VIEW;
					
					//setChanged();
					//notifyObservers(myCurrentState);
					break;
				case LOG_OUT_STATE:
					//System.out.println("Log out state entered");
					
					// reset session data and header gui state
					myPreviousStates.clear();
					//myPreviousStates.push(ParentFrameView.LOGIN_PANEL_VIEW);
					myLastState = ParentFrameView.LOGIN_PANEL_VIEW;
					//System.out.println("The stack is empty: " + myPreviousStates.isEmpty());
					this.resetCurrentSessionState();
					this.myParentFrame.logoutUser();
					isOpen = false;

					// switch to login view
					LoginView loginView = new LoginView();
					JPanel loginPanel = loginView.getPanel();
					loginView.addObserver(this);
					this.addObserver(loginView);
					myParentFrame.addPanel(loginPanel, ParentFrameView.LOGIN_PANEL_VIEW);
					myParentFrame.switchToPanel(ParentFrameView.LOGIN_PANEL_VIEW);
					break;
				case GO_BACK:
					if (!myPreviousStates.isEmpty()) {
						String lastView = myPreviousStates.pop();
						if (lastView.equals(ParentFrameView.LOGIN_PANEL_VIEW)) {
							changeState(LOG_OUT_STATE);
						} else {
							if (lastView.equals(ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW)) {
								isOpen = !isOpen;
							} else if(lastView.equals(ParentFrameView.USER_ROLE_VIEW)) {
								// reset user role
								myParentFrame.setUserRole("");

								// reset state of user roles
								myCurrentManuscript = null;
								myCurrentAuthor = null;
								myCurrentSubprogramChair = null;
								myCurrentReviewer = null;
							}
							myLastState = lastView;
							myParentFrame.switchToPanel(lastView);
						}
					}
					//System.out.println(myPreviousStates.toString());
					break;
			}
		} else {
			switch ((theNextState / ROLE_POS) * ROLE_POS) {
				case AUTHOR:
					switch (theNextState % ROLE_POS){
						
						case SUBMIT_MANUSCRIPT_VIEW:
							AuthorSubmitManuscriptView authorSubmitView = new AuthorSubmitManuscriptView(myCurrentAuthor, myCurrentConference);
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.SUBMIT_MANUSCRIPT_VIEW;

							authorSubmitView.addObserver(myParentFrame);
							myParentFrame.addPanel(authorSubmitView.submitManuscriptView(), ParentFrameView.SUBMIT_MANUSCRIPT_VIEW);
							myParentFrame.switchToPanel(ParentFrameView.SUBMIT_MANUSCRIPT_VIEW);
							break;
						/**
						 * Preconditions:
						 * 	The new manuscript to be submitted must have been set by notifying
						 * the controller with the new manuscript object to be submitted
						 *  myCurrentAuthor must be non-null
						 *  myCurrentConference must be non-null
						 */
						case SUBMIT_MANUSCRIPT_ACTION:
							System.out.println("Submit Manscript Action Entered ========");
							if(this.myCurrentManuscript == null) {
								System.out.println("No manuscript has been set. Please notify controller by sending"
										+ "the new manuscript.");
								break;
							}

							// Add the currently set manuscript to the current author and conference
							addManuscriptToAuthorAndConference(this.myCurrentManuscript);
							
							// redirect user to manuscript list view
							
							ArrayList<Manuscript> authorManuscriptListForRedirect = myCurrentConference.getManuscriptsBelongingToAuthor(myCurrentAuthor);

							// init manuscript list view
							AuthorManuscriptListTableView manuscriptListViewForRedirect = new AuthorManuscriptListTableView(authorManuscriptListForRedirect);
							
							// store state history
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW;
							
							// switch to manuscript list view
							manuscriptListViewForRedirect.addObserver(myParentFrame);
							myParentFrame.addPanel(manuscriptListViewForRedirect.getMyPanel(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							myParentFrame.setUserRole(ParentFrameView.AUTHOR_ROLE);
							myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							isOpen = true;
							break;
						case LIST_MANUSCRIPT_VIEW:
							if (!isOpen) {
								ArrayList<Manuscript> authorManuscriptList = myCurrentConference.getManuscriptsBelongingToAuthor(myCurrentAuthor);

								// init manuscript list view
								AuthorManuscriptListTableView manuscriptListView = new AuthorManuscriptListTableView(authorManuscriptList);
								
								// store state history
								myPreviousStates.push(myLastState);
								myLastState = ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW;
								
								// switch to manuscript list view
								manuscriptListView.addObserver(myParentFrame);
								myParentFrame.addPanel(manuscriptListView.getMyPanel(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								isOpen = true;
							} else {
								AuthorManuscriptOptionsView manuscriptOptionsView = new AuthorManuscriptOptionsView(myCurrentManuscript);
								myPreviousStates.push(myLastState);
								myLastState = ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW;
								manuscriptOptionsView.addObserver(this);
								myParentFrame.addPanel(manuscriptOptionsView.createManuscriptOptions(), ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW);
								isOpen = false;
							}
							break;
						case LIST_CONFERENCE_VIEW:
							UserRoleView userRoleView = new UserRoleView(myCurrentConference, myCurrentUser); //Will need to change constructor to take some boolean for SubChair
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.USER_ROLE_VIEW;
							userRoleView.addObserver(myParentFrame);
							myParentFrame.addPanel(userRoleView.createSelectRolePanel(), ParentFrameView.USER_ROLE_VIEW);
							myParentFrame.switchToPanel(ParentFrameView.USER_ROLE_VIEW);
							break;
						case MANUSCRIPT_OPTIONS_VIEW:
							if (!isOpen) {
								ArrayList<Manuscript> authorManuscriptList = myCurrentConference.getManuscriptsBelongingToAuthor(myCurrentAuthor);
								
								AuthorManuscriptListView manuscriptListView = new AuthorManuscriptListView(authorManuscriptList);
								myPreviousStates.push(myLastState);
								myLastState = ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW;
								manuscriptListView.addObserver(this);
								myParentFrame.addPanel(manuscriptListView.viewManuscriptListView(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								isOpen = true;
							} else {
								//this will be a switch to the manuscript's author list view
								isOpen = false;
							}
							break;
						case DELETE_MANUSCRIPT:
							System.out.println("DELET THIS");
							removeManuscriptFromAuthorAndConference(myCurrentManuscript);
							AuthorManuscriptListTableView manuscriptListTableView = new AuthorManuscriptListTableView(myCurrentConference
									.getManuscriptsBelongingToAuthor(myCurrentAuthor));
							manuscriptListTableView.addObserver(myParentFrame);
							myParentFrame.addPanel(manuscriptListTableView.getMyPanel(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
							break;
					}
					
					break;
				case REVIEWER:
					switch (myCurrentState % ROLE_POS){
	
					}
					break;
				case SUBPROGRAM_CHAIR:
					switch (myCurrentState % ROLE_POS){
	                    case ASSIGN_REVIEWERS:
	                    	//find eligible reviewers here
	                    	
	                        SPCAssignReviewersView assignReviewersView = new SPCAssignReviewersView();
	                        myPreviousStates.push(myLastState);
	                        myLastState = ParentFrameView.ASSIGN_REVIEWERS_VIEW;
	                        assignReviewersView.addObserver(myParentFrame);
	                        myParentFrame.addPanel(assignReviewersView.viewReviewersListView(), ParentFrameView.ASSIGN_REVIEWERS_VIEW);
	                        myParentFrame.switchToPanel(ParentFrameView.ASSIGN_REVIEWERS_VIEW);
	                        break;
	                    case SUBMIT_RECOMMENDATION:
	                 
	                    	/*
	                    	 *Does this mean "assign a manuscript to a SPC"?  
	                    	 *Unless we need this for testing purposes, we don't need
	                    	 *this type of functionality for the project.
	                    	 * 
	                    	 */
	                    	
	                    	
	                    	break;
						case LIST_MANUSCRIPT_VIEW:
							SPCHomeView spcHomeView = new SPCHomeView(myCurrentConference.getManuscripts(), 
									myCurrentConference);
							myPreviousStates.push(myLastState);
							myLastState = ParentFrameView.SPC_HOME_VIEW;
							spcHomeView.addObserver(myParentFrame);
							myParentFrame.addPanel(spcHomeView.getMyPanel(), ParentFrameView.SPC_HOME_VIEW);
							myParentFrame.switchToPanel(ParentFrameView.SPC_HOME_VIEW);
							break;
	                    case LIST_ASSIGNED_REVIEWERS_VIEW:
	                    	//will assign a chosen reviewer here
	                    	//Is this different from Assign_Reviewer?
	                    	
							
	                        break;
	                    case USER_OPTIONS:
	                    	
	                    	
	                    	break;
					}
					break;
			}
		}
		if (!myPreviousStates.isEmpty()) {
			//System.out.println(myPreviousStates.toString());
		}
	}
	
	
	public String getUserRole () {
		return myParentFrame.getUserRole();
	}
	
	
	public Manuscript getManuscript() {
		return myCurrentManuscript;
	}
	
	
	/**
	 * Sets the current state to the passed int value. Used for testing
	 * purposes only.
	 * 
	 * Pre: theNewState must be a value made from the Controller class constants.
	 * 
	 * @param theNewState The new state to set
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	public void setState (int theNewState) {
		myCurrentState = theNewState;
	}
	
	
	/**
	 * Returns the current int state. Used for testing purposes only.
	 * 
	 * @return The current state
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	public int getState () {
		return myCurrentState;
	}

	
	/**
	 * Finds the Conference title referenced within theNextState inside the Conference list that
	 * is retrieved from the Subprogram Chair.
	 * 
	 * @param theNextState The String used to pull the Conference title from
	 * @param conferenceList The list of Conferences to check against
	 * @return The Conference, otherwise null.
	 * @author Josiah Hopkins
	 * @version 5/6/2017
	 */
    private Conference findConference(String theNextState, List<Conference> conferenceList) {			//May move this into another class to share responsibilities.
	    for(Conference c: conferenceList){

        }
        return null;
    }

    
    /**
	 * Finds the Reviewer name/UID referenced within theNextState inside the past Reviewers list that
	 * is retrieved from the current Conference.
	 * 
	 * @param theNextState The String used to pull the Reviewer name/UID from
	 * @param pastReviewers The list of Reviewers to check against
	 * @return The Reviewer, otherwise null.
	 * @author Josiah Hopkins
	 * @version 5/6/2017
	 */
    private Reviewer findReviewer(String theNextState, List<Reviewer> pastReviewers) {			//May move this into another class to share responsibilities.
		for(Reviewer r: pastReviewers){

		}
		return null;
	}


	/**
	 * Makes the Manuscript to submit. This is just a helper method for clarity of
	 * FSM.
	 * 
	 * Post: Can return a null returnManuscript if any Author name included in the
	 * passed String is already an Author for the Manuscript.
	 * 
	 * @param thePieces The parsed String array received from changeState
	 * @return The new Manuscript
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	private Manuscript makeManuscript (String[] thePieces) {
		//Creating the return manuscript. May need to refactor Manuscript to take an Author instead of a User.
		//Not sure why it's using that.
		Manuscript returnManuscript = new Manuscript(thePieces[1], new File(thePieces[2]), myCurrentAuthor);
		returnManuscript.setSubmissionDate(new Date());
		for (int i = 3; i < thePieces.length; i++) {
			String[] name = thePieces[i].split(" ");
			Author temp = new Author(name[0], name[1]);
			try {
				returnManuscript.addAuthor(new Author(name[0], name[1]));
				temp.addManuscript(returnManuscript);
			} catch (AuthorExistsInListException e) {
				//If the exception is thrown, then makeManuscript will return null
				returnManuscript = null;
				break;
			}
		}
		
		return returnManuscript;
	}
	
	
	/**
	 * Adds theManuscriptToAdd to the current Author and the current Conference after checking if it already
	 * belongs to either of them.
	 * 
	 * Pre: theManuscriptToAdd is not null.
	 * 
	 * @param theManuscriptToAdd The new Manuscript to add
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	private void addManuscriptToAuthorAndConference (Manuscript theManuscriptToAdd) { //not checking for conference yet
		if (!myCurrentAuthor.checkForExistingManuscript(theManuscriptToAdd.getTitle())) {
			myCurrentAuthor.addManuscript(theManuscriptToAdd);
			try {
				myCurrentConference.addManuscript(theManuscriptToAdd);
			} catch (Exception e) {
				//System.out.println("manuscript failed to add =============");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private void removeManuscriptFromAuthorAndConference (Manuscript theManuscriptToRemove) {
		myCurrentConference.removeManuscript(theManuscriptToRemove);
	}
	
	
	public String getCurrentPanelName () {
		return myParentFrame.getCurrentPanelName();
	}
	
	/**
	 * Sets the field myCurrentUser to theNewUsernameLiteral if it is a valid name within the
	 * User list.
	 * 
	 * Pre: theNewUsername is not null received from the update.
	 * 
	 * @param theNewUsernameLiteral The new Username to check. If it is valid, then set.
	 * @author Connor Lundberg
	 * @version 5/23/2017
	 */
	private void setUser (String theNewUsernameLiteral) {
		if(User.doesEmailBelongToUser(theNewUsernameLiteral)) {
			//System.out.println("setting a user");
			myCurrentUser = User.getUserByEmail(theNewUsernameLiteral);
			//System.out.println("Updating header gui to refelect logged in user");
			this.myParentFrame.setUserToBeLoggedIn(myCurrentUser);
		}
	}
	
	
	private void printAccounts () {

	}
	
	/**
	 * this method will be used to reset all myCurrent* fields in the controller
	 * and reset the controller's state back to what it should be when at the login screen.
	 * 
	 * @author Ryan Tran
	 * @version 5/25/17
	 */
	private void resetCurrentSessionState() {
		myCurrentState = AUTHOR;
		myCurrentUser = null;
		myCurrentConference = new Conference("", new Date(), new Date(), new Date(), new Date());
		myCurrentManuscript = null;
		myCurrentAuthor = new Author(myCurrentUser);
		myCurrentSubprogramChair = new SubprogramChair(myCurrentUser);
		myCurrentReviewer = new Reviewer(null);
	}
	
	
	/**
	 * Sets the field myCurrentConference to theNewConference.
	 * 
	 * Pre: theNewConference is not null received from the update.
	 * 
	 * @param theNewConference The new Conference to set.
	 * @author Connor Lundberg
	 * @version 5/15/2017
	 */
	public void setConference (Conference theNewConference) {
		myCurrentConference = theNewConference;
		//System.out.println("set a conference");
	}
	
	/**
	 * Sets the current manuscript for the controller myCurrentManuscript field
	 * to theManuscript
	 * 
	 * PreConditions:
	 * 	theManuscript must be non-null
	 * @param theManuscript manuscript object to set myCurrentManuscript to
	 * @author Ryan Tran
	 * @version 5/27/17
	 */
	public void setManuscript(Manuscript theManuscript) {
		myCurrentManuscript = theManuscript;
	}
	
	
	public Conference getCurrentConference () {
		return myCurrentConference;
	}
	
	
	public User getCurrentUser () {
		return myCurrentUser;
	}
	
	
	public void setReviewer(Reviewer theNewReviewer) {
		myCurrentReviewer = theNewReviewer;
	}
	
	
	public void setAuthor(Author theNewAuthor) {
		myCurrentAuthor = theNewAuthor;
	}
	
	
	public void setSubprogramChair(SubprogramChair theNewSubprogramChair) {
		myCurrentSubprogramChair = theNewSubprogramChair;
	}
	
	
	/**
	 * Used to test the user in the states without having to add to the database.
	 * Don't use this when setting the actual user. Pass a string to Controller's Update
	 * as normal, not a User object.
	 * 
	 * @param theNewTestUser The new test user
	 * 
	 * @author Connor Lundberg
	 * @version 5/27/2017
	 */
	public void setTestUser (User theNewTestUser) {
		myCurrentUser = theNewTestUser;
	}
	


	/**
	 * Used to talk to the UI while staying decoupled.
	 * 
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof String) {
			//System.out.println("going to set a user");
			setUser((String) arg1);
		} else if (arg1 instanceof Conference) {
			//System.out.println("going to set a conference");
			setConference((Conference) arg1);
		} else if (arg1 instanceof Integer) {
			System.out.println("Going to make a new state");
			changeState((Integer) arg1);
		} else if (arg1 instanceof Reviewer) {
			setReviewer((Reviewer) arg1);
		} else if (arg1 instanceof Author) {
			setAuthor((Author) arg1);
		} else if (arg1 instanceof SubprogramChair) {
			setSubprogramChair((SubprogramChair) arg1);
		} else if (arg1 instanceof Manuscript) {
			/* Setting manuscript as we're changing to style where we first notify the controller
			 * about the current manuscript THEN notifying the controller of the state change
			 * and the new state will perform the action using the controller myCurrentManuscript field.
			*/ 
			setManuscript((Manuscript) arg1);
		} else if (arg1 instanceof List<?>) {
			myCurrentManuscript.setReviewerList((ArrayList<Reviewer>) arg1);
		}
	}
		
}

