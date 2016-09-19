package com.appified.jsonparsingexample;

/**
 * Created by Priyabrat on 6/13/2015.
 */
public class City {

    private int id;
    private String name;
    private String state;
    private String description;
    private String logo,jurursan;
    //byte[] logo;
    public City() {
    }

    public City(String name, String state, String description, String logo, String jurursan) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.logo  = logo;
        this.jurursan  = jurursan;
    }

    public City(int id, String name, String state, String description, String logo,String jurursan) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.description = description;
        this.logo  = logo;
        this.jurursan = jurursan;
    }

    public String getJurursan() {
        return jurursan;
    }

    public void setJurursan(String jurursan) {
        this.jurursan = jurursan;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
