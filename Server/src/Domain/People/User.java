package Domain.People;

import Domain.AuctionMechanism.Feedback;
import Domain.People.Credentials.Password;
import Domain.People.Credentials.RecoveryAnswer;
import Domain.People.Credentials.Username;

public class User {
    private Username username;
    private Password password;
    private RecoveryAnswer recAnswer;
    private String address, city;
    private String eMail, phoneNumber;
    private Feedback evaluation;
}
