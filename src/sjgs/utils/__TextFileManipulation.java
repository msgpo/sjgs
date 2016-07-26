package sjgs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.JOptionPane;

class __TextFileManipulation {

	static int getNumLinesFromTextFile(final String path) { /* loads as resource to work in jars */
		int count = 0;
		try { final InputStream stream = __TextFileManipulation.class.getResourceAsStream(path);
		final Scanner scanner = new Scanner(stream);
		while (scanner.hasNextLine()) { scanner.nextLine(); count++; }
		stream.close(); scanner.close();
		} catch (final Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(null, "Text Files Failed To Load --- Utils.java"); }
		return count;
	}

	static String[] makeStringArrayFromLinesOfTextFile(final String path) { /* loads as resource to work in jars */
		final String[] lines = new String[getNumLinesFromTextFile(path)];
		try { final InputStream stream = __TextFileManipulation.class.getResourceAsStream(path);
		final Scanner scanner = new Scanner(stream);
		for (int i = 0; scanner.hasNextLine(); i++) lines[i] = scanner.nextLine();
		stream.close(); scanner.close();
		} catch (final Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(null, "Text Files Failed To Load --- Utils.java"); }
		return lines;
	}

	static StringBuilder[] makeStringBuilderArrayFromLinesOfTextFile(final String path) { /* loads as resource to work in jars */
		final StringBuilder[] lines = new StringBuilder[getNumLinesFromTextFile(path)];
		try { final InputStream stream = __TextFileManipulation.class.getResourceAsStream(path);
		final Scanner scanner = new Scanner(stream);
		for (int i = 0; scanner.hasNextLine(); i++) lines[i] = new StringBuilder(scanner.nextLine());
		stream.close(); scanner.close();
		} catch (final Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(null, "Text Files Failed To Load --- Utils.java"); }
		return lines;
	}

	static String readTextFileAsString(final String path){
		InputStream stream = null;
		// NOTICE: Depending on what was used for making the jar, you *may* or *may not* have a /src/
		//         super directory, or it may be placed alongside your class files with anything that
		// 		   wasn't a *.java file during compile time. Fear not, as this will catch both of these
		//		   cases should they be an issue.
		try { stream = __TextFileManipulation.class.getResourceAsStream(path); } catch (final Exception e) {
			stream = __TextFileManipulation.class.getResourceAsStream("/src/" + path);
		}
		Scanner file = new Scanner(stream);
		final StringBuilder sb = new StringBuilder();
		while(file.hasNextLine()) sb.append(file.nextLine() + "\n"); // NOTE: Preserves new line characters!
		try { stream.close(); file.close(); } catch (final IOException e) { e.printStackTrace(); System.exit(1); }
		if(sb.toString().length() == 0) System.err.println("PROBLEM: TEXT FILE APPEARS TO BE BLANK? -- Utils.class");
		file.close(); file = null; stream = null;
		return sb.toString();
	}
}
