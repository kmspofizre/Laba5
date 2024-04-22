import commands.CHCommand;
import commands.Command;
import commands.ExitCommand;
import components.Request;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void client(String [] args) throws IOException {
        // init
        // считывание ввода через while
        // получение команды
        // валидация (сначала команда, потом переданные значения)
        // если все норм, формируем request в классе request есть user
        // если нет, валидатор выдаст ошибку
        Scanner scanner = new Scanner(System.in);
        Command[] commands = CommandsInitiator.initClientCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);
        User user = new User();
        List<Request> requestList = new ArrayList<>();

        while (true) {
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            String[] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            try {
                requestList.clear();
                Command currentCommand = infetch.instructionFetch(command[0]);
                if (currentCommand instanceof ExitCommand){
                    ((ExitCommand) currentCommand).execute();
                }
                else {
                    if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                        String scriptCommands = infetch.fetchAndCheckCHC(currentCommand, argsToGive, commands, user);
                        if (currentCommand.getCommandName().equals("execute_script")) {
                            requestList = infetch.parseScript(scriptCommands, infetch);
                            // список request (add request и так далее)
                        }
                    }
                    else {
                        Request commandRequest = currentCommand.prepareRequest(argsToGive, scanner);
                        RequestMachine.addCommandToRequest(commandRequest, currentCommand);
                        requestList.add(commandRequest);
                        // this.commandHandler.executeCommand(currentCommand, argsForCommand, false);
                    }
                    ByteBuffer buf = DataPreparer.serializeObj(requestList);
                }
            }
            catch (CommandExecutingException | WrongDataException e){
                ResponseMachine.makeStringResponse(e.getMessage());
            }
            catch (NumberFormatException exc){
                ResponseMachine.makeStringResponse("Неверный формат ввода числового значения");
            }
            catch (FileNotFoundException fnfe){
                ResponseMachine.makeStringResponse("Передано неверное имя файла");
            }
        }
    }
}
