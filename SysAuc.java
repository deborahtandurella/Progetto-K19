import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SysAuc {
    private ArrayList<User> Users_list;
    //private ArrayList<Lot> Lot_list;
    private ArrayList<Auction> Auction_list;
    //private ArrayList<Lot> Sold_lots_list;
    public SysAuc() {
        Users_list = new ArrayList<>();
        Users_list.add(new User("alessio","alessio"));
        Auction_list = new ArrayList<>();
    }


    //PRINCIPAL FUNCTION
    public void createUser(User user){
        while (!(registrationControl(user))){
            //non serve fare niente
        }

    }
    public boolean registrationControl(User user) {
        boolean succ=!(checkListUser(user)) && validatePassword(user);
        if(succ)
            addUser(user);
        return succ;
    }
    public boolean createAuction(User extUser,Lot lot, Date date){
        try{
            User activeUser=getUser(extUser);
            if(activeUser.isLoggedIn()) {
                    addAuction(new Auction(lot, date));
                    return true;
                }
            else
            return false;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean login(User extUser){
        try {
            User activeUser=getUser(extUser);
            if(activeUser.checkPassword(extUser.getPassword())){
                    activeUser.setLoggedIn(true);
                    return true;
            }
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean logout(User extUser) {
        try {
            User activeUser=getUser(extUser);
            activeUser.setLoggedIn(false);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean makeBid(User extUser,int amount,Auction auction){
        User activeUser=getUser(extUser);
        if(activeUser.isLoggedIn()){
                Bid bid=activeUser.makeBid(amount);
                Auction request=getAuction(auction);
                return request.makeBid(bid);
        }
        return false;
    }






    //BASE PACK
    public Auction getAuction(Auction auction){
        return Auction_list.get(Auction_list.indexOf(auction));
    }
    public void addUser(User user){
        Users_list.add(user);
    }
    public boolean checkListUser(User user){
        boolean succ=Users_list.contains(user);
        if (!succ){
            System.out.println("Nuovo username: ");
            String username=askNewInput();
            user.setUsername(username);
        }
        return succ;
    }
    public User getUser(User user){
        return  Users_list.get(Users_list.indexOf(user));
    }
    public void addAuction(Auction auction){
        Auction_list.add(auction);
    }
    public void removeAllClosedAuction(){
        for (Auction auction: Auction_list){
            if (auction.isClosed()){
                Auction_list.remove(auction);
            }
        }
    }
    public String askNewInput(){
        Scanner scanner=new Scanner(System.in);
        return scanner.nextLine();
    }
    public boolean validatePassword(User user){
        boolean suc=user.validatePassword(user.getPassword());
        if(!suc){
            System.out.println("Nuova password: ");
            String password=askNewInput();
            user.setPassword(password);
        }
        return suc;
    }

}
