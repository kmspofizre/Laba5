package components;

import utils.ResponseMachine;

public class User {
    private String[] history;
    private int historyIndex;
    public User(){
        this.history = new String [14];
        this.historyIndex = 0;
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
}
