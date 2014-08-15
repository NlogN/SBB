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


//        List<Station> studentList = entityManager.createQuery ("SELECT u FROM Station u").getResultList();
//        for (Station station:studentList) {
//            System.out.println(station);
//        }
//        List<Schedule> studentList = entityManager.createQuery ("SELECT u FROM Schedule u").getResultList();
//        for (Schedule schedule:studentList) {
//            System.out.println(schedule);
//        }
//
//        List<Train> studentList = entityManager.createQuery ("SELECT u FROM Train u").getResultList();
//        for (Train train:studentList) {
//            System.out.println(train);
//        }

//        System.out.println("\n get passenger by train number:\n-------------");
//        List<Passenger> passengerList = getPassengerByTrain(123, entityManager);
//        for (Passenger passenger : passengerList) {
//            System.out.println(passenger);
//        }
        getScheduleByStation("Moskow",entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    static void getScheduleByStation(String stationName, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT st FROM Station st WHERE st.name=:stName");
        query.setParameter("stName", stationName);
        List<Station> list = query.getResultList();
        if (list.isEmpty()) {
            log.warning("station not found");

        } else {
            Station station = list.get(0);
            station.printSchedule();
        }
    }

    static List<Passenger> getPassengerByTrain(int trainNum, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM Train tr " + "WHERE tr.number=:numb");
        query.setParameter("numb", trainNum);
        List<Train> list = query.getResultList();
        List<Passenger> passengerList = new ArrayList<Passenger>();
        if (list.isEmpty()) {
            log.warning("train not found");
            return passengerList;
        } else {
            List<Ticket> ticketList = list.get(0).getTicketList();
            for (Ticket ticket : ticketList) {
                passengerList.add(ticket.getPassenger());
            }
            return passengerList;
        }

    }


}
