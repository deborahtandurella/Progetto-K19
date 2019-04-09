package Domain.AuctionMechanism;



import Domain.People.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Auction {
    private static int count=0;
    private int id;
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private GregorianCalendar openingDate;
    private int raise;

    public Auction(Lot lot, GregorianCalendar openingDate) {
        id=count;
        count++;
        this.lot = lot;
        this.openingDate = openingDate;
        partecipantsList=new ArrayList<>();
        bidsList=new ArrayList<>();
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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Auction){
            if(((Auction) o).getId()==this.getId())
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public ArrayList<Bid> getBidsList() {
        return bidsList;
    }

    @Override
    public String toString() {
        return "Auction{" + this.id+
                '}';
    }
}
