package utils;

import commands.Command;
import components.Request;

public class RequestMachine {
    public static Request addCommandToRequest(Request request, Command command){
        request.setCommand(command);
        return request;
    }
}
