package resources;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.Lot;
import Domain.People.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;

public class HibernateTest {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        Session s = factory.openSession();

        User u1 = new User("Fabio","IlFabio96*");
        Lot l1 = new Lot("prova",100,"Fabio");
        Auction a1 = new Auction(1,l1, LocalDateTime.now());
        s.beginTransaction();

        s.save(a1);
        s.getTransaction().commit();
        s.close();

        factory.close();
    }
}
