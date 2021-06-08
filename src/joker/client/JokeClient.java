/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import joker.models.Joke;

/**
 *
 * @author juandre
 */
public class JokeClient {
    private Socket socket;
    private final String HOST;
    private final int PORT;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    public JokeClient(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public Joke getJoke() {
        Joke joke = null;
        if (socket != null) {
            try {
                outStream.writeBoolean(true);
                outStream.flush();
                System.out.println("Wrote boolean");
                joke = (Joke) inStream.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return joke;
    }

    public boolean connect() {
        boolean isConnected = true;
        if (socket == null || socket.isClosed()) {
            try {
                this.socket = new Socket(HOST, PORT);
                inStream = new ObjectInputStream(socket.getInputStream());
                outStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                isConnected = false;
            }
        }
        return isConnected;
    }

    public void exit() {
        if (socket != null) {
            try {
                outStream.writeBoolean(false);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (outStream != null) {
            try {
                outStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
