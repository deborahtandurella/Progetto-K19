package Domain.AuctionMechanism;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Entity
@Table(name = "auction")
public class Auction implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Transient
    private int higherOffer;

    @Transient
    private ArrayList<String> partecipantsList;

    @Transient
    private Lot lot;

    @Transient
    private ArrayList<Bid> bidsList;

    @Column(name = "closingdate")
    private LocalDateTime closingDate;

    @Column(name = "closed")
    private boolean closed;


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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Auction() {}
    public Auction(int id, Lot lot, LocalDateTime closingDate) {
        this.id = id;
        this.partecipantsList=new ArrayList<>();
        this.closingDate = closingDate;
        this.lot=lot;
        this.higherOffer=lot.getBasePrice();
        this.bidsList= new ArrayList<>();
    }
}