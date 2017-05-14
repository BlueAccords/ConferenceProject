package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   AuthorTest.class,
	   UserTest.class,
	   ManuscriptTest.class
	})

/**
 * JUnit test suite which allows us to run all of our tests at once. 
 * @author James Roberts
 * @version 5/6/2017
 *
 */
public class TestingSuite {

}
