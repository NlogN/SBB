package ru.sbb;


import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.GetTrainPassengersRequest;
import ru.sbb.request.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 19.08.14
 */

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket serverSock = new ServerSocket(8070);


        while (true) {
            Socket clientSock = serverSock.accept();


            SocketAddress sockAddr = clientSock.getRemoteSocketAddress();
            System.out.println("Processing new connection from " + sockAddr);

            ObjectInputStream input = new ObjectInputStream(
                    new BufferedInputStream(clientSock.getInputStream()));
            Request req = receive(sockAddr, input);
            ObjectOutputStream output = new ObjectOutputStream(
                    new BufferedOutputStream(clientSock.getOutputStream()));

            switch (req.getType()) {
                case GET_STATION_SCHEDULE: {
                    GetStationScheduleRequest request = (GetStationScheduleRequest) req;
                    stationScheduleRequestMethod(sockAddr, request, output);
                    break;
                }
                case GET_TRAIN_PASSENGERS: {
                    GetTrainPassengersRequest request = (GetTrainPassengersRequest) req;
                    RequestService requestService = new RequestService();
                    String res = requestService.getPassengersByTrainInfo(request.getTrainNum(), requestService.entityManager);
                    System.out.println(res);
                    break;
                }
                default:
                    System.out.println();
                    break;
            }


                  send(sockAddr, output, new GetStationScheduleRequest("Hello, I'm a server!"));
            //  receive(sockAddr, input);
            //  System.out.println("ru.sbb.Server thinking on the answer for " + sockAddr);
            //  Thread.sleep(10000);
            //  send(sockAddr, output, new Message("Fine, thanks! And you?"));


        }

    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Request m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, String s) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + s);
        output.writeObject(s);
        output.flush();
    }

    static void stationScheduleRequestMethod(SocketAddress sockAddr,GetStationScheduleRequest request,ObjectOutputStream output) throws IOException {
        RequestService requestService = new RequestService();
        String res = requestService.printStationSchedule(request.getStationName(), requestService.entityManager);
        System.out.println(res);
        send(sockAddr,output,res);
    }

    private static Request receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.println("Receiving a message from " + sockAddr + ": ");
        Request m = (Request) input.readObject();
        return m;



//        System.out.println("Receiving a message from " + sockAddr + ": ");
//        GetStationScheduleRequest obj = null;
//        while ((obj = (GetStationScheduleRequest)input.readObject()) != null) {
//            System.out.println(obj);
//        }
    }

}