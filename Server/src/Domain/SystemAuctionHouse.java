package Domain;

import Domain.AuctionMechanism.Lot;
import Domain.People.Credentials.Password;
import Domain.People.Credentials.Username;
import Domain.People.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemAuctionHouse {
    private ArrayList<User> Users_list;
    private ArrayList<Username> Username_list;
    private ArrayList<Lot> Lots_list;
    private ArrayList<Lot> Sold_lots_list;

    public SystemAuctionHouse() {
        Users_list = new ArrayList<>();
        Username_list = new ArrayList<>();
        loadUsers();
    }

    public void createUser() {
        Scanner scanner = new Scanner(java.lang.System.in);
        java.lang.System.out.println("CREATE USERNAME: ");
        Username username = new Username(scanner.nextLine());
        if(!(username.getUsername() == null)) {
            scanner = new Scanner(java.lang.System.in);
            java.lang.System.out.println("CREATE PASSWORD: ");
            Password password = new Password(scanner.nextLine());
            if(!(password.getPassword() == null)) {
                if(uniqueness(username)) {
                    Username_list.add(username);
                    Users_list.add(new User(username, password));
                    java.lang.System.out.println("USER CREATED ");
                } else  {
                    java.lang.System.out.println("USERNAME ALREADY TAKEN");
                }
            } else {
                createUser();
            }
        }
        else {
            createUser();
        }
    }

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
                Users_list.add(new User(username, password)); }
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException ex1) {
            java.lang.System.out.println(ex1);
        }
        catch (IOException ex2) {
            java.lang.System.out.println(ex2);
        }
    }

    public boolean uniqueness(Username username) {
        for(Username u: Username_list) {
            if(username.getUsername() == u.getUsername()) {
                return false;
            }
        }
        return true;
    }

    public void uniquenessprint(Username username) {
        for(Username u: Username_list) {
            System.out.println(u.getUsername());
            System.out.println(username.getUsername());
        }
    }

    public void printUsers() {
        for(User user: Users_list) {
            java.lang.System.out.println(user.toString());
        }
    }
}
