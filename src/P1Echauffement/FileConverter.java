package P1Echauffement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import interfaces.ICle;

/**
 * Lit un fichier contenant les valeurs des clés en hexadécimal et renvoit un vecteur contenant les traductions
 * de ces clés sur 128 bits.
 */
public class FileConverter {
	
	/** Le fichier à lire. */
	private File file;
	
	/**
	 * Constructeur
	 * @param path : chemin du fichier a lire.
	 */
	public FileConverter(String path) {
		file = new File(path);
		if (file.exists()) {
		}
		else {
			System.out.println("File does not exist");
		}
		
	}
	
	/**
	 * Lit dans le fichier pour récupérer les clés en les stocke dans un vecteur. 
	 * @return le vecteur contenant les clés.
	 */
	public Vector<ICle> getCle(){
		Vector<ICle> cles = new Vector<ICle>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				
				String cleS = ligne.substring(2);
				
				Cle128Bit cle = new Cle128Bit(cleS);
				
				cles.add(cle);
			}
			br.close();
		}catch (IOException e) {
			System.out.println("erreur");
		}
		
		return cles;
	}
	
	/**
	 * Lit dans le fichier et renvoie l'ensemble des valeurs hexadécimales.
	 * @return le vector contenant les clés.
	 */
	public Vector<String> getCleHexa(){
		Vector<String> cles = new Vector<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				
				String cleS = ligne.substring(2);
				
				cles.add(cleS);
			}
			br.close();
		}catch (IOException e) {
			System.out.println("erreur");
		}
		
		return cles;
	}
}
