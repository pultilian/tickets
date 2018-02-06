/**
 * Created by Pultilian on 1/22/2018.
 */
package common;


import client.proxy.ServerProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Method;

public class Command implements ICommand {

    //private Class<?>[] classes;
    private String command;
    private String parametersJSON;
    private String paramTypesJSON;

    public String objectToJSON(Object[] request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Object[] JSONToObjects(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Object[].class);
    }

    public String objectToJSON(String[] request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public String[] JSONToStrings(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, String[].class);
    }

    public Command(Object[] parameters, String[] paramTypes, String command){
        this.parametersJSON = objectToJSON(parameters);
        this.command = command;
        this.paramTypesJSON = objectToJSON(paramTypes);
    }
    
    public void execute(){
        try {
            String[] paramTypes = JSONToStrings(this.paramTypesJSON);
            Object[] parameters = JSONToObjects(this.parametersJSON);
            ServerProxy proxy = new ServerProxy();
            Class<?>[] classes = {parameters[0].toString().getClass()};
            Class<?> receiver = Class.forName("ServerProxy");
            Method method = receiver.getMethod(command, classes);
            parameters[0] = method.invoke(proxy, parameters[0]).toString();
        } catch(Exception e){

        }
    }


}
