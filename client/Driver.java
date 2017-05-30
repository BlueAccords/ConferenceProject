package client;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JPanel;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Manuscript.AuthorExistsInListException;
import model.Reviewer;
import model.SubprogramChair;
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
			TestDataGenerator.generateMasterTestData(true);
		} else {
			User.initializeUserListFromSerializableObject();
			Conference.initializeConferenceListFromSerializableObject();
		}
		systemController.startProgram();
	}
	
}
