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

import com.google.gson.Gson;

import tickets.common.Command;

public class FileAccess {
	private static final String OUTFOLDER = "files/";
	private static Gson gson = new Gson();
	
	private String createJsonString(Object o) {
		return gson.toJson(o);
	}

	private Object createObjectFromJsonString(String json, Class<?> type) {
		return gson.fromJson(json, type);
	}
	
	public <T> void checkpointUpdate(T o, String id) throws IOException {
		String filename = OUTFOLDER + o.getClass().getName() + id;
		Path file = Paths.get(filename);
		List<String> content = Arrays.asList(createJsonString(o));
		Files.write(file, content, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
	}
	
	public void removeFile(Class<?> type, String id) throws IOException {
		String filename = OUTFOLDER + type.getName() + id;
		Path file = Paths.get(filename);
		Files.delete(file);
	}
	
	public void addUpdate(Command c, Class<?> type, String id) throws IOException {
		String filename = OUTFOLDER + type.getName() + id;
		Path file = Paths.get(filename);
		List<String> content = Arrays.asList(createJsonString(c));
		Files.write(file, content, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
	}
	
	public List<Command> getDeltasForObject(Class<?> type, String id) throws IOException {
		String filename = OUTFOLDER + type.getName() + id;
		Path file = Paths.get(filename);
		List<String> lines = Files.readAllLines(file);
		List<Command> commands = new ArrayList<>();
		// Start at one to skip object json
		for (int i = 1; i < lines.size(); i++) {
			commands.add((Command) createObjectFromJsonString(lines.get(i), Command.class));
		}
		return commands;
	}
	
	public <T> List<T> getAllObjects(Class T) throws IOException {
		List<T> objects = new ArrayList<>();
		File folder = new File(OUTFOLDER);
		File[] files = folder.listFiles();
		String fileType = T.getName();
		for (File file : files) {
			if (file.getName().contains(fileType)) {
				Path theFile = Paths.get(OUTFOLDER + file.getName());
				List<String> lines = Files.readAllLines(theFile);
				objects.add((T) createObjectFromJsonString(lines.get(0), T));
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
