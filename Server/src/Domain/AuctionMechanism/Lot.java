package Domain.AuctionMechanism;

import Domain.People.User;

// classe lotto
public class Lot {
    private String description;
    private int basePrice;
    private String pathImage;
    private User owner;
    private CategoryEN category;

    public Lot(String description, int basePrice, User owner) {
        this.description = description;
        this.basePrice = basePrice;
        this.owner = owner;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
