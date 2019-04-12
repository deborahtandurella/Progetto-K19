import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Username;
import Domain.People.User;
import Domain.SystemAuctionHouse;

import java.util.GregorianCalendar;


public class TesterMain {
    public static void main(String[] args) {
        SystemAuctionHouse s = new SystemAuctionHouse();
        s.printUsers();
        Lot lot1 = new Lot("gatto di giordano", 200, s.getUsers_list().get(1));
        GregorianCalendar gregorianCalendar00 = new GregorianCalendar(2019, 4,10,17,30);
        s.createAuction(lot1, gregorianCalendar00);
        /*User user=s.getUsers_list().get(0);
        s.getAuction_list().get(0).makeBid(user,200);
        s.getAuction_list().get(0).makeBid(user,100);*/
        s.printAuctions();


    }
}
