/* $Id: ChatClient.java 723 2009-09-24 07:48:58Z cartho $ */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClient {
    int id;
    static int currID = 0;

    public final static void main(String args[]) {
        new ChatClient().exec();
    }

    public ChatClient() {
        synchronized(getClass()) {
            System.out.println("id before" + id);
            int newID = (currID+1);
            System.out.println("currID " + newID);
            id = newID;
            System.out.println("id after" + id);
        }
    }

    public void exec() {
        try {
            Socket socket = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 4444);
            socket.connect(addr);
            System.out.println("Client " + id + " connected.");
            InputStreamReader istr =
                new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(istr);
            OutputStreamWriter out =
                new OutputStreamWriter(socket.getOutputStream());
            out.write(id + ": Hello, world!\n");
            out.flush();
            for (int i = 0; i < 1; i++) {
                String recieved = in.readLine();
                System.out.println(id + ": Received " + recieved);
                String[] recParts = recieved.split(":");
                if(recieved.matches("\\[[01]\\]0: Hello, world!")){
                    System.out.println("true");
                }
                assert(recieved.matches("\\[\\d\\]0: Hello, world!")||recParts[1].trim().equals("Server busy"));

            }
            out.close();
        } catch(IOException e) {
            System.err.println("Client " + id + ": " + e);
        }
    }
}
