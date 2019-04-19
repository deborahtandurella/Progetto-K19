package Domain;

import Domain.People.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote {
    public boolean alredyTakenUsername(String username) throws RemoteException;
    public void createUser(String username, String password) throws RemoteException;
    public boolean checkLogin(String username,String pass) throws RemoteException;
    public boolean logoutS(String username) throws RemoteException;
}
