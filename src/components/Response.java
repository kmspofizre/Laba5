package components;

import java.io.Serializable;

public class Response implements Serializable {
    private String responseString;
    private static final long serialVersionUID = 333333L;
    public Response(String reponseString){
        this.responseString = reponseString;
    }

    public String getResponseString() {
        return this.responseString;
    }
    public void addCommandToResponse(String commandWithArgs){
        this.responseString = commandWithArgs + "\n" + this.responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }
}
