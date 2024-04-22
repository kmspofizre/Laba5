package utils;

import collections.CSVDataBase;
import components.Request;
import components.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;

public class TCPServer {
    private final ByteBuffer intBuffer;
    private final CSVDataBase dataBase;
    private final Selector selector;
    public TCPServer(CSVDataBase csvDataBase) throws IOException {
        this.intBuffer = ByteBuffer.allocate(Integer.BYTES);
        this.dataBase = csvDataBase;
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(5000));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }
    public void runTCP() throws IOException, ClassNotFoundException {
        while(true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (var iter = keys.iterator(); iter.hasNext(); ) {
                SelectionKey key = iter.next(); iter.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()){
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
                    }
                    else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        int requestSize = this.readInt(client);
                        if (requestSize == -1) {
                            client.close();
                        }
                        byte[] requestBytes = this.readRequest(client, requestSize);
                        List<Response> responses = RequestHandler.handleRequests(DataPreparer.getRequests(requestBytes),
                                this.dataBase);
                        sendResponse(client, responses);

                        // отправить на клиент
                    }
                }
            }
        }
    }
    private int readInt(SocketChannel client) throws IOException {
        intBuffer.clear();
        int size = client.read(intBuffer);
        if (size == -1) {
            return -1;
        }
        return intBuffer.flip().getInt();
    }
    private byte[] readRequest(SocketChannel client, int responseSize) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(responseSize);
        int curRead = 0;
        while (curRead < responseSize) {
            curRead = client.read(buffer);
            if (curRead == 0) {
                continue;
            }
            if (curRead == -1) {
                break;
            }
            responseSize -= curRead;
        }
        return buffer.array();
    }

    private void writeInt(SocketChannel client, int x) throws IOException {
        intBuffer.clear();
        intBuffer.putInt(x);
        intBuffer.flip();
        client.write(intBuffer);
    }
    private void sendResponse(SocketChannel client, List<Response> response) throws IOException {
        byte[] responseBytes = DataPreparer.serializeObj(response).array();
        this.writeInt(client, responseBytes.length);
        client.write(ByteBuffer.wrap(responseBytes));
    }
}
