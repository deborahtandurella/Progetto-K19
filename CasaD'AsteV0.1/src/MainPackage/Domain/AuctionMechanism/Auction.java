package MainPackage.Domain.AuctionMechanism;

import MainPackage.Domain.People.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Auction {
    private ArrayList<User> partecipantsList;
    private Lot lot;
    private ArrayList<Bid> bidsList;
    private GregorianCalendar openingDate;
    private int raise;
}
