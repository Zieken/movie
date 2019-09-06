/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Movie;

/**
 *
 * @author Kasper Jeppesen
 */
public class MovieDTO 
{
    private String title, author;
    
    public MovieDTO(Movie movie)
    {
        this.title = movie.getTitle();
        this.author = movie.getAuthor();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    
    

    
    
    
    
}
