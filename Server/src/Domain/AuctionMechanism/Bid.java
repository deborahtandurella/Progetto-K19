package Domain.AuctionMechanism;

import Domain.People.User;

// classe offerta per ora si spiega da sola
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

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "actor=" + actor +
                ", amount=" + amount +
                '}';
    }
}
