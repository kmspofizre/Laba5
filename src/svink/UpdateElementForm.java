package svink;

import commands.Command;
import components.*;
import exceptions.WrongDataException;
import utils.*;
import validators.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateElementForm extends JFrame implements ActionListener {
    TCPClient tcpClient;
    JButton submitButton;
    JTextField nameField, xCoordField, yCoordField, areaField,
            populationField, metersAboveField, climateField, governmentField, standardField, governorField, idField;
    JLabel nameLabel, xCoordLabel, yCoordLabel, areaLabel, populationLabel,
            metersAboveLabel, climateLabel, governmentLabel, standardLabel, governorLabel, responseLabel, idLabel;
    User user;
    InstructionFetcher infetch;
    public UpdateElementForm(TCPClient providedTCPClient, User providedUser){
        super("Форма изменения элемента");
        user = providedUser;
        tcpClient = providedTCPClient;
        Command[] commands = CommandsInitiator.initClientCommands();
        infetch = new InstructionFetcher(commands);
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

        idLabel = new JLabel("ID");
        idLabel.setHorizontalAlignment(JTextField.RIGHT);
        idField = new JTextField(30);

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
        contents.add(idLabel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 10;
        contents.add(idField, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 11;
        contents.add(submitButton, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 12;
        contents.add(responseLabel, c);
        setContentPane(contents);
        this.setSize(new Dimension(600, 600));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        Float x = Float.valueOf((float) 0.0);
        Integer y = 0;
        Integer area = 0;
        Integer population = 0;
        Double metersAboveSeaLevel = 0.0;
        Long id = (long) 0;
        Climate outputClimate = null;
        Government outputGovernment = null;
        StandardOfLiving outputStandardOfLiving = null;
        Integer age = 0;
        if (actionCommand.equals("Добавить элемент")){
            try{
                String name = nameField.getText();
                CityNameValidator.validateData(name);
                try {
                    x = Float.parseFloat(xCoordField.getText());
                }
                catch (NumberFormatException exception){
                    responseLabel.setText("Координата X должна быть вещественным числом");
                }
                try {
                    y = Integer.parseInt(yCoordField.getText());
                }
                catch (NumberFormatException exception){
                    responseLabel.setText("Координата Y должна быть целым числом");
                }
                try {
                    area = Integer.parseInt(areaField.getText());
                    AreaValidator.validateData(area);
                }
                catch (NumberFormatException exception) {
                    responseLabel.setText("Площадь должна быть целым положительным числом");
                }
                try {
                    population = Integer.parseInt(populationField.getText());
                    PopulationValidator.validateData(population);
                }
                catch (NumberFormatException exception) {
                    responseLabel.setText("Население должно быть целым неотрицательным числом");
                }
                try {
                    metersAboveSeaLevel = Double.parseDouble(metersAboveField.getText());
                }
                catch (NumberFormatException exception) {
                    responseLabel.setText("Высота над уровнем моря должна быть вещественным числом");
                }

                outputClimate = switch (climateField.getText()) {
                    case ("MONSOON") -> Climate.MONSOON;
                    case ("MEDITERRANIAN") -> Climate.MEDITERRANIAN;
                    case ("SUBARCTIC") -> Climate.SUBARCTIC;
                    case ("DESERT") -> Climate.DESERT;
                    default -> null;
                };
                ClimateValidator.validateData(outputClimate);
                outputGovernment = switch (governmentField.getText()) {
                    case ("DESPOTISM") -> Government.DESPOTISM;
                    case ("DICTATORSHIP") -> Government.DICTATORSHIP;
                    case ("STRATOCRACY") -> Government.STRATOCRACY;
                    default -> null;
                };
                GovernmentValidator.validateData(outputGovernment);
                outputStandardOfLiving = switch (standardField.getText()) {
                    case ("HIGH") -> StandardOfLiving.HIGH;
                    case ("LOW") -> StandardOfLiving.LOW;
                    case ("NIGHTMARE") -> StandardOfLiving.NIGHTMARE;
                    default -> null;
                };
                StandardOfLivingValidator.validateData(outputStandardOfLiving);
                try {
                    age = Integer.parseInt(governorField.getText());
                    if (age <= 0){
                        throw new WrongDataException("Возраст губернатора должен быть положительным числом");
                    }
                }
                catch (NumberFormatException exception) {
                    responseLabel.setText("Возраст губернатора должен быть положительным числом");
                }
                try {
                    id = Long.parseLong(idField.getText());
                    if (id < 1){
                        throw new WrongDataException("ID не может быть меньше единицы");
                    }
                }
                catch (NumberFormatException exception){
                    responseLabel.setText("ID должен быть целым положительным числом");
                }
                String[] data = new String[11];
                data[0] = id.toString();
                data[1] = name;
                data[2] = x.toString();
                data[3] = y.toString();
                data[4] = area.toString();
                data[5] = population.toString();
                data[6] = metersAboveSeaLevel.toString();
                data[7] = outputClimate.toString();
                data[8] = outputGovernment.toString();
                data[9] = outputStandardOfLiving.toString();
                data[10] = age.toString();
                City city = CityCollectionMaker.makeCityInstance(data);
                String [] kort = new String[1];
                Command currentCommand = infetch.instructionFetch("update");
                CityRequest cityRequest = new CityRequest(kort, city);
                RequestMachine.addCommandToRequest(cityRequest, currentCommand);
                cityRequest.setUser(user);
                java.util.List<Request> requestList = new ArrayList<>();
                requestList.add(cityRequest);
                byte[] bytes = DataPreparer.serializeObj(requestList).array();
                List<Response> responses = tcpClient.send(bytes);
                responseLabel.setText(responses.get(0).getResponseString());

            }
            catch (WrongDataException exception){
                responseLabel.setText(exception.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

