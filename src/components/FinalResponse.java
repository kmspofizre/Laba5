package components;

import commands.Command;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FinalResponse extends Response{
    private List<Response> responses;
    private boolean containsReversible;
    private Map.Entry<Command, TreeMap<Long, City>> lastAction;
    public FinalResponse(String responseText){
        super(responseText);
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public void setContainsReversible(boolean containsReversible) {
        this.containsReversible = containsReversible;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public boolean isContainsReversible() {
        return containsReversible;
    }

    public void setLastAction(Map.Entry<Command, TreeMap<Long, City>> lastAction) {
        this.lastAction = lastAction;
    }

    public Map.Entry<Command, TreeMap<Long, City>> getLastAction() {
        return lastAction;
    }
}
