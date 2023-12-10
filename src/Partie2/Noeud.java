package Partie2;

import interfaces.ICle;

/**
 * Classe d'un noeud pour le tas min utilisant un arbre.
 */
public class Noeud {

	private ICle noeud;
	private Noeud pere;
	private Noeud filsGauche;
	private Noeud filsDroit;
	private boolean isLeftChild;
	private boolean isExtremeRight; 
	
	/**
	 * Constructeur d'un noeud avec une clé.
	 */
	public Noeud(ICle n, Noeud pere, boolean isLeftChild, boolean isExtremeRight) {
		noeud = n;
		this.pere = pere;
		filsGauche = new Noeud(this, true, false);
		filsDroit = new Noeud(this, false, true);
		
		this.isLeftChild = isLeftChild;
		this.isExtremeRight = isExtremeRight;
	}
	
	/**
	 * Constructeur sans clé d'un noeud.
	 */
	public Noeud(Noeud pere, boolean isLeftChild, boolean isExtremeRight) {
		this.pere = pere;
		noeud = null;
		filsGauche = null;
		filsDroit = null;
		
		this.isLeftChild = isLeftChild;
		this.isExtremeRight = isExtremeRight;
	}
	
	public ICle getNoeud() {
		return noeud;
	}
	

	public Noeud getFilsGauche() {
		return filsGauche;
	}
	
	
	public Noeud getFilsDroit() {
		return filsDroit;
	}
	
	
	public boolean isLeftChild() {
		return isLeftChild;
	}

	public boolean estFilsDroit() {
		return isLeftChild;
	}
	

	public boolean isExtremeRight() {
		return isExtremeRight;
	}
	
	
	public Noeud getPere() {
		return pere;
	}
	
	/**
	 * Setteur sur l'attribut indiquant si le noeud courant est l'extrémité droit de l'arbre.
	 * @param isExtremeRight Un booléen indiquant si le noeud courant est l'extrémité droit de l'arbre.
	 */
	public void setisExtremeRight(boolean isExtremeRight) {
		this.isExtremeRight = isExtremeRight;
	}
	
	
	public void setNoeud(ICle cle) {
		noeud = cle;
	}
	
	public void setFilsGauche(Noeud filsGauche) {
		this.filsGauche = filsGauche;
	}
	

	public void setFilsDroit(Noeud filsDroit) {
		this.filsDroit = filsDroit;
	}
	
	/**
	 * 
	 * @param c Clé 
	 * Modife le noeud en rajoutant deux fils, un gauche à null et un fils à droite 
	 */
	public void add(ICle c) {
		noeud = c;
		filsGauche = new Noeud(this, true, false);
		
		// Le fils droit de l'extrémité est la nouvelle extrémité
		// Si le noeud courant n'est pas l'ectrémité, alors son fils droit ne le sera pas non plus
		filsDroit = new Noeud(this, false, isExtremeRight);
	}
	
	/**
	 * Affichage infixe de l'arbre
	 * @param tabLvl le niveau de tabulation courant.
	 * @return La string d'affichage de l'arbre.
	 */
	public String infixeToString(String tabLvl) { 
		String tmp = "";
		if (noeud != null) {
			if (filsGauche.getNoeud() != null)
				tmp += filsGauche.infixeToString(tabLvl+"\t");
			
			if (pere == null)
				tmp += tabLvl+"R : ";
			else if (isLeftChild)
				tmp += tabLvl+"FG : ";
			else
				tmp += tabLvl+"FD : ";
			tmp += " Clé : " + noeud.toString() + " \n";
				
			if (filsDroit.getNoeud() != null)
				tmp += filsDroit.infixeToString(tabLvl+"\t");
		}
		return tmp;
	}
}
