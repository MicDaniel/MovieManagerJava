/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myprojects.moviemanager.main;

import com.myprojects.moviemanager.login.DB_CONNECTION;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author "Daniel Mic"
 */
public class MovieQuery {
    private int USER_ID;
    
    public MovieQuery(int id) {
        this.USER_ID = id;
    }
    
    public boolean insertMovie(Movie movie) {
        
        boolean movieAdded = true;
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps; 
        
        try {
            ps = connection.prepareStatement("INSERT INTO `movies`(`name`, `category`, `releasedate`, `rating`, `imdbscore`, `image_path`, `userid`) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getCategory());
            ps.setString(3, movie.getReleasedate());
            ps.setFloat(4, movie.getRating());
            ps.setFloat(5, movie.getImdbscore());
            ps.setString(6, movie.getImagePth());
            ps.setInt(7, movie.getUserid());
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Movie added!");
                movieAdded = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "Something went wrong. Try again");
                movieAdded = false;
            }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movieAdded;
    }
    
    public void deleteMovie(int id){
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps;
        
        try {
            ps = connection.prepareStatement("DELETE FROM `movies` WHERE `id` = ? AND `userid` = ?");
            ps.setInt(1, id);
            ps.setInt(2,USER_ID);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Movie deleted!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Something went wrong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public boolean updateMovie(Movie movie, int id) {
        boolean movieEdited = true;
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps; 
        
        try {
            ps = connection.prepareStatement("UPDATE `movies` SET `name`=?,`category`=?,`releasedate`=?,`rating`=?,`userid`=? WHERE `id` = ? AND `userid` = ?");
            ps.setInt(6,id);
            ps.setInt(7,USER_ID);
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getCategory());
            ps.setString(3, movie.getReleasedate());
            ps.setFloat(4, movie.getRating());
            ps.setInt(5, movie.getUserid());
            
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Movie edited!");
                movieEdited = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "Something went wrong. Try again");
                movieEdited = false;
            }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movieEdited;
    }
    
    public Movie getMovie(int id) {
        
        Movie movie = new Movie();
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        
        try {
            ps = connection.prepareStatement("SELECT * FROM `movies` WHERE `id` = ? AND `userid` = ?");
            ps.setInt(1, id);
            ps.setInt(2,USER_ID);
            rs = ps.executeQuery();
            
            if(rs.next()){
                movie = new Movie(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("releasedate"),
                        rs.getFloat("rating"),
                        rs.getFloat("imdbscore"),
                        rs.getString("image_path"),
                        0
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movie;
    }
    
    public ArrayList<Movie> getMovieList() {
        
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        
        try {
            ps = connection.prepareStatement("SELECT `id`, `name`, `category`, `releasedate`, `rating`, `imdbscore`, `image_path` FROM `movies` WHERE `userid` = ?");
            ps.setInt(1,USER_ID);
            rs = ps.executeQuery();
            
            Movie movie;
            
            while(rs.next()){
                movie = new Movie(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("releasedate"),
                        rs.getFloat("rating"),
                        rs.getFloat("imdbscore"),
                        rs.getString("image_path"),
                        0
                );
                
                movieList.add(movie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return movieList;
    }
    

public ArrayList<Movie> getMovieListByCateg(String category) {
        
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement ps;
        ResultSet rs;
       
        
        try {
            ps = connection.prepareStatement("SELECT `id`, `name`, `category`, `releasedate`, `rating`, `imdbscore`, `image_path` FROM `movies` WHERE `category` = ? AND `userid` = ?");
            ps.setString(1, category);
            ps.setInt(2,USER_ID);
            rs = ps.executeQuery();
            
            Movie movie;
            
            while(rs.next()){
                movie = new Movie(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("releasedate"),
                        rs.getFloat("rating"),
                        rs.getFloat("imdbscore"),
                        rs.getString("image_path"),
                        0
                );
                
                movieList.add(movie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return movieList;
    }
}
