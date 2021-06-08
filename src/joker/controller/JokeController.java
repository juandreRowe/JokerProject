/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.controller;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import joker.dao.JokeDao;
import joker.interfaces.IJokeDao;
import joker.models.Joke;

/**
 *
 * @author juandre
 */
public class JokeController {
    private IJokeDao dao;
    private Connection connection;
    
    public JokeController(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("src/joker/server/login.properties"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            dao = new JokeDao(connection);
        }catch(SQLException | IOException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public Joke getRandomJoke(){
        return dao.getRandomJoke();
    }
    
    public Joke getJokeById(int id){
        return dao.getJokeById(id);
    }
    
    public void exit(){
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
