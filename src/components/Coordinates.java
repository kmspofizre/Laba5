package components;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private Integer y;
    public Coordinates(float x, Integer y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return this.x + " " + this.y;
    }

    public Float getX(){
        return x;
    }

    public Integer getY(){
        return y;
    }
}