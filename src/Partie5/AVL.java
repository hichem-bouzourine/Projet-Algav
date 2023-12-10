package Partie5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;

import Partie1.Cle128Bit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import interfaces.IAVL;
import interfaces.ICle;

/**
 * Classe AVL représentant un arbre de recherche équilibré de type AVL (Adelson–Velsky, Landis).
 * @param <C> @param <C> Le type des clés contenues dans l'arbre
 */
public class AVL<C extends ICle> implements IAVL<C> {
	
	/** La racine de l'arbre. */
	private Noeud<C> racine;
	
	/** La taille de l'arbre */
	private int taille;
	
	/** Le nombre de comparaison pour la dernière opération de recherche. */
	private int nbComparaison;

	@Override
	public Noeud<C> getRacine() {
		return racine;
	}
		
	@Override
	public void setRacine(Noeud<C> racine) {
		this.racine = racine;
	}

	@Override
	public int getHauteur() {
		return getHauteur(getRacine());
	} 
	
	/**
	 * Retourne la hauteur d'un noeud.
	 * @param n Le noeud
	 * @return La hauteur du noeud, -1 si le noeud n'existe pas.
	 */
	private int getHauteur(Noeud<C> n) {
		if (n != null)
			return n.getHauteur();
		else
			return -1;
	}
	
	@Override
	public int size() {
		return taille;
	}
		
	@Override
	public String infixeToString() {
		return infixeToString(getRacine());
	}
		
	/**
	 * Affichage du parcours infixe depuis une racine.
	 * @param racine La racine de l'arbre.
	 * @return La chaîne de caractère correspondant à la suite des clés depuis la racine
	 * selon un parcours infixe.
	 */
	private String infixeToString(Noeud<C> racine) { 
		if (racine != null)
			return racine.infixeToString("");
		return "";
	}
	
	@Override
	public String prefixeToString() {
		return prefixeToString(getRacine());
	}
		
	/**
	 * Affichage du parcours préfixe depuis une racine.
	 * @param racine La racine de l'arbre.
	 * @return La chaîne de caractère correspondant à la suite des clés depuis la racine
	 * selon un parcours prefixe.
	 */
	private String prefixeToString(Noeud<C> racine) { 
		if (racine != null)
			return racine.prefixeToString();
		return "";
	}

	/**
	 * Calcule et renvoie le maximum parmi deux entiers. 
	 * @param a Le premier entier à comparer.
	 * @param b Le second entier à comparer.
	 * @return Le maximum des deux entiers.
	 */
	private int max(int a, int b) { 
		return (a > b) ? a : b; 
	}
		
	/**
	 * Calcule le niveau d'equilibrage d'un noeud. 
	 * @param racine La racine pour laquelle on souhaite calculer son niveau d'equilibrage.
	 * @return Le niveau d'equilibrage d'un noeud.
	 */
	private int getEquilibrage(Noeud<C> racine) { 
		if (racine == null) 
			return 0; 

		return getHauteur(racine.getFilsGauche()) - getHauteur(racine.getFilsDroit()); 
	} 
	
	@Override
	public void inserer(C cle) throws InsertionException{
		try {
			setRacine(inserer(getRacine(), cle));	
		}
		catch (InsertionException IE) {
			throw IE;
		}
	}
		
	/**
	 * Insère une clé dans la racine
	 * @param racine La racine dans laquelle on souhaite effectuer l'insertion.
	 * @param cle La clé à insérer.
	 * @return La racine avec la clé insérée.
	 */
	private Noeud<C> inserer(Noeud<C> racine, C cle) throws InsertionException {
		if (racine == null) {
			taille++;
			return new Noeud<C>(cle);
		}
		else if (cle.eg(racine.getCle()))
			throw new InsertionException();
		else if (cle.inf(racine.getCle()))
        	racine.setFilsGauche(inserer(racine.getFilsGauche(), cle));
        else						
        	racine.setFilsDroit(inserer(racine.getFilsDroit(), cle));
        
        racine.setHauteur( 1 + max(getHauteur(racine.getFilsGauche()), getHauteur(racine.getFilsDroit())));
        return equilibrage(racine);
	}
		
