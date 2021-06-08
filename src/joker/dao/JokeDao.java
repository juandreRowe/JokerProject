/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import joker.interfaces.IJokeDao;
import joker.models.Joke;

/**
 *
 * @author juandre
 */
public class JokeDao implements IJokeDao {
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public JokeDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public Joke getRandomJoke() {
        Joke joke = null;
        if(connection != null){
            try{
                preparedStatement = connection.prepareStatement("select max(id) from JokeTable");
                resultSet = preparedStatement.executeQuery();
                int max = 0;
                while(resultSet.next()){
                    max = resultSet.getInt("max(id)");
                }
                int randomJoke = ((int)(Math.random() * max)) + 1;
                joke = getJokeById(randomJoke);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }finally{
                this.closePreparedStatement();
            }
        }
        return joke;
    }

    @Override
    public Joke getJokeById(int id) {
        Joke joke = null;
        if(connection != null){
            try{
                preparedStatement = connection.prepareCall("select id, joke from JokeTable where id = ?");
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    joke = new Joke(resultSet.getInt("id"), resultSet.getString("joke"));
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }finally{
                this.closePreparedStatement();
            }
        }
        return joke;
    }
    
    private void closePreparedStatement(){
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
