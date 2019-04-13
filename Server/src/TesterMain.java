import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Username;
import Domain.People.User;
import Domain.SystemAuctionHouse;

import java.util.GregorianCalendar;


public class TesterMain {
    public static void main(String[] args) {
        SystemAuctionHouse s = new SystemAuctionHouse();
        Lot lot1 = new Lot("gatto di giordano", 20, s.getUsers_list().get(1));
        GregorianCalendar gregorianCalendar00 = new GregorianCalendar(2019, 3,13,17, 00);
        s.createAuction(lot1, gregorianCalendar00);
        User user=s.getUsers_list().get(0);
        s.getAuction_list().get(0).makeBid(user);
        s.getAuction_list().get(0).makeBid(user);

    }
}
