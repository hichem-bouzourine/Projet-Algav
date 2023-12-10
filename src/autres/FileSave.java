package autres;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FileSave {
    /**
	 * Calcule la moyenne des éléments d'une liste.
	 * @param liste La liste pour laquelle on souhaite la moyenne des éléments.
	 * @return La moyenne des éléments de la liste.
	 */
	public static float getMoyenne(ArrayList<Float> liste) {
		float somme = 0;
		for(Float x : liste)
			somme += x;
			
		return somme/liste.size();
	}
	
	/**
	 * Récupère la valeur maximale dans une liste.
	 * @param liste La liste pour laquelle on souhaite récupérer la valeur maximale.
	 * @return L'élément le plus grand de la liste.
	 */
	public static float getMax(ArrayList<Float> liste) {
		float max = -1;
		for(Float x : liste) {
			if (x > max)
				max = x;
		}
		
		return max;
	}

    /**
	 * Sauvegarde les résultats dans un fichier CSV.
	 * @param nomFichierCSV Le nom du fichier CSV.
	 * @param tempsParTaille Les résultats à sauvegarder
	 * @return True si la sauvegarder est un succès, false sinon.
	 */
	public static boolean sauvegarderResultatTasMin(String nomFichierCSV, HashMap<Integer, ArrayList<Float>> tempsParTaille) {
		ArrayList<Float> liste;
		float moyenne;
		float max;
		ArrayList<Integer> listeTriee = new ArrayList<>(tempsParTaille.keySet());
		Collections.sort(listeTriee);

		// Écriture des résultats
		try {
			File fichierCSV = new File("resultats/" + nomFichierCSV);
	        String aEcrire = "Taille,moyenne,maximum\n";
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
	        DecimalFormat df = new DecimalFormat("#.#####",symbols);			// Version valeurs normales
	        // DecimalFormat df = new DecimalFormat("#.###");			// Version valeurs normales
	        for (Integer taille : listeTriee) {
				liste = new ArrayList<>(tempsParTaille.get(taille));
				moyenne = getMoyenne(liste);
				max = getMax(liste);
				aEcrire += taille + "," + df.format(moyenne)+ "," + df.format(max) + "\n";
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
	 * Sauvegarde les résultats dans un fichier CSV.
	 * @param nomFichierCSV Le nom du fichier CSV.
	 * @param tempsParTaille Les résultats à sauvegarder
	 * @return True si la sauvegarder est un succès, false sinon.
	 */
	public static boolean sauvegarderResultatFileBinomiale(String nomFichierCSV, HashMap<Integer, ArrayList<Float>> tempsParTaille) {
		ArrayList<Float> liste;
		float moyenne;
		float max;
		ArrayList<Integer> listeTriee = new ArrayList<>(tempsParTaille.keySet());
		Collections.sort(listeTriee);

		// Écriture des résultats
		try {
			File fichierCSV = new File("resultats/" + nomFichierCSV);
	        String aEcrire = "Taille,moyenne,maximum\n";
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
	        DecimalFormat df = new DecimalFormat("#.#####",symbols);	
	        for (Integer taille : listeTriee) {
				liste = new ArrayList<>(tempsParTaille.get(taille));
				moyenne = getMoyenne(liste);
				max = getMax(liste);
				aEcrire += taille + "," + df.format(moyenne)+ "," + df.format(max) + "\n";
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


    public static boolean sauvegarderResultatTasEtFileBinomiale(String nomFichierCSV, HashMap<Integer, ArrayList<Float>> tempsParTailleTas,HashMap<Integer, ArrayList<Float>> tempsParTailleFile){
        ArrayList<Float> listeTas,listeFile;
		float moyenneTas,moyenneFile,maxTas,maxFile;
		ArrayList<Integer> listeTrieeTas = new ArrayList<>(tempsParTailleTas.keySet());
		ArrayList<Integer> listeTrieeFile = new ArrayList<>(tempsParTailleFile.keySet());
		Collections.sort(listeTrieeTas);
		Collections.sort(listeTrieeFile);

		// Écriture des résultats
		try {
			File fichierCSV = new File("resultats/" + nomFichierCSV);
	        String aEcrire = "Taille,moyenne_tas,maximum_tas,moyenne_file,max_file\n";
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
	        DecimalFormat df = new DecimalFormat("#.#####",symbols);	
	        for (Integer taille : listeTrieeTas) {
				listeTas = new ArrayList<>(tempsParTailleTas.get(taille));
				moyenneTas = getMoyenne(listeTas);
				maxTas = getMax(listeTas);
                
				listeFile = new ArrayList<>(tempsParTailleFile.get(taille));
				moyenneFile = getMoyenne(listeFile);
				maxFile = getMax(listeFile);
				aEcrire += taille + "," + df.format(moyenneTas)+ "," + df.format(maxTas) +"," +df.format(moyenneFile)+ "," + df.format(maxFile) + "\n";
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
}
