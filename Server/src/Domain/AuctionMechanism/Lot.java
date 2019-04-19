package Domain.AuctionMechanism;


public class Lot {
        private String description;
        private int basePrice;
        private String pathImage;
        private String vendor;
        private String winner;

        public Lot(String description, int basePrice, String owner) {
            this.description = description;
            this.basePrice = basePrice;
            this.vendor = owner;
        }

        public int getBasePrice() {
            return basePrice;
        }

    public String getVendor() {
        return vendor;
    }

    public String getWinner() {
        return winner;
    }
}
