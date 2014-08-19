package ru.sbb.app;

import ru.sbb.request.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 19.08.14
 */

public class TestClient {
    public static Socket sock;
    static SocketAddress sockAddr;
    static ObjectOutputStream output;

    public Socket getSock(){
         return sock;
    }

    static{
        try {
            sock = new Socket("localhost", 8070);
            output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sockAddr = sock.getRemoteSocketAddress();

    }

    public TestClient() throws IOException {
        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//            TestClient client = new TestClient();
//            client.send(new GetStationScheduleRequest("Moskow"));
//           // client.send(new GetStationScheduleRequest("Moskow1"));
//           // output.flush();
////            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
////            receive(sockAddr, input);
//
//
//    }

    public void send(Request m) throws IOException {
        //System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    public void receive(ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.print("Receiving a message from " + sockAddr + ": ");
        Request m = (Request) input.readObject();
        System.out.println(m);
    }

}