package validators;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputDataValidator {
    public String stringDataValidator(String string, Scanner scanner) {
        while (string.equals("")) {
            System.out.println("Имя не может быть пустым");
            System.out.println("Введите название города");
            string = scanner.nextLine();
        }
        return string;
    }

    public String xCoordinateValidator(Float x, Scanner scanner) {
        boolean f = false;
        while (!f) {
            System.out.println("Введите координату x");
            try {
                x = Float.parseFloat(scanner.nextLine());
                f = true;
            } catch (NumberFormatException e) {
                System.out.println("Координата x должна быть вещественным числом");
            }
        }
        return x.toString();
    }

    public String yCoordinateValidator(Integer y, Scanner scanner) {
        boolean f = false;
        while (!f) {
            System.out.println("Введите координату y");
            try {
                y = Integer.parseInt(scanner.nextLine());
                f = true;
            } catch (NumberFormatException e) {
                System.out.println("Координата y должна быть целым числом");
            }
        }
        return y.toString();
    }

    public String areaValidator(Integer area, Scanner scanner) {
        boolean f = false;
        while (!f) {
            System.out.println("Введите площадь города");
            try {
                area = Integer.parseInt(scanner.nextLine());
                if (area <= 0){
                    System.out.println("Площадь должна быть целым положительным числом");
                }
                else {
                    f = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Площадь должна быть целым положительным числом");
            }
        }
        return area.toString();
    }

    public String populationValidator(Integer population, Scanner scanner) {
        boolean f = false;
        while (!f) {
            System.out.println("Введите население города");
            try {
                population = Integer.parseInt(scanner.nextLine());
                if (population <= 0){
                    System.out.println("Население должно быть целым положительным числом");
                }
                else {
                    f = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Население должно быть целым положительным числом");
            }
        }
        return population.toString();
    }
    public String metersAboveSeaLevelValidator(Double metersAboveSeaLevel, Scanner scanner) {
        boolean f = false;
        while (!f) {
            System.out.println("Введите высоту над уровнем моря");
            try {
                metersAboveSeaLevel = Double.parseDouble(scanner.nextLine());
                f = true;
            } catch (NumberFormatException e) {
                System.out.println("Высота над уровнем моря должна быть вещественным числом");
            }
        }
        return metersAboveSeaLevel.toString();
    }
    public String climateValidator(Scanner scanner) {
        List<String> climateTypes = new ArrayList<>();
        String climate = scanner.nextLine();
        climateTypes.add("MONSOON");
        climateTypes.add("MEDITERRANIAN");
        climateTypes.add("SUBARCTIC");
        climateTypes.add("DESERT");
        while (!(climateTypes.contains(climate))) {
            System.out.println("Неверное значение. Выберите одно из предложенных: MONSOON," +
                    " MEDITERRANIAN, SUBARCTIC, DESERT");
            climate = scanner.nextLine();
        }
        return climate;
    }
    public String governmentValidator(Scanner scanner) {
        List<String> governmentTypes = new ArrayList<>();
        String government = scanner.nextLine();
        governmentTypes.add("DESPOTISM");
        governmentTypes.add("DICTATORSHIP");
        governmentTypes.add("STRATOCRACY");
        while (!(governmentTypes.contains(government))) {
            System.out.println("Неверное значение. Выберите одно из предложенных: DESPOTISM," +
                    " DICTATORSHIP, STRATOCRACY");
            government = scanner.nextLine();
        }
        return government;
    }

    public String standardOfLivingValidator(Scanner scanner) {
        List<String> standardOfLivingTypes = new ArrayList<>();
        String standardOfLiving = scanner.nextLine();
        standardOfLivingTypes.add("VERY_HIGH");
        standardOfLivingTypes.add("LOW");
        standardOfLivingTypes.add("NIGHTMARE");
        while (!(standardOfLivingTypes.contains(standardOfLiving))) {
            System.out.println("Неверное значение. Выберите одно из предложенных: VERY_HIGH," +
                    " LOW, NIGHTMARE");
            standardOfLiving = scanner.nextLine();
        }
        return standardOfLiving;
    }
    public String governorValidator(Integer age, Scanner scanner){
        boolean f = false;
        while (!f) {
            System.out.println("Введите население города");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0){
                    System.out.println("Население должно быть целым положительным числом");
                }
                else {
                    f = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Население должно быть целым положительным числом");
            }
        }
        return age.toString();
    }
}
