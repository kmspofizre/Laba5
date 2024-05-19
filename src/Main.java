import collections.CSVDataBase;
import commands.*;
import components.CityRequest;
import components.Request;
import components.Response;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import sun.misc.Signal;
import utils.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static java.nio.channels.SelectionKey.OP_WRITE;


public class Main {
    public static void main(String[] args) throws IOException, SQLException {


        Command [] commands = CommandsInitiator.initCommands();
        //jdbc:postgresql://localhost:5432/lab7
        Properties properties = PropertiesReader.getProperties("server_config.properties");
        String jdbcURL = properties.getProperty("jdbcURL");
        String username = properties.getProperty("username");
        String passwd = properties.getProperty("passwd");

        Connection connection = DriverManager.getConnection(jdbcURL, username, passwd);
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv", connection);
        CommandHandler handler = new CommandHandler(dataBase, commands);
        ProgramRunner programRunner = new ProgramRunner(dataBase, handler);
        Signal.handle(new Signal("INT"), signal -> {
            dataBase.save();
            System.exit(0);
        });
        TCPServer tcpServer = new TCPServer(dataBase, "localhost", 8080);
        try{
            tcpServer.runTCP();
        }
        catch (ClassNotFoundException e1){
            ResponseMachine.makeStringResponse(e1.getException());
            ResponseMachine.makeStringResponse(e1.getMessage());
        }
    }
}
