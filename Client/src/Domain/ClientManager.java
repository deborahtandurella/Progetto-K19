package Domain;

import Domain.People.Credentials.CharAnalizer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Scanner;

public class ClientManager  {
    private String loggedUser;
    private Proxy ad;

    /**
     * Consente il logout solo se vi e' un utente loggato nel client, in caso di logout setta la stringa utente a null e non permette nessun'azione.
     * PER UNA MAGGIORE SICUREZZA SI POTREBBE INSERIRE UN ID GENERATO CASUALMENTE ED ASSEGNATO AD OGNI UTENTE OLTRE ALL'username, IN QUESTO MODO E' PIU DIFFICILE SPACCIARSI PER QUALCUN ALTRO
     * UN ULTERIORE LIVELLO DI SICUREZZA SI PUO' OTTENERE ASSEGNANDO UN NUOVO ID CASUALE AD OGNI NUOVO LOGIN.
     * @throws RemoteException
     */
    private boolean logout() throws RemoteException {
        if(!(loggedUser == null)) {
            System.out.println("Effettuo la disconnessione per l'account: " + loggedUser );
            if (ad.logoutS(loggedUser)) {
                loggedUser = null;
                System.out.println("Disconnessione avvenuta con successo!");
                return true;
            }
            else {
                System.out.println("Ci sono stati problemi nella disconnessione, contatta l'amministratore");
            }
        }
        else {
            System.out.println("Nessun utente e' connesso!");
            return true;
        }
        return false;
    }

    /**
     * Crea un account solo se la password rispetta le specifiche del Char Analizer e se l'username non e' in uso.
     */
    private void createAccount() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("//-------------------------//");
            System.out.println("Enter your Login ID: ");
            String uid = scan.nextLine();
            System.out.println("Enter your Password:");
            String pw = scan.nextLine();
            if (validatePassword(pw)) {
                if (!ad.alredyTakenUsername(uid)) {
                    ad.createUser(uid,pw);
                    System.out.println("Utente creato con successo: " + uid + "\t" + pw);
                }
                else
                    System.out.println("Username gia' in uso, riprovare");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean login() throws RemoteException {
        boolean retry = true;
        if (loggedUser == null) {
            while (retry) {
                Scanner scan = new Scanner(System.in);
                System.out.println("//-------------------------//");
                System.out.println("Enter your Login ID: ");
                String uid = scan.nextLine();
                System.out.println("Enter your Password:");
                String pw = scan.nextLine();
                if (ad.checkLogin(uid, pw)) {
                   loggedUser = uid;
                    System.out.println("Login successful. Welcome " + uid + " !");
                    retry = false;
                    return true;
                } else {
                    System.out.println("Invalid Login ID or password. Please try again.");
                    return false;
                }
            }
        } else {
            System.out.println("Sei gia' loggato,effettua prima il logout");
            return true;
        }
        return false;
    }

    /**
     * Il metodo effettua il controllo sulla correttezza della password.
     * Viene analizzata attraverso il Char Analizer che restituisce l'esito ed eventualmente il motivo per cui una pass non e' accettata.
     * @param password
     * @return
     */
    private boolean validatePassword(String password){
        CharAnalizer analizer = new CharAnalizer();
        if(!analizer.validatePassword(password)) {
            return false;
        }
        return true;
    }

    private void createAuction() throws RemoteException {
        Scanner scn = new Scanner(System.in);

        System.out.print("Inserisci il titolo dell'inserzione: ");
        String name = scn.nextLine();

        System.out.print("Inserisci il prezzo di partenza: ");
        int price = 0;
        try {
            price = Integer.parseInt(scn.nextLine()); //usato in quanto nel buffer rimane uno \n
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }

        System.out.print("Inserisci la fine dell'asta (formato dd/mm/yy hs:min),Se lasci vuoto termina dopo 1 minuti:");
        String line = scn.nextLine();
        LocalDateTime d = formatDate(line);
        if(!d.isBefore(LocalDateTime.now())) { //Faccio il controllo che la data attuale non sia inferiore a quella attuale
            String vendor = loggedUser;
            ad.addAuction(name, price, vendor, d);
            System.out.println("Aggiunta con successo!");
        }
        else
            System.out.println("La data inserita e' precedente all'orario attuale, non e' stato possibile creare l'asta!");
    }

    /**
     * Permette l'offerta SOLO su aste esistenti
     * Fa si che il creatore non possa offrire sulla sua stessa asta
     * Permette l'offerta solo se l'importo e' superiore all'offerta piu' alta precedente
     * @throws RemoteException
     */
    private void makeOffer() throws RemoteException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Inserisci l'id dell'asta su cui vuoi offrire:");
        int id = Integer.parseInt(scn.nextLine());

        if(ad.checkExistingAuction(id)) {
            if(!(ad.vendorOfAuction(id,loggedUser))) {
                int higherOffer = ad.higherOffer(id);
                System.out.println("Offerta massima attuale:" + higherOffer);
                System.out.println("Inserisci la tua offerta:");
                int amount = Integer.parseInt(scn.nextLine());
                if (amount > higherOffer) {
                    ad.makeBid(loggedUser, amount, id);
                    System.out.println("Offerta accettata! Sei il nuovo offerente migliore");
                } else {
                    System.out.println("Offerta Rifiutata, importo troppo basso");
                }
            }
            else
                System.out.println("Non puoi effettuare offerte su un oggetto da te venduto!");
        }
        else
            System.out.println("L'id inserito non e' abbinato a nessun'asta esistente");
    }

