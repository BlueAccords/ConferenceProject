package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import model.Conference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.Controller;
import model.Author;
import model.Manuscript;
import model.User;

/**
 * Tests for the Controller class. This will encompass the different business rules
 * that the Controller handles, along with just general testing for state switching.
 *
 * @author Connor Lundberg
 * @version 5/15/2017
 */
public class ControllerTest {

    ArrayList<Conference> conferenceList;
    Conference RSAConference;
    Conference TesselationsConference;


    @Before
    public void setUp() throws Exception {
        RSAConference = new Conference("RSA", new Date(), new Date(), new Date(), new Date());
        TesselationsConference = new Conference("Tesselations", new Date(), new Date(), new Date(), new Date());
        conferenceList = Conference.readConferences();
    }


    @Test
    public void readConferences_newConference_true(){

    }

    @Test
    public void readConferences_noNewConference_true(){

    }


    @Test
    public void writeConferences_newConference_true(){

    }

    @Test
    public void writeConferences_noNewConference_true(){

    }
}
