package utils;

import commands.Command;
import components.Request;
import components.Response;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Scanner;

public class DataPreparer {
    public static String[] prepareData(Command command, String[] inlineArgs,
                                       Scanner scanner) throws CommandExecutingException, WrongDataException,
            NumberFormatException{
        if (command.isHasInlineArguments()){
            if (inlineArgs.length == 0){
                throw new CommandExecutingException("Inline аргументы не были доставлены");
            }
        }
        String [] data = command.prepareData(inlineArgs, scanner);
        return data;
    }
    public static String[] prepareScriptData(Command command, String[] inlineArgs,
                                             Scanner scanner) throws CommandExecutingException, WrongDataException,
            NumberFormatException{
        if (command.isHasInlineArguments()){
            if (inlineArgs.length == 0){
                throw new CommandExecutingException("Inline аргументы не были доставлены");
            }
        }
        String [] data = command.prepareScriptData(inlineArgs, scanner);
        return data;
    }
    public static ByteBuffer serializeObj(Object obj){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            out.flush();
            ByteBuffer buf = ByteBuffer.wrap(bos.toByteArray());
            return buf;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public static List<Response> getResponses(ByteBuffer buf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(buf.array());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (List<Response>) ois.readObject();
    }

    public static List<Request> getRequests(byte[] buf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (List<Request>) ois.readObject();
    }
}
