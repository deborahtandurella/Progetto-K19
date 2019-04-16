import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
    Socket mySocket = null;
    int porta=6789;
    BufferedReader in;
    DataOutputStream out;
    BufferedReader keyboard;
    public boolean communicate(){
        try{
            System.out.println("-->");
            keyboard=new BufferedReader(new InputStreamReader(System.in));
            String message=keyboard.readLine();
            out.writeBytes(message + "\n");
            String received=in.readLine();
            System.out.printf("--<  "+ received+'\n');
            if (message.equals("Chiudo")) {
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







    public Socket connect(){
        try{
            System.out.println("[0]-Inizializzo il client....");
            Socket mySocket=new Socket(InetAddress.getLocalHost(),porta);
            System.out.println("[1]-Bonjour, Finesse");
            out=new DataOutputStream(mySocket.getOutputStream());
            in=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        }
        catch(UnknownHostException e){
            System.err.println("Host sconosciuto");
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return mySocket;

    }




    public static void main(String argv[]){
        Client c=new Client();
        c.connect();
        while( c.communicate()){};
    }

}