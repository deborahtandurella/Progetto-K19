import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {
    private ServerSocket server=null;
    private Socket socketClient=null;
    BufferedReader in;
    DataOutputStream out;
    BufferedReader keyboard;
    //DataInputStream inff;
    int porta=6789;
    public boolean communicate(){
        try{
            String letto=in.readLine();
            System.out.println("--<"+ letto +'\n');
            System.out.println("--> ");
            keyboard=new BufferedReader(new InputStreamReader(System.in));
            String risposta=keyboard.readLine();
            out.writeBytes(risposta + "\n");
            if(risposta.equals("Chiudo")){
                return false;
            }
            else
                return true;
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return false;




    }

    public Socket waiting(){
        try{
            System.out.println("[0]- Inizializzo il server...");
            server=new ServerSocket(porta);// inizializza il servizio
            System.out.println("[1]-Server pronto,in ascolto sulla porta: "+porta);
            // mi metto in ascolto sulla porta
            socketClient=server.accept();
            System.out.println("[2]-Connessione stabilita con un client");
            // evitiamo connessioni multiple
            server.close();
            in= new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            out=new DataOutputStream(socketClient.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return socketClient;
    }
    public static void main(String argv[]) throws Exception {
        Server s = new Server();
        s.waiting();
        while(s.communicate()){};
    }
}