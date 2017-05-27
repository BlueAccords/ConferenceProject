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
	public static final int LOG_OUT_STATE = -6;
	public static final int FAIL_REVIEWER_IS_AUTHOR_ON_MANUSCRIPT= -5;
	public static final int FAIL_SUBMITED_PAST_DEADLINE = -4;
	public static final int FAIL_AUTHOR_HAS_TO_MANY_MANUSCRIPTS = -3;
    public static final int LOG_IN_STATE = -2;
    public static final int CHOOSE_USER = -1;
	public static final int AUTHOR = 0;
	public static final int REVIEWER = 10;
	public static final int SUBPROGRAM_CHAIR = 20;
	
	//Action States
	public static final int USER_OPTIONS = 0;
	public static final int SUBMIT_MANUSCRIPT = 1;
	public static final int LIST_MANUSCRIPT_VIEW = 2;
	public static final int LIST_CONFERENCE_VIEW = 3;
	public static final int ASSIGN_REVIEWER = 4;
	public static final int SUBMIT_RECOMMENDATION = 6;
	public static final int LIST_ASSIGNED_REVIEWERS_VIEW = 5;

	
	//Objects we are adding in the System. We are saving them because we need persistence between states.
	private int myCurrentState;
	private User myCurrentUser;
	private Conference myCurrentConference;
	private Manuscript myCurrentManuscript;
	private Author myCurrentAuthor;
	private SubprogramChair myCurrentSubprogramChair;
	private Reviewer myCurrentReviewer;
	private ParentFrameView myParentFrame;
	
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
		myCurrentManuscript = new Manuscript(null, null, null);
		myCurrentAuthor = new Author(myCurrentUser);
		myCurrentSubprogramChair = new SubprogramChair(myCurrentUser);
		myCurrentReviewer = new Reviewer(null);
		
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
		myParentFrame.addPanel(loginPanel, "loginPanel");
		myParentFrame.getJFrame().setVisible(true);

		setChanged();
		notifyObservers(myCurrentState);
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
			switch (theNextState % -10) {
				case LOG_IN_STATE:
					if(myCurrentUser != null) {
						ConferenceListView confListView = new ConferenceListView(Conference.getConferences(), myCurrentUser);
						confListView.addObserver(myParentFrame);
						myParentFrame.addPanel(confListView.createConferenceListView(), ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW);
						myParentFrame.switchToPanel(ParentFrameView.AUTHOR_CONFERENCE_LIST_VIEW);
						System.out.println("ParentFrame panel name: " + myParentFrame.getCurrentPanelName());
					} else {
						myParentFrame.switchToPanel(ParentFrameView.FAIL_INVALID_USERNAME);
					}
					
					//setChanged();					//This is commented out because I don't think Controller needs to be observable.
					//notifyObservers(myCurrentState);
					break;
				case CHOOSE_USER:
					switch (((theNextState * -1) / 10) * 10) { //will need to break this up more here.
						case AUTHOR:
							//System.out.println("User chose Author role");
							//myCurrentAuthor = (Author) myCurrentUser;
							UI_Author authorView = new UI_Author(myCurrentAuthor); //need to use a static getManuscripts once it's available and pass it here.
							authorView.addObserver(myParentFrame);
							myParentFrame.addPanel(authorView.createConferenceOptions(), ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW);
							myParentFrame.setUserRole(ParentFrameView.AUTHOR_ROLE);
							myParentFrame.switchToPanel(ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW);
							break;
						case SUBPROGRAM_CHAIR:
							//System.out.println("User chose Subprogram Chair role");
							//myCurrentSubprogramChair = (SubprogramChair) myCurrentUser;
							SPCAssignReviewerView subprogramChairView = new SPCAssignReviewerView(); //need to use a static getManuscripts once it's available and pass it here.
							subprogramChairView.addObserver(myParentFrame);
							myParentFrame.addPanel(subprogramChairView.viewReviewersListView(), ParentFrameView.VIEW_REVIEWERS_LIST_VIEW);
							myParentFrame.setUserRole(ParentFrameView.SUBPROGRAM_CHAIR_ROLE);
							myParentFrame.switchToPanel(ParentFrameView.VIEW_REVIEWERS_LIST_VIEW);
							break;
					}
					//myCurrentState += LIST_CONFERENCE_VIEW;
					
					//setChanged();
					//notifyObservers(myCurrentState);
					break;
				case LOG_OUT_STATE:
					//System.out.println("Log out state entered");
					
					// reset session data and header gui state
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
			}
		} else {
			switch ((theNextState / 10) * 10) {
				case AUTHOR:
					switch (theNextState % 10){
						
						case SUBMIT_MANUSCRIPT:
							if (!isOpen) {
								UI_Author authorView = new UI_Author(myCurrentAuthor);
								authorView.addObserver(myParentFrame);
								myParentFrame.addPanel(authorView.submitManuscriptView(), ParentFrameView.SUBMIT_MANUSCRIPT_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.SUBMIT_MANUSCRIPT_VIEW);
								isOpen = true;
							} else {
								UI_Author authorView = new UI_Author(myCurrentAuthor);
								authorView.addObserver(myParentFrame);
								myParentFrame.addPanel(authorView.createConferenceOptions(), ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.CREATE_CONFERENCE_OPTIONS_VIEW);
								isOpen = false;
							}
							break;
						case LIST_MANUSCRIPT_VIEW:
							if (!isOpen) {
								//System.out.println("Manuscript list view ========================================");
								ArrayList<Manuscript> authorManuscriptList = myCurrentConference.getManuscriptsBelongingToAuthor(myCurrentAuthor);
								//System.out.println("size of manuscripts belonging to auth and conference is..." + authorManuscriptList.size());

								UI_Author authorView = new UI_Author(authorManuscriptList, myCurrentAuthor);
								authorView.addObserver(myParentFrame);
								myParentFrame.addPanel(authorView.viewManuscriptListView(), ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.VIEW_MANUSCRIPT_LIST_VIEW);
								isOpen = true;
							} else {
								UI_Author authorView = new UI_Author(myCurrentAuthor);
								authorView.addObserver(myParentFrame);
								myParentFrame.addPanel(authorView.createManuscriptOptions(), ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW);
								myParentFrame.switchToPanel(ParentFrameView.CREATE_MANUSCRIPT_OPTIONS_VIEW);
								isOpen = false;
							}
							break;
						case LIST_CONFERENCE_VIEW:
							UserRoleView userRoleView = new UserRoleView(myCurrentConference, myCurrentUser); //Will need to change constructor to take some boolean for SubChair
							userRoleView.addObserver(myParentFrame);
							myParentFrame.addPanel(userRoleView.createSelectRolePanel(), ParentFrameView.USER_ROLE_VIEW);
							myParentFrame.switchToPanel(ParentFrameView.USER_ROLE_VIEW);
							break;
						case USER_OPTIONS:
							
							break;
					}
					
					break;
				case REVIEWER:
					switch (myCurrentState % 10){
	
					}
					break;
				case SUBPROGRAM_CHAIR:
					switch (myCurrentState % 10){
	                    case ASSIGN_REVIEWER:
	                       //SPC assigns a reviewer to a manuscript.
	                    	//displays a list of valid reviewers available for this manuscript. 
	                        break;
	                    case SUBMIT_RECOMMENDATION:
	                 
	                    	/*
	                    	 *Does this mean "assign a manuscript to a SPC"?  
	                    	 *Unless we need this for testing purposes, we don't need
	                    	 *this type of functionality for the project.
	                    	 * 
	                    	 */
	                    	
	                    	
	                    	break;
	                    case LIST_CONFERENCE_VIEW:
	                    	//does this need to be here?  Conference gets chosen before role in UI.
							
							
	                        break;
						case LIST_MANUSCRIPT_VIEW:
							//will display list of assigned manuscripts for this conference
							//will be the SPC main page
							
	                    	
							
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
			//myCurrentAuthor.addManuscript(theManuscriptToAdd);
			try {
				myCurrentConference.addManuscript(theManuscriptToAdd);
			} catch (Exception e) {
				System.out.println("manuscript failed to add =============");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		myCurrentManuscript = new Manuscript(null, null, null);
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
			addManuscriptToAuthorAndConference((Manuscript) arg1);
		} else if (arg1 instanceof User) {
			setTestUser ((User) arg1);
		}
	}
		
}

