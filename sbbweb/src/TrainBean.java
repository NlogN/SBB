

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by Ilya Makeev on 04.09.2014.
 */

@ManagedBean
@SessionScoped
public class TrainBean implements Serializable {

    private String trains = "train nums";

    public void setTrains(String trains) {
       // ManagerService managerService = new Server().getManagerService();
       // this.trains = managerService.getTrainNumbers();
    }

    public String getTrains() {
       return trains;
    }

}
