package thread;

import collections.PostgresDataBase;
import components.FinalResponse;
import components.Request;
import utils.DataPreparer;
import utils.RequestHandler;
import utils.ResponseHandler;
import utils.ResponseMachine;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.List;

public class RequestReader implements Runnable {
    private SocketChannel client;
    private PostgresDataBase dataBase;
    public RequestReader(SocketChannel client, PostgresDataBase dataBase){
        this.client = client;
        this.dataBase = dataBase;
    }


    @Override
    public void run() {
        while (true) {
            try{
                ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
                int size = this.client.read(intBuffer);

                if (size == 0) continue;
                if (size == -1) throw new IOException();

                ByteBuffer buffer = ByteBuffer.allocate(intBuffer.flip().getInt());
                int curRead = 0;
                while (curRead < size) {
                    curRead = this.client.read(buffer);
                    if (curRead == 0) {
                        continue;
                    }
                    if (curRead == -1) {
                        break;
                    }
                    size -= curRead;
                }
                byte[] bytes = buffer.array();
                List<Request> requests = DataPreparer.getRequests(bytes);
                FinalResponse responses;
                Thread requestHandlerThread = new Thread(new thread.RequestHandler(requests, this.dataBase, this.client));
                requestHandlerThread.start();

            }
            catch (IOException e){
                ResponseMachine.makeStringResponse("IOEX");
//                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
            catch (ClassNotFoundException e) {
                ResponseMachine.makeStringResponse("IOEX");
                Thread.currentThread().interrupt();
                break;
            }

        }

    }
}
