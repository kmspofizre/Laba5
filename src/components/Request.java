package components;

import commands.Command;

public class Request {
    private String args;
    private Command command;
    public Request(String args){
        this.args = args;
    }

    public String getArgs() {
        return args;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
