package common;

import com.google.gson.Gson;

public class ResultTransferObject {

	private String typeName;
	private String jsonString;
	
	private static final Gson gson = new Gson();

	public ResultTransferObject(String typeName, Object object) {
		this.typeName = typeName;
		this.jsonString = gson.toJson(object);
	}

	public Object getResult() {
		Class<?> objectClass = null;
		try {
			objectClass = Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: could not find class " + typeName);
			e.printStackTrace();
		}
		Object result = gson.fromJson(jsonString, objectClass);
		return result;
	}

}
