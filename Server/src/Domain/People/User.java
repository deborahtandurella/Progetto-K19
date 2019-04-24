package Domain.People;

public class User {
    private String username;
    private String password;
    private boolean isLoggedIn;

    public boolean equals(Object obj) {
        if (obj instanceof User)
            return username.equals(((User)obj).getUsername());
        else
            return false;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

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

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }
}
