package svink;

import commands.Command;
import components.Request;
import components.Response;
import components.User;
import utils.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableWindow extends JFrame implements ActionListener { // этот класс уже унаследован от Frame
    // поля класса
    private String iconPath;
    private ImageIcon iconFile;

    private JPanel buttonsPanel;
    private JButton start, stop;
    private final int width, height;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField smallField;
    private JButton filterButton, insertButton, updateButton, removeButton;
    JComboBox comboBox;
    TCPClient tcpClient;
    String[] columnNames;
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
        smallField = new JTextField(30);
        smallField.setToolTipText("Фильтр по названию");
        smallField.setPreferredSize(new Dimension(300, 30));

        String[] items = {
                "Нет сортировки",
                "Возрастание",
                "Убывание"
        };
        comboBox = new JComboBox(items);
        comboBox.setPreferredSize(new Dimension(300, 30));

        String[][] data = justDoIt();
        String[] columnNames = {
                "ID",
                "Название",
                "Координаты",
                "Площадь",
                "Население",
                "Уровень жизни",
                "Владелец"
        };
        table = new JTable(data, columnNames);

        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        table.setEnabled(false);
        JButton button;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.5;
        contents.add(comboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 0.5;
        contents.add(smallField, c);
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
        insertButton = new JButton("Добавить");
        insertButton.addActionListener(this);
        insertButton.setActionCommand("Добавить");
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        contents.add(insertButton, c);

        updateButton = new JButton("Изменить");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("Изменить");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        contents.add(updateButton, c);

        removeButton = new JButton("Удалить");
        removeButton.addActionListener(this);
        removeButton.setActionCommand("Удалить");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 4;
        contents.add(removeButton, c);
        frame.setContentPane(contents);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(600, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public String[][] justDoIt() {
        Scanner scanner = null;
        Command[] commands = CommandsInitiator.initClientCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);
        String[] kort = new String[1];
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        ResponseHandler.handleResponses(responses);
        String citiesData = responses.get(0).getResponseString();
        int i = 0;
        int j = 0;
        int dataLength = citiesData.split("\n").length;
        String[][] tableData = new String[dataLength][7];
        String[][] tableDataFinal = new String[dataLength][7];
        for (String elem : citiesData.split("\n")) {
            String[] dataToGet = elem.split("_");
            tableData[i][0] = dataToGet[0];
            tableData[i][1] = dataToGet[1];
            tableData[i][2] = dataToGet[2] + ", " + dataToGet[2];
            tableData[i][3] = dataToGet[4];
            tableData[i][4] = dataToGet[5];
            tableData[i][5] = dataToGet[9];
            tableData[i][6] = dataToGet[11];
            i++;
            if ((dataToGet[1].contains(smallField.getText())) | smallField.getText().equals("")) {
                tableDataFinal[j][0] = dataToGet[0];
                tableDataFinal[j][1] = dataToGet[1];
                tableDataFinal[j][2] = dataToGet[2] + ", " + dataToGet[2];
                tableDataFinal[j][3] = dataToGet[4];
                tableDataFinal[j][4] = dataToGet[5];
                tableDataFinal[j][5] = dataToGet[9];
                tableDataFinal[j][6] = dataToGet[11];
                j++;
            }
        }
        String selectedItem = (String) comboBox.getSelectedItem();
        if (Objects.equals(selectedItem, "Возрастание")) {
            List<String[]> data = Arrays.stream(tableDataFinal).toList();
            data.sort(Comparator.comparingInt(o -> Integer.parseInt(o[4])));
        } else if (Objects.equals(selectedItem, "Убывание")) {
            List<String[]> data = Arrays.stream(tableDataFinal).toList();
            data.sort((o1, o2) -> -1 * (Integer.parseInt(o1[4]) - Integer.parseInt(o2[4])));
        }
        String[] columnNames = {
                "ID",
                "Название",
                "Координаты",
                "Площадь",
                "Население",
                "Уровень жизни",
                "Владелец"
        };
        new BackGroundWorker().execute();
        return tableDataFinal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Добавить")) {
            InsertElementForm insertElementForm = new InsertElementForm(tcpClient, user);
        } else if (actionCommand.equals("Изменить")) {
            UpdateElementForm updateElementForm = new UpdateElementForm(tcpClient, user);
        } else if (actionCommand.equals("Удалить")) {
            RemoveElementForm removeElementForm = new RemoveElementForm(tcpClient, user);
        }
    }

    class BackGroundWorker extends SwingWorker {

        @Override
        protected Boolean doInBackground() throws Exception {
            System.out.println("Swing swing swing");
            return true;
        }

        @Override
        protected void done() {
            try {

                String[][] data = justDoIt();
                String[] columnNames = {
                        "ID",
                        "Название",
                        "Координаты",
                        "Площадь",
                        "Население",
                        "Уровень жизни",
                        "Владелец"
                };
                table.setModel(new DefaultTableModel(data, columnNames));
                System.out.println("Swing swing swing");
            } catch (Exception ignore) {
            }

        }
    }
}
