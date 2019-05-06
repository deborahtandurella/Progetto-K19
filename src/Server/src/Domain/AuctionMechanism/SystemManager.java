package Domain.AuctionMechanism;

import Domain.People.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SystemManager extends UnicastRemoteObject implements Proxy {

    private ConcurrentHashMap<String,User> usersList;
    private ConcurrentHashMap<Integer,Auction> auctionList;
    private HashMap<Integer,Auction> closedAuction;
    private HashMap<LifeCycleAuctionTask, Long> timerTasks;
    private int auctionIdCounter = 0; //Fare attenzione, ogni volta che spengo il server il valore non e' salvato
    private transient Timer timer;
    private FileManager files;
    private DBManager db;


    public void createUser(String username, String password){
            User user = new User(username,password);
            addUser(user);
    }

    public void createUserDB (String username, String password){
        db.addUser(username,password);
    }

    public boolean logoutS(String username) {
        userListed(username).setLoggedIn(false);
        return true;
    }


    private void addUser(User user){
        usersList.put(user.getUsername(),user);
    }


    /**
     * Il metodo effettua il controllo sull'utilizzo dell'username.
     * Se l'username e' gia' utilizzato rifiuta la creazione dell'utente.
     * @param username
     *
     */
    public boolean alredyTakenUsername(String username){
        if(usersList.containsKey(username))
            return true;
        else
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

    synchronized public void addAuction(String title, int price, String vendor, LocalDateTime closingTime) {
        Lot lot = new Lot(title,price,vendor);
        Auction au = new Auction(auctionIdCounter,lot,closingTime);
        auctionList.put(auctionIdCounter,au);
        // Timer for ending the auction
        ZonedDateTime zdt = closingTime.atZone(ZoneId.of("Europe/Rome"));
        long millis = zdt.toInstant().toEpochMilli();
        LifeCycleAuctionTask t = new LifeCycleAuctionTask(auctionIdCounter,millis);
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
            if(userToCheck.isLoggedIn())
                return false;
            if (userToCheck.checkPassword(pass)) {
                userToCheck.setLoggedIn(true);
                return true;
            }
            return false;
        }

        return false;
    }

    private User userListed(String username) {
        if(usersList.containsKey(username))
            return usersList.get(username);
        else
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

    synchronized public void makeBid(String user, int amount,int id){
        Bid bid = new Bid(id,user,amount);
        Auction request = auctionListed(id);
        request.addBid(bid);
    }

    public void probe()  {}

    public void saveState() {
        System.out.println(files.saveState());
    }

    public void loadState() {
        System.out.println(files.loadState());
    }

    public ConcurrentHashMap<Integer, Auction> getAuctionList() { return auctionList; }

    public HashMap<Integer, Auction> getClosedAuction() { return closedAuction; }

    public HashMap<LifeCycleAuctionTask, Long> getTimerTasks() { return timerTasks; }

    public void setUsersList(ConcurrentHashMap<String, User> usersList) { this.usersList = usersList; }

    public void setAuctionList(ConcurrentHashMap<Integer, Auction> auctionList) { this.auctionList = auctionList; }

    public void setClosedAuction(HashMap<Integer, Auction> closedAuction) { this.closedAuction = closedAuction; }

    public void setTimerTasks(HashMap<LifeCycleAuctionTask, Long> timerTasks) { this.timerTasks = timerTasks; }

    public ConcurrentHashMap<String, User> getUsersList() {
        return usersList;
    }

    public SystemManager() throws RemoteException {
        usersList = new ConcurrentHashMap<String, User>();
        usersList.put("alessio",new User("alessio","alessio"));
        auctionList = new ConcurrentHashMap<Integer, Auction>();
        closedAuction = new HashMap<Integer, Auction>();
        timer = new Timer();
        timerTasks = new HashMap<LifeCycleAuctionTask, Long>();
        files = new FileManager(this);
        db = new DBManager (this);
    }
}

