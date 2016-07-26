package sjgs.utils.encryption;

import static sjgs.utils.Utils.alphabet;
import static sjgs.utils.Utils.nextByte;
import static sjgs.utils.Utils.nextInt;
import sjgs.utils.Utils;

public class CaesarCipher implements EncryptionInterface {

	private final byte[] message;
	private int key;

	public CaesarCipher(final String message) {
		this.message = message.getBytes();
	}

	public String encrypt(int key) {
		if(key < 1 || key > 26) {
			Utils.print("INVALID KEY!");
			return "INVALID KEY!";
		}
		this.key = --key; translate(key);
		return getMessage();
	}

	/** @encrypt: default no arg generates a random key */
	@Override
	public String encrypt() {
		key = nextInt(25) + 1;
		translate(key);
		return getMessage();
	}
	@Override
	public String decrypt() { translate(-key); return getMessage(); }

	private String translate(final int k) {
		for(int i = 0; i < message.length; i++)
			if (Character.isLowerCase(message[i])) message[i] = rotate(message[i], k);
		return new String(message);
	}

	private byte rotate(final byte c, final int key) {
		for (int i = 0; i < 26; i++)
			if (c == alphabet.charAt(i)) return (byte)alphabet.charAt((i + key + 26) % 26);
		Utils.print("ERROR! We shouldn't be here... -- CaeserCipher.class"); return c;
	}

	/** @method shred: Randomizes current data  */
	@Override
	public void shred() {
		for(int i = 0; i < message.length; i++) message[i] = nextByte();
	}

	@Override
	public String toString() { return new String(message); }

}
