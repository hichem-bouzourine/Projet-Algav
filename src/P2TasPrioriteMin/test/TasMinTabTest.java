package P2TasPrioriteMin.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import P1Echauffement.Cle128Bit;
import P1Echauffement.FileConverter;
import P2TasPrioriteMin.TasMinTab;
import autres.CleInteger;
import interfaces.ICle;

public class TasMinTabTest {
	TasMinTab tas1;
	TasMinTab tasConstLoop;
	TasMinTab tas2;
	List<ICle> list;
	
	@Before
	public void initialize() {
		tas1 = new TasMinTab(5);
		tas2 = new TasMinTab(5);
		tasConstLoop = new TasMinTab(5);
		
		list = new ArrayList<>();
		list.add(new CleInteger(1));
		list.add(new CleInteger(9));
		list.add(new CleInteger(5));
		list.add(new CleInteger(10));
		list.add(new CleInteger(11));
		list.add(new CleInteger(3));
		list.add(new CleInteger(2));
	}
	
	@Test
	public void testAjoutCle() {
		tas1.ajout(new Cle128Bit("9CF48CF5"));
		tas1.ajout(new Cle128Bit("6CF48C5"));
		tas1.ajout(new Cle128Bit("AEF48CF5"));
		
		assertEquals(tas1.size(), 3);
		testStructureTasMin(tas1, 0);
		
		//Pour des contrainte d'affichage, on utilise ICleInteger
		tas1 = new TasMinTab(5);
		
		tas1.ajout(new CleInteger(1));
		tas1.ajout(new CleInteger(9));
		tas1.ajout(new CleInteger(5));
		tas1.ajout(new CleInteger(10));
		tas1.ajout(new CleInteger(11));
		tas1.ajout(new CleInteger(3));
		
		assertEquals(tas1.size(), 6);
		assertEquals(tas1.toString(), "[1, 9, 3, 10, 11, 5]");
		testStructureTasMin(tas1, 0);
	}
	
	@Test
	public void testDeleteCleMin() {
		
		tas1.ajout(new Cle128Bit("149CF48CF5"));
		tas1.ajout(new Cle128Bit("56CF48C5"));
		tas1.ajout(new Cle128Bit("EF48CF5"));
		tas1.ajout(new Cle128Bit("EF48CF8A954A5"));
		assertEquals(tas1.size(), 4);
		testStructureTasMin(tas1, 0);
		
		
		assertTrue(tas1.supprMin() != null);
		assertEquals(tas1.size(), 3);
		testStructureTasMin(tas1, 0);
		
		assertTrue(tas1.supprMin() != null);
		assertEquals(tas1.size(), 2);
		testStructureTasMin(tas1, 0);
		
		assertTrue(tas1.supprMin() != null);
		assertEquals(tas1.size(), 1);
		testStructureTasMin(tas1, 0);
		
		//Pour des contrainte d'affichage, on utilise ICleInteger
		tas1 = new TasMinTab(5);
		
		tas1.ajout(new CleInteger(10));
		tas1.ajout(new CleInteger(9));
		tas1.ajout(new CleInteger(15));
		tas1.ajout(new CleInteger(10));
		tas1.ajout(new CleInteger(11));
		tas1.ajout(new CleInteger(30));
		
		assertEquals(tas1.size(), 6);
		assertEquals("[9, 10, 15, 10, 11, 30]", tas1.toString());
		testStructureTasMin(tas1, 0);
		
		assertTrue(tas1.supprMin() != null);
		
		assertEquals(tas1.size(), 5);
		assertEquals(tas1.toString(), "[10, 10, 15, 30, 11]");
		testStructureTasMin(tas1, 0);
	}

	@Test
	public void testConstructionTasMin() {
		
		assertEquals(tasConstLoop.size(), 0);
		
		tasConstLoop.construction(list);
		
		assertEquals(tasConstLoop.size(), list.size());
		assertEquals(tasConstLoop.toString(), "[1, 9, 2, 10, 11, 3, 5]");
		testStructureTasMin(tasConstLoop, 0);
		
	}
	
