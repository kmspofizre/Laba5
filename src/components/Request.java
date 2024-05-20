package components;

import commands.Command;

import java.io.Serializable;

public class Request implements Serializable {
    protected String[] args;
    protected Command command;
    private User user;
    private static final long serialVersionUID = 777777L;
    public Request(String[] args){
        this.args = args;
    }

    public String[] getArgs() {
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
