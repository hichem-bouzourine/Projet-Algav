package autres;

import interfaces.ICle;

/**
 * Clé contenant un entier.
 * Classe utilisé pour avoir un meilleur visuel sur nos structures.
 */
public class CleInteger implements ICle{

	/** La valeur de la clé. */
	private Integer valeur;
	
	/** Construit une clé avec un entier. */
	public CleInteger(Integer valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * Getteur de la valeur de la clé.
	 * @return La valeur de la clé.
	 */
	public Integer getValeur() {
		return valeur;
	}

	@Override
	public boolean inf(ICle cle1) throws RuntimeException{
		if (cle1 instanceof CleInteger)
			return valeur < ((CleInteger) cle1).getValeur();
		else
			throw new RuntimeException("Impossible de comparer une \"CleInteger\" avec une instance de " + cle1.getClass());
	}

	@Override
	public boolean eg(ICle cle1) {
		if (cle1 instanceof CleInteger)
			return valeur.equals(((CleInteger) cle1).getValeur());
		else
			throw new RuntimeException("Impossible de comparer une \"CleInteger\" avec une autre classe");
	}
	
	@Override
	public String toString() {
		return ""+valeur;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CleInteger) {
			CleInteger c = (CleInteger) o;
			return this.eg(c);
		}
		else
			return false;
			
	}
	
	@Override
	public int hashCode() {
		return valeur.hashCode();
	}
}
