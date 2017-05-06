package conference_package;

import java.io.Serializable;

/**
 * Representation of a User in the conference program. 
 * @author Vincent Povio, Ayub Tiba, James Roberts, Vinh Le
 * @version 4/30/2017
 *
 */
public class User implements Serializable{

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 8870025955073752215L;
	/**
	 * The user's last name.
	 */
	private String lastName;
	/**
	 * The user's first name. 
	 */
	private String firstName;
	/**
	 * The user's email, used as a unique identifier for them. 
	 */
	private String email;
		
	//This constructor should maybe take the first name & last name so the
	//view component or a createAccount class can get the user info and just make a new
	//User object
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
	 * 
	 * @return the User's last name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Set's the User's last name. 
	 * @param lastName
	 * @author Ayub Tiba
	 * @version 4/25/2017
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return the first name of the User.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the User's first name.
	 * @param firstName The User's first name.
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return the User's email address
	 * @author Ayub Tiba
	 * @version 4/25/2017 
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the user's email address
	 * @param email the User's email address
	 * @version 4/25/2017
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	
	
	
	
	

	
	
	
	
	
	
		
		
		
		
	
}
