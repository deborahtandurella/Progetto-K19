package Domain.AuctionMechanism;

import Domain.People.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import resources.HibernateUtil;


public class DBManager {
    private SystemManager sys;
    private SessionFactory sessionFactory;
    private Session s;

   public void addUser(String username, String pass) {
       s = sessionFactory.openSession();
       User u = new User(username,pass);
       s.save(u);
       s.getTransaction().commit();
       s.close();
   }


    public DBManager(SystemManager sys) {
        this.sys = sys;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

}
