package utils;

import components.Response;

public class ResponseMachine {
    public static <K> void makeStringResponse(K outputString){
        System.out.println(outputString);
    }
    public static <K> Response makeClientResponse(K clientResponse){
        return new Response(clientResponse.toString());
    }
}
