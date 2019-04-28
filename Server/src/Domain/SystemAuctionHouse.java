package Domain;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.Bid;
import Domain.AuctionMechanism.LifeCycleAuctionTask;
import Domain.AuctionMechanism.Lot;
import Domain.People.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SystemAuctionHouse extends UnicastRemoteObject implements Proxy {

    private ArrayList<User> Users_list;
    private ConcurrentHashMap<Integer,Auction> auctionList;
    private HashMap<Integer,Auction> closedAuction;
    private HashMap<LifeCycleAuctionTask, Long> timerTasks;
    private int auctionIdCounter = 0; //Fare attenzione, ogni volta che spengo il server il valore non e' salvato
    private transient Timer timer;


    public void createUser(String username, String password){
            User user = new User(username,password);
            addUser(user);
    }

    public boolean logoutS(String username) {
        userListed(username).setLoggedIn(false);
        return true;
    }


    public void addUser(User user){
        Users_list.add(user);
    }


    /**
     * Il metodo effettua il controllo sull'utilizzo dell'username.
     * Se l'username e' gia' utilizzato chiede all'utente di inserirne uno nuovo finche non ne trova uno valido.
     * @param username
     *
     */
    public boolean alredyTakenUsername(String username){
        for(User user : Users_list) {
            if(user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public String showAllActiveAuctions() {
        String toPrint = "";
        for (Map.Entry<Integer,Auction> entry : auctionList.entrySet()) {
            Auction entryValue = entry.getValue();
            toPrint =  toPrint + entryValue.auctionInformation();
        }
        if(auctionList.isEmpty()) {
            toPrint = "Nessun Inserzione Esistente" + "\n";
        }
        return toPrint;
    }

    public String showClosedAuctions() {
        String toPrint = "";
        for (Map.Entry<Integer,Auction> entry : closedAuction.entrySet()) {
            Auction entryValue = entry.getValue();
            toPrint =  toPrint + entryValue.closedAuctionInformation();
        }
        if(closedAuction.isEmpty()) {
            toPrint = "Nessun Inserzione Chiusa" + "\n";
        }
        return toPrint;
    }

    public void addAuction(String title, int price, String vendor, LocalDateTime closingTime) {
        Lot lot = new Lot(title,price,vendor);
        Auction au = new Auction(auctionIdCounter,lot,closingTime);
        auctionList.put(auctionIdCounter,au);
        // Timer for ending the auction
        ZonedDateTime zdt = closingTime.atZone(ZoneId.of("Europe/Rome"));
        long millis = zdt.toInstant().toEpochMilli();
        LifeCycleAuctionTask t = new LifeCycleAuctionTask(auctionIdCounter);
        t.passArgument(auctionList,closedAuction,timerTasks);
        timer.schedule(t, (millis - System.currentTimeMillis()));
        timerTasks.put(t, millis );

        auctionIdCounter++;
    }

    /**
     * Il metodo controlla se e' gia' loggato un utente nel servizio, in tal caso consiglia il logout
     * Il metodo effettua il controllo sulla presenza effettiva nella lista utente, altrimenti non permette il login
     * Se le due condizioni sopra non si avverano allora permette il login
     * @param username
     * @return
     */
    public boolean checkLogin(String username,String pass) {
        User userToCheck = userListed(username);
        if(userToCheck != null) {
            if (userToCheck.checkPassword(pass)) {
                userToCheck.setLoggedIn(true);
                return true;
            }
            return false;
        }
        return false;
    }

    private User userListed(String username) {
        for(User user  : Users_list) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkExistingAuction (int idAuction) {
        if (auctionList.containsKey(idAuction))
            return true;

        else
            return false;
    }

    public int higherOffer(int id) {
        if (auctionList.containsKey(id))
            return auctionList.get(id).getHigherOffer();
        else
            return -1;
    }

    public boolean vendorOfAuction(int idAuction,String logged) {
        if(auctionListed(idAuction).getLot().getVendor().equalsIgnoreCase(logged)) {
            return true;
        }
        return false;
    }


    private Auction auctionListed(int idAuction) {
        if(auctionList.containsKey(idAuction))
            return auctionList.get(idAuction);
        else
            return null;
    }


    public void makeBid(String user, int amount,int id){
        Bid bid = new Bid(user,amount);
        Auction request = auctionListed(id);
        request.addBid(bid);
    }

    public SystemAuctionHouse() throws RemoteException {
        Users_list = new ArrayList<>();
        Users_list.add(new User("alessio","alessio"));
        auctionList = new ConcurrentHashMap<>();
        closedAuction = new HashMap<>();
        timer = new Timer();
        timerTasks = new HashMap<>();
    }

    public void probe() throws RemoteException {}

    public ConcurrentHashMap<Integer, Auction> getAuctionList() { return auctionList; }

    public HashMap<Integer, Auction> getClosedAuction() { return closedAuction; }

    public HashMap<LifeCycleAuctionTask, Long> getTimerTasks() { return timerTasks; }



    public static void main(String[] args) throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(9999);
            SystemAuctionHouse sys = new SystemAuctionHouse();
            reg.rebind("hii", sys);
            System.out.println("Server Ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

