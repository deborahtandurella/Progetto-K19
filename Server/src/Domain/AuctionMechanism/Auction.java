package Domain.AuctionMechanism;

import Domain.People.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Auction {
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

    public void addBid(Bid bid) {
        bidsList.add(bid);
        higherOffer = bid.getAmount();
    }

    public String auctionInformation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String closeDate = closingDate.format(formatter);
        return "Id:"+ id + "\t" + "Current value:" + higherOffer + "\t" + lot.information() + "\t"  + "Data Fine:" + closeDate + "\n";
    }

    public String closedAuctionInformation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String closeDate = closingDate.format(formatter);
        return "Id:"+ id + "\t" + "Current value:" + higherOffer + "\t" + lot.closedInformation() + "\n";
    }

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