package utils;


import collections.CSVDataBase;
import commands.Command;
import components.City;
import components.FinalResponse;
import components.Request;
import components.Response;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class TCPServer {
    private final CSVDataBase dataBase;
    private TreeMap<SocketChannel, Map.Entry<Command, TreeMap<Long, City>>> lastActions;
    private Map<SocketChannel, List<Response>> userResponses;
    private final Selector selector;
    private final ByteBuffer intBuffer;

    public TCPServer(CSVDataBase csvDataBase, String hostname, int port) throws IOException {
        this.dataBase = csvDataBase;
        this.selector = Selector.open();
        this.intBuffer = ByteBuffer.allocate(Integer.BYTES);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(hostname, port));
        server.configureBlocking(false);
        server.register(this.selector, SelectionKey.OP_ACCEPT);
        this.userResponses = new HashMap<>();
    }

    public void runTCP() throws IOException, ClassNotFoundException {
        while (true) {
            this.selector.select();
            Set<SelectionKey> keys = this.selector.selectedKeys();
            for (var iter = keys.iterator(); iter.hasNext(); ) {
                SelectionKey key = iter.next();
                iter.remove();
                try {
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            connectUser(key);
                        }
                        if (key.isReadable()) {
                            readUser(key);
                        }
                        if (key.isWritable()) {
                            writeUser(key);
                        }
                    }
                }
                catch (SocketException se){
                    continue;
                }
            }
        }

    }


    private void connectUser(SelectionKey key) throws IOException, SocketException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(this.selector, SelectionKey.OP_READ);
    }

    private void disconnect(SocketChannel client) throws IOException, SocketException {
        client.close();
    }


    private void readUser(SelectionKey key) throws IOException, ClassNotFoundException, SocketException {
        SocketChannel client = (SocketChannel) key.channel();
        this.intBuffer.clear();
        int size = client.read(this.intBuffer);
        if (size == -1) {
            disconnect(client);
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(this.intBuffer.flip().getInt());
        int curRead = 0;
        while (curRead < size) {
            curRead = client.read(buffer);
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
        if (this.lastActions.containsKey(client)){
            responses = RequestHandler.handleRequests(requests, this.dataBase,
                    this.lastActions.get(client));
        }
        else {
            responses = RequestHandler.handleRequests(requests, this.dataBase,
                    null);
        }
        if (responses.isContainsReversible()){
            this.lastActions.put(client, responses.getLastAction());
        }
        this.userResponses.put(client, responses.getResponses());
        ResponseHandler.handleResponses(responses.getResponses());
        client.register(this.selector, SelectionKey.OP_WRITE);
    }

    public void writeUser(SelectionKey key) throws IOException, SocketException {
        SocketChannel client = (SocketChannel) key.channel();
        this.intBuffer.clear();
        List<Response> responses = this.userResponses.get(client);
        this.userResponses.remove(client);
        byte[] bytes = DataPreparer.serializeObj(responses).array();
        this.intBuffer.clear();
        this.intBuffer.putInt(bytes.length);
        this.intBuffer.flip();
        client.write(this.intBuffer);
        client.write(ByteBuffer.wrap(bytes));
        client.register(this.selector, SelectionKey.OP_READ);
        this.intBuffer.clear();
    }
}