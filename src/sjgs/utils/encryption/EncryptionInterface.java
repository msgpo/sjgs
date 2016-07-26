package sjgs.utils.encryption;

import sjgs.utils.Utils;

public interface EncryptionInterface {

	public abstract String decrypt();
	public abstract String encrypt();
	public abstract void shred();
	@Override
	public abstract String toString();

	default String getMessage() { return toString(); }
	default void print() { Utils.print(getMessage()); }

}
