package Domain.AuctionMechanism;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Auction implements Serializable {
    private int id, higherOffer;
    private ArrayList<String> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private LocalDateTime closingDate;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auction)
            return ((Auction)obj).getId()==this.id;
        else
            return false;
    }

    /**
     * Aggiunge offerta all'asta
     * @param bid
     */
    public void addBid(Bid bid) {
        bidsList.add(bid);
        higherOffer = bid.getAmount();
    }

    /**
     * Stampa informazioni su asta aperta
     * @return
     */
    public String auctionInformation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String closeDate = closingDate.format(formatter);
        return "Id:"+ id + "\t" + "Current value:" + higherOffer + "\t" + lot.information() + "\t"  + "Data Fine:" + closeDate + "\n";
    }

    /**
     * Stampa informazioni su asta chiusa
     * @return
     */
    public String closedAuctionInformation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        closingDate.format(formatter);
        return "Id:"+ id + "\t" + "Current value:" + higherOffer + "\t" + lot.closedInformation() + "\n";
    }

    /**
     * Ottiene l'attuale Offerta piu' alta
     * @return
     */
    public Bid getLastBid() {
        if(bidsList.size() != 0) {
            return bidsList.get((bidsList.size()-1));
        }
        else
            return null;
    }


    public int getId() { return id; }

    public int getHigherOffer() { return higherOffer; }

    public ArrayList<String> getPartecipantsList() { return partecipantsList; }

    public Lot getLot() { return lot; }

    public ArrayList<Bid> getBidsList() { return bidsList; }

    public LocalDateTime getClosingDate() { return closingDate; }

    public Auction(int id, Lot lot, LocalDateTime closingDate) {
        this.id = id;
        this.partecipantsList=new ArrayList<>();
        this.closingDate = closingDate;
        this.lot=lot;
        this.higherOffer=lot.getBasePrice();
        this.bidsList= new ArrayList<>();
    }
}