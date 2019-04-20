package Domain;

import Domain.People.Credentials.CharAnalizer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class ClientManager  {
    private String loggedUser;
    private Proxy ad;

    /**
     * Consente il logout solo se vi e' un utente loggato, in caso di logout setta la stringa utente a null e non permette nessun'azione
     * PER UNA MAGGIORE SICUREZZA SI POTREBBE INSERIRE UN ID GENERATO CASUALMENTE ED ASSEGNATO AD OGNI UTENTE OLTRE ALL'username, IN QUESTO MODO E' PIU DIFFICILE SPACCIARSI PER QUALCUN ALTRO
     * UN ULTERIORE LIVELLO DI SICUREZZA SI PUO' OTTENERE ASSEGNANDO UN NUOVO ID CASUALE AD OGNI NUOVO LOGIN.
     * @throws RemoteException
     */
    public boolean logout() throws RemoteException {
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

    public void createAccount() {
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

    /**
     * Attenzione per ore,per scelta progettuale, il login blocca finche non si inseriscono i dati corretti, in extremis inserire alessio alessio per uscire
     * @throws RemoteException
     */
    public boolean login() throws RemoteException {
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

        System.out.print("Inserisci la data d'inizio dell'asta (formato dd/mm/yy hs:min):");
        String line = scn.nextLine();
        LocalDateTime d = formatDate(line);
        String vendor = loggedUser;
        ad.addAuction(name,price,vendor,d);
        System.out.println("Aggiunta con successo!");

    }

    private LocalDateTime formatDate(String line) {
        String[] line1 = line.split(" ");
        String[] date = line1[0].split("/");
        String[] hour = line1[1].split(":");

        LocalDateTime d = LocalDateTime.of(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]),Integer.parseInt(hour[0]),Integer.parseInt(hour[1]));
        return d;
    }

    private String showAllActiveAuctions() throws RemoteException {
        return ad.showAllActiveAuctions();
    }

    private void menu() throws RemoteException {
        int decision = 0;
        while (decision != 99) {
            System.out.println("Benvenuto nel sistema gestione d'aste,scegli cosa fare: 1)CREATE USER  2)LOGIN   100)CLOSE");
            Scanner tastiera = new Scanner(System.in);
            decision = tastiera.nextInt();
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

    private void loggedMenu() throws RemoteException {
        int decision = 0;
        while (decision != 99) {
            System.out.println("Sei nella tua area privata " + loggedUser + " scegli cosa fare: 1)CREATE AN AUCTION  2)VIEW ALL AUCTION ITEMS  3)BID FOR AN ITEM  4)LOGOUT  ");
            Scanner tastiera = new Scanner(System.in);
            decision = tastiera.nextInt();
            switch (decision) {
                case 1:
                    createAuction();
                    break;
                case 2:
                    System.out.println(showAllActiveAuctions());
                    break;
                case 4:
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

    public ClientManager() throws RemoteException  { }

    public static void main(String[] args) throws RemoteException {
        ClientManager c = new ClientManager();
        c.connect();
        c.menu();
    }
}
