package com.averagecoders.bunkmeter;

public class DataModel {

    String sName;
    int tbunked;
    int tclass;


    public DataModel(String name, int bunked, int total ) {
        this.sName=name;
        this.tbunked=bunked;
        this.tclass=total;

    }

    public String getName() {
        return sName;
    }

    public int getTclass() {
        return tclass;
    }

    public int getBunked() {

        return tbunked;
    }


}