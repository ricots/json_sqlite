package com.appified.jsonparsingexample;

import java.util.ArrayList;

/**
 * Created by PP on 6/14/2015.
 */
public interface CityListener {

    public void addCity(City city);
    public void addjur(jurusan jur);

    public ArrayList<City> getAllCity();
    public ArrayList<jurusan> getAlljur();


    public int getCityCount();
    public int getjur();
}
