package svink;

// Использование текстовых полей JTextField
import components.*;
import utils.TCPClient;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

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
        this.smallField = new JTextField(30);
        this.smallField.setToolTipText("Короткое поле");
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
        setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        byte[] bytes1 = new byte[1];
        User user = new User("qwerty", "qwerty".getBytes());
        this.setVisible(false);
        TableWindow anotherWindow = new TableWindow("Another Window", 600, 200, tcpClient, user);
    }
}
