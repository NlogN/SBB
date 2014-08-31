package ru.sbb;


import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.request.Request;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;
import ru.sbb.request.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 20.08.14
 */

public class Server {
    private ClientService clientService;
    private ManagerService managerService;
    private final RegistrationService regService = new RegistrationService();
    private static Logger log = Logger.getLogger(Server.class);


    Server(ClientService clientService, ManagerService managerService) {
        this.clientService = clientService;
        this.managerService = managerService;
    }



    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("server\\src\\main\\resources\\META-INF\\log4j.properties");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TrainDAO trainDAO = new TrainDAOImpl(entityManager);
        TicketDAO ticketDAO = new TicketDAOImpl(entityManager);
        ScheduleRecordDAO scheduleRecordDAO = new ScheduleRecordDAOImpl(entityManager);
        PassengerDAO passengerDAO = new PassengerDAOImpl(entityManager);
        StationDAO stationDAO = new StationDAOImpl(entityManager);

        ClientService clientService = new ClientService(ticketDAO, trainDAO, scheduleRecordDAO, passengerDAO);
        ManagerService managerService = new ManagerService(passengerDAO, trainDAO, stationDAO, scheduleRecordDAO);

        Server server = new Server(clientService, managerService);
        server.start();

    }


    void start() throws IOException {
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

                        while (true) {
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
                                        send(sockAddr, output, new Response(""));
                                        break;
                                }
                            } catch (EOFException e) {
                                log.info("End of inputStream");
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


    void getTrainByRouteMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetTrainsByRouteRequest request = (GetTrainsByRouteRequest) req;
        String res = clientService.getTrainsByRoute(request.getLowerBound(), request.getUpperBound(), request.getStationAName(), request.getStationBName());

        send(sockAddr, output, new Response(res));
    }

    void getStationScheduleRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetStationScheduleRequest request = (GetStationScheduleRequest) req;
        String res;
        try {
            res = clientService.getStationSchedule(request.getStationName());
        } catch (StationNotFoundException stationNotFoundExeption) {
            res = "No schedule found";
        }

        send(sockAddr, output, new Response(res));
    }

    void getTrainPassengersRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetTrainPassengersRequest request = (GetTrainPassengersRequest) req;
        String res;
        if (regService.checkPassword(request.getPassword())) {
            res = managerService.getPassengersInfoByTrainNum(request.getTrainNum());
        } else {
            res = "incorrect password";
        }


        send(sockAddr, output, new Response(res));
    }

    void addTrainRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddTrainRequest request = (AddTrainRequest) req;
        String res;
        if (regService.checkPassword(request.getPassword())) {
            managerService.addTrain(request.getNumber(), request.getCapacity());
            res = "train added";
        } else {
            res = "incorrect password";
        }

        send(sockAddr, output, new Response(res));
    }

    void addScheduleRecordRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddScheduleRecordRequest request = (AddScheduleRecordRequest) req;
        String res;
        if (regService.checkPassword(request.getPassword())) {
            try {
                managerService.addScheduleRecord(request.getStationName(), request.getTrainNumber(), request.getTime(), request.getOffset());
                res = "shedule record added";
            } catch (StationNotFoundException stationNotFoundException) {
                res = stationNotFoundException.getMessage();
            } catch (TrainNotFoundException trainNotFoundException) {
                res = trainNotFoundException.getMessage();
            }

        } else {
            res = "incorrect password";
        }

        send(sockAddr, output, new Response(res));
    }

    void addStationRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        AddStationRequest request = (AddStationRequest) req;
        String res;
        if (regService.checkPassword(request.getPassword())) {
            managerService.addStation(request.getName());
            res = "station added";
        } else {
            res = "incorrect password";
        }

        send(sockAddr, output, new Response(res));
    }

    void getAllTrainsRequestMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        GetAllTrainsRequest request = (GetAllTrainsRequest) req;
        String res;
        if (regService.checkPassword(request.getPassword())) {
            res = managerService.getTrainNumbers();
        } else {
            res = "incorrect password";
        }

        send(sockAddr, output, new Response(res));
    }

    void buyTicketMethod(SocketAddress sockAddr, Request req, ObjectOutputStream output) throws IOException {
        BuyTicketRequest request = (BuyTicketRequest) req;
        Passenger passenger = new Passenger();
        passenger.setName(request.getName());
        passenger.setSurname(request.getSurname());
        passenger.setDate(request.getDayOfBirth());

        try {
            clientService.buyTicket(request.getTrainNumber(), request.getStationName(), passenger, request.getDateOfRace());
            send(sockAddr, output, new Response("operation was successful"));
        } catch (BuyTicketException buyTicketException) {
            send(sockAddr, output, new Response("you can not buy a ticket: \n " + buyTicketException.getMessage()));
        } catch (TrainNotFoundException trainNotFoundException) {
            send(sockAddr, output, new Response("you can not buy a ticket: \n " + trainNotFoundException.getMessage()));
        }

    }

    private void send(SocketAddress sockAddr, ObjectOutputStream output, Response m) throws IOException {
        log.info("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private Request receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
//        System.out.println("Receiving a message from " + sockAddr + ": ");
        Request m = (Request) input.readObject();
        return m;

    }

}
