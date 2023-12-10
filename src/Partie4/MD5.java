package Partie4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;


public class MD5
{
	/** Paramètre h0 de l'algorithme du MD5. */
	private static final int h0 = 0x67452301;
	
	/** Paramètre h1 de l'algorithme du MD5. */
	private static final int h1 = 0xEFCDAB89;
	
	/** Paramètre h2 de l'algorithme du MD5. */
	private static final int h2 = 0x98BADCFE;
	
	/** Paramètre h3 de l'algorithme du MD5. */
	private static final int h3 = 0x10325476;

	private static final int[] r = {
		7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
		5, 9, 14, 20,  5, 9, 14, 20,  5, 9, 14, 20,  5, 9, 14, 20,
		4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23, 
		6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21
	};

	/* ces nombres représentent des sinus d'entiers. l'algorithme sauevgarde en hexa par contre nous on aura jus */
	private static final int[] k = new int[64];
	
	private static boolean kEstDefini = false;
	
	
	private static void definirK() {
		for (int i = 0; i < 64; i++)
			// partie entière de : (2^32)* sin(i+1)
			k[i] = (int)(long) Math.floor(Math.abs(Math.sin(i + 1)) * Math.pow(2, 32));
	}

	/**
	 * Calcule le MD5 d'un message.
	 * @param message Le message pour lequel on cherche à calculer le MD5.
	 * @return L'empreinte MD5 du message sous forme hexadécimal.
	 */
	public static String genererMd5(String message) {
	
		if (!kEstDefini) {
			definirK();
			kEstDefini = true;
		}
		
		byte[] messageBytes = message.getBytes();


		// Il faut réserver une taille multiple de 64 bytes permettant de stocker le message ainsi que son padding.
		// Les 64 bytes réservés ici représentent les 512 bits nécessaires à l'algorithme du MD5.
		// L'utillisation du ByteBuffer nous permet d'ajouter facilement les informations de padding du MD5.
		int taille = (((messageBytes.length) / 64) + 1) * 64;
		ByteBuffer byteBufferMessage = ByteBuffer.allocate(taille).order(ByteOrder.LITTLE_ENDIAN);
		
		// On ajoute le message au buffer
		byteBufferMessage.put(messageBytes);
		
		// Les opérations bit à bit étant très compliqué sur des ByteBuffers, on utilise ici la technique suivante :
		// Le ByteBuffer que l'on alloue a toujours une taille multiple de 512 bits (soit 64 bytes).
		// L'ajout du bit à 1 et des bits restant à zero se fait via l'ajout du byte 0x80 (1000 0000 en binaire).
		// Cela ajoute un byte dont le premier bit est un '1' est les autres sont des '0'.
		// Les bits restants dans le ByteBuffer sont déjà tous des zéros, cela nous évite de boucler pour les ajouter.
		byteBufferMessage.put((byte)0x80);

		// Le type long est encodé sur 64bits.
		long tailleMessageEnBit = messageBytes.length * 8;
		
		// On ajoute la taille du message à la fin du ByteBuffer.
		// L'ajout se fait 8 bytes avant la fin du ByteBuffer car 64 bits correspondent à 8 bytes en mémoire :
		// 64/8 = 8.
		byteBufferMessage.putLong(byteBufferMessage.capacity() - 8, tailleMessageEnBit);
		
		byteBufferMessage.rewind();
				
		
		int a = h0;
		int b = h1;
		int c = h2;
		int d = h3;
		int temp;
		while (byteBufferMessage.hasRemaining()) {
			
			IntBuffer w = byteBufferMessage.slice().order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();

			// On avance la position de 512 bits.
			byteBufferMessage.position(byteBufferMessage.position() + 64);
			
			int g;
			for (int i = 0; i < 64; i++) {
				int f = 0;
				if ((0 <= i) && (i <= 15)) {		
					f = (b & c) | (~b & d);
					g = i;
				}
				else if ((16 <= i) && (i <= 31)) {	
					f = (d & b) | (~d & c);
					g = (5 * i + 1) % 16;
				}
				else if ((32 <= i) && (i <= 47)){	
					f = b ^ c ^ d;
					g = (3 * i + 5) % 16;
				}
				else {					
					f = c ^ (b | ~d);
					g = (7 * i) % 16;
				}
				
				temp = d;
				d = c;
				c = b;
				b = Integer.rotateLeft(a + f + k[i] + w.get(g), r[i]) + b;
				a = temp;
			}

			a += h0;
			b += h1;
			c += h2;
			d += h3;
		}

		ByteBuffer md5 = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
		

		// mettre en little indian 
		md5.putInt(a);
		md5.putInt(b);
		md5.putInt(c);
		md5.putInt(d);
		
		return toHexadecimal(md5.array());
	}
	
	/**
	 * Convertie un tableau de byte en une string sous forme hexadécimal.
	 * @param message Le message à convertir. 
	 * @return La conversion hexadécimal du message.
	 */
	public static String toHexadecimal(byte[] message) {
		StringBuilder sb = new StringBuilder(message.length * 2);
		for(byte b: message)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}
}
/*
!	Pseudo code : 
* Note : Toutes les variables sont sur 32 bits

* Définir r comme suit : 
var entier[64] r, k
r[ 0..15] := {7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22}
r[16..31] := {5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20}
r[32..47] := {4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23}
r[48..63] := {6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21}

* MD5 utilise des sinus d'entiers pour ses constantes :
pour i de 0 à 63 faire
    k[i] := floor(abs(sin(i + 1)) × 2^32)
fin pour

* Préparation des variables :
var entier h0 := 0x67452301
var entier h1 := 0xEFCDAB89
var entier h2 := 0x98BADCFE
var entier h3 := 0x10325476

* Préparation du message (padding) :
ajouter le bit "1" au message
ajouter le bit "0" jusqu'à ce que la taille du message en bits soit égale à 448 (mod 512)
ajouter la taille du message initial(avant le padding) codée en 64-bit little-endian au message

* Découpage en blocs de 512 bits :
pour chaque bloc de 512 bits du message
    subdiviser en 16 mots de 32 bits en little-endian w[i], 0 ≤ i ≤ 15

    * initialiser les valeurs de hachage :
    var entier a := h0
    var entier b := h1
    var entier c := h2
    var entier d := h3

    * Boucle principale :
    pour i de 0 à 63 faire
        si 0 ≤ i ≤ 15 alors
              f := (b et c) ou ((non b) et d)
              g := i
        sinon si 16 ≤ i ≤ 31 alors
              f := (d et b) ou ((non d) et c)
              g := (5×i + 1) mod 16
        sinon si 32 ≤ i ≤ 47 alors
              f := b xor c xor d
              g := (3×i + 5) mod 16
        sinon si 48 ≤ i ≤ 63 alors
            f := c xor (b ou (non d))
            g := (7×i) mod 16
        fin si
        var entier temp := d
        d := c
        c := b
        b := leftrotate((a + f + k[i] + w[g]), r[i]) + b
        a := temp
    fin pour

    * ajouter le résultat au bloc précédent :
    h0 := h0 + a
    h1 := h1 + b 
    h2 := h2 + c
    h3 := h3 + d
fin pour

var entier empreinte := h0 concaténer h1 concaténer h2 concaténer h3 //(en little-endian) 
*/
