import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();



        List<Train> studentList = entityManager.createQuery ("SELECT u FROM Train u").getResultList();

        for (Iterator<Train> iterator = studentList.iterator(); iterator.hasNext(); ) {
            Train next = iterator.next();
            System.out.println(next);
        }
        entityManager.close();
        entityManagerFactory.close();
    }
}
