package ru.sbb;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;

public class RequestService {
    static Logger log = Logger.getLogger(RequestService.class.getName());
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
    public EntityManager entityManager = entityManagerFactory.createEntityManager();

    RequestService(){

    }

    void close(){
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void main(String[] args) throws ParseException {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        System.out.println("\n get passenger by train number:\n-------------");
//        List<ru.sbb.Passenger> passengerList = getPassengersByTrain(123, entityManager);
//        for (ru.sbb.Passenger passenger : passengerList) {
//            System.out.println(passenger);
//        }

//        System.out.println("\n print station shedule:\n-------------");
//        getStationSchedule("Moskow", entityManager);

//        System.out.println("\n train number list:\n-------------");
//        for (String s : getTrainNumberList(entityManager)) {
//            System.out.println(s);
//        }

//        addTrain(239,200,entityManager);
        // addStation("Petrozavodsk",entityManager);


//        System.out.println("\n check capacity:\n-------------");
//        ru.sbb.Train train = new ru.sbb.Train();
//        train.setNumber(239);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Date utilDate = formatter.parse("2013/05/03");
//        Date curDate = new Date();
//        checkNotFilledState(train, curDate, entityManager);
//        printSchedule(entityManager);

//        System.out.println("\n A to B \n-------------");
//        Date date1 = createDate(2014, 8, 17, 11, 1, 2);
//        Date date2 = createDate(2014, 8, 17, 16, 1, 2);
//        //List<ru.sbb.Train> list = getTrainOnRouteABList(date1,date2,"Moskow","Saint-Peterburg",entityManager);
//        List<ru.sbb.Train> list = getTrainOnRouteABList(date1,date2,"Moskow","Novosibirsk",entityManager);
//        for (ru.sbb.Train train : list) {
//            System.out.println(train);
//        }

        //addSchedule("Moskow",239,createDate(2014, 8, 17, 11, 1, 2),15, entityManager);
        //buyTicket(239, "Moskow", "Rubens", "Barikello", createDate(1886, 8, 17, 11, 1, 2), createDate(2013, 5, 3), entityManager);

//        entityManager.close();
//        entityManagerFactory.close();
    }


    static List<Train> getTrainOnRouteABList(Date lowerBound, Date upperBound, String stationAName, String stationBName, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.Train tr");
        List<Train> allTrainList = query.getResultList();
        List<Train> trainFromAToBList = new ArrayList<Train>();
        for (Train train : allTrainList) {
            if (checkTrainRoute(train, lowerBound, upperBound, stationAName, stationBName)) {
                trainFromAToBList.add(train);
            }
        }
        return trainFromAToBList;
    }

    static boolean checkTrainRoute(Train train, Date lowerBound, Date upperBound, String stationAName, String stationBName) {
        List<Schedule> scheduleList = train.getScheduleList();
        Schedule scheduleA = null;
        Schedule scheduleB = null;
        for (Schedule schedule : scheduleList) {
            if (scheduleA != null && scheduleB != null) {
                break;
            }
            if (schedule.getStation().getName().equals(stationAName)) {
                scheduleA = schedule;
            }
            if (schedule.getStation().getName().equals(stationBName)) {
                scheduleB = schedule;
            }
        }
        if (scheduleA != null && scheduleB != null) {
            if (scheduleA.getUnixTime() < scheduleB.getUnixTime()) {
                if (getUnixTime(lowerBound) < scheduleA.getUnixTime() && scheduleB.getUnixTime() < getUnixTime(upperBound)) {
                    return true;
                }
            }
        }
        return false;
    }

    static List<Passenger> getPassengersByTrain(int trainNum, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT ts.passenger FROM ru.sbb.Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }

     String getPassengersByTrainInfo(int trainNum, EntityManager entityManager) {
        List<Passenger> passengerList = getPassengersByTrain(trainNum,entityManager);
        StringBuffer sb = new StringBuffer();
        for (Passenger passenger : passengerList) {
            sb.append(passenger.getName()+" "+passenger.getSurname() + " ;\n");
        }
        return sb.toString();
    }

     String getStationSchedule(String stationName, EntityManager entityManager) {
//        Query query = entityManager.createQuery("SELECT ts.train FROM ru.sbb.Station st join st.scheduleList ts where st.name =:stName");
//        query.setParameter("stName", stationName);
//        List<ru.sbb.Train> list = query.getResultList();
//        for (ru.sbb.Train train : list) {
//            System.out.println(train);
//        }
        Query query = entityManager.createQuery("SELECT st FROM ru.sbb.Station st where st.name =:stName");
        query.setParameter("stName", stationName);
        List<Station> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("Station not found!");
        } else {
            StringBuffer sb = new StringBuffer();
            List<Schedule> scheduleList = list.get(0).getScheduleList();
            for (Schedule schedule : scheduleList) {
             //   System.out.println(schedule.getTrain().getNumber()+" "+schedule.getTime());
                sb.append(schedule.getTrain().getNumber()+" "+schedule.getTime()+"; \n");
            }
            return sb.toString();
        }
         return "";

    }

