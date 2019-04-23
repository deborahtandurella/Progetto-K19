package Domain;

import Domain.People.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Date;

public interface Proxy extends Remote {
    public boolean alredyTakenUsername(String username) throws RemoteException;
    public void createUser(String username, String password) throws RemoteException;
    public boolean checkLogin(String username,String pass) throws RemoteException;
    public boolean logoutS(String username) throws RemoteException;
    public void addAuction(String title, int price, String vendor, LocalDateTime d) throws RemoteException;
    public String showAllActiveAuctions() throws RemoteException;
    public boolean checkExistingAuction(int id) throws RemoteException;
    public int higherOffer(int id) throws RemoteException;
    public void makeBid(String user, int amount,int id) throws RemoteException;
    public boolean vendorOfAuction(int idAuction,String logged) throws RemoteException;
}
