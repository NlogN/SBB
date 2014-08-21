package ru.sbb;


import ru.sbb.request.*;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 20.08.14
 */

public class Server {
    static Logger log = Logger.getLogger(SbbEntityManager.class.getName());

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
                                        stationScheduleRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case GET_TRAIN_PASSENGERS: {
                                        trainPassengersRequestMethod(sockAddr,req,output);
                                        break;
                                    }
                                    case GET_ALL_TRAIN_LIST: {
                                        trainRequestMethod(sockAddr,req,output);
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


    static void stationScheduleRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetStationScheduleRequest request = (GetStationScheduleRequest) req;
        String res = ClientService.getInstance().getStationSchedule(request.getStationName());
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void trainPassengersRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetTrainPassengersRequest request = (GetTrainPassengersRequest) req;
        String res;
        if(RegService.getInstance().checkPassword(request.getPassword())){
            res = ManagerService.getInstance().getPassengersByTrainInfo(request.getTrainNum());
        } else{
            res = "incorrect password";
        }
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void trainRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetAllTrainsRequest request = (GetAllTrainsRequest) req;
        String res;
        if(RegService.getInstance().checkPassword(request.getPassword())){
             res = ManagerService.getInstance().getTrainNumbers();
        } else{
            res = "incorrect password";
        }
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Message m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private static Request receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.println("Receiving a message from " + sockAddr + ": ");
        Request m = (Request) input.readObject();
        return m;

    }

}
