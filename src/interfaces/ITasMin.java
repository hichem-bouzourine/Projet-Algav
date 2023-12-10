package interfaces;

import java.util.List;

/**
 * Interface d'un tas min.
 */
public interface ITasMin {
	
	/**
	 * Renvoie le nombre de clé dans le tas.
	 * @return Le nombre de clé dans le tas au moment de l'appel.
	 */
	public int size();
	
	/**
	 * Supprime la clé minimal 
	 * @return La clé de valeur minimal dans le tas.
	 */
	public ICle supprMin();
	/**
	 * Ajoute une clé au tas minimal 
	 * @param c clé a ajouter
	 */
	public void ajout(ICle c);
	
	/**
	 * Construit un tas minimal en complexité Linéaire : O(n), a partir d'une liste de clés.
	 * @param elems
	 * @return true si le tas à été construit, false sinon.
	 */
	public boolean construction(List<ICle> elems);
	/**
	 * Ajout itérativement à un tas minimal en complexité  : O(n *log n), a partir d'une liste de clés.
	 * @param elems
	 * @return true si le tas à été construit, false sinon.
	 */
	public boolean ajoutsIteratifs(List<ICle> elems);
	
	/**
	 * Fusionne 2 tas minimaux, pour former un seul tas minimal. 
	 * @param t2 le second tas avec lequel faire la fusion.
	 */
	public void union(ITasMin t2);
	
	/**
	 * Renvoie la représentation du tas min sous la forme d'un tableau.
	 * @return La représentation du tas min sous la forme d'un tableau.
	 */
	public ICle[] getRepresentationTableau();
	
}
