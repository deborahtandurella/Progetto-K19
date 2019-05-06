package Domain.AuctionMechanism;

import Domain.People.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import resources.HibernateUtil;

import javax.persistence.Query;


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
       int numb;

       try {
           String sql = "SELECT COUNT(*) FROM User where User.username= :userna";
           Query query = s.createQuery(sql);
           query.setParameter("userna",userna);
           numb = (Integer)query.getSingleResult();
           if(numb == 1) {
               s.beginTransaction();
               User user = s.get(User.class, userna);
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
           String sql = "SELECT COUNT(*) FROM User where User.username= :userna AND User.password= :pass AND User.isLoggedIn=false";
           Query query = s.createQuery(sql);
           query.setParameter("userna",userna);
           query.setParameter("pass", pass);
           int numb = (Integer)query.getSingleResult();
           if (numb == 1) {
               User user = s.get(User.class, userna);
               user.setLoggedIn(true);
               s.saveOrUpdate(user);
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
       int numb = 0;
       s = sessionFactory.openSession();
       try {
           String sql = "SELECT COUNT(*) FROM User where User.username= :usern";
           Query query = s.createQuery(sql);
           query.setParameter("usern",userna);
           numb = ((Number)query.getSingleResult()).intValue();
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
