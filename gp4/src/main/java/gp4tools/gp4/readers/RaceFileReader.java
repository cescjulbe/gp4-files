package gp4tools.gp4.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaceFileReader {

	/**
	 * @param raceFile
	 * @param flaps
	 * @return
	 * @throws IOException
	 */
	private List<String> readRaceFile(String raceFile) throws IOException {
		BufferedReader brRace = new BufferedReader(new FileReader( raceFile ));
		List<String> raceResults = new ArrayList<String>();
		List<String> driverRaceResults = new ArrayList<String>();
		List<String> teams = new ArrayList<String>();
		try {
		    String line = brRace.readLine();
		    int lineCounter = 0;
		    int interLineCounter = 0;
		    int racePos = 0;
			while (line != null) {
				if(lineCounter>7) {					
					if(interLineCounter%2==0 && line.trim().length()>0) {
						racePos++;
						String driver = line.trim().substring(4, 27);
						String result = line.trim().substring(27);
						driverRaceResults.add(String.valueOf(racePos+","+driver.trim()+","+result.trim()));
					}
					else if(interLineCounter%2!=0) {
						teams.add(line.trim());
					}								
					interLineCounter++;
				}
				lineCounter++;
				line = brRace.readLine();
		    }
			for(int i=0;i<driverRaceResults.size();i++) {
				raceResults.add(driverRaceResults.get(i)+","+teams.get(i));
			}
			return raceResults;
		} finally {
			brRace.close();
		}
	}

	/**
	 * @param flaps
	 * @return
	 * @throws IOException
	 */
	private List<String> readFlapsFile(String flaps) throws IOException {
		BufferedReader brRace = new BufferedReader(new FileReader( flaps ));
		List<String> flapResults = new ArrayList<String>();
		try {
			String line = brRace.readLine();
			int lineCounter = 0;
			int interLineCounter = 0;
			while (line != null) {
				if (lineCounter > 7) {
					if (interLineCounter % 2 == 0 && line.length() > 0) {
						String driver = line.substring(21, 49);
						String result = line.substring(49);
						if (driver.trim().length() > 1)
							flapResults.add(String.valueOf(
									driver.trim() + "," + result.trim().replace("m ", ".").replaceAll("s", "")));
					}
					interLineCounter++;
				}
				lineCounter++;
				line = brRace.readLine();
			}
			return flapResults;
		} finally {
			brRace.close();
		}
	}


	
	public String[][] combinedRaceData(String raceFile, String flapsFile) throws IOException{
		/*	List<Object> result = raceData.stream() 
				   .map(p -> p.split(",")).map(p -> p[0]).collect(Collectors.toList());
		
		*/
		List<String> raceData = readRaceFile(raceFile);
		List<String> flapData = readFlapsFile(flapsFile);
		int rowCounter = 0;
		String[][] raceResults = new String[22][5];
		
		for(String raceRow: raceData) {
			
			String[] singleRowData=raceRow.split(",");
			String driver1=singleRowData[1];
			raceResults[rowCounter][0]=singleRowData[0];
			raceResults[rowCounter][1]=driver1;
			raceResults[rowCounter][2]=singleRowData[3];
			raceResults[rowCounter][3]=singleRowData[2];
			
			for(String flapRow: flapData) {
				String[] singleFlapsRow=flapRow.split(",");
				String driver2=singleFlapsRow[0];
				if(driver2.equals(driver1)) {
					if(singleFlapsRow.length>1)
						raceResults[rowCounter][4]=singleFlapsRow[1];
					break;
				}
			}
			rowCounter++;
		}
		return raceResults;
		
	}
}
