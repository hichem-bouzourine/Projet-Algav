package P2TasPrioriteMin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import interfaces.ICle;
import interfaces.ITasMin;


public class TasMinArbre implements ITasMin {

	/** La racine de l'arbre. */
	private Noeud racine;
	
	/** Le noeud sur lequel se fera la prochaine insertion. */
	private Noeud positionProchaineInsertion;
	
	/**
	 * La pile des noeuds retirés.
	 * Utilisé afin de réinsérer les noeuds à la bonne place du tas.
	 * */
	private Stack<Noeud> pileNoeud;
	
	/** La taille du tas */
	private int taille;
	
	/**
	 * Constructeur d'un tas min vide.
	 */
	public TasMinArbre() {
		racine = null;
		positionProchaineInsertion = racine;
		pileNoeud = new Stack<>();
		taille = 0;
	}
	
	/**
	 * Constructeur d'un tas min avec une clé.
	 * @param n La clé à insérer.
	 */
	public TasMinArbre(ICle n) {
		// La racine n'a pas de père est n'est ni un fils gauche, ni un fils droit
		racine = new Noeud(n, null, false, false);		
		positionProchaineInsertion = racine.getFilsGauche();
		pileNoeud = new Stack<>();
		taille = 1;
	}
	
	/** Retourne la racine de l'arbre sans la supprimer */
	public Noeud getRacine() {
		return racine;
	}
	
	@Override
	public int size() {
		return taille;
	}

	@Override
	public void ajout(ICle c) {
		taille++;
		// On insère à la position du dernier noeud supprimé de la pile
		if (!pileNoeud.isEmpty()) {
			Noeud prochain = pileNoeud.pop();
			prochain.setNoeud(c);
			
			if ((prochain.getPere() != null) && (prochain.getPere().isExtremeRight())) {
				if (prochain.estFilsDroit()) {
					prochain.setisExtremeRight(true);
					prochain.getPere().setisExtremeRight(false);
				}
			}
			
			// Il faut remonter la clé
			remonterCle(prochain);
		}	
		else if (positionProchaineInsertion != null){
			positionProchaineInsertion.add(c);
			
			Noeud anciennepositionProchaineInsertion = positionProchaineInsertion;
			
			positionProchaineInsertion = positionProchaineInsertion(anciennepositionProchaineInsertion);
			
			if (anciennepositionProchaineInsertion.isExtremeRight())
				anciennepositionProchaineInsertion.setisExtremeRight(false);
			
			remonterCle(anciennepositionProchaineInsertion);
		}
		// Insertion à la racine
		else {
			racine = new Noeud(c, null, false, false);
			positionProchaineInsertion = racine.getFilsGauche();
		}
	}

	public boolean ajoutsIteratifs(List<ICle> elems){
		if(elems.size()==0)
			return false; 
		for(ICle c : elems)
			ajout(c);
		return true; 
	}



	
	/**
	 * Calcule le prochain noeud sur lequel se fera la prochaine insertion.
	 * @param courant Le noeud sur lequel on vient d'insérer.
	 * @return Le noeud sur lequel se fera la prochaine insertion.
	 */
	private Noeud positionProchaineInsertion(Noeud courant) {
		if (courant.isLeftChild())
			return courant.getPere().getFilsDroit();
		else if (courant.isExtremeRight())
			return positionProchaineInsertionDepuisExtremite();
		else
			return positionProchaineInsertionDepuisFilsDroit(courant);
	}
	
	/**
	 * Calcule le prochain noeud sur lequel doit se faire la prochaine insertion en cas d'insertion dans un fils droit.
	 * Le fils droit ne doit pas être l'extrémité de l'arbre.
	 * Le noeud renvoyé sera le noeud le plus à gauche par rapport au premier fils gauche à la remontée.
	 * @param courant Le noeud sur lequel on vient d'insérer.
	 * @return Le noeud sur lequel se fera la prochaine insertion.
	 */
	private Noeud positionProchaineInsertionDepuisFilsDroit(Noeud courant) {
		// On est sur un fils gauche.
		// La prochaine insertion se fait sur le fils droit du père puis tout à gauche
		if(courant.isLeftChild())
			return trouverExtremiteGauche(courant.getPere().getFilsDroit()); 

		// On est à la racine.
		// La prochaine insertion se fait sur le chemin le plus à gauche depuis le fils droit
		else  if (courant.getPere() == null)
			return trouverExtremiteGauche(courant.getFilsDroit());
		
		// On est sur un fils droit.
		// Il faut continuer à monter.
		else
			return positionProchaineInsertionDepuisFilsDroit(courant.getPere());
	}
	
	/**
	 * Calcule le prochain noeud sur lequel doit se faire la prochaine insertion en cas d'insertion à l'extémité de l'arbre.
	 * Le noeud renvoyé sera le noeud le plus à gauche par rapport à la racine.
	 * @return Le noeud sur lequel se fera la prochaine insertion.
	 */
	private Noeud positionProchaineInsertionDepuisExtremite() {	
		// On cherche l'extremité gauche de la racine
		return trouverExtremiteGauche(racine);
	}
	
	/**
	 * Recherche le noeud le plus à gauche depuis le noeud courant.
	 * @param courant Le noeud sur lequel on vient d'insérer.
	 * @return Le noeud le plus à gauche depuis le noeud courant.
	 */
	private Noeud trouverExtremiteGauche(Noeud courant) {
		while(true) {
			if (courant.getFilsGauche() == null)
				break;
			else
				courant = courant.getFilsGauche();
		}
		return courant;
	}
	
