/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.models;

import java.io.Serializable;

/**
 *
 * @author juandre
 */
public class Joke implements Serializable {
    private int id;
    private String joke;
    
    public Joke(){
        
    }
    
    public Joke(String joke){
        this.joke = joke;
    }
    
    public Joke(int id, String joke){
        this(joke);
        this.id = id;
    }
    
    public void setJoke(String joke){
        this.joke = joke;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getJoke(){
        return this.joke;
    }
    
    @Override
    public String toString(){
        return "ID: " + this.getId() + ", JOKE: " + this.getJoke();
    }
}
