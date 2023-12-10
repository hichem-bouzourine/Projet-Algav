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
import Partie2.TasMinTab;
import Partie3.FileBinomiale;
import Partie3.TournoiBinomial;
import Partie4.MD5;
import Partie5.AVL;
import Partie5.InsertionException;
import interfaces.ICle;

public class ShakeSpear {
    
    
	/**
	 * Stocke tous les MD5 des mots de Shakespeare dans un AVL et affiche le nombre de collision.
	 */
	public static void stockerMD5DansArbre() {
		int nbCollision = 0;
		File folder = new File("donnees/Shakespeare/");
		File file;
		File[] listeFichiers = folder.listFiles();
		AVL<Cle128Bit> avl = new AVL<>();
		HashSet<String> motsAjoutes = new HashSet<>();
		ArrayList<String> listeMot = new ArrayList<>();
		
		for (int i=0; i<listeFichiers.length; i++) {
			file = listeFichiers[i];
			BufferedReader bufferedReader;
			String mot, md5;
			System.out.println("Lecture du fichier : " + file.getName() + " : Progression : " + (i+1) + "/" + listeFichiers.length);
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				mot = bufferedReader.readLine();
				
				while (mot != null) {
					// Mot n'existe pas 
					if (!motsAjoutes.contains(mot)) {		
						motsAjoutes.add(mot);
						listeMot.add(mot);
						md5 = MD5.genererMd5(mot);
						try {
							avl.inserer(new Cle128Bit(md5));
						} catch (InsertionException e) {
							System.err.println("Collison du MD5 : " + md5);
							nbCollision++;
						}
					}
					mot = bufferedReader.readLine();
				}	
				bufferedReader.close();
			} catch (IOException e) {
				System.err.println("Erreur avec le fichier : " + file.getName());
				e.printStackTrace();
				return;
			}
		}
		System.out.println("Nombre de collision : " + nbCollision);
		// System.out.println("Affichage de la liste de mot : ");
		// for(int i=0; i<listeMot.size(); i++) {
		// 	System.out.println("\t" + listeMot.get(i));
		// }
		System.out.println("Nombre de mots différents dans le jeu de données : " + listeMot.size());
	}	


	/**
	 * Stocke tous les MD5 des mots de Shakespeare dans leurs fichiers respectifs
	 */
	public static void sauvegarderHashShakespeare() {
		File folder = new File("donnees/Shakespeare/");
		File file;
		File[] listeFichiers = folder.listFiles();
		AVL<Cle128Bit> avl = new AVL<>();
		HashSet<String> motsAjoutes = new HashSet<>();
		ArrayList<String> listeMot = new ArrayList<>();
		String aEcrire="";
		File fichierTxtMD5 = new File("resultats/shakespeare/hashes.txt" );

		for (int i=0; i<listeFichiers.length; i++) {
			file = listeFichiers[i];
			BufferedReader bufferedReader;
			System.out.println(file.getName());
			// String nomFichierShakeSpearMd5 = file.getName().split(".txt")[0]+"_hashed_md5.txt";
			String mot, md5;
			System.out.println("Lecture du fichier : " + file.getName() + " : Progression : " + (i+1) + "/" + listeFichiers.length);
			try {

				bufferedReader = new BufferedReader(new FileReader(file));
				mot = bufferedReader.readLine();
				// Parcours et ajout de tous les MD5 des mots du fichier courant
				while (mot != null) {
					if (!motsAjoutes.contains(mot)) {		// On a pas encore calculé la signature de ce mot
						motsAjoutes.add(mot);
						listeMot.add(mot);
						md5 = MD5.genererMd5(mot);
						// System.out.println(md5 + " <= " + mot);
						try {
							avl.inserer(new Cle128Bit(md5));
							// sauvegarder dans le fichier 
							aEcrire+="0x"+md5+"\n";

						} catch (InsertionException e) {
							System.err.println("Collison du MD5 : " + md5);
						}
					}
					mot = bufferedReader.readLine();
					
				}	
				bufferedReader.close();
				

			} catch (IOException e) {
				System.err.println("Erreur avec le fichier : " + file.getName());
				e.printStackTrace();
				return;
			}
		}// Fin des ajouts
		try{
			FileWriter writer = new FileWriter(fichierTxtMD5);
			writer.write(aEcrire);
			writer.close();
		}
		catch(Exception e){
			System.out.println("Could not save hashes");
		}
		
	}	

    /**
	 *! 10% dernier  :  Calcule et sauvegarde les temps d'exécution de l'ajout d'une clé à une file binomiale vs un tas.
	 */
	public static void AjoutTasVsFileBinomiale() {
        TasMinTab tas;
		FileBinomiale file;
		HashMap<Integer, ArrayList<Float>> tempsParTailleTas = new HashMap<>();
		HashMap<Integer, ArrayList<Float>> tempsParTailleFile = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				// Division pour obtenir le temps en milliseconde
        nomFichier = "hashes.txt";
        fc = new FileConverter("resultats/shakespeare/"+nomFichier);
		System.out.println("taille "+tailles.length);
		System.out.println("taille elt pb "+tailles[4]);
		String nomFichierCSV = "ajout_tas_tab_vs_fileBinomiale_shakespeare.csv";
		for(int i=1; i<nb; i++) {
			for(int j=1; j<tailles.length; j++) {//tailles.length
				if (!tempsParTailleTas.containsKey(tailles[j])){

					tempsParTailleTas.put(tailles[j], new ArrayList<Float>());
					tempsParTailleFile.put(tailles[j], new ArrayList<Float>());
                }
				
                tas = new TasMinTab(10000);
				file = new FileBinomiale(10000);
				cpt++;
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
				Vector<ICle> cles = fc.getCle();
				System.out.println(cles.size());
				// Ajout de toutes les cles
				for (int k = 0; k<tailles[j]; k++) {
					//System.out.println("boucle");
					//System.out.println("j="+j);
					//System.out.println("k="+k);
					
					ICle c = cles.get(k);
					//System.out.println);
					// rajouter 80 % du dataset dans une clé et calculer le temps nécessaire pour les 20 %
					if (k < tailles[j] - (tailles[j]/20)){

                        tas.ajout(c);
						file = (FileBinomiale) file.ajout(new TournoiBinomial(c));
                    }

					// Les 20% des ajouts restant
					// Il faut calculer le temps de l'ajout
					else {
                        // TAS
                        debut = System.nanoTime();
                        tas.ajout(c);
						fin = System.nanoTime();
						ecoule = ((fin - debut)/f);
						//System.out.println("\tTemps d'exécution Tas : " + ecoule + "ms");
						tempsParTailleTas.get(tailles[j]).add(ecoule);
                        

                        // File

						debut = System.nanoTime();
						file = (FileBinomiale) file.ajout(new TournoiBinomial(c));
						fin = System.nanoTime();
						ecoule = ((fin - debut)/f);
						//System.out.println("\tTemps d'exécution File : " + ecoule + "ms");
						tempsParTailleFile.get(tailles[j]).add(ecoule);
					}
				}
			}
		}// Toutes les constructions ont été effectuées
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasEtFileBinomiale(nomFichierCSV,tempsParTailleTas,tempsParTailleFile))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
	/**
	 *! 10% dernier : Calcule le temps nécessaire de la suppression du minimum tas min vs file binomiale .
	 */
	public static void SupprMinTasVsFileBinomiale() {
        TasMinTab tas;
		FileBinomiale file;
		HashMap<Integer, ArrayList<Float>> tempsParTailleTas = new HashMap<>();
		HashMap<Integer, ArrayList<Float>> tempsParTailleFile = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				// Division pour obtenir le temps en milliseconde
		
		String nomFichierCSV = "supprMin_tas_tab_vs_fileBinomiale_shakespeare.csv";
        nomFichier = "hashes.txt";
        fc = new FileConverter("resultats/shakespeare/"+nomFichier);

		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTailleTas.containsKey(tailles[j])){
					tempsParTailleTas.put(tailles[j], new ArrayList<Float>());
					tempsParTailleFile.put(tailles[j], new ArrayList<Float>());
                }
				
                tas = new TasMinTab(10000);
				file = new FileBinomiale(0);
				cpt++;
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				
                tas.construction(new ArrayList<>(fc.getCle()));
                file=(FileBinomiale)file.construction(fc.getCle());
				// Ajout de toutes les cles
				for (int k = 0; k<tailles[j]/20; k++) {
                    // TAS
                    debut = System.nanoTime();
                    tas.supprMin();
                    fin = System.nanoTime();
                    ecoule = ((fin - debut)/f);
                    System.out.println("\tTemps d'exécution Tas : " + ecoule + "ms");
                    tempsParTailleTas.get(tailles[j]).add(ecoule);
                    // File

                    debut = System.nanoTime();
                    file = (FileBinomiale) file.supprCleMin();
                    fin = System.nanoTime();
                    ecoule = ((fin - debut)/f);
                    System.out.println("\tTemps d'exécution File : " + ecoule + "ms");
                    tempsParTailleFile.get(tailles[j]).add(ecoule);
				}
			}
		}// Toutes les constructions ont été effectuées
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasEtFileBinomiale(nomFichierCSV,tempsParTailleTas,tempsParTailleFile))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	

    /**
	 *! 10% dernier : Calcule le temps nécessaire de la construction tas min vs file binomiale .
	 */
	public static void ConstructionTasVsFileBinomiale() {
        TasMinTab tas;
		FileBinomiale file;
		HashMap<Integer, ArrayList<Float>> tempsParTailleTas = new HashMap<>();
		HashMap<Integer, ArrayList<Float>> tempsParTailleFile = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5; int cpt = 0;
		
		String nomFichier;
		FileConverter fc;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				// Division pour obtenir le temps en milliseconde
		
		String nomFichierCSV = "construction_tas_tab_vs_fileBinomiale_shakespeare.csv";
        nomFichier = "hashes.txt";
        fc = new FileConverter("resultats/shakespeare/"+nomFichier);

		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTailleTas.containsKey(tailles[j])){
					tempsParTailleTas.put(tailles[j], new ArrayList<Float>());
					tempsParTailleFile.put(tailles[j], new ArrayList<Float>());
                }
				
                tas = new TasMinTab(10000);
				file = new FileBinomiale(0);
				cpt++;
				System.out.println("Fichier : " + nomFichier + "Progression : " + cpt + "/" + nb*tailles.length);
				List<ICle> cles = fc.getCle();

                // TAS
                debut = System.nanoTime();
                tas.construction(cles);
                fin = System.nanoTime();
                ecoule = ((fin - debut)/f);
                System.out.println("\tTemps d'exécution Tas : " + ecoule + "ms");
                tempsParTailleTas.get(tailles[j]).add(ecoule);
                // File

                debut = System.nanoTime();
                file = (FileBinomiale) file.construction(cles);
                fin = System.nanoTime();
                ecoule = ((fin - debut)/f);
                System.out.println("\tTemps d'exécution File : " + ecoule + "ms");
                tempsParTailleFile.get(tailles[j]).add(ecoule);
			}
		}// Toutes les constructions ont été effectuées
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasEtFileBinomiale(nomFichierCSV,tempsParTailleTas,tempsParTailleFile))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	


    /**
	 * Calcule le temps nécessaire du l'union tas min vs file binomiale .
	 */
	public static void UnionTasVsFileBinomiale() {
        TasMinTab tas,tas2;
		FileBinomiale file,file2;
		HashMap<Integer, ArrayList<Float>> tempsParTailleTas = new HashMap<>();
		HashMap<Integer, ArrayList<Float>> tempsParTailleFile = new HashMap<>();
		
		int tailles[] = {1000, 5000, 10000, 20000, 50000, 80000, 120000, 200000};
		int nb = 5;
		
		String nomFichier,nomFichier2;
		FileConverter fc,fc2;
		
		long debut, fin;
		float ecoule;
		float f = 1000000;				// Division pour obtenir le temps en milliseconde
		
		String nomFichierCSV = "union_tas_tab_vs_fileBinomiale_shakespeare.csv";
        nomFichier = "hashes.txt";
        fc = new FileConverter("resultats/shakespeare/"+nomFichier);
        List<ICle> cles = fc.getCle();


		for(int i=1; i<=nb; i++) {
			for(int j=0; j<tailles.length; j++) {
				if (!tempsParTailleTas.containsKey(tailles[j])){
					tempsParTailleTas.put(tailles[j], new ArrayList<Float>());
					tempsParTailleFile.put(tailles[j], new ArrayList<Float>());
                }
                tas = new TasMinTab(10000);
                tas.construction(cles);
				file = new FileBinomiale(0);
                file = (FileBinomiale) file.construction(cles);
                System.out.println("Union de " + nomFichier + " avec : ");
                for(int k=1; k<=nb;k++){
                    nomFichier2 = "jeu_"+k+"_nb_cles_"+tailles[j]+".txt";
                    System.out.println("\t- " + nomFichier2);
                    fc2 = new FileConverter("donnees/cles_alea/"+nomFichier2);
                    List<ICle> cles2 = fc2.getCle();
                    tas2 = new TasMinTab(10000);
                    tas2.construction(cles2);
                    file2 = new FileBinomiale(0);
                    file2 = (FileBinomiale) file2.construction(cles);

                    // TAS
                    debut = System.nanoTime();
                    tas.union(tas2);
                    fin = System.nanoTime();
                    ecoule = ((fin - debut)/f);
                    System.out.println("\tTemps d'exécution Tas : " + ecoule + "ms");
                    tempsParTailleTas.get(tailles[j]).add(ecoule);


                    // File
                    debut = System.nanoTime();
                    file.unionFile(file, file2);
                    fin = System.nanoTime();
                    ecoule = ((fin - debut)/f);
                    System.out.println("\tTemps d'exécution File : " + ecoule + "ms");
                    tempsParTailleFile.get(tailles[j]).add(ecoule);


                }
			}
		}// Toutes les constructions ont été effectuées
		
		System.out.println("Sauvegarde des resultats dans le fichier \"" + nomFichierCSV + "\"...");
		if (FileSave.sauvegarderResultatTasEtFileBinomiale(nomFichierCSV,tempsParTailleTas,tempsParTailleFile))
			System.out.println("Les résultats ont été sauvegardés !");
		else
			System.err.println("Erreur lors de la sauvagardes des résultats");
	}
	
    

}
