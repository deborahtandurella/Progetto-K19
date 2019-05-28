package Domain.AuctionMechanism;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
Viene usata per passare le info da mostrare al client
 */
public class SimpleAuction implements Serializable {
    private int auctionId;
    private String auctionName;
    private int amount;
    private LocalDateTime closeDate;

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public SimpleAuction(int auctionId, String auctionName, int amount, LocalDateTime closeDate) {
        this.auctionId = auctionId;
        this.auctionName = auctionName;
        this.amount = amount;
        this.closeDate = closeDate;
    }
}
