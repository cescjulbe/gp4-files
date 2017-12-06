package gp4tools.gp4;

import java.io.IOException;

import gp4tools.gp4.readers.RaceFileReader;
import junit.framework.TestCase;

public class TestRaceParser extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testReadQualFile() {
		RaceFileReader rr= new RaceFileReader();
		try {
			String[][] raceResults = rr.combinedRaceData("resources/race.txt", "resources/flaps.txt");
			assertEquals("1.29.911",raceResults[2][4]);
			assertEquals("-2 Laps",raceResults[8][3]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
