package client;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import model.*;

/**
 * The System controller that handles the different states of the 
 * program. It is the bridge between the UI and the Model. 
 * 
 * @author Connor Lundberg
 * @version 5/13/2017
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

	
	//Objects we are adding in the //System. We are saving them because we need persistence between states.
	private int myCurrentState;

	private Conference myCurrentConference;
	private Manuscript myCurrentManuscript;
	private Reviewer myCurrentReviewer;
	

	/**
	 * The Controller constructor. This takes no arguments and sets all of
	 * the fields.
	 * 
	 * @author Connor Lundberg
	 * @version 5/13/2017
	 */
	public Controller () {
		myCurrentState = AUTHOR;
		myCurrentConference = new Conference(null, null, null, null, null);
		myCurrentManuscript = new Manuscript(null, null, null);
		myCurrentReviewer = new Reviewer(null);
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
	
	
	/**
	 * Sets the current state to the passed int value. Used for testing
	 * purposes only.
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
    private Conference findConference(String theNextState, List<Conference> conferenceList) {
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
    private Reviewer findReviewer(String theNextState, List<Reviewer> pastReviewers) {
		for(Reviewer r: pastReviewers){

		}
		return null;
	}


	/**
	 * Makes the Manuscript to submit. This is just a helper method for clarity of
	 * FSM.
	 * 
	 * @param thePieces The parsed String array
	 * @return The new Manuscript
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	private Manuscript makeManuscript (String[] thePieces) {
		return null;
	}
	
	
	/**
	 * Sets the new Account. This checks if theNewAccount is a valid Account within
	 * the AccountDatabase. If so, then it will set the current Account to theNewAccount,
	 * otherwise it will add theNewAccount to the AccountDatabase.
	 * 
	 * @param theNewAccount The new Account to set.
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	private void setAccount (User theNewAccount) {

	}
	
	
	private void printAccounts () {

	}
	
	
	/**
	 * Sets the new Conference. This checks if theNewConference is a valid Conference within
	 * the ConferenceDatabase. If so, then it will set the current Conference to theNewConference,
	 * otherwise it will add theNewConference to the ConferenceDatabase. *A new Conference should
	 * not be added in any case other than with the Program Chair.
	 * 
	 * @param theNewConference The new Conference to set.
	 * @author Connor Lundberg
	 * @version 5/6/2017
	 */
	public void setConference (Conference theNewConference) {

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
			changeState ((String) arg1);
		} else if (arg1 instanceof Conference) {
			setConference((Conference) arg1);
		}
	}
		
}


























