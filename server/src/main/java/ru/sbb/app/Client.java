package ru.sbb.app;

import ru.sbb.request.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */

public class Client {
    private Socket sock;
    private SocketAddress sockAddr;
    private ObjectOutputStream output;
    private ObjectInputStream input;



    void close() throws IOException {
        sock.close();
    }

    public Client() throws IOException {
        try {
            sock = new Socket("localhost", 8070);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sockAddr = sock.getRemoteSocketAddress();

        this.output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        send(new OverRequest(""));
        this.input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
        receive();


        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
    }


    public void send(Request m) throws IOException {
        output.writeObject(m);
        output.flush();
    }

    public String receive() throws IOException {
        System.out.print("Receiving a message from " + sockAddr + ": ");
        Response m = null;
        try {
            m = (Response) input.readObject();
            System.out.println(m);
            return m.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

}