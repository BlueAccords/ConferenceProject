/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Reviewer implements Serializable{
	
	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 7044360208215202991L;

	/** The User associated with this reviewer. */
	User myUser;
	
	/** Collection of any manuscript the user has been assigned to review. */
	private ArrayList<Manuscript> assignedManuscripts;
	
	/**
	 * hashmap containing the reviewers assigned manuscripts plus the score they reviewed it.
	 */
	private HashMap<Manuscript, Integer> myScoreMap;
	
	/**
	 * Constructor for the Reviewer.
	 * @param aUser The User associated with the Reviewer.
	 */
	public Reviewer(User aUser) {
		//no defensive copy made, we want this to act as a pointer.
		myUser = aUser;
		assignedManuscripts = new ArrayList<Manuscript>();
		myScoreMap = new HashMap<Manuscript, Integer>();
	}
	
	/**
	 * Constructor for the Reviewer for testing.
	 * @param aUser The User associated with the Reviewer.
	 * @param theScoreMap The map of manuscript and their scores this reviewer has submitted.
	 */
	public Reviewer(User aUser, HashMap<Manuscript, Integer> theScoreMap) {
		//no defensive copy made, we want this to act as a pointer.
		myUser = aUser;
		assignedManuscripts = new ArrayList<Manuscript>();
		myScoreMap = theScoreMap;
	}
	
	/**
	 * Getter for User type.
	 * @return the User associated with the Reviewer.
	 */
	public User getUser() {
		return myUser;
	}
	
	/**
	 * This method will return the assigned manuscripts.
	 * @return the collection of Manuscripts's assigned to the User to review
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public ArrayList<Manuscript> getAssignedManuscripts() {
		ArrayList<Manuscript> reviewManuscriptsCopy = new ArrayList<Manuscript>();
		reviewManuscriptsCopy.addAll(assignedManuscripts);
		return assignedManuscripts;
	}
	
	/**
	 * Adds the passed Manuscript to the User's collection of Manuscripts
	 * they have been assigned to review
	 * @param theManuscript
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void addManuscriptToReviewer(Manuscript theManuscript) {
		assignedManuscripts.add(theManuscript);
	}
	
	/**
	 * Removes the passed Manuscript from the User's collection
	 * of Manuscripts to review if it exists in the collection.
	 * @param theManuscript the Paper to be removed
	 * @author Ayub Tiba
	 * @version 4/29/2017
	 */
	public void removeManuscriptFromReviwer(Manuscript theManuscript) {
		for (Manuscript target: assignedManuscripts) {
			if (target == theManuscript) {
				assignedManuscripts.remove(target);
			}
		}
	}
	
	/**
	 * Method to get the score the reviewer has set for theManuscript and -1 if none set yet.
	 * @param theManuscript the manuscript to get the score of.
	 * @return The score this reviewer has set for theManuscript or -1 if none set yet.
	 * @author Casey Anderson
	 */
	public int getReviewerScore(Manuscript theManuscript) {
		
		int score = -1;
		
		if (myScoreMap.containsKey(theManuscript)) {
			score = myScoreMap.get(theManuscript);
		}
		
		return score;
		
	}
	
	/**
	 * Method to set theScore to theManuscript for this reviewer.
	 * @param theManuscript the manuscript to set theScore of.
	 * @param theScore the score to set to theManuscript.
	 * @author Casey Anderson
	 */
	public void setReviewerScore(Manuscript theManuscript, int theScore) {
		if (myScoreMap.containsKey(theManuscript)) {
			myScoreMap.replace(theManuscript, theScore);
		} else {
			myScoreMap.put(theManuscript, theScore);
		}
	}
	
	/**
	 * This method will return the number of assigned manuscripts.
	 * @return the number of assigned manuscripts
	 */
	public int getNumAssignedManuscripts() {
		return assignedManuscripts.size();
	}
}
