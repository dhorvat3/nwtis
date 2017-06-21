/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.klijenti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davorin Horvat
 */
public class SocketClient {
     private int port = 5589;
    private String host = "localhost";
    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public SocketClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public SocketClient() {

    }

    public void connect() throws IOException {
        System.out.println("--- SPAJANJE ---");
        socket = new Socket(host, port);

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public String sendMessage(String msg) {
        System.out.println("--- SLANJE PORUKE ---");
        String response = "";
        if (outputStream != null) {
            try {
                outputStream.write(msg.getBytes());
                outputStream.flush();
                socket.shutdownOutput();

                StringBuffer odgovor = getInputStreamString(inputStream);
                System.out.println("Odgovor: " + odgovor);
                response = odgovor.toString();
            } catch (IOException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return response;
    }
    
    private StringBuffer getInputStreamString(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int znak = inputStream.read();
            if (znak == -1) {
                break;
            }
            stringBuffer.append((char) znak);
        }

        return stringBuffer;
    }
}
