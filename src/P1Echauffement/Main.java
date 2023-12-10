package P1Echauffement;
import java.util.Vector;
	
import interfaces.ICle;
/**
 * Main graphique pour les clés de 128 bits.
 */
public class Main {	
	
	public static void main(String[] args) {
		Cle128Bit c;
		FileConverter cv = new FileConverter("donnees/cles_alea/jeu_5_nb_cles_1000.txt");
		Vector <ICle> cles = cv.getCle();
		//System.out.println(cles);
		Vector <String> clesS = cv.getCleHexa();
		
		String hex;
		
		for(int i=0; i<cles.size(); i++) {
			c = (Cle128Bit) cles.get(i);
		 	hex = clesS.get(i);
			
		 	System.out.println(hex + " ==> " + c.valeurBinaire());
		}
		System.out.println("--------------------------");
		c = new Cle128Bit("73f7ffcaae4a6081927b69238c4a8a8a");
		Cle128Bit cle32 = new Cle128Bit("9CF48CF5");
		Cle128Bit cle64 = new Cle128Bit("9CF48CF4E89AF1A2");
		System.out.println(c.toString()+" "+ c.valeurBinaire().length());
		System.out.println(c.valeurBinaire());
		System.out.println("Cle32 binaire: " + cle32.valeurBinaire());
		System.out.println("Cle64 binaire: " + cle64.valeurBinaire());
		System.out.println(cle32.inf(cle64));
	}

}
