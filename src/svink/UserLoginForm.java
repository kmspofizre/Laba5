package svink;

// Использование текстовых полей JTextField
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class UserLoginForm extends JFrame
{
    // Текстовые поля
    public JTextField smallField;
    public JButton submit;
    public JLabel response, login, password;
    public UserLoginForm()
    {
        super("Форма входа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание текстовых полей
        this.smallField = new JTextField(30);
        this.smallField.setToolTipText("Короткое поле");
        // Настройка шрифта
        this.submit = new JButton("Отправить");
        this.submit.setPreferredSize(new Dimension(100, 20));

        this.response = new JLabel("Здесь текст лейбла");
        this.response.setPreferredSize(new Dimension(400, 20));

        this.login = new JLabel("Логин");
        this.login.setPreferredSize(new Dimension(400, 20));

        this.password = new JLabel("Пароль");
        this.password.setPreferredSize(new Dimension(400, 20));

        // Слушатель окончания ввода
        // Поле с паролем
        JPasswordField password = new JPasswordField(30);
        password.setEchoChar('*');
        // Создание панели с текстовыми полями
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contents.add(this.login);
        contents.add(this.smallField);
        contents.add(this.password);
        contents.add(password);
        contents.add(this.submit);
        contents.add(this.response);
        setContentPane(contents);
        // Определяем размер окна и выводим его на экран
        setSize(400, 400);
        setVisible(false);
    }
}