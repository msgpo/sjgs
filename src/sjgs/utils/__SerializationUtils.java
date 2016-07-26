package sjgs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class __SerializationUtils {

	static final String userHomeDir = System.getProperty("user.home");

	static synchronized void saveObject(final Object obj, final String path){
		try { ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oos.writeObject(obj); oos.close(); oos = null;
		} catch (final Exception e) { e.printStackTrace(); System.exit(1); }
	}

	static synchronized Object readObject(final String path){
		try { InputStream is = __SerializationUtils.class.getResourceAsStream(path);
		ObjectInputStream oos = new ObjectInputStream(is);
		final Object object = oos.readObject();
		oos.close(); oos = null;
		is.close(); is = null;
		return object;
		} catch (final Exception e) { e.printStackTrace(); System.exit(1); return null; }
	}

	static synchronized void writeStringToFile(final String string, final String path) {
		try (FileWriter fw = new FileWriter(new File(path))) {
			fw.write(string);
		} catch (final Exception e) { e.printStackTrace(); }
	}
}
