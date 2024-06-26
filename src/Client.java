import commands.*;
import components.*;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import svink.UserLoginForm;
import utils.*;
import validators.InputDataValidator;
import validators.UserDataValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.security.MessageDigest;



public class Client {
    private Scanner scanner;
    public static void main(String [] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Command[] commands = CommandsInitiator.initClientCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);
        try {
            List<Request> requestList = new ArrayList<>();
            TCPClient tcpClient = new TCPClient("localhost", 8080);
            UserLoginForm userLoginForm = new UserLoginForm(tcpClient);
            userLoginForm.setMinimumSize(new Dimension(750, 300));
            userLoginForm.setPreferredSize(new Dimension(950, 600));
            userLoginForm.setVisible(true);

        }

        catch (ConnectException ce) {
            ResponseMachine.makeStringResponse("В данный момент сервер недоступен");
        }
    }
    public static User accessUser(Scanner scanner, TCPClient tcpClient) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean isSuccess = false;
        byte[] bytes1 = new byte[1];
        User user = new User("name", bytes1);
        MessageDigest md = MessageDigest.getInstance("MD5");
        String pepper = "*63&^mVLC(#";
        while (!isSuccess){
            boolean registered = InputDataValidator.yesOrNo("Вы зарегистрированы? (YES/NO)");
            String name = UserDataValidator.askNameData("Введите логин", scanner);
            String passwd = UserDataValidator.askPasswordData("Введите пароль", scanner);
            byte[] hash = md.digest(
                    (passwd + pepper).getBytes("UTF-8"));

            user = new User(name, hash);
            user.setName(name);
            user.setPasswrd(hash);
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