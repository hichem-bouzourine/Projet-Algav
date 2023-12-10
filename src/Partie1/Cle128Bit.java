package Partie1;

import java.math.BigInteger;

import interfaces.ICle;
/**
 * Clé codée sur 128 bits.
 */
public class Cle128Bit implements ICle{

	/** Representation hexadécimal de la clé. */
	private String cleString;
	
	/** Representation binaire sur 128 bits. 
	 * !	Si la taille ne dépasse pas 128 bits, alors la taille 
	 */
	/**
     * Représentation binaire sur 128 bits sous forme de quadruplet de 4 entiers non signés de 32 bits.
     */
    private int[] cle;
	
	/**
	 * Constructeur
	 * @param cleS : hexadecimal à transformer en clé de 128 bits, chaque caratère peut être représenter en 4 bits 
	 */
	public Cle128Bit(String cleS)  {
		cleString = cleS;
		/* Crée un bit set sur 128 bits  */
		cle = new int[4];

        /**
         * Convertir la clé (ou bien le string ) en hexadécimal puis la convertir en binaire
         * ! la classe BigInteger de Java ne prend en charge que la conversion directe de chaînes hexadécimales en entiers, mais pas en binaire
         */
        String cleBinaire = new BigInteger(cleString, 16).toString(2);
        int index = 0;
        // Remplir le quadruplet avec les 32 premiers bits, puis les 32 suivants, et ainsi de suite.
        for (int i = 0; i < cleBinaire.length(); i++) {
            int bitValue = Character.getNumericValue(cleBinaire.charAt(i));
            cle[index / 32] |= (bitValue << (31 - index % 32));
            index++;
        }
	}
	
	@Override
	public String toString() {
		return cleString + " Taille : " + cleString.length();
	}
	
	/**
	 * Renvoie la valeur binaire de la clé.
	 * @return La valeur binaire de la clé.
	 */
	public String valeurBinaire() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < cle.length * 32; i++) {
            int bitValue = (cle[i / 32] >> (31 - i % 32)) & 1;
            sb.append(bitValue);
        }

        return sb.toString();
	}
	
	@Override
	public boolean inf(ICle cle1) {
		if (!(cle1 instanceof Cle128Bit))
            throw new RuntimeException("Can't compare with other than Cle");
        Cle128Bit c = (Cle128Bit) cle1;
        boolean result = false;

        // Compare les entiers du quadruplet un par un
        for (int i = cle.length - 1; i >= 0; i--) {
			long unsignedThis = cle[i] & 0xFFFFFFFFL;
			long unsignedOther = c.cle[i] & 0xFFFFFFFFL;
			if (unsignedThis < unsignedOther) {
				return true;
			} else if (unsignedThis > unsignedOther) {
				return false;
			}
		}
        return result;
		
			
	}

	@Override
	public boolean eg(ICle cle1) {
		
		if (!(cle1 instanceof Cle128Bit))
            throw new RuntimeException("Can't compare with other than Cle");
        Cle128Bit c = (Cle128Bit) cle1;

        // Compare les entiers du quadruplet un par un
        for (int i = 0; i < cle.length; i++) {
			long unsignedThis = cle[i] & 0xFFFFFFFFL;
			long unsignedOther = c.cle[i] & 0xFFFFFFFFL;
            if (unsignedThis != unsignedOther) {
                return false;
            }
        }

        return true;
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Cle128Bit) {
			Cle128Bit c = (Cle128Bit) o;
			return this.eg(c);
		}
		else
			return false;
			
	}
	
	@Override
	public int hashCode() {
		return cleString.hashCode();
	}
}
