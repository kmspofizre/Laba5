package validators;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputDataValidator {
    public static String stringDataValidator(Scanner scanner) {
        System.out.println("Введите название города");
        String string = scanner.nextLine();
        while (string.equals("")) {
            System.out.println("Имя не может быть пустым");
            System.out.println("Введите название города");
            string = scanner.nextLine();
        }
        return string;
    }

    public static String xCoordinateValidator(Scanner scanner) {
        Float x = Float.parseFloat("0");
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

    public static String yCoordinateValidator(Scanner scanner) {
        Integer y = Integer.parseInt("0");
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

    public static String areaValidator(Scanner scanner) {
        Integer area = Integer.parseInt("0");
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

    public static String populationValidator(Scanner scanner) {
        Integer population = Integer.parseInt("0");
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
    public static String metersAboveSeaLevelValidator(Scanner scanner) {
        Double metersAboveSeaLevel = Double.parseDouble("0");
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
    public static String climateValidator(Scanner scanner) {
        System.out.println("Выберите климат: MONSOON, MEDITERRANIAN, SUBARCTIC, DESERT");
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
    public static String governmentValidator(Scanner scanner) {
        System.out.println("Выберите тип правительства: DESPOTISM, DICTATORSHIP, STRATOCRACY");
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

    public static String standardOfLivingValidator(Scanner scanner) {
        System.out.println("Выберите уровень жизни в городе: NIGHTMARE, LOW, VERY_HIGH");
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
    public static String governorValidator(Scanner scanner){
        Integer age = Integer.parseInt("0");
        boolean f = false;
        while (!f) {
            System.out.println("Введите возраст губернатора");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0){
                    System.out.println("Возраст должен быть целым положительным числом");
                }
                else {
                    f = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Возраст должен быть целым положительным числом");
            }
        }
        return age.toString();
    }
}

