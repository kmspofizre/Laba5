package thread;

import collections.PostgresDataBase;
import components.FinalResponse;
import components.Request;
import components.Response;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.List;

public class RequestHandler implements Runnable{
    private List<Request> requests;
    private PostgresDataBase dataBase;
    private SocketChannel client;
    public RequestHandler(List<Request> requests, PostgresDataBase dataBase, SocketChannel client){
        this.requests = requests;
        this.dataBase = dataBase;
        this.client = client;
    }

    @Override
    public void run(){
        try {
            FinalResponse responses = utils.RequestHandler.handleRequests(this.requests, this.dataBase);
            ThreadExecutor.getInstance().execute(new RequestWriter(responses, this.client));
        } catch (SQLException e) {
            Thread.currentThread().interrupt();
        }
    }
}
