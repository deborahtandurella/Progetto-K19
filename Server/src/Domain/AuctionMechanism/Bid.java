package Domain.AuctionMechanism;

import Domain.People.User;

public class Bid {
    private User actor;
    private int amount;

    public Bid(User actor, int amount) {
        this.actor = actor;
        this.amount = amount;
    }

    public User getActor() {
        return actor;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "actor=" + actor +
                ", amount=" + amount +
                '}';
    }
}
