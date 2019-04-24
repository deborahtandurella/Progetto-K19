package Domain.AuctionMechanism;


public class Lot {
    private String description;
    private int basePrice;
    private String pathImage;
    private String vendor;
    private String winner;


    public int getBasePrice() {
        return basePrice;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() { return description; }

    public String information() {
        return "Name:" + description + "\t" + "Vendor: " + vendor + "\t" + "Base Price:" + basePrice + "\n";
    }

    public String closedInformation() {
        return "Name:" + description + "\t" + "Vendor: " + vendor + "\t" + "Base Price:" + basePrice + "\t\t" + "Winner:" + winner + "\n";
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) { this.winner = winner; }

    public Lot(String description, int basePrice, String owner) {
        this.description = description;
        this.basePrice = basePrice;
        this.vendor = owner;
    }
}
