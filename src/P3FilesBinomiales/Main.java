package P3FilesBinomiales;

import java.util.Vector;

import P1Echauffement.FileConverter;
import autres.CleInteger;
import interfaces.ICle;

/**
 * Main graphique pour la file binomiale.
 */
public class Main {

	public static void main(String[] args) {
		mainCleInteger();
		mainCle128();
	}
	
	/**
	 * Affiche une file binomiale utilisant des CleInteger.
	 */
	public static void mainCleInteger() {
		System.out.println("#################");
		System.out.println("File Binomiale - CleInteger");
		FileBinomiale fileBinomiale = new FileBinomiale(1000);
		
		int N = 10;
		for (int i=N; i>=1; i--) {
			fileBinomiale = (FileBinomiale) fileBinomiale.ajout(new TournoiBinomial(new CleInteger(i)));
			fileBinomiale = (FileBinomiale) fileBinomiale.ajout(new TournoiBinomial(new CleInteger(i)));
		}
		System.out.println(fileBinomiale.toString() + "\n");
	}
	
	/**
	 * Affiche une file binomiale utilisant des Cle128Bit.
	 */
	public static void mainCle128() {
		System.out.println("#################");
		System.out.println("File Binomiale - Cle128Bit");
		
		FileBinomiale fileBinomiale = new FileBinomiale(10);
		FileConverter fc = new FileConverter("donnees/cles_alea/jeu_4_nb_cles_100.txt");
		Vector<ICle> v = fc.getCle();
		
		for(int i=0; i<19; i++)
			fileBinomiale = (FileBinomiale) fileBinomiale.ajout(new TournoiBinomial(v.get(i)));
		
		System.out.println(fileBinomiale.toString() + "\n");
	}
}
