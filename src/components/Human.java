package components;

import java.io.Serializable;

public class Human implements Serializable {
    private int age; //Значение поля должно быть больше 0
    public Human(int age){
        this.age = age;
    }
    public Integer getAge(){
        return age;
    }
    @Override
    public String toString(){
        return String.valueOf(this.age);
    }
}
