package com.ruthbeeler.possibleapplication;

public class Traffic {
    String label;
    String image;
    String owner;
    double[] coordinates;

    public Traffic(String label, String image, String owner, double[] coordinates){

        this.label = label;
        this.image = image;
        this.owner = owner;
        this.coordinates = coordinates;

    }
    public String imageUrl() {
        return this.image;
    }

    public String getLabel(){
        return this.label;
    }

    public double[] myCoordinates(){
        return this.coordinates;
    }
}
