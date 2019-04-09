import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Username;
import Domain.People.User;
import Domain.SystemAuctionHouse;

import java.util.GregorianCalendar;


public class TesterMain {
    public static void main(String[] args) {
        SystemAuctionHouse s = new SystemAuctionHouse();
        Lot lot1 = new Lot("00", "gatto di giordano", 100, "utente08");
        GregorianCalendar gregorianCalendar00 = new GregorianCalendar(2020, 01,01);
        s.createAuction(lot1, gregorianCalendar00);
        s.createAuction(lot1, gregorianCalendar00);
        s.createAuction(lot1, gregorianCalendar00);
        User user=s.getUsers_list().get(0);
        s.getAuction_list().get(0).makeBid(user,200);
        s.getAuction_list().get(0).makeBid(user,100);
        System.out.println(s.getAuction_list().get(0).getBidsList());



    }
}
