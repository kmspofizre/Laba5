package components;

import commands.Command;

import java.io.Serializable;

public class UserRequest extends Request implements Serializable {
    protected String[] args;
    protected Command command;
    private User user;
    public UserRequest(String[] args){
        super(args);
    }
}
