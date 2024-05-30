package svink;

import components.User;
import utils.TCPClient;

import java.awt.*;
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
    // Данные для таблиц
// Конструктор с параметрами


    public TableWindow(String winTitle, int w, int h, TCPClient tcpClient, User user) {
        width = w;
        height = h;
        JFrame frame = new JFrame("Главное окно");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {
                "Название",
                "Координаты",
                "Площадь",
                "Население",
                "Уровень жизни"
        };

        String[][] data = {
                {"addins", "02.11.2006 19:15", "Folder", "", ""},
                {"AppPatch", "03.10.2006 14:10", "Folder", "", ""},
                {"assembly", "02.11.2006 14:20", "Folder", "", ""},
                {"Boot", "13.10.2007 10:46", "Folder", "", ""},
                {"Branding", "13.10.2007 12:10", "Folder", "", ""},
                {"Cursors", "23.09.2006 16:34", "Folder", "", ""},
                {"Debug", "07.12.2006 17:45", "Folder", "", ""},
                {"Fonts", "03.10.2006 14:08", "Folder", "", ""},
                {"Help", "08.11.2006 18:23", "Folder", "", ""},
                {"explorer.exe", "18.10.2006 14:13", "File", "2,93MB", ""},
                {"helppane.exe", "22.08.2006 11:39", "File", "4,58MB", ""},
                {"twunk.exe", "19.08.2007 10:37", "File", "1,08MB", ""},
                {"nsreg.exe", "07.08.2007 11:14", "File", "2,10MB", ""},
                {"avisp.exe", "17.12.2007 16:58", "File", "12,67MB", ""},
        };

        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTable table = new JTable(data, columnNames);
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
}
