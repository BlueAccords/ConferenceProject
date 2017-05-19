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
			//e.printStackTrace();
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
}
