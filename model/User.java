/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Representation of a User in the conference program. 
 * @author Vincent Povio, Ayub Tiba, James Roberts, Vinh Le
 * @version 4/30/2017
 *
 */
public class User implements Serializable{

	private static final String PERSISTENT_DATA_LOCATION = "./persistent_storage_folder/userData.ser";

	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 8870025955073752215L;
	
	/** The user's last name. */
	private String myLastName;
	
	/** The user's first name. */
	private String myFirstName;
	
	/** The user's email, used as a unique identifier for them. */
	private String myEmail;
	
	private boolean myIsSubprogramChair;
		
	/**
	 * Constructor for a User.
	 * 
	 * @param theEmail the User's current email.
	 * @author Vincent Povio, Ayub Tiba
	 * @version 4/25/2017
	 */
	public User(String theEmail) {
		this(theEmail, false);	
	}
	
	/**
	 * Constructor that sets the Subprogram Chair boolean too.
	 * 
	 * @param theEmail The email attached to this User
	 * @param theIsSubprogramChair The boolean showing whether this User is registered as a Subprogram Chair
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public User(String theEmail, boolean theIsSubprogramChair) {
		myEmail = theEmail;
		myIsSubprogramChair = theIsSubprogramChair;
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
	 * Getter for the User's whole name.
	 * 
	 * @return the User's current whole name (first name + last name)
	 * 
	 * @author Connor Lundberg
	 * @version 5/25/2017
	 */
	public String getWholeName() {
		return myFirstName + " " + myLastName;
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
	
	
	public boolean isSubprogramChair() {
		return myIsSubprogramChair;
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
	
	/**
	 * Compares the passed in email to the passed in list of users and
	 * returns a true or false depending on if the list contains a user with the given email.
	 * PreConditions:
	 * 	theUsers must be non-null
	 * 	theEmail must be non-null
	 * 
	 * @param theUsers The user list to check for the given email
	 * @param theEmail The email to check against the user list
	 * @return a boolean, indicating if the email exists within the list or not
	 */
	public static boolean doesEmailBelongToUser(ArrayList<User> theUsers, String theEmail) {
		boolean userExists = false;
		
		for(User aUser : theUsers) {
			if(aUser.getEmail().equals(theEmail)) {
				userExists = true;
			}
		}
		
		return userExists;
	}
	
	/**
	 * Returns the given user by passed in email.
	 * @param theUsers The user list to check against to obtain the user object
	 * @param theEmail The email to get the user object by
	 * @throws IllegalArgumentException if user with given email is not found
	 * @return A User
	 */
	public static User getUserByEmail(ArrayList<User> theUsers, String theEmail) {
		User userToReturn = null;

		for(User aUser : theUsers) {
			if(aUser.getEmail().equals(theEmail)) {
				userToReturn = aUser;
			}
		}
		
		if(userToReturn == null) {
			throw new IllegalArgumentException("No user with given email exists with user list");
		}
		
		
		return userToReturn;
	}


	/**
	 * Writes the passed list of users to a file for storage and retrieval.
	 * returns true if write successful, false otherwise.
	 * @param theUsers List of all Users.
	 * @return t/f if write successful.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public static boolean writeUsers(ArrayList<User> theUsers) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		//Try to open both streams and write the ArrayList
		try {
			fout = new FileOutputStream(PERSISTENT_DATA_LOCATION);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(theUsers);

		} catch (Exception e) {
			e.printStackTrace(); //Maybe remove this in final version?
			return false;
		} finally {
			// Close both streams, return false if there is an issue.
			if(fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
					return false;
				}
			}

			if(oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Reads the ArrayList of Users stored in the file destination the object
	 * was initialized with, returns null if the operation failed.
	 * @return The list of stored Users.
	 * @author James Roberts
	 * @version 4/28/2017
	 */
	public static ArrayList<User> getUsers() {
		ArrayList<User> allUsers = new ArrayList<User>();
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		//attempt to open the file and read in the ArrayList
		try {
			fin = new FileInputStream(PERSISTENT_DATA_LOCATION);
			ois = new ObjectInputStream(fin);
			//This unchecked cast should be ok since we are the ones in control of the system.
			allUsers = (ArrayList<User>) ois.readObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//close both streams
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					// need to do something here to indicate failure?
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					// need to do something here to indicate failure?
				}
			}
		}

		return allUsers;
	}

	@Override
	public boolean equals(Object o){
		User comparingUser = (User) o;
		if(comparingUser.getEmail().equals(this.getEmail())){
			return true;
		} else {
			return false;
		}
	}
}
