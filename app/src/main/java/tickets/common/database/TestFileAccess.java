package tickets.common.database;

import java.io.IOException;
import java.util.List;

public class TestFileAccess {
	
	public static void main(String[] args) {
		FileAccess access = new FileAccess();
		
		String object = "Time to party";
		String type = "String";
		String id = "0000001";
		try {
			access.checkpointUpdate(object, type, id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String delta = "This is a delta";
		try {
			access.addUpdate(delta, type, id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			List<String> objects = access.getAllObjects(type);
			for (String obj : objects) {
				System.out.println(obj);
				List<String> deltas = access.getDeltasForObject(type, id);
				for (String del : deltas) {
					System.out.println("  " + del);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		object = "Party time is over";
		try {
			access.checkpointUpdate(object, type, id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			List<String> objects = access.getAllObjects(type);
			for (String obj : objects) {
				System.out.println(obj);
				List<String> deltas = access.getDeltasForObject(type, id);
				for (String del : deltas) {
					System.out.println("  " + del);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			access.removeAllFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
