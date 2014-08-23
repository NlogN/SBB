package ru.sbb.app;

import ru.sbb.RegService;
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
    private  Socket sock;
    private SocketAddress sockAddr;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Socket getSock(){
         return sock;
    }


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

//        try {} catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
            this.output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
            send(new OverRequest(""));
            this.input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
            receive();




        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

            Client client = new Client();
            client.send(new GetStationScheduleRequest("Moskow"));
//            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(client.getSock().getInputStream()));
           client.receive();
        client.send(new GetTrainPassengersRequest(123,"123"));
        client.receive();
           client.close();

    }

    public void send(Request m) throws IOException {
        //System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    public String receive() throws IOException {
        System.out.print("Receiving a message from " + sockAddr + ": ");
        Message m = null;
        try {
            m = (Message) input.readObject();
            System.out.println(m);
            return m.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

}