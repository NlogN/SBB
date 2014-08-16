import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
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

        System.out.println("\n print station shedule:\n-------------");
        printStationSchedule("Moskow", entityManager);

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
