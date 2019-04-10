package Domain.AuctionMechanism;



import Domain.People.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


// Auction è la nostra classe asta. Questa è caratterizzata da un ID univoco assegnatogli dal sistema, un vettore per i partecipanti, un lotto,
// un vettore per le offerte, una data di apertura ed un timer per la durata.
public class Auction {
    private static int count=0;
    private int id, total;
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private GregorianCalendar openingDate;
    private TimerAuction timerAuction;

    public Auction(Lot lot, GregorianCalendar openingDate) {
        id=count;
        count++;
        this.lot = lot;
        this.openingDate = openingDate;
        partecipantsList=new ArrayList<>();
        bidsList=new ArrayList<>();
        this.timerAuction = new TimerAuction();
        this.total = lot.getBasePrice();
    }
    //Fai una offerta, aggiungi partecipante se non è in lista, se la sua offerta è maggiore del piatto ed eventualmente aggiorna il timer se si è sotto il tempo limite
    public void makeBid(User user, int amount) {
        if(checkDate()) {
            if (AuctionIsOpen()) {
                if(amount > total) {
                    Bid bid = new Bid(user, amount);
                    bidsList.add(bid);
                    addPartecipant(user);
                    updateTimer();
                    total = bid.getAmount();
                }
            }
        }
        else {
            System.out.println("AUCTION SCHEDULED FOR " + openingDate);
        }
    }

    // dovrebbe chiudere l'asta MA NON FUNZIONA
    private void closeAuction() {
        if(timerAuction.getInterval() == 0) {
            System.out.println("LOT WIN FROM USER " + partecipantsList.get(partecipantsList.size()-1));
        }
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
            return ((Auction) o).getId()==this.getId();
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
    // metodo che aggiorna il timer se sotto il tempo limite
    private void updateTimer() {
        if(timerAuction.getInterval() <= 60) {
            timerAuction.setInterval(20);
        }
    }
    // verifica che il timer dell'asta sia ancora maggiore di zero
    private boolean AuctionIsOpen() {
        if(timerAuction.getInterval() > 0) {
            return true;
        }
        else
            return false;
    }

    private boolean checkDate() {
        Calendar g = GregorianCalendar.getInstance();
        if((g.get(Calendar.YEAR))==(openingDate.get(GregorianCalendar.YEAR))) {
            if ((g.get(Calendar.MONTH) + 1) == (openingDate.get(GregorianCalendar.MONTH))) {
                if ((g.get(Calendar.DAY_OF_MONTH)) == (openingDate.get(GregorianCalendar.DAY_OF_MONTH))) {
                    if ((g.get(Calendar.HOUR_OF_DAY)) == (openingDate.get(GregorianCalendar.HOUR_OF_DAY))) {
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
