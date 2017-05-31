package client;

import model.Conference;
import model.User;
import utility.TestDataGenerator;


public class Driver {
	/**
	 * If debug is set to true, will init all data inside of debug if statement.
	 * If false will init data from stored data in serialized object
	 */
	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		Controller systemController = new Controller();
		
		// add dummy users
		if(DEBUG) {
			TestDataGenerator.generateMasterTestData(false);
		} else {
			User.initializeUserListFromSerializableObject();
			Conference.initializeConferenceListFromSerializableObject();
		}
		systemController.startProgram();
	}
	
}
