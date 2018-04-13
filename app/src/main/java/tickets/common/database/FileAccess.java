package tickets.common.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccess {
	private static final String OUTFOLDER = "files/";
	
	public void checkpointUpdate(String object, String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		Path file = Paths.get(filename);
		List<String> content = Arrays.asList();
		Files.write(file, content, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
	}
	
	public void removeFile(String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		Path file = Paths.get(filename);
		Files.delete(file);
	}
	
	public void addUpdate(String command, String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		Path file = Paths.get(filename);
		List<String> content = Arrays.asList(command);
		Files.write(file, content, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
	}
	
	public List<String> getDeltasForObject(String type, String id) throws IOException {
		String filename = OUTFOLDER + type + id;
		Path file = Paths.get(filename);
		List<String> lines = Files.readAllLines(file);
		// Start at one to skip object json
		lines.remove(0);
		return lines;
	}
	
	public List<String> getAllObjects(String type) throws IOException {
		List<String> objects = new ArrayList<>();
		File folder = new File(OUTFOLDER);
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.getName().contains(type)) {
				Path theFile = Paths.get(OUTFOLDER + file.getName());
				List<String> lines = Files.readAllLines(theFile);
				objects.add(lines.get(0));
			}
		}
		return objects;
	}
	
	public void removeAllFiles() throws IOException {
		File folder = new File(OUTFOLDER);
		File[] files = folder.listFiles();
		for (File file : files) {
			String filename = OUTFOLDER + file.getName();
			Path theFile = Paths.get(filename);
			Files.delete(theFile);
		}
	}
}
