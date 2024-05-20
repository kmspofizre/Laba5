import commands.*;
import components.*;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.*;
import validators.InputDataValidator;
import validators.UserDataValidator;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Scanner scanner;
    public static void main(String [] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Command[] commands = CommandsInitiator.initClientCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);

        try {
            List<Request> requestList = new ArrayList<>();
            TCPClient tcpClient = new TCPClient("localhost", 8080);
            User user = accessUser(scanner, tcpClient);
            while (true) {
                try {
                    String line = scanner.nextLine();
                    String[] command = line.split(" ");
                    String[] argsToGive = Arrays.copyOfRange(command, 1, command.length);

                    requestList.clear();
                    Command currentCommand = infetch.instructionFetch(command[0]);
                    if (currentCommand instanceof ExitCommand) {
                        ((ExitCommand) currentCommand).execute();
                    }
                    else {
                        if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                            String scriptCommands = infetch.fetchAndCheckCHC(currentCommand, argsToGive, commands, user);
                            if (currentCommand.getCommandName().equals("execute_script")) {
                                requestList = infetch.parseScript(scriptCommands, infetch, user);
                            }
                        } else {
                            Request commandRequest = currentCommand.prepareRequest(argsToGive, scanner, false);
                            RequestMachine.addCommandToRequest(commandRequest, currentCommand);
                            commandRequest.setUser(user);
                            requestList.add(commandRequest);
                        }
                        byte[] bytes = DataPreparer.serializeObj(requestList).array();
                        List<Response> responses = tcpClient.send(bytes);
                        ResponseHandler.handleResponses(responses);
                    }
                }
                catch (CommandExecutingException | WrongDataException e) {
                    ResponseMachine.makeStringResponse(e.getMessage());
                } catch (NumberFormatException exc) {
                    ResponseMachine.makeStringResponse("Неверный формат ввода числового значения");
                } catch (FileNotFoundException fnfe) {
                    ResponseMachine.makeStringResponse("Передано неверное имя файла");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        catch (ConnectException ce) {
            ResponseMachine.makeStringResponse("В данный момент сервер недоступен");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static User accessUser(Scanner scanner, TCPClient tcpClient) throws IOException, ClassNotFoundException {
        boolean isSuccess = false;
        User user = new User("name", "des");
        while (!isSuccess){
            boolean registered = InputDataValidator.yesOrNo("Вы зарегистрированы? (YES/NO)");
            String name = UserDataValidator.askNameData("Введите логин", scanner);
            String passwd = UserDataValidator.askPasswordData("Введите пароль", scanner);
            user = new User(name, passwd);
            user.setName(name);
            user.setPasswrd(passwd);
            String [] kort = new String[1];
            List<Request> requestList = new ArrayList<>();
            if (registered) {
                Login login = new Login();
                UserRequest userRequest = new UserRequest(kort);
                userRequest.setCommand(login);
                userRequest.setUser(user);
                requestList.add(userRequest);
            }
            else {
                Register register = new Register();
                UserRequest userRequest = new UserRequest(kort);
                userRequest.setCommand(register);
                userRequest.setUser(user);
                requestList.add(userRequest);
            }
            byte[] bytes = DataPreparer.serializeObj(requestList).array();
            List<Response> responses = tcpClient.send(bytes);
            UserResponse userResponse = (UserResponse) responses.get(0);
            isSuccess = userResponse.isSuccess();
            user = userResponse.getUser();
            ResponseHandler.handleResponses(responses);

        }
        return user;
    }
}
