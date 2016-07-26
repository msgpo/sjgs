package sjgs.utils.encryption;

/** @class: This is essentially a CaeserCipher for every character
 * 		    in the encrypted message. Much more secure and more
 * 			truly "encrypted". Each letter in the message has
 * 			its own randomly generated key   				  */
public class StrongCaesarCipher implements EncryptionInterface {

	private final CaesarCipher[] message;

	public StrongCaesarCipher(final String message) {
		this.message = new CaesarCipher[message.length()];
		for(int i = 0; i < message.length(); i++)
			this.message[i] = new CaesarCipher(""+message.charAt(i));
	}

	@Override
	public String decrypt() {
		for (final CaesarCipher element : message)
			element.decrypt();
		return getMessage();
	}

	@Override
	public String encrypt() {
		for (final CaesarCipher element : message)
			element.encrypt();
		return getMessage();
	}

	@Override
	public void shred() { for (final CaesarCipher element : message)
		element.shred(); }

	@Override
	public String getMessage() { return toString(); }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final CaesarCipher element : message)
			sb.append(element.toString());
		return sb.toString();
	}
}
