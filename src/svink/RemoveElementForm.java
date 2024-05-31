package svink;

import components.User;
import utils.TCPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveElementForm extends JFrame implements ActionListener {
    JTextField elementToRemove;
    JLabel removeLabel, responseLabel;
    JButton submitButton;
    TCPClient tcpClient;
    public RemoveElementForm(TCPClient providedTCPClient, User providedUser){
        super("Форма удаления элемента");
        tcpClient = providedTCPClient;
        removeLabel = new JLabel("ID элемента");
        removeLabel.setHorizontalAlignment(JTextField.RIGHT);
        elementToRemove = new JTextField(30);
        submitButton = new JButton("Удалить");

        responseLabel = new JLabel("Состояние");
        responseLabel.setHorizontalAlignment(JTextField.RIGHT);

        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        contents.add(removeLabel, c);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        contents.add(elementToRemove, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        contents.add(submitButton, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        contents.add(responseLabel, c);
        setContentPane(contents);
        this.setSize(new Dimension(600, 300));
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
