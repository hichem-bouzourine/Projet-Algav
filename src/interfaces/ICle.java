package interfaces;

/**
 * Interface d'une clé pour nos structures.
 */
public interface ICle {
	
	/**
	 * Permet de déterminer si la clé est strictement inférieure à clé.
	 * @param cle1 clé dont on veux vérifier l'inférieurité.
	 */
	public boolean inf(ICle cle1);
	
	/**
	 * Vérifie si l'egalité entre les cle1
	 * @param cle1 clé avec laquelle on veux vérifier l'égalité.
	 */
	public boolean eg(ICle cle1);
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object o);	
}
