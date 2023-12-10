package P2TasPrioriteMin;

import java.util.ArrayList;
import java.util.Vector;

import P1Echauffement.FileConverter;
import autres.CleInteger;
import interfaces.ICle;

/**
 * Main graphique pour les tas min.
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("BRRRRRRRR");
		//mainTabCleInteger();
		mainTabCle128();
		
		//mainArbreCleInteger();
		//mainArbreCle128();
	}
	
	/**
	 * Affiche un tas min utilisant un tableau de CleInteger.
	 */
	public static void mainTabCleInteger() {
		System.out.println("#################");
		System.out.println("Tas Min Tableau - CleInteger");
		TasMinTab tasConst = new TasMinTab(100);
		ArrayList<ICle> liste = new ArrayList<ICle>();
		int N = 10;
		for (int i=N; i>=1; i--) {
			liste.add(new CleInteger(i*10));
			liste.add(new CleInteger(i));
		}
		
		tasConst.construction(liste);
		System.out.println("\t" + tasConst + "\n");
	}
	
	/**
	 * Affiche un tas min utilisant un tableau de Cle128Bit.
	 */
	public static void mainTabCle128() {
		System.out.println("#################");
		System.out.println("Tas Min Tableau - Cle128Bit");
		
		TasMinTab tTab128 = new TasMinTab(100);
		
		FileConverter fc = new FileConverter("donnees\\cles_alea\\jeu_1_nb_cles_1000.txt");
	
		Vector<ICle> v = fc.getCle();
		
		for(int i=0; i<10; i++) {
			ICle c = v.get(i);
			tTab128.ajout(c);
		}
		
		System.out.println("\t" + tTab128.toString() + "\n");
	}
	
	/**
	 * Affiche un tas min utilisant un arbre de CleInteger.
	 */
	public static void mainArbreCleInteger() {
		System.out.println("#################");
		System.out.println("Tas Min Arbre - CleInteger");
		TasMinArbre tasConst = new TasMinArbre();
		ArrayList<ICle> liste = new ArrayList<ICle>();
		int N = 10;
		for (int i=N; i>=1; i--) {
			liste.add(new CleInteger(i));
			liste.add(new CleInteger(i));
		}
		
		tasConst.construction(liste);
		System.out.println(tasConst.infixeToString() + "\n");
	}
	
	/**
	 * Affiche un tas min utilisant un arbre de Cle128Bit.
	 */
	public static void mainArbreCle128() {
		System.out.println("#################");
		System.out.println("Tas Min Arbre - Cle128Bit");
		
		TasMinArbre tArbre128 = new TasMinArbre();
		FileConverter fc = new FileConverter("donnees/cles_alea/jeu_4_nb_cles_50000.txt");
		Vector<ICle> v = fc.getCle();
		
		for(int i=0; i<20; i++) {
			ICle c = v.get(i);
			tArbre128.ajout(c);
		}
		System.out.println(tArbre128.infixeToString() + "\n");
	}
}
