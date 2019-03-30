package classesTraitment;
import java.io.IOException;
import java.net.ServerSocket;


public class ServeurPrincipale  {
	
	public static void main (String [] atbm)
	{
		ServerSocket socketServeurAtbM;
		
		try {
			socketServeurAtbM = new ServerSocket(2014);
			Thread threadClient = new Thread(new AcceptClient(socketServeurAtbM));
			threadClient.start();
			System.out.println("Lancement des threads");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
