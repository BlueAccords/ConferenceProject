/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.Serializable;

/**
 * Representation of a User in the conference program. 
 * @author Vincent Povio, Ayub Tiba, James Roberts, Vinh Le
 * @version 4/30/2017
 *
 */
public class User implements Serializable{

	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 8870025955073752215L;
	
	/** The user's last name. */
	private String myLastName;
	
	/** The user's first name. */
	private String myFirstName;
	
	/** The user's email, used as a unique identifier for them. */
	private String myEmail;
		
	/**
	 * Constructor for a User.
	 * 
	 * @param theEmail the User's current email.
	 * @author Vincent Povio, Ayub Tiba
	 * @version 4/25/2017
	 */
	public User(String theEmail) {
		myEmail = theEmail;		
	}
	
	/**
	 * Getter for User's last name.
	 * 
	 * @return the User's current last name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getLastName() {
		return myLastName;
	}
	
	
	/**
	 * Setter for User's last name. 
	 * 
	 * @param theLastName The new last name to set
	 * @author Ayub Tiba
	 * @version 4/25/2017
	 */
	public void setLastName(String theLastName) {
		this.myLastName = theLastName;
	}
	
	
	/**
	 * Getter for User's first name.
	 * 
	 * @return the first name of the User.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getFirstName() {
		return myFirstName;
	}
	
	
	/**
	 * Setter for User's first name.
	 * 
	 * @param theFirstName The User's first name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void setFirstName(String theFirstName) {
		this.myFirstName = theFirstName;
	}
	
	
	/**
	 * Getter for User's email.
	 * 
	 * @return the User's email address
	 * @author Ayub Tiba
	 * @version 4/25/2017 
	 */
	public String getEmail() {
		return myEmail;
	}
	
	
	/**
	 * Setter for user's email address.
	 * 
	 * @param theEmail the User's new email address
	 * @version 4/25/2017
	 */
	public void setEmail(String theEmail) {
		this.myEmail = theEmail;
	}

	
	
	
	
	
	
	
	

	
	
	
	
	
	
		
		
		
		
	
}
