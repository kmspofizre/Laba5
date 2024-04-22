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
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

import static java.nio.channels.SelectionKey.OP_WRITE;


public class Main {
    public static void main(String[] args) throws IOException {
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv");

        Command [] commands = CommandsInitiator.initCommands();

        CommandHandler handler = new CommandHandler(dataBase, commands);
        ProgramRunner programRunner = new ProgramRunner(dataBase, handler);
        Signal.handle(new Signal("INT"), signal -> {
            dataBase.save();
            System.exit(0);
        });
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (var iter = keys.iterator(); iter.hasNext(); ) {
                SelectionKey key = iter.next(); iter.remove();
                if (key.isValid()) {
                    if (key.isReadable()) {
                        var sc = (SocketChannel) key.channel();

                        var data = (List<Request>) key.attachment();

                        sc.register(key.selector(), OP_WRITE);
                        List<Response> responses = RequestHandler.handleRequests(data, dataBase);
                        ByteBuffer buf = DataPreparer.serializeObj(responses);
                        sc.write(buf);
                    }
                }
            }
        }
        // считываем запросы
        // передаем в ch и получаем результат
        // формируем response
        // передаем на клиент

    }
}
