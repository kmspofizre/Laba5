package svink;

import commands.Command;
import components.Request;
import components.Response;
import components.User;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveElementForm extends JFrame implements ActionListener {
    JTextField elementToRemove;
    JLabel removeLabel, responseLabel;
    JButton submitButton;
    TCPClient tcpClient;
    User user;
    public RemoveElementForm(TCPClient providedTCPClient, User providedUser){
        super("Форма удаления элемента");
        tcpClient = providedTCPClient;
        user = providedUser;
        removeLabel = new JLabel("ID элемента");
        removeLabel.setHorizontalAlignment(JTextField.RIGHT);
        elementToRemove = new JTextField(30);
        submitButton = new JButton("Удалить");
        submitButton.setActionCommand("Удалить");
        submitButton.addActionListener(this);

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
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Удалить")){
            try {
                Long id = Long.parseLong(elementToRemove.getText());
                Scanner scanner = null;
                Command[] commands = CommandsInitiator.initClientCommands();
                InstructionFetcher infetch = new InstructionFetcher(commands);
                String [] kort = new String[1];
                kort[0] = id.toString();
                java.util.List<Request> requestList = new ArrayList<>();
                Command currentCommand = infetch.instructionFetch("remove");
                Request commandRequest = currentCommand.prepareRequest(kort, scanner, false);
                RequestMachine.addCommandToRequest(commandRequest, currentCommand);
                commandRequest.setUser(user);
                requestList.add(commandRequest);
                byte[] bytes = DataPreparer.serializeObj(requestList).array();
                List<Response> responses = null;
                try {
                    responses = tcpClient.send(bytes);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                responseLabel.setText(responses.get(0).getResponseString());
            }
            catch (NumberFormatException exception){
                responseLabel.setText("ID должен быть целым положительным числом");
            }
        }
    }
}
