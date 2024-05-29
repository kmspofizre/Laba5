package components;

import utils.ResponseMachine;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private byte[] passwrdHash;
    private String[] history;
    private int historyIndex;
    private static final long serialVersionUID = 52525252L;
    private boolean ready;
    public User(String name, byte[] passwrdHash){
        this.history = new String [14];
        this.historyIndex = 0;
        this.name = name;
        this.passwrdHash = passwrdHash;
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

    public void setPasswrd(byte[] passwrdHash) {
        this.passwrdHash = passwrdHash;
    }


    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public byte[] getPasswrd() {
        return this.passwrdHash;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
