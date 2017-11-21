package model;

/**
 * Created by joebreda on 11/20/17.
 */


//Hub class for subclasses pulled from JSON

public class Weather {

    public Place place;
    public String iconData;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();

}
