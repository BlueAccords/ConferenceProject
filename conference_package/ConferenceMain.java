package conference_package;

import java.util.ArrayList;
import java.util.Date;

public class ConferenceMain {

	public static void main(String[] args) {
		//ConferenceReadWriteTest();
	}
	
	//This is an example of reading and writing the list of conferences
	//Will need to be used to store and retrieve our user's and conferences in the next version of the program
	private static void ConferenceReadWriteTest() {
		//Make ArrayList of 3 conference objects to test 
		Date d = new Date();
		Conference c1 = new Conference("firstC", d,d,d,d);
		c1.addPaper(new Paper("a paper", "stuff", "more stuff"));
		Conference c2 = new Conference("SecondC", d,d,d,d);
		Conference c3 = new Conference("ThirdC", d,d,d,d);
		ArrayList<Conference> firstC = new ArrayList<Conference>();
		firstC.add(c1);
		firstC.add(c2);
		firstC.add(c3);
		//Make a ConfereneReadWrite object (parameter is the file path)
		ConferenceReadWrite crw = new ConferenceReadWrite("./data.ser");
		
		crw.writeConferences(firstC);
		
		ArrayList<Conference> confs = new ArrayList<Conference>();
		confs = crw.readConferences();
		System.out.println(confs.size());
		for(int i = 0; i < confs.size(); i++) {
			System.out.println(confs.get(i).getConferenceName());
		}
		
	}
}
