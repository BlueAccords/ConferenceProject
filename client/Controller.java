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
	public static final int ASSIGN_MANUSCRIPT = 6;
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
	
	// Persistent Data for objects we will be serializing/deserializing
	private ArrayList<User> myUserList;
	

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
		
		// initialization data from local serialized file
		myUserList = User.getUsers();
		
		// initialize parent JFrame window and initialize observer connection between the two
		myParentFrame = new ParentFrameView("MSEE Conference Program", 1280, 720);
		myParentFrame.addObserver(this);
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
	 * @version 5/6/2017
	 * @param theNextState The next state the program will be in.
	 */
	private void changeState (int theNextState) {
		//String[] pieces = theNextState.split(",");
		
		if (theNextState < 0) {
			switch (theNextState) {
				case LOG_IN_STATE:
					if(myCurrentUser != null) {
						ConferenceListView confListView = new ConferenceListView(Conference.getConferences());
						confListView.addObserver(myParentFrame);
						myParentFrame.addPanel(confListView.createConferenceListView(), "AuthConfView");
						myParentFrame.switchToPanel("AuthConfView");
						myCurrentState = AUTHOR + LIST_CONFERENCE_VIEW;		//Should be CHOOSE_USER, but that screen has not been created yet.
					} else {
						System.out.println("invalid user name");
					}
					
					//setChanged();					//This is commented out because I don't think Controller needs to be observable.
					//notifyObservers(myCurrentState);
					break;
				case CHOOSE_USER:
					switch (theNextState) { //will need to break this up more here.
						case 0:
							myCurrentState = AUTHOR;
							break;
						case 1:
							myCurrentState = SUBPROGRAM_CHAIR;
							break;
					}
					myCurrentState += LIST_CONFERENCE_VIEW;
					
					//setChanged();
					//notifyObservers(myCurrentState);
					break;
				case LOG_OUT_STATE:
					System.out.println("Log out state entered");
					
					// reset session data and header gui state
					this.resetCurrentSessionState();
					this.myParentFrame.logoutUser();

					// switch to login view
					LoginView loginView = new LoginView();
					JPanel loginPanel = loginView.getPanel();
					loginView.addObserver(this);
					myParentFrame.addPanel(loginPanel, "loginPanel");
					myParentFrame.switchToPanel("loginPanel");
					break;
			}
		} else {
			switch ((theNextState / 10) * 10) {
				case AUTHOR:
					switch (theNextState % 10){
						
						case SUBMIT_MANUSCRIPT:
							Manuscript manuscriptToSubmit;
							
	
							break;
						case LIST_MANUSCRIPT_VIEW:
							
							break;
						case LIST_CONFERENCE_VIEW:
							UI_Author authorView = new UI_Author(); //need to use a static getManuscripts once it's available and pass it here.
							authorView.addObserver(myParentFrame);
							myParentFrame.addPanel(authorView.viewManuscriptListView(), "ViewManuscriptListView");
							myParentFrame.switchToPanel("ViewManuscriptListView");
							
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
	                        myCurrentState = SUBPROGRAM_CHAIR + ASSIGN_MANUSCRIPT;
	                        setChanged();
							notifyObservers(myCurrentState);
	                        break;
	                    case ASSIGN_MANUSCRIPT:
	                    	//UUID key = UUID.fromString(pieces[1]);

	                    	myCurrentState = SUBPROGRAM_CHAIR + LIST_MANUSCRIPT_VIEW;
	                    	setChanged();
	                    	notifyObservers(myCurrentState);
	                    	break;
	                    case LIST_CONFERENCE_VIEW:
							myCurrentState = SUBPROGRAM_CHAIR + USER_OPTIONS;
							setChanged();
							notifyObservers(myCurrentState);
	                        break;
						case LIST_MANUSCRIPT_VIEW:
							myCurrentState = SUBPROGRAM_CHAIR + USER_OPTIONS;
	                    	
							setChanged();
							notifyObservers(myCurrentState);
							break;
	                    case LIST_ASSIGNED_REVIEWERS_VIEW:
	                    	myCurrentState = SUBPROGRAM_CHAIR + USER_OPTIONS;
	                    	
							setChanged();
							notifyObservers(myCurrentState);
	                        break;
	                    case USER_OPTIONS:
	                    	
	                    	setChanged();
							notifyObservers(myCurrentState);
	                    	break;
					}
					break;
			}
		}
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
		if(User.doesEmailBelongToUser(myUserList, theNewUsernameLiteral)) {
			System.out.println("setting a user");
			myCurrentUser = User.getUserByEmail(myUserList, theNewUsernameLiteral);
			System.out.println("Updating header gui to refelect logged in user");
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
		System.out.println("set a conference");
	}
	
	
	public Conference getCurrentConference () {
		return myCurrentConference;
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
			System.out.println("going to set a user");
			setUser((String) arg1);
		} else if (arg1 instanceof Conference) {
			System.out.println("going to set a conference");
			setConference((Conference) arg1);
		} else if (arg1 instanceof Integer) {
			changeState((Integer) arg1);
		}
	}
		
}


























