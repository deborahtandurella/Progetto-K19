package Domain.AuctionMechanism;

import Domain.People.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private int id, total;
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private LocalDateTime openingDate;
    private boolean isClosed;
    private TimerAuction timerAuction;
    //BASE PACK
    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auction)
            return ((Auction)obj).getId()==this.id;
        else
            return false;
    }
    public ArrayList<User> getPartecipantsList() {
        return partecipantsList;
    }
    public ArrayList<Bid> getBidsList() {
        return bidsList;
    }
    public int getTotal() {
        return total;
    }
    public boolean isClosed() {
        return isClosed;
    }

    //PRINCIPAL FUNCTIONS

    public boolean makeBid(Bid bid){
        boolean suc=checkBid(bid) && checkIfIsOpened(LocalDateTime.now());
        if(isFirstBid()){
            startingBid();
        }
        if(suc){
            suc=isTimeNotOut();
            if (suc) {
                addBid(bid);
                total = bid.getAmount();
                updateTimer();
            }
            else
                {
                    closeAuction();
                }

        }
        return suc;
    }
    public boolean checkBid(Bid bid){
        return bid.getAmount()>total;
    }
    public void addBid(Bid bid){
        bidsList.add(bid);
    }
    public  boolean checkIfIsOpened(LocalDateTime date){
        return openingDate.isBefore(date);
    }
    public boolean isTimeNotOut(){
        return false;
    }
    public boolean isFirstBid(){
        return bidsList.size()==0;
    }
    private void startingBid() {
        timerAuction.setTimer();
    }
    private void updateTimer(){
        if(timerAuction.getInterval() <= 60) {
            timerAuction.setInterval(10);
        }
    }

    public String auctionInformation() {
        return "Id:"+ getId() + "\t" + "Current value:" + getTotal() + "\t" + lot.Information() + "\t" +  "Data Inizio:" + openingDate.toString();
    }
    private void closeAuction(){
        isClosed=true;
    }

    public Auction(int id,Lot lot, LocalDateTime openingDate) {
        this.id = id;
        this.openingDate=openingDate;
        this.partecipantsList=new ArrayList<>();
        this.timerAuction=new TimerAuction();
        this.lot=lot;
        this.total=lot.getBasePrice();
        this.bidsList= new ArrayList<>();
    }

}

