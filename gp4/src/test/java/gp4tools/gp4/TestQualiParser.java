package gp4tools.gp4;

import java.io.IOException;

import gp4tools.gp4.readers.QualiFileReader;
import junit.framework.TestCase;

public class TestQualiParser extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testReadQualFile() {
		QualiFileReader qfr= new QualiFileReader();
		try {
			String[][] qualiData=qfr.readQualFile("resources/quali.txt");
			assertEquals("1.27.618", qualiData[0][2]);
			assertEquals("1", qualiData[0][0]);
			assertEquals("Lewis Hamilton", qualiData[0][1]);
			
			qualiData=qfr.readQualFile("resources/pole1.txt");
			assertEquals("1.29.236", qualiData[3][2]);
			assertEquals("12", qualiData[11][0]);
			assertEquals("Tiago Monteiro", qualiData[15][1]);
			assertEquals("Pedro De La Rosa", qualiData[17][1]);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
