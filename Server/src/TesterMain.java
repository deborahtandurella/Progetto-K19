import Domain.People.Credentials.Username;
import Domain.SystemAuctionHouse;


public class TesterMain {
    public static void main(String[] args) {
        SystemAuctionHouse s = new SystemAuctionHouse();
        s.printUsers();
        s.logIn();
        s.printLogged();
    }
}
