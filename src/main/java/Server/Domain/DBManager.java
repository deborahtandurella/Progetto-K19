package Server.Domain;

import Server.People.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import Server.services.DBConnection.HibernateUtil;


import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DBManager {
    private SystemManager sys;
    private SessionFactory sessionFactory;
    private Session s;


    public void addUser(String username, String pass) {
        s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            User u = new User(username, pass);
            s.save(u);
            s.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

   public boolean logout(String userna) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where User.username= :userna";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("userna",userna);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if(numb == 1) {
                User user = s.get(User.class, userna);
                user.setLoggedIn(false);
                s.saveOrUpdate(user);
                s.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    public boolean login(String userna, String pass) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where username= :userna AND pass= :pass AND loggedstatus=false";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("userna",userna);
            sqlQuery.setParameter("pass", pass);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1) {
                User u = s.get(User.class,userna);
                u.setLoggedIn(true);
                s.saveOrUpdate(u);
                s.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    public void addAuction(String title, int price, String vendor, LocalDateTime closingTime) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            Lot l = new Lot(title,price);
            User u = s.get(User.class,vendor);
            l.setVendorDB(u);
            Auction au = new Auction(l,closingTime);
            au.setHigherOffer(price);
            s.save(au); //Faccio cosi per fare generare al database la chiave
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public boolean isClosed(int id) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            s.getTransaction().commit();
            return au.isClosed();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
           s.close();
        }
        return false;
    }

    public void closeAuction(int id) {
        s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            au.setClosed(true);
            s.saveOrUpdate(au);
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void winner(int id) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            List<Bid> list = au.getBidsList();
            if (list.size() != 0) {
                list.sort(Comparator.comparing(Bid::getAmount)); //ordino in base all'amount
                int lastOne = list.size()-1;
                String winner = list.get(lastOne).getActorDB().getUsername();
                User u = s.get(User.class,winner);
                au.getLot().setWinnerDB(u);
            }
            else {
                au.getLot().setWinner("No Winner!");
            }
            s.saveOrUpdate(au);
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public synchronized int idOfAuction() {
        s = sessionFactory.openSession();

        String sql = "SELECT max(id) FROM auction";
        try {
            s.beginTransaction();
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            int id = ((Number)sqlQuery.getSingleResult()).intValue();
            s.getTransaction().commit();
            return id;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return  -1;
    }

    public boolean makeBid(String user, int amount,int id) {
        s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            User u = s.get(User.class,user);
            Bid b = new Bid();
            b.setActorDB(u);
            b.setAmount(amount);
            Auction a = s.get(Auction.class,id);
            if(amount> a.getHigherOffer()) {
                a.addBidDB(b);
                a.setHigherOffer(amount);
                s.saveOrUpdate(a);
                s.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    public boolean vendorOfAuction(int idAuction,String logged) {
       s = sessionFactory.openSession();

       try {
           s.beginTransaction();
           String sql = "FROM Auction where id='" + idAuction + "'";
           Query query = s.createQuery(sql);
           Auction au = (Auction)query.getSingleResult();
           s.getTransaction().commit();
           return (au.getLot().getVendorDB().getUsername().equalsIgnoreCase(logged));
       } catch (Exception e){
           e.printStackTrace();
       } finally {
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
       } catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return false;
    }

    public int higherOffer(int id) {
        s = sessionFactory.openSession();

        try {
            Auction au = s.get(Auction.class,id);
            return au.getHigherOffer();
        } catch (Exception e){
           e.printStackTrace();
        } finally {
           s.close();
        }
        return -1;
    }

    public boolean checkExistingAuction(int id) {
        s = sessionFactory.openSession();

        try {
            String sql = "SELECT COUNT(*) FROM auction where auction.id= :idA AND closed=false";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("idA",id);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1) {
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
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
                toPrint = toPrint + a.openAuctionInfoDB();
            }
            if(list.isEmpty()) {
                toPrint = "Nessun Inserzione Esistente" + "\n";
            }
            return toPrint;
        } catch (Exception e){
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
                toPrint = toPrint + a.closedAuctionInfoDB();
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

    public ArrayList<SimpleAuction> AuctionList() {
        s = sessionFactory.openSession();
        ArrayList<SimpleAuction> Alist = new ArrayList<>();

        String sql = " FROM  Auction where closed= false";
        try {
            Query query = s.createQuery(sql);
            List<Auction> list = (List<Auction>)query.list();

            for (int i = 0; i < list.size(); i++) {
                Auction a = list.get(i);
                int auctionId = a.getId();
                String name = a.getLot().getDescription();
                int highOffer = a.getHigherOffer();
                LocalDateTime close = a.getClosingDate();
                File image = new File("src\\main\\java\\Server\\services\\AuctionImages\\" + a.getId() + ".png");

                SimpleAuction s = new SimpleAuction(auctionId, name, highOffer, close,image);
                Alist.add(s);
            }

            return Alist;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }


    public DBManager(SystemManager sys) {
        this.sys = sys;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
}
