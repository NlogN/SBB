import javax.persistence.*;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws ParseException {
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

//        addTrain(239,200,entityManager);
        // addStation("Petrozavodsk",entityManager);


//        System.out.println("\n check capacity:\n-------------");
//        Train train = new Train();
//        train.setNumber(239);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        java.util.Date utilDate = formatter.parse("2013/05/03");
//        java.util.Date curDate = new java.util.Date();
//        checkNotFilledState(train, curDate, entityManager);
//        printSchedule(entityManager);

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
        for (Train train : list) {
            System.out.println(train);
        }
        //return passengerList;
    }

    static boolean buyTicket(int trainNum, String stationName, String name, String surname, Date birthday, Date DateOfRace, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tr FROM Train tr where tr.number =:trNum");
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("Train not found!");
        } else {
            Train train = list.get(0);
            if(checkStartTime(train,stationName)&&checkSamePassengerNotReg(train,name,surname,birthday)&&checkNotFilledState(train,DateOfRace,entityManager)){
                //        if(checkReg(trainNum,birthday,entityManager)){
//            EntityTransaction transaction = entityManager.getTransaction();
//            try {
//                transaction.begin();
//                Ticket newtTicket = new Ticket();
//                //
//                entityManager.persist(newtTicket);
//                transaction.commit();
//            } finally {
//                if (transaction.isActive()){
//                    transaction.rollback();
//                }
//            }
//        }else{
//            return false;
//        }
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
        Query query = entityManager.createQuery("SELECT tr FROM Train tr");
        List<Train> trainList = query.getResultList();
        return trainList;
    }


    static boolean checkNotFilledState(Train train, Date dateOfRace, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT tic FROM Ticket tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        for (Ticket t : list) {
            System.out.println(t);
        }
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

    static void printSchedule(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT ts FROM Schedule ts");

        List<Schedule> list = query.getResultList();
        for (Schedule schedule : list) {
            System.out.println(schedule);
        }
        //return passengerList;
    }


    static long getCurrentTime() {
        java.util.Date date = new java.util.Date();
        return date.getTime() / 1000;
    }


}
