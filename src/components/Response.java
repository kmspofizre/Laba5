package components;

import java.io.Serializable;

public class Response implements Serializable {
    private String reponseString;
    private static final long serialVersionUID = 333333L;
    public Response(String reponseString){
        this.reponseString = reponseString;
    }

    public String getReponseString() {
        return reponseString;
    }
    public void addCommandToResponse(String commandWithArgs){
        this.reponseString = commandWithArgs + "\n" + this.reponseString;
    }
}
