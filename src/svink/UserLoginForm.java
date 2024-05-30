package svink;

// Использование текстовых полей JTextField
import commands.Login;
import commands.Register;
import components.*;
import utils.DataPreparer;
import utils.TCPClient;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserLoginForm extends JFrame implements ActionListener
{
    // Текстовые поля
    public JTextField smallField;
    public JButton loginButton, registerButton;
    public JLabel response, login, password;
    public JPasswordField passwordField;
    public TCPClient tcpClient;
    public UserLoginForm(TCPClient providedTCPClient)
    {
        super("Форма входа");
        tcpClient = providedTCPClient;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание текстовых полей
        this.smallField = new JTextField();
        this.smallField.setToolTipText("Короткое поле");
        this.smallField.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 30));
        // Настройка шрифта
        this.loginButton = new JButton("Вход");
        this.loginButton.setPreferredSize(new Dimension(150, 20));


        this.response = new JLabel("Здесь текст лейбла");
        this.response.setPreferredSize(new Dimension(400, 20));

        this.login = new JLabel("Логин");
        this.login.setPreferredSize(new Dimension(400, 20));
        this.login.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        this.password = new JLabel("Пароль");
        this.password.setPreferredSize(new Dimension(400, 20));
        this.password.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));


        this.registerButton = new JButton("Регистрация");
        this.registerButton.setPreferredSize(new Dimension(150, 20));
        // Слушатель окончания ввода
        // Поле с паролем
        this.passwordField = new JPasswordField();
        this.passwordField.setEchoChar('*');
        this.passwordField.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 30));
        // Создание панели с текстовыми полями

        this.loginButton.addActionListener(this);
        this.loginButton.setActionCommand("Login");
        this.registerButton.addActionListener(this);
        this.registerButton.setActionCommand("Register");
        GridLayout gridLayout = new GridLayout(4, 1, 10, 20);
        JPanel loginPanel = new JPanel(new GridLayout(1, 2, 10, 20));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        loginPanel.add(this.login);
        loginPanel.add(this.smallField);
        JPanel passwordPanel = new JPanel((new GridLayout(1, 2, 10 ,20)));
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        passwordPanel.add(this.password);
        passwordPanel.add(this.passwordField);
        JPanel buttonsPanel = new JPanel((new GridLayout(1, 2, 10, 20)));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        buttonsPanel.add(this.loginButton);
        buttonsPanel.add(this.registerButton);
        JPanel labelPanel = new JPanel((new GridLayout(1, 1)));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        labelPanel.add(this.response);
        JPanel contents = new JPanel(gridLayout);
        contents.add(loginPanel);
        contents.add(passwordPanel);
        contents.add(buttonsPanel);
        contents.add(labelPanel);
        setContentPane(contents);

        // Определяем размер окна и выводим его на экран
        setSize(400, 400);
        setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        byte[] bytes1 = new byte[1];
        User user;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String pepper = "*63&^mVLC(#";
            String login = smallField.getText();
            String passwrd = new String(passwordField.getPassword());
            if ((login.isEmpty()) | (login == null) | (login.equals("null")) | (login.length() < 2)){
                response.setText("Логин должен содержать не менее двух символов");
                return;
            }
            else if ((passwrd.isEmpty()) | (passwrd == null) | (passwrd.equals("null")) | (passwrd.length() < 5)){
                response.setText("Пароль не может быть меньше пяти символов");
                return;
            }
            byte[] hash = md.digest(
                    (passwrd + pepper).getBytes("UTF-8"));

            user = new User(login, hash);
            user.setName(login);
            user.setPasswrd(hash);
            String [] kort = new String[1];
            List<Request> requestList = new ArrayList<>();
            String command = e.getActionCommand();
            UserRequest userRequest = new UserRequest(kort);
            if (command.equals("Login")){
                Login loginCommand = new Login();
                userRequest.setCommand(loginCommand);
            }
            else if (command.equals("Register")){
                Register register = new Register();
                userRequest.setCommand(register);
            }
            userRequest.setUser(user);
            requestList.add(userRequest);
            byte[] bytes = DataPreparer.serializeObj(requestList).array();
            List<Response> responses = tcpClient.send(bytes);
            UserResponse userResponse = (UserResponse) responses.get(0);
            boolean isSuccess = userResponse.isSuccess();
            if (!isSuccess){
                response.setText(userResponse.getResponseString());
                return;
            }
            else {
                response.setText(userResponse.getResponseString());
                this.setVisible(false);
                AnotherWindow anotherWindow = new AnotherWindow("Another Window", 300, 300);
                anotherWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                anotherWindow.setResizable(false);
                anotherWindow.setLocationRelativeTo(null);
                anotherWindow.setVisible(true);
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
