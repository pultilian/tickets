package tickets.common.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {
	private static final String OUTFOLDER = System.getProperty("user.dir") + "/files/";
	
	public void checkpointUpdate(String object, String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(object);
	    writer.write("\n");
	    writer.close();
	}
	
	public void removeFile(String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		File file = new File(filename);
		file.delete();
	}
	
	public void addUpdate(String command, String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
	    writer.append(command);
	    writer.append("\n");
	    writer.close();
	}
	
	public List<String> getDeltasForObject(String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		List<String> lines = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
	    String line = br.readLine();
	    while (line != null) {
	    	lines.add(line);
	    	line = br.readLine();
	    }
	    br.close();
		// Start at one to skip object json
		lines.remove(0);
		return lines;
	}
	
	public List<String> getAllObjects(String type) throws IOException {
		List<String> objects = new ArrayList<>();
		File folder = new File(OUTFOLDER);
		File[] files = folder.listFiles();
		for (File file : files) {
			if(file.getName().startsWith(type)) {
				BufferedReader br = new BufferedReader(new FileReader(OUTFOLDER + file.getName()));

				long length = 0;
				String retVal = "";
				String line;
				while ((line = br.readLine()) != null) {
					retVal += line;
				}
				objects.add(retVal);
				br.close();
			} else {
				continue;
			}
		}
		return objects;
	}
	
	public void removeAllFiles() throws IOException {
		File folder = new File(OUTFOLDER);
		File[] files = folder.listFiles();
		for (File file : files) {
			file.delete();
		}
	}
}
