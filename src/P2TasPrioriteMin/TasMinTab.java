package P2TasPrioriteMin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import interfaces.ICle;
import interfaces.ITasMin;


public class TasMinTab implements ITasMin {

	/** Le tas. */
	private ICle [] tas;
	
	/** Le nombre de clé dans le tas. */
	private int nbElements;
	
	/**
	 * Constructeur d'un tas min.
	 * @param capacite La capacité initiale du tas.
	 */
	public TasMinTab(int capacite) {
		tas = new ICle[capacite];
		nbElements=0;	
	}
	
	/**
	 * Inverse la position de 2 elements i et j.
	 * @param i
	 * @param j
	 */
	public void swap(int i, int j) {
	        ICle tmp = tas[j];
	        tas[j] = tas[i];
	        tas[i] = tmp;
	}
	
	@Override
	public ICle supprMin() {
		
		if(nbElements == 0) { // Cas ou le tas est vide.
			System.out.println("Tas vide, suppression impossible.");
			return null;

		}else if(nbElements == 1) { // Cas où le tas possède 1 element à supprimer.			
			ICle min = tas[0];
			tas[0] = null;
			nbElements--;
			
			return min;
			
		}
		// Cas où le tas possède plus de 1 element.
		ICle min = tas[0];
		
		ICle c = tas[nbElements - 1];
    	
    	tas[0] = c;
    	
    	tas[nbElements-1] = null;
    	
		nbElements--; // nombre d'element diminue de 1

		// On vérifie que le tas est toujours minimal
		verifMin(0);
		
		return min;
	}
	
	
	/**
	 * Vérifie que le tas est toujours minimal, et le rend minimal si cela est nécessaire.
	 * @param noeud
	 */
    public void verifMin(int noeud) {
	
        int fGauche = idFilsGauche(noeud);
        int fDroit = idFilsDroit(noeud);
        
        int idMin = noeud;
        int nbElems = nbElements-1;
        
        // Il faut prendre l'id du fils le plus petit si un des fils est plus petit que le père
        if (fDroit <= nbElems && fDroit > 0 && tas[fDroit].inf(tas[idMin])) { // Vérifie si le fils droit n'est pas plus grand que le pere.
            idMin = fDroit;
        } if (fGauche <= nbElems && fGauche > 0 && tas[fGauche].inf(tas[idMin])) { // Vérifie si le fils gauche n'est pas le noeud le plus petit.
            idMin = fGauche;
        }

        if (idMin != noeud) {
            swap(noeud, idMin);
            verifMin(idMin); // On continue la descente
        }
    }
	
	/**
	 * Renvoie l'indice du fils gauche du noeud d'indice "noeud", -1 si ce noeud ne possède pas de fils gauche.
	 * @param noeud
	 * @return indice du fils gauche de "noeud".
	 */
	public int idFilsGauche(int noeud) {
		
		if((2*noeud + 1) >= nbElements)
			return -1;
		else {
			return (2*noeud + 1);
		}
	
	}
	
	/**
	 * Renvoie l'indice du fils droit du noeud d'indice "noeud", -1 si ce noeud ne possède pas de fils droit.
	 * @param noeud
	 * @return indice du fils droit de "noeud".
	 */
	public int idFilsDroit(int noeud) {
		
		if((2*noeud + 2) >= nbElements)
			return -1;
		else {
			return (2*noeud + 2);
		}
	
	}

	/**
	 * Renvoie la capacité du tas.
	 * @return La capacité du tas.
	 */
	public int capacite() {
		return tas.length;
	}
	

	@Override
	public int size() {
		return nbElements;
	}
	
	@Override
	public void ajout(ICle c) {
		
		if(nbElements == 0) {
			tas[0] = c;
			nbElements++;
			return ;
		}else if (capacite() == nbElements) { 
			// Si la capacité est atteinte, on agrandie le tableau.
			ICle [] tasTmp = new ICle[capacite() * 2]; 
			
			for(int i=0; i < nbElements; i++)
				tasTmp[i] = tas[i];
				
			tas = tasTmp;	
		}
		
		tas[nbElements] = c; // Clé ajoute en fin de tas (en feuille)
		
		nbElements++;
		
		// Remonter la clé
		
		int lastId = nbElements - 1;
		int pere = idPere(lastId); // indice du père de la dernière clé ajoutée
		
		while(pere != lastId && tas[lastId].inf(tas[pere])) {
			swap(lastId, pere);
			lastId = pere;
			pere = idPere(lastId);
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
	 * Renvoie l'indice dans le tas du noeud père, d'un noeud. 
	 * @param noeud dont on cherche le père.
	 * @return id du noeud père.
	 */
	public int idPere(int noeud) {
        if (noeud % 2 == 1) {
            return noeud / 2;
        }

        return (noeud - 1) / 2;
	}
	
	@Override
	public boolean construction(List<ICle> elems) {
		if (elems.size() == 0)
			return false;
		else {
			List<ICle> listeTriee = ConstruireTasMin.convertirListeCleTriee(elems);
			tas = (ICle[]) listeTriee.toArray(new ICle[0]);
			
			nbElements = listeTriee.size();
			return true;
		}
	}

	
	

	@Override
	public void union(ITasMin t2) {
		// faire l'union des deux tas, pour celà on récupère la valeur hashés pour éviter les doublons 
		ICle[] tabTas1 = this.getRepresentationTableau();
		ICle[] tabTas2 = t2.getRepresentationTableau();
		
		Set<ICle> unionSet = new HashSet<ICle>();
		// parcourir les deux tableaux 
		
		for(int i=0; i<tabTas1.length; i++)
			unionSet.add(tabTas1[i]);
		
		for(int i=0; i<tabTas2.length; i++)
			unionSet.add(tabTas2[i]);
		
		// On crée une liste contenant tous les élément du tas : complexité linéaire
		List<ICle> unionSansDoublon = new ArrayList<ICle>(unionSet);
		
		// On appelle construction pour construire le tas contenant l'union des deux tas
		construction(unionSansDoublon);
	}
	
	/**
	 * Renvoie le tableau.
	 * @return tas
	 */
	public ICle[] getTas(){
		return tas;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		
		for(int i = 0; i<nbElements; i++) {
			str.append(tas[i]);
			
			if(i!=nbElements-1)
				str.append(", ");
		}
		str.append("]");
		return str.toString();
	}

	@Override
	public ICle[] getRepresentationTableau() {
		 return Arrays.copyOfRange(tas, 0, nbElements);		// On ne renvoie pas la totalité du tableau afin d'éviter d'avoir à gérer les valeurs null
	}
}
