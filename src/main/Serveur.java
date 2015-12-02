package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;

/**
 * La classe serveur pour créer le serveur.
 * 
 */
public class Serveur extends ServerSocket {

	static BaseDeDonnees bd = new BaseDeDonnees();

	private boolean ok = true;
	DatagramSocket ss;

	/**
	 * Le constructeur
	 * 
	 * @param port
	 * @throws Exception
	 */
	public Serveur(int port) throws Exception {

		System.out.println("Socket serveur: " + port);

		Personne p = new Personne();
		Personne p2 = new Personne();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		list.add("superlisa");
		p.setNom("Lisa");
		p.setSurnoms(list);
		list2.add("Nono");
		p2.setNom("Arnaud");
		p2.setSurnoms(list2);
		bd.ajouterPersonne(p);
		bd.ajouterPersonne(p2);

		try {
			ss = new DatagramSocket(port);
		} catch (IOException e) {
			System.out.println("Erreur !");
			e.printStackTrace();
		}

		// on ne peut pas mettre while(true) sinon ss.close() est inatteignable
		while (ok) {
			byte[] buf = new byte[256];
			byte[] buf1 = new byte[256];
			DatagramPacket paquet = new DatagramPacket(buf, buf.length);

			ss.receive(paquet);
			InetAddress adresse = paquet.getAddress();
			int prt = paquet.getPort();
			String str = "Hello world";
			buf1 = str.getBytes();

			DatagramPacket snd = new DatagramPacket(buf1, buf1.length, adresse,
					prt);

			ss.send(snd);
		}

		ss.close();
	}

}
