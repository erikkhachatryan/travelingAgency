package service.model;

import service.dao.DataSource;
import service.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Erik on 10/22/2017.
 */
public class GeneralClassifierCache {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private HashMap<String, Classifier> locations;

    public Map<String, Classifier> loadLocations() {
        if (locations == null) {
            locations = new HashMap<>();
            Classifier location1 = new ClassifierImpl(1);
            location1.setName("Yerevan");
            locations.put("Yerevan", location1);
            location1 = new ClassifierImpl(2);
            location1.setName("Spitak");
            locations.put("Spitak", location1);
        }
        return locations;
    }

    private List<Classifier> allUsers;

    public List<Classifier> loadUsers() {
        if (allUsers == null) {
            allUsers = UserDao.loadUsers();
        }
        return allUsers;
    }

    public void saveUser(Classifier user) {
        if (user.getId() == -1) {
            UserDao.insertUser(user);
        } else {
            UserDao.updateUser(user);
        }
        allUsers = null;
    }

}
