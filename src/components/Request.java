package components;

import commands.Command;

public class Request {
    protected String args;
    protected Command command;
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
    @Override
    public String toString(){
        return this.command.toString() + "\n" + this.args;
    }
}
