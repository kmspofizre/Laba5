package components;

import commands.Command;

public class CityRequest extends Request{
    private City city;
    public CityRequest(String args, City city){
        super(args);
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}
