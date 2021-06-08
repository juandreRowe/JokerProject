/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import joker.models.Joke;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author juandre
 */
public class ClientGUI extends JFrame {
    private final JokeClient client;
    private final JPanel top_panel, main_panel, bottom_panel;
    private final JLabel message_label;
    private final JTextField message_field;
    private final JTextArea joke_area;
    private final JButton connect_button, getJoke_button, close_button;
    private final JScrollPane joke_pane;

    public ClientGUI() {
        this.setTitle("Joker Time");
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("src/joker/server/login.properties"));
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.exit(WIDTH);
        }
        client = new JokeClient(properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));

        Font font = new Font("Purisa", Font.BOLD, 25);

        message_label = new JLabel("Message");
        message_field = new JTextField(30);
        message_field.setEditable(false);
        message_field.setText("Disconnected");
        message_field.setForeground(Color.RED);

        message_label.setFont(font);
        message_field.setFont(font);

        top_panel = new JPanel();
        top_panel.add(message_label);
        top_panel.add(message_field);

        joke_area = new JTextArea();
        joke_area.setEditable(false);
        joke_pane = new JScrollPane(joke_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        joke_area.setFont(font);

        main_panel = new JPanel();
        main_panel.setLayout(new GridLayout(1, 1));
        main_panel.add(joke_pane);

        connect_button = new JButton("Connect");
        getJoke_button = new JButton("Get Joke");
        close_button = new JButton("Close");

        connect_button.setFont(font);
        getJoke_button.setFont(font);
        close_button.setFont(font);

        bottom_panel = new JPanel();
        bottom_panel.setLayout(new GridLayout(1, 3));
        bottom_panel.add(connect_button);
        bottom_panel.add(getJoke_button);
        bottom_panel.add(close_button);

        this.add(top_panel, BorderLayout.NORTH);
        this.add(main_panel, BorderLayout.CENTER);
        this.add(bottom_panel, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(700, 500));
        this.pack();
        this.setVisible(true);

        ActionListener buttonListener = new MyButtonListener();
        connect_button.addActionListener(buttonListener);
        getJoke_button.addActionListener(buttonListener);
        close_button.addActionListener(buttonListener);

        this.addWindowListener(new MyWindowListener());
    }

    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == connect_button) {
                String message = "Disconnected";
                if(client.connect()){
                    message_field.setForeground(Color.BLUE);
                    message = "Connected";
                    getJokeFromServer();
                }else{
                    message_field.setForeground(Color.RED);
                }
                message_field.setText(message);
            } else if (source == getJoke_button) {
                getJokeFromServer();
            } else if (source == close_button) {
                client.exit();
                message_field.setText("Disconnected");
                message_field.setForeground(Color.RED);
            }
        }

        private void getJokeFromServer() {
            Joke joke = client.getJoke();
            if (joke != null) {
                joke_area.setText("");
                joke_area.setText(joke.getJoke());
            }
        }
    }

    private class MyWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            client.exit();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}