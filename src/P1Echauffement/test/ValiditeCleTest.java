package P1Echauffement.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import P1Echauffement.Cle128Bit;
import P1Echauffement.FileConverter;
import interfaces.ICle;

public class ValiditeCleTest{

	FileConverter fileConverter;
	Cle128Bit cle32;
	Cle128Bit cle64;
	Cle128Bit cle96;
	Vector <ICle> cles;
	
	@Before
	public void initialize() {
		fileConverter = new FileConverter("donnees/cles_alea/jeu_1_nb_cles_1000.txt");
		cles = fileConverter.getCle();
		cle32 = new Cle128Bit("ABCDFEff");
		cle64 = new Cle128Bit("ABCDFEffE89AF1A2");
		cle96 = new Cle128Bit("ABCDFEffE89AF1A2EFC892A8");
	}
	
	@Test
	public void testInferieure() {
		assertFalse(cle96.inf(cle32));
		assertTrue(cle32.inf(cle64));
	}
	
	@Test
	public void testEgalite() {
		assertFalse(cle32.eg(cle64));
		assertTrue(cle32.eg(cle32));
	}
	
	@Test
	public void testEgAndInfCle() {
		assertTrue(cles.get(3).eg(cles.get(3)));
		assertFalse(cles.get(3).inf(cles.get(3)));
		assertFalse(cles.get(3).inf(cles.get(2)));
	}
}