	@Test
	public void testUnionTas() {
		
		tas1.ajout(new Cle128Bit("149CF48CF5"));
		tas1.ajout(new Cle128Bit("56CF48C5"));
		tas1.ajout(new Cle128Bit("EF48CF5"));
		tas1.ajout(new Cle128Bit("EF48CF8A954A5"));
		assertEquals(tas1.size(), 4);
		testStructureTasMin(tas1, 0);
		
		tas2.ajout(new Cle128Bit("11C9CF49CF7"));
		tas2.ajout(new Cle128Bit("EA4CCC5"));
		tas2.ajout(new Cle128Bit("AEF49C64A954A5"));
		tas2.ajout(new Cle128Bit("AEF49C64A954A5"));
		tas2.ajout(new Cle128Bit("EA4CCC5"));
		testStructureTasMin(tas2, 0);
		
		tas1.union(tas2);
		assertEquals(tas1.size(), 7);
		testStructureTasMin(tas1, 0);
		
		//Pour des contrainte d'affichage, on utilise ICleInteger
		tas1 = new TasMinTab(5);
		tas2 = new TasMinTab(5);
		
		tas1.ajout(new CleInteger(10));
		tas1.ajout(new CleInteger(9));
		tas1.ajout(new CleInteger(15));
		tas1.ajout(new CleInteger(0));
		testStructureTasMin(tas1, 0);
		
		tas2.ajout(new CleInteger(10));
		tas2.ajout(new CleInteger(11));
		tas2.ajout(new CleInteger(30));
		tas2.ajout(new CleInteger(0));
		tas2.ajout(new CleInteger(90));
		testStructureTasMin(tas2, 0);
		
		tas1.union(tas2);
		assertEquals(tas1.toString(), "[0, 9, 10, 90, 11, 30, 15]");
		testStructureTasMin(tas1, 0);
		
	}
	
	@Test
	public void testAvecCle128Bit() {
		TasMinTab tArbre128 = new TasMinTab(50000);
		FileConverter fc = new FileConverter("donnees/cles_alea/jeu_4_nb_cles_50000.txt");
		Vector<ICle> v = fc.getCle();
		
		for(ICle c : v)
			tArbre128.ajout(c);
		
		testStructureTasMin(tArbre128, 0);
		
		tArbre128.supprMin();
		testStructureTasMin(tArbre128, 0);
		tArbre128.supprMin();
		testStructureTasMin(tArbre128, 0);
		tArbre128.supprMin();
		
		testStructureTasMin(tArbre128, 0);
		
		fc = new FileConverter("donnees/cles_alea/jeu_2_nb_cles_20000.txt");
		v = fc.getCle();
		for(ICle c : v)
			tArbre128.ajout(c);
		
		testStructureTasMin(tArbre128, 0);
		
		tArbre128.supprMin();
		
		testStructureTasMin(tArbre128, 0);
	}
	
	@Test
	public void testClesAlea() {
		TasMinTab tas;
		ArrayList<ICle> liste;
		
		int tailles[] = {100, 200, 500, 1000, 5000, 10000, 20000, 50000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				tas = new TasMinTab(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Test sur : " + nomFichier + " Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				liste = new ArrayList<ICle>(fc.getCle());					// La liste des clé à insérer
				tas.construction(liste);
				testStructureTasMin(tas, 0);
			}
		}
	}
	
	public void testStructureTasMin(TasMinTab tas, int courant) {
		int filsGauche = tas.idFilsGauche(courant);
		int filsDroit = tas.idFilsDroit(courant);
		ICle[] tab = tas.getRepresentationTableau();
		if (filsGauche != -1) {
			boolean assertion = (tab[courant].inf(tab[filsGauche]) || tab[courant].eg(tab[filsGauche])); 
			// System.out.println("(" + tab[courant] + " <= " + tab[filsGauche] + ")   ==> " + assertion);
			assertTrue(assertion);
			testStructureTasMin(tas, filsGauche);
		}
		if (filsDroit != -1) {
			boolean assertion = (tab[courant].inf(tab[filsDroit]) || tab[courant].eg(tab[filsDroit])); 
			// System.out.println("(" + tab[courant] + " <= " + tab[filsDroit] + ")   ==> " + assertion);
			assertTrue(assertion);
			testStructureTasMin(tas, filsDroit);
		}
	}
}
