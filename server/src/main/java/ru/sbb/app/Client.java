package ru.sbb.app;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 20.08.14
 */

import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.GetTrainPassengersRequest;
import ru.sbb.request.Message;
import ru.sbb.request.Request;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket sock = new Socket("localhost", 8070);
        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
        SocketAddress sockAddr = sock.getRemoteSocketAddress();
        ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        //send(sockAddr, output, new GetStationScheduleRequest("Saint-Peterburg"));
        send(sockAddr, output, new GetTrainPassengersRequest(239));
        ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
        receive(sockAddr, input);

//        send(sockAddr, output, new GetStationScheduleRequest("Moskow"));
//        receive(sockAddr, input);

    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Request m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private static void receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.println("Receiving a message from " + sockAddr + ": ");
        Message m = (Message) input.readObject();
        System.out.println(m);
    }

}