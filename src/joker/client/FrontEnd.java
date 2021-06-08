/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.client;

import joker.models.Joke;

/**
 *
 * @author juandre
 */
public class FrontEnd {
    public static void main(String[] args) {
        new FrontEnd().runForrestRun();
    }
    
    private void runForrestRun(){
        JokeClient jokeClient = new JokeClient("localhost", 6969);
        if(jokeClient.connect()){
            System.out.println("Connected");
            Joke joke = jokeClient.getJoke();
            System.out.println(joke);
            joke = jokeClient.getJoke();
            System.out.println(joke);
        }
        jokeClient.exit();
    }
}