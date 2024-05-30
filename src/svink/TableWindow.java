package svink;

import commands.Command;
import components.Request;
import components.Response;
import components.User;
import utils.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableWindow extends JFrame { // этот класс уже унаследован от Frame
    // поля класса
    private String iconPath;
    private ImageIcon iconFile;

    private JPanel buttonsPanel;
    private JButton start, stop;
    private final int width, height;
    private DefaultTableModel tableModel;
    private JTable table1;
    private JTextField smallField;
    private JButton filterButton;
    TCPClient tcpClient;
    User user;
    // Данные для таблиц
// Конструктор с параметрами


    public TableWindow(String winTitle, int w, int h, TCPClient providedtcpClient, User providedUser) {
        tcpClient = providedtcpClient;
        user = providedUser;
        width = w;
        height = h;
        JFrame frame = new JFrame("Главная");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String citiesData = justDoIt();
        int i = 0;
        int dataLength = citiesData.split("\n").length;
        String[][] tableData = new String[dataLength][5];
        for (String elem : citiesData.split("\n")){
            String[] dataToGet = elem.split("_");
            tableData[i][0] = dataToGet[0];
            tableData[i][1] = dataToGet[1] + ", " + dataToGet[2];
            tableData[i][2] = dataToGet[3];
            tableData[i][3] = dataToGet[4];
            tableData[i][4] = dataToGet[8];
            i++;
        }
        String[] columnNames = {
                "Название",
                "Координаты",
                "Площадь",
                "Население",
                "Уровень жизни"
        };


        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTable table = new JTable(tableData, columnNames);
        table.setEnabled(false);
        JButton button;
        String[] items = {
                "Элемент списка 1",
                "Элемент списка 2",
                "Элемент списка 3"
        };
        JComboBox comboBox = new JComboBox(items);
        comboBox.setPreferredSize(new Dimension(300, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.5;
        contents.add(comboBox, c);
        smallField = new JTextField(30);
        smallField.setToolTipText("Фильтр по названию");
        smallField.setPreferredSize(new Dimension(300, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 0.5;
        contents.add(smallField, c);
        filterButton = new JButton("Фильтровать");
        filterButton.setPreferredSize(new Dimension(300, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 0;
        c.gridx = 2;
        c.weightx = 0.5;
        contents.add(filterButton, c);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 600));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 80;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 1;
        contents.add(scrollPane, c);
        button = new JButton("Button 1");
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        contents.add(button, c);

        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        contents.add(button, c);

        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 4;
        contents.add(button, c);
        frame.setContentPane(contents);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(600, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public String justDoIt(){
        Scanner scanner = null;
        Command[] commands = CommandsInitiator.initClientCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);
        String [] kort = new String[1];
        java.util.List<Request> requestList = new ArrayList<>();
        Command currentCommand = infetch.instructionFetch("show");
        Request commandRequest = currentCommand.prepareRequest(kort, scanner, false);
        RequestMachine.addCommandToRequest(commandRequest, currentCommand);
        commandRequest.setUser(user);
        requestList.add(commandRequest);
        byte[] bytes = DataPreparer.serializeObj(requestList).array();
        List<Response> responses = null;
        try {
            responses = tcpClient.send(bytes);
            System.out.println("here");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        ResponseHandler.handleResponses(responses);
        return responses.get(0).getResponseString();
    }
}
