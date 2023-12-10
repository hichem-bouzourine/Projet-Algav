package P4HachageMD5.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import P4HachageMD5.MD5;

/**
 * Classe de test du calcule du MD5.
 */
public class MD5Test {
	/** Les strings que l'on va convertir. */
	String[] aTester = { "a", "A", "b","B" , "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "123456789" ,"jason", "JASON", "statham", "STATHAM", "Jason Statham", "ABCDEFGHIKLMNOPQRSTUVWXYZ012345678910111213141516171819" };
	
	/** Les résultats attendus. */
	String[] resAttendu  = { "0cc175b9c0f1b6a831c399e269772661", "7fc56270e7a70fa81a5935b72eacbe29", "92eb5ffee6ae2fec3ad71c777531578f","9d5ed678fe57bcca610140957afab571" , "c3fcd3d76192e4007dfb496cca67e13b", "437bba8e0bf58337674f4539e75186ac", "25f9e794323b453885f5181f1b624d0b" ,"2b877b4b825b48a9a0950dd5bd1f264d", "3ed4690647a0b4f8a071247717b7e863", "b6cb92871daf36e83e00decc627742f6", "ab147bc35023c3f6e1d41a6b880a7231", "cbea8e656880f9aeb99426549584aa4c", "8070ce5ba75ec919d25035678ee64985" };
	
	/**
	 * Méthode de test du calcule du MD5.
	 */
	@Test
	public void testMD5() {
		assertTrue(aTester.length == resAttendu.length);
		String res;
		for(int i=0; i<aTester.length; i++) {
			res = MD5.genererMd5(aTester[i]);
			System.out.println("MD5(\"" + aTester[i] + "\") => " + res);
			assertTrue(resAttendu[i].equals(res));
		}	
	}
}
