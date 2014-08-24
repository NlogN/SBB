package ru.sbb.test;


import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.OverRequest;
import ru.sbb.request.Request;
import ru.sbb.request.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 20.08.14
 */

public class TestClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket sock = new Socket("localhost", 8070);
        System.out.print("Connected to 8080 at localhost");
        System.out.println("Local socket address is " + sock.getLocalSocketAddress());
        SocketAddress sockAddr = sock.getRemoteSocketAddress();
        ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        send(sockAddr, output, new OverRequest(""));
        ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
        receive(sockAddr, input);
        send(sockAddr, output, new GetStationScheduleRequest("Saint-Petersburg"));
       // send(sockAddr, output, new BuyTicketRequest("Peter","Petrov","1991/01/01",239,"Novosibirsk","2014/08/22"));

      // send(sockAddr, output, new GetTrainPassengersRequest(239,"123"));
      //  receive(sockAddr, input);
//        try {
//            Date time = DateBuilder.createDateTime("1991/01/01 12:01:01");
//            send(sockAddr, output, new AddScheduleRecordRequest("Bergorod",239,time,23,"123"));
//            receive(sockAddr, input);
//        } catch (ParseException e) {
//        }

        //
        //send(sockAddr, output, new GetTrainPassengersRequest(123,"123"));
        receive(sockAddr, input);

        sock.close();

    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Request m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private static String receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.println("Receiving a message from " + sockAddr + ": ");
        Response m = (Response) input.readObject();
        System.out.println(m.getText());
        return  m.getText();
    }

}