package Domain.AuctionMechanism;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "higheroffer")
    private int higherOffer;

    @Transient
    private List<String> partecipantsList;

    @OneToOne(mappedBy = "auL",cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
    private Lot lot;

    @OneToMany(mappedBy = "au",cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
    private List<Bid> bidsList = new ArrayList<>();

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

    public void valuateHigher() {
        int higher = lot.getBasePrice();
        for(Bid b : bidsList) {
            if( b.getAmount() > higher) {
                higher = b.getAmount();
            }
        }
        higherOffer = higher;
    }


    public int getId() { return id; }

    public int getHigherOffer() { return higherOffer; }



    public Lot getLot() { return lot; }



    public LocalDateTime getClosingDate() { return closingDate; }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHigherOffer(int higherOffer) {
        this.higherOffer = higherOffer;
    }

    public void setPartecipantsList(ArrayList<String> partecipantsList) {
        this.partecipantsList = partecipantsList;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public void setBidsList(ArrayList<Bid> bidsList) {
        this.bidsList = bidsList;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }


    public List<Bid> getBidsList() {
        return bidsList;
    }

    public void setBidsList(List<Bid> bidsList) {
        this.bidsList = bidsList;
    }

    public Auction() {}

    public Auction(Lot lot, LocalDateTime closingDate) {
        this.lot = lot;
        this.closingDate = closingDate;
        this.higherOffer=lot.getBasePrice();
    }

    public List<String> getPartecipantsList() {
        return partecipantsList;
    }

    public void setPartecipantsList(List<String> partecipantsList) {
        this.partecipantsList = partecipantsList;
    }

    public Auction(int id, Lot lot, LocalDateTime closingDate) {
        this.id = id;
        this.partecipantsList=new ArrayList<>();
        this.closingDate = closingDate;
        this.lot=lot;
        this.higherOffer=lot.getBasePrice();
    }
}