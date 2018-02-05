/**
 * Created by Pultilian on 1/22/2018.
 */
package common;


public class Command implements ICommand {

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getString(){
        return parameters[0].toString();
    }

    //private Class<?>[] classes;
    private String command;
    private Object[] parameters;

    public Command(Object[] parameters, String command){
        this.parameters = parameters;
        //this.classes = classes;
        this.command = command;
    }

    public void execute(){

    }


}
