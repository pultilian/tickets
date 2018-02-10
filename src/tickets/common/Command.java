package tickets.common;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.gson.Gson;

import server.ServerFacade;

public class Command {

	private String methodName;
	private String[] parameterTypeNames;
	private Object[] parameters = null;
	private String[] parametersAsJsonStrings;
	private Class<?>[] parameterTypes;

	private static Gson gson = new Gson();

	public Command(String methodName, String[] parameterTypeNames, Object[] parameters) {
		this.methodName = methodName;
		this.parameterTypeNames = parameterTypeNames;
		createJsonStringsFromParameters(parameters);
	}

	public Command(InputStreamReader reader) {
		Command temp = (Command)gson.fromJson(reader, Command.class);
		methodName = temp.getMethodName();
		parameterTypeNames = temp.getParameterTypeNames();
		parametersAsJsonStrings = temp.getParametersAsJsonStrings();
		createParameterTypes();
		createParametersFromJsonStrings();
	}

	public String getMethodName() {
		return methodName;
	}

	public String[] getParameterTypeNames() {
		return parameterTypeNames;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public String[] getParametersAsJsonStrings() {
		return parametersAsJsonStrings;
	}

	private void createJsonStringsFromParameters(Object[] parameters) {
		parametersAsJsonStrings = new String[parameters.length];
		for(int i = 0; i < parameters.length; i++) {
			parametersAsJsonStrings[i] = gson.toJson(parameters[i]);
		}
	}

	private void createParametersFromJsonStrings() {
		parameters = new Object[parametersAsJsonStrings.length];
		for(int i = 0; i < parametersAsJsonStrings.length; i++) {
			parameters[i] = gson.fromJson(parametersAsJsonStrings[i], parameterTypes[i]);
		}
	}
	
	private void createParameterTypes() {
		parameterTypes = new Class<?>[parameterTypeNames.length];
		for(int i = 0; i < parameterTypeNames.length; i++) {
			try {
				parameterTypes[i] = getClassFor(parameterTypeNames[i]);
			} catch (ClassNotFoundException e) {
				System.err.println("ERROR: unrecognized type name " + parameterTypeNames[i]);
				e.printStackTrace();

			}
		}
	}


	private static final Class<?> getClassFor(String className) throws ClassNotFoundException {
		Class<?> result = null;
		switch (className) {
			case "boolean":
				result = boolean.class; break;
			case "byte":
				result = byte.class; break;
			case "char":
				result = char.class; break;
			case "double":
				result = double.class; break;
			case "float":
				result = float.class; break;
			case "int":
				result = int.class; break;
			case "long":
				result = long.class; break;
			case "short":
				result = short.class; break;
			default:
				result = Class.forName(className);
		}
		return result;
	}

	public Object execute() {
		Object result = null;
		try {
			Method method = ServerFacade.class.getMethod(methodName, parameterTypes);
			result = method.invoke(ServerFacade.getInstance(), parameters);
		} catch (NoSuchMethodException e) {
			System.err.println("ERROR: no method " + methodName + "exists");
			e.printStackTrace();
		} catch (NumberFormatException | InvocationTargetException e) {
			result = "NUMBERFORMATERROR";
			System.err.println("ERROR: number format error");
		} catch (IllegalAccessException e) {
			System.err.println("ERROR: illegal access");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.err.println("ERROR: security error");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("ERROR: illegal argument");
			e.printStackTrace();
		}	
		return result;
	}
	
}
