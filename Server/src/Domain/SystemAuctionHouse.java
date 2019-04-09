package Domain;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.Bid;
import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Password;
import Domain.People.Credentials.Username;
import Domain.People.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

// SystemAH ha i metodi per creare user da tastiera, da file(in automatico) che utilizzano un metodo per il controllo dell'unicità
// dell'username, qui incluso, un metodo per il login ed i metodi di stampa

public class SystemAuctionHouse {
    private ArrayList<User> Users_list;
    private ArrayList<Username> Username_list;
    private ArrayList<Lot> Lot_list;
    private ArrayList<Auction> Auction_list;
    private ArrayList<Lot> Sold_lots_list;

    public SystemAuctionHouse() {
        Users_list = new ArrayList<>();
        Username_list = new ArrayList<>();
        loadUsers();
        this.Auction_list = new ArrayList<>();
    }

    // crea user assicurandosi unicita username, validita password. Aggiunge user in array user_list
    // e username in username_list(questo array è poi utile per il controllo unicità degli username)
    public void createUser() {
        Scanner scanner = new Scanner(java.lang.System.in);
        System.out.println("CREATE USERNAME: ");
        Username username = new Username(scanner.nextLine());
        if(!(username.getUsername() == null) && (uniqueness(username))) {
            scanner = new Scanner(java.lang.System.in);
            System.out.println("CREATE PASSWORD: ");
            Password password = new Password(scanner.nextLine());
            if(!(password.getPassword() == null)) {
                    Username_list.add(username);
                    Users_list.add(new User(username, password));
                    System.out.println("USER CREATED ");

            } else {
                createUser();
            }
        }
        else {
            System.out.println("USERNAME ALREADY TAKEN");
            createUser();
        }
    }

    // loader di utenti da file txt
    private void loadUsers() {
        try{
            FileReader fr = new FileReader("utenti.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while((line = br.readLine()) != null) {
                String[] word = line.split("\t");
                Username username = new Username(word[0]);
                if(uniqueness(username)) {
                    Password password = new Password(word[1]);
                    Users_list.add(new User(username, password));
                    Username_list.add(username);
                }
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException ex1) {
            System.out.println(ex1);
        }
        catch (IOException ex2) {
            System.out.println(ex2);
        }
    }

    // uniqueness assicura che gli username creati in createUser siano univoci
    private boolean uniqueness(Username username) {
        for (Username u : Username_list) {
            if (username.getUsername().equals(u.getUsername())) {
                return false;
            }
        }
        return true;
    }

    // questo credo si spieghi da se. in ogni caso si appoggia alla classe user
    public void logIn() {
        User user = null;
        System.out.println("USERNAME:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("PASSWORD:");
        scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        for (User u : Users_list) {
            if (username.equals(u.getUsername())) {
                user = u;
                System.out.println(user.getUsername());
                if(u.getPassword().equals(password)) {
                    user.logInOut(true);
                }
            }
        }
        user.logInOut(false);
    }

    public void printUsers() {
        for(User user: Users_list) {
            java.lang.System.out.println(user.toString());
        }
    }

    public void printLogged() {
        for (User u : Users_list) {
            if (u.isLogged()) {
                System.out.println(u.getUsername());
            }
        }
    }

    public ArrayList<User> getUsers_list() {
        return Users_list;
    }

    public void createAuction(Lot lot, GregorianCalendar date){
        Auction_list.add(new Auction(lot, date));
    }

    public ArrayList<Auction> getAuction_list() {
        return Auction_list;
    }
    public boolean makeAnOffer(User user,int amount,int id){
        Auction auction=Auction_list.get(Auction_list.indexOf(id));
        auction.makeBid(user,amount);
        return true;
    }
}
