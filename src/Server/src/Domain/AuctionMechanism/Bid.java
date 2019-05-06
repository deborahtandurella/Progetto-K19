package Domain.AuctionMechanism;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BID")
public class Bid implements Serializable {
    @Id
    @Column(name = "auctionid", updatable = false)
    private int idAu;

    @Id
    @Column(name = "offerer", updatable = false)
    private String actor;

    @Id
    @Column(name = "amount", updatable = false)
    private int amount;

    public String getActor() {
        return actor;
    }

    public int getAmount() {
        return amount;
    }

    public Bid() {}

    public Bid(int idAu,String actor, int amount) {
        this.idAu = idAu;
        this.actor = actor;
        this.amount = amount;
    }

}
