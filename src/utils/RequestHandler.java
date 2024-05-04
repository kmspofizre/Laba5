package utils;

import collections.CSVDataBase;
import commands.Command;
import commands.DataBaseCommand;
import commands.ExecuteScriptCommand;
import components.*;

import java.nio.channels.SocketChannel;
import java.util.*;

public class RequestHandler {
    public static List<Response> handleRequests(List<Request> requestList,
                                                CSVDataBase csvDataBase,
                                                Map.Entry<Command, TreeMap<Long, City>> lastAction){
        List<Response> responses = new ArrayList<>();
        boolean canUndo = true;
        for (Request request : requestList){
            Command command = request.getCommand();
            if (Objects.equals(command.getCommandName(), "undo")){
                if (lastAction == null){
                    responses.add(new Response("Вы не вносили изменений в рамках этой сессии"));
                }
                else {
                    if (canUndo){
                        canUndo = false;
                        ((Reversible) lastAction.getKey()).undo(lastAction.getValue(), csvDataBase);
                    }
                }
            }
            else if ((DataBaseCommand.class.isAssignableFrom(command.getClass()))){
                Response response = ((DataBaseCommand) command).execute(request.getArgs(),
                        ((CityRequest) request).getCity(), csvDataBase, false);
                String additionString;
                additionString = command.getCommandName() + " " + String.join(" ", request.getArgs());
                response.addCommandToResponse(additionString);
                responses.add(response);
                canUndo = true;
            }
            else {
                responses.add(command.execute(request.getArgs(), csvDataBase, false));
            }
        }
        return responses;
    }
}
