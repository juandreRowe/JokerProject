/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import joker.controller.JokeController;

/**
 *
 * @author juandre
 */
public class JokeServer {
    private ServerSocket serverSocket;
    private final JokeController jokeController;    
    
    public JokeServer(){
        try{
            Properties properties = new Properties();
            properties.load(new FileReader("src/joker/server/login.properties"));
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        jokeController = new JokeController();
        while(true){
            try{
                new JokeThread(serverSocket.accept()).start();
            }catch(IOException ex){
                System.out.println();
            }
        }
    }
    
    private class JokeThread extends Thread {
        private final Socket socket;
        private ObjectOutputStream outStream;
        private ObjectInputStream inStream;
        
        public JokeThread(Socket socket){
            this.socket = socket;
            try{
                outStream = new ObjectOutputStream(socket.getOutputStream());
                inStream = new ObjectInputStream(socket.getInputStream()); 
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        
        @Override
        public void run(){
            while(true){
                try{
                    boolean nextJoke = inStream.readBoolean();
                    if(!nextJoke){
                        break;
                    }
                    outStream.writeObject(jokeController.getRandomJoke());
                    outStream.flush();
                    System.out.println("Write a joke...>>>");
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                    break;
                }
            }
            System.out.println("Client closing down...>>>");
            if(outStream != null){
                try{
                    outStream.close();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(inStream != null){
                try{
                    inStream.close();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(socket != null){
                try{
                    socket.close();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new JokeServer();
    }
}