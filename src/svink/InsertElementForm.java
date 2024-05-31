package svink;

import components.User;
import utils.TCPClient;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertElementForm extends JFrame implements ActionListener {
    TCPClient tcpClient;
    JButton submitButton;
    JTextField nameField, xCoordField, yCoordField, areaField,
            populationField, metersAboveField, climateField, governmentField, standardField, governorField;
    JLabel nameLabel, xCoordLabel, yCoordLabel, areaLabel, populationLabel,
            metersAboveLabel, climateLabel, governmentLabel, standardLabel, governorLabel, responseLabel;
    public InsertElementForm(TCPClient providedTCPClient, User user){
        super("Форма добавления элемента");
        tcpClient = providedTCPClient;
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        nameLabel = new JLabel("Название");
        nameLabel.setHorizontalAlignment(JTextField.RIGHT);
        nameField = new JTextField(30);

        xCoordLabel = new JLabel("Координата X");
        xCoordLabel.setHorizontalAlignment(JTextField.RIGHT);
        xCoordField = new JTextField(30);

        yCoordLabel = new JLabel("Координата Y");
        yCoordLabel.setHorizontalAlignment(JTextField.RIGHT);
        yCoordField = new JTextField(30);

        areaLabel = new JLabel("Площадь");
        areaLabel.setHorizontalAlignment(JTextField.RIGHT);
        areaField = new JTextField(30);

        populationLabel = new JLabel("Население");
        populationLabel.setHorizontalAlignment(JTextField.RIGHT);
        populationField = new JTextField(30);

        metersAboveLabel = new JLabel("Высота над уровнем моря");
        metersAboveLabel.setHorizontalAlignment(JTextField.RIGHT);
        metersAboveField = new JTextField(30);

        climateLabel = new JLabel("Климат");
        climateLabel.setHorizontalAlignment(JTextField.RIGHT);
        climateField = new JTextField(30);
        climateField.setToolTipText("MONSOON, MEDITERRANIAN, SUBARCTIC, DESERT");

        governmentLabel = new JLabel("Правительство");
        governmentLabel.setHorizontalAlignment(JTextField.RIGHT);
        governmentField = new JTextField(30);
        governmentField.setToolTipText("DESPOTISM, DICTATORSHIP, STRATOCRACY");

        standardLabel = new JLabel("Уровень жизни");
        standardLabel.setHorizontalAlignment(JTextField.RIGHT);
        standardField = new JTextField(30);
        standardField.setToolTipText("NIGHTMARE, LOW, HIGH");

        governorLabel = new JLabel("Возраст губернатора");
        governorLabel.setHorizontalAlignment(JTextField.RIGHT);
        governorField = new JTextField(30);

        submitButton = new JButton("Отправить");
        submitButton.addActionListener(this);
        submitButton.setActionCommand("Добавить элемент");

        responseLabel = new JLabel("Состояние");
        responseLabel.setHorizontalAlignment(JTextField.RIGHT);

        JPanel contents = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        contents.add(nameLabel, c);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        contents.add(nameField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        contents.add(xCoordLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        contents.add(xCoordField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        contents.add(yCoordLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        contents.add(yCoordField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        contents.add(areaLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        contents.add(areaField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        contents.add(populationLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        contents.add(populationField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        contents.add(metersAboveLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        contents.add(metersAboveField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 6;
        contents.add(climateLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 6;
        contents.add(climateField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 7;
        contents.add(governmentLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 7;
        contents.add(governmentField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 8;
        contents.add(standardLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 8;
        contents.add(standardField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 9;
        contents.add(governorLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 9;
        contents.add(governorField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 10;
        contents.add(submitButton, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 11;
        contents.add(responseLabel, c);
        setContentPane(contents);
        this.setSize(new Dimension(600, 600));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