	/**
	 * Effectue un réequilibrage depuis un noeud racine.
	 * @param racine Le noeud depuis lequel on souhaite rééquilibrer.
	 * @return La nouvelle racine après rééquilibrage.
	 */
	private Noeud<C> equilibrage(Noeud<C> racine) {
		if (getEquilibrage(racine) < -1) {
			// Rotation droite gauche
            if (getEquilibrage(racine.getFilsDroit()) > 0)
                racine.setFilsDroit(rotationDroite(racine.getFilsDroit()));
            
            racine = rotationGauche(racine);
        }
        else if (getEquilibrage(racine) > 1) {			
        	// Rotation gauche droite
            if (getEquilibrage(racine.getFilsGauche()) < 0)
                racine.setFilsGauche(rotationGauche(racine.getFilsGauche()));
            
            racine = rotationDroite(racine);
        }
        return racine;
	}
	
	/**
	 * Effectue une rotation droite avec l'arbre dont la racine est le noeud passé en paramètre.
	 * @param q La racine à partir de laquelle il faut effectuer la rotation.
	 * @return La nouvelle racine après la rotation.
	 */
	private Noeud<C> rotationDroite(Noeud<C> q) { 
		Noeud<C> p = q.getFilsGauche();
		
		// La rotation !
		q.setFilsGauche(p.getFilsDroit());		// V
        p.setFilsDroit(q);
        
        // Il faut recalculer les hauteurs
        q.setHauteur(1 + max(getHauteur(q.getFilsGauche()), getHauteur(q.getFilsDroit())));
        p.setHauteur(1 + max(getHauteur(p.getFilsGauche()), getHauteur(p.getFilsDroit())));
        
        return p;
	} 

	/**
	 * Effectue une rotation gauche avec l'arbre dont la racine est le noeud passé en paramètre.
	 * @param p La racine à partir de laquelle il faut effectuer la rotation.
	 * @return La nouvelle racine après la rotation.
	 */ 
	private Noeud<C> rotationGauche(Noeud<C> p) { 
		Noeud<C> q = p.getFilsDroit();
		
		// La rotation
		p.setFilsDroit(q.getFilsGauche());		// V
		q.setFilsGauche(p);
		
		// Il faut recalculer les hauteurs
        p.setHauteur(1 + max(getHauteur(p.getFilsGauche()), getHauteur(p.getFilsDroit())));
        q.setHauteur(1 + max(getHauteur(q.getFilsGauche()), getHauteur(q.getFilsDroit())));
        
        return q;
	}
	
	@Override
	public SimpleEntry<Noeud<C>, Integer> rechercher(C cle) throws Exception {
		nbComparaison = 0;
		Noeud<C> res = rechercher(getRacine(), cle);
		return new SimpleEntry<Noeud<C>, Integer>(res, nbComparaison);
	}
		
	/**
	 * Recherche une clé dans la racine passée en argument.
	 * @param racine Le noeud racine dans lequel on souhaite effectuer la recherche.
	 * @param cle La clé à recherchée.
	 * @return Le noeud contenant la clé.
	 * @throws Exception La clé n'est pas présente ni dans la racine, ni dans ses fils.
	 */
	private Noeud<C> rechercher(Noeud<C> racine, C cle) throws Exception {
		if (racine != null) {
			if (cle.eg(racine.getCle()))
				return racine;
			else {
				nbComparaison++;
				if (cle.inf(racine.getCle()))
					return rechercher(racine.getFilsGauche(), cle);
				else
					return rechercher(racine.getFilsDroit(), cle);
			}
		}
		else
			throw new Exception("La clé " + cle + " n'est pas présente dans l'arbre");
	}

	@Override
	public List<C> getCleTriee() {
		List<C> l = new ArrayList<C>();
		l = getCleTriee(racine, l);
		return l;
	}
	
	/**
	 * Retourne les clés sous la forme d'une liste triée.
	 * @param racine La racine à laquelle ou se situe.
	 * @param l La liste actuelle.
	 * @return Une liste triée des clés de l'AVL.
	 */
	private List<C> getCleTriee(Noeud<C> racine, List<C> l) {
		if (racine != null) {
			getCleTriee(racine.getFilsGauche(), l);
			l.add(racine.getCle());
			getCleTriee(racine.getFilsDroit(), l);
			return l;
		}
		else
			return Collections.emptyList();
	}
	
