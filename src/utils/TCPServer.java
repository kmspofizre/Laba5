package utils;


import collections.PostgresDataBase;
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
import java.sql.SQLException;
import java.util.*;

public class TCPServer {

    private ServerSocketChannel server;

    public TCPServer(PostgresDataBase csvDataBase, String hostname, int port) throws IOException {
        this.server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(hostname, port));
        server.configureBlocking(false);
    }
//
//    public void runTCP() throws IOException, ClassNotFoundException, SQLException {
//        while (true) {
//            this.selector.select();
//            Set<SelectionKey> keys = this.selector.selectedKeys();
//            for (var iter = keys.iterator(); iter.hasNext(); ) {
//                SelectionKey key = iter.next();
//                iter.remove();
//                try {
//                    if (key.isValid()) {
//                        if (key.isAcceptable()) {
//                            connectUser(key);
//                        }
//                        if (key.isReadable()) {
//                            readUser(key);
//                        }
//                        if (key.isWritable()) {
//                            writeUser(key);
//                        }
//                    }
//                }
//                catch (SocketException se){
//                    continue;
//                }
//            }
//        }

//    }
//
//
//    private void connectUser(SelectionKey key) throws IOException, SocketException {
//        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
//        SocketChannel client = serverSocketChannel.accept();
//        client.configureBlocking(false);
//        client.register(this.selector, SelectionKey.OP_READ);
//    }

    private void disconnect(SocketChannel client) throws IOException, SocketException {
        client.close();
    }

//
//    private void readUser(SelectionKey key) throws IOException, ClassNotFoundException, SQLException {
//        SocketChannel client = (SocketChannel) key.channel();
//        this.intBuffer.clear();
//        int size = client.read(this.intBuffer);
//        if (size == -1) {
//            disconnect(client);
//            return;
//        }
//        ByteBuffer buffer = ByteBuffer.allocate(this.intBuffer.flip().getInt());
//        int curRead = 0;
//        while (curRead < size) {
//            curRead = client.read(buffer);
//            if (curRead == 0) {
//                continue;
//            }
//            if (curRead == -1) {
//                break;
//            }
//            size -= curRead;
//        }
//        byte[] bytes = buffer.array();
//        List<Request> requests = DataPreparer.getRequests(bytes);
//        FinalResponse responses;
//        if (this.lastActions.containsKey(client)){
//            responses = RequestHandler.handleRequests(requests, this.dataBase);
//        }
//        else {
//            responses = RequestHandler.handleRequests(requests, this.dataBase);
//        }
//        this.userResponses.put(client, responses.getResponses());
//        ResponseHandler.handleResponses(responses.getResponses());
//        client.register(this.selector, SelectionKey.OP_WRITE);
//    }
//
//    public void writeUser(SelectionKey key) throws IOException, SocketException {
//        SocketChannel client = (SocketChannel) key.channel();
//        this.intBuffer.clear();
//        List<Response> responses = this.userResponses.get(client);
//        this.userResponses.remove(client);
//        byte[] bytes = DataPreparer.serializeObj(responses).array();
//        this.intBuffer.clear();
//        this.intBuffer.putInt(bytes.length);
//        this.intBuffer.flip();
//        client.write(this.intBuffer);
//        client.write(ByteBuffer.wrap(bytes));
//        client.register(this.selector, SelectionKey.OP_READ);
//        this.intBuffer.clear();
//    }

    public ServerSocketChannel getServer() {
        return server;
    }
}