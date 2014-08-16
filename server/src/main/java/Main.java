import javax.persistence.*;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        System.out.println("\n get passenger by train number:\n-------------");
//        List<Passenger> passengerList = getPassengerByTrain(123, entityManager);
//        for (Passenger passenger : passengerList) {
//            System.out.println(passenger);
//        }

//        System.out.println("\n print station shedule:\n-------------");
//        printStationSchedule("Moskow", entityManager);

//        System.out.println("\n train number list:\n-------------");
//        for (String s : getTrainNumberList(entityManager)) {
//            System.out.println(s);
//        }

//        addTrain(243,50,entityManager);
//        addStation("Petrozavodsk",entityManager);
        checkTrainCapacity(239, new Date(93, 2, 20), entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    static List<Passenger> getPassengerByTrain(int trainNum, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT ts.passenger FROM Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }

    static void printStationSchedule(String stationName, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT ts.train FROM Station st join st.scheduleList ts where st.name =:stName");
        query.setParameter("stName", stationName);
        List<Train> list = query.getResultList();
        for (Train train:list){
            System.out.println(train);
        }
        //return passengerList;
    }

    static List<Train> getTrainList(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM Train tr");
        List<Train> trainList = query.getResultList();
        return trainList;
    }

    static boolean checkTrainCapacity(int trainNum, Date date, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM Train tr where tr.number =:trNum" );
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        for (Train t:list){
            return checkCapacity(t,date,entityManager);
        }
        return true;

    }

    static boolean checkCapacity(Train train, Date date, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tic FROM Ticket tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", date);
        List<Ticket> list = query.getResultList();
        for (Ticket t:list){
            System.out.println(t);
        }
        return train.getCapacity()>list.size();

    }

    static Set<String> getTrainNumberList(EntityManager entityManager) {
        List<Train> trainList = getTrainList(entityManager);
        Set<String> trainNumberList = new HashSet<String>();
        for (Train train:trainList){
                  trainNumberList.add(Integer.toString(train.getNumber()));
        }
        return trainNumberList;
    }

    static void addTrain(int number, int capacity, EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Train newtTrain = new Train();
            newtTrain.setId(3);
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
            newtStation.setId(4);
            newtStation.setName(name);
            entityManager.persist(newtStation);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }


//    static void printStationSchedule1(String stationName, EntityManager entityManager) {
//        Query query = entityManager.createQuery("SELECT st FROM Station st WHERE st.name=:stName");
//        query.setParameter("stName", stationName);
//        List<Station> list = query.getResultList();
//        if (list.isEmpty()) {
//            log.warning("station not found");
//        } else {
//            Station station = list.get(0);
//            station.printSchedule();
//        }
//    }

//    static List<Passenger> getPassengerByTrain(int trainNum, EntityManager entityManager) {
//        Query query = entityManager.createQuery("SELECT tr FROM Train tr WHERE tr.number=:numb");
//        query.setParameter("numb", trainNum);
//        List<Train> list = query.getResultList();
//        List<Passenger> passengerList = new ArrayList<Passenger>();
//        if (list.isEmpty()) {
//            log.warning("train not found");
//            return passengerList;
//        } else {
//            List<Ticket> ticketList = list.get(0).getTicketList();
//            for (Ticket ticket : ticketList) {
//                passengerList.add(ticket.getPassenger());
//            }
//            return passengerList;
//        }
//
//    }
//
//    static List<Passenger> getPassengerByTrain(int trainNum, EntityManager entityManager) {
//        //
//        Query query = entityManager.createQuery("SELECT ts.passenger FROM Train tr join tr.ticketList ts where tr.number =:numb");
//       // Query query = entityManager.createQuery("SELECT trn.ticketList FROM (SELECT Train tr FROM where tr.number =:numb) trn  ");
//        query.setParameter("numb", trainNum);
//        //Query query = entityManager.createQuery("SELECT tr.ticketList FROM Train tr");
//       // List<Train> list = query.getResultList();
////        List<Ticket> list = query.getResultList();
////        for (Ticket t :list){
////            System.out.println(t);
////        }
//        List<Passenger> passengerList = query.getResultList();
////        if (list.isEmpty()) {
////            log.warning("train not found");
////            return passengerList;
////        } else {
////            List<Ticket> ticketList = list.get(0).getTicketList();
////            for (Ticket ticket : ticketList) {
////                passengerList.add(ticket.getPassenger());
////            }
////            return passengerList;
////        }
//        return passengerList;
//
//    }



}
