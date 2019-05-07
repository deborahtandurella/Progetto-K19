package Domain.AuctionMechanism;

import Domain.People.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import resources.HibernateUtil;

import javax.persistence.Query;
import java.util.List;


public class DBManager {
    private SystemManager sys;
    private SessionFactory sessionFactory;
    private Session s;

   public void addUser(String username, String pass) {
       s = sessionFactory.openSession();
       try {
           User u = new User(username, pass);
           s.beginTransaction();
           s.save(u);
           s.getTransaction().commit();
           s.close();
       } catch (HibernateException e) {
           e.printStackTrace();
       } finally {
           s.close();
       }
   }

   public boolean logout(String userna) {
       s = sessionFactory.openSession();
       int numb = 0;

       try {
           String sql = "SELECT COUNT(*) FROM User where User.username= :userna";
           NativeQuery sqlQuery = s.createSQLQuery(sql);
           sqlQuery.setParameter("userna",userna);
           numb = ((Number)sqlQuery.getSingleResult()).intValue();
           if(numb == 1) {
               s.getTransaction().begin();
               User user = (User)s.get(User.class, userna);
               user.setLoggedIn(false);
               s.saveOrUpdate(user);
               s.getTransaction().commit();
               return true;
           }
       }catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return false;
   }

   public boolean login(String userna, String pass) {
       s = sessionFactory.openSession();
       try {
           String sql = "SELECT COUNT(*) FROM User where username= :userna AND pass= :pass AND loggedstatus=false";
           NativeQuery sqlQuery = s.createSQLQuery(sql);
           sqlQuery.setParameter("userna",userna);
           sqlQuery.setParameter("pass", pass);
           int numb = ((Number)sqlQuery.getSingleResult()).intValue();
           if (numb == 1) {
               s.getTransaction().begin();
               User u = (User)s.get(User.class,userna);
               u.setLoggedIn(true);
               s.saveOrUpdate(u);
               s.getTransaction().commit();
               return true;
           }
       }catch (Exception e){
           e.printStackTrace();
       }   finally {
           s.close();
       }
       return false;
   }

   //TO FIX RITORNA 0 ANCHE IN PRESENZA DI COPIA, IL PROBLEMA SECONDO ME RISIEDE NEL SINGLERESULT CHE NON E' IL MODO GIUSTO DI TRATTARLO
   public  boolean alredyTakenUsername(String userna) {

       s = sessionFactory.openSession();
       try {
           String sql = "SELECT COUNT(*) FROM User where User.username= :usern";
           NativeQuery sqlQuery = s.createSQLQuery(sql);
           sqlQuery.setParameter("usern",userna);
           int numb = ((Number)sqlQuery.getSingleResult()).intValue();
           if (numb == 1)
               return true;
           else
               return false;
       }catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return false;
   }


    public DBManager(SystemManager sys) {
        this.sys = sys;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

}
