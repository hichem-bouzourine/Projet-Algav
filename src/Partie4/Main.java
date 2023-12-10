package Partie4;

/**
 * Main graphique pour le MD5.
 */
public class Main {

	public static void main(String[] args) {
		String[] aTester = { "", "a", "A", "b","B" , "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "123456789" ,"jason", "JASON", "statham", "STATHAM", "Jason Statham", "Goku", "Broly", "Gagner", "Croisssant", "Dormir", "Manger", "Sport", "ABCDEFGHIKLMNOPQRSTUVWXYZ012345678910111213141516171819" };
		
		String res;
		for(int i=0; i<aTester.length; i++) {
			res = MD5.genererMd5(aTester[i]);
			System.out.println("MD5(\"" + aTester[i] + "\") => " + res);
		}
	}

}
