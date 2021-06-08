/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.interfaces;

import joker.models.Joke;

/**
 *
 * @author juandre
 */
public interface IJokeDao {
    public abstract Joke getRandomJoke();
    public abstract Joke getJokeById(int id);
}
