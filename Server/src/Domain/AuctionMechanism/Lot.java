package Domain.AuctionMechanism;

import Domain.People.User;

// classe lotto
public class Lot {
    private String id, description;
    private int basePrice;
    private String pathImage;
    private String owner;
    private CategoryEN category;

    public Lot(String id, String description, int basePrice, String owner) {
        this.id = id;
        this.description = description;
        this.basePrice = basePrice;
        this.owner = owner;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
