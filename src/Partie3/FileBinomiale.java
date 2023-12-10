package Partie3;

import java.util.LinkedList;
import java.util.List;


import interfaces.ICle;
import interfaces.IFileBinomiale;
import interfaces.ITournoiBinomial;

/**
 * Classe de la file binomiale.
 */
public class FileBinomiale implements IFileBinomiale{
	
	/** Degre de la file binomiale. e.g : FB5 =>  taille = 5  */
	private int taille;
	/** Nombre de tournois binomiaux actuellement dans la file binomiale. e.g : FB5  = <TB4,TB1>=> nbElmt =2  */
	private int nbElmt; 
	/** Ensemble des tournois binomiaux composant la file binomiale. */
	private LinkedList<ITournoiBinomial> tas;
	/** On stockera à chaque ajout, l'éventuel nouveau tournoi binomiale de plus petite clé, de la file binomiale. */
	private ITournoiBinomial tournoiMin;
	
	/**
	 * Constructeur d'une file binomiale avec une taille fixée.
	 * @param n la taille de la file.
	 */
	public FileBinomiale(int n) {
		//bitCount : compte le nombre de bits à 1. 
		nbElmt = Integer.bitCount(n);  
		taille = n; 
		tas = new LinkedList<ITournoiBinomial>();
	}

	public int getNombreElement(){
		return this.nbElmt; 
	}
	
	/**
	 * Constructeur d'une file binomiale, en lui fournissant une forêt (liste) de tournois.
	 * @param l la liste de tournois composant la file.
	 */
	public FileBinomiale(LinkedList<ITournoiBinomial> l) {
		nbElmt = l.size(); //Le nombre d'éléments de la file binomiale, c'est le nombre d'éléments de la liste de tournois binomiale.
		tas = l; 
	}
	
	/**
	 * Constructeur qui cree une file binomiale avec 1 seul élément.
	 * @param init seul tournoi binomial de la file.
	 */
	public FileBinomiale(ITournoiBinomial init) {
		nbElmt = 1;
		tas = new LinkedList<ITournoiBinomial>();
		tournoiMin = init; //La plus petit tournoi de la file, est l'unique tournoi la composant.
		tas.addFirst(init);
		// La taille de la file est egal a 2^(degre de l'unique tournoi le composant) : e.g : <TB4> => 2^4
		taille = (int) Math.pow(2,init.degre());  
	}
	
	@Override
	public IFileBinomiale ajout(ITournoiBinomial t){
		return unionFile(this, t.file());
	}

	@Override
	public TournoiBinomial minDeg() {
		return (TournoiBinomial)tas.getFirst(); // On cast car getFirst renvoie un ITournoiBinomial.
	}
	@Override
	public IFileBinomiale reste() {
		taille -= Math.pow(2, tas.pollFirst().degre()); // On enlève un tournoi, donc on réduit le nombre de nœuds de la file. 
		return this;
	}

	@Override
	public boolean estVide() {
		return tas.isEmpty();
	}

	

	@Override
	public IFileBinomiale ajoutMin(ITournoiBinomial t, IFileBinomiale f) {
		FileBinomiale tmp = (FileBinomiale)f;
		
		//On maintient un pointeur sur le tournoi avec la plus petite racine.
		if(tmp.tournoiMin == null || t.getCle().inf(tmp.tournoiMin.getCle()))
			tmp.tournoiMin = t;
		
		tmp.tas.addFirst(t); // Le tournoi étant le futur tournoi de plus petit degré de la file, on l'ajoute en premier de la file.
		tmp.taille += Math.pow(2,t.degre()); // On ajoute un tournoi à la file, donc on augmente le nombre de nœuds total que contient la file..
		
		return tmp;
	}
	
	/**
	 * Ajoute un tournoi binomiale, en fin de la liste de tournois de la file. 
	 * @param t le tournoi à ajouter à la file.
	 */
	public void ajoutMax(ITournoiBinomial t) {
		tas.addLast(t);
		taille += Math.pow(2,t.degre()); // On ajoute un tournoi à la file, donc on augmente le nombre de nœuds total que contient la file..
	}
	
