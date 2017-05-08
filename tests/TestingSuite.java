package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   BusinessRule1aTest.class,
	   BusinessRule1bTest.class,
	   BusinessRule2aTest.class,
	   BusinessRule2bTest.class
	})

/**
 * JUnit test suite which allows us to run all of our tests at once. 
 * @author James Roberts
 * @version 5/6/2017
 *
 */
public class TestingSuite {

}
