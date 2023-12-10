package autres;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import Partie1.Cle128Bit;
import Partie1.FileConverter;
import Partie2.TasMinArbre;
import Partie2.TasMinTab;
import Partie3.FileBinomiale;
import Partie3.TournoiBinomial;
import Partie4.MD5;
import Partie5.AVL;
import Partie5.InsertionException;
import interfaces.ICle;

/**
 * Classe permettant la génération de nos résulats.
 */
public class Outils {

	/**
	 * Affiche un tableau.
	 * @param tab Le tableau à afficher.
	 */
	@SuppressWarnings("unused")
	private static void printTab(ICle[] tab) {
		ICle c;
		System.out.print("[");
		for (int i=0; i<tab.length; i++) {
			c = tab[i];
			System.out.print(c);
			if (i != tab.length-1)
				System.out.print(", ");
		}
		System.out.println("]");
	}
/*
!			Tas : Arbre 	 
*/
	/**
	 * Calcule et sauvegarde les temps d'exécution de l'ajout d'une clé à un tas min utilisant un tableau.
	*/
	public static void AjoutIteratifsTasArbre() {
		TasMinArbre tas;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "ajout_iteratifs_tas_arbre.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinArbre();
				cpt++;
				//nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				nomFichier = "donnees\\cles_alea\\jeu_1_nb_cles_1000.txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
			
				fc = new FileConverter(nomFichier);
				//fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				Vector<ICle> cles = fc.getCle();
				
				debut = System.nanoTime();
				tas.ajoutsIteratifs(cles);
				fin = System.nanoTime();
				ecoule = ((fin - debut)/f);
				
				System.out.println("\tTemps d'exécution : " + ecoule + "ms");
				tempsParTaille.get(tailles[j]).add(ecoule);
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	public static void SupprMinTasArbre() {
		TasMinArbre tas;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		ArrayList<ICle> liste;

		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "supprMin_tas_arbre.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				//tas = new TasMinArbre();
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				//Vector<ICle> cles = fc.getCle();
				liste = new ArrayList<ICle>(fc.getCle());
				tas = new TasMinArbre();
				tas.construction(liste);

				//tas.construction(new ArrayList<>(fc.getCle()));
				int tmpTaille = tas.size();
				for (int k=0; k<tmpTaille/20; k++ ) {
					debut = System.nanoTime();
					
					tas.supprMin();
					
					fin = System.nanoTime();
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	

	/**
	 * Calcule et sauvegarde les temps d'exécution de construction sur un tas min utilisant un arbre.
	 */
	public static void ConstructionTasArbre() {
		TasMinArbre tas;
		ArrayList<ICle> liste;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "construction_tas_arbre.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinArbre();
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				liste = new ArrayList<ICle>(fc.getCle());					// La liste des clé à insérer
				
				debut = System.nanoTime();
				
				tas.construction(liste);
				
				fin = System.nanoTime();
				ecoule = ((fin - debut)/f);
				
				System.out.println("\tTemps d'exécution : " + ecoule + "ms");
				tempsParTaille.get(tailles[j]).add(ecoule);
				
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagarde des résultats");
	}
	
	/**
	 * Calcule et sauvegarde les temps d'exécution de l'union de deux tas min utilisant des arbres.
	 */
	public static void UnionTasArbre() {
		TasMinArbre tas1, tas2;
		ArrayList<ICle> liste1, liste2;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5;
		
		String nomFichier1, nomFichier2;
		FileConverter fc1, fc2;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "union_tas_arbre.csv";
		for(int i=1; i<nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				nomFichier1 = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				fc1 = new FileConverter("donnees/cles_alea/"+nomFichier1);
				liste1 = new ArrayList<ICle>(fc1.getCle());
				tas1 = new TasMinArbre();
				tas1.construction(liste1);
				
				System.out.println("Union de " + nomFichier1 + " avec : ");
				for (int k=i+1; k<=nb; k++) {
					nomFichier2 = "jeu_"+k+"_nb_cles_"+tailles[j]+".txt";
					System.out.println("\t- " + nomFichier2);
					fc2 = new FileConverter("donnees/cles_alea/"+nomFichier2);
					liste2 = new ArrayList<ICle>(fc2.getCle());
					tas2 = new TasMinArbre();
					tas2.construction(liste2);
					
					debut = System.nanoTime();
					tas2.union(tas1);
					fin = System.nanoTime();
					
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
				System.out.println();
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}



	/*
!			Tas : Tableau 	 
*/
	/**
	 * Calcule et sauvegarde les temps d'exécution de l'ajout d'une clé à un tas min utilisant un tableau.
	*/
	public static void AjoutIteratifsTasTab() {
		TasMinTab tas;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "ajout_iteratifs_tas_tab.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinTab(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				
				
				Vector<ICle> cles = fc.getCle();
				
				debut = System.nanoTime();
				tas.ajoutsIteratifs(cles);
				fin = System.nanoTime();
				ecoule = ((fin - debut)/f);
				
				System.out.println("\tTemps d'exécution : " + ecoule + "ms");
				tempsParTaille.get(tailles[j]).add(ecoule);
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 *! 10% dernier :  Calcule et sauvegarde les temps d'exécution de l'ajout d'une clé à un tas min utilisant un tableau.
	 */
	public static void AjoutTasTab() {
		TasMinTab tas;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "ajout_steped_tas_tab.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinTab(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				Vector<ICle> cles = fc.getCle();
				// Ajout de toutes les cles
				for (int k = 0; k<cles.size(); k++) {
					ICle c = cles.get(k);
					if (k < cles.size() - (cles.size()/20))
						tas.ajout(c);

					// Les 20% des ajouts restant
					// Il faut calculer le temps de l'ajout
					else {
						debut = System.nanoTime();
						
						tas.ajout(c);
						
						fin = System.nanoTime();
						ecoule = ((fin - debut)/f);
						
						System.out.println("\tTemps d'exécution : " + ecoule + "ms");
						tempsParTaille.get(tailles[j]).add(ecoule);
					}
				}
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 * Calcule et sauvegarde les temps d'exécution de construction sur un tas min utilisant un tableau.
	 */
	@SuppressWarnings("unused")
	public static void ConstructionTasTab() {
		TasMinTab tas;
		ArrayList<ICle> liste;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "construction_tas_tab.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinTab(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				liste = new ArrayList<ICle>(fc.getCle());					// La liste des clé à insérer
				
				debut = System.nanoTime();
				
				tas.construction(liste);
				
				fin = System.nanoTime();
				ecoule = ((fin - debut)/f);
				
				System.out.println("\tTemps d'exécution : " + ecoule + "ms");
				tempsParTaille.get(tailles[j]).add(ecoule);
				
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	
	/**
	 *! 10% dernier :  Calcule et sauvegarde les temps d'exécution de la suppression de la clé minimum dans un tas min utilisant un tableau.
	 */
	public static void SupprMinTasTab() {
		TasMinTab tas;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "supprMin_tas_tab.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				tas = new TasMinTab(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				
				tas.construction(new ArrayList<>(fc.getCle()));
				int tmpTaille = tas.size();
				for (int k=0; k<tmpTaille/20; k++ ) {
					debut = System.nanoTime();
					
					tas.supprMin();
					
					fin = System.nanoTime();
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 * Calcule et sauvegarde les temps d'exécution de l'union de deux tas min utilisant des tableaux.
	 */
	@SuppressWarnings("unused")
	public static void UnionTasTab() {
		TasMinTab tas1, tas2;
		ArrayList<ICle> liste1, liste2;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5;
		
		String nomFichier1, nomFichier2;
		FileConverter fc1, fc2;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "union_tas_tab.csv";
		for(int i=1; i<nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				nomFichier1 = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				fc1 = new FileConverter("donnees/cles_alea/"+nomFichier1);
				liste1 = new ArrayList<ICle>(fc1.getCle());
				tas1 = new TasMinTab(10000);
				tas1.construction(liste1);
				
				System.out.println("Union de " + nomFichier1 + " avec : ");
				for (int k=i+1; k<=nb; k++) {
					nomFichier2 = "jeu_"+k+"_nb_cles_"+tailles[j]+".txt";
					System.out.println("\t- " + nomFichier2);
					fc2 = new FileConverter("donnees/cles_alea/"+nomFichier2);
					liste2 = new ArrayList<ICle>(fc2.getCle());
					tas2 = new TasMinTab(10000);
					tas2.construction(liste2);
					
					debut = System.nanoTime();
					tas2.union(tas1);
					fin = System.nanoTime();
					
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
				System.out.println();
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	
	/**
	 * Calcule et sauvegarde les temps d'exécution de la construction d'une file binomiale.
	 */
	public static void ConstructionFileBinomiale() {
		FileBinomiale fileBinomiale;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "construction_fileBinomiale.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				fileBinomiale = new FileBinomiale(0);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				
				List<ICle> cles = fc.getCle();
				
				debut = System.nanoTime();
				fileBinomiale.construction(cles);
				fin = System.nanoTime();
				
				ecoule = ((fin - debut)/f);
				
				System.out.println("\tTemps d'exécution : " + ecoule + "ms");
				tempsParTaille.get(tailles[j]).add(ecoule);
				
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatFileBinomiale(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
		
	}
	
	/**
	 * Calcule et sauvegarde les temps d'exécution de l'union de deux files binomiales.
	 */
	public static void UnionFileBinomiale(){
		
		FileBinomiale fileBinomiale1, fileBinomiale2;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5;
		
		String nomFichier1, nomFichier2;
		FileConverter fc1, fc2;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;
		
		String nomFichierCSV = "union_fileBinomiale.csv";
		for(int i=1; i<nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				nomFichier1 = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				fc1 = new FileConverter("donnees/cles_alea/"+nomFichier1);
				
				fileBinomiale1 = new FileBinomiale(0);
				fileBinomiale1.construction(fc1.getCle());
				
				System.out.println("Union de " + nomFichier1 + " avec : ");
				for (int k=i+1; k<=nb; k++) {
					nomFichier2 = "jeu_"+k+"_nb_cles_"+tailles[j]+".txt";
					System.out.println("\t- " + nomFichier2);
					fc2 = new FileConverter("donnees/cles_alea/"+nomFichier2);
					
					fileBinomiale2 = new FileBinomiale(0);
					fileBinomiale2.construction(fc2.getCle());
					
					debut = System.nanoTime();
					
					fileBinomiale2 = (FileBinomiale)fileBinomiale2.unionFile(fileBinomiale2, fileBinomiale1);
					
					fin = System.nanoTime();
					
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
				System.out.println();
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatFileBinomiale(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}

	/**
	 *! 10% dernier  :  Calcule et sauvegarde les temps d'exécution de l'ajout d'une clé à une file binomiale.
	 */
	public static void AjoutFileBinomiale() {
		FileBinomiale file;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "ajout_fileBinomiale.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				file = new FileBinomiale(10000);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				Vector<ICle> cles = fc.getCle();
				// Ajout de toutes les cles
				for (int k = 0; k<cles.size(); k++) {
					ICle c = cles.get(k);
					// rajouter 80 % du dataset dans une clé et calculer le temps nécessaire pour les 20 %
					if (k < cles.size() - (cles.size()/20))
						file = (FileBinomiale) file.ajout(new TournoiBinomial(c));

					// Les 20% des ajouts restant
					// Il faut calculer le temps de l'ajout
					else {
						debut = System.nanoTime();
						
						file = (FileBinomiale) file.ajout(new TournoiBinomial(c));
						
						fin = System.nanoTime();
						ecoule = ((fin - debut)/f);
						
						System.out.println("\tTemps d'exécution : " + ecoule + "ms");
						tempsParTaille.get(tailles[j]).add(ecoule);
					}
				}
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 *! 10% dernier : Calcule et sauvegarde les temps d'exécution de la suppression de la clé minimum dans une file binomiale.
	 */
	public static void SupprMinFileBinomiale() {
		FileBinomiale file;
		HashMap<Integer, ArrayList<Float>> tempsParTaille = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				
		
		String nomFichierCSV = "supprMin_fileBinomiale.csv";
		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTaille.containsKey(tailles[j]))		// Initialisation des ArrayList des Hashmap
					tempsParTaille.put(tailles[j], new ArrayList<Float>());
				
				file = new FileBinomiale(0);
				cpt++;
				nomFichier = "jeu_"+i+"_nb_cles_"+tailles[j]+".txt";
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				fc = new FileConverter("donnees/cles_alea/"+nomFichier);
				
				file=(FileBinomiale)file.construction(fc.getCle());
				for (int k=0; k<tailles[j]/20; k++ ) {
					debut = System.nanoTime();
					
					file = (FileBinomiale) file.supprCleMin();
					
					fin = System.nanoTime();
					ecoule = ((fin - debut)/f);
					
					System.out.println("\tTemps d'exécution : " + ecoule + "ms");
					tempsParTaille.get(tailles[j]).add(ecoule);
				}
			}
		}
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasMin(nomFichierCSV, tempsParTaille))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
}
