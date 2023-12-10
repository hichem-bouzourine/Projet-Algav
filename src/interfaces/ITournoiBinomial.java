package interfaces;

/**
 * Interface d'un tournoi binomial.
 */
public interface ITournoiBinomial {

	/**
	 * Indique si le tournoi ne possède aucune clés.
	 * @return true si le tournoi n'a aucun noeuds stockant une clé, false sinon.
	 */
	public boolean estVide();
	
	/**
	 * Fait l'union de 2 tournoi binomial de même taille.
	 * @param t2 tas binomial
	 * @return un tas binomial de taille = taille+1, issue de l'union du tas courant et d'un tas tb2.
	 */
	public ITournoiBinomial union(ITournoiBinomial t2);
	
	/**
	 * Renvoie le degré (la taille) du tournoi binomial.
	 * @return le degré du tournoi binomial.
	 */
	public int degre();
	
	/**
	 * Renvoie la file binomiale obtenue en supprimant la racine du tournoi binomial.
	 * @return une file binomiale composée des sous arbres du tournoi binomial initial.
	 */
	public IFileBinomiale decapite();
	
	/**
	 * Renvoie la file binomiale réduite au tournoi binomial courant.
	 * @return une file binomiale composée d'un seul tournoi binomial.
	 */
	public IFileBinomiale file();
	
	/**
	 * Renvoie la clé du tournoi
	 * @return la clé du tournoi
	 */
	public ICle getCle();
	
	/**
	 * Renvoie le nombre de fils du tournoi.
	 * @return Le nombre de fils du tournoi.
	 */
	public int getNbFils();
	
	/**
	 * Renvoie le fils présent à l'indice donnée en paramètre.
	 * @param index L'indice du fils dans le tournoi.
	 * @return Le fils présent à l'indice donné en paramètre.
	 */
	public ITournoiBinomial getFils(int index);
}
