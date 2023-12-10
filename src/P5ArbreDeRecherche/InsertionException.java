package P5ArbreDeRecherche;

public class InsertionException extends Exception{

	public InsertionException() {
		super("La clé est déjà présente dans l'arbre");
	}
}
