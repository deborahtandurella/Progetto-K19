package Domain.AuctionMechanism;



import Domain.People.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Auction {
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private GregorianCalendar openingDate;
    private int raise;

    public Auction(Lot lot, GregorianCalendar openingDate) {
        this.lot = lot;
        this.openingDate = openingDate;
    }
    //Fai una offerta e aggiungi partecipante se non è in lista
    public void makeBid(User user, int amount) {
        Bid bid = new Bid(user, amount);
        bidsList.add(bid);
        addPartecipant(user);
    }
    //Aggiungi partecipante all'asta contorllando che non ci siano doppioni
    private boolean addPartecipant(User user) {
        if(partecipantsList.contains(user))
            return false;
        else {
            partecipantsList.add(user);
            return true;
        }
    }
}
