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
    private LocalDateTime openingDate;
    private boolean isOpen;
    private boolean openBid;
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
        String openDate = openingDate.format(formatter);
        String closeDate = closingDate.format(formatter);
        return "Id:"+ id + "\t" + "Current value:" + higherOffer + "\t" + lot.information() + "\t" +  "Data Inizio:" + openDate +"\t" + "Data Fine:" + closeDate + "\n";
    }


    public int getId() { return id; }

    public int getHigherOffer() { return higherOffer; }

    public ArrayList<String> getPartecipantsList() { return partecipantsList; }

    public Lot getLot() { return lot; }

    public ArrayList<Bid> getBidsList() { return bidsList; }

    public LocalDateTime getOpeningDate() { return openingDate; }

    public boolean isOpen() { return isOpen; }

    public boolean isOpenBid() { return openBid; }

    public LocalDateTime getClosingDate() { return closingDate; }

    public Auction(int id, Lot lot, LocalDateTime openingDate) {
        this.id = id;
        this.openingDate=openingDate;
        this.partecipantsList=new ArrayList<>();
        closingDate = openingDate.plusHours(48); //OGNI ASTA DURA MASSIMO 48 ORE, QUESTO PUO ESSERE VARIATO
        this.lot=lot;
        this.higherOffer=lot.getBasePrice();
        this.bidsList= new ArrayList<>();
    }

}