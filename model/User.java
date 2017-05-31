/*
 * TCSS 360 - Spring 2017
 * Conference Project - Group 2
 * 
 */

package model;

import java.io.*;
import java.util.ArrayList;


/**
 * Representation of a User in the conference program. This holds a static User list that
 * contains all of the Users in the entire program (this is what gets written to and pulled 
 * from persistent storage), a last name, a first name, and an email all held as Strings.
 * 
 * @author Vincent Povio, Ayub Tiba, James Roberts, Vinh Le, Connor Lundberg
 * @version 4/30/2017
 *
 */
public class User implements Serializable{

	private static final String PERSISTENT_DATA_LOCATION = "userData.ser";

	/**  A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 8870025955073752215L;
	
	/**
	 * A static User list holding all users for the system.
	 * Should be initialized on program start and all changes to users should be done
	 * to this list.
	 * Upon program exit this list should be saved to the serialized object.
	 */
	private static ArrayList<User> myUserList;
	
	/** The user's last name. */
	private String myLastName;
	
	/** The user's first name. */
	private String myFirstName;
	
	/** The user's email, used as a unique identifier for them. */
	private String myEmail;
	
	/**
	 * Boolean to indicate if User is Subprogram Chair.
	 */
	private boolean myIsSubprogramChair;
		
	
	/**
	 * Constructor for a User.
	 * 
	 * @param theEmail the User's current email.
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @author Vincent Povio
	 * @version 4/25/2017
	 */
	public void setFirstName(String theFirstName) {
		this.myFirstName = theFirstName;
	}
	
	
	/**
	 * Method to return if this User is a Subprogram Chair.
	 * 
	 * @return myIsSubprogramChair to indicate if this User is a Subprogram Chair.
	 */
	public boolean isSubprogramChair() {
		return myIsSubprogramChair;
	}
	
	
	/**
	 * Getter for User's email.
	 * 
	 * @return the User's email address
	 * 
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
	 * 
	 * @version 4/25/2017
	 */
	public void setEmail(String theEmail) {
		this.myEmail = theEmail;
	}
	
	
	/**
	 * Compares the passed in email to the User class' static user list
	 * returns a true or false depending on if the list contains a user with the given email.
	 * 
	 * Pre:
	 * 	theUsers must be non-null
	 * 	theEmail must be non-null
	 * 	User.initializeUserList() must have been called at program start
	 * 
	 * @param theEmail The email to check against the user list
	 * 
	 * @return a boolean, indicating if the email exists within the list or not
	 */
	public static boolean doesEmailBelongToUser(String theEmail) {
		ArrayList<User> theUsers = myUserList;
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
	 * 
	 * Preconditions
	 * 	Program must have called User.initalizeUserList() on program start
	 * 
	 * @param theUsers The user list to check against to obtain the user object
	 * @param theEmail The email to get the user object by
	 * 
	 * @throws IllegalArgumentException if user with given email is not found
	 * 
	 * @return A User
	 */
	public static User getUserByEmail(String theEmail) {
		ArrayList<User> theUsers = myUserList;
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
	 * Writes the User class' static list of users to a file for storage and retrieval.
	 * returns true if write successful, false otherwise.
	 * 
	 * Pre:
	 * 	Program must have initialized the userlist by calling User.initializeUserList() at program start
	 * 
	 * @return t/f if write successful.
	 * 
	 * @author James Roberts, Ryan Tran
	 * @version 4/27/2017
	 */
	public static boolean writeUsers() {
		ArrayList<User> theUsers = myUserList;

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
	 * 
	 * @return The list of stored Users.
	 * 
	 * @author James Roberts, Ryan Tran
	 * @version 4/28/2017
	 */
	public static ArrayList<User> getUsersFromSerializedObject() {
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
	
	
	/**
	 * This method will return the User class' static user list
	 * 
	 * Pre:
	 * 	User class have its user list initialized prior to this call.
	 * 
	 * @return An arraylist of Users
	 * 
	 * @author Ryan Tran
	 */
	public static ArrayList<User> getUsers() {
		return myUserList;
	}
	
	
	/**
	 * This method will add a user to the current in memory user list.
	 * 
	 * Pre:
	 *	Program must have initialized the User class' static user list before attempting to add a User.
	 * 
	 * @param theUser
	 */
	public static void addUser(User theUser) {
		myUserList.add(theUser);
	}
	
	
	/**
	 * This method will initialize the global user list in memory by deserializing the users
	 * from serializable object. This should be run only once at the beginning of the program.
	 * 
	 * @author Ryan Tran
	 */
	public static void initializeUserListFromSerializableObject() {
		myUserList = User.getUsersFromSerializedObject();
	}
	
	
	/**
	 * Initializes the User class' user list to an empty list.
	 * Note: If you call writeUsers at a later time it will overwrite the locally stored User List.
	 * 
	 * @author Ryan Tran
	 */
	public static void initializeUserListToEmptyList() {
		myUserList = new ArrayList<User>();
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
