package utils;


import collections.PostgresDataBase;
import commands.*;
import components.*;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.*;

public class RequestHandler {
    public static FinalResponse handleRequests(List<Request> requestList,
                                                PostgresDataBase csvDataBase,
                                                Map.Entry<Command, TreeMap<Long, City>> lastAction) throws SQLException {
        List<Response> responses = new ArrayList<>();
        FinalResponse finalResponse = new FinalResponse("Final Response");
        finalResponse.setContainsReversible(false);
        boolean canUndo = true;
        boolean isLastUndo = false;
        for (Request request : requestList){
            User user = request.getUser();
            Command command = request.getCommand();
            if (Objects.equals(command.getCommandName(), "undo")){
                if (lastAction == null){
                    responses.add(new Response("У Вас нет новых изменений"));
                }
                else if (lastAction.getValue() == null){
                    responses.add(new Response("У Вас нет новых изменений"));
                }
                else {
                    if (canUndo){
                        canUndo = false;
                        Response response = ((Reversible) lastAction.getKey()).undo(lastAction.getValue(), csvDataBase);
                        responses.add(response);
                        isLastUndo = true;
                    }
                }
            }
            else if (command instanceof UserRegisterCommand){
                if (command instanceof Register){
                    System.out.println(user);
                    responses.add(csvDataBase.registerUser(request.getUser()));
                }
                else {
                    responses.add(csvDataBase.loginUser(request.getUser()));
                }
            }
            else if (command instanceof Reversible){
                isLastUndo = false;
                if ((DataBaseCommand.class.isAssignableFrom(command.getClass()))) {
                    DataBaseResponse dataBaseResponse = (DataBaseResponse) ((DataBaseCommand) command).execute(request.getArgs(),
                            ((CityRequest) request).getCity(), csvDataBase, user);
                    String additionString;
                    additionString = command.getCommandName() + " " + String.join(" ", request.getArgs());
                    dataBaseResponse.addCommandToResponse(additionString);
                    responses.add(dataBaseResponse);
                    finalResponse.setContainsReversible(true);
                    Map.Entry<Command, TreeMap<Long, City>> entry = new AbstractMap.SimpleEntry<>(command, dataBaseResponse.getDeletedPart());
                    finalResponse.setLastAction(entry);
                }
                else {
                    isLastUndo = false;
                    DataBaseResponse dataBaseResponse = (DataBaseResponse) command.execute(request.getArgs(),
                            csvDataBase, user);
                    responses.add(dataBaseResponse);
                    finalResponse.setContainsReversible(true);
                    Map.Entry<Command, TreeMap<Long, City>> entry = new AbstractMap.SimpleEntry<>(command, dataBaseResponse.getDeletedPart());
                    finalResponse.setLastAction(entry);
                }
                canUndo = true;

            }
            else {
                isLastUndo = false;
                responses.add(command.execute(request.getArgs(), csvDataBase, user));
            }
        }
        finalResponse.setResponses(responses);
        finalResponse.setLastUndo(isLastUndo);
        return finalResponse;
    }
}
