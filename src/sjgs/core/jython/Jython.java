package sjgs.core.jython;

import static sjgs.utils.Utils.readTextFileAsString;
import org.python.util.PythonInterpreter;
import sjgs.graphics.ui.InventorySystem;
import sjgs.utils.pyutils.PyUtils;

public final class Jython {

	public static final PythonInterpreter pi = new PythonInterpreter();

	public void __init__() {
		//final long then = System.nanoTime();
		initialize();
		imports();
		loadMethodsIntoInterpretor();
		createPyFuncs();
		//pi.exec("print " + "'Jython / Python initialized --- Time taken: " + Utils.df.format((System.nanoTime() - then) / Utils.second) + " seconds'");
	}

	// INITIALIZES INTERP WITH SYSTEM INFORMATION
	private static void initialize() { PyUtils.initializePySystem(); }

	// IMPORTS ALL NECESSARY JAVA CLASSES FOR INTERP
	private static void imports() { pi.exec(readTextFileAsString("/sjgs/core/jython/engine_imports.py")); }

	// RUNS THE MODULES THROUGH THE INTERP TO LOAD THE NAMES OF FUNCS
	private static void loadMethodsIntoInterpretor() {
		pi.exec(readTextFileAsString("/sjgs/graphics/ui/__engine_inventory.py"));
		pi.exec(readTextFileAsString("/sjgs/base_objects/mob_ai/mob_travel_around_rectangle.py"));
	}

	private static void createPyFuncs() {
		InventorySystem.__engine_init_pyfuncs();
	}

}
