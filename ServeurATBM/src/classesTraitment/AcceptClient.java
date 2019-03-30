package classesTraitment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AcceptClient implements Runnable{
	
	private ServerSocket socketServeur;
	private Socket socketClient;
	private int nbreClient = 1;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String idAction = null;
	
	public AcceptClient (ServerSocket s)
	{
		socketServeur = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			while(true)
			{
				socketClient = socketServeur.accept();
				System.out.println("Le client numéro "+nbreClient+" est connecté");
				nbreClient++;
				in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				out = new PrintWriter(socketClient.getOutputStream());
				idAction = in.readLine();
				System.out.println(idAction);
				
				if(idAction.equals("connexion"))
				{
					String [] login = new String[2];
					int i = 0;
					while(in.ready())
					{
						
							System.out.println(i);
							login[i]= in.readLine();
							System.out.println(login[i]);
						
						i++;
					}
					
					ActionConnexion ac = new ActionConnexion(login[0], login[1]);
					String connexionTest = ac.verifierConnexion();
					
					System.out.println("verifier connexion compte   "+connexionTest);
					
					out.println(connexionTest);
					if(connexionTest.equals("ok"))
					{
						int taille = ac.donneeChargee().length;
						System.out.println("Taille de donnees: "+taille);
						String [] testData = ac.donneeChargee();
						for(int j=0;j<testData.length;j++)
						{
							System.out.println("in accept client...  "+testData[j]);
							out.println(testData[j]);
						}
					}
					out.flush();
					
					
					socketClient.close();
					in.close();
					out.close();
				}
				
				if(idAction.equals("compte"))
				{
					System.out.println("debut chargement solde...");
					String [] compte = new String[20];
					
					int i = 0;
					int tailleData = 0;
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								compte[i]= in.readLine();
								System.out.println(compte[i]);
							tailleData++;
							i++;
						}
						String [] listCompte = new String[tailleData];
						for (i = 0;i<tailleData;i++)
							listCompte[i] = compte[i];
						
						ActionCompte actionCompte = new ActionCompte(listCompte);
						String [] testData = actionCompte.soldeChargee();
						for(int j=0;j<testData.length;j++)
						{
							System.out.println("in accept client...  "+testData[j]);
							out.println(testData[j]);
						}
						out.flush();
						
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("historique"))
				{
					
					System.out.println("debut chargement transactions...");
					String [] infoTrans = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoTrans[i]= in.readLine();
								System.out.println(infoTrans[i]);
							
							i++;
						}
						
						
						ActionHistorique actionHisto = new ActionHistorique(infoTrans[0], infoTrans[1], infoTrans[2]);
						String [] transData = actionHisto.transChargee();
						String [] dataToSend;
						int tailleFinal = 0;
						
						while((tailleFinal<transData.length)&&(transData[tailleFinal]!= null))
							tailleFinal++;
						
						dataToSend = new String[tailleFinal];
						
						for(int j=0;j<tailleFinal;j++)
						{	
								dataToSend[j]=transData[j];
								System.out.println("in accept client (transactions)...\n"+dataToSend[j]);
								out.println(dataToSend[j]);
								
						}
						
						out.flush();
						
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("virement"))
				{
					System.out.println("debut virement...");
					String [] infoVirement = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoVirement[i]= in.readLine();
								System.out.println(infoVirement[i]);
							
							i++;
						}
						
						ActionVirement actionVir = new ActionVirement(infoVirement[0], infoVirement[1], infoVirement[2]);
						out.println(actionVir.virement());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("d_chequier"))
				{
					System.out.println("debut demande...");
					String [] infoDemande = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoDemande[i]= in.readLine();
								System.out.println(infoDemande[i]);
							
							i++;
						}
						
						ActionDemandeCh actionDeCh = new ActionDemandeCh(infoDemande[0], infoDemande[1], infoDemande[2]);
						out.println(actionDeCh.autoDemandeCh());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("o_chequier"))
				{
					
					System.out.println("debut demande...");
					String [] infoOpposition = new String[2];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoOpposition[i]= in.readLine();
								System.out.println(infoOpposition[i]);
							
							i++;
						}
						
						ActionOppositionCh actionOppoCh = new ActionOppositionCh(infoOpposition[0], infoOpposition[1]);
						out.println(actionOppoCh.autoOppositionCh());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("d_carte"))
				{
					
					System.out.println("debut demande...");
					String [] infoDemande = new String[5];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoDemande[i]= in.readLine();
								System.out.println(infoDemande[i]);
							
							i++;
						}
						
						ActionDemandeC actionDeC = new ActionDemandeC(infoDemande[0], infoDemande[1], infoDemande[2], infoDemande[3], infoDemande[4]);
						out.println(actionDeC.autoDemandeC());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("o_carte"))
				{
					
					System.out.println("debut demande...");
					String [] infoOpposition = new String[2];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoOpposition[i]= in.readLine();
								System.out.println(infoOpposition[i]);
							
							i++;
						}
						
						ActionOppositionC actionOppoC = new ActionOppositionC(infoOpposition[0], infoOpposition[1]);
						out.println(actionOppoC.autoOppositionC());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("pwd_change"))
				{
					
					System.out.println("parametre mot de passe...");
					String [] infoPwd = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoPwd[i]= in.readLine();
								System.out.println(infoPwd[i]);
							
							i++;
						}
						
						ActionParametrePwd actionParaPwd = new ActionParametrePwd(infoPwd[0], infoPwd[1], infoPwd[2]);
						out.println(actionParaPwd.changePass());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("ajout_mail"))
				{
					
					System.out.println("parametre mot de passe...");
					String [] infoMail = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoMail[i]= in.readLine();
								System.out.println(infoMail[i]);
							
							i++;
						}
						
						ActionParametreAddMail actionParaAddMail = new ActionParametreAddMail(infoMail[0], infoMail[1], infoMail[2]);
						out.println(actionParaAddMail.ajoutMail());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("supp_mail"))
				{
					
					System.out.println("parametre mot de passe...");
					String [] infoMail = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoMail[i]= in.readLine();
								System.out.println(infoMail[i]);
							
							i++;
						}
						
						ActionParametreDeleteMail actionParaDelMail = new ActionParametreDeleteMail(infoMail[0], infoMail[1], infoMail[2]);
						out.println(actionParaDelMail.suppMail());
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(idAction.equals("inscription"))
				{
					System.out.println("Inscription...");
					String [] infoInscrit = new String[3];
					
					int i = 0;
					
					try {
						while(in.ready())
						{
							
								System.out.println(i);
								infoInscrit[i]= in.readLine();
								System.out.println(infoInscrit[i]);
							
							i++;
						}
						
						ActionInscription ai = new ActionInscription(infoInscrit[0], infoInscrit[1], infoInscrit[2]);
						if(ai.verifierCompte().equals("ok"))
						{
							String passwordG = null;
							char c;
							int j = 0;
							while(j<9)
							{
								c = toChar((int)(Math.random()*(122-65))+65);
								if((c!='0')&&(c!='[')&&(c!='\\')&&(c!=']')&&(c!='^')&&(c!='_')&&(c!='[')&&(c!='`')&&(c!='!')&&(c!='?')&&(c!=',')&&(c!=';')&&(c!='.')&&(c!='&')&&(c!='é')&&(c!='"')&&(c!='\'')&&(c!='(')&&(c!='-')&&(c!='|')&&(c!='#')&&(c!='~')&&(c!='è')&&(c!='^')&&(c!='à')&&(c!='@')&&(c!=')')&&(c!='°')&&(c!='=')&&(c!='+')&&(c!='{')&&(c!='}'))
								{
									
									passwordG += c;
									j++;
								}
								
							}
							System.out.println(passwordG.substring(4, passwordG.length()));
							ai.enregistrer(passwordG.substring(4, passwordG.length()));
							sendMessage("ATB Mobile", "Bonjour, chère client,\n\n" +
									"ATB vous souhaite la bienvenue \n" +
									"Ce mail contient votre login et mot de passe\n" +
									"Pour utiliser l'application (ATB Mobile).\n" +
									"Votre login:"+ infoInscrit[0] +"\n" +
									"Votre mot de passe de securite: "+passwordG.substring(4, passwordG.length())+"\n\n" +
									"SVP! Vous devriez entrer pour la première fois le mot de passe envoyé dans ce mail\n" +
									"Et pour confirmer, entrez votre mot de passe personnel\n\n" +
									"Merci d'être un client ATB", infoInscrit[2], infoInscrit[2]);
							out.println("inscrit ok");
						}else
							out.println("not ok");
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(idAction.equals("map"))
				{
					ArrayList<String> listCoordonnees = new ArrayList<String>();
					
					
					try {
						
						ActionMap actionMap = new ActionMap();
						
						listCoordonnees = actionMap.infoMap();
						
						System.out.println("affichage des elements");
						
						out.println("les coordonnées");
						for (int i = 0;i<listCoordonnees.size();i++)
						{
							System.out.println("in accept client...   "+listCoordonnees.get(i));
							out.println(listCoordonnees.get(i));
						}
						
						
						out.flush();
						
						socketClient.close();
						in.close();
						out.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	//convert from code ASCII to Char
		public char toChar(int codeASCII)
		{
			//System.out.println("convert from code ASCII to Char");
			//System.out.print(codeASCII+"    ");
			//System.out.println((char)codeASCII);
		       return (char)codeASCII;
		}
	
    public static void sendMessage(String subject, String text, String destinataire, String copyDest) { 

        // 1 -> Création de la session 

        Properties properties = new Properties(); 

        properties.setProperty("mail.transport.protocol", "smtp"); 

        properties.setProperty("mail.smtp.host", "smtp.gmail.com"); 

        properties.setProperty("mail.smtp.user", "intissarbypfe@gmail.com"); 

        properties.setProperty("mail.from", "intissarbypfe@gmail.com"); 

        Session session = Session.getInstance(properties);
        
        // 2 -> Création du message 

        MimeMessage message = new MimeMessage(session); 

        try { 

            message.setText(text); 

            message.setSubject(subject); 

            message.addRecipients(Message.RecipientType.TO, destinataire); 

            message.addRecipients(Message.RecipientType.CC, copyDest); 

        } catch (MessagingException e) { 

            e.printStackTrace(); 

        } 
        
        // 3 -> Envoi du message 
        
        Transport transport = null;

        try { 

            transport = session.getTransport("smtp"); 

            transport.connect("intissarbypfe@gmail.com", "1989Iby1989"); 

            transport.sendMessage(message, new Address[] { new InternetAddress(destinataire), 

                                                            new InternetAddress(copyDest) }); 

        } catch (MessagingException e) { 

            e.printStackTrace(); 

        } finally { 

            try { 

				if (transport != null) { 

                    transport.close(); 

                } 

            } catch (MessagingException e) { 

                e.printStackTrace(); 

            } 

        } 

    } 

}
