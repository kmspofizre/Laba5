package utils;

import components.Response;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;

public class TCPClient{
    private static final int RESPONSE_BUFFER_SIZE = 16;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private String host;
    private int port;
    private final ByteBuffer intBuffer;
    public TCPClient(String host, int port) throws IOException {
        this.intBuffer = ByteBuffer.allocate(Integer.BYTES);
        this.host = host;
        this.port = port;
        connect();
        System.out.println("connected to " + socket.toString());
    }


    private void connect() throws IOException {
        this.socket = new Socket(host, port);
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            ResponseMachine.makeStringResponse("Сервер недоступен");
        }
        this.outputStream = this.socket.getOutputStream();
        this.inputStream = this.socket.getInputStream();
    }


    public void disconnect() {
        try {
            System.out.println("disconnecting from " + socket.toString());
            socket.close();
            this.inputStream.close();
            this.outputStream.close();
        } catch (IOException e) {
        }
    }
    public List<Response> send(byte[] bytes) throws IOException, ClassNotFoundException {

        this.intBuffer.clear();
        this.intBuffer.putInt(bytes.length);
        this.intBuffer.flip();
        this.outputStream.write(this.intBuffer.array());
        this.outputStream.write(bytes);
        this.outputStream.flush();
        this.intBuffer.clear();
        this.inputStream.read(this.intBuffer.array());
        int size = this.intBuffer.getInt();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes1 = new byte[this.RESPONSE_BUFFER_SIZE];
        int totalRead = 0;
        while (totalRead < size) {
            totalRead += this.inputStream.read(bytes1);
            baos.write(bytes1);
        }
        byte[] inputBytes = baos.toByteArray();
        List<Response> responseList = DataPreparer.getResponses(inputBytes);
        return responseList;
    }
}