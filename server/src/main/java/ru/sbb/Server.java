package ru.sbb;


import ru.sbb.entity.Passenger;
import ru.sbb.request.*;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                                        getStationScheduleRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case GET_TRAIN_PASSENGERS: {
                                        getTrainPassengersRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case GET_ALL_TRAIN_LIST: {
                                        getAllTrainsRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case ADD_TRAIN: {
                                        addTrainRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case BUY_TICKET: {
                                        buyTicketMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case ADD_STATION: {
                                        addStationRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case ADD_SHEDULE_RECORD: {
                                        addScheduleRecordRequestMethod(sockAddr, req, output);
                                        break;
                                    }
                                    case TRAIN_BY_ROUTE: {
                                        getTrainByRouteMethod(sockAddr, req, output);
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

    static void getTrainByRouteMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetTrainsByRouteRequest request = (GetTrainsByRouteRequest) req;
        String res = ClientService.getInstance().getTrainsByRoute(request.getLowerBound(),request.getUpperBound(),request.getStationAName(),request.getStationBName());
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void getStationScheduleRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetStationScheduleRequest request = (GetStationScheduleRequest) req;
        String res = ClientService.getInstance().getStationSchedule(request.getStationName());
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void getTrainPassengersRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
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

    static void addTrainRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddTrainRequest request = (AddTrainRequest) req;
        String res;
        if(RegService.getInstance().checkPassword(request.getPassword())){
              ManagerService.getInstance().addTrain(request.getNumber(),request.getCapacity());
            res = "train added";
        } else{
            res = "incorrect password";
        }
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void addScheduleRecordRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddScheduleRecordRequest request = (AddScheduleRecordRequest) req;
        String res;
        if(RegService.getInstance().checkPassword(request.getPassword())){
            ManagerService.getInstance().addScheduleRecord(request.getStationName(), request.getTrainNumber(), request.getTime(), request.getOffset());
            res = "shedule record added";
        } else{
            res = "incorrect password";
        }
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void addStationRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddStationRequest request = (AddStationRequest) req;
        String res;
        if(RegService.getInstance().checkPassword(request.getPassword())){
            ManagerService.getInstance().addStation(request.getName());
            res = "station added";
        } else{
            res = "incorrect password";
        }
        System.out.println(res);
        send(sockAddr, output, new Message(res));
    }

    static void getAllTrainsRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
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

    static void buyTicketMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        BuyTicketRequest request = (BuyTicketRequest) req;

        Passenger passenger = new Passenger();
        passenger.setName(request.getName());
        passenger.setSurname(request.getSurname());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        java.util.Date dateOfBirth = null;
        java.util.Date dateOfRace = null;
        try {
            dateOfBirth = formatter.parse(request.getDayOfBirth());
            dateOfRace = formatter.parse(request.getDateOfRace());
        } catch (ParseException e) {
            send(sockAddr, output, new Message("incorrect date format"));
            e.printStackTrace();
        }

        passenger.setDate(dateOfBirth);

        boolean res = ClientService.getInstance().buyTicket(request.getTrainNumber(),request.getStationName(),passenger,dateOfRace);

        if(res){
            send(sockAddr, output, new Message("operation was successful"));
        } else{
            send(sockAddr, output, new Message("you could not buy a ticket"));
        }

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
