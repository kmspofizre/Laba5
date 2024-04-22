package utils;

import components.Response;

import java.util.List;

public class ResponseHandler {
    public static void handleResponses(List<Response> responses){
        for (Response response : responses){
            ResponseMachine.makeStringResponse(response.getReponseString());
        }
    }
}
