package ru.sbb;


import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.GetTrainPassengersRequest;
import ru.sbb.request.Message;
import ru.sbb.request.Request;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 20.08.14
 */

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSock = new ServerSocket(8070);

        System.out.println("Listening on 8070 at localhost");
        while (true) {
            final Socket clientSock = serverSock.accept();
            new Thread(new Runnable() {
                public void run() {
                    SocketAddress sockAddr = clientSock.getRemoteSocketAddress();
                    System.out.println("Processing new connection from " + sockAddr);
                    try {
                        InputStream inputStream = clientSock.getInputStream();
                        ObjectInputStream input = new ObjectInputStream(
                                new BufferedInputStream(inputStream));
                        ObjectOutputStream output = new ObjectOutputStream(
                                new BufferedOutputStream(clientSock.getOutputStream()));

                        while (true){
                            try {
                                Request req = receive(sockAddr, input);

                                switch (req.getType()) {
                                    case GET_STATION_SCHEDULE: {
                                        GetStationScheduleRequest request = (GetStationScheduleRequest) req;
                                        stationScheduleRequestMethod(sockAddr, request, output);
                                        break;
                                    }
                                    case GET_TRAIN_PASSENGERS: {
                                        GetTrainPassengersRequest request = (GetTrainPassengersRequest) req;
                                        String res = ManagerService.getInstance().getPassengersByTrainInfo(request.getTrainNum());
                                        send(sockAddr, output, new Message(res));
                                        break;
                                    }
                                    default:
                                        System.out.println();
                                        send(sockAddr, output, new Message(""));
                                        break;
                                }
                            } catch (EOFException e){
                                System.out.println("End of stream");
                                break;
                            }
                        }


                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (clientSock != null) {
                            try {
                                clientSock.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }).start();
        }

    }


    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Message m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }


    static void stationScheduleRequestMethod(SocketAddress sockAddr, GetStationScheduleRequest request, ObjectOutputStream output) throws IOException {
        String res = ClientService.getInstance().getStationSchedule(request.getStationName());
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    private static Request receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.println("Receiving a message from " + sockAddr + ": ");
        Request m = (Request) input.readObject();
        return m;

    }

}
