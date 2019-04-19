package Domain;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.Bid;
import Domain.AuctionMechanism.Lot;
import Domain.People.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

public class SystemAuctionHouse extends UnicastRemoteObject implements Proxy {
    private ArrayList<User> Users_list;
    //private ArrayList<Lot> Lot_list;
    private ArrayList<Auction> Auction_list;
    //private ArrayList<Lot> Sold_lots_list;


    public void createUser(String username, String password){
            User user = new User(username,password);
            addUser(user);
    }

    public boolean createAuction(User extUser, Lot lot, Date date){
        try{
            User activeUser=getUser(extUser);
            if(activeUser.isLoggedIn()) {
                    addAuction(new Auction(lot, date));
                    return true;
            }
            else
            return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean logoutS(String username) {
        userListed(username).setLoggedIn(false);
        return true;
    }

    public boolean makeBid(User extUser,int amount,Auction auction){
        User activeUser=getUser(extUser);
        if(activeUser.isLoggedIn()){
                Bid bid=activeUser.makeBid(amount);
                Auction request=getAuction(auction);
                return request.makeBid(bid);
        }
        return false;
    }

    public Auction getAuction(Auction auction){
        return Auction_list.get(Auction_list.indexOf(auction));
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

    public User getUser(User user){
        return  Users_list.get(Users_list.indexOf(user));
    }

    public void addAuction(Auction auction){
        Auction_list.add(auction);
    }

    public void removeAllClosedAuction(){
        for (Auction auction: Auction_list){
            if (auction.isClosed()){
                Auction_list.remove(auction);
            }
        }
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
        if(userToCheck.checkPassword(pass)) {
            userToCheck.setLoggedIn(true);
            return true;
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

    public SystemAuctionHouse() throws RemoteException {
        Users_list = new ArrayList<>();
        Users_list.add(new User("alessio","alessio"));
        Auction_list = new ArrayList<>();

    }

    public static void main(String[] args) throws RemoteException {
        try{
            Registry reg = LocateRegistry.createRegistry(9999);
            reg.rebind("hii",new SystemAuctionHouse());
            System.out.println("Server Ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

