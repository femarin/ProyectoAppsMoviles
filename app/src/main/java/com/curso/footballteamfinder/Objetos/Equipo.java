package com.curso.footballteamfinder.Objetos;

public class Equipo {
    String name;
    float score;
    int avage;
    String city;
    String color1;
    String color2;
    String numcontacto;
    public Equipo() {
    }

    public Equipo(String name, float score, int avage, String city, String color1, String color2,String numcontacto) {
        this.name = name;
        this.score = score;
        this.avage = avage;
        this.city = city;
        this.color1 = color1;
        this.color2 = color2;
        this.numcontacto = numcontacto;
    }

    public String getName() {
        return name;
    }

    public String getNumcontacto() {
        return numcontacto;
    }

    public void setNumcontacto(String numcontacto) {
        this.numcontacto = numcontacto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getAvage() {
        return avage;
    }

    public void setAvage(int avage) {
        this.avage = avage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }
}
