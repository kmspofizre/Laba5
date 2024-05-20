package thread;

import components.FinalResponse;
import components.Response;
import utils.DataPreparer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

public class RequestWriter implements Runnable{
    private FinalResponse responses;
    private SocketChannel client;
    public RequestWriter(FinalResponse responses, SocketChannel client){
        this.responses = responses;
        this.client = client;
    }


    @Override
    public void run(){
        ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
        intBuffer.clear();
        byte[] bytes = DataPreparer.serializeObj(this.responses.getResponses()).array();
        intBuffer.clear();
        intBuffer.putInt(bytes.length);
        intBuffer.flip();
        try {
            this.client.write(intBuffer);
            this.client.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
        intBuffer.clear();
    }
}
