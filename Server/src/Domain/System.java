package Domain;

import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Password;
import Domain.People.Credentials.Username;
import Domain.People.User;

import java.util.ArrayList;
import java.util.Scanner;

public class System {
    private ArrayList<User>  Users_list;
    private ArrayList<Username> Username_list;
    private ArrayList<Lot>   Lots_list;
    private ArrayList<Lot>   Sold_lots_list;

    public System() {
        Users_list = new ArrayList<>();
        Username_list = new ArrayList<>();
    }

}
