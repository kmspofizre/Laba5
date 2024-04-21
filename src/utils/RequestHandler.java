package utils;

import collections.CSVDataBase;
import commands.Command;
import commands.DataBaseCommand;
import commands.ExecuteScriptCommand;
import components.Request;
import components.Response;

import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    public static List<Response> handleRequests(List<Request> requestList, CSVDataBase csvDataBase){
        List<Response> responses = new ArrayList<>();
        for (Request request : requestList){
            Command command = request.getCommand();
            if ((DataBaseCommand.class.isAssignableFrom(command.getClass()))){
                responses.add(command.execute(request.getArgs(), csvDataBase, false));
            }
            else {
                responses.add(command.execute(request.getArgs(), csvDataBase, false));
            }
        }
        return responses;
    }
}