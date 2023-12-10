package P3FilesBinomiales;

import java.util.LinkedList;

import interfaces.ICle;
import interfaces.IFileBinomiale;
import interfaces.ITournoiBinomial;

/**
 * Classe d'un tournoi binomial.
 */
public class TournoiBinomial implements ITournoiBinomial{
	/** Ensemble des tournois binomiaux fils, du tournoi binomial. */
	private LinkedList<ITournoiBinomial> fils;
	/** Clé stockée dans le noeud. */
	private ICle cle;
	/** Degre a la racine (TBk). */
	private int degre;  
	/** Nombre de cle insérée dans le tournoi. */
	private int taille;
	
	/**
	 * Constructeur d'un tournoi binomial, stockant une clé en noeud.
	 * @param c clé à stocker dans le noeud
	 */
	public TournoiBinomial(ICle c) {
		fils = new LinkedList<>();
		cle = c;
		// Initialement on a un TB0, ne possédant pas de fils, donc de degré 0.
		degre = 0;
		taille = 1;
	}
	
	/**
	 * Constructeur d'un tournoi binomial vide.
	 */
	public TournoiBinomial() {
		fils = new LinkedList<>();
		// Initialement le tournoi est vide, donc de degre 0.
		degre = 0; 
		taille = 0;
	}
	
	@Override
	public IFileBinomiale decapite() {
		IFileBinomiale result = new FileBinomiale(fils);
		return result;
	}

	@Override
	public IFileBinomiale file() {
		FileBinomiale result = new FileBinomiale(this);
		return result;
	}

	@Override
	public ITournoiBinomial union(ITournoiBinomial t2) {
		TournoiBinomial copieT2 = (TournoiBinomial)t2; 
		TournoiBinomial copieCourant = this; 
		
		//Si la clé racine du tournoi courant, est inférieur à celle du tournoi t2:
		if(copieCourant.cle.inf(copieT2.cle)) { 
			// On ajoute le tournoi t2 au fils du tournoi courant.
			copieCourant.fils.add(copieT2); 
			//L'union de 2 tournoi de même taille, crée un tournoi de taille, taille+1.
			copieCourant.degre++;
			return copieCourant; 
		}
		//Sinon c'est la clé en racine du tournoi t2 qui est inférieur alors :
		else { 
			// On ajoute le tournoi courant au fils du tournoi t2.
			copieT2.fils.add(copieCourant); 
			copieT2.degre++;
			return copieT2;
		}
	}
	
	/**
	 * Affiche un tournoi binomial : (racine , puis fils en partant vers la gauche), pour chaque noeud.
	 */
	public void print() {
		System.out.println(toString());
	}
	

	@Override
	public String toString() {
		return toString("", "R");
	}
	
	/**
	 * Retourne la chaîne de caractère représentant le tournoi actuel ainsi que ses fils.
	 * @param tabLvl Le niveau de tabulation.
	 * @param role Le role du tas.
	 * @return La chaîne de caractère représentant le tournoi actuel ainsi que ses fils.
	 */
	public String toString(String tabLvl, String role) {
		String tmp = "";
		int nbNoeud = fils.size();
		
		/* Affichage de la première moitié des fils */
		int i = 0;
		if(nbNoeud > 0) {
			for (i = 0; i < nbNoeud/2; i++)
				tmp += ((TournoiBinomial)fils.get(i)).toString(tabLvl+"\t", "F"+(i+1));
		}
		
		if (cle != null) {
			tmp += tabLvl+role+" "+cle.toString()+"\n";
		}
		
		/* Affichage de la seconde moitié des fils */
		if(nbNoeud > 0) {
			for (int j=i; j < nbNoeud; j++)
				tmp+= ((TournoiBinomial)fils.get(j)).toString(tabLvl+"\t", "F"+(j+1));
		}
		
		return tmp;
	}
	/**
	 * Renvoie le nombre d'elements ajoute au tournoi.
	 * @return le nombre d'elements ajoute au tournoi.
	 */
	public int getTaille() {
		return fils.size()+1;
	}

	@Override
	public boolean estVide() {		
		return cle == null;
	}

	@Override
	public int degre() {
		return degre;
	}
	
	@Override
	public ICle getCle() {
		return cle;
	}

	@Override
	public int getNbFils() {
		return fils.size();
	}

	@Override
	public ITournoiBinomial getFils(int index) {
		return fils.get(index);
	}
}
