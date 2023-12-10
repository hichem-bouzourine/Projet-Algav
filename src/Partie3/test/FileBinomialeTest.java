package Partie3.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import Partie1.FileConverter;
import Partie3.FileBinomiale;
import Partie3.TournoiBinomial;
import autres.CleInteger;
import interfaces.ICle;
import interfaces.IFileBinomiale;
import interfaces.ITournoiBinomial;

public class FileBinomialeTest {

	
	IFileBinomiale fileBinomiale;
	
	@Before
	public void initialize() {
		fileBinomiale = new FileBinomiale(1000);
		
		int N = 100000;
		for (int i=N; i>=1; i--) {
			fileBinomiale = fileBinomiale.ajout(new TournoiBinomial(new CleInteger(i)));
			fileBinomiale = fileBinomiale.ajout(new TournoiBinomial(new CleInteger(i)));
		}
		
		assertTrue(fileBinomiale.getTaille() == N*2);
	}
	
	@Test
	public void testAjoutCle() {
		testStructureFile(fileBinomiale);
	}
	
	@Test
	public void testSupprMin() {
		for (int i=0; i<100000; i++)
			fileBinomiale = fileBinomiale.supprCleMin();
		
		testStructureFile(fileBinomiale);
	}
	
	@Test
	public void testSupprMin2() {
		for (int i=100000; i>=0; i--)
			fileBinomiale = fileBinomiale.supprCleMin();
		
		testStructureFile(fileBinomiale);
	}
	
	@Test
	public void testSupprMin3() {
		for (int i=0; i>=10000; i--) {
			fileBinomiale = fileBinomiale.ajout(new TournoiBinomial(new CleInteger(i)));
			fileBinomiale = fileBinomiale.supprCleMin();
		}
		
		testStructureFile(fileBinomiale);
	}
	
	@Test
	public void construction() {
		List<ICle> liste = new ArrayList<ICle>();
		
		for (int i=1000000; i>0; i--)
			liste.add(new CleInteger(i));
		
		IFileBinomiale fileBinomialeConstIter = new FileBinomiale(0);
		fileBinomialeConstIter.construction(liste);
		
		testStructureFile(fileBinomialeConstIter);
		
		for (int i=5000000; i>4000000; i--)
			fileBinomialeConstIter = fileBinomialeConstIter.ajout(new TournoiBinomial(new CleInteger(i)));
		
		testStructureFile(fileBinomialeConstIter);
		
		for (int i=0; i<1500000; i++)
			fileBinomialeConstIter = fileBinomialeConstIter.supprCleMin();
			
		testStructureFile(fileBinomialeConstIter);
	}
	
	@Test
	public void testUnion() {
		IFileBinomiale fileBinomialeUnion1 = new FileBinomiale(0);
		IFileBinomiale fileBinomialeUnion2 = new FileBinomiale(0);
		for (int i=100000; i>0; i--)
			fileBinomialeUnion1 = fileBinomialeUnion1.ajout(new TournoiBinomial(new CleInteger(i)));
		
		for (int i=0; i<250000; i++)
			fileBinomialeUnion2 = fileBinomialeUnion2.ajout(new TournoiBinomial(new CleInteger(i)));
		
		fileBinomialeUnion1 = fileBinomialeUnion1.unionFile(fileBinomialeUnion1, fileBinomialeUnion2);
		fileBinomialeUnion2 = fileBinomialeUnion2.unionFile(fileBinomialeUnion2, fileBinomialeUnion2);
		
		testStructureFile(fileBinomialeUnion1);
		testStructureFile(fileBinomialeUnion2);
	}
	
	@Test
	public void testAvecCle128Bit() {
		IFileBinomiale fileBinomiale128 = new FileBinomiale(0);
		FileConverter fc = new FileConverter("donnees/cles_alea/jeu_4_nb_cles_50000.txt");
		Vector<ICle> v = fc.getCle();
		
		for(ICle c : v)
			fileBinomiale128 = fileBinomiale128.ajout(new TournoiBinomial(c));
		
		testStructureFile(fileBinomiale128);
		
		fileBinomiale128 = fileBinomiale128.supprCleMin();
		fileBinomiale128 = fileBinomiale128.supprCleMin();
		fileBinomiale128 = fileBinomiale128.supprCleMin();
		
		testStructureFile(fileBinomiale128);
		
		fc = new FileConverter("donnees/cles_alea/jeu_2_nb_cles_20000.txt");
		v = fc.getCle();
		for(ICle c : v)
			fileBinomiale128 = fileBinomiale128.ajout(new TournoiBinomial(c));
		
		testStructureFile(fileBinomiale128);
		
		fileBinomiale128 = fileBinomiale128.supprCleMin();
		
		testStructureFile(fileBinomiale128);
	}
	
	@Test
	public void testClesAlea() {
		IFileBinomiale fileBinomialeAlea;
		ArrayList<ICle> liste;
		
		int tailles[] = {100, 200, 500, 1000, 5000, 10000, 20000, 50000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				fileBinomialeAlea = new FileBinomiale(0);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + " Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				liste = new ArrayList<ICle>(fc.getCle());					
				fileBinomialeAlea.construction(liste);
				testStructureFile(fileBinomialeAlea);
			}
		}
	}
	
	public void testStructureFile(IFileBinomiale fileBinomiale) {
		ITournoiBinomial tournoi;
		for (int i=0; i<fileBinomiale.getNbTas(); i++) {
			tournoi = fileBinomiale.getTas(i);
			testStructureTournoi(tournoi);
		}
	}
	
	public void testStructureTournoi(ITournoiBinomial tournoi) {
		ICle racine  = tournoi.getCle();
		ITournoiBinomial filsActuel;
		for (int i=0; i<tournoi.getNbFils(); i++) {
			filsActuel = tournoi.getFils(i);
			boolean assertion = (racine.inf(filsActuel.getCle()) || racine.eg(filsActuel.getCle())); 
			assertTrue(assertion);
			testStructureTournoi(filsActuel);
		}		
	}
}
