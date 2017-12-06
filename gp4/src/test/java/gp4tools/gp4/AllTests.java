package gp4tools.gp4;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestQualiParser.class);
		suite.addTestSuite(TestRaceParser.class);
		//$JUnit-END$
		return suite;
	}

}
