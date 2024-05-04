package components;

import java.util.TreeMap;

public class DataBaseResponse extends Response{

    private TreeMap<Long, City> changedPart;
    private static final long serialVersionUID = 333555L;

    public DataBaseResponse(String responseString){
        super(responseString);
    }

    public void addDeletedPart(TreeMap<Long, City> changedPart){
        this.changedPart = changedPart;
    }

    public TreeMap<Long, City> getDeletedPart(){
        return this.changedPart;
    }
}
