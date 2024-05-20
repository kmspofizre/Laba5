package components;

import utils.ResponseMachine;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String passwrd;
    private String[] history;
    private int historyIndex;
    private static final long serialVersionUID = 52525252L;
    public User(String name, String passwrd){
        this.history = new String [14];
        this.historyIndex = 0;
        this.name = name;
        this.passwrd = passwrd;
        this.id = 0;
    }
    public void getHistory(){
        ResponseMachine.makeStringResponse("Последние вызванные команды:");
        for (String command : this.history){
            if (command != null){
                ResponseMachine.makeStringResponse(command);
            }
        }
        this.addCommandToHistory("history");
    }
    private void incrementHistoryIndex(){
        if (this.historyIndex == 13){
            this.historyIndex = 0;
        }
        else {
            this.historyIndex += 1;
        }
    }

    public void addCommandToHistory(String command){
        this.history[this.historyIndex] = command;
        incrementHistoryIndex();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }


    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