	/**
	 * Génère un fichier CSV avec les résultats de la méthode de recherche dans un AVL.
	 */
	public static void genererResultatRecherche() {
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};;
		int nb = 5; int cpt = 0;
		File file;
		String nomFichier, mot;
		Cle128Bit cle;
		BufferedReader bufferedReader;
		AVL<Cle128Bit> avl;
		HashMap<Integer, ArrayList<Integer>> nbComparaisonParTaille = new HashMap<>();
		SimpleEntry<Noeud<Cle128Bit>, Integer> res;
		
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!nbComparaisonParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					nbComparaisonParTaille.put(tailles[j], new ArrayList<Integer>());
				
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				file = new File("donnees/cles_alea/"+nomFichier);
				avl = new AVL<>();
				try {
					bufferedReader = new BufferedReader(new FileReader(file));
					mot = bufferedReader.readLine();
					while (mot != null) {
						mot = mot.substring(2);
						cle = new Cle128Bit(mot);
						avl.inserer(cle);
						mot = bufferedReader.readLine();
					}
					bufferedReader.close();
					
					bufferedReader = new BufferedReader(new FileReader(file));
					mot = bufferedReader.readLine();
					while (mot != null) {
						mot = mot.substring(2);
						res = avl.rechercher(new Cle128Bit(mot));
						nbComparaisonParTaille.get(tailles[j]).add(res.getValue());
						mot = bufferedReader.readLine();
					}
					bufferedReader.close();
				} catch (Exception e) {
					System.err.println("Erreur avec le fichier : " + file.getName());
					e.printStackTrace();
				}
			}
		}// Toutes les recherches ont été effectuées
		String nomFichierCSV = "recherche_AVL.csv";
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (sauvegarderResultat(nomFichierCSV, nbComparaisonParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 * Sauvegarde le contenu de la HashMap dans un fichier.
	 * @param nomFichier Le nom du fichier.
	 * @param nbComparaisonParTaille La hashmap contenant les résultats à sauvegarder.
	 * @return Un booléen indiquant si la sauvegarde s'est bien déroulée.
	 */
	private static boolean sauvegarderResultat(String nomFichier, HashMap<Integer, ArrayList<Integer>> nbComparaisonParTaille) {
		String nomFichierCSV = "recherche_AVL.csv";
		ArrayList<Integer> liste;
		float moyenne;
		int max;
		ArrayList<Integer> listeTriee = new ArrayList<>(nbComparaisonParTaille.keySet());
		Collections.sort(listeTriee);

		// Écriture des résultats
		try {
			File fichierCSV = new File("resultats/" + nomFichierCSV);
	        String aEcrire = "Taille moyenne maximum\n";
	        DecimalFormat df = new DecimalFormat("#.###");
	        for (Integer taille : listeTriee) {
				liste = new ArrayList<>(nbComparaisonParTaille.get(taille));
				moyenne = getMoyenne(liste);
				max = getMax(liste);
				aEcrire += taille + " " + df.format(moyenne )+ " " + max + "\n";
			}
	        FileWriter writer = new FileWriter(fichierCSV);
	        writer.write(aEcrire);
	        writer.close();
	        
	        return true;
	        
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Calcule la moyenne des éléments d'une liste.
	 * @param liste La liste pour laquelle on souhaite la moyenne des éléments.
	 * @return La moyenne des éléments de la liste.
	 */
	private static float getMoyenne(ArrayList<Integer> liste) {
		int somme = 0;
		for(Integer x : liste)
			somme += x;
			
		return (float) somme/liste.size();
	}
	
	/**
	 * Récupère la valeur maximale dans une liste.
	 * @param liste La liste pour laquelle on souhaite récupérer la valeur maximale.
	 * @return L'élément le plus grand de la liste.
	 */
	private static int getMax(ArrayList<Integer> liste) {
		int max = -1;
		for(Integer x : liste) {
			if (x > max)
				max = x;
		}
		
		return max;
	}
	
	
}