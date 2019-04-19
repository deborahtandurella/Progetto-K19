package Domain.AuctionMechanism;

import Domain.People.User;
import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private static int count = 0;
    private int id, total;
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private Date openingDate;
    private boolean isClosed;
    private TimerAuction timerAuction;
    //BASE PACK
    public int getId() {
        return id;
    }
    public Auction(int id) {
        this.id = id;
    }
    public Auction(Lot lot, Date openingDate) {
        id=count;
        count++;
        this.openingDate=openingDate;
        this.partecipantsList=new ArrayList<>();
        this.timerAuction=new TimerAuction();
        this.lot=lot;
        this.total=lot.getBasePrice();
        this.bidsList= new ArrayList<>();
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
        boolean suc=checkBid(bid) && checkIfIsOpened(new Date());
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
    public  boolean checkIfIsOpened(Date date){
        return openingDate.before(date);
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
    private void closeAuction(){
        isClosed=true;
    }
}

