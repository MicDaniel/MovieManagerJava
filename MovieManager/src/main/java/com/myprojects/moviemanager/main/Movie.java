/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myprojects.moviemanager.main;


/**
 *
 * @author Daniel Mic
 */
public class Movie {
    private Integer id;
    private String name;
    private String category;
    private String releasedate;
    private float rating;
    private float imdbscore;
    private String imagePth;
    private int userid;

    public Movie(){
        
    }
    
    public Movie(Integer id, String name, String category, String releasedate, float rating, float imdbscore, String imagePth, int userid) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.releasedate = releasedate;
        this.rating = rating;
        this.imdbscore = imdbscore;
        this.imagePth = imagePth;
        this.userid = userid;
    }

    
  

    public int getId() {
        return id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getImdbscore() {
        return imdbscore;
    }

    public void setImdbscore(float imdbscore) {
        this.imdbscore = imdbscore;
    }
    
    public String getImagePth() {
        return imagePth;
    }

    public void setImagePth(String imagePth) {
        this.imagePth = imagePth;
    }
    
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    } 
}
