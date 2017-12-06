package gp4tools.gp4.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * This file parses the qualification file from
 * GP4 output, filling a 22x6 string, with useful contents
 * @author root
 *
 */
public class QualiFileReader {
	
	private static final String[] KEYWORDS = {"Pole", "Row"};
	
	/**
	 * From a GP4 quali file, parses its contents returning 
	 * a proper structure for DB ingestion
	 * 
	 * @throws IOException
	 */
	public String[][] readQualFile(String qualiFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(qualiFile));
		
		try {
			
		    String line = br.readLine();
		    List<String> names = new ArrayList<String>();
		    List<String> times = new ArrayList<String>();
		    List<Float> floatTimes = new ArrayList<Float>();
		    String[][] parsedFile = new String[22][6];
		    String minute = null;
		    String seconds = null;
		    boolean hasTime = false;
		    int lineCounter=0;
		    
		    // Each line starting...
			while (line != null) {
				
				String[] tokens = line.trim().split(" ");
				
				for (String str : tokens) {
					if (!str.equals("")) {
						// Checking times
						if (str.matches("1m")) {
							minute = str.replace("m", ".");
							hasTime = true;
						}
						if (str.matches("..\\....s")) {
							seconds = str.replace("s", "");
						}
						if (lineCounter > 7) {							
							if (	str.matches("[a-zA-Z]{1,}") && 
									!str.equals(KEYWORDS[0]) 	&& 
									!str.equals(KEYWORDS[1])) 	{								
								names.add(str);
							}
						}
					}
				}
				if (hasTime) {
					times.add(minute + seconds);
					hasTime=false;
					floatTimes.add(Float.parseFloat(minute)*60 + Float.parseFloat(seconds));
				}
		        line = br.readLine();
		        lineCounter++;
		    }
			
			int iterCounter =0;
			for (int i=0; i<names.size(); i=i+2) {
				parsedFile[iterCounter][0]=String.valueOf(iterCounter+1);
				parsedFile[iterCounter][1]=names.get(i)+ " " + names.get(i+1);
				parsedFile[iterCounter][2]=times.get(iterCounter);
				parsedFile[iterCounter][3]=String.valueOf(floatTimes.get(iterCounter));
				iterCounter++;
			}
			return getParsedData(parsedFile);

		} finally {
		    br.close();
		}
	}

	/**
	 * This method reads the parsed data form the file,
	 * filling only the differences field, with the first one
	 * and with the previous one.
	 * 
	 * @param data
	 * @return
	 */
	public String[][] getParsedData(String[][] data){
		float bestTime = Float.parseFloat(data[0][3]);
		int rowCounter = 0;
		float lastTime = 0;
		for(String[] row: data) {
			float diffToFirst = Float.parseFloat(row[3]) - bestTime;
			float diffToPrevious = 0;
			
			if(rowCounter == 0) {
				diffToPrevious = diffToFirst;				
			}
			else {
				diffToPrevious = Float.parseFloat(row[3]) - lastTime;
			}
			row[4]=String.valueOf(diffToFirst);
			row[5]=String.valueOf(diffToPrevious);
			lastTime = Float.parseFloat(row[3]);
			rowCounter++;
		}
		return data;
	}

}
