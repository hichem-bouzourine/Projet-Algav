package P2TasPrioriteMin;

import java.util.List;

import interfaces.ICle;

/**
 * Classe s'occupant de la conversion d'un tableau d'éléments non triés en un tableau représentant un tas min. 
 */
public abstract class ConstruireTasMin {
	
	/** Les éléments du tas */
	private static List<ICle> elems;
	
	/**
	 * Point d'entrée pour la conversion d'une liste non triée en une liste représentant un tas min.
	 * @param listeElement La liste d'élément non triée.
	 * @return La liste représentant le tas min.
	 */
	public static List<ICle> convertirListeCleTriee(List<ICle> listeElement) {
		elems = listeElement;
		 
		// On part des parents les plus profonds jusqu'à la racine
		// Pour chaque appel sur un noeud à une profondeur i, on aura au pire cas n/(2**(i+1)) appel récursif
		for (int i = elems.size() / 2; i >= 0; i--)
            trierTasLocal(i);
		
		return elems;
	}
	
	/**
	 * Trie le tas local au noeud présent à l'indice donné en paramètre.
	 * @param i L'indice du noeud.
	 */
	private static void trierTasLocal(int i) {
		
        int gauche = filsGauche(i);
        int droit = filsDroit(i);
        int petit;				// L'indice de la clé la plus petite entre l'actuelle et ses deux fils

        // Calcule quelle est la clé la plus petite entre l'actuelle et ses fils
        // Le fils gauche existe et est plus petit que la clé actuelle
        if (gauche <= elems.size() - 1 && elems.get(gauche).inf(elems.get(i)))
            petit = gauche;
        
        // Condition non vérifié, la clé la plus petite est l'actuelle
        else
            petit = i;

        
        // Le fils droit existe, et est plus petit que la valeur trouvée au dessus
        if (droit <= elems.size() - 1 && elems.get(droit).inf(elems.get(petit)))
            petit = droit;

        // La clé la plus petite n'est pas la clé actuelle (le parent), il faut changer sa place dans le tableau
        // Il faut aussi appeler cette opération sur l'indice de la clé la plus petite
        if (petit != i) {
            swap(i, petit);
            trierTasLocal(petit);
        }
    }
	
	/**
	 * Calcule le fils gauche du noeud.
	 * @param i L'indice du noeud.
	 * @return L'indice du fils gauche.
	 */
    private static int filsGauche(int i) {
        return 2 * i + 1;
    }
	
	/**
	 * Calcule le fils droit du noeud.
	 * @param i L'indice du noeud.
	 * @return L'indice du fils droit.
	 */
	private static int filsDroit(int i) {
        return 2 * i + 2;
    }
	
    /**
     * Échange deux éléments dans le tableau.
     * @param i L'indice du premier élément.
     * @param j L'indice du second élément.
     */
	private static void swap(int i, int j) {
        ICle temp = elems.get(j);
        elems.set(j, elems.get(i));
        elems.set(i, temp);
    }
}