	@Override
	public IFileBinomiale unionFile(IFileBinomiale f1, IFileBinomiale f2) {
		return UFret(f1, f2, new TournoiBinomial());
	}

	@Override
	public  IFileBinomiale UFret(IFileBinomiale f1, IFileBinomiale f2, ITournoiBinomial retenue) {
		
		if(retenue.estVide()) {
			if(f1.estVide()) 
				return f2;
			if(f2.estVide())
				return f1;
			TournoiBinomial t1 = (TournoiBinomial)f1.minDeg();
			TournoiBinomial t2 = (TournoiBinomial)f2.minDeg();
			if(t1.degre() < t2.degre())
				return ajoutMin(t1, unionFile(f1.reste(), f2));
			if(t2.degre() < t1.degre())
				return ajoutMin(t2, unionFile(f2.reste(), f1));
			if(t1.degre() == t2.degre())
				return UFret(f1.reste(), f2.reste(), t1.union(t2));				
		}
		// retenue 
		else { 
			if(f1.estVide()) 
				return unionFile(retenue.file(), f2);
			if(f2.estVide()) 
				return unionFile(retenue.file(), f1);
			ITournoiBinomial t1 = f1.minDeg();
			ITournoiBinomial t2 = f2.minDeg();
			if (retenue.degre() < t1.degre() && retenue.degre() < t2.degre())
				return ajoutMin(retenue, unionFile(f1 ,f2));
			if (retenue.degre() == t1.degre() && retenue.degre() == t2.degre())
				return ajoutMin(retenue, UFret(f1.reste(),f2.reste(),t1.union(t2)));
			if (retenue.degre() == t1.degre() && retenue.degre() < t2.degre())
				return UFret (f1.reste(), f2 , t1.union(retenue));
			if (retenue.degre() == t2.degre() && retenue.degre() < t1.degre())
				return UFret (f2.reste(), f1 , t2.union(retenue));
		}
		return null;
	}
	public IFileBinomiale construction(List<ICle> elems){
		//! Construire la file.
		IFileBinomiale file= new FileBinomiale(elems.size()); 
		for(ICle cle : elems)
			file = file.ajout(new TournoiBinomial(cle));
		return file; 
	}
	

	
	@Override
	public ICle rechercheMin() {
		return tournoiMin.getCle();
	}

	@Override
	public IFileBinomiale supprCleMin() {
		// enlever le tounoir minimale 
		tas.remove(tournoiMin);
		return unionFile(this, tournoiMin.decapite());
	}
	
	@Override
	public int getTaille() {
		return taille;
	}
	
	/**
	 * Renvoie le tournoi avec la cle la plus petite de la file.
	 * @return le tournoi avec la cle la plus petite de la file.
	 */
	public ITournoiBinomial getTournoiMin() {
		return tournoiMin;
	}

	/**
	 * Permet de pointer sur le tournoi de clé minimal de la file.
	 */
	public void getTournoiCleMin() {
		for(ITournoiBinomial t : tas)
			if( ((TournoiBinomial) t).getCle().inf(tournoiMin.getCle()))
				tournoiMin = t;
	}
	
	/**
	 * Affiche les tournois de la file binomiale. 
	 */
	public void print() {
		System.out.println("File binomiale : FB"+taille+" : ");
		for(ITournoiBinomial b : tas) {
			System.out.print("TB"+b.degre()+" : ");	
			((TournoiBinomial) b).print();
			System.out.println();
		}
	}

	@Override
	public int getNbTas() {
		return tas.size();
	}

	@Override
	public ITournoiBinomial getTas(int index) {
		return tas.get(index);
	}
	
	@Override
	public String toString() {
		String res = "FB" + this.getTaille() + " : " + Integer.toBinaryString(this.getTaille()) + "\n\n";
		for (int i=0; i<tas.size(); i++)
			res += "TB" + tas.get(i).degre() + "\n" + tas.get(i).toString() + "\n";
		
		return res;
	}
}
