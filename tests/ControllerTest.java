package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Controller;
import client.ParentFrameView;
import model.Author;
import model.Manuscript;
import model.User;

/**
 * Tests for the Controller class. This will encompass the different business rules
 * that the Controller handles, along with just general testing for state switching.
 * 
 * @author Connor Lundberg
 * @version 5/27/2017
 */
public class ControllerTest {

	Controller myTestController;
	User myTestUser;
	ParentFrameView myParentFrameView;
	
	@Before
	public void setUp() throws Exception {
		myTestController = new Controller();
		myTestUser = new User("Luke Skywalker");
		myParentFrameView = new ParentFrameView("MSEE Conference Program", 1280, 720);
		
		myParentFrameView.addObserver(myTestController);
	}
	
	@After
	public void tearDown() throws Exception {
		myParentFrameView.deleteObservers();
	}

	
	
	@Test
	public void 
}
