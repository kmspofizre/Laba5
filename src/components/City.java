package components;

import java.util.Date;

public class City implements Comparable<City>{
    private long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int area; // Значение поля должно быть больше 0
    private int population; // Значение поля должно быть больше 0
    private Double metersAboveSeaLevel;
    private Climate climate; // Поле не может быть null
    private Government government; // Поле может быть null
    private StandardOfLiving standardOfLiving; // Поле не может быть null
    private Human governor; // Поле не может быть null
    public City(long id, String name, Coordinates coordinates,
                Date creationDate, int area,
                int population, Double metersAboveSeaLevel, Climate climate,
                Government government, StandardOfLiving standardOfLiving, Human governor){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }


    @Override
    public String toString(){
        return this.id + ". Название: " + this.name + "\n" + "Координаты: " + this.coordinates + "\n" + "Площадь: " +
                this.area + "\n" + "Население: " + this.population ;
    }

    public Double getMetersAboveSeaLevel() {
        return this.metersAboveSeaLevel;
    }

    public String getName(){
        return this.name;
    }
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public Integer getArea(){
        return this.area;
    }

    public Integer getPopulation(){
        return this.population;
    }

    public Climate getClimate(){
        return this.climate;
    }

    public Government getGovernment(){
        return this.government;
    }

    public StandardOfLiving getStandardOfLiving(){
        return this.standardOfLiving;
    }

    public Human getGovernor(){
        return governor;
    }

    public long getId(){
        return this.id;
    }

    @Override
    public int compareTo(City city) {
        return Integer.compare(this.area, city.area);
    }
}