    static boolean buyTicket(int trainNum, String stationName, String name, String surname, Date birthday, Date dateOfRace, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.Train tr where tr.number =:trNum");
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("ru.sbb.Train not found!");
        } else {
            Train train = list.get(0);
            if (checkStartTime(train, stationName) && checkSamePassengerNotReg(train, name, surname, birthday) && checkNotFilledState(train, dateOfRace, entityManager)) {

                EntityTransaction transaction = entityManager.getTransaction();
                try {

                    transaction.begin();

                    Passenger newPassenger = new Passenger();
                    newPassenger.setDate(birthday);
                    newPassenger.setName(name);
                    newPassenger.setSurname(surname);

                    Ticket newTicket = new Ticket();
                    newTicket.setDate(dateOfRace);
                    newTicket.setTrain(train);
                    newTicket.setPassenger(newPassenger);

                    List<Ticket> ticketList = new ArrayList<Ticket>();
                    ticketList.add(newTicket);
                    newPassenger.setTicketList(ticketList);

                    entityManager.persist(newPassenger);
                    entityManager.persist(newTicket);
                    transaction.commit();
                } finally {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                }

            } else {
                return false;
            }

        }

        return true;
    }


    static boolean checkStartTime(Train train, String stationName) {
        long currentTime = getCurrentTime();
        List<Schedule> scheduleList = train.getScheduleList();
        for (Schedule schedule : scheduleList) {
            if (schedule.getStation().equals(stationName) && (schedule.getUnixTime() - currentTime) > 0 && (schedule.getUnixTime() - currentTime) < 360) {
                return false;
            }
        }
        return true;
    }

    static List<Train> getTrainList(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.Train tr");
        List<Train> trainList = query.getResultList();
        return trainList;
    }


    static boolean checkNotFilledState(Train train, Date dateOfRace, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tic FROM ru.sbb.Ticket tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        return train.getCapacity() > list.size();
    }

    static boolean checkSamePassengerNotReg(Train train, String name, String surname, Date birthday) {
        List<Ticket> ticketList = train.getTicketList();
        for (Ticket ticket : ticketList) {
            Passenger passenger = ticket.getPassenger();
            if (passenger.getName().equals(name) && passenger.getSurname().equals(surname) && passenger.getDate().equals(birthday)) {
                return false;
            }
        }
        return true;
    }

    static Set<String> getTrainNumberList(EntityManager entityManager) {
        List<Train> trainList = getTrainList(entityManager);
        Set<String> trainNumberList = new HashSet<String>();
        for (Train train : trainList) {
            trainNumberList.add(Integer.toString(train.getNumber()));
        }
        return trainNumberList;
    }

    static void addTrain(int number, int capacity, EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Train newtTrain = new Train();
            newtTrain.setNumber(number);
            newtTrain.setCapacity(capacity);
            entityManager.persist(newtTrain);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }

    static void addStation(String name, EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Station newtStation = new Station();
            newtStation.setName(name);
            entityManager.persist(newtStation);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }

    static void addSchedule(String stationName, int trainNumber, Date time, int offset, EntityManager entityManager) {
        Query stationQuery = entityManager.createQuery("SELECT st FROM ru.sbb.Station st where st.name =:stName");
        stationQuery.setParameter("stName", stationName);
        List<Station> stationList = stationQuery.getResultList();
        if (stationList.isEmpty()) {
            System.out.println("ru.sbb.Station not found!");
            return;
        }

        Query trainQuery = entityManager.createQuery("SELECT tr FROM ru.sbb.Train tr where tr.number =:trNum");
        trainQuery.setParameter("trNum", trainNumber);
        List<Train> trainList = trainQuery.getResultList();
        if (trainList.isEmpty()) {
            System.out.println("ru.sbb.Train not found!");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Schedule newSchedule = new Schedule();
            newSchedule.setTrain(trainList.get(0));
            newSchedule.setStation(stationList.get(0));
            newSchedule.setOffset(offset);
            newSchedule.setTime(time);
            entityManager.persist(newSchedule);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }


//    public void printSchedule() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Query query = entityManager.createQuery("SELECT ts FROM ru.sbb.Schedule ts");
//
//        List<ru.sbb.Schedule> list = query.getResultList();
//        for (ru.sbb.Schedule schedule : list) {
//            System.out.println(schedule);
//        }
//        //return passengerList;
//        entityManager.close();
//        entityManagerFactory.close();
//    }


    static long getCurrentTime() {
        Date date = new Date();
        return date.getTime() / 1000;
    }

    static long getUnixTime(Date date) {
        return date.getTime() / 1000;
    }

    public static Date createDate(int year, int month, int day, int hourofday, int minute, int second) {
        if (day == 0 && month == 0 && year == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hourofday, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date createDate(int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date utilDate = null;
        try {
            utilDate = formatter.parse(year + "/" + month + "/" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utilDate;
    }


}
