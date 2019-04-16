import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User {
    private String username;
    private String password;
    private boolean isLoggedIn;
    private CharAnalizer analizer;

    public User(String username,String password) {
        this.username = username;
        this.password=password;
        this.analizer=new CharAnalizer();
    }

    //BASE PACK
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return username.equals(((User)obj).getUsername());
        else
            return false;
    }

    //PRINCIPAL FUNCTIONS

    public Lot createLot(String description, int basePrice){
        return new Lot(description,basePrice,this.username);
    }
    public Bid makeBid(int amount){
        return new Bid(this.username,amount);
    }
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
    public boolean validatePassword(String password){
       return analizer.validatePassword(password);
    }
    /***
     * @param month  use Calendar.JANUARY,Calendar.FEBRUARY,etc...
     *
     */
    public Date generateDate(int year,int month,int date){
        Calendar calendar=new GregorianCalendar();
        calendar.set(year,month,date);
        return calendar.getTime();
    }
}
