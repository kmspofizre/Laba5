package components;

public class Response {
    private String reponseString;
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
