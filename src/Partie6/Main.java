package Partie6;


import autres.Outils;
import autres.ShakeSpear;


/**
 * Classe Main de l'étude expérimentale
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		/* Tas min tableau */
		/// Outils.AjoutIteratifsTasTab();
		/// Outils.SupprMinTasTab();
		/// Outils.ConstructionTasTab();
		/// Outils.UnionTasTab();
		
		/* Tas min arbre */
		/* ajouts itératifs */
		//* Outils.AjoutIteratifsTasArbre();
		//* Outils.ConstructionTasArbre();
		 ////Outils.SupprMinTasArbre();
		//* Outils.UnionTasArbre();

		/* File Binomiale */ 
		//* Outils.AjoutFileBinomiale();
		//* Outils.ConstructionFileBinomiale();
		//* Outils.UnionFileBinomiale();
		//* Outils.SupprMinFileBinomiale();



		/*ShakeSpear */
		ShakeSpear.stockerMD5DansArbre();
		//ShakeSpear.sauvegarderHashShakespeare();
		//ShakeSpear.AjoutTasVsFileBinomiale();
		 //ShakeSpear.SupprMinTasVsFileBinomiale();;
		 //ShakeSpear.ConstructionTasVsFileBinomiale();;
		 //ShakeSpear.UnionTasVsFileBinomiale();;

	}
	/* 
	!	10% de dataset insérer : 
	!	-	AjoutFileBinomiale 
	!	-	SupprMinFileBinomiale 
	!	-	AjoutTasVsFileBinomiale 
	!	-	SupprMinTasVsFileBinomiale 
	*/

}