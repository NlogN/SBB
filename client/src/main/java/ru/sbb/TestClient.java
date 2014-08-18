package ru.sbb;

import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.Request;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 19.08.14
 */

public class TestClient {
    Socket sock = new Socket("localhost", 8080);
    SocketAddress sockAddr = sock.getRemoteSocketAddress();
    ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));

    public TestClient() throws IOException {
        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
            TestClient client = new TestClient();
            client.send(new GetStationScheduleRequest("Moskow"));
            //ru.sbb.TestClient client1 = new ru.sbb.TestClient();
           // client1.send(new GetStationScheduleRequest("Novosibirsk"));
//            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
//            receive(sockAddr, input);
//            send(sockAddr, output, new ru.sbb.request.GetStationScheduleRequest("Moskow");
//            receive(sockAddr, input);

    }

    public void send(Request m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

//    private static void receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
//        System.out.print("Receiving a message from " + sockAddr + ": ");
//        Message m = (Message) input.readObject();
//        System.out.println(m);
//    }

}