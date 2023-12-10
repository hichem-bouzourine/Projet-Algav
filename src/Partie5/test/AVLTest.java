package Partie5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Partie5.AVL;
import Partie5.InsertionException;
import Partie5.Noeud;
import autres.CleInteger;

public class AVLTest {

	private int taille = 10;
	private AVL<CleInteger> arbre;
	
	@Before
	public void initialiserAVL() {
		arbre = new AVL<CleInteger>();
		for (int i=0; i<taille; i++)
			try {
				arbre.inserer(new CleInteger(i));
			} catch (InsertionException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Teste la structure de l'arbre (si les clés sont bien dans les emplacements
	 * ou elle sont sensées être).
	 */
	@Test
	public void testStructure() {
		assertTrue(arbre.getRacine().getCle().eg(new CleInteger(3)));
		assertTrue(arbre.getRacine().getFilsGauche().getCle().eg(new CleInteger(1)));
		assertTrue(arbre.getRacine().getFilsGauche().getFilsGauche().getCle().eg(new CleInteger(0)));
		assertTrue(arbre.getRacine().getFilsGauche().getFilsDroit().getCle().eg(new CleInteger(2)));
		assertTrue(arbre.getRacine().getFilsDroit().getCle().eg(new CleInteger(7)));
		assertTrue(arbre.getRacine().getFilsDroit().getFilsGauche().getCle().eg(new CleInteger(5)));
		assertTrue(arbre.getRacine().getFilsDroit().getFilsGauche().getFilsGauche().getCle().eg(new CleInteger(4)));
		assertTrue(arbre.getRacine().getFilsDroit().getFilsGauche().getFilsDroit().getCle().eg(new CleInteger(6)));
		assertTrue(arbre.getRacine().getFilsDroit().getFilsDroit().getFilsDroit().getCle().eg(new CleInteger(9)));
	}
	/**
	 * Vérifie que l'on ne peux insérer deux fois la même clé.
	 */
	public void testRecherche() {
		try {
			Noeud<CleInteger> res = arbre.rechercher(new CleInteger(5)).getKey();
			
			assertTrue(res.getCle().getValeur() == 5);
			assertTrue(res.getFilsGauche().getCle().getValeur() == 4);
			assertTrue(res.getFilsDroit().getCle().getValeur() == 6);
			
			arbre.rechercher(new CleInteger(taille));
			assertTrue(false);
		}
		catch (Exception e) {
			assertTrue(true);
		}
	}

	/**
	 * Méthode pour vérifier qu'une liste est triée.
	 * @param l La liste à vérifier.
	 * @return true si la liste est triée. false sinon.
	 */
	private boolean isSorted(List<CleInteger> l) {
		CleInteger c1;
		CleInteger c2;
		for (int i=1; i<l.size(); i++) {
			c1 = l.get(i-1);
			c2 = l.get(i);
			if (!c1.inf(c2))
				return false;
		}
		return true;
	}
	/**
	 * Teste la taille de l'arbre.
	 */
	@Test
	public void testSize() {
		assertEquals(arbre.size(), taille);
	}
	
	/**
	 * Teste si l'ordre préfixe retourne bien les clés dans l'ordre croissant.
	 */
	@Test
	public void testOrdre() {
		List<CleInteger> l = arbre.getCleTriee();
		assertTrue(isSorted(l));
		l.add(new CleInteger(4));
		assertFalse(isSorted(l));
	}
}