	/**
	 * Remonte la clé dans l'arbre afin de garder une structure de tas min.
	 * @param courant Le noeud depuis lequel il faut faire la remontée.
	 */
	private void remonterCle(Noeud courant) {
		if ((courant.getPere() != null) && (courant.getPere().getNoeud() != null)) {
			if (courant.getNoeud().inf(courant.getPere().getNoeud())) {			// La valeur de la clé courante est inférieure à celle du père
				ICle tmp = courant.getNoeud();
				courant.setNoeud(courant.getPere().getNoeud());
				courant.getPere().setNoeud(tmp);
				remonterCle(courant.getPere());
			}
		}
	}
	
	@Override
	public ICle supprMin() {
		if(taille == 0)
			return null;
		else {
			taille--;
			ICle min = racine.getNoeud();
			supprMin(racine);
			return min;
		}
	}
	
	/**
	 * Swap récursivement les clés de l'arbre pour conserver une structure de tas min
	 * @param courant Le noeud courant
	 */
	private void supprMin(Noeud courant) {
		if ((courant.getFilsGauche().getNoeud() != null) && (courant.getFilsDroit().getNoeud() != null)) {		
			if (courant.getFilsGauche().getNoeud().inf(courant.getFilsDroit().getNoeud()))	{		
				courant.setNoeud(courant.getFilsGauche().getNoeud());
				supprMin(courant.getFilsGauche());
			}
			else {																								
				courant.setNoeud(courant.getFilsDroit().getNoeud());
				supprMin(courant.getFilsDroit());
			}
		}
		else if (courant.getFilsGauche().getNoeud() != null) {	
			courant.setNoeud(courant.getFilsGauche().getNoeud());
			supprMin(courant.getFilsGauche());
		}
		else if (courant.getFilsDroit().getNoeud() != null) {	
			courant.setNoeud(courant.getFilsDroit().getNoeud());
			supprMin(courant.getFilsDroit());
		}
		else {	// deux noeuds fils à null
			if (courant.isExtremeRight()) {
				courant.setisExtremeRight(false);
				courant.getPere().setisExtremeRight(true);
			}
			courant.setNoeud(null);
			pileNoeud.push(courant);
		}
	}

	@Override
	public boolean construction(List<ICle> elems) {
		if (elems.size() == 0)
			return false;
		taille = elems.size();
		List<ICle> listeTriee = ConstruireTasMin.convertirListeCleTriee(elems);
		racine = new Noeud(null, false, false);
		
		// Il faut construire l'arbre
		construction(racine, listeTriee, 0);
		
		return true;
	}
	
	/**
	 * Méthode recursive pour construire l'arbre du tas min en parcourant le tableau.
	 * @param courant Le noeud courant
	 * @param elems La liste des éléments du tas.
	 * @param indiceCourant L'indice courant dans la liste.
	 */
	private void construction(Noeud courant, List<ICle> elems, int indiceCourant) {
		int size = elems.size();
		if (indiceCourant == size-1)
			positionProchaineInsertion = positionProchaineInsertion(courant);
		courant.setNoeud(elems.get(indiceCourant));
		
		int gauche = 2 * indiceCourant + 1;
		int droit = 2 * indiceCourant + 2;
		
		courant.setFilsGauche(new Noeud(courant, true, false));
		courant.setFilsDroit(new Noeud(courant, false, droit == size - 1));
		if (gauche < size) {
			construction(courant.getFilsGauche(), elems, gauche);
		}
		if (droit < size) {
			construction(courant.getFilsDroit(), elems, droit);
		}
	}




	
	
	@Override
	public void union(ITasMin t2) {
		// L'objectif pour effectuer l'union est d'utiliser la méthode construction
		// Pour cela il faut réaliser une liste contenant tous les éléments des deux tas
		// Afin de ne pas avoir de doublon et de garder une complexité linéaire, nous allons utiliser un Set
		ICle[] tabTas1 = this.getRepresentationTableau();
		ICle[] tabTas2 = t2.getRepresentationTableau();
		
		Set<ICle> unionSet = new HashSet<ICle>();
		
		// On ajoute le premier tas à au set
		for(int i=0; i<tabTas1.length; i++)
			unionSet.add(tabTas1[i]);
		
		// On ajoute le second tas à au set
		for(int i=0; i<tabTas2.length; i++)
			unionSet.add(tabTas2[i]);
		
		// On crée une liste contenant tous les élément du tas : complexité linéaire
		List<ICle> unionSansDoublon = new ArrayList<ICle>(unionSet);
		
		// On appelle construction pour construire le tas contenant l'union des deux tas
		construction(unionSansDoublon);
	}

	/**
	 * Renvoie la string représentant le parcours infixe du tas
	 * @return La string représentant le parcours infixe du tas.
	 */
	public String infixeToString() {
		if (racine == null)
			return "";
		else
			return racine.infixeToString("\t");
	}

	@Override
	public ICle[] getRepresentationTableau() {
		ICle[] tab = new ICle[size()];
		
		getRepresentationTableau(racine, tab, 0);
		
		return tab;
	}
	
	/**
	 * Méthode récursive pour obtenir le tas sous forme de tableau.
	 * @param courant Le noeud courant.
	 * @param tab Le tab à modifier.
	 * @param indiceCourant L'indice du noeud dans le tableau.
	 */
	private void getRepresentationTableau(Noeud courant, ICle[] tab, int indiceCourant) {
		tab[indiceCourant] = courant.getNoeud();
		if (courant.getFilsGauche().getNoeud() != null)
			getRepresentationTableau(courant.getFilsGauche(), tab, 2 * indiceCourant + 1);
		if (courant.getFilsDroit().getNoeud() != null)
			getRepresentationTableau(courant.getFilsDroit(), tab, 2 * indiceCourant + 2);
	}
}
