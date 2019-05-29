package Server.Domain;

import java.io.File;
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
    private File image;

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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public SimpleAuction(int auctionId, String auctionName, int amount, LocalDateTime closeDate, File image) {
        this.auctionId = auctionId;
        this.auctionName = auctionName;
        this.amount = amount;
        this.closeDate = closeDate;
        this.image = image;
    }
}
