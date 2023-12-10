package P5ArbreDeRecherche;

import interfaces.ICle;

public class Noeud<C extends ICle> {
    private C cle;
    private int hauteur;
    
    private Noeud<C> filsGauche;
    private Noeud<C> filsDroit; 
  
  
    public Noeud(C cle) { 
        this.cle = cle; 
        hauteur = 0;
        filsGauche = null;
        filsDroit = null;
    }

	public C getCle() {
		return cle;
	}


	public void setCle(C cle) {
		this.cle = cle;
	}

	
	public int getHauteur() {
		return hauteur;
	}

	
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	
	public Noeud<C> getFilsGauche() {
		return filsGauche;
	}

	
	public void setFilsGauche(Noeud<C> filsGauche) {
		this.filsGauche = filsGauche;
	}

	
	public Noeud<C> getFilsDroit() {
		return filsDroit;
	}


	public void setFilsDroit(Noeud<C> filsDroit) {
		this.filsDroit = filsDroit;
	}

	public String infixeToString(String tabLvl) { 
		String tmp = "";
		if (getFilsGauche() != null)
			tmp += getFilsGauche().infixeToString(tabLvl + "\t");
		
		tmp += tabLvl + "Clé : " + getCle().toString() + "\n";
		
		if (getFilsDroit() != null)
			tmp += getFilsDroit().infixeToString(tabLvl + "\t");
		
		return tmp;
	}
	

	public String prefixeToString() {
		return prefixeToString("");
	}
	
	/**
	 * @param niveauTabulation Le niveau de tabulation actuel.
	 * @return La chaîne de caractère représentant le parcours infixe depuis le noeud courant.
	 */
	private String prefixeToString(String niveauTabulation) { 
		String tmp = niveauTabulation;
		
		tmp += "\t Cle : " + getCle().toString() +",\n";
		
		if (getFilsGauche() != null)
			tmp += "G -> " + getFilsGauche().prefixeToString(niveauTabulation + "\t");
		
		if (getFilsDroit() != null)
			tmp += "D -> " + getFilsDroit().prefixeToString(niveauTabulation + "\t");
		
		return tmp;
	}
}
