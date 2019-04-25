import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import gov.nasa.jpf.vm.Verify;

class Worker implements Runnable {
    Socket sock;
    PrintWriter out;
    BufferedReader in;
    ChatServer chatServer;
    int idx;

    public Worker(Socket s, ChatServer cs) throws IOException{
        chatServer = cs;
        sock = s;
        try{
            if(Verify.getBoolean()) {
                throw new IOException("Simulated exception");
            }
                out = new PrintWriter(sock.getOutputStream(), true);
                in = new BufferedReader(new
                        InputStreamReader(sock.getInputStream()));

        }catch(IOException e){
            throw new IOException("resource(s) cannot be initialized");
        }
    }

    public void run() {
        System.out.println("Thread running: " + Thread.currentThread());
    	idx = chatServer.n;
    	chatServer.n++;
	try {
            //out
            assert(out != null);
    	    assert(chatServer.workers.get(idx) == null);
            System.err.println("Adding "+idx+" to workers");
    	    chatServer.workers.put(idx, this);
    	    System.out.println("Registered worker " + idx + ".");
            //in
            String s = null;
            while ((s = in.readLine()) != null) {
                chatServer.sendAll("[" + idx + "]" + s);
            }
            sock.close();
        } catch(IOException ioe) {
            System.out.println("Worker thread " + idx + ": " + ioe);
	} finally {
            chatServer.remove(idx);
        }
    }

    public void send(String s) {
        out.println(s);
    }
}

public class ChatServer {
    HashMap<Integer, Worker> workers = new HashMap<>();
    int n = 0;

    public ChatServer(int maxServ) {
        int port = 4444;
        boolean init = true;
        Socket sock;
	    ServerSocket servsock = null;
        try {
            servsock = new ServerSocket(port);
            while (maxServ-- != 0) {
                sock = servsock.accept();
                Worker worker = null;
                try{
                    worker = new Worker(sock, this);
                }catch(IOException e){
                    init = false;
                }
                if(init){
                    //assert(false);
                    new Thread(worker).start();
                }
            }
	    //servsock.close();
        } catch(IOException ioe) {
            System.err.println("Server: " + ioe);
        }
        System.out.println("Server shutting down.");
    }

    public static void main(String args[]) throws IOException {
	if (args.length == 0) {
            new ChatServer(-1);
	} else {
	    new ChatServer(Integer.parseInt(args[0]));
        }
    }

    public synchronized void sendAll(String s) {
        for (Worker w: workers.values()) {
            w.send(s);
        }
    }

    public synchronized void remove(int i) {
	    //workers[i] = null;
        workers.remove(i);
        sendAll("Client " + i + " quit.");
    }
}