    /**
     * Metodo usato per la formattazione della data inserita.
     * @param line
     * @return
     */
    private LocalDateTime formatDate(String line) {
        LocalDateTime d;
        if(line.equalsIgnoreCase("")) {
            d = LocalDateTime.now().plusMinutes(1); // dura un minuto
        }
        else {
            String[] line1 = line.split(" ");
            String[] date = line1[0].split("/");
            String[] hour = line1[1].split(":");

            d = LocalDateTime.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(hour[0]), Integer.parseInt(hour[1]));

        }
        return d;
    }

    private String activeAuctions() throws RemoteException {
        return ad.showAllActiveAuctions();
    }

    private String closedAuctions() throws RemoteException {
        return ad.showClosedAuctions();
    }

    /**
     * Menu base
     * @throws RemoteException
     */
    private void menu() throws RemoteException {
        int decision = 0;
        while (decision != 99) {
            System.out.println("Benvenuto nel sistema gestione d'aste , scegli cosa fare:   1)CREATE USER   2)LOGIN   100)CLOSE");
            Scanner tastiera = new Scanner(System.in);
            decision = Integer.parseInt(tastiera.nextLine());
            switch (decision) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    if(login()) {
                        loggedMenu();
                    }
                    break;
                case 100:
                    decision = 99;
                    break;
            }
        }
    }

    /**
     * Menu avanzato attivo solo una volta che nel client e' loggato un utente
     * @throws RemoteException
     */
    private void loggedMenu() throws RemoteException {
        int decision = 0;
        while (decision != 99) {
            System.out.println("Sei nella tua area privata " + loggedUser + " scegli cosa fare: 1)CREATE AN AUCTION  2)VIEW ACTIVE AUCTIONS  3)BID FOR AN ITEM 4)VIEW CLOSED AUCTIONS  5 )LOGOUT  ");
            Scanner tastiera = new Scanner(System.in);
            decision = Integer.parseInt(tastiera.nextLine());
            switch (decision) {
                case 1:
                    createAuction();
                    break;
                case 2:
                    System.out.println(activeAuctions());
                    break;
                case 3:
                    makeOffer();
                    break;
                case 4:
                    System.out.println(closedAuctions());
                    break;
                case 5:
                    if(logout()) {
                        decision = 99;
                    }
                    break;
            }
        }
    }

    private void connect() throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost",9999);
            ad = (Proxy) reg.lookup("hii");
            System.out.println("Connesso al server!");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public ClientManager()  { }

    public static void main(String[] args) throws RemoteException {
        ClientManager c = new ClientManager();
        c.connect();
        c.menu();
    }
}
