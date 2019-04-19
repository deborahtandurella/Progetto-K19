package Domain.AuctionMechanism;

public class Bid {

        private String actor;
        private int amount;

        //BASE PACK
        public Bid(String actor, int amount) {
            this.actor = actor;
            this.amount = amount;
        }
        public String getActor() {
            return actor;
        }
        public int getAmount() {
            return amount;
        }

        //PRINCIPAL FUNCTIONS

}
