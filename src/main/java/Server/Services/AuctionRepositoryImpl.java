package Server.Services;

import Server.Domain.Auction;
import Server.Domain.Lot;
import Server.People.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class AuctionRepositoryImpl {
    private SessionFactory sessionFactory;
    private Session s;

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
                String title = a.getDescriptionLot().toLowerCase();
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
                if(a.getVendorDB().equals(user) || user.isAPartecipant(a)) {
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
    void deleteAuctions(){
        s = sessionFactory.openSession();

        try {
            s.beginTransaction();
            String sql ="select auction.id from auction left join lot on " +
                    "auction.id = lot.auctionid where closingdate<now() and winner is null;";
            NativeQuery query = s.createSQLQuery(sql);
            ArrayList<Integer> numbers = (ArrayList<Integer>) query.getResultList();
            if (!numbers.isEmpty()) {
                sql = "delete from favorites where id in :numbers";
                executeUpdate(sql,numbers);
                sql = "delete from partecipants where id in :numbers";
                executeUpdate(sql,numbers);
                sql = "delete from bid where auctionid in :numbers";
                executeUpdate(sql,numbers);
                sql = "delete from lot where auctionid in :numbers";
                executeUpdate(sql,numbers);
                sql = "delete from timer where id in :numbers";
                executeUpdate(sql,numbers);
                sql = "delete from auction where id in :numbers";
                executeUpdate(sql,numbers);
                s.getTransaction().commit();
                deleteImages(numbers);
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    private void executeUpdate(String sql,ArrayList<Integer> numbers ){
        NativeQuery query = s.createSQLQuery(sql);
        query.setParameterList("numbers", numbers);
        query.executeUpdate();

    }

    private void deleteImages(ArrayList<Integer> numbers){
        for(Integer id:numbers){
            File image = new File("src\\main\\resources\\Images\\" + id + ".png");
            image.delete();
        }
    }

    AuctionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

