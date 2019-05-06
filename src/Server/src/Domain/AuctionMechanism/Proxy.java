package Domain.AuctionMechanism;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface Proxy extends Remote {
    boolean alredyTakenUsername(String username) throws RemoteException;
    boolean alredyTakenUsernameDB(String username) throws RemoteException;
    void createUser(String username, String password) throws RemoteException;
    void createUserDB (String username, String password) throws RemoteException;
    boolean checkLogin(String username,String pass) throws RemoteException;
    boolean checkLoginDB(String username,String pass) throws RemoteException;
    boolean logoutS(String username) throws RemoteException;
    boolean logoutSDB(String username) throws RemoteException;
    void addAuction(String title, int price, String vendor, LocalDateTime d) throws RemoteException;
    String showAllActiveAuctions() throws RemoteException;
    boolean checkExistingAuction(int id) throws RemoteException;
    int higherOffer(int id) throws RemoteException;
    void makeBid(String user, int amount,int id) throws RemoteException;
    boolean vendorOfAuction(int idAuction,String logged) throws RemoteException;
    String showClosedAuctions() throws RemoteException;
    void probe() throws RemoteException;

}
