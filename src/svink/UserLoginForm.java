package svink;

// Использование текстовых полей JTextField

import commands.Command;
import commands.Login;
import commands.Register;
import components.*;
import utils.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserLoginForm extends JFrame implements ActionListener {
    // Текстовые поля
    public JTextField smallField;
    public JButton loginButton, registerButton;
    public JLabel response, login, password;
    public JPasswordField passwordField;
    public TCPClient tcpClient;

    public UserLoginForm(TCPClient providedTCPClient) {
        super("Форма входа");
        tcpClient = providedTCPClient;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание текстовых полей
        this.smallField = new JTextField(30);
        this.smallField.setPreferredSize(new Dimension(100, 20));

        // Настройка шрифта
        this.loginButton = new JButton("Вход");
        this.loginButton.setPreferredSize(new Dimension(150, 20));


        this.response = new JLabel("Здесь текст лейбла");
        this.response.setPreferredSize(new Dimension(400, 20));
        this.response.setHorizontalAlignment(JTextField.RIGHT);

        this.login = new JLabel("Логин");
        this.login.setPreferredSize(new Dimension(100, 20));
        this.login.setHorizontalAlignment(JTextField.RIGHT);

        this.password = new JLabel("Пароль");
        this.password.setPreferredSize(new Dimension(100, 20));
        this.password.setHorizontalAlignment(JTextField.RIGHT);


        this.registerButton = new JButton("Регистрация");
        this.registerButton.setPreferredSize(new Dimension(150, 20));
        // Слушатель окончания ввода
        // Поле с паролем
        this.passwordField = new JPasswordField(30);
        this.passwordField.setPreferredSize(new Dimension(100, 20));
        this.passwordField.setEchoChar('*');

        // Создание панели с текстовыми полями

        this.loginButton.addActionListener(this);
        this.loginButton.setActionCommand("Login");
        this.registerButton.addActionListener(this);
        this.registerButton.setActionCommand("Register");


        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        contents.add(this.login, c);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        contents.add(this.smallField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        contents.add(this.password, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        contents.add(this.passwordField, c);
        c.insets = new Insets(10, 150, 0, 0);
        c.weightx = 0.5;
        c.gridy = 2;
        c.gridx = 0;
        contents.add(this.loginButton, c);
        c.insets = new Insets(10, 80, 0, 0);
        c.weightx = 0.5;
        c.gridy = 2;
        c.gridx = 1;
        contents.add(this.registerButton, c);
        c.insets = new Insets(10, 10, 0, 0);
        c.weightx = 1.0;
        c.gridy = 3;
        c.gridx = 0;
        contents.add(this.response, c);
        setContentPane(contents);

        // Определяем размер окна и выводим его на экран
        setSize(400, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        byte[] bytes1 = new byte[1];
        User user;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String pepper = "*63&^mVLC(#";
            String login = smallField.getText();
            String passwrd = new String(passwordField.getPassword());
            if ((login.isEmpty()) | (login == null) | (login.equals("null")) | (login.length() < 2)) {
                response.setText("Логин должен содержать не менее двух символов");
                return;
            } else if ((passwrd.isEmpty()) | (passwrd == null) | (passwrd.equals("null")) | (passwrd.length() < 5)) {
                response.setText("Пароль не может быть меньше пяти символов");
                return;
            }
            byte[] hash = md.digest(
                    (passwrd + pepper).getBytes("UTF-8"));

            user = new User(login, hash);
            user.setName(login);
            user.setPasswrd(hash);
            String[] kort = new String[1];
            List<Request> requestList = new ArrayList<>();
            String command = e.getActionCommand();
            UserRequest userRequest = new UserRequest(kort);
            if (command.equals("Login")) {
                Login loginCommand = new Login();
                userRequest.setCommand(loginCommand);
            } else if (command.equals("Register")) {
                Register register = new Register();
                userRequest.setCommand(register);
            }
            userRequest.setUser(user);
            requestList.add(userRequest);
            byte[] bytes = DataPreparer.serializeObj(requestList).array();
            List<Response> responses = tcpClient.send(bytes);
            UserResponse userResponse = (UserResponse) responses.get(0);
            boolean isSuccess = userResponse.isSuccess();
            if (!isSuccess) {
                response.setText(userResponse.getResponseString());
                return;
            } else {
                System.out.println(userResponse.getUser().getId());
                response.setText(userResponse.getResponseString());
                TableWindow anotherWindow = new TableWindow("Another Window", 300, 300, tcpClient, userResponse.getUser());
                this.setVisible(false);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
