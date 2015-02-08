package com.example.markokv.mymoviesproject;

/**
 * Created by Marko Vähä-Erkkilä on 24.1.15.
 */
public class Movie {
    private int _id;
    private String _title;
    private int _year;
    private String _format;
    private double _lenght;
    private String _genre;
    private String _path;


    public Movie() {
    }

    public Movie(int id, String title, int year, String format, double lenght, String genre, String path){
        this._id = id;
        this._title = title;
        this._year = year;
        this._format = format;
        this._lenght = lenght;
        this._genre = genre;
        this._path = path;
    }

    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }
    public String getTitle(){
        return this._title;
    }
    public void setTitle(String title){
        this._title = title;
    }
    public String getFormat(){
        return this._format ;
    }
    public void setFormat(String format){
        this._format=format;
    }
    public int getYear(){
        return this._year ;
    }
    public void setYear(int year){
        this._year =year;
    }
    public double getLenght(){
        return this._lenght;
    }
    public void setLenght(double lenght){
        this._lenght=lenght;
    }
    public String getGenre(){
        return this._genre;
    }
    public void setGenre(String genre){
        this._genre = genre ;
    }
    public void setPath(String path){
        this._path = path;
    }
    public String getPath(){
        return this._path;
    }


}

