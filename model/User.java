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
	private String lastName;
	
	/** The user's first name. */
	private String firstName;
	
	/** The user's email, used as a unique identifier for them. */
	private String email;
		
	/**
	 * Constructor for a User.
	 * @param theEmail the user's email.
	 * @author Vincent Povio, Ayub Tiba
	 * @version 4/25/2017
	 */
	public User(String theEmail) {
		email = theEmail;		
	}
	
	/**
	 * Getter for user' last name.
	 * @return the User's last name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for User's last name. 
	 * @param lastName
	 * @author Ayub Tiba
	 * @version 4/25/2017
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for user' first name.
	 * @return the first name of the User.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for User's first name.
	 * @param firstName The User's first name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * getter for User's emails.
	 * @return the User's email address
	 * @author Ayub Tiba
	 * @version 4/25/2017 
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for user's email address
	 * @param email the User's email address
	 * @version 4/25/2017
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	
	
	
	
	

	
	
	
	
	
	
		
		
		
		
	
}
