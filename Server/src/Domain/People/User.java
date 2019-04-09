package Domain.People;

import Domain.AuctionMechanism.Feedback;
import Domain.People.Credentials.Password;
import Domain.People.Credentials.RecoveryAnswer;
import Domain.People.Credentials.Username;


// User è il nostro utente. Qui ci sono i suoi attributi, il metodo per il login e le getter
public class User {
    private Username username;
    private Password password;
    private RecoveryAnswer recAnswer;
    private String address, city;
    private String eMail, phoneNumber;
    private Feedback evaluation;
    private boolean logged;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
        this.logged = false;
    }

    public boolean logInOut(boolean b) {
        return this.logged = b;
    }
    @Override
    public String toString() {
        return "User{" +
                "username=" + username.getUsername()+
                ", password=" + password.getPassword()+
                '}';
    }

    public String getUsername() {
        return username.getUsername();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public boolean isLogged() {
        return logged;
    }

 // User sono confrontabili mediante username
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User other=(User) obj;
            if(other.getUsername().equals(this.getUsername()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
