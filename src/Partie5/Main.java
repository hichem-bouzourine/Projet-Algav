package Partie5;

import java.util.Vector;

import Partie1.Cle128Bit;
import Partie1.FileConverter;
import interfaces.ICle;

public class Main {

	public static void main(String[] args) throws InsertionException {
		System.out.println("#################");
		System.out.println("AVL - Cle128Bit");
		
		AVL<Cle128Bit> avl = new AVL<>();
		FileConverter fc = new FileConverter("donnees/cles_alea/jeu_3_nb_cles_5000.txt");
		Vector<ICle> v = fc.getCle();
		
		for(int i=0; i<14; i++) {
			ICle c = v.get(i);
			avl.inserer((Cle128Bit) c);
		}
		
		System.out.println(avl.infixeToString() + "\n");
		// AVL.genererResultatRecherche();
	}
	
	

}
