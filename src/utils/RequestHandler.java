package utils;

import collections.CSVDataBase;
import commands.Command;
import commands.DataBaseCommand;
import commands.ExecuteScriptCommand;
import components.*;

import java.nio.channels.SocketChannel;
import java.util.*;

public class RequestHandler {
    public static FinalResponse handleRequests(List<Request> requestList,
                                                CSVDataBase csvDataBase,
                                                Map.Entry<Command, TreeMap<Long, City>> lastAction){
        List<Response> responses = new ArrayList<>();
        FinalResponse finalResponse = new FinalResponse("Final Response");
        finalResponse.setContainsReversible(false);
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
            else if (command instanceof Reversible){
                if ((DataBaseCommand.class.isAssignableFrom(command.getClass()))) {
                    DataBaseResponse dataBaseResponse = (DataBaseResponse) ((DataBaseCommand) command).execute(request.getArgs(),
                            ((CityRequest) request).getCity(), csvDataBase, false);
                    String additionString;
                    additionString = command.getCommandName() + " " + String.join(" ", request.getArgs());
                    DataBaseResponse.addCommandToResponse(additionString);
                    responses.add(dataBaseResponse);
                    finalResponse.setContainsReversible(true);
                    Map.Entry<Command, TreeMap<Long, City>> entry = new AbstractMap.SimpleEntry<>(command, dataBaseResponse.getDeletedPart());
                    finalResponse.setLastAction(entry);
                }
                else {
                    DataBaseResponse dataBaseResponse = (DataBaseResponse) command.execute(request.getArgs(),
                            csvDataBase, false);
                    responses.add(dataBaseResponse);
                    finalResponse.setContainsReversible(true);
                    Map.Entry<Command, TreeMap<Long, City>> entry = new AbstractMap.SimpleEntry<>(command, dataBaseResponse.getDeletedPart());
                    finalResponse.setLastAction(entry);
                }
                canUndo = true;

            }
            else {
                responses.add(command.execute(request.getArgs(), csvDataBase, false));
            }
        }

        return finalResponse;
    }
}
