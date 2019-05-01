import Domain.AuctionMechanism.SystemManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ServerTextualApplication {
    public static void main(String[] args) throws RemoteException {
        SystemManager sys = new SystemManager();

        Registry reg = LocateRegistry.createRegistry(1099);
        System.setProperty("java.rmi.server.hostname","IPLOCAL"); //digitare ipconfig nel prompt del cmd e inserire qui l'ip locale IPV4 su cui e' esposto il registro, disattivare il firewall o aprire la porta 1099 e usare questo ip sul client
        reg.rebind("hii", sys);
        System.out.println("Server Ready");

        Scanner scn = new Scanner(System.in);
        while(true) {
            System.out.println("1)Carica da File   2)Salva su file");
            int decision = scn.nextInt();
            switch (decision) {
                case 1:
                    sys.loadState();
                    break;
                case 2:
                    sys.saveState();
                    break;
                default:
                    break;
            }
        }
    }
}
