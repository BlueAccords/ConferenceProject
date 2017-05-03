package conference_package;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Allows for the writing of an ArrayList of conferences to a file for storage
 * and the reading of a file to retrieve an ArrayList of conferences.
 * @author James Roberts
 * @version 4/27/2017
 *
 */
public class ConferenceReadWrite {
	
	/**
	 * The destination of the file to read from or write to.
	 */
	private String myFileLocation;
	
	/**
	 * Constructor for ConferenceReadWrite object
	 * @param theLocation destination of the file to read from or write to.
	 */
	public ConferenceReadWrite(String theLocation) {
		myFileLocation = theLocation;
	}
	
	/**
	 * Writes the passed list of conferences to a file for storage and retrieval. 
	 * returns true if write successful, false otherwise. 
	 * @param theConferences List of all conferences.
	 * @return t/f if write successful. 
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public boolean writeConferences(ArrayList<Conference> theConferences) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		//Try to open both streams and write the ArrayList
		try {
			fout = new FileOutputStream(myFileLocation);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(theConferences);
			
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
	 * Reads the ArrayList of Conferences stored in the file destination the object
	 * was initialized with, returns null if the operation failed.
	 * @return The list of stored conferences.
	 * @author James Roberts
	 * @version 4/27/2017
	 */
	public ArrayList<Conference> readConferences() {
		ArrayList<Conference> allConfs = new ArrayList<Conference>();
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		//attempt to open the file and read in the ArrayList
		try {
			fin = new FileInputStream(myFileLocation);
			ois = new ObjectInputStream(fin);
			//This unchecked cast should be ok since we are the ones in control of the system.
			allConfs = (ArrayList<Conference>) ois.readObject();
			
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
		
		return allConfs;
	}
	
	
	
	
}
