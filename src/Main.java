import collections.PostgresDataBase;
import commands.*;
import sun.misc.Signal;
import thread.RequestReader;
import utils.*;

import java.io.IOException;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static java.nio.channels.SelectionKey.OP_WRITE;


public class Main {
    public static TCPServer serv;
    public static void main(String[] args) throws IOException, SQLException {


        Command [] commands = CommandsInitiator.initCommands();
        Properties properties = PropertiesReader.getProperties("server_config.properties");
        String jdbcURL = properties.getProperty("jdbcURL");
        String username = properties.getProperty("username");
        String passwd = properties.getProperty("passwd");

        Connection connection = DriverManager.getConnection(jdbcURL, username, passwd);
        PostgresDataBase dataBase = new PostgresDataBase("test_this_crap.csv", connection);
        CommandHandler handler = new CommandHandler(dataBase, commands);
        Signal.handle(new Signal("INT"), signal -> {
            dataBase.save();
            System.exit(0);
        });
        serv = new TCPServer(dataBase, "localhost", 8080);
        getServerAcceptThread(dataBase).start();
        //try{
         //   tcpServer.runTCP();
        //}
        //catch (ClassNotFoundException e1){
         //   ResponseMachine.makeStringResponse(e1.getException());
         //   ResponseMachine.makeStringResponse(e1.getMessage());
        //}
    }


    private static Thread getServerAcceptThread(PostgresDataBase dataBase) {
        return new Thread(() -> {
            ServerSocketChannel serverChannel = serv.getServer();

            while (true) {
                try {
                    SocketChannel client = serverChannel.accept();

                    if (client == null) continue;

                    client.configureBlocking(false);

                    Thread requestReaderThread = new Thread(new RequestReader(client, dataBase));
                    requestReaderThread.start();
                } catch (IOException e) {
                    ResponseMachine.makeStringResponse("User () disconnected");
                }
            }
        });
    }

}
