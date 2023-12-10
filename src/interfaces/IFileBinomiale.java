package interfaces;

import java.util.List;

public interface IFileBinomiale {
	
	/**
	 * @param tb Le tournoi binomial à ajouter.
	 * @return La nouvelle file contenant le tournoi.
	 */
	public IFileBinomiale ajout(ITournoiBinomial tb);
	/**
	 * @param l liste de clé pour la contruction de la file.
	 */
	public IFileBinomiale construction(List<ICle> elems);
	
	/**
	 * @return le tournoi de degre minimal dans la file.
	 */
	public ITournoiBinomial minDeg();
	
	/**
	 * @return true, si la file est vide, false sinon.
	 */
	public boolean estVide();
	
	/**
	 * @return la file privee de son tournoi de degre minimal
	 */
	public IFileBinomiale reste();
	
	/**
	 * @param t tournoi binomial a ajouter.
	 * @return la nouvelle file avec pour nouveau tournoi de degre min, le tournoi qui a ete ajoute.
	 */
	public IFileBinomiale ajoutMin(ITournoiBinomial t, IFileBinomiale f);
	
	/**
	 * @param f la file avec laquelle ont faire l'union.
	 * @return une nouvelle file union des deux autres (la courante et 'f').
	 */
	public IFileBinomiale unionFile(IFileBinomiale f1, IFileBinomiale f2);
	
	/**
	 * @param f la file avec laquelle ont faire l'union.
	 * @param retenue un tournoi en retenu
	 * @return une nouvelle file union des deux autres (la courante et 'f').
	 */
	public IFileBinomiale UFret(IFileBinomiale f1, IFileBinomiale f2, ITournoiBinomial retenue);
	
	/**
	 * @return la plus petit cle, presente dans la file.
	 */
	public ICle rechercheMin();
	
	/**
	 * @return une file privée de sa clé minimale.
	 */
	public IFileBinomiale supprCleMin();

	/**
	 * @return la taille de la file binomiale.
	 */
	public int getTaille();
	
	/**
	 * @return Le nombre de tas présent dans la file.
	 */
	public int getNbTas();
	
	/**
	 * @param index L'indice du tas dans la file.
	 * @return Le tournoi binomial présent à l'indice donnée en paramètre.
	 */
	public ITournoiBinomial getTas(int index);
}
