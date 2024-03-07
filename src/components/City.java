package components;

public class City {
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.time.ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int area; // Значение поля должно быть больше 0
    private long population; // Значение поля должно быть больше 0
    private Double metersAboveSeaLevel;
    private Climate climate; // Поле не может быть null
    private Government government; // Поле может быть null
    private StandardOfLiving standardOfLiving; // Поле не может быть null
    private Human governor; // Поле не может быть null
}