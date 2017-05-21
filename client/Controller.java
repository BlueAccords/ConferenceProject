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
	

	/**
	 * The Controller constructor. This takes no arguments and sets all of
	 * the fields.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	public Controller () {
		myCurrentState = AUTHOR;
		myCurrentUser = new User(null);
		myCurrentConference = new Conference("", new Date(), new Date(), new Date(), new Date());
		myCurrentManuscript = new Manuscript(null, null, null);
		myCurrentAuthor = new Author(myCurrentUser);
		myCurrentSubprogramChair = new SubprogramChair(myCurrentUser);
		myCurrentReviewer = new Reviewer(null);
		
		// initialize parent JFrame window and initialize observer connection between the two
		myParentFrame = new ParentFrameView("MSEE Conference Program", 600, 900);
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
	private void changeState (String theNextState) {
		String[] pieces = theNextState.split(",");
		
		if (myCurrentState < 0) {
			switch (myCurrentState) {
				case LOG_IN_STATE:
					myCurrentState = CHOOSE_USER;
					
					setChanged();
					notifyObservers(myCurrentState);
					break;
				case CHOOSE_USER:
					switch (pieces[0]) {
						case "":
							myCurrentState = AUTHOR;
							break;
						case "a":
							myCurrentState = SUBPROGRAM_CHAIR;
							break;
					}
					myCurrentState += LIST_CONFERENCE_VIEW;
					
					setChanged();
					notifyObservers(myCurrentState);
					break;
			}
		} else {
			switch ((myCurrentState / 10) * 10) {
				case AUTHOR:
					switch (myCurrentState % 10){
						
						case SUBMIT_MANUSCRIPT:
							Manuscript manuscriptToSubmit;
							if(pieces[0].equals("")){
								manuscriptToSubmit = makeManuscript(pieces);
								// handle case where paper is past submission deadline
								if(manuscriptToSubmit == null) {
									myCurrentState = FAIL_SUBMITED_PAST_DEADLINE;
									setChanged();
									notifyObservers(myCurrentState);
									break;
								// handle max limitcase
								} else if(5 >= 5) {
									myCurrentState = FAIL_AUTHOR_HAS_TO_MANY_MANUSCRIPTS;
									setChanged();
									notifyObservers(myCurrentState);
									break;
								}
								//TODO Will need to add this Manuscript into some serialized list for storage.
								myCurrentManuscript = manuscriptToSubmit;
								myCurrentState = AUTHOR + LIST_MANUSCRIPT_VIEW;
								setChanged();
								notifyObservers(myCurrentState);
	                        }
	
							break;
						case LIST_MANUSCRIPT_VIEW:
							if (pieces[0].equals("")) {
								myCurrentState = AUTHOR + USER_OPTIONS;
								setChanged();
								notifyObservers(myCurrentState);
							}
							else if (pieces[0].equals("")) {
								myCurrentState = AUTHOR;
								setChanged();
								notifyObservers(myCurrentState);
							}
							break;
						case LIST_CONFERENCE_VIEW:
							if (pieces[0].equals("")) {
								myCurrentState = AUTHOR + USER_OPTIONS;
								setChanged();
								notifyObservers(myCurrentState);
							}
							break;
						case USER_OPTIONS:
							switch (pieces[0]) {
							case "":
								myCurrentState = AUTHOR;
	                    		break;
	                    	case "a":
	                    		myCurrentState = AUTHOR + SUBMIT_MANUSCRIPT;
	                    		break;
	                    	case "b":
	                    		myCurrentState = AUTHOR + LIST_MANUSCRIPT_VIEW;
	                    		break;
	                    	case "c":
	                    		myCurrentState = AUTHOR + LIST_CONFERENCE_VIEW;
	                    		break;
							}
							
							setChanged();
							notifyObservers(myCurrentState);
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
	                    	UUID key = UUID.fromString(pieces[1]);

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
	                    	switch (pieces[0]) {
		                    	case "":
		                    		myCurrentState = SUBPROGRAM_CHAIR + ASSIGN_REVIEWER;
		                    		break;
		                    	case "a":
		                    		myCurrentState = SUBPROGRAM_CHAIR + LIST_CONFERENCE_VIEW;
		                    		break;
		                    	case "b":
		                    		myCurrentState = SUBPROGRAM_CHAIR + LIST_MANUSCRIPT_VIEW;
	                    	}
	                    	
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
	 * Sets the field myCurrentUser to theNewUser.
	 * 
	 * Pre: theNewUser is not null received from the update.
	 * 
	 * @param theNewUser The new User to set.
	 * @author Connor Lundberg
	 * @version 5/15/2017
	 */
	private void setUser (User theNewUser) {
		myCurrentUser = theNewUser;
	}
	
	
	private void printAccounts () {

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
		System.out.println("Cntroller updated");
		if (arg1 instanceof String) {
			changeState ((String) arg1);
		} else if (arg1 instanceof Conference) {
			setConference((Conference) arg1);
		} else if (arg1 instanceof User) {
			setUser((User) arg1);
		}
	}
		
}


























