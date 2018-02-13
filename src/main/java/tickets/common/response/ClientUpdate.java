package tickets.common.response;

import tickets.common.Command;

import java.util.Queue;

public class ClientUpdate extends Response{

    private Queue<Command> commands;
    private String lastCommandID;

    public ClientUpdate(Exception e){ super(e); }

    public ClientUpdate(Queue<Command> commands, String lastCommandID){
        this.commands = commands;
        this.lastCommandID = lastCommandID;
    }

    public Queue<Command> getCommands() { return commands; }
    public String getLastCommandID(){ return lastCommandID; }
}
