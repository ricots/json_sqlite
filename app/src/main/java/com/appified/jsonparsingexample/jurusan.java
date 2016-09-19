package com.appified.jsonparsingexample;

/**
 * Created by ACER on 9/17/2016.
 */
public class jurusan {
    String idkampus,jurusan;

    public jurusan(){
    }

    public jurusan(String idkampus, String jurusan){
        this.idkampus = idkampus;
        this.jurusan = jurusan;
    }

    public String getIdkampus() {
        return idkampus;
    }

    public void setIdkampus(String idkampus) {
        this.idkampus = idkampus;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
