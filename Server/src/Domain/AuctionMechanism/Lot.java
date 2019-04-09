package Domain.AuctionMechanism;

import Domain.People.User;

public class Lot {
    private String id, description;
    private int basePrice;
    private String pathImage;
    private User owner;
    private CategoryEN category;

    public Lot(String id, String description, int basePrice, String pathImage, User owner) {
        this.id = id;
        this.description = description;
        this.basePrice = basePrice;
        this.pathImage = pathImage;
        this.owner = owner;
    }
}
