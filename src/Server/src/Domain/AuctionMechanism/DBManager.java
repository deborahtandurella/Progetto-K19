package Domain.AuctionMechanism;

import Domain.People.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import resources.HibernateUtil;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

   public void addAuction(String title, int price, String vendor, LocalDateTime closingTime) {
        s = sessionFactory.openSession();
        try {
            s.getTransaction().begin();
            Lot l = new Lot(title,price,vendor);
            Auction au = new Auction(l,closingTime);
            s.save(au); //Faccio cosi per fare generare al database la chiave
            s.save(l);
            s.getTransaction().commit();
        } catch (Exception e){
        e.printStackTrace();
    }   finally {
        s.close();
    }
   }

    public void makeBid(String user, int amount,int id) {
        s = sessionFactory.openSession();
        try {
            s.getTransaction().begin();
            Bid b = new Bid(id,user,amount);
            Auction au = (Auction)s.get(Auction.class,id);
            au.getBidsList().add(b);
            au.setHigherOffer(amount);
            s.saveOrUpdate(au);
            s.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }   finally {
            s.close();
        }
    }


    public boolean vendorOfAuction(int idAuction,String logged) {
       s = sessionFactory.openSession();
       try {
           String sql = "FROM Auction where id='" + idAuction + "'";
           Query query = s.createQuery(sql);
           Auction au = (Auction)query.getSingleResult();
           if(au.getLot().getVendor().equalsIgnoreCase(logged))
               return true;
           else
               return false;

       }catch (Exception e){
           e.printStackTrace();
       }   finally {
           s.close();
       }
       return false;
    }



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

   public int higherOffer(int id) {
       s = sessionFactory.openSession();
       try {
            s.getTransaction().begin();
            Auction au = (Auction)s.get(Auction.class,id);
            au.valuateHigher();
            s.getTransaction().commit();
            return au.getHigherOffer();

       }catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return -1;
   }

    public boolean checkExistingAuction(int id) {
        s = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM auction where id= :idA AND closed=false";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("idA",id);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }   finally {
            s.close();
        }
        return false;
    }


   public String showAllActive() {
       s = sessionFactory.openSession();
       String toPrint = "";
       String sql = " FROM  Auction where closed= false";
       try {
           Query query = s.createQuery(sql);
           List<Auction> list = (List<Auction>)query.list();

           for(Auction a : list) {
               toPrint = toPrint + a.auctionInformation();
           }
           if(list.isEmpty()) {
               toPrint = "Nessun Inserzione Esistente" + "\n";
           }
           return toPrint;
       }catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return "NAN";
   }

    public String showAllClosed() {
        s = sessionFactory.openSession();
        String toPrint = "";
        String sql = " FROM  Auction where closed= true ";
        try {
            Query query = s.createQuery(sql);
            List<Auction> list = (List<Auction>)query.list();

            for(Auction a : list) {
                toPrint = toPrint + a.closedAuctionInformation();
            }
            if(list.isEmpty()) {
                toPrint = "Nessun Inserzione Chiusa" + "\n";
            }
            return toPrint;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return "NAN";

    }


    public DBManager(SystemManager sys) {
        this.sys = sys;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

}
