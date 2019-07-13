package Server.Domain;

import Server.People.User;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import Server.Services.HibernateUtil;

import java.io.File;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;


class InterpreterRDB {
    private SessionFactory sessionFactory;
    private Session s;

    /**
     * Create the user and store it in the DB
     */
    void addUser(String username, String pass,String email) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            User u = new User(username, pass,email);
            s.save(u);
            s.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    boolean changeEmail(String email,String username){ s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where User.username= :username";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("username",username);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if(numb == 1) {
                User user = s.get(User.class, username);
                user.setEmail(email);
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
    boolean changePassword(String password,String username){
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where User.username= :username";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("username",username);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if(numb == 1) {
                User user = s.get(User.class, username);
                user.setPassword(password);
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

    /**
     * Check if the user is registered and logout
     */
    boolean logout(String username) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where User.username= :username";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("username",username);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if(numb == 1) {
                User user = s.get(User.class, username);
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

    void logoutAll(){
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "update User set loggedstatus=0";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }

    }

    /**
     * Check if the user is registered and login
     */
    boolean login(String username, String pass) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql = "SELECT COUNT(*) FROM User where username= :userna AND pass= :pass AND loggedstatus=false";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("userna",username);
            sqlQuery.setParameter("pass", pass);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1) {
                User u = s.get(User.class,username);
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

    /**
     * Create a new auction
     */
    void addAuction(String title, int price, String vendor, LocalDateTime closingTime) {
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


    /**
     * Modify an existing (and not closed) auction
     */
    void modifyAuction(String title, int price,int id) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            if(title!= null) {
                au.setDescriptionLot(title);
            }
            if(price!= -1) {
                au.setBasePriceLot(price);
                au.setHigherOffer(price);
            }
            s.saveOrUpdate(au);
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * Check if the auction is closed
     */
    boolean isClosed(int id) {
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

    /**
     * Close the auction
     */
    void closeAuction(int id) {
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

    /**
     * Set the winner of the auction (the biggest offer), in addition the method order the bids
     */
    void winner(int id) {
        s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            List<Bid> list = au.getBidsList();
            if (list.size() != 0) {
                list.sort(Comparator.comparing(Bid::getAmount)); //ordino in base all'amount
                int lastOne = list.size()-1;
                String winner = list.get(lastOne).getActorDBUsername();//questo si può cambiare c'è già il metodo
                User u = s.get(User.class,winner);
                au.setWinnerDB(u);
            }
            else {
                au.setWinner("No Winner!");
            }
            s.saveOrUpdate(au);
            s.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    String getActualWinner(int id) {
        s = sessionFactory.openSession();
        try {
            s.beginTransaction();
            Auction au = s.get(Auction.class,id);
            List<Bid> list = au.getBidsList();
            if (list.size() != 0) {
                list.sort(Comparator.comparing(Bid::getAmount)); //ordino in base all'amount
                int lastOne = list.size()-1;
               return list.get(lastOne).getActorDBUsername();//questo si può cambiare c'è già il metodo
            }
            else {
                return "No Winner!";
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Used to get the latest used id for the auction
     */
    synchronized int latestId() {
        s = sessionFactory.openSession();

        String sql = "SELECT max(id) FROM auction";
        try {
            s.beginTransaction();
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            if(sqlQuery.getSingleResult() != null) {
                int id = ((Number) sqlQuery.getSingleResult()).intValue();
                s.getTransaction().commit();
                return id;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return  1;
    }

    /**
     * Add a bid to the auction's bids list
     */
    synchronized boolean makeBid(String user, int amount,int id) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            User u = s.get(User.class,user);
            Bid b = new Bid();
            b.setActorDB(u);
            b.setAmount(amount);
            Auction a = s.get(Auction.class,id);
            Hibernate.initialize(u.getPartecipantAuction());
            if(amount> a.getHigherOffer()) {
                a.addBidDB(b);
                a.setHigherOffer(amount);
                u.getPartecipantAuction().add(a);
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

    /**
     * Used to check if the user that is trying to do something is the vendor
     */
    boolean vendorOfAuction(int idAuction,String logged) {
       s = sessionFactory.openSession();

       try {
           s.beginTransaction();
           String sql = "FROM Auction where id='" + idAuction + "'";
           Query query = s.createQuery(sql);
           Auction au = (Auction)query.getSingleResult();
           s.getTransaction().commit();
           return (au.getUsernameVendorDB().equalsIgnoreCase(logged));//anche questo si può modificare getusernamevendor
       } catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return false;
    }

    /**
     * Check if the username is alredy registered
     */
    boolean alredyTakenUsername(String userna) {
       s = sessionFactory.openSession();

       try {
           String sql = "SELECT COUNT(*) FROM User where User.username= :usern";
           NativeQuery sqlQuery = s.createSQLQuery(sql);
           sqlQuery.setParameter("usern",userna);
           int numb = ((Number)sqlQuery.getSingleResult()).intValue();
           if (numb == 1)
               return true;
       } catch (Exception e){
           e.printStackTrace();
       } finally {
           s.close();
       }
       return false;
    }

    /**
     * Check if the email is alredy registered
     */
    boolean alredyTakenEmail(String email) {
        s = sessionFactory.openSession();

        try {
            String sql = "SELECT COUNT(*) FROM User where User.email= :email";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("email",email);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1)
                return true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    /**
     * Return the highest for a specific auction
     */
    int highestOffer(int id) {
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

    /**
     * Check if an auction exists
     */
    boolean checkExistingAuction(int id) {
        s = sessionFactory.openSession();

        try {
            String sql = "SELECT COUNT(*) FROM auction where auction.id= :idA AND closed=false";
            NativeQuery sqlQuery = s.createSQLQuery(sql);
            sqlQuery.setParameter("idA",id);
            int numb = ((Number)sqlQuery.getSingleResult()).intValue();
            if (numb == 1)
                return true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    boolean checkActor(String username,int id){
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            Bid bid= s.get(Bid.class,id);
            System.out.println(bid.getActorDBUsername());
            if (bid.getActorDBUsername().equals(username))
                return true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
    return false;
    }

    /**
     * Return all the open auctions
     */
    String showAllActive() {
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

    /**
     * Return all the closed auctions
     */
    String showAllClosed() {
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

    /**
     * Return a list of open auction (used by the GUI)
     */
    ArrayList<Auction> AuctionList() {
        s = sessionFactory.openSession();
        ArrayList<Auction> Alist = new ArrayList<>();

        String sql = "FROM  Auction where closed= false";

        try {
            Query query = s.createQuery(sql);
            List<Auction> list = (List<Auction>)query.list();

            for (int i = 0; i < list.size() && i <= 9; i++) {
                Auction a = list.get(i);
                File image = new File("src\\main\\resources\\Images\\" + a.getId() + ".png");
                a.setImage(image);

                Alist.add(a);
            }
            return Alist;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Return a list of auction that have a specific text in the title
     */
    ArrayList<Auction> searchAuctionList(String textToSearch) {
        s = sessionFactory.openSession();
        ArrayList<Auction> Alist = new ArrayList<>();

        String sql = "FROM  Auction where closed= false";
        try {
            Query query = s.createQuery(sql);
            List<Auction> list = (List<Auction>)query.list();

            for (int i = 0; i < list.size(); i++) {
                Auction a = list.get(i);
                File image = new File("src\\main\\resources\\Images\\" + a.getId() + ".png");
                a.setImage(image);
                String title = a.getLot().getDescription().toLowerCase();

                textToSearch = ".*" + textToSearch.toLowerCase() + ".*";
                Pattern PATTERN = Pattern.compile(textToSearch); //Uso la regex     ".*STRINGA.*"      per matchare ogni corrispondenza

                if(PATTERN.matcher(title).matches()) {
                    Alist.add(a);
                }
            }

            return Alist;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Return a list of favorite auctions for a specific user
     */
    ArrayList<Auction> favoriteAuction(String user) {
        s = sessionFactory.openSession();
        ArrayList<Auction> Alist = new ArrayList<>();
        User u = s.get(User.class,user);
        String sql = " SELECT a FROM Auction a INNER JOIN Favorites f on a=f.auctionLiked where :u=f.liker ";
        try {
            Hibernate.initialize(u.getFavoriteList()); //Essenziale nel caso di relazione Many to Many

            Query query = s.createQuery(sql);
            query.setParameter("u", u);
            List<Auction> list = (List<Auction>)query.list();


            for (int i = 0; i < list.size(); i++) {
                Auction a = list.get(i);
                File image = new File("src\\main\\resources\\Images\\" + a.getId() + ".png");
                a.setImage(image);
                Alist.add(a);
            }

            return Alist;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Return a list of auction where the user has participated or has sold
     */
    ArrayList<Auction> myAuctionList(String username) {
        s = sessionFactory.openSession();
        ArrayList<Auction> Alist = new ArrayList<>();

        String sql = "FROM  Auction";
        try {
            Query query = s.createQuery(sql);
            List<Auction> list = (List<Auction>)query.list();
            User user = s.get(User.class, username);
            Hibernate.initialize(user.getPartecipantAuction());
            for (int i = 0; i < list.size() && i <= 9; i++) {
                Auction a = list.get(i);
                if(a.getLot().getVendorDB().equals(user) || user.isAPartecipant(a)) {// si può cambiare anche questo getVendorDB in au
                    File image = new File("src\\main\\resources\\Images\\" + a.getId() + ".png");
                    a.setImage(image);

                    Alist.add(a);
                }
            }

            return Alist;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Return an Auction given the id
     */
    Auction getAuction(int id) {
        s = sessionFactory.openSession();
        String sql = "FROM Auction where id=:id";

        try {
            Query query = s.createQuery(sql);
            query.setParameter("id",id);
            Auction a = (Auction)query.getSingleResult();
            File image = new File("src\\main\\resources\\Images\\" + a.getId() + ".png");
            if(image.exists())
                a.setImage(image);
            return a;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Return an User given the username
     */
    User getUser(String username) {
        s = sessionFactory.openSession();
        String sql = "FROM User where username=:user";

        try {
            Query query = s.createQuery(sql);
            query.setParameter("user",username);

            return (User)query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
    String getVendorEmail(String username) {
        s = sessionFactory.openSession();
        String sql = "FROM User where username=:user";

        try {
            Query query = s.createQuery(sql);
            query.setParameter("user",username);
            return ((User)query.getSingleResult()).getEmail();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }


    /**
     * Update the User's stats
     */
    synchronized void saveUserStateFavorites(User user,Auction au,int choose) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            User u = s.get(User.class,user.getUsername());
            Auction a = s.get(Auction.class,au.getId());
            Hibernate.initialize(u.getFavoriteList());
            if(choose == 1)
                u.getFavoriteList().add(a);
            else
                u.getFavoriteList().remove(a);
            s.saveOrUpdate(u);

            s.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * Update Auction's state
     */
    synchronized void saveAuctionState(Auction auction) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            s.saveOrUpdate(auction);
            s.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * Return if the User likes a specific Auction
     */
    synchronized boolean userLikeAuction(String username, int id) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            User u = s.get(User.class,username);
            Auction a = s.get(Auction.class,id);
            if(u.isFavourite(a))
                return true;
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.getTransaction().commit();
            s.close();
        }
        return false;
    }

    /**
     * Save the Timers
     */
    void saveTimer( ArrayList<AuctionDBTimerStrategy> timerTasksDB) {
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
                for(AuctionDBTimerStrategy timer : timerTasksDB) {
                s.saveOrUpdate(timer);
            }

        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.getTransaction().commit();
            s.close();
        }
    }

    /**
     * Reload the Timers
     */
    HashMap<Integer, BigInteger> reloadTimer() {
        s = sessionFactory.openSession();

        String sql = "SELECT auction FROM timer ";
        String sql2 = "SELECT millis FROM timer ";
        try {
            s.beginTransaction();
            NativeQuery query = s.createSQLQuery(sql);
            List<Integer> a = query.getResultList();
            NativeQuery query2 = s.createSQLQuery(sql2);
            List<BigInteger> b = query2.getResultList();
            HashMap<Integer,BigInteger> timer = new HashMap<>();

            for(int i =0; i < a.size(); i++) {
                timer.put(a.get(i),b.get(i));
            }

            s.getTransaction().commit();
            return timer;
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    /**
     * Delete all the Timers
      */
    void deleteTimer() {
        s = sessionFactory.openSession();
        String sql = "DELETE FROM AuctionDBTimerStrategy";

        try {
            s.beginTransaction();
            Query query = s.createQuery(sql);
            query.executeUpdate();
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.getTransaction().commit();
            s.close();
        }
    }

    /**
     * Close the Hibernate's session factory
     */
    void closeSession(){
        this.sessionFactory.close();
    }


    void deleteAuctions(){
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql ="select auction.id from auction left join lot on " +
                    "auction.id = lot.auctionid where closingdate<now() and winner is null;";
            NativeQuery query = s.createSQLQuery(sql);
            ArrayList<Integer> numbers = (ArrayList<Integer>) query.getResultList();
            sql="delete from favorites where id in :numbers";
            query = s.createSQLQuery(sql);
            query.setParameterList("numbers",numbers);
            query.executeUpdate();
            sql="delete from partecipants where id in :numbers";
            query = s.createSQLQuery(sql);
            query.setParameterList("numbers",numbers);
            query.executeUpdate();
            sql="delete from bid where auctionid in :numbers";
            query = s.createSQLQuery(sql);
            query.setParameterList("numbers",numbers);
            query.executeUpdate();
            sql="delete from lot where auctionid in :numbers";
            query = s.createSQLQuery(sql);
            query.setParameterList("numbers",numbers);
            query.executeUpdate();
            sql="delete from auction where id in :numbers";
            query = s.createSQLQuery(sql);
            query.setParameterList("numbers",numbers);
            query.executeUpdate();
            s.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }



    public InterpreterRDB() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static void main(String[] args) {
        InterpreterRDB db = new InterpreterRDB();
        db.logoutAll();
        db.deleteAuctions();
    }

}
