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
			
			raceResults = rr.combinedRaceData("resources/race1.txt", "resources/flaps1.txt");
			assertEquals("21m 49.689s",raceResults[0][3]);
			assertEquals("+49.973s",raceResults[10][3]);
			assertEquals("Pedro De La Rosa",raceResults[9][1]);
			assertEquals("1.49.851",raceResults[9][4]);
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
