package MainPackage.Domain.People;

import MainPackage.Domain.AuctionMechanism.Feedback;
import MainPackage.Domain.AuctionMechanism.StarsEN;
import MainPackage.Domain.People.Credentials.Password;
import MainPackage.Domain.People.Credentials.RecoveryAnswer;
import MainPackage.Domain.People.Credentials.Username;

public class User {
    private Username username;
    private Password password;
    private RecoveryAnswer recAnswer;
    private String address, city;
    private String eMail, phoneNumber;
    private Feedback evaluation;
}
